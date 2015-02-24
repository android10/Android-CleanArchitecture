/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.internal.di.modules;

import android.app.Application;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
final class ApplicationModule {

  private final Application application;

  ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides Application provideApplication() {
    return this.application;
  }
}
