package com.wya.uikit.segmentedcontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wya.uikit.R;

import java.util.List;

/**
 *  @author : XuDonglin
 *  @time   : 2019-01-10 
 *  @description     : 
 */
public class WYASegmentedView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private static final String TAG = "SegmentedView";
    private Paint mPaint;
    private int lineColor;
    private int titleNormalColor;
    private int titleSelectedColor;
    private int itemSelectedColor;
    private int itemNormalColor;
    private int strokeColor;
    private float strokeWidth;
    private float lineSize;
    private float radius;
    private float titleSize;
    private boolean isEnable = true;
    private OnItemClickListener mOnItemClickListener;


    public WYASegmentedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WYASegmentedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable
                .WYASegmentedView);
        lineColor = typedArray.getColor(R.styleable.WYASegmentedView_lineColor, getResources()
                .getColor(R.color.segment_default));
        lineSize = typedArray.getDimension(R.styleable.WYASegmentedView_lineSize, dp2px(2));
        titleNormalColor = typedArray.getColor(R.styleable.WYASegmentedView_titleNormalColor,
                getResources().getColor(R.color.segment_default));
        titleSelectedColor = typedArray.getColor(R.styleable.WYASegmentedView_titleSelectColor,
                getResources().getColor(R.color.white));
        itemSelectedColor = typedArray.getColor(R.styleable.WYASegmentedView_itemSelectBackground,
                getResources().getColor(R.color.segment_default));
        itemNormalColor = typedArray.getColor(R.styleable.WYASegmentedView_itemNormalBackground,
                getResources().getColor(R.color.white));

        strokeWidth = typedArray.getDimension(R.styleable.WYASegmentedView_strokeWidth, dp2px(2));
        titleSize = typedArray.getDimension(R.styleable.WYASegmentedView_titleSize, sp2px(14));
        strokeColor = typedArray.getColor(R.styleable.WYASegmentedView_strokeColor, getResources()
                .getColor(R.color.segment_default));

        radius = typedArray.getDimension(R.styleable.WYASegmentedView_radius, dp2px(10));
        typedArray.recycle();
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(lineColor);
        initBackground();
    }

    /**
     * init segmentView Background
     */
    private void initBackground() {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(radius);
        shape.setStroke((int) strokeWidth, strokeColor);
        setBackgroundDrawable(shape);
    }


    /**
     * add child item tab
     *
     * @param tabs item's name array
     */
    public void addTabs(String[] tabs) {
        if (tabs.length < 1) {
            throw new IllegalArgumentException("tabs must more than 1 item!");
        }
        for (int i = 0; i < tabs.length; i++) {
            addTab(tabs[i], i, tabs.length);
        }
        setItemClicked(0);
        invalidate();
    }

    /**
     * add child item tab
     *
     * @param tabs list
     */
    public void addTabs(List<String> tabs) {
        if (tabs.size() < 1) {
            throw new IllegalArgumentException("tabs must more than 1 item!");
        }
        for (int i = 0; i < tabs.size(); i++) {
            addTab(tabs.get(i), i, tabs.size());
        }
        setItemClicked(0);
        invalidate();
    }

    /**
     * add child item tab
     *
     * @param title name
     * @param index position
     * @param size  childCount
     */
    private void addTab(String title, int index, int size) {
        Log.i(TAG, "addTab: "+getMeasuredHeight());
        View view = LayoutInflater.from(mContext).inflate(R.layout.segment_item, this, false);
        view.setOnClickListener(this);
        if ((index + 1) < size) {
            LinearLayout.LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.rightMargin = (int) lineSize;
            layoutParams.height = LayoutParams.MATCH_PARENT;
            view.setLayoutParams(layoutParams);
        }

        addView(view);
        TextView textView = view.findViewById(R.id.segment_item);
        textView.setText(title);
        textView.setTextSize(px2sp(titleSize));
    }


    /**
     * every child draw a line
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        int childCount = getChildCount();
        if (childCount == 0) {
            throw new IllegalArgumentException("SegmentView's child could not zero! ");
        }
        int width = (int) ((getWidth() - lineSize * (childCount - 1)) / childCount);
        for (int i = 1; i < childCount; i++) {
            canvas.drawRect(width * i + (i - 1) * lineSize, 0, (width + lineSize) * i, getHeight()
                    , mPaint);
        }
    }

    /**
     * when the item clicked,change the background
     *
     * @param position which you click
     */
    public void setItemClicked(int position) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            TextView child = getChildAt(i).findViewById(R.id.segment_item);
            GradientDrawable itemBg = new GradientDrawable();
            if (i == 0) {
                itemBg.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, radius, radius});
            } else if (i + 1 == childCount) {
                itemBg.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
            } else {
                itemBg.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
            }
            itemBg.setStroke((int) strokeWidth, strokeColor);
            if (i == position) {
                itemBg.setColor(itemSelectedColor);
                child.setTextColor(titleSelectedColor);
            } else {
                itemBg.setColor(itemNormalColor);
                child.setTextColor(titleNormalColor);
            }
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{itemBg});
            if (i == 0) {
                layerDrawable.setLayerInset(0, 0, 0, (int) -lineSize, 0);
            } else if (i + 1 == childCount) {
                layerDrawable.setLayerInset(0, (int) -lineSize, 0, 0, 0);
            } else {
                layerDrawable.setLayerInset(0, (int) -lineSize, 0, (int) -lineSize, 0);
            }
            child.setBackgroundDrawable(layerDrawable);
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawLine(canvas);
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }



    @Override
    public void onClick(View v) {
        int childCount = getChildCount();
        if (isEnable) {
            for (int i = 0; i < childCount; i++) {
                if (getChildAt(i).equals(v)) {
                    setItemClicked(i);
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.itemClicked(i);
                    }
                }
            }
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        throw new RuntimeException("Don't call setOnClickListener for an AdapterView.You " +
                "probably want setOnItemClickListener instead");
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void itemClicked(int position);
    }


    @Override
    public void setEnabled(boolean enabled) {
        this.isEnable = enabled;
    }

    /**
     * dp2px
     *
     * @param dp
     * @return
     */

    public float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources()
                .getDisplayMetrics());
    }


    /**
     * px2sp
     *
     * @param pxValue
     * @return
     */
    public float px2sp(float pxValue) {
        return pxValue / getResources().getDisplayMetrics().scaledDensity + 0.5f;
    }

    public float sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

}
