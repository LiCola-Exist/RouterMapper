package com.licola.model.module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import routermapper.Route;

@Route
public class ModuleActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }
}
