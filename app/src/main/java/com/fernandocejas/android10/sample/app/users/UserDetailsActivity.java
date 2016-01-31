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
import com.fernandocejas.android10.sample.app.core.di.modules.UserModule;
import com.fernandocejas.android10.sample.app.core.view.activity.BaseActivity;

/**
 * Activity that shows details of a certain user.
 */
public class UserDetailsActivity extends BaseActivity implements HasComponent<UserComponent> {

  private static final String INTENT_EXTRA_PARAM_USER_ID = "org.android10.INTENT_PARAM_USER_ID";
  private static final String INSTANCE_STATE_PARAM_USER_ID = "org.android10.STATE_PARAM_USER_ID";

  public static Intent getCallingIntent(Context context, int userId) {
    Intent callingIntent = new Intent(context, UserDetailsActivity.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId);
    return callingIntent;
  }

  private int userId;
  private UserComponent userComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_layout);

    this.initializeActivity(savedInstanceState);
    this.initializeInjector();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    if (outState != null) {
      outState.putInt(INSTANCE_STATE_PARAM_USER_ID, this.userId);
    }
    super.onSaveInstanceState(outState);
  }

  /**
   * Initializes this activity.
   */
  private void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      this.userId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_USER_ID, -1);
      addFragment(R.id.fragmentContainer, new UserDetailsFragment());
    } else {
      this.userId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_USER_ID);
    }
  }

  private void initializeInjector() {
    this.userComponent = DaggerUserComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .userModule(new UserModule(this.userId))
        .build();
  }

  @Override public UserComponent getComponent() {
    return userComponent;
  }
}
