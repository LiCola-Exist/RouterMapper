package com.licola.routermapper;

import android.os.Bundle;
import android.view.View;
import routermapper.Route;


@Route
public class MainActivity extends BaseViewActivity implements BaseView {

  BasePresenter basePresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    basePresenter = new BasePresenter(this);
  }

  public void onClickNavigation(View view) {
    basePresenter.navigationToSecond();
  }
}
