/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

/**
 * Default subscriber base class to be used whenever you want default error handling.
 */
public class DefaultSubscriber<T> extends rx.Subscriber<T> {
  @Override public void onCompleted() {
    // no-op by default.
  }

  @Override public void onError(Throwable e) {
    // no-op by default.
  }

  @Override public void onNext(T t) {
    // no-op by default.
  }
}
