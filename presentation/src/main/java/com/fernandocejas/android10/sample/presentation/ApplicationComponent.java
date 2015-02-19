/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation;

import dagger.Component;

@Component(modules = ApplicationModule.class)
interface ApplicationComponent {
  AndroidApplication injectApplication(AndroidApplication androidApplication);
}
