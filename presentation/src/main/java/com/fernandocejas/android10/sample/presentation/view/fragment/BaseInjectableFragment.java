package com.fernandocejas.android10.sample.presentation.view.fragment;

import com.fernandocejas.android10.sample.presentation.internal.di.components.ActivityComponent;

/**
 * @author goraczka
 */
public abstract class BaseInjectableFragment<T extends ActivityComponent>
    extends BaseFragment{

  @Override public void onStart() {
    super.onStart();
    injectFragmentDependencies(getActivityComponentClass());
    getPresenter().initialize();
  }

  protected abstract Class<T> getActivityComponentClass();

  protected abstract void injectFragmentDependencies(Class<T> activityComponentClass);
}
