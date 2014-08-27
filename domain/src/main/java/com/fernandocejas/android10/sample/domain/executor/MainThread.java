/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.executor;

/**
 * UI thread abstraction created to change the execution context from any thread to the UI thread.
 */
public interface MainThread {
  /**
   * Causes the {@link Runnable} to be added to the message queue of the Main UI Thread
   * of the application.
   * @param runnable {@link Runnable} to be executed.
   */
  void post(Runnable runnable);
}
