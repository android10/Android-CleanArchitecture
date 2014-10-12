package com.fernandocejas.android10.sample.presentation.viewmodel;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetailsUseCase;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.UserModel;

import org.robobinding.annotation.DependsOnStateOf;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 * Created by Cheng Wei on 2014/9/29.
 */
@PresentationModel
public class UserDetailsViewModel implements HasPresentationModelChangeSupport {
    /**
     * id used to retrieve user details
     */
    private int userId;

    private final UserDetailsView viewDetailsView;
    private final GetUserDetailsUseCase getUserDetailsUseCase;
    private final UserModelDataMapper userModelDataMapper;

    private boolean retryVisible;
    private UserModel userModel;

    private final PresentationModelChangeSupport changeSupport;

    public UserDetailsViewModel(UserDetailsView userDetailsView,
                                GetUserDetailsUseCase getUserDetailsUseCase, UserModelDataMapper userModelDataMapper) {
        this.viewDetailsView = userDetailsView;
        this.getUserDetailsUseCase = getUserDetailsUseCase;
        this.userModelDataMapper = userModelDataMapper;
        changeSupport = new PresentationModelChangeSupport(this);
    }

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

    private void hideViewRetry() {
        setRetryVisible(false);
    }

    private void setRetryVisible(boolean retryVisible) {
        this.retryVisible = retryVisible;
        changeSupport.firePropertyChange("retryVisible");
    }

    public boolean isRetryVisible() {
        return retryVisible;
    }

    private void showViewLoading() {
        this.viewDetailsView.showLoading();
    }

    private void getUserDetails() {
        this.getUserDetailsUseCase.execute(this.userId, this.userDetailsCallback);
    }

    private final GetUserDetailsUseCase.Callback userDetailsCallback = new GetUserDetailsUseCase.Callback() {
        @Override
        public void onUserDataLoaded(User user) {
            UserDetailsViewModel.this.showUserDetailsInView(user);
            UserDetailsViewModel.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            UserDetailsViewModel.this.hideViewLoading();
            UserDetailsViewModel.this.showErrorMessage(errorBundle);
            UserDetailsViewModel.this.showViewRetry();
        }
    };

    private void showUserDetailsInView(User user) {
        userModel = this.userModelDataMapper.transform(user);
        changeSupport.firePropertyChange("userModel");
    }

    public  UserModel getUserModel() {
        return userModel;
    }

    @DependsOnStateOf("userModel")
    public String getCoverUrl() {
        return userModel.getCoverUrl();
    }

    @DependsOnStateOf("userModel")
    public String getFullName() {
        return userModel.getFullName();
    }

    @DependsOnStateOf("userModel")
    public String getEmail() {
        return userModel.getEmail();
    }

    @DependsOnStateOf("userModel")
    public String getFollowers() {
        return String.valueOf(userModel.getFollowers());
    }

    @DependsOnStateOf("userModel")
    public String getDescription() {
        return userModel.getDescription();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        this.viewDetailsView.showError(errorBundle);
    }

    private void hideViewLoading() {
        this.viewDetailsView.hideLoading();
    }

    private void showViewRetry() {
       setRetryVisible(true);
    }

    public void onRetryClicked() {
        loadUserDetails();
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return changeSupport;
    }
}
