package com.weiyian.android.index.suspension;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * @author :
 */
public class SuspensionDecoration extends RecyclerView.ItemDecoration {
    private List<? extends ISuspensionInterface> mDatas;
    private Paint mPaint;
    /**
     * 用于存放测量文字Rect
     */
    private Rect mBounds;
    
    private LayoutInflater mInflater;
    
    private int mTitleHeight;
    private static int COLOR_TITLE_BG = Color.parseColor("#F6F6F7");
    private static int COLOR_TITLE_FONT = Color.parseColor("#FF999999");
    private static int mTitleFontSize;
    
    private int mHeaderViewCount = 0;
    
    public SuspensionDecoration(Context context, List<? extends ISuspensionInterface> datas) {
        super();
        if (null == context) {
            return;
        }
        mDatas = datas;
        mPaint = new Paint();
        mBounds = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleFontSize);
        mPaint.setAntiAlias(true);
        mInflater = LayoutInflater.from(context);
    }
    
    public SuspensionDecoration setmTitleHeight(int mTitleHeight) {
        this.mTitleHeight = mTitleHeight;
        return this;
    }
    
    public SuspensionDecoration setColorTitleBg(int colorTitleBg) {
        COLOR_TITLE_BG = colorTitleBg;
        return this;
    }
    
    public SuspensionDecoration setColorTitleFont(int colorTitleFont) {
        COLOR_TITLE_FONT = colorTitleFont;
        return this;
    }
    
    public SuspensionDecoration setTitleFontSize(int mTitleFontSize) {
        mPaint.setTextSize(mTitleFontSize);
        return this;
    }
    
    public SuspensionDecoration setDatas(List<? extends ISuspensionInterface> mDatas) {
        this.mDatas = mDatas;
        return this;
    }
    
    public int getHeaderViewCount() {
        return mHeaderViewCount;
    }
    
    public SuspensionDecoration setHeaderViewCount(int headerViewCount) {
        mHeaderViewCount = headerViewCount;
        return this;
    }
    
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            position -= getHeaderViewCount();
            if (mDatas == null || mDatas.isEmpty() || position > mDatas.size() - 1 || position < 0 || !mDatas.get(position).isShowSuspension()) {
                continue;
            }
            if (position > -1) {
                if (position == 0) {
                    drawTitleArea(c, left, right, child, params, position);
                } else {
                    if (null != mDatas.get(position).getSuspensionTag() && !mDatas.get(position).getSuspensionTag().equals(mDatas.get(position - 1).getSuspensionTag())) {
                        drawTitleArea(c, left, right, child, params, position);
                    }
                }
            }
        }
    }
    
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        
        mPaint.getTextBounds(mDatas.get(position).getSuspensionTag(), 0, mDatas.get(position).getSuspensionTag().length(), mBounds);
        c.drawText(mDatas.get(position).getSuspensionTag(), child.getPaddingLeft(), child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }
    
    @Override
    public void onDrawOver(Canvas c, final RecyclerView parent, RecyclerView.State state) {
        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        pos -= getHeaderViewCount();
        if (mDatas == null || mDatas.isEmpty() || pos > mDatas.size() - 1 || pos < 0 || !mDatas.get(pos).isShowSuspension()) {
            return;
        }
        
        String tag = mDatas.get(pos).getSuspensionTag();
        View child = parent.findViewHolderForLayoutPosition(pos + getHeaderViewCount()).itemView;
        
        boolean flag = false;
        if ((pos + 1) < mDatas.size()) {
            if (null != tag && !tag.equals(mDatas.get(pos + 1).getSuspensionTag())) {
                Log.d("zxt", "onDrawOver() called with: c = [" + child.getTop());
                if (child.getHeight() + child.getTop() < mTitleHeight) {
                    c.save();
                    flag = true;
                    
                    c.translate(0, child.getHeight() + child.getTop() - mTitleHeight);
                }
            }
        }
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        if (null != tag) {
            mPaint.getTextBounds(tag, 0, tag.length(), mBounds);
        }
        c.drawText(tag, child.getPaddingLeft(), parent.getPaddingTop() + mTitleHeight - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
        if (flag) {
            c.restore();
        }
    }
    
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        position -= getHeaderViewCount();
        if (mDatas == null || mDatas.isEmpty() || position > mDatas.size() - 1) {
            return;
        }
        if (position > -1) {
            ISuspensionInterface titleCategoryInterface = mDatas.get(position);
            if (titleCategoryInterface.isShowSuspension()) {
                if (position == 0) {
                    outRect.set(0, mTitleHeight, 0, 0);
                } else {
                    if (null != titleCategoryInterface.getSuspensionTag() && !titleCategoryInterface.getSuspensionTag().equals(mDatas.get(position - 1).getSuspensionTag())) {
                        outRect.set(0, mTitleHeight, 0, 0);
                    }
                }
            }
        }
    }
    
}
