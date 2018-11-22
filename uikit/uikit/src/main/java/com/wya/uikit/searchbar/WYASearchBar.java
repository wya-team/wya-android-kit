package com.wya.uikit.searchbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
    private OnTextChangeListener mOnTextChangeListener;
    private OnClickCancelListener onClickCancelListener;

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

    public EditText getEtSearch() {
        return mEtSearch;
    }

    public void setEtSearch(EditText etSearch) {
        this.mEtSearch = etSearch;
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
     * @param onClickCancelListener
     */
    public void setOnClickCancelListener(OnClickCancelListener onClickCancelListener) {
        this.onClickCancelListener = onClickCancelListener;
    }

    public WYASearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.wya_search_bar_layout, null);
        mEtSearch = rootView.findViewById(R.id.et_search);
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
                if (onClickCancelListener != null) {
                    onClickCancelListener.onClickCancel();
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
                } else {
                    mIvClear.setVisibility(View.GONE);
                }
                doTextChange(s);

            }
        });
    }

    private void doTextChange(Editable s) {
        if (mOnTextChangeListener != null) {
            mOnTextChangeListener.onTextChange(s);
        }
    }

    public interface OnTextChangeListener {
        void onTextChange(Editable s);
    }

    public interface OnClickCancelListener {
        void onClickCancel();
    }
}
