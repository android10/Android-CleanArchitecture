/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.sample.presentation.navigation;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.fragment.InitFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.UserDetailsFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.UserListFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

  @Inject
  public void Navigator() {
    //empty
  }

  /**
   * Goes to the user list screen.
   *
   * @param activity An activity needed to load the destination fragment.
   */
  public void navigateToUserList(Activity activity) {
    if (activity != null) {
      replaceFragment(activity, R.id.container, UserListFragment.newInstance(), true);
    }
  }

  /**
   * Goes to the user details screen.
   *
   * @param activity An activity needed to open the destination fragment.
   */
  public void navigateToUserDetails(Activity activity, int userId) {
    if (activity != null) {
      replaceFragment(activity, R.id.container, UserDetailsFragment.newInstance(userId), true);
    }
  }

  public void navigateToInitFragment(Activity activity) {
    if (activity != null) {
      replaceFragment(activity, R.id.container, InitFragment.newInstance(), false);
    }
  }

  protected void replaceFragment(Activity activity, int containerViewId, Fragment fragment,
                                 boolean addToBackstack) {
    FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
    fragmentTransaction.replace(containerViewId, fragment);
    if (addToBackstack) {
      fragmentTransaction.addToBackStack(null);
    }
    fragmentTransaction.commit();
  }
}
