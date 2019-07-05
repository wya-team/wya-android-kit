# PhotoView

## 功能说明
图片浏览器

## 用法说明
- 代码用法
Application中初始化（先加TestImageLoader copy到自己的项目中）
```
 ZoomMediaLoader.getInstance().init(new TestImageLoader());
```

```
         GPreviewBuilder.from(this)
                .setData(imageItem)
                .setCurrentIndex(position)
                .setType(GPreviewBuilder.IndicatorType.Dot)
                .setSingleFling(true)
                .start();
```

- 方法

方法|说明
---|---
setData|设置数据源
setCurrentIndex|默认打开图片位置
setType|指示器类型，包含底部圆点和数字两种形式
setSingleFling|设置超出内容点击退出（黑色区域）
setUserFragment|设置自己的Fragment类
setDrag|设置图片禁用拖拽返回
setSingleShowType|是否设置为一张图片时 显示指示器  默认显示
setDuration|设置动画时长
setFullscreen|设置是否全屏