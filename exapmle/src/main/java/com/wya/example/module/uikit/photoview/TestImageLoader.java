package com.wya.example.module.uikit.photoview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wya.example.R;
import com.wya.uikit.photoview.preview.loader.IZoomMediaLoader;
import com.wya.uikit.photoview.preview.loader.MySimpleTarget;

/**
 * Created by yangc on 2017/9/4.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated:
 */

public class TestImageLoader implements IZoomMediaLoader {


    @Override
    public void displayImage(@NonNull Fragment context, @NonNull String path, ImageView imageView, @NonNull final MySimpleTarget simpleTarget) {
        Glide.with(context).load(path).apply(new RequestOptions().error(R.drawable.icon_camera)).into(imageView);
        simpleTarget.onResourceReady();
    }

    @Override
    public void displayGifImage(@NonNull Fragment context, @NonNull String path, ImageView imageView, @NonNull final MySimpleTarget simpleTarget) {
    }
    @Override
    public void onStop(@NonNull Fragment context) {
          Glide.with(context).onStop();
    }

    @Override
    public void clearMemory(@NonNull Context c) {
             Glide.get(c).clearMemory();
    }
}
