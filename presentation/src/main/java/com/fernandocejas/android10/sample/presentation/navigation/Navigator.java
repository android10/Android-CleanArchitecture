/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.navigation;

import android.content.Context;
import android.content.Intent;
import com.fernandocejas.android10.sample.presentation.view.activity.UserListActivity;

/**
 * Class used to navigate through the application.
 */
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
      Intent intentToLaunch = createIntentToLaunch(context, UserListActivity.class);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Create a navigation intent to be launched.
   *
   * @param context A context is needed.
   * @param destinyClass The {@link android.app.Activity} class to be open.
   * @return A valid {@link android.content.Intent}
   */
  private Intent createIntentToLaunch(Context context, Class destinyClass) {
    Intent intent = new Intent(context, destinyClass);
    return intent;
  }
}
