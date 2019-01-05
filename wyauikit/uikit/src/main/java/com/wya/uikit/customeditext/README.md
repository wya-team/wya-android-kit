# CustomEditText
## 功能说明
- WYACustomEditText自定义文本输入框，带数字统计

## 属性说明
属性 | 说明 | 类型 | 默认值
---|---|---|---
gradientDrawable|编辑框背景颜色|GradientDrawable|R.drawable.white_r5_bg
countTextColor|统计文字颜色|ColorStateList|#000000
hintTextColor|提示文字颜色|ColorStateList|#000000
hintEditColor|编辑框提示文字颜色|ColorStateList|-
countTextColor|编辑框文字颜色|ColorStateList|#999999
hintText|提示文字内容|String|-
editText|编辑框文字内容|String|-
hintEditText|编辑框文本提示内容|String|-
maxNum|最大输入文字|int|100


## 用法说明
- 布局引用
```
      <com.wya.uikit.customeditext.WYACustomEditText
             android:layout_width="match_parent"
             android:layout_height="wrap_content"
             android:layout_margin="@dimen/dp_10"
             app:editText="输入内容"
             app:hintText=""
             app:hintEditText="我是"
             app:maxNum="20">
         </com.wya.uikit.customeditext.WYACustomEditText>
```

- 方法

方法|说明
---|---
setBackgroundDrawable|设置编辑框背景图片
setHintEditText|设置编辑框文本提示内容
setEditText|设置编辑框文本
setHintText|设置提示文本
setEditTextColor|设置编辑框文本字体颜色
setHintTextColor|设置提示字体颜色
setHintEditColor|设置编辑文本提示字体颜色
setCountTextColor|设置统计字体颜色
getEditText|获取输入文本内容