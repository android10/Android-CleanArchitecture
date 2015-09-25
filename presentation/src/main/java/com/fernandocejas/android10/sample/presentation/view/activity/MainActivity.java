package com.fernandocejas.android10.sample.presentation.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import butterknife.Bind;

import com.fernandocejas.android10.sample.presentation.AndroidApplication;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ActivityComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerActivityComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.navigation.Navigator;
import com.fernandocejas.android10.sample.presentation.view.fragment.InitFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.UserListFragment;

import javax.inject.Inject;


/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends Activity implements HasComponent<ApplicationComponent>,
        UserListFragment.UserListListener, InitFragment.LoadbuttonListener {
  private ActivityComponent activityComponent;
  private ActivityModule activityModule;

  @Inject Navigator navigator;

  @Bind(R.id.btn_LoadData) Button btn_LoadData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_main);
    this.initializeInjector();
    activityComponent.inject(this);
    if (savedInstanceState == null) {
      navigator.navigateToInitFragment(this);
    }
  }

  private void initializeInjector() {
    this.activityModule = getActivityModule();
    this.activityComponent = DaggerActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(activityModule)
        .build();
  }

  private ActivityComponent getActivityComponent() {
    return activityComponent;
  }

  @Override
  public ActivityComponent getComponent() {
    return getActivityComponent();
  }

  public ActivityModule getActivityModule() {
    return activityModule == null ? new ActivityModule(this) : activityModule;
  }

  @Override public void onUserClicked(UserModel userModel) {
    this.navigator.navigateToUserDetails(this, userModel.getUserId());
  }

  @Override
  public void onLoadClicked() {
    navigator.navigateToUserList(this);
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent}
   */
  private ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication)getApplication()).getApplicationComponent();
  }
}
