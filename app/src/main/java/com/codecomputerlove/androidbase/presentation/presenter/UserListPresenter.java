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
package com.codecomputerlove.androidbase.presentation.presenter;

import android.support.annotation.NonNull;
import com.codecomputerlove.androidbase.domain.interactor.DefaultSubscriber;
import com.codecomputerlove.androidbase.domain.User;
import com.codecomputerlove.androidbase.domain.exception.DefaultErrorBundle;
import com.codecomputerlove.androidbase.domain.exception.ErrorBundle;
import com.codecomputerlove.androidbase.domain.interactor.UseCase;
import com.codecomputerlove.androidbase.presentation.exception.ErrorMessageFactory;
import com.codecomputerlove.androidbase.presentation.internal.di.PerActivity;
import com.codecomputerlove.androidbase.presentation.mapper.UserModelDataMapper;
import com.codecomputerlove.androidbase.presentation.model.UserModel;
import com.codecomputerlove.androidbase.presentation.view.UserListView;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class UserListPresenter extends DefaultSubscriber<List<User>> implements Presenter {

  private UserListView viewListView;

  private final UseCase getUserListUseCase;
  private final UserModelDataMapper userModelDataMapper;

  @Inject
  public UserListPresenter(@Named("userList") UseCase getUserListUserCase, UserModelDataMapper userModelDataMapper) {
    this.getUserListUseCase = getUserListUserCase;
    this.userModelDataMapper = userModelDataMapper;
  }

  public void setView(@NonNull UserListView view) {
    this.viewListView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.getUserListUseCase.unsubscribe();
  }

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
    this.getUserListUseCase.execute(this);
  }

  @Override public void onCompleted() {
    this.hideViewLoading();
  }

  @Override public void onError(Throwable e) {
    this.hideViewLoading();
    this.showErrorMessage(new DefaultErrorBundle((Exception) e));
    this.showViewRetry();
  }

  @Override public void onNext(List<User> users) {
    this.showUsersCollectionInView(users);
  }
}
