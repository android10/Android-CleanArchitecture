/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.navigation;

import android.content.Context;
import android.content.Intent;
import com.fernandocejas.android10.sample.presentation.view.activity.UserDetailsActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.UserListActivity;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

  public void Navigator() {
    //empty
  }

  /**
   * Goes to the user list screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToUserList(Context context) {
    if (context != null) {
      Intent intentToLaunch = UserListActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the user details screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToUserDetails(Context context, int userId) {
    if (context != null) {
      Intent intentToLaunch = UserDetailsActivity.getCallingIntent(context, userId);
      context.startActivity(intentToLaunch);
    }
  }
}
