# CustomItems

# 1、WYACardView
## 功能说明
- 自定义item,卡片式（WYACardView）

## 属性说明
属性 | 说明 | 类型 | 默认值
---|---|---|---
backColor|item背景颜色|int|-
titleTextColor|item标题颜色|ColorStateList|null
rightTextColor|标题右边文字颜色|ColorStateList|null
assistTextColor|辅助说明文字颜色|ColorStateList|null
contentTextColor|内容文字颜色|ColorStateList|null
wyaCardRadius|卡片圆角大小|int|0

## 用法说明
- 布局引用
```
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
```

- 方法

方法|说明
---|---
setTitleTextColor(ColorStateList titleTextColor)|设置标题字体颜色
setRightTextColor(ColorStateList rightTextColor)|设置右边字体颜色
setContentTextColor(ColorStateList contentTextColor)|设置内容字体颜色
setAssistTextColor(ColorStateList assistTextColor)|设置辅助字体颜色
setRadius(float radius)|设置圆角的角度
setBackColor(int backColor)|设置背景的背景色
setTitle(String title)|设置卡片标题


# 2、WYAInputItem

## 功能说明
- 自定义常见item,常用于我的页面布局，以展示为主


## 属性说明
属性 | 说明 | 类型 | 默认值
---|---|---|---
backColor|按钮背景颜色|int|-
canEdit|是否可以编辑|boolean|false
fillet|是否设置圆角或者圆形等样式|boolean|false
contentHint|编辑文本框提示内容|String|null
contentTextColor|中间文字内容颜色|int|-
leftImage|左边的图片|Drawable|null
rightImage|右边的图片|Drawable|null
leftTvText|左边文字内容|String|null
rightText|右边文字内容|String|null
lineViewColor|分割线颜色|int|-
rightCanEdit|右边辅助文字是否可以编辑|boolean|false
rightTextColor|右边辅助文字颜色|int|-

## 用法说明
- 布局引用
```
          <com.wya.uikit.customitems.WYAInputItem
                 android:layout_width="match_parent"
                 android:layout_height="wrap_content"
                 app:backColor="#ffffff"
                 app:canEdit="false"
                 app:contentHint="编辑文本提示"
                 app:leftImage="@mipmap/icon_nav_more"
                 app:leftTvText="左边"
                 app:lineViewColor="#000000"
                 app:contentTextColor="@color/black"
                 app:rightCanEdit="false"
                 app:rightImage="@mipmap/icon_right_arrow"
                 app:rightText="右边文字"
                 app:rightTextColor="#333333"></com.wya.uikit.customitems.WYAInputItem>
```

- 方法

方法|说明
---|---
setLeftBackgroundDrawable(Drawable leftDrawable)|设置左边的图片
setRightBackgroundDrawable(Drawable rightDrawable)|设置右边的图片
setLeftText(String leftText)|设置左边文字内容
setLeftTextColor(ColorStateList leftTextColor)|设置左边文字的颜色
setContentText(String contentText)|设置编辑框内容
setContentHint(String contentHint)|设置编辑框提示语
setContentEdit(boolean canEdit)|是否可以编辑
setContentTextColor(ColorStateList contentTextColor)|设置编辑框文字颜色
setRightText(String rightText)|设置右边编辑框内容
setRightHint(String rightHint)|设置右边编辑框提示语
setRightEdit(boolean rightCanEdit)|右边编辑框是否可以编辑
setRightTextColor(ColorStateList rightTextColor)|设置右边编辑框文字
setLineViewColor(String lineViewColor) |设置分隔线颜色
getLeftText()| 获取左边文字内容
getContentText()|获取中间编辑框内容
getRightText()|获取右边编辑框内容




