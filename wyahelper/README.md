## 1.WYAConstants
全局常量
#####  使用说明
###### Application初始化
        new WYAConstants.Builder(this)
                .dBName("test")//设置数据库名称
                .themeColor("#000000")//设置数据库名称
                .isShowLog(true)//设置是否显示log，log 搜索WYA_LOG
                .isDebug(AppUtils.isApkInDebug(getApplicationContext()))
                .build();