package com.licola.routermapper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import routermapper.Route;

@Route
public class SecondActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
  }
}
