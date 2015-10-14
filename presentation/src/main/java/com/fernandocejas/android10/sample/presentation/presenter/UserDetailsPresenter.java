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
import com.fernandocejas.android10.sample.presentation.view.UserDetailsView;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerFragment public class UserDetailsPresenter implements Presenter {

  private final UserDetailsView userDetailsView;
  private final UseCase getUserDetailsUseCase;
  private final UserModelDataMapper userModelDataMapper;

  @Inject public UserDetailsPresenter(@Named("userDetails") UseCase getUserDetailsUseCase,
      UserModelDataMapper userModelDataMapper, UserDetailsView userDetailsView) {

    this.getUserDetailsUseCase = getUserDetailsUseCase;
    this.userModelDataMapper = userModelDataMapper;
    this.userDetailsView = userDetailsView;
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
    this.getUserDetailsUseCase.unsubscribe();
  }

  @Override public void initialize() {
    this.loadUserDetails();
  }

  private void loadUserDetails() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getUserDetails();
  }

  private void showViewLoading() {
    this.userDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.userDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.userDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.userDetailsView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage =
        ErrorMessageFactory.create(this.userDetailsView.getContext(), errorBundle.getException());
    this.userDetailsView.showError(errorMessage);
  }

  private void showUserDetailsInView(User user) {
    final UserModel userModel = this.userModelDataMapper.transform(user);
    this.userDetailsView.renderUser(userModel);
  }

  private void getUserDetails() {
    this.getUserDetailsUseCase.execute(new DefaultSubscriber<User>() {
      @Override public void onCompleted() {
        UserDetailsPresenter.this.hideViewLoading();
      }

      @Override public void onError(Throwable e) {
        UserDetailsPresenter.this.hideViewLoading();
        UserDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
        UserDetailsPresenter.this.showViewRetry();
      }

      @Override public void onNext(User user) {
        UserDetailsPresenter.this.showUserDetailsInView(user);
      }
    });
  }
}
