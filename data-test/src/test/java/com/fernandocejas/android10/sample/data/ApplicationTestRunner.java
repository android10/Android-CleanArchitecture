/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data;

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

  private static final String ANDROID_MANIFEST_PATH = "../data/src/main/AndroidManifest.xml";
  private static final String ANDROID_MANIFEST_RES_PATH = "../data/src/main/res";

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
