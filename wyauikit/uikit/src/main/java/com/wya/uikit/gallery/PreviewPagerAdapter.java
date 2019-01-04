package com.wya.uikit.gallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wya.uikit.R;
import com.wya.uikit.gallery.photoview.PhotoView;

import java.io.File;
import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 */
public class PreviewPagerAdapter extends PagerAdapter {
    private List<String> mList;
    private Context mContext;
    private int changePosition = -1;
    private static final String TYPE = "MPEG/MPG/DAT/AVI/MOV/ASF/WMV/NAVI/3GP/MKV/FLV/F4V/RMVB/WEBM/MP4";

    public PreviewPagerAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    public void updateData(int position) {
        changePosition = position;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        View view = (View) object;
        int tag = (int) view.getTag();
        if (tag == changePosition) {
            return POSITION_NONE;
        } else {
            return POSITION_UNCHANGED;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View contentView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.preview_item, container, false);
        final PhotoView imageView = contentView.findViewById(R.id.preview_image);
        ImageView play = contentView.findViewById(R.id.preview_video_play);
        String imageUrl = mList.get(position);
        Glide.with(mContext).load(imageUrl).into(imageView);

        contentView.setTag(position);

        String[] split = imageUrl.split("[.]");
        String mediaType = split[split.length - 1];
        play.setVisibility(isVideo(mediaType) ? View.VISIBLE : View.INVISIBLE);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                File file = new File(imageUrl);
                Uri uri;
                if (Build.VERSION.SDK_INT >= 24) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", file);
                } else {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    uri = Uri.fromFile(file);
                }

                intent.setDataAndType(uri, "video/" + mediaType);
                if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "没有播放器可以播放视频", Toast.LENGTH_SHORT).show();
                }
            }
        });
        container.addView(contentView, 0);
        return contentView;
    }


    private boolean isVideo(String mediaType) {
        return TYPE.contains(mediaType.toUpperCase());
    }
}
