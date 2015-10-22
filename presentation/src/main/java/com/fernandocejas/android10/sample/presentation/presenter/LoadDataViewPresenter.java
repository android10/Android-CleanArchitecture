package com.fernandocejas.android10.sample.presentation.presenter;

import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

/**
 * @author goraczka
 */
public class LoadDataViewPresenter<T extends LoadDataView> implements Presenter<T> {

  protected T view;

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void initialize(T view) {
    this.view = view;
  }
}
