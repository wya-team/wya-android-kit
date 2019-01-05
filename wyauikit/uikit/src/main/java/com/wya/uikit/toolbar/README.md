# ToolBar
## 功能说明
- 标题栏,沉浸式颜色根据标题颜色而改变

## 属性说明

属性 | 说明 | 类型 | 默认值
--- | ---  |  ---  |  ---
show | 是否显示标题栏 | boolean | true
backgroundColor | 标题栏颜色 | int | Color.parseColor("#ffffff ")
isLight | 是否是明亮的标题栏  | boolean | false
title | 标题文字 | String | ""
titleSize | 标题大小 | int | 16
titleColor | 标题颜色 |  int | Color.parseColor("#000000")
showLeftText | 左边文字是否显示 | boolean | false
leftText | 左边文字 | String | ""
leftTextSize | 左边文字大小 |int| 14
leftTextColor | 左边文字颜色 |int|  Color.parseColor("#000000")
showLeftIcon | 是否显示左图标 | boolean | true
leftIcon | 左边图片资源 | int | -
showFirstRightText | 显示右边第一个文字| boolean | false
firstRightText | 右边第一个文字内容 | String| ""
firstRightTextColor | 右边第一个文字顏色 |  int |  Color.parseColor("#000000")
firstRightTextSize | 右边第一个文字大小 |  int |  14
showSecondRightText | 是否显示右边第二个文字| boolean | false
secondRightText | 右边第二个文字内容 | String| ""
secondRightTextColor | 右边第二个文字顏色 |  int |  Color.parseColor("#000000")
secondRightTextSize | 右边第二个文字大小 |  int |  14
showFirstRightIcon | 是否显示右边第一张图片| boolean | false
firstRightIcon | 右边第一张图片| int | -
showSecondRightIcon | 是否显示右边第二张图片 | boolean | false
secondRightIcon | 右边第二章图片资源 | int | -


## 用法说明
- 项目中BaseActivity继承BaseToolBarActivity添加标题栏ToolBar

- 方法

方法|说明
---|---
setTitle | 设置标题内容
setTitleColor | 设置标题颜色
setTitleSize | 设置标题字体大小
setLeftText | 设置标题左边文字内容
setLeftTextSize | 设置标题左边文字大小
setLeftTextColor | 设置标题左边文字颜色
showLeftText | 设置是否显示左边文字
setLeftIcon | 设置标题左边图标
showLeftIcon | 设置标题左边图标显示
showFirstRightText | 设置右边第一文字显示
setFirstRightText | 设置右边第一文字内容
setFirstRightTextColor | 设置右边第一文字颜色
setFirstRightTextSize | 设置右边第一文字大小
showSecondRightText | 设置右边第二文字显示
setSecondRightText | 设置右边第二文字内容
setSecondRightTextColor | 设置右边第二文字颜色
setSecondRightTextSize | 设置右边第二文字大小
setFirstRightIcon | 设置标题右边第一个图标
showFirstRightIcon | 设置标题右边第一个图标显示
setSecondRightIcon | 设置标题右边第二个图标
showSecondRightIcon | 设置标题右边第二个图标显示
setLeftIconClickListener | 设置左边图标点击事件监听
setLeftTextClickListener | 设置左边文字点击事件监听
setRightFirstTextClickListener | 设置右边第一个文字点击事件监听
setRightSecondTextClickListener | 设置右边第二个文字点击事件监听
setRightFirstIconClickListener | 设置右边第一个图片点击事件监听
setRightSecondIconClickListener | 设置右边第二个图片点击事件监听
setRightSecondIconLongClickListener | 设置右边第二个图片长按点击事件监听


