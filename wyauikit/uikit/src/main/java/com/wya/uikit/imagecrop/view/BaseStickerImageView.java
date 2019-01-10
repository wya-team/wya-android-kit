package com.wya.uikit.imagecrop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.wya.uikit.R;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class BaseStickerImageView extends BaseStickerView {

    private ImageView mImageView;

    public BaseStickerImageView(Context context) {
        super(context);
    }

    public BaseStickerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseStickerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View onCreateContentView(Context context) {
        mImageView = new ImageView(context);
        mImageView.setImageResource(R.drawable.icon_photo);
        return mImageView;
    }
}
