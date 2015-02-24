/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation;

import android.app.Application;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override public void onCreate() {
    super.onCreate();

    this.applicationComponent = Dagger_ApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
    this.applicationComponent.inject(this);
  }

  public ApplicationComponent getComponent() {
    return applicationComponent;
  }
}
