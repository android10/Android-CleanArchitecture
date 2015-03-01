/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.AndroidApplication;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ApplicationModule;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.UserModule;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;
import com.fernandocejas.android10.sample.presentation.view.fragment.UserDetailsFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.UserListFragment;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {ApplicationModule.class, ActivityModule.class, UserModule.class})
public interface ApplicationComponent {
  void inject(AndroidApplication androidApplication);
  void inject(BaseActivity activity);
  void inject(UserListFragment userListFragment);
  void inject(UserDetailsFragment userDetailsFragment);
}
