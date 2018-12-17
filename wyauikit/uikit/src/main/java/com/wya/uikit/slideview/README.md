# SlideView

## 功能

滑动条

## 属性

属性 | 说明 | 类型 | 默认值
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

### slider_mode

样式|说明
---|---
single|单向
bange|双向

## 基本用法
* xml

```xml
     <com.wya.uikit.slider.RangeSlider
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         android:layout_gravity="center"
         android:layout_marginTop="@dimen/dp_30"
         android:layout_marginBottom="@dimen/dp_30"
         android:paddingLeft="@dimen/dp_10"
         android:paddingRight="@dimen/dp_10"
         app:rsd_slider_mode="range"
         app:rsd_progress_background_color="@color/light_gray"
         app:rsd_progress_foreground_color="@color/colorPrimary"
         app:rsd_progress_height="@dimen/dp_3"
         app:rsd_progress_max="100"
         app:rsd_progress_min="0"
         app:rsd_region_bitmap_size="@dimen/dp_20"
         app:rsd_region_drawable_min="@drawable/image_ic_adjust"
         app:rsd_region_drawable_max="@drawable/image_ic_adjust"
         app:rsd_region_padding="@dimen/dp_5"
         app:rsd_region_text_color="@color/black"
         app:rsd_region_text_size="@dimen/sp_15" />

```