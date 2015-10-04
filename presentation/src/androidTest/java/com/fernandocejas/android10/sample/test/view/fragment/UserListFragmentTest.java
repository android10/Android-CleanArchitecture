/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.sample.test.view.fragment;

        import android.app.Fragment;
        import android.os.SystemClock;
        import android.test.ActivityInstrumentationTestCase2;
        import com.fernandocejas.android10.sample.presentation.view.activity.MainActivity;
        import com.fernandocejas.android10.sample.presentation.view.fragment.UserListFragment;

        import static org.hamcrest.CoreMatchers.is;
        import static org.hamcrest.CoreMatchers.notNullValue;
        import static org.hamcrest.MatcherAssert.assertThat;

public class UserListFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mainActivity;

    public UserListFragmentTest() {
        super(MainActivity.class);
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
    }

    @Override protected void tearDown() throws Exception {
        super.tearDown();
    }

    private void setUpFragment() {
        mainActivity.onLoadClicked();
        waitForFragment(UserListFragment.class.getName(), 5000);
    }

    public void testContainsUserListFragment() {
        setUpFragment();
        Fragment userListFragment =
                mainActivity.getFragmentManager()
                        .findFragmentByTag(UserListFragment.class.getName());
        assertThat(userListFragment, is(notNullValue()));
        assertTrue(userListFragment.isVisible());
    }

    public void testContainsProperTitle() {
        setUpFragment();
        String actualTitle = this.mainActivity.getTitle().toString().trim();

        assertThat(actualTitle, is("Users List"));
    }

    private Fragment waitForFragment(String tag, int timeout) {
        long endTime = SystemClock.uptimeMillis() + timeout;
        while (SystemClock.uptimeMillis() <= endTime) {
            try {
                Thread.sleep(20); // Optional, to avoid active waiting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Fragment fragment = mainActivity.getFragmentManager().findFragmentByTag(tag);
            if (fragment != null) {
                return fragment;
            }
        }
        return null;
    }
}