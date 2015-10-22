package com.fernandocejas.android10.sample.presentation.view.fragment;

import com.fernandocejas.android10.sample.presentation.internal.di.components.ActivityComponent;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

/**
 * @author goraczka
 */
public abstract class BaseInjectableFragment<T extends ActivityComponent, P extends Presenter<V>, V extends BaseInjectableFragment>
    extends BaseFragment<P, V> {

  @Override public void onStart() {
    super.onStart();
    injectFragmentDependencies(getActivityComponentClass());
    getPresenter().initialize(getViewForPresenter());
  }

  protected abstract Class<T> getActivityComponentClass();

  //for self reference and type safety we have to call for a view
  protected abstract V getViewForPresenter();

  protected abstract void injectFragmentDependencies(Class<T> activityComponentClass);
}
