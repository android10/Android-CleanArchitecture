/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fernandocejas.android10.sample.data.cache.FileManager;
import com.fernandocejas.android10.sample.data.cache.UserCache;
import com.fernandocejas.android10.sample.data.cache.UserCacheImpl;
import com.fernandocejas.android10.sample.data.cache.serializer.JsonSerializer;
import com.fernandocejas.android10.sample.data.entity.mapper.UserEntityDataMapper;
import com.fernandocejas.android10.sample.data.executor.JobExecutor;
import com.fernandocejas.android10.sample.data.repository.UserDataRepository;
import com.fernandocejas.android10.sample.data.repository.datasource.UserDataStoreFactory;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCase;
import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCaseImpl;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.UIThread;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.viewmodel.UserListView;
import com.fernandocejas.android10.sample.presentation.viewmodel.UserListViewModel;

import org.robobinding.ViewBinder;

import java.util.Collection;

/**
 * Fragment that shows a list of Users.
 */
public class UserListFragment extends BaseFragment implements UserListView {
    private UserListViewModel viewModel;
    /**
     * Interface for listening user list events.
     */
    public interface UserListListener {
        void onUserClicked(final UserModel userModel);
    }

    private RelativeLayout rl_progress;
    private UserListListener userListListener;

    public UserListFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof UserListListener) {
            this.userListListener = (UserListListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewBinder viewBinder = createViewBinder(true);
        View fragmentView = viewBinder.inflateAndBind(R.layout.fragment_user_list, viewModel);
        this.rl_progress = (RelativeLayout) fragmentView.findViewById(R.id.rl_progress);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.viewModel.initialize();
    }

    @Override
    protected void initializeViewModel() {
        // All these dependency initialization could have been avoided using a
        // dependency injection framework. But in this case are used this way for
        // LEARNING EXAMPLE PURPOSE.
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        JsonSerializer userCacheSerializer = new JsonSerializer();
        UserCache userCache = UserCacheImpl.getInstance(getActivity(), userCacheSerializer,
                FileManager.getInstance(), threadExecutor);
        UserDataStoreFactory userDataStoreFactory =
                new UserDataStoreFactory(this.getContext(), userCache);
        UserEntityDataMapper userEntityDataMapper = new UserEntityDataMapper();
        UserRepository userRepository = UserDataRepository.getInstance(userDataStoreFactory,
                userEntityDataMapper);

        GetUserListUseCase getUserListUseCase = new GetUserListUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread);
        UserModelDataMapper userModelDataMapper = new UserModelDataMapper();

        this.viewModel = new UserListViewModel(this, getUserListUseCase, userModelDataMapper);
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void viewUser(UserModel userModel) {
        if (this.userListListener != null) {
            this.userListListener.onUserClicked(userModel);
        }
    }

    @Override
    public void showError(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(getContext(),
                errorBundle.getException());

        this.showToastMessage(errorMessage);
    }

    private Context getContext() {
        return this.getActivity().getApplicationContext();
    }
}
