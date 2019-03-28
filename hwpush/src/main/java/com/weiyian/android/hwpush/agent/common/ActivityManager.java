package com.weiyian.android.hwpush.agent.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity 管理类
 * 此类注册了activity的生命周期监听，用来获取最新的activity给后续逻辑处理使用
 *
 * @author :
 */
public final class ActivityManager implements Application.ActivityLifecycleCallbacks {
    
    /**
     * 单实例
     */
    public static final ActivityManager INST = new ActivityManager();
    
    /**
     * 应用程序
     */
    private Application application;
    
    /**
     * 最新的activity，如果没有则为null
     */
    private Activity lastActivity;
    
    /**
     * activity onResume 事件监听
     */
    private List<IActivityResumeCallback> resumeCallbacks = new ArrayList<IActivityResumeCallback>();
    /**
     * activity onPause 事件监听
     */
    private List<IActivityPauseCallback> pauseCallbacks = new ArrayList<IActivityPauseCallback>();
    
    /**
     * 私有构造方法
     * 放在外面直接创建实例
     */
    private ActivityManager() {
    }
    
    /**
     * 初始化方法
     *
     * @param app 应用程序
     */
    public void init(Application app, Activity initActivity) {
        if (application != null) {
            application.unregisterActivityLifecycleCallbacks(this);
        }
        
        application = app;
        lastActivity = initActivity;
        app.registerActivityLifecycleCallbacks(this);
    }
    
    /**
     * 释放资源，一般不需要调用
     */
    public void release() {
        if (application != null) {
            application.unregisterActivityLifecycleCallbacks(this);
        }
        
        lastActivity = null;
        clearActivitResumeCallbacks();
        application = null;
    }
    
    /**
     * 注册activity onResume事件
     *
     * @param callback activity onResume事件回调
     */
    public void registerActivityResumeEvent(IActivityResumeCallback callback) {
        resumeCallbacks.add(callback);
    }
    
    /**
     * 反注册activity onResume事件回调
     *
     * @param callback 已经注册的 activity onResume事件回调
     */
    public void unRegisterActivityResumeEvent(IActivityResumeCallback callback) {
        resumeCallbacks.remove(callback);
    }
    
    /**
     * 注册activity onPause 事件
     *
     * @param callback activity onPause 事件回调
     */
    public void registerActivitPauseEvent(IActivityPauseCallback callback) {
        pauseCallbacks.add(callback);
    }
    
    /**
     * 反注册activity onPause事件回调
     *
     * @param callback 已经注册的 activity onPause事件回调
     */
    public void unRegisterActivitPauseEvent(IActivityPauseCallback callback) {
        pauseCallbacks.remove(callback);
    }
    
    /**
     * 清空 activity onResume事件回调
     */
    public void clearActivitResumeCallbacks() {
        resumeCallbacks.clear();
    }
    
    /**
     * 清空 activity onPause 事件回调
     */
    public void clearActivitPauseCallbacks() {
        pauseCallbacks.clear();
    }
    
    /**
     * 获取最新的activity
     *
     * @return 最新的activity
     */
    public Activity getLastActivity() {
        return lastActivity;
    }
    
    /**
     * activity onCreate 监听回调
     *
     * @param activity           发生onCreate事件的activity
     * @param savedInstanceState 缓存状态数据
     */
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        lastActivity = activity;
    }
    
    /**
     * activity onStart 监听回调
     *
     * @param activity 发生onStart事件的activity
     */
    @Override
    public void onActivityStarted(Activity activity) {
        lastActivity = activity;
    }
    
    /**
     * activity onResume 监听回调
     *
     * @param activity 发生onResume事件的activity
     */
    @Override
    public void onActivityResumed(Activity activity) {
        lastActivity = activity;
        
        List<IActivityResumeCallback> tmdCallbacks = new ArrayList<IActivityResumeCallback>(resumeCallbacks);
        for (IActivityResumeCallback callback : tmdCallbacks) {
            callback.onActivityResume(activity);
        }
    }
    
    /**
     * activity onPause 监听回调
     *
     * @param activity 发生onPause事件的activity
     */
    @Override
    public void onActivityPaused(Activity activity) {
        List<IActivityPauseCallback> tmdCallbacks = new ArrayList<IActivityPauseCallback>(pauseCallbacks);
        for (IActivityPauseCallback callback : tmdCallbacks) {
            callback.onActivityPause(activity);
        }
    }
    
    /**
     * activity onStop 监听回调
     *
     * @param activity 发生onStop事件的activity
     */
    @Override
    public void onActivityStopped(Activity activity) {
    }
    
    /**
     * activity onSaveInstanceState 监听回调
     *
     * @param activity 发生 onSaveInstanceState 事件的activity
     */
    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }
    
    /**
     * activity onDestroyed 监听回调
     *
     * @param activity 发生 onDestroyed 事件的activity
     */
    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity == lastActivity) {
            lastActivity = null;
        }
    }
}
