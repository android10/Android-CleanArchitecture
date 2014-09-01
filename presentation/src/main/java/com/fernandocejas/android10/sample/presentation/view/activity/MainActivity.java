package com.fernandocejas.android10.sample.presentation.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.navigation.Navigator;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

  private Navigator navigator;

  private Button btn_LoadData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this.mapGUI();
    this.initialize();
  }

  /**
   * Maps the graphical user interface controls.
   */
  private void mapGUI() {
    btn_LoadData = (Button) findViewById(R.id.btn_LoadData);
    btn_LoadData.setOnClickListener(loadDataOnClickListener);
  }

  /**
   * Initializes activity's private members.
   */
  private void initialize() {
    //This initialization should be avoided by using a dependency injection framework.
    //But this is an example and for testing purpose.
    this.navigator = new Navigator();
  }

  /**
   * Goes to the user list screen.
   */
  private void navigateToUserList() {
    this.navigator.navigateToUserList(this);
  }

  private final View.OnClickListener loadDataOnClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      navigateToUserList();
    }
  };
}
