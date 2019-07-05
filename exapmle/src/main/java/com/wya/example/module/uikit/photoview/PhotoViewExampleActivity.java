package com.wya.example.module.uikit.photoview;

import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.photoview.preview.GPreviewBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2019/7/4 17:04
 * @author: Chunjiang Mao
 * @classname: PhotoViewExampleActivity
 * @describe: 图片查看
 */
public class PhotoViewExampleActivity extends BaseActivity {

    @BindView(R.id.photo)
    RecyclerView recyclerView;

    private PhotoViewAdapter photoViewAdapter;
    private List<String> imageItems;
    private List<ImageItem> imageItem = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_view_example;
    }

    @Override
    protected void initView() {
        initData();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        photoViewAdapter = new PhotoViewAdapter(this, R.layout.item_photo_view_item, imageItems);
        photoViewAdapter.bindToRecyclerView(recyclerView);
        photoViewAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                imageItem = new ArrayList<>();
                computeBoundsBackward(imageItem, adapter);
                GPreviewBuilder.from(PhotoViewExampleActivity.this)
                        .setData(imageItem)
                        .setCurrentIndex(position)
                        .setType(GPreviewBuilder.IndicatorType.Dot)
                        .setSingleFling(true)
                        .setSingleShowType(false)
                        .start();
            }
        });
    }

    private void initData() {
        imageItems = new ArrayList<>();
        imageItems.add("https://wyatest.oss-cn-hangzhou.aliyuncs.com/image/172/20190613/141510/image_0.jpg");
        imageItems.add("https://wyatest.oss-cn-hangzhou.aliyuncs.com/image/172/20190613/142305/image_0.jpg");
        imageItems.add("https://wyatest.oss-cn-hangzhou.aliyuncs.com/image/172/20190613/141510/image_0.jpg");
        imageItems.add("https://wyatest.oss-cn-hangzhou.aliyuncs.com/image/172/20190613/141510/image_0.jpg");
    }

    /**
     * 查找信息
     *
     * @param list         图片集合
     * @param imagesLayout
     */
    private void computeBoundsBackward(List<ImageItem> list, BaseQuickAdapter adapter) {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            View itemView = adapter.getViewByPosition(i, R.id.image);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView;
                thumbView.getGlobalVisibleRect(bounds);
            }
            ImageItem imageItem = new ImageItem();
            imageItem.setBounds(bounds);
            imageItem.setUrl(imageItems.get(i));
            list.add(imageItem);
        }
    }

}
