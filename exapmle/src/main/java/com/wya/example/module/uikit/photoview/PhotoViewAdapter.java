package com.wya.example.module.uikit.photoview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wya.example.R;

import java.util.List;

/**
 * @date: 2018/11/21 11:58
 * @author: Chunjiang Mao
 * @classname: PhotoViewAdapter
 * @describe:
 */

public class PhotoViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<String> data;
    private Context context;

    public PhotoViewAdapter(Context context, int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.data = data;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(context).load(item).apply(new RequestOptions().placeholder(R.drawable.icon_camera).error(R.drawable.icon_camera).transforms(new CenterCrop(), new RoundedCorners(5))).into((ImageView) helper.getView(R.id.image));
        helper.addOnClickListener(R.id.image);
    }
}
