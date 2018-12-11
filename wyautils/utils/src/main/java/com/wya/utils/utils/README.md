# utils

# 1、AppUtil
## 功能说明
- 获取app相关数据

## 属性说明

## 用法说明

方法|说明
---|---
getAppName(Context context)|获取应用程序名称
getVersionName(Context context)|获取前应用的版本名称
getVersionCode(Context context)|获取当前应用的版本号
getPackageName(Context context)|获取当前应用的包名
getBitmap(Context context)|获取当前应用的图标
isApkInDebug(Context context)|判断应用是否是debug状态


# 2、ColorUtil
## 功能说明
工具类（color整型、rgb数组、16进制互相转换）
## 属性说明

## 用法说明
方法|说明
---|---
int2Hex(int colorInt)|Color的Int整型转Color的16进制颜色值
int2Hex2(int colorInt)|Color的Int整型转Color的16进制颜色值【方案二】
int2Rgb(int colorInt)|Color的Int整型转Color的rgb数组
rgb2Hex(int[] rgb)|rgb数组转Color的16进制颜色值
hex2Int(String colorHex)|Color的16进制颜色值 转 Color的Int整型
hex2Rgb(String colorHex)|Color的16进制颜色值 转 rgb数组
rgb2Int(int[] rgb)|Color的rgb数组转Color的Int整型

# 3、FileUtil
## 功能说明
文件工具类
## 属性说明

## 用法说明
方法|说明
---|---
saveBitmap(String dir, Bitmap b)|保存图片
deleteFile(String url)| 删除文件
isExternalStorageWritable()|判断是否存在SD卡

# 4、LogUtil
## 功能说明
打印日志工具类
## 属性说明

## 用法说明
方法|说明
---|---
v(String msg)|VERBOSE日志
i(String msg)|INFO日志
d(String msg)|DEBUG日志
w(String msg)|WARN日志
e(String msg)|ERROR日志


# 5、NetWorkUtil
## 功能说明
网络判断工具类
## 属性说明

## 用法说明
方法|说明
---|---
isNetworkConnected(Context context)|判断是否有网络连接
isWifiConnected(Context context)|判断WIFI网络是否可用
isMobileConnected(Context context)|判断MOBILE网络是否可用



# 6、PhoneUtil
## 功能说明
手机信息工具类
## 属性说明

## 用法说明
方法|说明
---|---
getInstance()| 获取实例对象
getSDKVersionNumber()|获取手机系统版本号
getPhoneModel()|获取手机型号
getPhoneWidth(Context context)| 获取手机宽度
getPhoneHeight(Context context)|获取手机高度
isSDCardMount()|判断sd卡是否挂载
getSDFreeSize()|获取sd卡剩余空间的大小
getSDAllSize()|获取sd卡空间的总大小
isTablet(Context context)|判断是否是平板
isApkInstalled(Context context, String packageName)|判断一个apk是否安装
getAppPermissions(PackageInfo packageInfo)|获取应用权限 名称列表
getInstalledApp(Context context)| 获取手机内安装的应用
getUserInstalledApp(Context context)| 获取手机安装非系统应用
startAppPkg(Context context, String pkg)|打开指定包名的应用
unInstallApk(Context context, String packageName)|卸载指定包名的应用
isMobileNO(String mobile)|手机号判断


# 7、ScreenUtil
## 功能说明
屏幕工具类
## 属性说明

## 用法说明
方法|说明
---|---
dip2px(Context context,int dp)| dp转px
px2dp(Context context,int px)|px转换dip
px2sp(Context context,int pxValue)|px转换sp
sp2px(Context context,int spValue)|sp转换px
getScreenHeight(Context context)|获取屏幕高
getScreenWidth(Context context)|获取屏幕宽
toggleScreenOrientation(Activity activity)| 切换屏幕的方向.
isPortrait(Context context)|获得当前屏幕的方向



# 2、StringUtil
## 功能说明
String工具类
## 属性说明

## 用法说明
方法|说明
---|---
stringForTime(int timeMs)| 将毫秒值转化为时分秒显示
