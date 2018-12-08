package com.wya.example.base;

import android.app.Application;

/**
 * 创建日期：2018/11/28 10:28
 * 作者： Mao Chunjiang
 * 文件名称： BaseApplication
 * 类说明：
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandlerUtils crashHandler = CrashHandlerUtils.getInstance();
//        crashHandler.init(this);
    }
}
