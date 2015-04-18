/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data;

import java.lang.reflect.Field;
import org.junit.runner.RunWith;

/**
 * Base class for Robolectric data layer tests running with a custom Test Runner.
 * Inherit from this class to create a test.
 */
@RunWith(ApplicationTestRunner.class)
public abstract class ApplicationTestCase {

  /**
   * Resets a Singleton class.
   * This works using reflection and looking for a private field in the singleton called
   * "INSTANCE".
   * It is actually a workaround (hack?) to avoid global state when testing in isolation.
   *
   * @param clazz The class to reset.
   */
  protected void resetSingleton(Class clazz) {
    Field instance;
    try {
      instance = clazz.getDeclaredField("INSTANCE");
      instance.setAccessible(true);
      instance.set(null, null);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
