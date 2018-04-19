package com.licola.routermapper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.licola.routermapper.router.RouteContract;
import com.licola.routermapper.router.RouteContract.Name;
import com.licola.routermapper.router.RouteMapper;

/**
 * Created by LiCola on 2018/4/19.
 */
public abstract class BaseViewActivity extends AppCompatActivity {


  /**
   * 检查是否不能解析Intent跳转
   *
   * @return true：表示不能处理Intent跳转 false：非空的解析 即能够处理跳转
   */
  public static boolean isEmptyResolveIntent(Context context, Intent intent) {
    if (intent == null || context == null) {
      return true;
    }
    //只有当检查出能够接受intent的对象不为空 返回true
    return intent.resolveActivity(context.getPackageManager()) == null;
  }


  /**
   * 实际的Activity跳转方法
   * 暴露给Presenter使用，被动调用
   * @param target
   * @return
   */
  public boolean onNavigation(@RouteContract.Name int target) {
    Class targetActivity = RouteMapper.mapperActivity(target);
    if (targetActivity == null) {
      //不存在界面 只有UnknownAty才能通过 编译时检查
      return false;
    }

    Intent intent = new Intent(this, targetActivity);
    if (isEmptyResolveIntent(this, intent)) {
      //无法解析的intent
      return false;
    }

    startActivity(intent);
    return true;
  }
}
