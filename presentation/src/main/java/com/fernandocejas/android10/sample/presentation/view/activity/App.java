package com.fernandocejas.android10.sample.presentation.view.activity;

import android.app.Application;

import com.fernandocejas.android10.sample.presentation.view.component.AutoLoadImageView;

import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;
import org.robobinding.dynamicbinding.DynamicViewBinding;

/**
 * Created by Cheng Wei on 2014/9/29.
 */
public class App extends Application{
    private BinderFactory reusableBinderFactory;
    @Override
    public void onCreate() {
        super.onCreate();
        reusableBinderFactory = new BinderFactoryBuilder()
                .add(new DynamicViewBinding().forView(AutoLoadImageView.class).oneWayProperties("imageUrl"))
                .build();
    }

    public BinderFactory getReusableBinderFactory() {
        return reusableBinderFactory;
    }
}
