package com.weiyian.android.xpopup.enums;

/**
 * Description:
 * Create by dance, at 2018/12/9
 *
 * @author :
 */
public enum PopupAnimation {
    // 缩放 + 透明渐变
    /**
     * 从中心进行缩放+透明渐变
     */
    ScaleAlphaFromCenter,
    
    /**
     * 从左上角进行缩放+透明渐变
     */
    ScaleAlphaFromLeftTop,
    
    /**
     * 从右上角进行缩放+透明渐变
     */
    ScaleAlphaFromRightTop,
    
    /**
     * 从左下角进行缩放+透明渐变
     */
    ScaleAlphaFromLeftBottom,
    
    /**
     * 从右下角进行缩放+透明渐变
     */
    ScaleAlphaFromRightBottom,
    
    // 平移 + 透明渐变
    /**
     * 从左平移进入
     */
    TranslateAlphaFromLeft,
    
    /**
     * 从右平移进入
     */
    TranslateAlphaFromRight,
    
    /**
     * 从上方平移进入
     */
    TranslateAlphaFromTop,
    
    /**
     * 从下方平移进入
     */
    TranslateAlphaFromBottom,
    
    // 平移，不带透明渐变
    /**
     * 从左平移进入
     */
    TranslateFromLeft,
    
    /**
     * 从右平移进入
     */
    TranslateFromRight,
    
    /**
     * 从上方平移进入
     */
    TranslateFromTop,
    
    /**
     * 从下方平移进入
     */
    TranslateFromBottom,
    
    // 滑动 + 透明渐变
    ScrollAlphaFromLeft,
    ScrollAlphaFromLeftTop,
    ScrollAlphaFromTop,
    ScrollAlphaFromRightTop,
    ScrollAlphaFromRight,
    ScrollAlphaFromRightBottom,
    ScrollAlphaFromBottom,
    ScrollAlphaFromLeftBottom,
}
