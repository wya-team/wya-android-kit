package com.weiyian.android.bugly;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebView;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.CrashReport.UserStrategy;


/**
 * @author :
 */
public abstract class BaseBuglyHelper {
    
    private static long DEF_REPORT_DELAY_TIME = 15_000L;
    
    public void initCrashReport(@NonNull android.content.Context context, @NonNull String appID, boolean isDebug) {
        initCrashReport(context, appID, null, isDebug);
    }
    
    /**
     * @param context     :context,can not be null!
     * @param channelName :channel name
     * @param isDebug     :ture打开调试模式,建议在测试阶段建议设置成true，发布时改为false,特性如下：
     *                    <br> 输出详细的Bugly SDK的Log
     *                    <br> 每一条Crash都会被立即上报
     *                    <br> 自定义日志将会在Logcat中输出
     */
    public void initCrashReport(@NonNull android.content.Context context, @NonNull String appID, @Nullable String channelName, boolean isDebug) {
        String packageName = context.getPackageName();
        String processName = Util.getProcessName(android.os.Process.myPid());
        
        UserStrategy defStrategy = new UserStrategy(context);
        // 设置渠道名称
        if (!TextUtils.isEmpty(channelName)) {
            defStrategy.setAppChannel(channelName);
        }
        defStrategy.setAppReportDelay(DEF_REPORT_DELAY_TIME);
        // 设置上报主进程,避免重复上报
        defStrategy.setUploadProcess(TextUtils.isEmpty(processName) || processName.equals(packageName));
        UserStrategy strategy = configStrategy(defStrategy, isDebug);
        if (null == strategy) {
            throw new IllegalArgumentException("strategy can not be null!");
        }
        // 初始化bugly
        CrashReport.initCrashReport(context, appID, isDebug, strategy);
        // 调试设备设置成“开发设备
        CrashReport.setIsDevelopmentDevice(context, isDebug);
    }
    
    public abstract UserStrategy configStrategy(UserStrategy strategy, boolean isDebug);
    
    /**
     * @param webView    指定被监控的webView
     * @param autoInject 是否自动注入Bugly.js文件
     * @return :true 设置成功；false 设置失败
     * @see #setJavascriptMonitor(WebView)
     */
    public static boolean setJavascriptMonitor(WebView webView, boolean autoInject) {
        return null != webView && CrashReport.setJavascriptMonitor(webView, autoInject);
    }
    
    /**
     * @param webView 指定被监控的webView
     * @return :true 设置成功；false 设置失败
     */
    public static boolean setJavascriptMonitor(WebView webView) {
        return setJavascriptMonitor(webView, true);
    }
    
    /**
     * 以k-v的形式上报日志
     *
     * @param context   :
     * @param userKey   :
     * @param userValue :
     */
    public static void configUserData(@NonNull android.content.Context context, String userKey, String userValue) {
        if (TextUtils.isEmpty(userKey) || TextUtils.isEmpty(userValue)) {
            return;
        }
        CrashReport.putUserData(context, userKey, userValue);
    }
    
    /**
     * 上报部分自己定义的异常信息
     *
     * @param e :
     */
    public static void postException(Throwable e) {
        CrashReport.postCatchedException(e);
    }
}
