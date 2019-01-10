package com.wya.uikit.paginationview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.uikit.R;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class WYAPaginationBottomView extends FrameLayout implements View.OnClickListener {
    private static final String TAG = "WYAPaginationBottomView";
    private Context mContext;
    private TextView mButtonLeft;
    private TextView mButtonRight;
    private TextView mPageTextCurrent;
    private TextView mPageTextAll;
    private EditText mChangeEdit;
    private LinearLayout pageLayout;
    private LinearLayout changeLayout;
    private int allNum;
    private int currentPage;
    private String mLeftText;
    private String mRightText;
    private float mBtnWidth;
    private float mBtnHeight;
    private float mTextSize;
    private int mTextColor;
    private int mSelectColor;
    private OnPageButtonClickListener mOnPageButtonClickListener;
    private onPageSearchListener mPageSearchListener;
    private int mBtnEnableBg = R.drawable.pagination_item_enable;
    private int mBtnBg = R.drawable.pagination_item_click_bg;
    private boolean indexSearch = false;
    
    public WYAPaginationBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public WYAPaginationBottomView(@NonNull Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable
                .WYAPaginationBottomView);
        mLeftText = typedArray.getString(R.styleable.WYAPaginationBottomView_leftText);
        mRightText = typedArray.getString(R.styleable.WYAPaginationBottomView_rightText);
        mBtnWidth = typedArray.getDimension(R.styleable.WYAPaginationBottomView_buttonWidth,
                -1f);
        mBtnHeight = typedArray.getDimension(R.styleable
                .WYAPaginationBottomView_buttonHeight, dp2px(44));
        mTextSize = typedArray.getDimension(R.styleable.WYAPaginationBottomView_textSize,
                sp2px(13));
        mTextColor = typedArray.getColor(R.styleable.WYAPaginationBottomView_textColor, Color
                .BLACK);
        mSelectColor = typedArray.getColor(R.styleable.WYAPaginationBottomView_selectPageTextColor,
                Color.BLACK);
        
        typedArray.recycle();
        init();
    }
    
    private void init() {
        allNum = 10;
        currentPage = 1;
        
        View view = LayoutInflater.from(mContext).inflate(R.layout.pagination_bottom_view, null);
        addView(view);
        
        mButtonLeft = view.findViewById(R.id.pagination_btn_left);
        mButtonRight = view.findViewById(R.id.pagination_btn_right);
        mPageTextCurrent = view.findViewById(R.id.pagination_show_page_current);
        mPageTextAll = view.findViewById(R.id.pagination_show_page_all);
        mChangeEdit = view.findViewById(R.id.pagination_change_page);
        pageLayout = view.findViewById(R.id.pagination_page_layout);
        changeLayout = view.findViewById(R.id.pagination_change_layout);
        mButtonLeft.setOnClickListener(this);
        mButtonRight.setOnClickListener(this);
        pageLayout.setOnClickListener(this);
        
        mButtonLeft.setText(TextUtils.isEmpty(mLeftText) ? "上一页" : mLeftText);
        mButtonRight.setText(TextUtils.isEmpty(mRightText) ? "下一页" : mRightText);
        mButtonLeft.setTextColor(mTextColor);
        mButtonRight.setTextColor(mTextColor);
        mPageTextCurrent.setTextColor(mSelectColor);
        mPageTextCurrent.setTextSize(px2sp(mTextSize));
        mPageTextAll.setTextColor(mTextColor);
        mPageTextAll.setTextSize(px2sp(mTextSize));
        mButtonLeft.setTextSize(px2sp(mTextSize));
        mButtonRight.setTextSize(px2sp(mTextSize));
        
        ViewGroup.LayoutParams layoutParams = mButtonLeft.getLayoutParams();
        if (mBtnWidth != -1f) {
            layoutParams.width = (int) mBtnWidth;
        }
        layoutParams.height = (int) mBtnHeight;
        mButtonLeft.setLayoutParams(layoutParams);
        mButtonRight.setLayoutParams(layoutParams);
        
        searchListener();
    }
    
    /**
     * when you search page
     *
     * @param currentPage searched page
     */
    public void setCurrentPage(int currentPage) {
        changeLayout.setVisibility(GONE);
        pageLayout.setVisibility(VISIBLE);
        this.currentPage = currentPage;
        setCurrentPage();
    }
    
    public void setButtonLeftTextColor(@ColorInt int color) {
        mButtonLeft.setTextColor(color);
    }
    
    public void setButtonRightTextColor(@ColorInt int color) {
        mButtonRight.setTextColor(color);
    }
    
    public void setButtonBackgroundResource(@DrawableRes int resource) {
        mBtnBg = resource;
        mButtonRight.setBackgroundResource(resource);
        mButtonLeft.setBackgroundResource(resource);
    }
    
    @Override
    public void setBackgroundResource(@DrawableRes int resource) {
        View child = getChildAt(0);
        child.setBackgroundResource(resource);
    }
    
    @Override
    public void setBackgroundColor(@ColorInt int color) {
        View child = getChildAt(0);
        child.setBackgroundColor(color);
    }
    
    public void setButtonEnableBackgroundResource(@DrawableRes int resource) {
        mBtnEnableBg = resource;
    }
    
    public void setButtonVisible(boolean isVisible) {
        mButtonLeft.setVisibility(isVisible ? VISIBLE : INVISIBLE);
        mButtonRight.setVisibility(isVisible ? VISIBLE : INVISIBLE);
    }
    
    public void setNumberVisible(boolean isVisible) {
        pageLayout.setVisibility(isVisible ? VISIBLE : INVISIBLE);
    }
    
    public void setButtonLeftDrawable(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mButtonLeft.setCompoundDrawables(drawable, null, null, null);
    }
    
    public void setButtonRightDrawable(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mButtonRight.setCompoundDrawables(null, null, drawable, null);
    }
    
    /**
     * search listener
     */
    private void searchListener() {
        mChangeEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String edit = mChangeEdit.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    if (TextUtils.isEmpty(edit)) {
                        changeLayout.setVisibility(GONE);
                        pageLayout.setVisibility(VISIBLE);
                        keyBoard(false);
                    } else {
                        long page = Long.parseLong(edit);
                        if (page > 0 && page <= allNum) {
                            if (mPageSearchListener != null) {
                                boolean search = mPageSearchListener.onSearch(page);
                                if (search) {
                                    setCurrentPage((int) page);
                                }
                            }
                            keyBoard(false);
                        } else {
                            Toast.makeText(mContext, "请输入正确的页数", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });
    }
    
    /**
     * set page all numbers
     *
     * @param num page numbers
     */
    public void setAllNum(int num) {
        allNum = num;
        currentPage = 1;
        setCurrentPage();
    }
    
    @SuppressLint("DefaultLocale")
    private void setCurrentPage() {
        mPageTextCurrent.setText(String.format("%d", currentPage));
        mPageTextAll.setText(String.format("%d", allNum));
        
        if (currentPage == 1) {
            mButtonLeft.setEnabled(false);
            mButtonLeft.setBackgroundResource(mBtnEnableBg);
        } else {
            mButtonLeft.setEnabled(true);
            mButtonLeft.setBackgroundResource(mBtnBg);
        }
        
        if (allNum == currentPage) {
            mButtonRight.setEnabled(false);
            mButtonRight.setBackgroundResource(mBtnEnableBg);
        } else {
            mButtonRight.setEnabled(true);
            mButtonRight.setBackgroundResource(mBtnBg);
        }
    }
    
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.pagination_btn_left) {
            if (mOnPageButtonClickListener != null) {
                mOnPageButtonClickListener.onClick(1);
            }
            if (currentPage == 1) {
                Toast.makeText(mContext, "这是第一页，已经没有再前面一页了", Toast.LENGTH_SHORT).show();
            } else {
                currentPage--;
                setCurrentPage();
            }
        } else if (i == R.id.pagination_btn_right) {
            if (mOnPageButtonClickListener != null) {
                mOnPageButtonClickListener.onClick(2);
            }
            if (currentPage == allNum) {
                Toast.makeText(mContext, "这是最后一页，已经没有再下一页了", Toast.LENGTH_SHORT).show();
            } else {
                currentPage++;
                setCurrentPage();
            }
    
        } else if (i == R.id.pagination_page_layout) {
            if (indexSearch) {
                pageLayout.setVisibility(GONE);
                changeLayout.setVisibility(VISIBLE);
                mChangeEdit.setText(String.valueOf(currentPage));
                mChangeEdit.setSelection(mChangeEdit.getText().toString().length());
                keyBoard(true);
            }
        }
    }
    
    /**
     * remeasure group and child
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int childHeight = (int) (mBtnHeight + dp2px(20));
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                heightSize = childHeight;
                break;
            case MeasureSpec.EXACTLY:
                if (heightSize < childHeight) {
                    heightSize = childHeight;
                }
                break;
            default:
                break;
        }
    
        setMeasuredDimension(widthSize, heightSize);
        measureChildren(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightSize, heightMode));
    }
    
    /**
     * dp2px
     *
     * @param dp dp
     * @return px
     */
    
    public float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources()
                .getDisplayMetrics());
    }
    
    /**
     * px2sp
     *
     * @param pxValue px
     * @return sp
     */
    private float px2sp(float pxValue) {
        return pxValue / getResources().getDisplayMetrics().scaledDensity + 0.5f;
    }
    
    /**
     * @param spValue sp
     * @return px
     */
    private float sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }
    
    public void setOnPageButtonClickListener(OnPageButtonClickListener onPageButtonClickListener) {
        mOnPageButtonClickListener = onPageButtonClickListener;
    }
    
    public void setPageSearchListener(onPageSearchListener pageSearchListener) {
        mPageSearchListener = pageSearchListener;
    }
    
    public void setIndexSearch(boolean indexSearch) {
        this.indexSearch = indexSearch;
    }
    
    /**
     * keyboard status
     *
     * @param isShow true show false hide
     */
    public void keyBoard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (isShow) {
            imm.showSoftInputFromInputMethod(mChangeEdit.getWindowToken(), InputMethodManager
                    .SHOW_FORCED);
        } else {
            imm.hideSoftInputFromWindow(mChangeEdit.getWindowToken(), InputMethodManager
                    .HIDE_NOT_ALWAYS);
        }
    }
    
    /**
     * pageButton click Interface
     */
    public interface OnPageButtonClickListener {
        /**
         * click callback
         *
         * @param type 1:page up 2:page down
         */
        void onClick(int type);
    }
    
    public interface onPageSearchListener {
        /**
         * true success false failure
         *
         * @param page searched page
         * @return
         */
        boolean onSearch(long page);
    }
}
