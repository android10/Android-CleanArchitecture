/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.executor;

import com.fernandocejas.android10.sample.domain.interactor.Interactor;

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the {@link Interactor} out of the UI thread.
 *
 * Use this class to execute an {@link Interactor}.
 */
public interface Executor {
  /**
   * Executes an {@link Interactor} by creating a new runnable and execute its run() method.
   * @param interactor The {@link Interactor} to execute.
   */
  void execute(final Interactor interactor);

  /**
   * Executes a {@link Runnable}.
   * @param runnable The class that implements {@link Runnable} interface.
   */
  void execute(final Runnable runnable);
}
