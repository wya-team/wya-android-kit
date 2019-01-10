package com.wya.example.module.base;

import android.app.Application;
import android.content.Context;

/**
 * @date: 2019/1/4 11:16
 * @author: Chunjiang Mao
 * @classname: WYAApplication
 * @describe: WYAApplication
 */

public class WYAApplication extends Application {
    
    public static WYAApplication mApplication;
    
    public static WYAApplication getApplication() {
        return mApplication;
    }
    
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mApplication = this;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        
    }
    
}