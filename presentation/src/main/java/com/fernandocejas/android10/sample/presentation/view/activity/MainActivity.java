package com.fernandocejas.android10.sample.presentation.view.activity;

import android.os.Bundle;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.presentation.ActivityComponent;
import com.fernandocejas.android10.sample.presentation.ActivityModule;
import com.fernandocejas.android10.sample.presentation.Dagger_ActivityComponent;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.navigation.Navigator;
import javax.inject.Inject;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

  ActivityComponent activityComponent;

  @Inject Navigator navigator;

  @InjectView(R.id.btn_LoadData) Button btn_LoadData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    this.activityComponent = Dagger_ActivityComponent.builder()
        .activityModule(new ActivityModule())
        .build();
    this.activityComponent.inject(this);


    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
  }

  /**
   * Goes to the user list screen.
   */
  @OnClick(R.id.btn_LoadData)
  void navigateToUserList() {
    this.navigator.navigateToUserList(this);
  }
}
