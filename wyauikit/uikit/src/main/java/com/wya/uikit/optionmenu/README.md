# OptionMenu

## 功能说明
一级二级分类选择


## 用法说明

方法|说明
---|---
setValueFirst|抽象方法传递第一级上的数据
setValueSecond|抽象方法传递第二级上的数据
addLine|第一级添加分割线
addSecondLine|第二级添加分割线
notifyAdapterData|更新数据
`OptionMenu(Context context, List<T> list1,  int firstId)`|一级分类选择
`OptionMenu(Context context, List<T> list1, List<List<T>> list2, int firstId, int secondId)`|两级分类选择

1. 创建一个`OptionMenu<T>`对象,泛型代表里面的数据类型,需要实现setValueFirst，setValueSecond这两个抽象方法。在里面进行item的处理
2. choiceMenu.showAsDropDown(view)，设置选择器在view的下面
