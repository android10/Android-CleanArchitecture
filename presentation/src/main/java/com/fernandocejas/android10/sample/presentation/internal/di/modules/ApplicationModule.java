/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.internal.di.modules;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public final class ApplicationModule {

  private final Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides Context provideApplicationContext() {
    return this.application;
  }

  @Provides LayoutInflater provideLayoutInflater() {
    return LayoutInflater.from(this.application);
  }
}
