# KeyBoard
## 功能说明
- 数字软件盘 WYACustomNumberKeyboardView继承KeyboardView，通过WYACustomNumberKeyboard实现键盘的监听以及随机数字键盘的切换

## 属性说明

## 用法说明
- 布局引用（放在底部）
```
    <com.wya.uikit.keyboard.WYACustomNumberKeyboardView
        android:id="@+id/keyboard_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:focusable="true"
        android:focusableInTouchMode="true"
        android:keyBackground="@drawable/bg_keyboard_view"
        android:keyPreviewOffset="0dp"
        android:keyTextColor="#000"
        android:paddingTop="0dp"
        android:shadowColor="#fff"
        android:shadowRadius="0.0" />
```

- 方法
keyboard = new WYACustomNumberKeyboard(KeyboardExampleActivity.this);//获取到keyboard对象

方法|说明
---|---
attachTo(editext, true)|eiditext绑定keyboard并且显示，true表示随机数字
setOnOkClick()|确定按钮的监听
setOnCancelClick()|隐藏软件盘的按钮的监听
hideKeyBoard()|隐藏软件盘







