package com.fernandocejas.android10.sample.presentation.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.fernandocejas.android10.sample.presentation.R;

public class MainActivity extends Activity {

  private Button btn_LoadData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mapGUI();
  }

  /**
   * Maps the graphical user interface controls.
   */
  private void mapGUI() {
    btn_LoadData = (Button)findViewById(R.id.btn_LoadData);
    btn_LoadData.setOnClickListener(loadDataOnClickListener);
  }

  private final View.OnClickListener loadDataOnClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {

    }
  };
}
