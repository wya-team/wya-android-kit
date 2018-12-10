# WYAConstants
## 功能说明
- 全局常量

## 属性说明
属性 | 说明 | 类型 | 默认值
---|---|---|---
DB_NAME|数据库名称|String|wyaRealm.realm
THEME_COLOR|主题色|int|Color.parseColor("#ffffff");//默认白色
IS_SHOW_LOG|是否打印log|boolean|true
IS_DEBUG|是否debug|boolean|false

## 用法说明
- Application初始化
       
        new WYAConstants.Builder(this)
                       .dBName("test")//设置数据库名称
                       .themeColor("#000000")//设置数据库名称
                       .isShowLog(true)//设置是否显示log，log 搜索WYA_LOG
                       .isDebug(AppUtils.isApkInDebug(getApplicationContext()))
                       .build();
