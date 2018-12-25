package com.wya.uikit.searchbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wya.uikit.R;

/**
 * 创建日期：2018/11/22 15:11
 * 作者： Mao Chunjiang
 * 文件名称： WYASearchBar
 * 类说明：自定义搜索框
 */

public class WYASearchBar extends FrameLayout {

    private EditText mEtSearch;
    private ImageView mIvClear;
    private TextView mTvClose;
    private LinearLayout parent;
    private OnTextChangeListener mOnTextChangeListener;
    private OnTvClickListener onTvClickListener;

    /**
     * 设置搜索的图片
     *
     * @param left_res
     */
    public void setEtSearchLeftImg(int left_res) {
        if (left_res > 0) {
            Drawable drawable = getResources().getDrawable(left_res);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.mEtSearch.setCompoundDrawables(drawable, null, null, null);
        } else {
            this.mEtSearch.setCompoundDrawables(null, null, null, null);
        }
    }

    /**
     * 设置编辑框的提示语
     *
     * @param hint_text
     */
    public void setEditHint(String hint_text) {
        mEtSearch.setHint(hint_text);
    }

    public String getEtSearch() {
        return mEtSearch.getText().toString();
    }

    public void setEtSearch(String etSearch) {
        mEtSearch.setText(etSearch);
    }

    public ImageView getIvClear() {
        return mIvClear;
    }

    /**
     * 设置清除文本图片
     *
     * @param ivClear
     */
    public void setIvClear(ImageView ivClear) {
        this.mIvClear = ivClear;
    }

    public TextView getTvClose() {
        return mTvClose;
    }

    public void setTvClose(TextView tvClose) {
        this.mTvClose = tvClose;
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
    public void setOnClickCancelListener(OnTvClickListener onTvClickListener) {
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
        parent = rootView.findViewById(R.id.parent);
        mIvClear = rootView.findViewById(R.id.iv_clear);
        mTvClose = rootView.findViewById(R.id.tv_close);
        addView(rootView, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        mIvClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtSearch.setText("");
            }
        });
        mTvClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTvClickListener != null) {
                    if (mTvClose.getText().equals("取消")) {
                        cancel();
                        onTvClickListener.onClickCancel();
                    } else {//搜索
                        onTvClickListener.onClickSearch();
                    }
                } else {
                    if (mTvClose.getText().equals("取消")) {
                        cancel();
                        onTvClickListener.onClickCancel();
                    }
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
                    mTvClose.setText("搜索");
                } else {
                    mIvClear.setVisibility(View.GONE);
                    mTvClose.setText("取消");
                }
                doTextChange(s);

            }
        });
        mEtSearch.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    mEtSearch.setCursorVisible(true);// 再次点击显示光标
                    if (mEtSearch.getText().toString().length() > 0) {
                        mTvClose.setText("搜索");
                    } else {
                        mTvClose.setText("取消");
                    }
                    mTvClose.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }

    private void doTextChange(Editable s) {
        if (mOnTextChangeListener != null) {
            mOnTextChangeListener.onTextChange(s);
        }
    }

    public void cancel() {
        mEtSearch.setText("");
        mEtSearch.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
        mTvClose.setVisibility(View.GONE);
    }

    public interface OnTextChangeListener {
        void onTextChange(Editable s);
    }

    public interface OnTvClickListener {
        void onClickCancel();

        void onClickSearch();
    }

}
