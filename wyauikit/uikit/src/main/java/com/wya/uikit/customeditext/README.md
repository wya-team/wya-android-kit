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
hintTextStr|提示文字内容|String|-
editTextStr|编辑框文字内容|String|-
hintEditStr|编辑框文本提示内容|String|-
maxNum|最大输入文字|int|100


## 用法说明
- 布局引用
```
      <com.wya.uikit.customeditext.WYACustomEditText
             android:layout_width="match_parent"
             android:layout_height="wrap_content"
             android:layout_margin="@dimen/dp_10"
             app:editTextStr="输入内容"
             app:hintTextStr=""
             app:hintEditStr="我是"
             app:maxNum="20">
         </com.wya.uikit.customeditext.WYACustomEditText>
```

- 方法

方法|说明
---|---
setMyBackgroundDrawable(GradientDrawable gradientDrawable)|设置编辑框背景图片
setHintEditStr(String hintEditStr)|设置编辑框文本提示内容
setEditTextStr(String editTextStr)|设置编辑框文本
setHintTextStr(String hintTextStr)|设置提示文本
setEditTextColor(ColorStateList editTextColor)|设置编辑文本字体颜色
setHintEditColor(ColorStateList hintEditColor)|设置编辑文本提示字体颜色
setCountTextColor(ColorStateList countTextColor)|设置统计字体颜色
getEditTextStr()|获取输入文本