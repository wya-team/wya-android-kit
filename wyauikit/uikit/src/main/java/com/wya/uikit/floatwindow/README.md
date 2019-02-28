# floatwindow
## 功能说明
- 悬浮窗，用于webview返回后在应用内存在悬浮窗

## 属性说明

## 用法说明

1. 首先需要检查是否开启浮窗权限PermissionUtil.hasPermission(this)，如果没有需要去设置中开启；

2. 首次进入需要设置右下角关闭的布局
```
     FloatWindow
             .with(getApplicationContext())
             .setTag("cancel2")
             .setView(R.layout.layout_window)
             .setCancelParam2(320)
             .setMoveType(MoveType.inactive, 0, 0)
             .setDesktopShow(false)
             .build();
```
3. 再设置悬浮窗，并设置悬浮窗点击事件
```
                FloatWindow
                        .with(getApplicationContext())
                        .setTag("old")
                        .setView(imageView)
                        .setMoveType(MoveType.slide, 0, 0)
                        .setWidth(60)
                        .setFilter(false, FloatWindowExampleActivity.class)
                        .setHeight(60)
                        .setX(Screen.width, 0.8f)
                        .setY(ScreenUtil.getScreenHeight(this) / 3)
                        .setParentHeight(ScreenUtil.getScreenHeight(this))
                        .setMoveStyle(300, new AccelerateInterpolator())
                        .setDesktopShow(false)
                        .build();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), FloatWindowExampleActivity.class));
                    }
                });
```




