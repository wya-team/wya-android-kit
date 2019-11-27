package com.weiyian.android.bugly;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.CrashModule;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.lib.BuildConfig;

import java.util.Map;

/**
 * 文档 {@link "https://bugly.qq.com/docs/user-guide/instruction-manual-android-hotfix/?v=20180709165613"}
 *
 * @author :
 */
public class WYABugly extends BaseBuglyHelper {
    
    private static WYABugly sInstance;
    
    private final int BUGLY_TAG_ID_DEBUG = 96900;
    private final int BUGLY_TAG_ID_RELEASE = 98174;
    private int BUGLY_TAG_ID = BUGLY_TAG_ID_DEBUG;
    
    private WYABugly() {
    }
    
    public static synchronized WYABugly getInstance() {
        if (sInstance == null) {
            sInstance = new WYABugly();
        }
        return sInstance;
    }
    
    public void confiyBugyly(Application application, String buglyAppId, Context context, int icon, Class activity) {
        if (null == context || null == application) {
            return;
        }
        configyTinker(context, icon, activity);
        Bugly.init(application, buglyAppId, true);
    }
    
    public boolean hasInitialized() {
        return CrashModule.hasInitialized();
    }
    
    /**
     * 设置用户ID 您可能会希望能精确定位到某个用户的异常，我们提供了用户ID记录接口。
     * 例：网游用户登录后，通过该接口记录用户ID，在页面上可以精确定位到每个用户发生Crash的情况。
     *
     * @param userId :
     */
    public void configUserId(String userId) {
        CrashReport.setUserId(userId);
    }
    
    public void setUserSceneTag(Context context) {
        if (null == context) {
            return;
        }
        BUGLY_TAG_ID = BuildConfig.DEBUG ? BUGLY_TAG_ID_DEBUG : BUGLY_TAG_ID_RELEASE;
        CrashReport.setUserSceneTag(context, BUGLY_TAG_ID);
    }
    
    /**
     * 添加自定义额外的上报数据，k-v形式
     * 最多可以有9对自定义的key-value（超过则添加失败）；
     * key限长50字节，value限长200字节，过长截断；
     * key必须匹配正则：[a-zA-Z[0-9]]+。
     *
     * @param userkey   :
     * @param uservalue :
     */
    public void putUserData(Application application, @NonNull String userkey, String uservalue) {
        if (TextUtils.isEmpty(userkey) || null == application) {
            return;
        }
        CrashReport.putUserData(application, userkey, uservalue);
    }
    
    /**
     * 上报自定义异常信息
     *
     * @param throwable :
     */
    public void postCatchedException(Throwable throwable) {
        if (null != throwable) {
            CrashReport.postCatchedException(throwable);
        }
    }
    
    @Override
    public CrashReport.UserStrategy configStrategy(CrashReport.UserStrategy userStrategy, final boolean isDebug) {
        //there use to intercept crash report log
        userStrategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
            /**
             * there use to intercept crash report log
             *
             * @param crashType 错误类型：CRASHTYPE_JAVA，CRASHTYPE_NATIVE，CRASHTYPE_U3D ,CRASHTYPE_ANR
             * @param errorType 错误的类型名
             * @param errorMessage 错误的消息
             * @param errorStack 错误的堆栈
             * @return byte[] 额外的2进制内容进行上报
             */
            @Override
            public synchronized byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType, String errorMessage, String errorStack) {
                
                return super.onCrashHandleStart2GetExtraDatas(crashType, errorType, errorMessage, errorStack);
                
            }
            
            /**
             *there use to intercept crash report log
             *
             * @param crashType 错误类型：CRASHTYPE_JAVA，CRASHTYPE_NATIVE，CRASHTYPE_U3D ,CRASHTYPE_ANR
             * @param errorType 错误的类型名
             * @param errorMessage 错误的消息
             * @param errorStack 错误的堆栈
             * @return 返回额外的自定义信息上报
             */
            @Override
            public synchronized Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
                
                return super.onCrashHandleStart(crashType, errorType, errorMessage, errorStack);
            }
            
        });
        
        if (isDebug) {
            userStrategy.setAppVersion("wya_dev");
        }
        
        return userStrategy;
    }
    
    private void configyTinker(Context context, int icon, Class activity) {
        if (null == context) {
            return;
        }
        
        Beta.autoInit = true;
        
        // {@link MainActivity#init() Beta.checkUpgrade(); }
        Beta.autoCheckUpgrade = false;
        
        // delay时间内不再重复向后台请求策略
        Beta.initDelay = 1 * 1000L;
        
        // 设置通知栏大图标
        Beta.largeIconId = icon;
        
        // 设置状态栏小图标
        Beta.smallIconId = icon;
        
        Beta.defaultBannerId = icon;
        
        // 设置sd卡的Download为更新资源保存目录; 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        
        // 点击过确认的弹窗在APP下次启动自动检查更新时会再次显示;
        Beta.showInterruptedStrategy = false;
        
        // default all
        if (null != activity) {
            Beta.canShowUpgradeActs.add(activity);
        }
        Beta.dialogFullScreen = true;
        
        Beta.strToastCheckingUpgrade = null;
        Beta.strToastYourAreTheLatestVersion = null;
        Beta.strToastCheckUpgradeError = null;
    }
    
    /**
     * 安装Tinker
     */
    public void installTinker() {
        Beta.installTinker();
    }
    
}
