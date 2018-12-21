package com.wya.example.module.uikit.badge;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.badge.Builder;
import com.wya.uikit.badge.DisplayUtil;
import com.wya.uikit.badge.IBadgeView;

import butterknife.BindView;

public class BadgeExampleActivity extends BaseActivity {
    
    @BindView(R.id.ll_dot)
    LinearLayout llDot;
    
    @BindView(R.id.tv_num_dot)
    TextView tvNumDot;
    
    @BindView(R.id.tv_string_dot)
    View vStringDot;
    
    @Override
    protected int getLayoutID() {
        return R.layout.activity_badge_example;
    }
    
    private IBadgeView mBadgeOmit;
    private IBadgeView mBadgeShow;
    private IBadgeView mBadgeEdit;
    
    public static void start(Context context) {
        if (null != context) {
            context.startActivity(new Intent(context, BadgeExampleActivity.class));
        }
    }
    
    @Override
    protected void initView() {
        setToolBarTitle("badge");
        showBadge(llDot, 0);
        showBadgeCenterEnd(tvNumDot, 55, true, 50);
        showBadgeCenterStart(vStringDot, "new");
    }
    
    private void updateOffset(View view, int num) {
        if (null == view) {
            return;
        }
        mBadgeEdit = new Builder(this)
                .setOffset(0, 0)
                .setBadgeNum(num)
                .create();
        mBadgeEdit.bindToTarget(view);
    }
    
    public void showBadge(View view, int num) {
        showBadge(view, num, false);
    }
    
    public void updateBadge(View view, int num, boolean isShow) {
        if (view == null) {
            return;
        }
        mBadgeShow = new Builder(this)
                .setOffset(0, 0)
                .setBadgeNum(num)
                .isShow(isShow)
                .create();
        mBadgeShow.bindToTarget(view);
    }
    
    public void showBadge(View view, int num, boolean isOmitMode) {
        if (view == null) {
            return;
        }
        Builder builder = new Builder(this)
                .setOffset(0, 0)
                .setBadgeNum(num)
                .setOmitMode(isOmitMode);
        mBadgeOmit = builder.create();
        mBadgeOmit.bindToTarget(view);
    }
    
    public void showBadgeCenterEnd(View view, int num, boolean isOmitMode, int omit) {
        if (view == null) {
            return;
        }
        Builder builder = new Builder(this)
                .setOffset(0, 0)
                .setBadgeNum(num)
                .setOmitNum(omit)
                .setOmitMode(isOmitMode)
                .setOmitText(num + "+")
                .setGravity(new Builder.Gravity(Builder.BadgeGravity.GRAVITY_CENTER_END, 0, 0));
        mBadgeOmit = builder.create();
        mBadgeOmit.bindToTarget(view);
    }
    
    public void showBadgeCenterEnd(View view, int num) {
        if (view == null) {
            return;
        }
        IBadgeView badgeView = new Builder(this)
                .setOffset(0, 0)
                .setBadgeNum(num)
                .setGravity(new Builder.Gravity(Builder.BadgeGravity.GRAVITY_CENTER_END, 0, 0))
                .setTextSize(DisplayUtil.sp2px(this, 10))
                .create();
        badgeView.bindToTarget(view);
    }
    
    public void showBadgeCenterStart(View view, String text) {
        if (view == null) {
            return;
        }
        IBadgeView badgeView = new Builder(this)
                .setOffset(0, 0)
                .setText(text)
                .setGravity(new Builder.Gravity(Builder.BadgeGravity.GRAVITY_CENTER_START, 0, 0))
                .setTextSize(DisplayUtil.sp2px(this, 10))
                .create();
        badgeView.bindToTarget(view);
    }
    
    public void showBadgeCenterEnd(View view, String text) {
        if (view == null) {
            return;
        }
        IBadgeView badgeView = new Builder(this)
                .setOffset(0, 0)
                .setText(text)
                .setGravity(new Builder.Gravity(Builder.BadgeGravity.GRAVITY_CENTER_END, 0, 0))
                .setTextSize(DisplayUtil.sp2px(this, 10))
                .create();
        badgeView.bindToTarget(view);
    }
    
    public void showBadge(View view, String text) {
        if (view == null) {
            return;
        }
        IBadgeView badgeView = new Builder(this)
                .setOffset(0, 0)
                .setText(text)
                .setGravity(new Builder.Gravity(Builder.BadgeGravity.GRAVITY_CENTER_END, 0, 0))
                .setTextSize(DisplayUtil.sp2px(this, 10))
                .create();
        badgeView.bindToTarget(view);
    }
}

