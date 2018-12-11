# Stepper
## 功能说明
- 数字加减控制器

## 属性说明
属性 | 说明 | 类型 | 默认值
---|---|---|---
max_num|最大值|int|100
min_num|最小值|int|0
value|当前值|int|min_num
addDrawable|加号图片|Drawable|null
addDrawablePress|加号点击后的图片|Drawable|null
reduceDrawable|减号图片|Drawable|null
reduceDrawablePress|减号点击后的图片|Drawable|null

## 用法说明
- 布局引用
```
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
```

- 方法

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




