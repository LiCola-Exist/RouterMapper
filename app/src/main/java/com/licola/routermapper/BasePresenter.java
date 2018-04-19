package com.licola.routermapper;

import com.licola.routermapper.router.RouteContract;

/**
 * Created by LiCola on 2018/4/19.
 */
public class BasePresenter {

  BaseView view;

  public BasePresenter(BaseView view) {
    this.view = view;
  }

  /**
   * 由Presenter控制界面跳转
   * 界面跳转的流程：View点击发起请求--->Presenter接收请求控制界面跳转
   * 这样就可以实现P层环境隔离 Android代码依赖（没有intent出现）
   * 从而可以实现单元测试
   */
  public void navigationToSecond() {
    view.onNavigation(RouteContract.SecondActivity);
  }
}
