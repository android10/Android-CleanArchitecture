/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.mvp.view.impl;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.widget.Toast;

import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.mvp.view.View;
import com.fernandocejas.android10.sample.presentation.ui.component.ProgressDialogHelper;

public abstract class ViewImpl implements View {

    private Activity activity;

    private Fragment fragment;

    private android.view.View view;

    private Service service;

    private ProgressDialogHelper progressDialogHelper;

    public ViewImpl(Activity activity) {
        this.activity = activity;
        init();
    }

    public ViewImpl(Fragment fragment) {
        this.fragment = fragment;
        init();
    }

    public ViewImpl(android.view.View view) {
        this.view = view;
        init();
    }

    public ViewImpl(Service service) {
        this.service = service;
        init();
    }

    @Override
    public void showLoading() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        progressDialogHelper.showProgress(context);
    }

    @Override
    public void hideLoading() {
        progressDialogHelper.hideProgress();
    }

    @Override
    public void showRetry() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        progressDialogHelper.showProgress(context);
    }

    @Override
    public void hideRetry() {
        progressDialogHelper.hideProgress();
    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    @Override
    public void showError(ErrorBundle errorBundle) {
        Context context = getContext();
        if (context == null) {
            return;
        }
        String errorMessage = ErrorMessageFactory.create(getContext(),
                errorBundle.getException());
        showToastMessage(errorMessage);
    }

    private void init() {
        progressDialogHelper = new ProgressDialogHelper();
    }

    private Context getContext() {
        if (activity != null) {
            return activity;
        } else if (fragment != null) {
            return fragment.getActivity();
        } else if (view != null) {
            return view.getContext();
        } else if (service != null) {
            return service;
        }
        return null;
    }

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    //TODO replace with SnackBar?
    private void showToastMessage(String message) {
        Context context = getContext();
        if (context == null) {
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
