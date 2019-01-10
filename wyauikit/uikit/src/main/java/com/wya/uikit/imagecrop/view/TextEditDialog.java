package com.wya.uikit.imagecrop.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.RadioGroup;

import com.wya.uikit.R;
import com.wya.uikit.imagecrop.core.EditText;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class TextEditDialog extends Dialog implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {
    
    private static final String TAG = "TextEditDialog";
    
    private android.widget.EditText mEditText;
    
    private Callback mCallback;
    
    private EditText mDefaultText;
    
    private EditColorGroup mColorGroup;
    
    public TextEditDialog(Context context, Callback callback) {
        super(context, R.style.ImageTextDialog);
        setContentView(R.layout.image_text_dialog);
        mCallback = callback;
        Window window = getWindow();
        if (window != null) {
            window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mColorGroup = findViewById(R.id.cg_colors);
        mColorGroup.setOnCheckedChangeListener(this);
        mEditText = findViewById(R.id.et_text);
        
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_done).setOnClickListener(this);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        if (mDefaultText != null) {
            mEditText.setText(mDefaultText.getText());
            mEditText.setTextColor(mDefaultText.getColor());
            if (!mDefaultText.isEmpty()) {
                mEditText.setSelection(mEditText.length());
            }
            mDefaultText = null;
        } else {
            mEditText.setText("");
        }
        mColorGroup.setCheckColor(mEditText.getCurrentTextColor());
    }
    
    public void setText(EditText text) {
        mDefaultText = text;
    }
    
    public void reset() {
        setText(new EditText(null, Color.WHITE));
    }
    
    @Override
    public void onClick(View v) {
        int vid = v.getId();
        if (vid == R.id.tv_done) {
            onDone();
        } else if (vid == R.id.tv_cancel) {
            dismiss();
        }
    }
    
    private void onDone() {
        String text = mEditText.getText().toString();
        if (!TextUtils.isEmpty(text) && mCallback != null) {
            mCallback.onText(new EditText(text, mEditText.getCurrentTextColor()));
        }
        dismiss();
    }
    
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        mEditText.setTextColor(mColorGroup.getCheckColor());
    }
    
    public interface Callback {
        
        /**
         * onText
         *
         * @param text
         */
        void onText(EditText text);
    }
}
