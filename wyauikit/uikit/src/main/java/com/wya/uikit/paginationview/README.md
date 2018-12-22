# PaginationView

## 功能说明
分页选择器(页码)，分页小圆点

## 属性说明
1. PaginationBottomView 分页选择器

属性|说明
----|---
textSize|文字大小
buttonWidth|按钮的宽度
buttonHeight|按钮的高度
textColor|普通文字的颜色
leftText|左边按钮的文本
rightText|右边按钮的文本
selectPageTextColor|当前页数的文本颜色

2. PaginationDot 分页小圆点

属性|说明
----|---
dotNumber|小圆点数量
dotBackgroundResource|小圆点背景资源Id，这里必须使用有选中状态的资源文件，默认pagination_selector_dot_solid.xml和pagination_selector_dot_solid_dark.xml


## 用法说明
使用说明:PaginationView包下包含2个View，PaginationBottomView和PaginationDot两个。
1. PaginationBottomView 分页选择器

方法|说明
---|---
setAllNum(int num)|设置分页总页数
setOnPageButtonClickListener()|设置按钮监听事件
setPageSearchListener()|设置搜索某一页监听事件
setCurrentPage()|设置当前页数

````
 <com.wya.uikit.paginationview.WYAPaginationBottomView
        android:id="@+id/pagination_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/black"
        app:buttonHeight="20dp"
        app:buttonWidth="50dp"
        app:leftText="上一页"
        app:rightText="下一页"
        app:selectPageTextColor="@color/blue"
        app:textColor="@color/black"
        app:textSize="13sp" />
````
2. PaginationDot 分页小圆点

方法|说明
---|---
setPointNumber()|设置小圆点个数
setUpWithViewPager()|和viewpager进行关联

````
<com.wya.uikit.paginationview.WYAPaginationDot
        android:id="@+id/pagination_dot"
        android:layout_width="match_parent"
        android:layout_height="40dp"
        android:layout_alignParentBottom="true"
        android:background="@color/blue"
        app:dotBackgroundResource="@drawable/pagination_selector_dot"
        app:dotNumber="2" />
````


注：这里可以看到有多个设置小圆点数量的方法或属性，优先级：viewpager中的fragment数量 > setPointNumber()方法 > 属性dotNumber，同时选择只选其中最高级的数量。
