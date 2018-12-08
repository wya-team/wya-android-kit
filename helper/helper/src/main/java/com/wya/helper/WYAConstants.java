package com.wya.helper;

import android.content.Context;
import android.support.annotation.Nullable;

/**
 * 创建日期：2018/12/7 10:41
 * 作者： Mao Chunjiang
 * 文件名称： WYAConstants
 * 类说明：全局常量
 */

public final class WYAConstants {
    public static String DB_NAME;//数据库名称
    /**
     * @param dbName
     */
    protected WYAConstants(@Nullable String dbName) {
        this.DB_NAME = dbName;
    }

    public static class Builder {
        private String dbName;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalStateException("Call `Realm.init(Context)` before creating a RealmConfiguration");
            } else {
                this.initializeBuilder(context);
            }
        }

        /**
         * 初始化默认值
         *
         * @param context
         */
        private void initializeBuilder(Context context) {
            dbName = "wyaRealm.realm";
        }

        /**
         * 设置数据库名称
         *
         * @param dbName
         * @return
         */
        public WYAConstants.Builder dBName(String dbName) {
            if (dbName != null && !dbName.isEmpty()) {
                this.dbName = dbName + ".realm";
                return this;
            } else {
                throw new IllegalArgumentException("A non-empty filename must be provided");
            }
        }

        public WYAConstants build() {
            return new WYAConstants(dbName);
        }
    }
}
