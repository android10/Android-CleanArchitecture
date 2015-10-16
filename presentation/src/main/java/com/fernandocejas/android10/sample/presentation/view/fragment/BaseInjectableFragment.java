package com.fernandocejas.android10.sample.presentation.view.fragment;

import com.fernandocejas.android10.sample.data.codingtools.GenericsUtils;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ActivityComponent;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

/**
 * @author goraczka
 */
public abstract class BaseInjectableFragment<T extends ActivityComponent, P extends Presenter<V>, V extends BaseInjectableFragment>
    extends BaseFragment<P, V> {

  protected static Class activityComponentClassCache;

  @Override public void onStart() {
    super.onStart();
    injectFragmentDependencies(getActivityComponentClass());
    getPresenter().initialize(getViewForPresenter());
  }

  //the usage of hardcore generics is done here only to infer the re
  @SuppressWarnings(value = "unchecked") protected Class<T> getActivityComponentClass() {
    if (activityComponentClassCache == null) {
      activityComponentClassCache = GenericsUtils
          .getTypeArguments(BaseInjectableFragment.class, this.getClass())
          .get(0);
    }
    return (Class<T>) activityComponentClassCache;
  }

  //for self reference and type safety we have to call for a view
  protected abstract V getViewForPresenter();

  protected abstract void injectFragmentDependencies(Class<T> activityComponentClass);
}
