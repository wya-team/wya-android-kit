package com.weiyian.android.mvvm.binding

import android.databinding.BindingAdapter
import android.widget.ImageView

@BindingAdapter("bind_imageView_url")
fun loadImage(imageView: ImageView, url: String?) {
//    GlideApp.with(imageView.context)
//            .load(url)
//            .into(imageView)
}

@BindingAdapter("bind_imageView_url_circle")
fun loadImageCircle(imageView: ImageView, url: String?) {
//    GlideApp.with(imageView.context)
//            .load(url)
//            .apply(RequestOptions().circleCrop())
//            .into(imageView)
}