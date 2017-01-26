/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.UserComponent;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.mvp.presenter.UserListPresenter;
import com.fernandocejas.android10.sample.presentation.mvp.view.UserListView;
import com.fernandocejas.android10.sample.presentation.mvp.view.impl.UserListViewImpl;
import com.fernandocejas.android10.sample.presentation.ui.adapter.UsersAdapter;
import com.fernandocejas.android10.sample.presentation.ui.adapter.UsersLayoutManager;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that shows a list of Users.
 */
public class UserListFragment extends BaseFragment<UserListPresenter, UserListView> {

    /**
     * Interface for listening user list events.
     */
    public interface UserListListener {
        void onUserClicked(final UserModel userModel);
    }

    @Inject
    UserListPresenter userListPresenter;
    @Inject
    UsersAdapter usersAdapter;

    @Bind(R.id.rv_users)
    RecyclerView rv_users;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    private UserListListener userListListener;

    public UserListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UserListListener) {
            this.userListListener = (UserListListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.getComponent(UserComponent.class).inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.userListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.userListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv_users.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    protected UserListPresenter initPresenter() {
        return userListPresenter;
    }

    @Override
    protected UserListView initView() {
        return new UserListViewImpl(this) {

            @Override
            public void renderUserList(Collection<UserModel> userModelCollection) {
                if (userModelCollection != null) {
                    UserListFragment.this.usersAdapter.setUsersCollection(userModelCollection);
                }
            }

            @Override
            public void viewUser(UserModel userModel) {
                if (UserListFragment.this.userListListener != null) {
                    UserListFragment.this.userListListener.onUserClicked(userModel);
                }
            }

            @Override
            public void showLoading() {
                UserListFragment.this.rl_progress.setVisibility(View.VISIBLE);
                UserListFragment.this.getActivity().setProgressBarIndeterminateVisibility(true);
            }

            @Override
            public void hideLoading() {
                UserListFragment.this.rl_progress.setVisibility(View.GONE);
                UserListFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
            }

            @Override
            public void showRetry() {
                UserListFragment.this.rl_retry.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideRetry() {
                UserListFragment.this.rl_retry.setVisibility(View.GONE);
            }
        };
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.userListListener = null;
    }

    private void setupRecyclerView() {
        this.usersAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_users.setLayoutManager(new UsersLayoutManager(getActivity()));
        this.rv_users.setAdapter(usersAdapter);
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        presenter.refreshData();
    }

    private UsersAdapter.OnItemClickListener onItemClickListener =
            new UsersAdapter.OnItemClickListener() {
                @Override
                public void onUserItemClicked(UserModel userModel) {
                    if (UserListFragment.this.userListPresenter != null && userModel != null) {
                        UserListFragment.this.userListPresenter.onUserClicked(userModel);
                    }
                }
            };
}
