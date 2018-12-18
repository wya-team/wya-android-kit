# Banner

## 功能说明
轮播图

## 用法说明
方法|说明
---|---
`setData(List<T> data)`|设置轮播的item数据
start(boolean isAuto)|自动轮播启动，参数代表是否自动轮播，默认自动轮播，该方法需在最后调用
setOnItemListener(OnBannerListener<T> listener)|设置item监听，在回调方法中进行数据处理
setUpdateTime(long updateTime)|设置滚动时间间距
setDotVisible(boolean isVisible)|是否显示下面小圆点

- `WYABanner<T> `类的泛型代表数据类型，创建WYABanner对象后必须设置数据。

- 在布局文件中添加

```
<com.wya.uikit.banner.WYABanner
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

- 在activity等文件中使用
````
mWYABanner.setData(data)
	.setUpdateTime(1000)
	.setDotVisible(false)
	.start(true);
````