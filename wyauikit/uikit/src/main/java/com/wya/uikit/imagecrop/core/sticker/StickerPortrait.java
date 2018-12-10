package com.wya.uikit.imagecrop.core.sticker;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;


public interface StickerPortrait {

    boolean show();

    boolean remove();

    boolean dismiss();

    boolean isShowing();

    RectF getFrame();

//    RectF getAdjustFrame();
//
//    RectF getDeleteFrame();

    void onSticker(Canvas canvas);

    void registerCallback(Sticker.Callback callback);

    void unregisterCallback(Sticker.Callback callback);

    interface Callback {

        <V extends View & Sticker> void onDismiss(V stickerView);

        <V extends View & Sticker> void onShowing(V stickerView);

        <V extends View & Sticker> boolean onRemove(V stickerView);
    }
}
