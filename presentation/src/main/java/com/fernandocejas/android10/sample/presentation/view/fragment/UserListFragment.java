/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.UserComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.UserListViewModule;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;
import com.fernandocejas.android10.sample.presentation.presenter.UserListPresenter;
import com.fernandocejas.android10.sample.presentation.view.UserListView;
import com.fernandocejas.android10.sample.presentation.view.adapter.UsersAdapter;
import com.fernandocejas.android10.sample.presentation.view.adapter.UsersLayoutManager;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;

/**
 * Fragment that shows a list of Users.
 */
public class UserListFragment
    extends BaseInjectableFragment<UserComponent>
    implements UserListView {

  /**
   * Interface for listening user list events.
   */
  public interface UserListListener {
    void onUserClicked(final UserModel userModel);
  }

  @Inject UserListPresenter userListPresenter;

  @Bind(R.id.rv_users) RecyclerView rv_users;
  @Bind(R.id.rl_progress) RelativeLayout rl_progress;
  @Bind(R.id.rl_retry) RelativeLayout rl_retry;
  @Bind(R.id.bt_retry) Button bt_retry;

  private UsersAdapter usersAdapter;
  private UsersLayoutManager usersLayoutManager;

  private UserListListener userListListener;

  public UserListFragment() {
    super();
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof UserListListener) {
      this.userListListener = (UserListListener) activity;
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, true);
    ButterKnife.bind(this, fragmentView);
    setupUI();

    return fragmentView;
  }

  @Override protected Class<UserComponent> getActivityComponentClass() {
    return UserComponent.class;
  }

  @Override protected void injectFragmentDependencies(Class<UserComponent> activityComponentClass) {
    this.getComponent(activityComponentClass)
        .plus(new UserListViewModule(this))
        .inject(this);
  }

  @Override protected Presenter getPresenter() {
    return userListPresenter;
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
      this.usersAdapter.setUsersCollection(userModelCollection);
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

  @OnClick(R.id.bt_retry) void onButtonRetryClick() {
    UserListFragment.this.loadUserList();
  }

  //private

  private void loadUserList() {
    this.userListPresenter.initialize();
  }

  private void setupUI() {
    this.usersLayoutManager = new UsersLayoutManager(getActivity());
    this.rv_users.setLayoutManager(usersLayoutManager);

    this.usersAdapter = new UsersAdapter(getActivity(), new ArrayList<UserModel>());
    this.usersAdapter.setOnItemClickListener(onItemClickListener);
    this.rv_users.setAdapter(usersAdapter);
  }

  private UsersAdapter.OnItemClickListener onItemClickListener =
      new UsersAdapter.OnItemClickListener() {
        @Override public void onUserItemClicked(UserModel userModel) {
          if (UserListFragment.this.userListPresenter != null && userModel != null) {
            UserListFragment.this.userListPresenter.onUserClicked(userModel);
          }
        }
      };
}
