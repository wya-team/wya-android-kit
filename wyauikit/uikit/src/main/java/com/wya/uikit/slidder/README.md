# SlideView

## 功能

滑动条 支持单向与双向

## 方法

方法 | 说明 | 类型 | 默认值
---|---|---|---
setSliderMode|模式（ - single 单向 - bange 双向）| int | single
setProgressHeight|进度条高度|dimension|@dimen/dp_3
setProgressMin|设置最小值|int|-
setProgressMax|设置最大值|int|-
setProgressBackgroundColor|设置进度条背景颜色|color|grayColor
setProgressForegroundColor|设置进度条颜色|color|
setRegionPadding|设置图片间距|dimension|@dimen/dp_5
setRegionDrawableMin|设置最小区间图片|drawable|-
setRegionDrawableMax|设置最大区间图片|drawable|-
setRegionBitmapSize|设置图片尺寸|dimension|@dimen/dp_20
setRegionTextColor|设置区间文本颜色|color|@color/black
setRegionTextSize|设置区间文本尺寸|dimension|@dimen/sp_14
setRegionMode|设置区间类型|enum|mode_integer / mode_float

### slider_mode

样式|说明
---|---
single|单向
bange|双向

## 基本用法
* xml

```xml
     <com.wya.uikit.slidder.RangeSlider
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         android:layout_gravity="center"
         android:layout_marginTop="@dimen/dp_30"
         android:layout_marginBottom="@dimen/dp_30"
         android:paddingLeft="@dimen/dp_10"
         android:paddingRight="@dimen/dp_10"
         app:slidderMode="range"
         app:slidderProgressBackgroundColor="@color/light_gray"
         app:slidderProgressForegroundColor="@color/colorPrimary"
         app:slidderProgressHeight="@dimen/dp_3"
         app:slidderProgressMax="100"
         app:slidderProgressMin="0"
         app:slidderRegionBitmapSize="@dimen/dp_20"
         app:slidderRegionDrawableMin="@drawable/image_ic_adjust"
         app:slidderRegionDrawableMax="@drawable/image_ic_adjust"
         app:slidderRegionPadding="@dimen/dp_5"
         app:slidderRegionTextColor="@color/black"
         app:slidderRegionMode="mode_integer"
         app:slidderRegionTextSize="@dimen/sp_15" />

```