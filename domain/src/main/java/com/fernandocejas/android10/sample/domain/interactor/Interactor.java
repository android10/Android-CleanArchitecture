/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

/**
 * Common interface for an interactor declared in the application.
 * This interface represents a execution unit for different use cases.
 *
 * By convention each interactor implementation will return the result using a Callback should be
 * executed in the UI thread.
 */
public interface Interactor {
  /**
   * Everything inside this method will be executed asynchronously.
   */
  void run();
}
