package com.wya.example.base;


import com.wya.helper.WYAConstants;
import com.wya.utils.BaseUtilsApplication;
import com.wya.utils.utils.AppUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 创建日期：2018/11/28 10:28
 * 作者： Mao Chunjiang
 * 文件名称： BaseApplication
 * 类说明：
 */

public class BaseApplication extends BaseUtilsApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initConstants();
        initRealm();
    }

    /**
     * 初始化数据库
     */
    private void initRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(WYAConstants.DB_NAME)//指定数据库的名称，如果不指定默认为 default
                .schemaVersion(AppUtils.getVersionCode(this)) //指定数据库的版本号
                // .deleteRealmIfMigrationNeeded()//声明版本冲突时自动删除原数据库
                // .inMemory()//声明数据库只在内存中持久化
                // .encryptionkey()//指定数据库的秘钥
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    /**
     * 初始化常量
     */
    private void initConstants() {
        new WYAConstants.Builder(this)
                .dBName("test")//设置数据库名称
                .build();
    }
}
