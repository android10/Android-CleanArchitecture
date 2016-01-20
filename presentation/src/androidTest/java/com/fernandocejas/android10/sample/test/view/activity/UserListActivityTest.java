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
package com.fernandocejas.android10.sample.test.view.activity;

import android.app.Fragment;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.activity.UserListActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class UserListActivityTest {

  private UserListActivity userListActivity;

  @Rule public ActivityTestRule<UserListActivity> activityRule = new ActivityTestRule<>(
      UserListActivity.class,
      true,   // initialTouchMode
      false); // launchActivity. False to set up mocks before activity launch

  @Before public void setUp() {
    activityRule.launchActivity(createTargetIntent());
    userListActivity = activityRule.getActivity();
  }

  @Test
  public void testContainsUserListFragment() {
    Fragment userListFragment =
        userListActivity.getFragmentManager().findFragmentById(R.id.fragmentUserList);
    assertThat(userListFragment, is(notNullValue()));
  }

  @Test
  public void testContainsProperTitle() {
    String actualTitle = this.userListActivity.getTitle().toString().trim();

    assertThat(actualTitle, is("Users List"));
  }

  private Intent createTargetIntent() {
    Intent intentLaunchActivity =
        UserListActivity.getCallingIntent(InstrumentationRegistry.getTargetContext());

    return intentLaunchActivity;
  }
}
