package com.wya.example.module.uikit.badge;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.badge.Builder;
import com.wya.uikit.badge.DisplayUtil;
import com.wya.uikit.badge.IBadgeView;
import com.wya.uikit.slideview.Utils;

import butterknife.BindView;

public class BadgeExampleActivity extends BaseActivity {
    
    @BindView(R.id.ll_dot)
    LinearLayout llDot;
    
    @BindView(R.id.tv_num_dot)
    TextView tvNumDot;
    
    @BindView(R.id.tv_string_dot)
    View vStringDot;
    
    @BindView(R.id.tv_market_dot_1)
    View vMarketDot1;
    
    @BindView(R.id.tv_market_dot_2)
    View vMarketDot2;
    
    @BindView(R.id.tv_market_dot_3)
    View vMarketDot3;
    
    @BindView(R.id.tv_market_dot_4)
    View vMarketDot4;
    
    @BindView(R.id.tv_market_dot_5)
    View vMarketDot5;
    
    @BindView(R.id.ll_corner_dot)
    LinearLayout llCornerDot;
    
    @BindView(R.id.tv_corner_dot)
    TextView tvCornerDot;
    
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
        setToolBarTitle("徽标数(badge)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help, true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(BadgeExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        showBadge(llDot, 0);
        showBadgeCenterEnd(tvNumDot, 55, true, 50);
        showBadgeCenterStart(vStringDot, "new");
        showBadgeCenterStart(vMarketDot1, "减");
        showBadgeCenterStart(vMarketDot2, "惠");
        showBadgeCenterStart(vMarketDot3, "免");
        showBadgeCenterStart(vMarketDot4, "返");
        showBadgeCenterStart(vMarketDot5, "HOT");
        showBadgeDrawableCenterStart(llCornerDot, getResources().getDrawable(R.drawable.sale_badge), true);
        showBadgeDrawableCenterStart(tvCornerDot, getResources().getDrawable(R.drawable.sale_badge), true);
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
    
    public void showBadgeCenterStart(View view, String text) {
        if (view == null) {
            return;
        }
        showBadgeCenterStart(view, text, null, false);
    }
    
    public void showBadgeCenterStart(View view, String text, Drawable badgeDrawable, boolean isAttach) {
        if (view == null) {
            return;
        }
        IBadgeView badgeView = new Builder(this)
                .setText(text)
                .setGravity(new Builder.Gravity(Builder.BadgeGravity.GRAVITY_CENTER_START, 0, 0))
                .setTextSize(DisplayUtil.sp2px(this, 10))
                .setAttach(isAttach)
                .setBackgroundDrawable(badgeDrawable)
                .create();
        badgeView.bindToTarget(view);
    }
    
    public void showBadgeDrawableCenterStart(View view, Drawable badgeDrawable, boolean isAttach) {
        if (view == null) {
            return;
        }
        IBadgeView badgeView = new Builder(this)
                .setGravity(new Builder.Gravity(Builder.BadgeGravity.GRAVITY_CENTER_END, 0, 0))
                .setAttach(isAttach)
                .setBadgeBitmapSize(Utils.dp2px(this, 20))
                .setBadgeDrawable(badgeDrawable)
                .create();
        badgeView.bindToTarget(view);
    }
    
}

