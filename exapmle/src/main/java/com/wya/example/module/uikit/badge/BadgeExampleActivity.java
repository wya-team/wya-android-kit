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
import butterknife.OnClick;

public class BadgeExampleActivity extends BaseActivity {
    
    @BindView(R.id.ll_dot)
    LinearLayout llDot;
    
    @BindView(R.id.tv_num_dot)
    TextView tvNumDot;
    
    @BindView(R.id.v_string_dot)
    View vStringDot;
    
    @BindView(R.id.v_market_dot_1)
    View vMarketDot1;
    
    @BindView(R.id.v_market_dot_2)
    View vMarketDot2;
    
    @BindView(R.id.v_market_dot_3)
    View vMarketDot3;
    
    @BindView(R.id.v_market_dot_4)
    View vMarketDot4;
    
    @BindView(R.id.v_market_dot_5)
    View vMarketDot5;
    
    @BindView(R.id.ll_corner_dot)
    LinearLayout llCornerDot;
    
    @BindView(R.id.tv_corner_dot)
    TextView tvCornerDot;
    
    @BindView(R.id.v_bitmap_dot)
    View vBitmapDot;
    
    @Override
    protected int getLayoutID() {
        return R.layout.activity_badge_example;
    }
    
    private IBadgeView mDot;
    private IBadgeView mNumDot;
    private IBadgeView mStringDot;
    
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
        showDot(llDot);
        showBadgeCenterEnd(tvNumDot, 55, true, 50);
        showStringBadge(vStringDot, "new");
        
        showBadgeCenterStart(vMarketDot1, "减");
        showBadgeCenterStart(vMarketDot2, "惠");
        showBadgeCenterStart(vMarketDot3, "免");
        showBadgeCenterStart(vMarketDot4, "返");
        showBadgeCenterStart(vMarketDot5, "HOT");
        showBadgeDrawableCenterEnd(llCornerDot, getResources().getDrawable(R.drawable.icon_sale_badge), true);
        showBadgeDrawableCenterEnd(tvCornerDot, getResources().getDrawable(R.drawable.icon_sale_badge), true);
        showBadgeDrawableCenter(vBitmapDot, getResources().getDrawable(R.drawable.icon_on_sale));
    }
    
    @OnClick({R.id.ll_dot_container, R.id.ll_num_dot, R.id.ll_string_dot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_dot_container:
                if (null != mDot) {
                    mDot.update(false);
                }
                break;
            case R.id.ll_num_dot:
                if (null != mNumDot) {
                    mNumDot.update(false);
                }
                break;
            
            case R.id.ll_string_dot:
                if (null != mStringDot) {
                    mStringDot.update(false);
                }
                break;
        }
    }
    
    public void showDot(View view) {
        if (view == null) {
            return;
        }
        Builder builder = new Builder(this)
                .setOffset(0, 0)
                .setBadgeNum(0);
        mDot = builder.create();
        mDot.bindToTarget(view);
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
        mNumDot = builder.create();
        mNumDot.bindToTarget(view);
    }
    
    public void showStringBadge(View view, String text) {
        if (view == null) {
            return;
        }
        mStringDot = new Builder(this)
                .setText(text)
                .setGravity(new Builder.Gravity(Builder.BadgeGravity.GRAVITY_CENTER_START, 0, 0))
                .setTextSize(DisplayUtil.sp2px(this, 10))
                .create();
        mStringDot.bindToTarget(view);
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
    
    public void showBadgeDrawableCenterEnd(View view, Drawable badgeDrawable, boolean isAttach) {
        if (view == null) {
            return;
        }
        IBadgeView badgeView = new Builder(this)
                .setGravity(new Builder.Gravity(Builder.BadgeGravity.GRAVITY_END_TOP, 0, 0))
                .setAttach(isAttach)
                .setBadgeBitmapSize(Utils.dp2px(this, 20))
                .setBadgeDrawable(badgeDrawable)
                .create();
        badgeView.bindToTarget(view);
    }
    
    public void showBadgeDrawableCenter(View view, Drawable badgeDrawable) {
        if (view == null) {
            return;
        }
        IBadgeView badgeView = new Builder(this)
                .setGravity(new Builder.Gravity(Builder.BadgeGravity.GRAVITY_CENTER, 0, 0))
                .setAttach(false)
                .setBadgeBitmapSize(Utils.dp2px(this, 20))
                .setBadgeDrawable(badgeDrawable)
                .create();
        badgeView.bindToTarget(view);
    }
    
}

