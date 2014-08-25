/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.presenter;

import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCase;
import com.fernandocejas.android10.sample.presentation.view.UserListView;

/**
 *
 */
public class UserListPresenter implements Presenter {

  private final UserListView viewListView;
  private final GetUserListUseCase getUserListUserCase;

  public UserListPresenter(UserListView userListView, GetUserListUseCase getUserListUserCase) {
    if (userListView == null || getUserListUserCase == null) {
      throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
    }
    this.viewListView = userListView;
    this.getUserListUserCase = getUserListUserCase;
  }

  @Override public void resume() {
    this.loadUserList();
  }

  @Override public void pause() {
    //nothing to do here
  }

  private void loadUserList() {
    this.viewListView.hideRetry();
    this.viewListView.showLoading();
  }
}
