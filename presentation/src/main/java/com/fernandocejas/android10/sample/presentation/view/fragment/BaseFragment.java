/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;
import com.fernandocejas.android10.sample.presentation.AndroidApplication;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  /**
   * Shows a {@link android.widget.Toast} message.
   *
   * @param message An string representing a message to be shown.
   */
  protected void showToastMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  /**
   * Get the Android Main Application.
   *
   * @return singleton {@link com.fernandocejas.android10.sample.presentation.AndroidApplication}
   */
  protected AndroidApplication getApplication() {
    return (AndroidApplication)getActivity().getApplication();
  }
}
