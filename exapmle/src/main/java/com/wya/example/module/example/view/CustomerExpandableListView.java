package com.wya.example.module.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;

/**
 * 创建日期：2018/12/18 11:22
 * 作者： Mao Chunjiang
 * 文件名称： CustomerExpandableListView
 * 类说明：
 */

public class CustomerExpandableListView extends ExpandableListView {

    public CustomerExpandableListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CustomerExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerExpandableListView(Context context, AttributeSet attrs,
                                      int defStyle) {
        super(context, attrs, defStyle);

    }

    /**
     * 只需要重写这个方法即可
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}