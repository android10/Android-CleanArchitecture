/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.sample.presentation.mvp.presenter;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetails;
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetails.Params;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.mvp.view.UserDetailsView;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class UserDetailsPresenter extends BasePresenter<UserDetailsView> {

    private final GetUserDetails getUserDetailsUseCase;
    private final UserModelDataMapper userModelDataMapper;

    private int userId;

    @Inject
    public UserDetailsPresenter(GetUserDetails getUserDetailsUseCase,
                                UserModelDataMapper userModelDataMapper) {
        this.getUserDetailsUseCase = getUserDetailsUseCase;
        this.userModelDataMapper = userModelDataMapper;
    }

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        refreshData();
    }

    @Override
    protected void onViewDetached() {
        super.onViewDetached();
        this.getUserDetailsUseCase.dispose();
    }

    @Override
    public void refreshData() {
        view.hideRetry();
        view.showLoading();
        this.getUserDetails(userId);
    }

    //TODO Ihor Vitruk: consider to use lombok library @Setter annotation
    public void setUserId(int userId) {
        this.userId = userId;
    }

    private void getUserDetails(int userId) {
        this.getUserDetailsUseCase.execute(new UserDetailsObserver(), Params.forUser(userId));
    }


    private void showErrorMessage(ErrorBundle errorBundle) {
        view.showError(errorBundle);
    }

    private void showUserDetailsInView(User user) {
        final UserModel userModel = this.userModelDataMapper.transform(user);
        view.renderUser(userModel);
    }

    private final class UserDetailsObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
            view.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            view.hideLoading();
            view.hideRetry();
            UserDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @Override
        public void onNext(User user) {
            UserDetailsPresenter.this.showUserDetailsInView(user);
        }
    }
}
