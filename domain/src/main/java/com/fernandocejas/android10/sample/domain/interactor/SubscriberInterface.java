package com.fernandocejas.android10.sample.domain.interactor;

/**
 * SubscriberInterface class for send the callback of subscriber to presenter.
 */
public interface SubscriberInterface<T> {
    public static final String TAG = "SubscriberInterface";

    void onCompleted();

    void onError(Throwable e);

    void onNext(T t);
}
