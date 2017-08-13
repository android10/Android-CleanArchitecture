/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.sample.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Helper class to do operations on regular files/directories.
 */
@Singleton
public class FileManager {

  @Inject
  FileManager() {}

  /**
   * Writes a file to Disk.
   * This is an I/O operation and this method executes in the main thread, so it is recommended to
   * perform this operation using another thread.
   *
   * @param file The file to write to Disk.
   */
  void writeToFile(File file, String fileContent) {
    if (!file.exists()) {
      try {
        final FileWriter writer = new FileWriter(file);
        writer.write(fileContent);
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
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
  String readFileContent(File file) {
    final StringBuilder fileContentBuilder = new StringBuilder();
    if (file.exists()) {
      String stringLine;
      try {
        final FileReader fileReader = new FileReader(file);
        final BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((stringLine = bufferedReader.readLine()) != null) {
          fileContentBuilder.append(stringLine).append("\n");
        }
        bufferedReader.close();
        fileReader.close();
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
  boolean exists(File file) {
    return file.exists();
  }

  /**
   * Warning: Deletes the content of a directory.
   * This is an I/O operation and this method executes in the main thread, so it is recommended to
   * perform the operation using another thread.
   *
   * @param directory The directory which its content will be deleted.
   */
  boolean clearDirectory(File directory) {
    boolean result = false;
    if (directory.exists()) {
      for (File file : directory.listFiles()) {
        result = file.delete();
      }
    }
    return result;
  }

  /**
   * Write a value to a user preferences file.
   *
   * @param context {@link android.content.Context} to retrieve android user preferences.
   * @param preferenceFileName A file name reprensenting where data will be written to.
   * @param key A string for the key that will be used to retrieve the value in the future.
   * @param value A long representing the value to be inserted.
   */
  void writeToPreferences(Context context, String preferenceFileName, String key,
      long value) {

    final SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
        Context.MODE_PRIVATE);
    final SharedPreferences.Editor editor = sharedPreferences.edit();
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
  long getFromPreferences(Context context, String preferenceFileName, String key) {
    final SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
        Context.MODE_PRIVATE);
    return sharedPreferences.getLong(key, 0);
  }
}
