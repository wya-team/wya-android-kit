package com.weiyian.android.index.IndexBar.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.weiyian.android.index.IndexBar.bean.BaseIndexPinyinBean;
import com.weiyian.android.index.IndexBar.helper.IIndexBarDataHelper;
import com.weiyian.android.index.IndexBar.helper.IndexBarDataHelperImpl;
import com.weiyian.android.index.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author :
 */
public class IndexBar extends View {
    
    public static String[] INDEX_STRING = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private boolean isNeedRealIndex;
    private List<String> mIndexDatas;
    
    private int mWidth, mHeight;
    private int mGapHeight;
    private Paint mPaint;
    
    private int mPressedBackground;
    
    private IIndexBarDataHelper mDataHelper;
    
    private TextView mPressedShowTextView;
    private boolean isSourceDatasAlreadySorted;
    private List<? extends BaseIndexPinyinBean> mSourceDatas;
    private LinearLayoutManager mLayoutManager;
    private int mHeaderViewCount = 0;
    
    public IndexBar(Context context) {
        this(context, null);
    }
    
    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }
    
    public int getHeaderViewCount() {
        return mHeaderViewCount;
    }
    
    public IndexBar setHeaderViewCount(int headerViewCount) {
        mHeaderViewCount = headerViewCount;
        return this;
    }
    
    public boolean isSourceDatasAlreadySorted() {
        return isSourceDatasAlreadySorted;
    }
    
    public IndexBar setSourceDatasAlreadySorted(boolean sourceDatasAlreadySorted) {
        isSourceDatasAlreadySorted = sourceDatasAlreadySorted;
        return this;
    }
    
    public IIndexBarDataHelper getDataHelper() {
        return mDataHelper;
    }
    
    public IndexBar setDataHelper(IIndexBarDataHelper dataHelper) {
        mDataHelper = dataHelper;
        return this;
    }
    
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
        mPressedBackground = Color.BLACK;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IndexBar, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.IndexBar_indexBarTextSize) {
                textSize = typedArray.getDimensionPixelSize(attr, textSize);
            } else if (attr == R.styleable.IndexBar_indexBarPressBackground) {
                mPressedBackground = typedArray.getColor(attr, mPressedBackground);
            }
        }
        typedArray.recycle();
        
        initIndexDatas();
        
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mPaint.setColor(Color.BLACK);
        
        setmOnIndexPressedListener(new IndexPressedListener() {
            @Override
            public void onIndexPressed(int index, String text) {
                if (mPressedShowTextView != null) {
                    mPressedShowTextView.setVisibility(View.VISIBLE);
                    mPressedShowTextView.setText(text);
                }
                if (mLayoutManager != null) {
                    int position = getPosByTag(text);
                    if (position != -1) {
                        mLayoutManager.scrollToPositionWithOffset(position, 0);
                    }
                }
            }
            
            @Override
            public void onMotionEventEnd() {
                if (mPressedShowTextView != null) {
                    mPressedShowTextView.setVisibility(View.GONE);
                }
            }
        });
        
        mDataHelper = new IndexBarDataHelperImpl();
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidth = 0, measureHeight = 0;
        
        @SuppressLint("DrawAllocation")
        Rect indexBounds = new Rect();
        String index;
        for (int i = 0; i < mIndexDatas.size(); i++) {
            index = mIndexDatas.get(i);
            mPaint.getTextBounds(index, 0, index.length(), indexBounds);
            measureWidth = Math.max(indexBounds.width(), measureWidth);
            measureHeight = Math.max(indexBounds.height(), measureHeight);
        }
        measureHeight *= mIndexDatas.size();
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                measureWidth = wSize;
                break;
            case MeasureSpec.AT_MOST:
                measureWidth = Math.min(measureWidth, wSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }
        
        switch (hMode) {
            case MeasureSpec.EXACTLY:
                measureHeight = hSize;
                break;
            case MeasureSpec.AT_MOST:
                measureHeight = Math.min(measureHeight, hSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }
        
        setMeasuredDimension(measureWidth, measureHeight);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        int t = getPaddingTop();
        String index;
        for (int i = 0; i < mIndexDatas.size(); i++) {
            index = mIndexDatas.get(i);
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            int baseline = (int) ((mGapHeight - fontMetrics.bottom - fontMetrics.top) / 2);
            canvas.drawText(index, mWidth / 2 - mPaint.measureText(index) / 2, t + mGapHeight * i + baseline, mPaint);
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(mPressedBackground);
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int pressI = (int) ((y - getPaddingTop()) / mGapHeight);
                if (pressI < 0) {
                    pressI = 0;
                } else if (pressI >= mIndexDatas.size()) {
                    pressI = mIndexDatas.size() - 1;
                }
                if (null != mOnIndexPressedListener && pressI > -1 && pressI < mIndexDatas.size()) {
                    mOnIndexPressedListener.onIndexPressed(pressI, mIndexDatas.get(pressI));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            default:
                setBackgroundResource(android.R.color.transparent);
                if (null != mOnIndexPressedListener) {
                    mOnIndexPressedListener.onMotionEventEnd();
                }
                break;
        }
        return true;
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        if (null == mIndexDatas || mIndexDatas.isEmpty()) {
            return;
        }
        computeGapHeight();
    }
    
    public interface IndexPressedListener {
        /**
         * 当某个Index被按下
         *
         * @param index :
         * @param text  :
         */
        void onIndexPressed(int index, String text);
        
        /**
         * 当触摸事件结束（UP CANCEL）
         */
        void onMotionEventEnd();
    }
    
    private IndexPressedListener mOnIndexPressedListener;
    
    public IndexPressedListener getmOnIndexPressedListener() {
        return mOnIndexPressedListener;
    }
    
    public void setmOnIndexPressedListener(IndexPressedListener mOnIndexPressedListener) {
        this.mOnIndexPressedListener = mOnIndexPressedListener;
    }
    
    public IndexBar setPressedShowTextView(TextView mPressedShowTextView) {
        this.mPressedShowTextView = mPressedShowTextView;
        return this;
    }
    
    public IndexBar setLayoutManager(LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
        return this;
    }
    
    public IndexBar setNeedRealIndex(boolean needRealIndex) {
        isNeedRealIndex = needRealIndex;
        initIndexDatas();
        return this;
    }
    
    private void initIndexDatas() {
        if (isNeedRealIndex) {
            mIndexDatas = new ArrayList<>();
        } else {
            mIndexDatas = Arrays.asList(INDEX_STRING);
        }
    }
    
    public IndexBar setSourceDatas(List<? extends BaseIndexPinyinBean> mSourceDatas) {
        this.mSourceDatas = mSourceDatas;
        initSourceDatas();
        return this;
    }
    
    private void initSourceDatas() {
        if (null == mSourceDatas || mSourceDatas.isEmpty()) {
            return;
        }
        if (!isSourceDatasAlreadySorted) {
            mDataHelper.sortSourceDatas(mSourceDatas);
        } else {
            mDataHelper.convert(mSourceDatas);
            mDataHelper.fillInexTag(mSourceDatas);
        }
        if (isNeedRealIndex) {
            mDataHelper.getSortedIndexDatas(mSourceDatas, mIndexDatas);
            computeGapHeight();
        }
        sortData();
    }
    
    private void computeGapHeight() {
        mGapHeight = (mHeight - getPaddingTop() - getPaddingBottom()) / mIndexDatas.size();
    }
    
    private void sortData() {
    
    }
    
    private int getPosByTag(String tag) {
        if (null == mSourceDatas || mSourceDatas.isEmpty()) {
            return -1;
        }
        if (TextUtils.isEmpty(tag)) {
            return -1;
        }
        for (int i = 0; i < mSourceDatas.size(); i++) {
            if (tag.equals(mSourceDatas.get(i).getBaseIndexTag())) {
                return i + getHeaderViewCount();
            }
        }
        return -1;
    }
    
}
