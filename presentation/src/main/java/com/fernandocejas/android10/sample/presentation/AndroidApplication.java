/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation;

import android.app.Application;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.Dagger_ApplicationComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ApplicationModule;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;
import com.fernandocejas.android10.sample.presentation.view.fragment.UserDetailsFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.UserListFragment;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override public void onCreate() {
    super.onCreate();
    this.initializeInjector();
  }

  private void initializeInjector() {
    this.applicationComponent = Dagger_ApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
    this.applicationComponent.inject(this);
  }

  public void inject(BaseActivity baseActivity) {
    this.applicationComponent.inject(baseActivity);
  }

  public void inject(UserListFragment userListFragment) {
    this.applicationComponent.inject(userListFragment);
  }

  public void inject(UserDetailsFragment userDetailsFragment) {
    this.applicationComponent.inject(userDetailsFragment);
  }
}
