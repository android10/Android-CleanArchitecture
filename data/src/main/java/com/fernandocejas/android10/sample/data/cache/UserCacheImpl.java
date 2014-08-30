/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.cache;

import android.content.Context;
import com.fernandocejas.android10.sample.data.cache.serializer.JsonSerializer;
import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.data.exception.UserNotFoundException;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import java.io.File;

/**
 * {@link UserCache} implementation.
 */
public class UserCacheImpl implements UserCache {

  private static UserCacheImpl INSTANCE;

  public static synchronized UserCacheImpl getInstance(Context context,
      JsonSerializer userCacheSerializer, FileManager fileManager, ThreadExecutor threadExecutor) {
    if (INSTANCE == null) {
      INSTANCE = new UserCacheImpl(context, userCacheSerializer, fileManager, threadExecutor);
    }
    return INSTANCE;
  }

  private static final String SETTINGS_FILE_NAME = "com.fernandocejas.android10.SETTINGS";
  private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

  private static final String DEFAULT_FILE_NAME = "user_";
  private static final long EXPIRATION_TIME = 60 * 10 * 1000;

  private final Context context;
  private final File cacheDir;
  private final JsonSerializer serializer;
  private final FileManager fileManager;
  private final ThreadExecutor threadExecutor;

  /**
   * Constructor of the class {@link UserCacheImpl}.
   *
   * @param context A
   * @param userCacheSerializer {@link JsonSerializer} for object serialization.
   * @param fileManager {@link FileManager} for saving serialized objects to the file system.
   */
  private UserCacheImpl(Context context, JsonSerializer userCacheSerializer,
      FileManager fileManager, ThreadExecutor executor) {
    if (context == null || userCacheSerializer == null || fileManager == null || executor == null) {
      throw new IllegalArgumentException("Invalid null parameter");
    }
    this.context = context.getApplicationContext();
    this.cacheDir = this.context.getCacheDir();
    this.serializer = userCacheSerializer;
    this.fileManager = fileManager;
    this.threadExecutor = executor;
  }

  /**
   * {@inheritDoc}
   *
   * @param userId The user id to retrieve data.
   * @param callback The {@link UserCacheCallback} to notify the client.
   */
  @Override public synchronized void get(int userId, UserCacheCallback callback) {
    File userEntitiyFile = this.buildFile(userId);
    String fileContent = this.fileManager.readFileContent(userEntitiyFile);
    UserEntity userEntity = this.serializer.deserialize(fileContent);

    if (userEntity != null) {
      callback.onUserEntityLoaded(userEntity);
    } else {
      callback.onError(new UserNotFoundException());
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param userEntity Element to insert in the cache.
   */
  @Override public synchronized void put(UserEntity userEntity) {
    if (userEntity != null) {
      File userEntitiyFile = this.buildFile(userEntity.getUserId());
      if (!isCached(userEntity.getUserId())) {
        String jsonString = this.serializer.serialize(userEntity);
        this.executeAsynchronously(new CacheWriter(this.fileManager, userEntitiyFile,
            jsonString));
        setLastCacheUpdateTimeMillis();
      }
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param userId The id used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  @Override public boolean isCached(int userId) {
    File userEntitiyFile = this.buildFile(userId);
    return this.fileManager.exists(userEntitiyFile);
  }

  /**
   * {@inheritDoc}
   *
   * @return true, the cache is expired, otherwise false.
   */
  @Override public boolean isExpired() {
    long currentTime = System.currentTimeMillis();
    long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

    boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

    if (expired) {
      this.evictAll();
    }

    return expired;
  }

  /**
   * {@inheritDoc}
   */
  @Override public synchronized void evictAll() {
    this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
  }

  /**
   * Build a file, used to be inserted in the disk cache.
   *
   * @param userId The id user to build the file.
   * @return A valid file.
   */
  private File buildFile(int userId) {
    StringBuilder fileNameBuilder = new StringBuilder();
    fileNameBuilder.append(this.cacheDir.getPath());
    fileNameBuilder.append(File.separator);
    fileNameBuilder.append(DEFAULT_FILE_NAME);
    fileNameBuilder.append(userId);

    return new File(fileNameBuilder.toString());
  }

  /**
   * Set in millis, the last time the cache was accessed.
   */
  private void setLastCacheUpdateTimeMillis() {
    long currentMillis = System.currentTimeMillis();
    this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
  }

  /**
   * Get in millis, the last time the cache was accessed.
   */
  private long getLastCacheUpdateTimeMillis() {
    return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE);
  }

  /**
   * Executes a {@link Runnable} in another Thread.
   *
   * @param runnable {@link Runnable} to execute
   */
  private void executeAsynchronously(Runnable runnable) {
    this.threadExecutor.execute(runnable);
  }

  /**
   * {@link Runnable} class for writing to disk.
   */
  private static class CacheWriter implements Runnable {
    private final FileManager fileManager;
    private final File fileToWrite;
    private final String fileContent;

    CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
      this.fileManager = fileManager;
      this.fileToWrite = fileToWrite;
      this.fileContent = fileContent;
    }

    @Override public void run() {
      this.fileManager.writeToFile(fileToWrite, fileContent);
    }
  }

  /**
   * {@link Runnable} class for evicting all the cached files
   */
  private static class CacheEvictor implements Runnable {
    private final FileManager fileManager;
    private final File cacheDir;

    CacheEvictor(FileManager fileManager, File cacheDir) {
      this.fileManager = fileManager;
      this.cacheDir = cacheDir;
    }

    @Override public void run() {
      this.fileManager.clearDirectory(this.cacheDir);
    }
  }
}
