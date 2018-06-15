package com.licola.routermapper;

import com.licola.routermapper.router.RouteContract;

/**
 * Created by LiCola on 2018/4/19.
 */
public interface BaseView {

  /**
   * Activity跳转方法
   *
   * @param target 路由值
   * @return true：成功处理跳转 false：无法处理跳转
   */
  boolean onNavigation(@RouteContract.Target int target);
}
