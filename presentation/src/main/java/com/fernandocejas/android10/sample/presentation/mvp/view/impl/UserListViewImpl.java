package com.fernandocejas.android10.sample.presentation.mvp.view.impl;

import android.app.Fragment;

import com.fernandocejas.android10.sample.presentation.mvp.view.UserListView;

public abstract class UserListViewImpl extends ViewImpl implements UserListView {

    public UserListViewImpl(Fragment fragment) {
        super(fragment);
    }
}
