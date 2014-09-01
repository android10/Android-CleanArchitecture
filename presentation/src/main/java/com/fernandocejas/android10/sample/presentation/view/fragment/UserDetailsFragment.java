/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fernandocejas.android10.sample.presentation.R;

/**
 * Fragment that shows details of a certain user.
 */
public class UserDetailsFragment extends BaseFragment {

  private static final String ARGUMENT_KEY_USER_ID = "org.android10.ARGUMENT_USER_ID";

  private int userId;

  public UserDetailsFragment() { super(); }

  public static UserDetailsFragment newInstance(int userId) {
    UserDetailsFragment userDetailsFragment = new UserDetailsFragment();

    Bundle argumentsBundle = new Bundle();
    argumentsBundle.putInt(ARGUMENT_KEY_USER_ID, userId);
    userDetailsFragment.setArguments(argumentsBundle);

    return userDetailsFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.initialize();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_user_details, container, false);
    return fragmentView;
  }

  @Override void initializePresenter() {

  }

  /**
   * Initializes fragment's private members.
   */
  private void initialize() {
    this.userId = getArguments().getInt(ARGUMENT_KEY_USER_ID);
  }
}
