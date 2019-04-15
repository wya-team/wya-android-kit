package com.wya.hardware.upload.net;

import android.app.Application;

/**
 * @author :
 */
public class BaseApp extends Application {
    
    private static BaseApp APPLICATION;
    
    @Override
    public void onCreate() {
        super.onCreate();
        APPLICATION = this;
    }
    
    public static BaseApp getApp() {
        return APPLICATION;
    }
    
}
