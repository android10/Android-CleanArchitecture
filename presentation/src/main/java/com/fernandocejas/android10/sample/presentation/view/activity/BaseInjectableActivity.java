package com.fernandocejas.android10.sample.presentation.view.activity;

import android.app.Fragment;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ActivityComponent;

/**
 * @author goraczka
 */
public abstract class BaseInjectableActivity<T extends ActivityComponent> extends BaseActivity
    implements HasComponent<T> {

  private T component;

  //component initialization is done here to enable subclasses to have time for inserting
  //fragments and all needed data, that itself has to have some things injected
  @Override public void onAttachFragment(Fragment fragment) {
    component = initializeInjector();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    component = null;
  }

  protected abstract T initializeInjector();

  public T getComponent() {
    return component;
  }
}
