package com.weiyian.android.xpopup.impl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.weiyian.android.xpopup.animator.BasePopupAnimator;
import com.weiyian.android.xpopup.animator.TranslateAnimator;
import com.weiyian.android.xpopup.core.BaseAttachPopupView;
import com.weiyian.android.xpopup.enums.PopupAnimation;
import com.weiyian.android.xpopup.interfaces.OnClickOutsideListener;
import com.weiyian.android.xpopup.util.XPopupUtils;

/**
 * @author :
 */
public abstract class BasePartShadowPopupView extends BaseAttachPopupView {
    
    private Context mContext;
    
    public BasePartShadowPopupView(@NonNull Context context) {
        super(context);
        defaultOffsetY = 0;
        mContext = context;
    }
    
    @Override
    protected void doAttach() {
        if (popupInfo.getAtView() == null) {
            throw new IllegalArgumentException("atView must not be null for PartShadowPopupView！");
        }
        
        // 指定阴影动画的目标View
        shadowBgAnimator.targetView = getPopupContentView();
        
        //1. apply width and height
        ViewGroup.LayoutParams params = getPopupContentView().getLayoutParams();
        // 满宽
        //        params.width = getMeasuredWidth();
        params.width = ScreenUtil.getScreenWidth(mContext);
        
        //1. 获取atView在屏幕上的位置
        int[] locations = new int[2];
        popupInfo.getAtView().getLocationOnScreen(locations);
        Rect rect = new Rect(locations[0], locations[1], locations[0] + popupInfo.getAtView().getMeasuredWidth(),
                locations[1] + popupInfo.getAtView().getMeasuredHeight());
        int centerY = rect.top + rect.height() / 2;
        if (centerY > getMeasuredHeight() / 2) {
            // 说明atView在Window下半部分，PartShadow应该显示在它上方，计算atView之上的高度
            params.height = rect.top;
            isShowUp = true;
            getPopupContentView().setTranslationY(-defaultOffsetY);
            
            // 同时自定义的impl View应该Gravity居于底部
            View implView = ((ViewGroup) getPopupContentView()).getChildAt(0);
            FrameLayout.LayoutParams implParams = (LayoutParams) implView.getLayoutParams();
            implParams.gravity = Gravity.BOTTOM;
            params.width = ScreenUtil.getScreenWidth(mContext);
            implView.setLayoutParams(implParams);
            
        } else {
            // atView在上半部分，PartShadow应该显示在它下方，计算atView之下的高度
            params.height = getMeasuredHeight() - rect.bottom;
            // 防止伸到导航栏下面
            if (XPopupUtils.isNavBarVisible(getContext())) {
                params.height -= XPopupUtils.getNavBarHeight();
            }
            isShowUp = false;
            getPopupContentView().setTranslationY(rect.bottom + defaultOffsetY);
            
            // 同时自定义的impl View应该Gravity居于顶部
            View implView = ((ViewGroup) getPopupContentView()).getChildAt(0);
            FrameLayout.LayoutParams implParams = (LayoutParams) implView.getLayoutParams();
            implParams.gravity = Gravity.TOP;
            params.width = ScreenUtil.getScreenWidth(mContext);
            implView.setLayoutParams(implParams);
        }
        getPopupContentView().setLayoutParams(params);
        
        //        attachPopupContainer.setCardBackgroundColor(Color.TRANSPARENT);
        //        attachPopupContainer.setCardElevation(0);
        attachPopupContainer.setBackgroundColor(Color.TRANSPARENT);
        attachPopupContainer.setOnClickOutsideListener(new OnClickOutsideListener() {
            @Override
            public void onClickOutside() {
                dismiss();
            }
        });
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dismiss();
        return false;
    }
    
    @Override
    protected BasePopupAnimator getPopupAnimator() {
        return new TranslateAnimator(getPopupImplView(), isShowUp ?
                PopupAnimation.TranslateFromBottom : PopupAnimation.TranslateFromTop);
    }
    
}
