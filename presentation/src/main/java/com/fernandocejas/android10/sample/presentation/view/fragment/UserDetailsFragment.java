/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.data.cache.FileManager;
import com.fernandocejas.android10.sample.data.cache.UserCache;
import com.fernandocejas.android10.sample.data.cache.UserCacheImpl;
import com.fernandocejas.android10.sample.data.cache.serializer.JsonSerializer;
import com.fernandocejas.android10.sample.data.entity.mapper.UserEntityDataMapper;
import com.fernandocejas.android10.sample.data.executor.JobExecutor;
import com.fernandocejas.android10.sample.data.repository.UserDataRepository;
import com.fernandocejas.android10.sample.data.repository.datasource.UserDataStoreFactory;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetailsUseCase;
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetailsUseCaseImpl;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.UIThread;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.presenter.UserDetailsPresenter;
import com.fernandocejas.android10.sample.presentation.view.UserDetailsView;
import com.fernandocejas.android10.sample.presentation.view.component.AutoLoadImageView;

/**
 * Fragment that shows details of a certain user.
 */
public class UserDetailsFragment extends BaseFragment implements UserDetailsView {

  private static final String ARGUMENT_KEY_USER_ID = "org.android10.ARGUMENT_USER_ID";

  private int userId;
  private UserDetailsPresenter userDetailsPresenter;

  @InjectView(R.id.iv_cover) AutoLoadImageView iv_cover;
  @InjectView(R.id.tv_fullname) TextView tv_fullname;
  @InjectView(R.id.tv_email) TextView tv_email;
  @InjectView(R.id.tv_followers) TextView tv_followers;
  @InjectView(R.id.tv_description) TextView tv_description;
  @InjectView(R.id.rl_progress) RelativeLayout rl_progress;
  @InjectView(R.id.rl_retry) RelativeLayout rl_retry;
  @InjectView(R.id.bt_retry) Button bt_retry;

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
    ButterKnife.inject(this, fragmentView);

    return fragmentView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.userDetailsPresenter.initialize(this.userId);
  }

  @Override public void onResume() {
    super.onResume();
    this.userDetailsPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.userDetailsPresenter.pause();
  }

  @Override void initializePresenter() {
    // All these dependency initialization could have been avoided using a
    // dependency injection framework. But in this case are used this way for
    // LEARNING EXAMPLE PURPOSE.
    ThreadExecutor threadExecutor = JobExecutor.getInstance();
    PostExecutionThread postExecutionThread = UIThread.getInstance();

    JsonSerializer userCacheSerializer = new JsonSerializer();
    UserCache userCache = UserCacheImpl.getInstance(getActivity(), userCacheSerializer,
        FileManager.getInstance(), threadExecutor);
    UserDataStoreFactory userDataStoreFactory =
        new UserDataStoreFactory(this.getContext(), userCache);
    UserEntityDataMapper userEntityDataMapper = new UserEntityDataMapper();
    UserRepository userRepository = UserDataRepository.getInstance(userDataStoreFactory,
        userEntityDataMapper);

    GetUserDetailsUseCase getUserDetailsUseCase = new GetUserDetailsUseCaseImpl(userRepository,
        threadExecutor, postExecutionThread);
    UserModelDataMapper userModelDataMapper = new UserModelDataMapper();

    this.userDetailsPresenter =
        new UserDetailsPresenter(this, getUserDetailsUseCase, userModelDataMapper);
  }

  @Override public void renderUser(UserModel user) {
    if (user != null) {
      this.iv_cover.setImageUrl(user.getCoverUrl());
      this.tv_fullname.setText(user.getFullName());
      this.tv_email.setText(user.getEmail());
      this.tv_followers.setText(String.valueOf(user.getFollowers()));
      this.tv_description.setText(user.getDescription());
    }
  }

  @Override public void showLoading() {
    this.rl_progress.setVisibility(View.VISIBLE);
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override public void hideLoading() {
    this.rl_progress.setVisibility(View.GONE);
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override public void showRetry() {
    this.rl_retry.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    this.rl_retry.setVisibility(View.GONE);
  }

  @Override public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override public Context getContext() {
    return getActivity().getApplicationContext();
  }

  /**
   * Initializes fragment's private members.
   */
  private void initialize() {
    this.userId = getArguments().getInt(ARGUMENT_KEY_USER_ID);
  }

  /**
   * Loads all users.
   */
  private void loadUserDetails() {
    if (this.userDetailsPresenter != null) {
      this.userDetailsPresenter.initialize(this.userId);
    }
  }

  @OnClick(R.id.bt_retry)
  void onButtonRetryClick() {
    UserDetailsFragment.this.loadUserDetails();
  }
}
