# Progress
## 功能说明
- 进度条，环形进度条和条形进度条。（注：条形进度用用系统自带的）

## 属性说明
属性 | 说明 | 类型 | 默认值
---|---|---|---
circleThickness|圆环厚度|float|0
progressArgbColor|颜色渐变开关|boolean|false
progressEndColor|结束颜色|int|-
progressStartColor|开始颜色|int|-
smallCircleEnable|是否头部圆角|boolean|false

## 用法说明
- 布局引用
```
          <com.wya.uikit.progress.WYAProgress
                   android:id="@+id/wya_progress"
                   android:layout_width="200dp"
                   android:layout_height="200dp"
                   android:layout_gravity="center_horizontal"
                   app:circleThickness="10dp"
                   app:progressArgbColor="true"
                   app:progressEndColor="#0000ff"
                   app:progressStartColor="#ff0000"
                   app:smallCircleEnable="false" />
```

- 方法

方法|说明
---|---
setMaxProgress|设置进度的最大值
setCurrentProgress|设置进度值
setAnimation|为进度设置动画（起始位置到结束位置的动画）
setProgressArgbColor|颜色是否argb变化
setProgressCircleColor|进度条的颜色
setProgressStartColor|进度条起始颜色
setProgressEndColor|进度条结束颜色
setAnimationDuration|设置动画时长
setCircleThickness|圆环厚度
setSmallCircleEnable|设置是否圆头
