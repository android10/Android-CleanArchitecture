/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.sample.presentation.presenter;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultSubscriber;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerFragment;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.view.UserListView;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerFragment public class UserListPresenter implements Presenter {

  private final UserListView viewListView;
  private final UseCase getUserListUseCase;
  private final UserModelDataMapper userModelDataMapper;

  @Inject public UserListPresenter(@Named("userList") UseCase getUserListUserCase,
      UserModelDataMapper userModelDataMapper, UserListView viewListView) {

    this.getUserListUseCase = getUserListUserCase;
    this.userModelDataMapper = userModelDataMapper;
    this.viewListView = viewListView;
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
    this.getUserListUseCase.unsubscribe();
  }

  public void initialize() {
    this.loadUserList();
  }

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
    String errorMessage =
        ErrorMessageFactory.create(this.viewListView.getContext(), errorBundle.getException());
    this.viewListView.showError(errorMessage);
  }

  private void showUsersCollectionInView(Collection<User> usersCollection) {
    final Collection<UserModel> userModelsCollection =
        this.userModelDataMapper.transform(usersCollection);
    this.viewListView.renderUserList(userModelsCollection);
  }

  private void getUserList() {
    this.getUserListUseCase.execute(new DefaultSubscriber<List<User>>() {

      @Override public void onCompleted() {
        UserListPresenter.this.hideViewLoading();
      }

      @Override public void onError(Throwable e) {
        UserListPresenter.this.hideViewLoading();
        UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
        UserListPresenter.this.showViewRetry();
      }

      @Override public void onNext(List<User> users) {
        UserListPresenter.this.showUsersCollectionInView(users);
      }
    });
  }
}
