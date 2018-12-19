# Banner

## 功能说明
轮播图

## 用法说明
方法|说明
---|---
autoPlay(boolean isAuto)|自动轮播启动，参数代表是否自动轮播，默认自动轮播
setDotBackgroundResource(@DrawableRes int source)|设置小圆点背景色
setUpdateTime(long updateTime)|设置滚动时间间距
setDotVisible(boolean isVisible)|是否显示下面小圆点
setDotDark()|设置小圆点默认深色（蓝色）
setAdapter(BannerAdapter<T> bannerAdapter)|设置适配器，适配器构造方法中需要传入banner的布局文件和数据源list
convert(View view, int position, T item)|实现BannerAdapter的抽象方法，view是banner根布局，position是当前位置，item是数据

- `WYABanner<T> `类的泛型代表数据类型，创建WYABanner对象后必须设置适配器。

- 在布局文件中添加

```
<com.wya.uikit.banner.WYABanner
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

- 在activity等文件中使用
````
mWYABanner.setUpdateTime(2000)
	.setDotVisible(true)
	.autoPlay(true);

mWYABanner.setAdapter(new BannerAdapter<Integer>(data, R.layout.banner_example_item) {
	@Override
	public void convert(View view, int position, Integer item) {
                ImageView imageView = view.findViewById(R.id.image);
                imageView.setImageResource(item);
	}
});
````