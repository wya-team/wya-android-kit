package com.weiyian.android.xpopup.core;

import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;

import com.weiyian.android.xpopup.animator.BasePopupAnimator;
import com.weiyian.android.xpopup.enums.PopupAnimation;
import com.weiyian.android.xpopup.enums.PopupType;
import com.weiyian.android.xpopup.interfaces.XPopupCallback;

/**
 * Description: Popup的属性封装
 * Create by dance, at 2018/12/8
 *
 * @author :
 */
public class PopupInfo {
    
    public PopupType popupType = null;
    public Boolean isDismissOnBackPressed = true;
    public Boolean isDismissOnTouchOutside = true;
    public Boolean autoDismiss = true;
    public Boolean hasShadowBg = true;
    private View atView = null;
    public PopupAnimation popupAnimation = null;
    public BasePopupAnimator customAnimator = null;
    public PointF touchPoint = null;
    public int maxWidth;
    public int maxHeight;
    public Boolean autoOpenSoftInput = false;
    public XPopupCallback xPopupCallback;
    
    /**
     * 每个弹窗所属的DecorView
     */
    public ViewGroup decorView;
    
    public View getAtView() {
        return atView;
    }
    
    public void setAtView(View atView) {
        this.atView = atView;
        this.popupType = PopupType.AttachView;
    }
    
    @Override
    public String toString() {
        return "PopupInfo{" +
                "popupType=" + popupType +
                ", isDismissOnBackPressed=" + isDismissOnBackPressed +
                ", isDismissOnTouchOutside=" + isDismissOnTouchOutside +
                ", hasShadowBg=" + hasShadowBg +
                ", atView=" + atView +
                ", popupAnimation=" + popupAnimation +
                ", customAnimator=" + customAnimator +
                ", touchPoint=" + touchPoint +
                ", maxWidth=" + maxWidth +
                ", maxHeight=" + maxHeight +
                '}';
    }
}
