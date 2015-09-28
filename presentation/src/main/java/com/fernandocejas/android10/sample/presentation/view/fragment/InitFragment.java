package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerInitComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.InitComponent;
import com.fernandocejas.android10.sample.presentation.navigation.Navigator;
import com.fernandocejas.android10.sample.presentation.presenter.InitPresenter;
import com.fernandocejas.android10.sample.presentation.view.InitView;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wolfgang on 25.09.15.
 */
public class InitFragment extends BaseFragment implements InitView {
    @Inject InitPresenter presenter;

    private InitComponent initComponent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
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
                .applicationComponent(getComponent(ApplicationComponent.class))
                .activityModule(((MainActivity) getActivity()).getActivityModule())
                .build();
    }

    public static Fragment newInstance() {
        return new InitFragment();
    }

    @Override
    public void loadUserList() {
        ((MainActivity)getActivity()).navigateToUserList();
    }
}
