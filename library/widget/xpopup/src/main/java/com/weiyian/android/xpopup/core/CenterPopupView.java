package com.weiyian.android.xpopup.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.weiyian.android.xpopup.R;
import com.weiyian.android.xpopup.util.XPopupUtils;

/**
 * Description: 在中间显示的Popup
 * Create by dance, at 2018/12/8
 *
 * @author :
 */
public class CenterPopupView extends BasePopupView {
    
    protected FrameLayout centerPopupContainer;
    
    public CenterPopupView(@NonNull Context context) {
        super(context);
        centerPopupContainer = findViewById(R.id.centerPopupContainer);
        View contentView = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), centerPopupContainer, false);
        centerPopupContainer.addView(contentView);
    }
    
    @Override
    protected int getPopupLayoutId() {
        return R.layout._xpopup_center_popup_view;
    }
    
    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        XPopupUtils.widthAndHeight(getPopupContentView(), getMaxWidth(), getMaxHeight());
    }
    
    /**
     * 具体实现的类的布局
     *
     * @return
     */
    @Override
    protected int getImplLayoutId() {
        return 0;
    }
    
    @Override
    protected int getMaxWidth() {
        return popupInfo.maxWidth == 0 ? (int) (XPopupUtils.getWindowWidth(getContext()) * 0.86f)
                : popupInfo.maxWidth;
    }
    
}
