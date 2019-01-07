# Badge

## 功能说明
红点提示 可设置数字(超过某个值显示省略)、文本、图片

## 用法说明
方法|说明
---|---
setBadgeNum|设置数字类型红点
setBadgeText|设置文本类型红点
setMaxBadgeNum|设置默认最大数字，OmitMode省略模式为true时且大于该值显示省略
setOmitText|省略模式时的文本
setTextColor|设置文本字体颜色
setTextSize|设置文本字体大小
setBackgroundColor|设置背景颜色
setBackgroundDrawable|设置背景图片
setPadding|设置间隔
setGravity|设置位置 (BadgeGravity,xOffset,yOffset)
setOffset|设置位置偏移
bindToTarget|需要显示消息的View
update|更新显示状态
setAttach|是否依附于其他View,图片模式时设为false
setBadgeDrawable|设置图片
setBadgeDrawableSize|设置图片大小

- IBadgeView暴露所有支持的方法
- 通过Builder创建
- bindToTarget 将红点绑定至目标View，不需在xml中设置
- 通过gravity控制红点位置 offset控制偏移量

```java
  Builder.Gravity gravity = new Builder.Gravity(Builder.BadgeGravity.GRAVITY_CENTER_TOP, ScreenUtil.dp2px(12), ScreenUtil.dp2px(2));
  IBadgeView badgeView = new Builder(this)
                .setBadgeNum(num)
                .setOmitText("99+")
                .setgravity(grvity)
                .create();
        badgeView.bindToTarget(itemView);
        badgeView.update(false);
```


支持的gravity，设置最接近的BadgeGravity后 通过调整offset进行微调

```java
  @IntDef
    public @interface BadgeGravity {

        int GRAVITY_START_TOP = android.view.Gravity.START | android.view.Gravity.TOP;
        int GRAVITY_END_TOP = android.view.Gravity.END | android.view.Gravity.TOP;
        int GRAVITY_START_BOTTOM = android.view.Gravity.START | android.view.Gravity.BOTTOM;
        int GRAVITY_END_BOTTOM = android.view.Gravity.END | android.view.Gravity.BOTTOM;
        int GRAVITY_CENTER = android.view.Gravity.CENTER;
        int GRAVITY_CENTER_TOP = android.view.Gravity.CENTER | android.view.Gravity.TOP;
        int GRAVITY_CENTER_BOTTOM = android.view.Gravity.CENTER | android.view.Gravity.BOTTOM;
        int GRAVITY_CENTER_START = android.view.Gravity.CENTER | android.view.Gravity.START;
        int GRAVITY_CENTER_END = android.view.Gravity.CENTER | android.view.Gravity.END;

    }
```

