/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.app.users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import com.fernandocejas.android10.sample.app.R;
import com.fernandocejas.android10.sample.app.core.di.HasComponent;
import com.fernandocejas.android10.sample.app.core.di.components.DaggerUserComponent;
import com.fernandocejas.android10.sample.app.core.di.components.UserComponent;
import com.fernandocejas.android10.sample.app.view.activity.BaseActivity;

/**
 * Activity that shows a list of Users.
 */
public class UserListActivity extends BaseActivity implements HasComponent<UserComponent>,
    UserListFragment.UserListListener {

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, UserListActivity.class);
  }

  private UserComponent userComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_layout);

    this.initializeInjector();
    if (savedInstanceState == null) {
      addFragment(R.id.fragmentContainer, new UserListFragment());
    }
  }

  private void initializeInjector() {
    this.userComponent = DaggerUserComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  @Override public UserComponent getComponent() {
    return userComponent;
  }

  @Override public void onUserClicked(UserModel userModel) {
    this.navigator.navigateToUserDetails(this, userModel.getUserId());
  }
}
