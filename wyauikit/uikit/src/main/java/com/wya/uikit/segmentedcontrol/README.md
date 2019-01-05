# SegmentControl

## 功能说明
分段控制器
* WYASegmentedView ： 用于分段式选择，只能点击不能滑动

## 属性说明
WYASegmentedView属性说明

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

## 用法说明

1. WYASegmentedView使用方法
* setOnItemClickListener()设置item点击时间
* addTabs()添加标题，数据源List/Array
````
<com.wya.uikit.segmentedcontrol.WYASegmentedView
        android:id="@+id/segment_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:titleSelectColor="@color/white"
        app:titleNormalColor="@color/red"
        app:titleSize="14sp"
        app:strokeColor="@color/red"
        app:strokeWidth="2dp"
        app:radius="5dp"
        app:itemSelectBackground="@color/red"
        app:itemNormalBackground="@color/white"
        app:lineColor="@color/red"
        app:lineSize="2dp" />
````

