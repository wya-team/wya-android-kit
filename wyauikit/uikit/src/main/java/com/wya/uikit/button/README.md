# Button
## 功能说明
- WYAButton继承Button, 通过设置点击的背景颜色、字体颜色、背景图片、按钮大小来得到所需要的按钮以及点击效果

## 属性说明
属性 | 说明 | 类型 | 默认值
---|---|---|---
backGroundColor|按钮背景颜色|int|-
backGroundColorPress|按钮按下时背景颜色|int|-
fillet|是否设置圆角或者圆形等样式|boolean|false
textColor|按钮文字的颜色|ColorStateList|null
textColorPress|按钮按下时文字的颜色|ColorStateList|null
radius|按钮圆角度数|float|0
backGroundDrawable|按钮背景图片|Drawable|null
backGroundDrawablePress|按钮按下时背景图片|Drawable|null

## 用法说明
- 布局引用
```
        <com.wya.uikit.button.WYAButton
               android:layout_width="match_parent"
               android:layout_height="40dp"
               android:layout_margin="10dp"
               android:text="硬件（hardware）"
               android:textAllCaps="false"
               app:backGroundColor="#FF0000"
               app:fillet="true"
               app:textColor="#ffffff"
               app:textColorPress="#50ffffff"
               app:radius="10dp" />
```

- 方法

方法|说明
---|---
setBackColor|设置按钮的背景色
setBackColorPress|设置按钮被按下时的背景色
setBackGroundDrawable|设置按钮的背景图片
setBackGroundDrawablePress|设置按钮被按下时的背景图片
setTextColor|设置文字的颜色
setTextColorPress|设置按钮被按下时文字的颜色
setFillet|设置按钮是否设置圆角或者圆形等样式
setRadius|置圆角按钮的角度





