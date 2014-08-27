/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.presenter;

import com.fernandocejas.android10.sample.domain.interactor.Interactor;

/**
 * Abstract class representing a Presenter in a model view presenter (MVP) pattern.
 */
public abstract class BasePresenter {
  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onResume() method.
   */
  public abstract void resume();

  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onPause() method.
   */
  public abstract void pause();

  /**
   * Executes an {@link Interactor} asynchronously in a new thread.
   * @param interactor The {@link Interactor} to execute.
   */
  protected void executeInteractorAsync(Interactor interactor) {
    if (interactor == null) {
      throw new IllegalArgumentException("Interactor to execute cannot be null");
    }
  }
}
