package com.wya.uikit.searchbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.wya.uikit.R;

/**
 * @date: 2018/11/22 15:11
 * @author: Chunjiang Mao
 * @classname:  WYASearchBar
 * @describe: 自定义搜索框
 */

public class WYASearchBar extends FrameLayout {

    private EditText mEtSearch;
    private ImageView mIvClear;
    private TextView mTvClose;
    private TextView tvSearchUp;
    private TableRow tabUp;
    private OnTextChangeListener mOnTextChangeListener;
    private OnTextClickListener onTvClickListener;
    private static final String CANCEL = "取消";
    private static final String SEARCH = "搜索";

    /**
     * 设置搜索的图片
     *
     * @param leftRes
     */
    public void setEtSearchLeftImg(int leftRes) {
        if (leftRes > 0) {
            Drawable drawable = getResources().getDrawable(leftRes);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.mEtSearch.setCompoundDrawables(drawable, null, null, null);
        } else {
            this.mEtSearch.setCompoundDrawables(null, null, null, null);
        }
    }

    /**
     * 设置编辑框的提示语
     *
     * @param hintText
     */
    public void setEditHint(String hintText) {
        mEtSearch.setHint(hintText);

    }

    /**
     * 设置编辑框的提示语
     *
     * @param hintText
     */
    public void setTextHint(String hintText) {
        tvSearchUp.setHint(hintText);
    }

    public String getEtSearch() {
        return mEtSearch.getText().toString();
    }

    public void setEditSearch(String etSearch) {
        mEtSearch.setText(etSearch);
    }

    public void setTextSearch(String tvSearch) {
        tvSearchUp.setText(tvSearch);
    }


    /**
     * 设置搜索框的监听
     *
     * @param onTextChangeListener
     */
    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.mOnTextChangeListener = onTextChangeListener;
    }

    /**
     * 设置取消按钮的监听
     *
     * @param onTvClickListener
     */
    public void setOnClickCancelListener(OnTextClickListener onTvClickListener) {
        this.onTvClickListener = onTvClickListener;
    }

    public WYASearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.wya_search_bar_layout, null);
        mEtSearch = rootView.findViewById(R.id.et_search);
        tabUp = rootView.findViewById(R.id.tab_up);
        tvSearchUp = rootView.findViewById(R.id.tv_search_up);
        mIvClear = rootView.findViewById(R.id.iv_clear);
        mTvClose = rootView.findViewById(R.id.tv_close);
        addView(rootView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        mIvClear.setOnClickListener(v -> mEtSearch.setText(""));
        mTvClose.setOnClickListener(v -> {
            if (onTvClickListener != null) {
                if (CANCEL.equals(mTvClose.getText())) {
                    cancel();
                    onTvClickListener.onClickCancel();
                } else {//搜索
                    onTvClickListener.onClickSearch();
                }
            } else {
                if (CANCEL.equals(mTvClose.getText())) {
                    cancel();
                    onTvClickListener.onClickCancel();
                }
            }
        });
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    mIvClear.setVisibility(View.VISIBLE);
                    mTvClose.setText(SEARCH);
                } else {
                    mIvClear.setVisibility(View.GONE);
                    mTvClose.setText(CANCEL);
                }
                doTextChange(s);

            }
        });
        mEtSearch.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_DOWN == event.getAction()) {
                mEtSearch.setCursorVisible(true);
                if (mEtSearch.getText().toString().length() > 0) {
                    mTvClose.setText(SEARCH);
                } else {
                    mTvClose.setText(CANCEL);
                }
                mTvClose.setVisibility(View.VISIBLE);
            }
            return false;
        });
        tabUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                slideView(0, -((tabUp.getWidth() / 2 - tvSearchUp.getWidth() / 2)));
                setOnTouch();
            }
        });
    }


    public void slideView(final float p1, final float p2) {
        TranslateAnimation animation = new TranslateAnimation(p1, p2, 0, 0);
        animation.setDuration(300);
        //使其可以填充效果从而不回到原地
        animation.setFillEnabled(true);
        //不回到起始位置
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvSearchUp.clearAnimation();
                tabUp.setVisibility(View.GONE);
            }
        });
        tvSearchUp.startAnimation(animation);
    }

    private void setOnTouch() {
        final long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(
                downTime, downTime, MotionEvent.ACTION_DOWN, mEtSearch.getX(), mEtSearch.getY(), 0);
        final MotionEvent upEvent = MotionEvent.obtain(
                downTime, SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, mEtSearch.getX(), mEtSearch.getY(), 0);
        mEtSearch.onTouchEvent(downEvent);
        mEtSearch.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();

        mEtSearch.setCursorVisible(true);
        mTvClose.setText(CANCEL);
        mTvClose.setVisibility(View.VISIBLE);
    }

    private void doTextChange(Editable s) {
        if (mOnTextChangeListener != null) {
            mOnTextChangeListener.onTextChange(s);
        }
    }

    public void cancel() {
        mEtSearch.setText("");
        // 内容清空后将编辑框1的光标隐藏，提升用户的体验度
        mEtSearch.setCursorVisible(false);
        mTvClose.setVisibility(View.GONE);
        tabUp.setVisibility(View.VISIBLE);
    }

    public interface OnTextChangeListener {
        void onTextChange(Editable s);
    }

    public interface OnTextClickListener {
        void onClickCancel();

        void onClickSearch();
    }

}
