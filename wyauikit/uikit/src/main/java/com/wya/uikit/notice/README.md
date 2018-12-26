# NoticeBar

## 功能

通知栏

## 属性

MarqueeTextView
> 继承自AppCompactTextView，设置drawableLeft,drawableRight,drawablePadding,background同TextView

属性 | 说明 |
---|---|
startMarquee|开始滚动|
resumeMarquee|恢复滚动|
pauseMarquee|暂停滚动|
resetMarquee|重置滚动|
setMarqueeMode|设置模式|

MarqueeMode

属性 | 说明 |
---|---|
repeat|重复|
once|一次|

SwitcherView
> 类似淘宝头条 继承自ViewSwitcher 实现上下左右四个方向切换视图，支持设置动画

属性 | 说明 |
---|---|
startSwitcher|开始切换|
pauseSwitcher|暂停切换|
resetSwitcher|重置切换|
setAnimation|设置切换动画|
setAnimDirection|设置切换动画方向|
setSwitchDuration|设置切换动画间隔时长|

AnimDirection

属性 | 说明 |
---|---|
down2up|从下至上|
up2down|从下至上|
left2right|从左至右|
right2left|从右至左|

## 方法

## 基础用法

SwitcherView

```xml
    <com.wya.uikit.notice.switcher.SwitcherView
        android:id="@+id/vs_up2down"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/activity_switcher_item_margin"
        android:layout_marginBottom="10dp"
        android:background="#ffffff"
        app:anim_direction="up2down"
        app:anim_duration="400"
        app:switch_duration="3000" />

```

MarqueeTextView

```xml
    <com.wya.uikit.notice.MarqueeTextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:drawableLeft="@drawable/image_ic_adjust"
        android:drawablePadding="@dimen/dp_10"
        android:padding="@dimen/dp_20"
        android:text="因全国公民身份系统升级，添加银行卡银行卡" />
```

