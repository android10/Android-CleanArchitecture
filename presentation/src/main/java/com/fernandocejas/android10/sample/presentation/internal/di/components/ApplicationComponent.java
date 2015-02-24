/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.AndroidApplication;
import com.fernandocejas.android10.sample.presentation.internal.di.ApplicationModule;
import dagger.Component;

@Component(modules = ApplicationModule.class)
interface ApplicationComponent {
  AndroidApplication inject(AndroidApplication androidApplication);
}
