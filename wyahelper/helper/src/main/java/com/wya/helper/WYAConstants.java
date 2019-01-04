package com.wya.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;

/**
 * @date: 2018/12/7 10:41
 * @author: Chunjiang Mao
 * @classname: WYAConstants
 * @describe: 全局常量
 */

public class WYAConstants {
    //数据库名称
    public static String DB_NAME;

    //主题色
    public static int THEME_COLOR;

    //是否打印log
    public static boolean IS_SHOW_LOG;

    //是否debug
    public static boolean IS_DEBUG;


    /**
     * @param dbName
     */
    protected WYAConstants(@Nullable String dbName, int themeColor, boolean isShowLog, boolean isDebug) {
        DB_NAME = dbName;
        THEME_COLOR = themeColor;
        IS_SHOW_LOG = isShowLog;
        IS_DEBUG = isDebug;
    }

    public static class Builder {
        private String dbName;
        private int themeColor;
        private boolean isShowLog;
        private boolean isDebug;

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
            //默认数据库名称
            dbName = "wyaRealm.realm";
            //默认白色
            themeColor = Color.parseColor("#ffffff");
            //默认打印log
            isShowLog = true;
            //默认打印log
            isDebug = false;
        }

        /**
         * 设置数据库名称
         *
         * @param dbName
         * @return
         */
        @SuppressLint("NewApi")
        public WYAConstants.Builder dBName(String dbName) {
            if (dbName != null && !dbName.isEmpty()) {
                this.dbName = dbName + ".realm";
                return this;
            } else {
                throw new IllegalArgumentException("A non-empty filename must be provided");
            }
        }

        /**
         * 设置设置主题色
         *
         * @param colorValue
         * @return
         */
        @SuppressLint("NewApi")
        public WYAConstants.Builder themeColor(String colorValue) {
            if (colorValue != null && !colorValue.isEmpty()) {
                themeColor = Color.parseColor(colorValue);
                return this;
            } else {
                throw new IllegalArgumentException("A non-empty filename must be provided");
            }
        }

        /**
         * 设置是否显示log
         *
         * @param isShowLog
         * @return
         */
        public WYAConstants.Builder isShowLog(boolean isShowLog) {
            this.isShowLog = isShowLog;
            return this;
        }

        /**
         * 设置是否是debug
         *
         * @param isDebug
         * @return
         */
        public WYAConstants.Builder isDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }


        public WYAConstants build() {
            return new WYAConstants(dbName, themeColor, isShowLog, isDebug);
        }
    }
}
