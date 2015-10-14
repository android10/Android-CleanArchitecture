package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ActivityComponent;

/**
 * @author goraczka
 */
public abstract class BaseInjectableFragment<T extends ActivityComponent> extends BaseFragment{

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    injectFragmentDependencies(getActivityComponentClass());
  }

  @Override public void onDetach(){
    super.onDetach();
    //TODO: make fragment component null
  }

  protected abstract Class<T> getActivityComponentClass();

  protected abstract void injectFragmentDependencies(Class<T> activityComponentClass);
}
