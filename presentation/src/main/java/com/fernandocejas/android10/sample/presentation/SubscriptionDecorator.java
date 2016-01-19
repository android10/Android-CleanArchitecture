package com.fernandocejas.android10.sample.presentation;

import android.support.annotation.NonNull;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by jorgis on 1/19/16.
 */
public class SubscriptionDecorator<T extends Object> extends Subscriber<T> {
    private Subscriber<T> subscriber;

    public SubscriptionDecorator(Subscriber<T> subscriber) {
        this.subscriber = subscriber;
        this.add(new DisposingSubscription(this));
    }

    @Override public void onCompleted() {
        if (subscriber != null) {
            subscriber.onCompleted();
        }
    }

    @Override public void onError(Throwable e) {
        if (subscriber != null) {
            subscriber.onError(e);
        }
    }

    @Override public void onNext(T o) {
        if (subscriber != null) {
            subscriber.onNext(o);
        }
    }

    public void disposeDependencies() {
        subscriber = null;
    }

    private static class DisposingSubscription implements Subscription {

        private final SubscriptionDecorator callback;
        private boolean unsubscribed;

        private DisposingSubscription(@NonNull SubscriptionDecorator callback) {
            this.callback = callback;
        }

        @Override public void unsubscribe() {
            callback.disposeDependencies();
            unsubscribed = true;
        }

        @Override public boolean isUnsubscribed() {
            return unsubscribed;
        }
    }


}
