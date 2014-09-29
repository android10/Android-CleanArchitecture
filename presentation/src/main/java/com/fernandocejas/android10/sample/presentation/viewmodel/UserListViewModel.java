package com.fernandocejas.android10.sample.presentation.viewmodel;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCase;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.UserModel;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.presentationmodel.AbstractPresentationModel;
import org.robobinding.widget.adapterview.ItemClickEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Cheng Wei on 2014/9/29.
 */
public class UserListViewModel extends AbstractPresentationModel {
    private final UserListView userListView;
    private final GetUserListUseCase getUserListUseCase;
    private final UserModelDataMapper userModelDataMapper;

    private boolean retryVisible;
    private List<UserModel> userModels;

    public UserListViewModel(UserListView userListView, GetUserListUseCase getUserListUserCase,
                             UserModelDataMapper userModelDataMapper) {
        this.userListView = userListView;
        this.getUserListUseCase = getUserListUserCase;
        this.userModelDataMapper = userModelDataMapper;

        userModels = Collections.emptyList();
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize() {
        loadUserList();
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        hideViewRetry();
        showViewLoading();
        getUserList();
    }

    private void hideViewRetry() {
        setRetryVisible(false);
    }

    private void setRetryVisible(boolean retryVisible) {
        this.retryVisible = retryVisible;
        firePropertyChange("retryVisible");
    }

    public boolean isRetryVisible() {
        return retryVisible;
    }

    private void showViewLoading() {
        this.userListView.showLoading();
    }

    private void getUserList() {
        this.getUserListUseCase.execute(userListCallback);
    }

    private final GetUserListUseCase.Callback userListCallback = new GetUserListUseCase.Callback() {
        @Override
        public void onUserListLoaded(Collection<User> usersCollection) {
            UserListViewModel.this.showUsersCollectionInView(usersCollection);
            UserListViewModel.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            UserListViewModel.this.hideViewLoading();
            UserListViewModel.this.showErrorMessage(errorBundle);
            UserListViewModel.this.showViewRetry();
        }
    };

    private void showUsersCollectionInView(Collection<User> usersCollection) {
        final Collection<UserModel> userModelsCollection =
                this.userModelDataMapper.transform(usersCollection);
        renderUserList(userModelsCollection);
    }

    private void renderUserList(Collection<UserModel> newUserModels) {
        userModels = new ArrayList<UserModel>(newUserModels);
        firePropertyChange("userModels");
    }

    @ItemPresentationModel(value = UserItemViewModel.class)
    public List<UserModel> getUserModels() {
        return userModels;
    }

    private void hideViewLoading() {
        this.userListView.hideLoading();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        this.userListView.showError(errorBundle);
    }

    private void showViewRetry() {
        setRetryVisible(true);
    }

    public void onUserClicked(ItemClickEvent event) {
        UserModel userModel = userModels.get(event.getPosition());
        this.userListView.viewUser(userModel);
    }

    public void onRetryClicked() {
        loadUserList();
    }
}
