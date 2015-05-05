package com.codecomputerlove.androidbase.presentation.view.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.codecomputerlove.androidbase.presentation.AndroidApplication;
import com.codecomputerlove.androidbase.presentation.BuildConfig;
import com.codecomputerlove.androidbase.presentation.internal.di.components.ApplicationComponent;
import com.codecomputerlove.androidbase.presentation.internal.di.modules.ActivityModule;
import com.codecomputerlove.androidbase.presentation.navigation.Navigator;

import javax.inject.Inject;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends Activity {

  @Inject Navigator navigator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);

    if (BuildConfig.DEBUG) {  //this will wake up and unlock the phone when in debug mode
      KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
      KeyguardManager.KeyguardLock keyguardLock = km.newKeyguardLock("TAG");
      keyguardLock.disableKeyguard();
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }
  }

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  protected void addFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link com.codecomputerlove.androidbase.presentation.internal.di.components.ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication)getApplication()).getApplicationComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   * @return {@link com.codecomputerlove.androidbase.presentation.internal.di.modules.ActivityModule}
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }
}
