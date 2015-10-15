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
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetailsUseCaseParams;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.view.UserDetailsView;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity public class UserDetailsPresenter<T extends UserDetailsView>
    extends LoadDataViewPresenter<T> {

  //injected
  private final UseCase<GetUserDetailsUseCaseParams> getUserDetailsUseCase;
  private final UserModelDataMapper userModelDataMapper;
  private final int userId;

  @Inject public UserDetailsPresenter(
      @Named("userDetails") UseCase<GetUserDetailsUseCaseParams> getUserDetailsUseCase,
      UserModelDataMapper userModelDataMapper, int userId) {

    this.getUserDetailsUseCase = getUserDetailsUseCase;
    this.userModelDataMapper = userModelDataMapper;
    this.userId = userId;
  }

  @Override public void destroy() {
    super.destroy();
    this.getUserDetailsUseCase.unsubscribe();
  }

  @Override public void initialize(T view) {
    super.initialize(view);
    this.loadUserDetails();
  }

  private void loadUserDetails() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getUserDetails();
  }

  private void showViewLoading() {
    super.view.showLoading();
  }

  private void hideViewLoading() {
    super.view.hideLoading();
  }

  private void showViewRetry() {
    super.view.showRetry();
  }

  private void hideViewRetry() {
    super.view.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage =
        ErrorMessageFactory.create(super.view.getContext(), errorBundle.getException());
    super.view.showError(errorMessage);
  }

  private void showUserDetailsInView(User user) {
    final UserModel userModel = this.userModelDataMapper.transform(user);
    super.view.renderUser(userModel);
  }

  private void getUserDetails() {
    this.getUserDetailsUseCase.setupUseCase(
        GetUserDetailsUseCaseParams.builder().userId(this.userId).build())
        .execute(new DefaultSubscriber<User>() {

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
