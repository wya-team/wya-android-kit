package com.wya.example.module.base;

import android.app.Application;
import android.content.Context;

public class WYAApplication extends Application {
    
    public static WYAApplication mApplication;
    
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mApplication = this;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        
    }
    
    public static WYAApplication getApplication() {
        return mApplication;
    }
    
}