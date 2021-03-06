# ToolBar
## 功能说明
- 标题栏,沉浸式颜色根据标题颜色而改变

## 属性说明

属性 | 说明 | 类型 | 默认值
--- | ---  |  ---  |  ---
showToolBar | 是否显示标题栏 | boolean | true
toolbarBgColorValue | 标题栏背景颜色 | int | Color.parseColor("#000000")
isShowTitle | 是否展示标题  | boolean | true
titleStr | 标题内容 | String | "标题"
titleTextSize | 标题文字大小 | int | 16
titleTextColorValue | 标题文字颜色 |  int | Color.parseColor("#ffffff")
isShowTvLeft | 是否显示左边文字 | boolean | false
tvLeftStr | 左边文字内容 | String| "左边"
tvLeftTextSize | 左边文字大小 |int| 14
tvLeftTextColorValue | 左边文字颜色 |  Color.parseColor("#ffffff")
isShowImgLeft | 左边图片是否显示 | boolean | true
imgLeftRes | 左边图片资源 | int | null
isShowTvRight | 是否显示右边文字| boolean | false
tvRightStr | 右边文字内容 | String| "右边"
tvRightTextColorValue | 右边文字的颜色 |  Color.parseColor("#ffffff")
isShowImgRight | 是否显示右边第一张图片| boolean | false
isShowImgRightAnther | 是否显示右边第二张图片| boolean | false
imgRightRes | 右边第一张图片| int | null
imgRightResAnther | 右边第二章图片资源 | int | null
isLight | 是否是明亮的标题栏 | boolean | false


## 用法说明
- 项目中BaseActivity继承BaseToolBarActivity添加标题栏ToolBar,然后调用initWYAActionBarDefault方法初始化标题栏

- 方法

方法|说明
---|---
initWYAActionBarDefault(boolean showToolBar, String toolbarBgColorValue, boolean isShowTitle, String titleStr, int titleTextSize, String titleTextColorValue, boolean isShowTvLeft, String tvLeftStr, int tvLeftTextSize, String tvLeftTextColorValue, boolean isShowImgLeft, int imgLeftRes, boolean isShowTvRight, String tvRightStr, int tvRightTextSize, String tvRightTextColorValue, boolean isShowImgRight, boolean isShowImgRightAnther, int imgRightRes, int imgRightResAnther)|初始化标题（默认标题， 在baseActivity中调用）
initToolBarBgColor(String toolbar_bg_color_value)|修改标题栏背景颜色
initToolBarTitle(String titleStr, int titleTextSize, int titleTextColor, boolean showTitle)|修改标题
setToolBarTitle(String titleStr)|只修改标题内容
initImgLeft(int imgLeftRes, boolean showImgLeft)|修改标题栏左边图标
initToolBarBgColor(int toolbarBgColorValue, boolean isLight)|设置标题栏颜色和状态栏颜色
initImgRight(int imgRightRes, boolean showImgRight)|设置标题右边的图片
initImgRightAnther(int imgRightAntherRes, boolean showImgAnther)|设置标题右边另外的图片
initTvRight(String tvRightStr, int tvRightTextColor, int tvRightTextSize, boolean showTvRight) |设置标题右边的文字
initTvRightAnther(String tvRightAntherStr, int tvRightAntherTextColor, int tvRightAntherTextSize, boolean showTvRightAnther) |设置标题右边的文字
initShowToolBar(boolean showTitle)|设置是否显示标题
setLeftOnclickListener(onLeftOnclickListener onLeftOnclickListener)|设置左边点击事件内容和监听
seRightTvOnclickListener(onRightTvOnclickListener onRightTvOnclickListener)|设置右边文字点击事件
setRightTvAntherOnclickListener(onRightTvAntherOnclickListener onRightTvAntherOnclickListener)|设置右边第二个点击事件监听
setRightImageOnclickListener(onRightImageOnclickListener onRightImageOnclickListener)|设置右边图片点击事件监听
setRightImageAntherOnclickListener(onRightImageAntherOnclickListener onRightImageAntherOnclickListener)|设置右边第二个图片点击事件监听
