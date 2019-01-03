# Drawerlayout 
## 功能说明
- 左右抽屉,使用体统自带的DrawerLayout

## 属性说明

## 用法说明
- 布局引用
```
     <android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
         xmlns:app="http://schemas.android.com/apk/res-auto"
         xmlns:tools="http://schemas.android.com/tools"
         android:id="@+id/main_drawer_layout"
         android:layout_width="match_parent"
         android:layout_height="match_parent"
         android:background="@color/f4f4f4">
         <!-- 下层显示的主要内容 -->
         <LinearLayout
             android:layout_width="match_parent"
             android:layout_height="match_parent"
             android:background="@android:color/transparent">
     
         </LinearLayout>
         
         <!-- 左侧滑动栏 -->
         <RelativeLayout
             android:id="@+id/main_left_drawer_layout"
             android:layout_width="240dp"
             android:layout_height="match_parent"
             android:layout_gravity="left"
             android:background="@color/red"></RelativeLayout>
         <!-- 右侧滑动栏 -->
     
         <RelativeLayout
             android:id="@+id/main_right_drawer_layout"
             android:layout_width="240dp"
             android:layout_height="match_parent"
             android:layout_gravity="right"
             android:background="@color/primary_color"></RelativeLayout>
     
     </android.support.v4.widget.DrawerLayout>
```

- 方法

方法|说明
---|---
addDrawerListener(@NonNull DrawerLayout.DrawerListener listener)|添加滑动监听
openDrawer(int gravity)|设置打开抽屉，左边或右边





