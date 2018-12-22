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



# 8、StringUtil
## 功能说明
String工具类

## 属性说明

## 用法说明
方法|说明
---|---
stringForTime(int timeMs)| 将毫秒值转化为时分秒显示
getStrSplitByCondition(String str, String split, String condition)| 字符拆分成数组
getSign(String signStr)| MD5 加密
String copy(Object... obj)| 拼接后的字符串
replace(String strSc, String oldStr, String newStr)| 替换字符串
isNull(Object... strArray)| 判断多个参数是否都为空
String toHtml(String str)| 将字符串转换成HTML格式的字符串
toText(String str)| 将HTML格式的字符串转换成常规显示的字符串
getEncryptMobile(String phoneNum)| 获取加密的手机号
checkMobile(String phoneNum) | 检查手机号
checkPhone(String phone)| 验证固定电话号码
checkPass(String pass)| 检查密码有效
formatResourceString(Context context, int resource, Object... args)| 根据string.xml资源格式化字符串
checkIdCard(String idCard)| 验证身份证号码
numStrToInt(String numStr)| 将元单位数字转成int类型的元
simpleFormat(int num)| 格式化数字
newNumFormat(String numStr)| 数字转成以万、亿为单位，1.0–>1; 1.1–>1.1
join(String[] strs, String token)| 将一字符串数组以某特定的字符串作为分隔来变成字符串
check(String str, String test)| 验证字符串合法性
String2Integer(String str, Integer defaultValue)| 将数值型字符串转换成Integer型
Integer2String(Integer it, String ret)| 将数值型转换成字符串
compare(String str1, String str2)| 比较两字符串大小(ASCII码顺序)
num2Chinese(double num)| 将阿拉伯数字的钱数转换成中文方式
firstToUpper(String str)| 将字符串的首字母改为大写
listContain(List list, String str1)| 判断list中是否包含某一个字符串
List2String(List<String> list, String sign)| list转String
String2List(String target, String sign)| String转list 去除null 空串
getStringFromId(Application app, int resId)| 根据Resource ID获取字符串
doubleToString(double str)| 将浮点数进行四舍五入
trimString(String text)| 去除字符串前后的空格
escapeString(String text)| 去除转义
appendUrlParams(String url, String params)| 添加url参数
getColorSpan(Integer[] index, int[] colors, CharSequence... s)| 设置一段不同颜色的文字
getSubStrSplit(String str, String split)| 字符拆分
resizeContent(String content, int size)| 截取指定size的String
encodeChineseStr(String srcStr)| 转码中文字符串
compareToMin(String s1, String s2)| 用来比较手机号版本
spliteTime(String dateStr, int start, int end)| 截取指定长度 从0开始，包左不包右
hasChineseChar(String str)| 是否有中文字符

# 9、FileManagerUtil
## 功能说明
文件下载工具类，文件上传下载采用第三方库Aria，FileManagerUtil对其进行了使用封装。

## 属性说明

## 用法说明

- 首先需要在Application中初始化`Aria.init(this);`
- 在使用时首先需要在onCreate()注册，在onDestroy()中注销

方法|说明
---|---
FileManagerUtil()|构造方法获取FileManagerUtil对象
register()|注册
unRegister|注销
getDownloadReceiver()|获取DownloadReceiver对象，所有的下载在这个对象下操作
setOnDownLoaderListener(OnDownLoaderListener onDownLoaderListener)|设置监听事件
void onDownloadState(int state, DownloadTask task, Exception e)|监听事件回调方法state代表状态，task代表所有下载信息，Exception 错误提示，在正常是返回null


state状态|说明
---|---
TASK_START|任务开始时
TASK_RUNNING|任务正在运行
TASK_RESUME|任务重新启动
TASK_STOP|任务被暂停
TASK_CANCEL|任务取消
TASK_FAIL|下载失败
TASK_COMPLETE|下载完成
TASK_NO_POINT|该下载任务不支持断点下载

- 开始下载/重新开启（暂停之后）url代表文件地址，filepath 图片存储全路径
````
mFileManagerUtil.getDownloadReceiver().load(url)
                  .setFilePath(filepath)
                  .start();
````
- 暂停下载
````
mFileManagerUtil.getDownloadReceiver().load(url).stop();
````
- 取消下载 cancel方法中参数代表是否需要删除下载的文件
````
mFileManagerUtil.getDownloadReceiver().load(url).cancel(true);
````

10. DataCleanUtil
## 功能说明
清除应用缓存

## 属性说明

## 用法说明

方法|说明
---|---
cleanInternalCache(Context context)|清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
cleanDatabases(Context context)|清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
cleanSharedPreference(Context context)|清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
cleanDatabaseByName(Context context, String dbName)|按名字清除本应用数据库
cleanFiles(Context context)|清除/data/data/com.xxx.xxx/files下的内容
cleanExternalCache(Context context)|清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
cleanCustomCache(String filePath)|清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
cleanApplicationData(Context context, String... filepath)|清除本应用所有的数据
deleteFilesByDirectory(File directory)|删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
deleteFolderFile(String filePath, boolean deleteThisPath)|删除指定目录下文件及目录
getCacheSize(File file)|获取文件缓存大小

11. DateUtil
## 功能说明
时间格式化

## 属性说明

## 用法说明

方法|说明
---|---
stamp2date(long stamp, String pattern)|时间戳转字符串时间stamp   时间戳pattern 时间格式
stamp2date(long stamp)|时间戳转字符串时间 yyyy-MM-dd HH:mm:ss
formatDate(String time, String pattern)|格式化时间
date2Stamp(String time)|时间转时间戳
getYear(Date date)|获取某日期的年份
getMonth(Date date) |获取某日期的月份
getDay(Date date)|获取某日期的日数
getDayEndTime(final Date date)|获取指定时间的那天 23:59:59.999 的时间
getDistanceDays(String str1, String str2)|两个时间之间相差距离多少天
getDistanceTime(String str1, String str2)|两个时间相差距离多少天多少小时多少分多少秒

12. QRCodeUtil
## 功能说明
二维码/条形码生成 需要用到Google zxing库

## 属性说明

## 用法说明

方法|说明
---|---
createQRImage(String content, int widthPix, int heightPix, Bitmap logoBm, String filePath)|生成二维码Bitmap
addLogo(Bitmap src, Bitmap logo)|在二维码中间添加Logo图案
createBarcode(String content, int widthPix, int heightPix, String filePath)|绘制条形码