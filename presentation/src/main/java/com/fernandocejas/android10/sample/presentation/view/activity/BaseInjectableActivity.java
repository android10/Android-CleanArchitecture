package com.fernandocejas.android10.sample.presentation.view.activity;

import android.os.Bundle;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ActivityComponent;

/**
 * @author goraczka
 */
public abstract class BaseInjectableActivity<T extends ActivityComponent> extends BaseActivity
    implements HasComponent<T> {

  private T component;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }


  @Override
  protected void onStart(){
    super.onStart();
    component = initializeActivityComponent();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    component = null;
  }

  public T getComponent() {
    return component;
  }

  /***
   * Initialize ActivityComponent used for injection in fragments
   *
   * @return - created ActivityComponent
   */
  protected abstract T initializeActivityComponent();
}
