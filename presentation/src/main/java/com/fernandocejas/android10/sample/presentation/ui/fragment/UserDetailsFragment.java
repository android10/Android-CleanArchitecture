/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.UserComponent;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.mvp.presenter.UserDetailsPresenter;
import com.fernandocejas.android10.sample.presentation.mvp.view.UserDetailsView;
import com.fernandocejas.android10.sample.presentation.mvp.view.impl.UserDetailsViewImpl;
import com.fernandocejas.android10.sample.presentation.ui.component.AutoLoadImageView;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that shows details of a certain user.
 */
public class UserDetailsFragment extends BaseFragment<UserDetailsPresenter, UserDetailsView> {

    private static final String PARAM_USER_ID = "param_user_id";

    @Inject
    UserDetailsPresenter userDetailsPresenter;

    @Bind(R.id.iv_cover)
    AutoLoadImageView iv_cover;
    @Bind(R.id.tv_fullname)
    TextView tv_fullname;
    @Bind(R.id.tv_email)
    TextView tv_email;
    @Bind(R.id.tv_followers)
    TextView tv_followers;
    @Bind(R.id.tv_description)
    TextView tv_description;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    public static UserDetailsFragment forUser(int userId) {
        final UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_USER_ID, userId);
        userDetailsFragment.setArguments(arguments);
        return userDetailsFragment;
    }

    public UserDetailsFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.getComponent(UserComponent.class).inject(this);
        super.onCreate(savedInstanceState);
        presenter.setUserId(currentUserId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user_details, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected UserDetailsPresenter initPresenter() {
        return userDetailsPresenter;
    }

    @Override
    protected UserDetailsView initView() {
        return new UserDetailsViewImpl(this) {
            @Override
            public void renderUser(UserModel user) {
                if (user != null) {
                    UserDetailsFragment.this.iv_cover.setImageUrl(user.getCoverUrl());
                    UserDetailsFragment.this.tv_fullname.setText(user.getFullName());
                    UserDetailsFragment.this.tv_email.setText(user.getEmail());
                    UserDetailsFragment.this.tv_followers.setText(String.valueOf(user.getFollowers()));
                    UserDetailsFragment.this.tv_description.setText(user.getDescription());
                }
            }

            @Override
            public void showLoading() {
                UserDetailsFragment.this.rl_progress.setVisibility(View.VISIBLE);
                UserDetailsFragment.this.getActivity().setProgressBarIndeterminateVisibility(true);
            }

            @Override
            public void hideLoading() {
                UserDetailsFragment.this.rl_progress.setVisibility(View.GONE);
                UserDetailsFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
            }

            @Override
            public void showRetry() {
                UserDetailsFragment.this.rl_retry.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideRetry() {
                UserDetailsFragment.this.rl_retry.setVisibility(View.GONE);
            }
        };
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        presenter.refreshData();
    }

    /**
     * Get current user id from fragments arguments.
     */
    private int currentUserId() {
        final Bundle arguments = getArguments();
        Preconditions.checkNotNull(arguments, "Fragment arguments cannot be null");
        return arguments.getInt(PARAM_USER_ID);
    }
}
