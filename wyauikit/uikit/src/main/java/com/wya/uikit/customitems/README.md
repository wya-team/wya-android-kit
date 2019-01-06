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
radius|卡片圆角大小|float|0

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
               app:radius="5dp"
               android:layout_margin="@dimen/dp_10">
           </com.wya.uikit.customitems.WYACardView>
```

- 方法

方法|说明
---|---
setTitleTextColor|设置标题字体颜色
setRightTextColor|设置右边字体颜色
setContentTextColor|设置内容字体颜色
setAssistTextColor|设置辅助字体颜色
setRadius|设置圆角的角度
setBackColor|设置背景的背景色
setTitle|设置卡片标题


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
leftText|左边文字内容|String|null
rightText|右边文字内容|String|null
lineColor|分割线颜色|int|-
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
                 app:leftText="左边"
                 app:lineColor="#000000"
                 app:contentTextColor="@color/black"
                 app:rightCanEdit="false"
                 app:rightImage="@mipmap/icon_right_arrow"
                 app:rightText="右边文字"
                 app:rightTextColor="#333333"></com.wya.uikit.customitems.WYAInputItem>
```

- 方法

方法|说明
---|---
setLeftBackgroundDrawable|设置左边的图片
setRightBackgroundDrawable|设置右边的图片
setLeftText|设置左边文字内容
setLeftTextColor|设置左边文字的颜色
setContentText|设置编辑框内容
setContentHint|设置编辑框提示语
setContentEdit|是否可以编辑
setContentTextColor|设置编辑框文字颜色
setRightText|设置右边编辑框内容
setRightHint|设置右边编辑框提示语
setRightEdit|右边编辑框是否可以编辑
setRightTextColor|设置右边编辑框文字
setLineColor |设置分隔线颜色
getLeftText| 获取左边文字内容
getContentText|获取中间编辑框内容
getRightText|获取右边编辑框内容




