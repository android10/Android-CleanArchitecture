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
