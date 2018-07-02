package com.licola.routermapper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.licola.model.module.ModuleActivity;
import routermapper.Route;


@Route
public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  public void onClickNavigation(View view) {
//    Class targetActivity = RouteMapper.mapperActivity(RouteContract.SecondActivity);
//    if (targetActivity == null) {
//      //不存在界面 只有UnknownAty才能通过 编译时检查
//      return;
//    }
//
//    Intent intent = new Intent(this, targetActivity);
//    if (isEmptyResolveIntent(this, intent)) {
//      //无法解析的intent
//      return;
//    }
//
//    startActivity(intent);
  }

  public void onClickNavigationModule(View view) {
    Intent intent = new Intent(getApplicationContext(), ModuleActivity.class);
    startActivity(intent);
  }

  public static boolean isEmptyResolveIntent(Context context, Intent intent) {
    if (intent == null || context == null) {
      return true;
    }
    //只有当检查出能够接受intent的对象不为空 返回true
    return intent.resolveActivity(context.getPackageManager()) == null;
  }
}
