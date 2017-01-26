package com.fernandocejas.android10.sample.presentation.mvp.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.sample.presentation.mvp.view.View;

public abstract class BasePresenter<VIEW extends View> implements Presenter<VIEW> {

    protected VIEW view;

    @Override
    public void attachView(@NonNull VIEW view) {
        this.view = view;
        onViewAttached();
    }

    @Override
    public void detachView() {
        onViewDetached();
        this.view = null;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    public abstract void refreshData();

    protected void onViewAttached() {
    }

    protected void onViewDetached() {
    }
}
