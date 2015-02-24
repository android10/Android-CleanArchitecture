/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation;

import com.fernandocejas.android10.sample.presentation.navigation.Navigator;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
  @Provides Navigator provideNavigator() {
    return new Navigator();
  }
}
