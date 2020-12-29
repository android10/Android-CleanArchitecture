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
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.domain.interactor.GetUserList;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.mvp.view.UserListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class UserListPresenter extends BasePresenter<UserListView> {

    private final GetUserList getUserListUseCase;
    private final UserModelDataMapper userModelDataMapper;

    @Inject
    public UserListPresenter(GetUserList getUserListUserCase,
                             UserModelDataMapper userModelDataMapper) {
        this.getUserListUseCase = getUserListUserCase;
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
        this.getUserListUseCase.dispose();
    }

    @Override
    public void refreshData() {
        this.loadUserList();
    }
    
    public void onUserClicked(UserModel userModel) {
        view.viewUser(userModel);
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        view.hideRetry();
        view.showLoading();
        this.getUserList();
    }

    private void showUsersCollectionInView(Collection<User> usersCollection) {
        final Collection<UserModel> userModelsCollection =
                this.userModelDataMapper.transform(usersCollection);
        view.renderUserList(userModelsCollection);
    }

    private void getUserList() {
        this.getUserListUseCase.execute(new UserListObserver(), null);
    }

    private final class UserListObserver extends DefaultObserver<List<User>> {

        @Override
        public void onComplete() {
            view.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            view.hideLoading();
            view.showError(new DefaultErrorBundle((Exception) e));
            view.showRetry();
        }

        @Override
        public void onNext(List<User> users) {
            UserListPresenter.this.showUsersCollectionInView(users);
        }
    }
}
