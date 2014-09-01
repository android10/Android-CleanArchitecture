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
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCase;
import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCaseImpl;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.UIThread;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.presenter.UserListPresenter;
import com.fernandocejas.android10.sample.presentation.view.UserListView;
import com.fernandocejas.android10.sample.presentation.view.adapter.UsersAdapter;
import java.util.Collection;

/**
 * Fragment that shows a list of Users.
 */
public class UserListFragment extends BaseFragment implements UserListView {

  /**
   * Interface for listening user list events.
   */
  public interface UserListListener {
    void onUserClicked(final UserModel userModel);
  }

  private UserListPresenter userListPresenter;

  private ListView lv_users;
  private RelativeLayout rl_progress;
  private RelativeLayout rl_retry;
  private Button bt_retry;

  private UsersAdapter usersAdapter;

  private UserListListener userListListener;

  public UserListFragment() { super(); }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof UserListListener) {
      this.userListListener = (UserListListener) activity;
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, true);

    this.lv_users = (ListView) fragmentView.findViewById(R.id.lv_users);
    this.lv_users.setOnItemClickListener(this.userOnItemClickListener);
    this.rl_progress = (RelativeLayout) fragmentView.findViewById(R.id.rl_progress);
    this.rl_retry = (RelativeLayout) fragmentView.findViewById(R.id.rl_retry);
    this.bt_retry = (Button) fragmentView.findViewById(R.id.bt_retry);
    this.bt_retry.setOnClickListener(this.retryOnClickListener);

    return fragmentView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.userListPresenter.initialize();
  }

  @Override public void onResume() {
    super.onResume();
    this.userListPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.userListPresenter.pause();
  }

  @Override protected void initializePresenter() {
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

    this.userListPresenter = new UserListPresenter(this, getUserListUseCase, userModelDataMapper);
  }

  @Override public void showLoading() {
    this.rl_progress.setVisibility(View.VISIBLE);
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override public void hideLoading() {
    this.rl_progress.setVisibility(View.GONE);
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override public void showRetry() {
    this.rl_retry.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    this.rl_retry.setVisibility(View.GONE);
  }

  @Override public void renderUserList(Collection<UserModel> userModelCollection) {
    if (userModelCollection != null) {
      if (this.usersAdapter == null) {
        this.usersAdapter = new UsersAdapter(getActivity(), userModelCollection);
      } else {
        this.usersAdapter.setUsersCollection(userModelCollection);
      }
      this.lv_users.setAdapter(usersAdapter);
    }
  }

  @Override public void viewUser(UserModel userModel) {
    if (this.userListListener != null) {
      this.userListListener.onUserClicked(userModel);
    }
  }

  @Override public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override public Context getContext() {
    return this.getActivity().getApplicationContext();
  }

  /**
   * Loads all users.
   */
  private void loadUserList() {
    if (this.userListPresenter != null) {
      this.userListPresenter.initialize();
    }
  }

  /**
   * Views a {@link UserModel} when is clicked.
   * Uses the presenter via composition to achieve this.
   *
   * @param userModel {@link UserModel} to show.
   */
  private void onUserClicked(UserModel userModel) {
    if (this.userListPresenter != null) {
      this.userListPresenter.onUserClicked(userModel);
    }
  }

  private final View.OnClickListener retryOnClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      UserListFragment.this.loadUserList();
    }
  };

  private final AdapterView.OnItemClickListener userOnItemClickListener =
      new AdapterView.OnItemClickListener() {
        @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          UserModel userModel = (UserModel) UserListFragment.this.usersAdapter.getItem(position);
          UserListFragment.this.onUserClicked(userModel);
        }
      };
}
