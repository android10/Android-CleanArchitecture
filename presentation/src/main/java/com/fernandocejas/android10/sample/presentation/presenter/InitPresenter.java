package com.fernandocejas.android10.sample.presentation.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.sample.presentation.view.InitView;

import javax.inject.Inject;

/**
 * Created by wolfgang on 28.09.15.
 */
public class InitPresenter implements Presenter {
    private InitView initView;

    @Inject public InitPresenter() {
    }

    public void onLoadUserListClicked() {
        this.initView.loadUserList();
    }

    public void setView(@NonNull InitView view) {
        this.initView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
