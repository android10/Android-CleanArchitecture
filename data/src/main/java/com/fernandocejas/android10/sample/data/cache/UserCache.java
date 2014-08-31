/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.cache;

import com.fernandocejas.android10.sample.data.entity.UserEntity;

/**
 * An interface representing a user Cache.
 */
public interface UserCache {

  /**
   * Callback used to be notified when a {@link UserEntity} has been loaded.
   */
  interface UserCacheCallback {
    void onUserEntityLoaded(UserEntity userEntity);

    void onError(Exception exception);
  }

  /**
   * Gets an element from the cache using a {@link UserCacheCallback}.
   *
   * @param userId The user id to retrieve data.
   * @param callback The {@link UserCacheCallback} to notify the client.
   */
  void get(final int userId, final UserCacheCallback callback);

  /**
   * Puts and element into the cache.
   *
   * @param userEntity Element to insert in the cache.
   */
  void put(UserEntity userEntity);

  /**
   * Checks if an element (User) exists in the cache.
   *
   * @param userId The id used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  boolean isCached(final int userId);

  /**
   * Checks if the cache is expired.
   *
   * @return true, the cache is expired, otherwise false.
   */
  boolean isExpired();

  /**
   * Evict all elements of the cache.
   */
  void evictAll();
}
