package com.wya.uikit.imagecrop.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.wya.uikit.imagecrop.core.EditText;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class BaseStickerTextView extends BaseStickerView implements TextEditDialog.Callback {

    private static final String TAG = "StickerTextView";

    private TextView mTextView;

    private EditText mText;

    private TextEditDialog mDialog;

    private static float mBaseTextSize = -1f;

    private static final int PADDING = 26;

    private static final float TEXT_SIZE_SP = 24f;

    public BaseStickerTextView(Context context) {
        this(context, null, 0);
    }

    public BaseStickerTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseStickerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onInitialize(Context context) {
        if (mBaseTextSize <= 0) {
            mBaseTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    TEXT_SIZE_SP, context.getResources().getDisplayMetrics());
        }
        super.onInitialize(context);
    }

    @Override
    public View onCreateContentView(Context context) {
        mTextView = new TextView(context);
        mTextView.setTextSize(mBaseTextSize);
        mTextView.setPadding(PADDING, PADDING, PADDING, PADDING);
        mTextView.setTextColor(Color.WHITE);

        return mTextView;
    }

    public void setText(EditText text) {
        mText = text;
        if (mText != null && mTextView != null) {
            mTextView.setText(mText.getText());
            mTextView.setTextColor(mText.getColor());
        }
    }

    public EditText getText() {
        return mText;
    }

    @Override
    public void onContentTap() {
        TextEditDialog dialog = getDialog();
        dialog.setText(mText);
        dialog.show();
    }

    private TextEditDialog getDialog() {
        if (mDialog == null) {
            mDialog = new TextEditDialog(getContext(), this);
        }
        return mDialog;
    }

    @Override
    public void onText(EditText text) {
        mText = text;
        if (mText != null && mTextView != null) {
            mTextView.setText(mText.getText());
            mTextView.setTextColor(mText.getColor());
        }
    }
}