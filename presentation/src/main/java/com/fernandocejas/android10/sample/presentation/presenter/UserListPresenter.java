/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.presenter;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCase;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.view.UserListView;
import java.util.Collection;

public class UserListPresenter implements Presenter {

  private final UserListView viewListView;
  private final GetUserListUseCase getUserListUseCase;
  private final UserModelDataMapper userModelDataMapper;

  public UserListPresenter(UserListView userListView, GetUserListUseCase getUserListUserCase,
      UserModelDataMapper userModelDataMapper) {
    if (userListView == null || getUserListUserCase == null || userModelDataMapper == null) {
      throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
    }
    this.viewListView = userListView;
    this.getUserListUseCase = getUserListUserCase;
    this.userModelDataMapper = userModelDataMapper;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  /**
   * Initializes the presenter by start retrieving the user list.
   */
  public void initialize() {
    this.loadUserList();
  }

  /**
   * Loads all users.
   */
  private void loadUserList() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getUserList();
  }

  public void onUserClicked(UserModel userModel) {
    this.viewListView.viewUser(userModel);
  }

  private void showViewLoading() {
    this.viewListView.showLoading();
  }

  private void hideViewLoading() {
    this.viewListView.hideLoading();
  }

  private void showViewRetry() {
    this.viewListView.showRetry();
  }

  private void hideViewRetry() {
    this.viewListView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewListView.getContext(),
        errorBundle.getException());
    this.viewListView.showError(errorMessage);
  }

  private void showUsersCollectionInView(Collection<User> usersCollection) {
    final Collection<UserModel> userModelsCollection =
        this.userModelDataMapper.transform(usersCollection);
    this.viewListView.renderUserList(userModelsCollection);
  }

  private void getUserList() {
    this.getUserListUseCase.execute(userListCallback);
  }

  private final GetUserListUseCase.Callback userListCallback = new GetUserListUseCase.Callback() {
    @Override public void onUserListLoaded(Collection<User> usersCollection) {
      UserListPresenter.this.showUsersCollectionInView(usersCollection);
      UserListPresenter.this.hideViewLoading();
    }

    @Override public void onError(ErrorBundle errorBundle) {
      UserListPresenter.this.hideViewLoading();
      UserListPresenter.this.showErrorMessage(errorBundle);
      UserListPresenter.this.showViewRetry();
    }
  };
}
