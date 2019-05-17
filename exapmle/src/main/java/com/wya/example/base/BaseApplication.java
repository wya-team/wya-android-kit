package com.wya.example.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.arialyy.aria.core.Aria;
import com.wya.helper.WYAConstants;
import com.wya.utils.utils.AppUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @date: 2018/11/28 10:28
 * @author: Chunjiang Mao
 * @classname: BaseApplication
 * @describe:
 */

public class BaseApplication extends Application {
    
    private static BaseApplication INSTANCE;
    
    public static BaseApplication getInstance() {
        return INSTANCE;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        initBase();
        INSTANCE = this;
    }
    
    private void initBase() {
        initConstants();
        InitializeService.start(this);
        initRealm();
        Aria.init(this);
    }
    
    /**
     * 初始化数据库
     */
    private void initRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                //指定数据库的名称，如果不指定默认为 default
                .name(WYAConstants.DB_NAME)
                //指定数据库的版本号
                .schemaVersion(AppUtil.getVersionCode(this))
                //声明版本冲突时自动删除原数据库
                // .deleteRealmIfMigrationNeeded()
                //声明数据库只在内存中持久化
                // .inMemory()
                //指定数据库的秘钥
                // .encryptionkey()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
    
    /**
     * 初始化常量
     */
    private void initConstants() {
        new WYAConstants.Builder(this)
                //设置数据库名称
                .dBName("test")
                //设置主题颜色
                .themeColor("#ffffff")
                //设置是否显示log，log 搜索WYA_LOG
                .isShowLog(true)
                //是否是debug
                .isDebug(AppUtil.isApkInDebug(getApplicationContext()))
                .build();
    }
    
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
