/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.presenter;

import android.support.annotation.NonNull;
import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetailsUseCase;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.view.UserDetailsView;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
public class UserDetailsPresenter implements Presenter {

  /** id used to retrieve user details */
  private int userId;

  private UserDetailsView viewDetailsView;

  private final GetUserDetailsUseCase getUserDetailsUseCase;
  private final UserModelDataMapper userModelDataMapper;

  public UserDetailsPresenter(GetUserDetailsUseCase getUserDetailsUseCase,
      UserModelDataMapper userModelDataMapper) {
    this.getUserDetailsUseCase = getUserDetailsUseCase;
    this.userModelDataMapper = userModelDataMapper;
  }

  public void setView(@NonNull UserDetailsView view) {
    this.viewDetailsView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  /**
   * Initializes the presenter by start retrieving user details.
   */
  public void initialize(int userId) {
    this.userId = userId;
    this.loadUserDetails();
  }

  /**
   * Loads user details.
   */
  private void loadUserDetails() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getUserDetails();
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.viewDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.viewDetailsView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.getContext(),
        errorBundle.getException());
    this.viewDetailsView.showError(errorMessage);
  }

  private void showUserDetailsInView(User user) {
    final UserModel userModel = this.userModelDataMapper.transform(user);
    this.viewDetailsView.renderUser(userModel);
  }

  private void getUserDetails() {
    this.getUserDetailsUseCase.execute(this.userId, this.userDetailsCallback);
  }

  private final GetUserDetailsUseCase.Callback userDetailsCallback = new GetUserDetailsUseCase.Callback() {
    @Override public void onUserDataLoaded(User user) {
      UserDetailsPresenter.this.showUserDetailsInView(user);
      UserDetailsPresenter.this.hideViewLoading();
    }

    @Override public void onError(ErrorBundle errorBundle) {
      UserDetailsPresenter.this.hideViewLoading();
      UserDetailsPresenter.this.showErrorMessage(errorBundle);
      UserDetailsPresenter.this.showViewRetry();
    }
  };
}
