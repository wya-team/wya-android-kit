package com.weiyian.android.xpopup.core;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.weiyian.android.xpopup.R;
import com.weiyian.android.xpopup.animator.BasePopupAnimator;
import com.weiyian.android.xpopup.animator.ScrollScaleAnimator;
import com.weiyian.android.xpopup.enums.PopupAnimation;
import com.weiyian.android.xpopup.impl.ScreenUtil;
import com.weiyian.android.xpopup.util.XPopupUtils;
import com.weiyian.android.xpopup.widget.PartShadowContainer;

/**
 * Description: 依附于某个View的弹窗
 * Create by dance, at 2018/12/11
 *
 * @author :
 */
public abstract class BaseAttachPopupView extends BasePopupView {
    
    protected int defaultOffsetY = 6;
    protected int defaultOffsetX = 0;
    protected PartShadowContainer attachPopupContainer;
    
    public BaseAttachPopupView(@NonNull Context context) {
        super(context);
        defaultOffsetY = XPopupUtils.dp2px(context, defaultOffsetY);
        defaultOffsetX = XPopupUtils.dp2px(context, defaultOffsetX);
        attachPopupContainer = findViewById(R.id.attachPopupContainer);
        
        View contentView = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), attachPopupContainer, false);
        attachPopupContainer.addView(contentView);
    }
    
    public BaseAttachPopupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    
    public BaseAttachPopupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    protected int getPopupLayoutId() {
        return R.layout._xpopup_attach_popup_view;
    }
    
    protected boolean isShowUp;
    boolean isShowLeft;
    
    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        if (popupInfo.getAtView() == null && popupInfo.touchPoint == null) {
            throw new IllegalArgumentException("atView() or touchPoint must not be null for AttachView type！");
        }
        
        post(new Runnable() {
            @Override
            public void run() {
                doAttach();
            }
        });
    }
    
    /**
     * 执行附着逻辑
     */
    protected void doAttach() {
        // 弹窗显示的位置不能超越Window高度
        float maxY = XPopupUtils.getWindowHeight(getContext());
        // 显示在右边时候的最大值
        float maxX = 0;
        
        float translationX = 0, translationY = 0;
        //0. 判断是依附于某个点还是某个View
        if (popupInfo.touchPoint != null) {
            // 依附于指定点
            maxX = Math.max(popupInfo.touchPoint.x - getPopupContentView().getMeasuredWidth(), 0);
            // 尽量优先放在下方，当不够的时候在显示在上方
            isShowUp = (popupInfo.touchPoint.y + getPopupContentView().getMeasuredHeight()) > maxY;
            isShowLeft = popupInfo.touchPoint.x < XPopupUtils.getWindowWidth(getContext()) / 2;
            
            if (isShowUp) {
                // 应显示在point上方
                // translationX: 在左边就和atView左边对齐，在右边就和其右边对齐
                translationX = (isShowLeft ? popupInfo.touchPoint.x : maxX) + defaultOffsetX;
                translationY = popupInfo.touchPoint.y - getPopupContentView().getMeasuredHeight() - defaultOffsetY;
            } else {
                translationX = (isShowLeft ? popupInfo.touchPoint.x : maxX) + defaultOffsetX;
                translationY = popupInfo.touchPoint.y + defaultOffsetY;
            }
        } else {
            // 依附于指定View
            //1. 获取atView在屏幕上的位置
            int[] locations = new int[2];
            popupInfo.getAtView().getLocationOnScreen(locations);
            //            Rect rect = new Rect(locations[0], locations[1], locations[0] + popupInfo.getAtView().getMeasuredWidth(),
            //                    locations[1] + popupInfo.getAtView().getMeasuredHeight());
            
            Rect rect = new Rect(locations[0], locations[1], locations[0] + ScreenUtil.getScreenWidth(getContext()),
                    locations[1] + popupInfo.getAtView().getMeasuredHeight());
            
            maxX = Math.max(rect.right - getPopupContentView().getMeasuredWidth(), 0);
            int centerX = (rect.left + rect.right) / 2;
            
            // 尽量优先放在下方，当不够的时候在显示在上方 不能正好贴着底边
            isShowUp = (rect.bottom + getPopupContentView().getMeasuredHeight()) > maxY;
            isShowLeft = centerX < XPopupUtils.getWindowWidth(getContext()) / 2;
            
            if (isShowUp) {
                //说明上面的空间比较大，应显示在atView上方
                // translationX: 在左边就和atView左边对齐，在右边就和其右边对齐
                translationX = (isShowLeft ? rect.left : maxX) + defaultOffsetX;
                translationY = rect.top - getPopupContentView().getMeasuredHeight() - defaultOffsetY;
            } else {
                translationX = (isShowLeft ? rect.left : maxX) + defaultOffsetX;
                translationY = rect.bottom + defaultOffsetY;
            }
        }
        
        getPopupContentView().setTranslationX(translationX);
        getPopupContentView().setTranslationY(translationY);
    }
    
    @Override
    protected BasePopupAnimator getPopupAnimator() {
        BasePopupAnimator animator;
        if (isShowUp) {
            // 在上方展示
            if (isShowLeft) {
                animator = new ScrollScaleAnimator(getPopupContentView(), PopupAnimation.ScrollAlphaFromLeftBottom);
            } else {
                animator = new ScrollScaleAnimator(getPopupContentView(), PopupAnimation.ScrollAlphaFromRightBottom);
            }
        } else {
            // 在下方展示
            if (isShowLeft) {
                animator = new ScrollScaleAnimator(getPopupContentView(), PopupAnimation.ScrollAlphaFromLeftTop);
            } else {
                animator = new ScrollScaleAnimator(getPopupContentView(), PopupAnimation.ScrollAlphaFromRightTop);
            }
        }
        return animator;
    }
    
}
