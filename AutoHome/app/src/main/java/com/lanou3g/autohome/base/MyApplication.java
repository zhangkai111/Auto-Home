package com.lanou3g.autohome.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by leisure on 16/5/17.
 */
public class MyApplication extends Application {
    private static Context mContext;
    //Application创建的原因是因为我们需要一个属于自己的大
    // "环境"(context)
    //保证自己的App拥有单独的一个context对象

    //第一个生命周期中我们队context赋值

    @Override
    public void onCreate() {
        super.onCreate();
        //this代表当前的环境
        mContext = this;

    }
    //对外提供一个方法 这个方法就是让别的类获取自己的context对象
    public static Context getContext() {
        return mContext;
    }
}
