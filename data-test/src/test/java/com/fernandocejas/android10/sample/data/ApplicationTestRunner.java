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
package com.codecomputerlove.androidbase.data;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;

/**
 * Custom test runner for robolectric unit/integration tests.
 */
public class ApplicationTestRunner extends RobolectricTestRunner {

  //Maximun SDK Robolectric will compile (issues with SDK > 18)
  private static final int MAX_SDK_SUPPORTED_BY_ROBOLECTRIC = 18;

  private static final String ANDROID_MANIFEST_PATH = "data/src/main/AndroidManifest.xml";
  private static final String ANDROID_MANIFEST_RES_PATH = "data/src/main/res";

  /**
   * Call this constructor to specify the location of resources and AndroidManifest.xml.
   *
   * @throws org.junit.runners.model.InitializationError
   */
  public ApplicationTestRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
  }

  @Override protected AndroidManifest getAppManifest(Config config) {
    return new AndroidManifest(Fs.fileFromPath(ANDROID_MANIFEST_PATH),
        Fs.fileFromPath(ANDROID_MANIFEST_RES_PATH)) {
      @Override
      public int getTargetSdkVersion() {
        return MAX_SDK_SUPPORTED_BY_ROBOLECTRIC;
      }
    };
  }
}
