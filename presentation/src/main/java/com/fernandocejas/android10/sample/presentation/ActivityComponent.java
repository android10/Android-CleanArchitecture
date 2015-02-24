/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation;

import com.fernandocejas.android10.sample.presentation.view.activity.MainActivity;
import dagger.Component;

@Component(modules = ActivityModule.class)
public interface ActivityComponent {
  void inject(MainActivity activity);
}
