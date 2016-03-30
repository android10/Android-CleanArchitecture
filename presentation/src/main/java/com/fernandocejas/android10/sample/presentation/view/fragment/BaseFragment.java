/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {

  private boolean mIsInjected = false;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      mIsInjected = onInjectView();
    } catch (IllegalStateException e) {
      Log.e(e.getClass().getSimpleName(), e.getMessage());
      mIsInjected = false;
    }
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (mIsInjected) onViewInjected(savedInstanceState);
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (!mIsInjected) {
      mIsInjected = onInjectView();
      if (mIsInjected) onViewInjected(null);
    }
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
   * Gets a component for dependency injection by its type.
   *
   * @throws IllegalStateException if component has not been initialized yet.
   */
  @SuppressWarnings("unchecked")
  protected <C> C getComponent(Class<C> componentType) throws IllegalStateException {
    C component = componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    if (component == null) {
      throw new IllegalStateException(componentType.getSimpleName() + " has not been initialized yet.");
    }
    return component;
  }

  /**
   * Called to do an optional injection. This will be called on {@link #onCreate(Bundle)} and if
   * an exception is thrown or false returned, on {@link #onActivityCreated(Bundle)} again.
   * Within this method get the injection component and inject the view. Based on returned value
   * {@link #onViewInjected(Bundle)} will be called. Check {@link #onViewInjected(Bundle)}
   * documentation for more info.
   *
   * @return True, if injection was successful, false otherwise. Returns false by default.
   * @throws IllegalStateException If there is a failure in getting injection component or
   *                               injection process itself. This can occur if activity holding
   *                               component instance has been killed by the system and has not
   *                               been initialized yet.
   */
  protected boolean onInjectView() throws IllegalStateException {
    // Return false by default.
    return false;
  }

  /**
   * Called when the fragment has been injected and the field injected can be initialized. This
   * will be called on {@link #onViewCreated(View, Bundle)} if {@link #onInjectView()} returned
   * true when executed on {@link #onCreate(Bundle)}, otherwise it will be called on
   * {@link #onActivityCreated(Bundle)} if {@link #onInjectView()} returned true right before.
   *
   * @param savedInstanceState If non-null, this fragment is being re-constructed
   * from a previous saved state as given here.
   */
  protected void onViewInjected(Bundle savedInstanceState) {
    // Intentionally left empty.
  }
}
