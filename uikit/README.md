uikit开发
======

## 1、ToolBar


#### 使用例子在 example module下的 uikit/toolbar 依赖库在uikit/toolbar文件夹下
     
#### 使用说明
#####（1）项目中BaseActivity继承BaseToolBarActivity添加标题栏ToolBar
#####（2）API说明

* initToolBarBgColor(String toolbar_bg_color_value)

##### 修改标题栏背景颜色
字段  | 说明 
---- | -----   
toolbar_bg_color_value | 背景颜色值（#000000形式） 
    
* initToolBarTitle(String titleStr, int titleTextSize, int titleTextColor, boolean showTitle)
#####  修改标题
字段  | 说明 
       ---- | -----   
     titleStr  | 标题内容 
     titleTextSize  | 标题字体大小，默认18 
     titleTextColor  | 标题字体颜色， 默认白色 
     showTitle  | 标题是否显示，默认显示
     字段  | 说明 
     
* initImgLeft(int imgLeftRes, boolean showImgLeft)
##### 修改标题栏左边图标
 字段  | 说明 
       ---- | -----   
     imgLeftRes  | 左图标（R.mipmap.xxx/R.drawable.xxx）
     showImgLeft  | 左图标是否显示，默认显示 
     
     
* initTvLeft(String tvLeftStr, int tvLeftTextColor, int tvLeftTextSize, boolean showTvLeft)
##### 修改标题栏左边文字
 字段  | 说明 
       ---- | -----   
     tvLeftStr  | 左文字内筒
     tvLeftTextColor  | 左文字字体颜色
     tvLeftTextSize  | 左文字字体字体大小
     showTvLeft  | 左文字是否显示，默认不显示
     
* initImgRight(int imgRightRes, boolean showImgRight, int imgRightAntherRes, boolean showImgAnther)
##### 修改标题右边图标
 字段  | 说明 
       ---- | -----   
     imgRightRes  | 右图标（R.mipmap.xxx/R.drawable.xxx）
     showImgRight  | 右左图标是否显示，默认不显示 
     imgRightAntherRes  | 第二个右图标（R.mipmap.xxx/R.drawable.xxx）
     showImgAnther  | 第二个右图标是否显示，默认不显示 
     
     
* initTvRight(String tvRightStr, int tvRightTextColor, int tvRightTextSize, boolean showTvRight) 
##### 修改标题栏右边文字
 字段  | 说明 
       ---- | -----   
     tvRightStr  | 右文字内筒
     tvRightTextColor  | 右文字字体颜色
     tvRightTextSize  | 右文字字体字体大小
     showTvRight  | 右文字是否显示，默认不显示
* initShowToolBar(boolean showTitle)、
##### 设置是否显示标题
  字段  | 说明 
        ---- | -----   
      showTitle  | 设置标题栏是否显示，默认显示
 