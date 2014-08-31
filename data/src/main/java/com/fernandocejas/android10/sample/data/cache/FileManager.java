/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Helper class to do operations on regular files/directories.
 */
public class FileManager {

  private FileManager() {}

  private static class LazyHolder {
    private static final FileManager INSTANCE = new FileManager();
  }

  public static FileManager getInstance() {
    return LazyHolder.INSTANCE;
  }

  /**
   * Writes a file to Disk.
   * This is an I/O operation and this method executes in the main thread, so it is recommended to
   * perform this operation using another thread.
   *
   * @param file The file to write to Disk.
   */
  public void writeToFile(File file, String fileContent) {
    if (!file.exists()) {
      try {
        FileWriter writer = new FileWriter(file);
        writer.write(fileContent);
        writer.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {

      }
    }
  }

  /**
   * Reads a content from a file.
   * This is an I/O operation and this method executes in the main thread, so it is recommended to
   * perform the operation using another thread.
   *
   * @param file The file to read from.
   * @return A string with the content of the file.
   */
  public String readFileContent(File file) {
    StringBuilder fileContentBuilder = new StringBuilder();
    if (file.exists()) {
      String stringLine;
      try {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((stringLine = bufferedReader.readLine()) != null) {
          fileContentBuilder.append(stringLine + "\n");
        }
        bufferedReader.close();
        fileReader.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return fileContentBuilder.toString();
  }

  /**
   * Returns a boolean indicating whether this file can be found on the underlying file system.
   *
   * @param file The file to check existence.
   * @return true if this file exists, false otherwise.
   */
  public boolean exists(File file) {
    return file.exists();
  }

  /**
   * Warning: Deletes the content of a directory.
   * This is an I/O operation and this method executes in the main thread, so it is recommended to
   * perform the operation using another thread.
   *
   * @param directory The directory which its content will be deleted.
   */
  public void clearDirectory(File directory) {
    if (directory.exists()) {
      for (File file : directory.listFiles()) {
        file.delete();
      }
    }
  }

  /**
   * Write a value to a user preferences file.
   *
   * @param context {@link android.content.Context} to retrieve android user preferences.
   * @param preferenceFileName A file name reprensenting where data will be written to.
   * @param key A string for the key that will be used to retrieve the value in the future.
   * @param value A long representing the value to be inserted.
   */
  public void writeToPreferences(Context context, String preferenceFileName, String key,
      long value) {

    SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
        Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putLong(key, value);
    editor.apply();
  }

  /**
   * Get a value from a user preferences file.
   *
   * @param context {@link android.content.Context} to retrieve android user preferences.
   * @param preferenceFileName A file name representing where data will be get from.
   * @param key A key that will be used to retrieve the value from the preference file.
   * @return A long representing the value retrieved from the preferences file.
   */
  public long getFromPreferences(Context context, String preferenceFileName, String key) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
        Context.MODE_PRIVATE);
    return sharedPreferences.getLong(key, 0);
  }
}
