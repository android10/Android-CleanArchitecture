/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ActivityComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerInitComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.InitComponent;
import com.fernandocejas.android10.sample.presentation.presenter.InitPresenter;
import com.fernandocejas.android10.sample.presentation.view.InitView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Wolfgang Karl on 25.09.15.
 */
public class InitFragment extends BaseFragment implements InitView {
    /**
     * Interface for listening to load button events.
     */
    public interface LoadbuttonListener {
        void onLoadClicked();
    }

    @Inject InitPresenter presenter;

    private InitComponent initComponent;

    private LoadbuttonListener loadbuttonListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof LoadbuttonListener) {
            this.loadbuttonListener = (LoadbuttonListener) activity;
        }
    }

    /**
     * Goes to the user list screen.
     */
    @OnClick(R.id.btn_LoadData)
    void navigateToUserList() {
        presenter.onLoadUserListClicked();
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
    }

    private void initialize() {
        initializeInjector();
        initComponent.inject(this);
        presenter.setView(this);
    }

    private void initializeInjector() {
        initComponent = DaggerInitComponent.builder()
                .activityComponent(getComponent(ActivityComponent.class))
                .build();
    }

    public static Fragment newInstance() {
        return new InitFragment();
    }

    @Override
    public void loadUserList() {
        if (this.loadbuttonListener != null) {
            this.loadbuttonListener.onLoadClicked();
        }
    }
}
