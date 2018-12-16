package com.wya.example.base;


import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.wya.helper.WYAConstants;
import com.wya.uikit.toolbar.swipebacklayout.BGASwipeBackHelper;
import com.wya.utils.utils.AppUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
        initConstants();
        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);
        initRealm();
    }

    /**
     * 初始化数据库
     */
    private void initRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(WYAConstants.DB_NAME)//指定数据库的名称，如果不指定默认为 default
                .schemaVersion(AppUtil.getVersionCode(this)) //指定数据库的版本号
                // .deleteRealmIfMigrationNeeded()//声明版本冲突时自动删除原数据库
                // .inMemory()//声明数据库只在内存中持久化
                // .encryptionkey()//指定数据库的秘钥
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    /**
     * 初始化常量
     */
    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    private void initConstants() {
        new WYAConstants.Builder(this)
                .dBName("test")//设置数据库名称
                .themeColor("#0000ff")//设置主题颜色
                .isShowLog(true)//设置是否显示log，log 搜索WYA_LOG
                .isDebug(AppUtil.isApkInDebug(getApplicationContext()))//是否是debug
                .build();
    }
}
