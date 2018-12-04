uikit开发
======

## 1.ToolBar


#### 使用例子在 example module下的 uikit/toolbar 依赖库在uikit/toolbar文件夹下
     
#### 使用说明
#####（1）项目中BaseActivity继承BaseToolBarActivity添加标题栏ToolBar
#####（2）API说明

* initToolBarBgColor(String toolbar_bg_color_value)

##### 修改标题栏背景颜色
字段  | 说明 
---- | -----   
toolbar_bg_color_value | 背景颜色值（#000000形式） 

* initWYAActionBarDefault(boolean showToolBar, String toolbarBgColorValue, boolean isShowTitle, String titleStr, int titleTextSize, String titleTextColorValue,
                                          boolean isShowTvLeft, String tvLeftStr, int tvLeftTextSize, String tvLeftTextColorValue, boolean isShowImgLeft, int imgLeftRes,
                                          boolean isShowTvRight, String tvRightStr, int tvRightTextSize, String tvRightTextColorValue, boolean isShowImgRight, boolean isShowImgRightAnther, int imgRightRes, int imgRightResAnther)

##### 初始化标题（默认标题， 在baseActivity中调用）
字段  | 说明 
--- | ---  
showToolBar | 是否显示标题栏 
toolbarBgColorValue | 标题栏背景颜色 
isShowTitle | 是否展示标题 
titleStr | 标题内容 
titleTextSize | 标题文字大小 
titleTextColorValue | 标题文字颜色 
isShowTvLeft | 是否显示左边文字
tvLeftStr | 左边文字内容 
tvLeftTextSize | 左边文字大小 
tvLeftTextColorValue | 左边文字内容 
isShowImgLeft | 左边图片是否显示 
imgLeftRes | 左边图片资源 
isShowTvRight | 是否显示右边文字
tvRightStr | 右边文字内容
tvRightTextColorValue | 右边文字的颜色
isShowImgRight | 是否显示右边第一张图片
isShowImgRightAnther | 是否显示右边第二张图片
imgRightRes | 右边第一张图片
imgRightResAnther | 右边第二章图片资源 
    
* initToolBarTitle(String titleStr, int titleTextSize, int titleTextColor, boolean showTitle)
#####  修改标题
字段  | 说明 
---|---  
titleStr  | 标题内容 
titleTextSize  | 标题字体大小，默认18 
titleTextColor  | 标题字体颜色， 默认白色 
showTitle  | 标题是否显示，默认显示


* setToolBarTitle(String titleStr)
#####  只修改标题内容
字段  | 说明 
---|---  
titleStr  | 标题内容 
     
* initImgLeft(int imgLeftRes, boolean showImgLeft)
##### 修改标题栏左边图标
字段  | 说明 
---|--- 
imgLeftRes  | 左图标（R.mipmap.xxx/R.drawable.xxx）
showImgLeft  | 左图标是否显示，默认显示 
     
     
* initTvLeft(String tvLeftStr, int tvLeftTextColor, int tvLeftTextSize, boolean showTvLeft)
##### 修改标题栏左边文字
字段  | 说明 
---|---  
tvLeftStr  | 左文字内筒
tvLeftTextColor  | 左文字字体颜色
tvLeftTextSize  | 左文字字体字体大小
showTvLeft  | 左文字是否显示，默认不显示
     
* initImgRight(int imgRightRes, boolean showImgRight, int imgRightAntherRes, boolean showImgAnther)
##### 修改标题右边图标
字段  | 说明 
---|---
imgRightRes  | 右图标（R.mipmap.xxx/R.drawable.xxx）
showImgRight  | 右左图标是否显示，默认不显示 
imgRightAntherRes  | 第二个右图标（R.mipmap.xxx/R.drawable.xxx）
showImgAnther  | 第二个右图标是否显示，默认不显示 
     
     
* initTvRight(String tvRightStr, int tvRightTextColor, int tvRightTextSize, boolean showTvRight) 
##### 修改标题栏右边文字
字段  | 说明 
---|---
tvRightStr  | 右文字内筒
tvRightTextColor  | 右文字字体颜色
tvRightTextSize  | 右文字字体字体大小
showTvRight  | 右文字是否显示，默认不显示

* initShowToolBar(boolean showTitle)
##### 设置是否显示标题
字段  | 说明 
---|---
showTitle  | 设置标题栏是否显示，默认显示


## 2.TabBar

TabBar继承自design包下的BottomNavigationView,
1. 新增方法disableShiftMode()取消偏移效果
2. 新增方法enableAnimation(boolean enable)设置是否取消item动画效果

##### xml文件下进行配置属性
属性|说明
---|---
menu|在menu文件夹下配置，具体可查看demo，里面可以配置图标和文字,其中item个数最多5个
itemIconTint|图标颜色，在drawable中配置选择和非选择颜色即可
itemTextColor|图标颜色，在drawable中配置选择和非选择颜色即可，同itemIconTint
itemBackground|item背景颜色，如果不需要动画效果可以设置为@null

##### 使用说明
1. 在xml文件中使用TabBar，配置好属性menu和选择状态drawable等
2. 在activity中初始化控件，并调用disableShiftMode()方法
3. 添加setOnNavigationItemSelectedListener回调监听方法，返回true


## 3.Dialog
#### 使用例子在 example module下的 uikit/dialog 依赖库在uikit/dialog
##### API说明

* setTitle(String title)
##### 修改dialog标题
字段  | 说明 
---|---  
title  | 标题文字，为空或者空的字符串时不显示标题


* setTitleColor(int color, int text_color)
##### 修改dialog标题
字段  | 说明 
---|---
color  | 标题背景颜色
text_color  | 标题文字颜色

* setMessage(String message)
##### 设置提示内容
字段  | 说明 
---|---  
message  | 内容文字，为空或者空的字符串时不显示内容
     
* setEdit_text(String hint_text_str, String edit_text_str)
##### 设置编辑框内容
字段  | 说明 
---|--- 
hint_text_str  | 编辑框提示文字
edit_text_str  | 编辑框文字，为空或者空的字符串时不显示编辑框
     
* setCancleText(String cancleText)
##### 设置取消按钮
字段  | 说明 
---|---
cancleText  | 取消按钮文字，为空或者空的字符串时不显示取消按钮

* setConfirmText(String confirmText)
##### 设置确定内容
字段  | 说明 
---|---  
confirmText  | 确定按钮文字

* setNoButton()
##### 设置按钮不显示

* setConfirmColor(int text_color)
##### 设置确定按钮字体颜色
字段  | 说明 
---|--- 
text_color  | 设置确定按钮颜色
     
* setCancelColor(int text_color)
##### 设置取消按钮字体颜色
字段  | 说明 
---|--- 
text_color  | 设置确定按钮颜色

* setRecyclerView(List<String> data, Context context)
##### 设置选择列表
字段  | 说明 
---|---  
data  | 数据
context  | 上下文

* setNoOnclickListener(onNoOnclickListener onNoOnclickListener)
##### 取消按钮的监听
字段  | 说明 
---|---  
onNoOnclickListener  | 取消按钮监听器
     
* setYesOnclickListener(onYesOnclickListener onYesOnclickListener)
##### 确定按钮的监听
字段  | 说明 
---|---  
onYesOnclickListener  | 确定按钮监听器
     
* setListOnclickListener(ListOnclickListener listOnclickListener)
##### 取消按钮的监听
字段  | 说明 
---|---   
listOnclickListener  | 列表点击监听器

##### 第三方依赖库
      recyclerview: "com.android.support:recyclerview-v7:${androidSupportLibVersion}",
      base_adapter: "com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.30",


## 4.PopupWindow


#### 使用例子在 example module下的 uikit/popupwindow 依赖库在uikit/popupwindow文件夹下
     
#### 使用说明
#####（1）实例化WYAPopupWindow，然后在项目中调用show方法显示
#####（2）API说明

* show(View view, int xoff, int yoff)

##### 显示popupwindow
字段  | 说明 
---- | -----   
view | 显示该view的下方 
xoff | 横向偏移量 
yoff | 纵向偏移量 

* setPopupWindowListOnclickListener(PopupWindowListOnclickListener popupWindowListOnclickListener)

##### 设置列表点击事件监听
字段  | 说明 
---- | -----   
popupWindowListOnclickListener | 列表点击监听回调



## 5.Toast


#### 使用例子在 example module下的 uikit/toast 依赖库在uikit/toast文件夹下
     
#### 使用说明
#####（1）直接调用WYAToast.showShort,showLong,showToastWithImg方法显示Toast
#####（2）API说明

* showShort(Context context, CharSequence message)

##### 短时间toast
字段  | 说明 
---- | -----   
context | 上下文 
message | Toast文本内容

* showLong(Context context, CharSequence message)

##### 长时间toast
字段  | 说明 
---- | -----   
context | 上下文 
message | Toast文本内容

* showToastWithImg(Context context, final String tvStr, final int imageResource, int gravity)

##### 长时间toast
字段  | 说明 
---- | -----   
context | 上下文 
tvStr | Toast文本内容
imageResource | Toast图片资源（R.mipmap.xxx）
gravity | Toast显示位子


## 6.SegmentControl
##### 说明： SegmentControl包下包含2个功能
* WYATabLayoutControl：design包下TabLayout控制器，lineWidth(final TabLayout layout)方法，利用反射技术，设置TabLayout的item下划线与文字同宽。以及clear(final TabLayout layout)清除设置的下滑长度。
* WYASegmentedView ： 用于分段式选择，只能点击不能滑动

##### WYASegmentedView属性说明
属性|说明
---|---
lineColor|中间横线颜色
lineSize|中间横线宽度
titleNormalColor|标题普通颜色
titleSelectColor|标题选择颜色
itemNormalBackground|单个item普通颜色
itemSelectBackground|单个item选择颜色
titleSize|标题大小
strokeWidth|边线的宽度
strokeColor|边线的颜色
radius|WYASegmentedView的圆角

##### WYASegmentedView使用方法
* setOnItemClickListener()设置item点击时间
* addTabs()添加标题，数据源List/Array


## 7.SearchBar


#### 使用例子在 example module下的 uikit/searchbar 依赖库在uikit/searchbar
     
#### 使用说明
#####（1）布局文件中添加com.wya.uikit.searchbar.WYASearchBar控件
#####（2）API说明

* setOnTextChangeListener(OnTextChangeListener onTextChangeListener)

##### 设置搜索框的监听
字段  | 说明 
---- | -----   
onTextChangeListener | 设置搜索框的监听

* setEditHint(String hint_text)
##### 设置搜索框的提示文本内容
字段  | 说明 
---- | -----   
hint_text | 搜索框的提示文本内容

* setEtSearchLeftImg(int left_res)
##### 设置编辑框左边的图片
字段  | 说明 
---- | -----   
left_res | 图片资源（R.mipmap.xx） 当为0的时候不显示 

* setOnClickCancelListener(OnClickCancelListener onClickCancelListener)
##### 设置取消按钮的监听
字段  | 说明 
---- | -----   
onClickCancelListener | 取消按钮的监听


## 8.Keyboard


#### 使用例子在 example module下的 uikit/keyboard 依赖库在uikit/keyboard
     
#### 使用说明
#####（1）实例化WYACustomNumberKeyboard
#####（2）API说明

* WYKCustomNumberKeyboard(@NonNull Context context, final ChooseInterface chooseInterface)

##### 实例化自定义数字软件盘
字段  | 说明 
---- | -----   
context | 上下文
chooseInterface | 按钮监听

## 9.PaginationView
使用说明:PaginationView包下包含2个View，PaginationBottomView和PaginationDot两个。
* 1.PaginationBottomView 分页选择器,方法和属性如下:

方法|说明
---|---
setAllNum(int num)|设置分页总页数
setOnPageButtonClickListener()|设置按钮监听事件
setPageSearchListener()|设置搜索某一页监听事件
setCurrentPage()|设置当前页数

属性|说明
----|---
textSize|文字大小
buttonWidth|按钮的宽度
buttonHeight|按钮的高度
textColor|普通文字的颜色
leftText|左边按钮的文本
rightText|右边按钮的文本
selectPageTextColor|当前页数的文本颜色

* 2.PaginationDot 分页小圆点，方法设属性如下:

方法|说明
---|---
setPointNumber()|设置小圆点个数
setUpWithViewPager()|和viewpager进行关联

属性|说明
----|---
dotNumber|小圆点数量
dotBackgroundResource|小圆点背景资源Id，这里必须使用有选中状态的资源文件，默认pagination_selector_dot_solid.xml和pagination_selector_dot_solid_dark.xml

注：这里可以看到有多个设置小圆点数量的方法或属性，优先级：viewpager中的fragment数量 > setPointNumber()方法 > 属性dotNumber，同时选择只选其中最高级的数量。

## 10.ChoiceMenu
一级二级分类选择
##### 说明
ChoiceMenu继承自PopupWindow的抽象类，创建ChoiceMenu对象时，构造方法有2个：
1. public ChoiceMenu(Context context, List<T> list1, List<List<T>> list2, @LayoutRes int firstId,@LayoutRes int secondId)
使用这个构造方法会创建二级分类 list1 第一级数据，list2 第二级数据，firstId 第一级中item的布局，secondId 第二级中item的数据
2. public ChoiceMenu(Context context, List<T> list1, @LayoutRes int firstId)
使用这个构造方法只会创建一级分类 参数如上

方法|说明
---|---
setValueFirst|抽象方法传递第一级上的数据
setValueSecond|抽象方法传递第二级上的数据
addLine|第一级添加分割线
addSecondLine|第二级添加分割线
notifyAdapterData|更新数据

在使用时，设置背景点击效果等，在setValueFirst和setValueSecond中进行。


## 11.BadgeView
消息提示
##### 说明
在目标View的指定位置设置消息提示，通过控制Gravity指定消息位置


方法|说明
---|---
setBadgeNum|设置数字类型红点
setBadgeText|设置文本类型红点
setMaxBadgeNum|设置默认最大数字，OmitMode省略模式为true时且大于该值显示省略
setOmitText|省略模式时的文本
setTextColor|设置文本字体颜色
setTextSize|设置文本字体大小
setBackgroundColor|设置背景颜色
setBackgroundDrawable|设置背景图片
setPadding|设置间隔
setGravity|设置位置
setOffset|设置位置偏移
bindToTarget|需要显示消息的View
update|更新显示状态



## 12.Button
自定义按钮
##### 说明
WYAButton继承Button, 通过设置点击的背景颜色、字体颜色、背景图片、按钮大小来得到所需要的按钮以及点击效果

方法|说明
---|---
setBackColor(int backColor)|设置按钮的背景色
setBackColorPress(int backColorPress)|设置按钮被按下时的背景色
setBackGroundDrawable(Drawable backGroundDrawable)|设置按钮的背景图片
setBackGroundDrawablePress(Drawable backGroundDrawablePress)|设置按钮被按下时的背景图片
setTextColor(int textColor)|设置文字的颜色
setTextColorPress(int textColorPress)|设置按钮被按下时文字的颜色
setFillet(boolean fillet)|设置按钮是否设置圆角或者圆形等样式
setRadius(float radius)|置圆角按钮的角度
setSize(Context context, int type, int height, int width)|设置按钮大小（正常/小按钮/自定义大小）



## 13.Gallery
相册浏览
##### 使用说明
使用第三方库：Glide加载图片

> implementation 'com.github.bumptech.glide:glide:4.8.0'
>
>annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'

GalleryCreator这个类控制进入相册预览界面

GalleryCreator.create(Activity/Fragment).openPreviewGallery(position,List<string>)

1. create方法中传入Activity或fragment创建GalleryCreator实例
2. openPreviewGallery方法是进入预览页面


## 14.CardView

#### 使用例子在 example module下的 uikit/customitems/cardview 依赖库在uikit/customitems/cardview文件夹下
卡片布局
##### 使用说明
#####  1、布局使用
    <com.wya.uikit.customitems.WYACardView
        android:id="@+id/wya_card_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:backColor="#999999"
        app:titleTextColor="#ffffff"
        app:rightTextColor="@color/red"
        app:assistTextColor="@color/blue"
        app:contentTextColor="@color/green"
        app:wyaCardRadius="20"
        android:layout_margin="@dimen/dp_10">
    </com.wya.uikit.customitems.WYACardView>
    
#####  2、方法说明
方法|说明
---|---
setTitleTextColor(ColorStateList titleTextColor)|设置标题字体颜色
setRightTextColor(ColorStateList rightTextColor)|设置右边字体颜色
setContentTextColor(ColorStateList contentTextColor)|设置内容字体颜色
setAssistTextColor(ColorStateList assistTextColor)|设置辅助字体颜色
setRadius(float radius)|设置圆角的角度
setBackColor(int backColor)|设置背景的背景色
setTitle(String title)|设置卡片标题

## 15.Grid
列表布局，动态改变列数
#### 使用例子在 example module下的 uikit/customitems/grid 依赖库在uikit/customitems/grid 文件夹下
##### wya_grid_item Grid布局文件

## 16.InputItem
#### 使用例子在 example module下的 uikit/customitems/inputitem 依赖库在uikit/customitems/inputitem 文件夹下

##### 使用说明
#####  1、布局使用
    <com.wya.uikit.customitems.WYAInputItem
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         app:backColor="#ffffff"
         app:canEdit="false"
         app:contentHint="编辑文本提示"
         app:leftImage="@mipmap/icon_nav_more"
         app:leftTvText="左边"
         app:lineViewColor="#000000"
         app:rightCanEdit="false"
         app:rightImage="@mipmap/icon_right_arrow"
         app:rightText="右边文字"
         app:rightTextColor="#333333"></com.wya.uikit.customitems.WYAInputItem>
    
#####  2、方法说明
方法|说明
---|---
setLeftBackgroundDrawable(Drawable leftDrawable)|设置左边的图片
setRightBackgroundDrawable(Drawable rightDrawable)|设置右边的图片
setLeftText(String leftText)|设置左边文字内容
setLeftTextColor(ColorStateList leftTextColor)|设置左边文字的颜色
setContentText(String contentText)|设置编辑框内容
setContentHint(String contentHint)|设置编辑框提示语
setContentEdit(boolean canEdit)|是否可以编辑
setContentTextColor(ColorStateList contentTextColor)|设置编辑框文字
setRightText(String rightText)|设置右边编辑框内容
setRightHint(String rightHint)|设置右边编辑框提示语
setRightEdit(boolean rightCanEdit)|右边编辑框是否可以编辑
setRightTextColor(ColorStateList rightTextColor)|设置右边编辑框文字
setLineViewColor(String lineViewColor) |设置分隔线颜色
getLeftText()| 获取左边文字内容
getContentText()|获取中间编辑框内容
getRightText()|获取右边编辑框内容

## 17.ImagePicker
说明：通过ImagePickerCreator类使用图片选择器

方法|说明
---|---
ImagePickerCreator(Activity/Fragment)|构造方法,参数可以传入fragment或Activity
maxImages(int num) |设置最大选取图片数量,默认是1
forResult(int requestCode)|结果回调onActivityResult requestCode

onActivityResult中返回结果中获取选中的图片list

key是PickerConfig.IMAGE_SELECTED；
value是Serializable类型的List<LocalImage>

## 18.Stepper
数字加减控制器
#### 使用例子在 example module下的 uikit/customitems/stepper 依赖库在uikit/customitems/stepper 文件夹下

##### 使用说明
#####  1、布局使用
    <com.wya.uikit.stepper.WYAStepper
              android:id="@+id/stepper"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              app:addDrawable="@mipmap/add"
              app:addDrawablePress="@mipmap/add_press"
              app:max_num="120"
              app:value="0"
              app:min_num="0"
              app:reduceDrawable="@mipmap/reduce"
              app:reduceDrawablePress="@mipmap/reduce_press"/>

    
#####  2、方法说明
方法|说明
---|---
setReduceBackgroundDrawable(Drawable reduceDrawable)|设置减号按钮默认背景图片
setReduceBackgroundDrawablePress(Drawable reduceDrawablePress)|设置减号按钮点击图片
setAddBackgroundDrawable(Drawable addDrawable)|设置加号按钮默认背景图片
setAddBackgroundDrawablePress(Drawable reduceDrawablePress)|设置加号按钮点击图片
setValue(int value)|设置左边的图片
getValue()|获取当前值
setMax_num(int max_num)|设置最大值
setMin_num(int min_num)|设置最小值
getMin_num()|获取最小值
getMax_num()|获取最大值

## 19.Progress
进度条，环形进度条和条形进度条
#### 使用例子在 example module下的 uikit/customitems/stepper 依赖库在uikit/customitems/stepper 文件夹下
##### （1）条形进度条用系统默认自带的ProgressBar，不做使用说明
##### （2）环形进度条使用说明
#####  1、布局使用
    <com.wya.uikit.progress.WYAProgress
          android:id="@+id/wya_progress"
          android:layout_width="200dp"
          android:layout_height="200dp"
          android:layout_gravity="center_horizontal"
          app:circleThickness="10dp"
          app:progressArgbColor="true"
          app:progressEndColor="#0000ff"
          app:progressStartColor="#ff0000"
          app:smallCircleEnable="false" />

#####  2、方法说明
方法|说明
---|---
setMaxProgress(double maxProgress)|设置进度的最大值
setCurrentProgress(double currentProgress)|设置进度值
setAnimation(double start, double end)|为进度设置动画（起始位置到结束位置的动画）
setProgressArgbColor(boolean progressArgbColor)|颜色是否argb变化
setProgressCircleColor(int progressCircleColor)|进度条的颜色
setProgressStartColor(int progressCircleColor)|进度条起始颜色
setProgressEndColor(int progressCircleColor)|进度条结束颜色
setAnimationDuration(long animationDuration)|设置动画时长
setCircleThickness(float circleThickness)|圆环厚度
setSmallCircleEnable(boolean smallCircleEnable)|设置是否圆角











