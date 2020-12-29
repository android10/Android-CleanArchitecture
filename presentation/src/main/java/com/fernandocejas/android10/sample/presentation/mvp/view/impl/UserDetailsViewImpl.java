package com.fernandocejas.android10.sample.presentation.mvp.view.impl;

import android.app.Fragment;

import com.fernandocejas.android10.sample.presentation.mvp.view.UserDetailsView;

public abstract class UserDetailsViewImpl extends ViewImpl implements UserDetailsView {

    public UserDetailsViewImpl(Fragment fragment) {
        super(fragment);
    }
}
