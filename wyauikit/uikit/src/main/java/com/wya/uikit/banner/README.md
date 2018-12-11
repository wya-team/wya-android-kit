# Banner

## 功能说明
轮播图

## 用法说明
方法|说明
---|---
`setData(List<T> data)`|设置轮播的item数据
setAutoPlay(boolean auto)|设置是否自动轮播，默认true
setOnItemListener(OnBannerListener<T> listener)|设置item监听，在回调方法中进行数据处理
- `WYABanner<T> `类的泛型代表数据类型，创建WYABanner对象后必须设置数据。

- 在布局文件中添加

```
<com.wya.uikit.banner.WYABanner
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```