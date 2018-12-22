# TabBar

## 功能说明
底部导航栏
TabBar继承自design包下的BottomNavigationView,
1. 新增方法disableShiftMode()取消偏移效果
2. 新增方法enableAnimation(boolean enable)设置是否取消item动画效果

## 属性说明

属性|说明
---|---
menu|在menu文件夹下配置，具体可查看demo，里面可以配置图标和文字,其中item个数最多5个
itemIconTint|图标颜色，在drawable中配置选择和非选择颜色即可
itemTextColor|图标颜色，在drawable中配置选择和非选择颜色即可，同itemIconTint
itemBackground|item背景颜色，如果不需要动画效果可以设置为@null

## 用法说明

1. 在xml文件中使用TabBar，配置好属性menu和选择状态drawable等
2. 在activity中初始化控件，并调用disableShiftMode()方法
3. 添加setOnNavigationItemSelectedListener回调监听方法，返回true

````
 <com.wya.uikit.tabbar.WYATabBar
        android:id="@+id/tab"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        app:itemBackground="@null"
        app:itemIconTint="@drawable/navigation_select"
        app:itemTextColor="@drawable/navigation_select"
        app:menu="@menu/navigation" />
````