/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.viewmodel;

import android.content.Context;

import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;

/**
 * Interface representing a View that will use to load data.
 */
public interface LoadDataView {
  /**
   * Show a view with a progress bar indicating a loading process.
   */
  void showLoading();

  /**
   * Hide a loading view.
   */
  void hideLoading();

  /**
   * Show an error message
   *
   * @param errorBundle An errorBundle representing an error.
   */
  void showError(ErrorBundle errorBundle);
}
