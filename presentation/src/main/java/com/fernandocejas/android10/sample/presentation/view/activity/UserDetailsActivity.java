/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import com.fernandocejas.android10.sample.presentation.R;

/**
 * Activity that shows details of a certain user.
 */
public class UserDetailsActivity extends Activity {
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_user_details);
  }
}
