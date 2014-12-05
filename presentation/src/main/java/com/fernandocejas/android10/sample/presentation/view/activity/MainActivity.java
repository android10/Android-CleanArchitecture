package com.fernandocejas.android10.sample.presentation.view.activity;

import android.os.Bundle;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.navigation.Navigator;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

  private Navigator navigator;

  @InjectView(R.id.btn_LoadData) Button btn_LoadData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.inject(this);
    this.initialize();
  }

  /**
   * Initializes activity's private members.
   */
  private void initialize() {
    this.navigator = new Navigator();
  }

  /**
   * Goes to the user list screen.
   */
  @OnClick(R.id.btn_LoadData)
  void navigateToUserList() {
    this.navigator.navigateToUserList(this);
  }
}
