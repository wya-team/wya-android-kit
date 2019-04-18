# PickerView

## 功能说明
三级联动，时间选择器

## 用法说明
1. CustomPickerView 常用的三级联动选择器样式

方法|说明
---|---
`setData(List<T> option1Items, List<List<T>> option2Items,List<List<List<T>>> option3Items)`|设置联动数据，可选择最多传3个参数最少一个
`CustomPickerView setNPData(List<T> option1Items, List<T> option2Items, List<T> option3Items)`|设置不联动数据，最多3个最少一个
setCycle(boolean isCycle)|设置数据是否循环，默认循环
setTitle(String title)|设置弹框的标题
setCancelTextColor(int color)|设置取消按钮颜色
setSureTextColor(int color)|设置确定按钮颜色
setTitleTextColor(int color) |设置标题文字颜色
setDividerColor(int color)|设置分割线颜色
setTitleContentColor(int color)|设置title这一块背景色
setTextSize(float textSize) |设置文字大小
setOutTextColor(int color)|设置滚动中外部文字大小
setCenterTextColor(int color)|这种选择的文字大小

接口OnChooseItemListener在构造方法中实现，其中的方法itemSelected(int position1, int position2, int position3);返回选择的三个位置。
   - 提供了OptionsPickerView布局，可以直接在布局文件中使用，其中的方法和CustomPickerView中的方法相同。
-    滚动数据为泛型，如果是个对象，需要实现IPickerViewData接口，在方法getPickerViewText中返回需要展示的字段。

2. CustomTimePicker常用时间选择器样式

方法|说明
---|---
setTitle(String title)|设置弹框的标题
setCancelTextColor(int color)|设置取消按钮颜色
setSureTextColor(int color)|设置确定按钮颜色
setTitleTextColor(int color) |设置标题文字颜色
setDividerColor(int color)|设置分割线颜色
setTitleContentColor(int color)|设置title这一块背景色
setTextSize(float textSize) |设置文字大小
setOutTextColor(int color)|设置滚动中外部文字大小
setCenterTextColor(int color)|这种选择的文字大小
setHourSpace(int space)|设置小时时间间隔
setMinuteSpace(int space)|设置分钟时间间隔
setSecondSpace(int space)|设置秒时间间隔
setType(boolean[] type)|设置年月日时分秒滚动布局是否显示，type数组长度为6，默认都是true
setRangeTime(Calendar start, Calendar end)|设置滚动的起止时间，默认当前时间上下100年
setSelectDate(Calendar selectDate)|设置选中时间，默认当前时间，如设置必须在起止时间之间且必须在setRangeTime之前调用


- CustomTimePicker构造方法中参数OnTimePickerSelectedListener必须传，回调方法selected(Date date)返回选择的时间类型为date。
- 提供了TimePickerView布局，可以直接在布局文件中使用，其中的方法和TimePickerView中的方法相同.
