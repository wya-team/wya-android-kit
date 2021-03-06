package com.wya.example.module.uikit.imagepicker;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wya.example.R;

import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/19
 * desc   :
 * version: 1.0
 */
public class ImagePickerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> images;
    private Context mContext;
    private static final int ADD = 1;
    private static final int IMG = 2;
    private OnItemClickListener mOnItemClickListener;
    private static final String type = "MPEG/MPG/DAT/AVI/MOV/ASF/WMV/NAVI/3GP/MKV/FLV/F4V/RMVB/WEBM/MP4";

    public ImagePickerAdapter(List<String> images, Context context) {
        this.images = images;
        mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        if (viewType == ADD) {
            view = LayoutInflater.from(mContext).inflate(R.layout.picker_layout_add_item, viewGroup, false);
            return new AddViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.picker_layout_item, viewGroup, false);
            return new ImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof AddViewHolder) {
            viewHolder.itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onAddClick();
                }
            });
        }

        if (viewHolder instanceof ImageViewHolder) {

            String url = images.get(position);
            String[] split = url.split("[.]");
            //check is video type
            if (isVideo(split[split.length - 1])) {
                MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
                metadataRetriever.setDataSource(url);
                String duration = metadataRetriever.extractMetadata(android.media.MediaMetadataRetriever
                        .METADATA_KEY_DURATION);
                ((ImageViewHolder) viewHolder).video_msg.setVisibility(View.VISIBLE);
                ((ImageViewHolder) viewHolder).video_duration.setText(dateFormate(duration));
                metadataRetriever.release();
            } else {
                ((ImageViewHolder) viewHolder).video_msg.setVisibility(View.GONE);
            }


            Glide.with(mContext).load(url).into(((ImageViewHolder) viewHolder)
                    .mImageView);
            ((ImageViewHolder) viewHolder).mImageView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            });

            ((ImageViewHolder) viewHolder).delete.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onDelete(position);
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.isEmpty(images.get(position))) {
            return ADD;
        } else {
            return IMG;
        }

    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    class AddViewHolder extends RecyclerView.ViewHolder {
        public AddViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        LinearLayout delete;
        LinearLayout video_msg;
        TextView video_duration;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_test);
            delete = itemView.findViewById(R.id.delete);
            video_msg = itemView.findViewById(R.id.video_msg);
            video_duration = itemView.findViewById(R.id.video_duration);
        }
    }


    public interface OnItemClickListener {
        void onDelete(int position);

        void onItemClick(int position);

        void onAddClick();
    }


    private String dateFormate(String duration) {
        long time = Long.parseLong(duration);
        String min = String.valueOf(time / 60000);
        String sec = String.valueOf((time % 60000) / 1000);
        min = min.length() == 1 ? String.format("%s%s", "0", min) : min;
        sec = sec.length() == 1 ? String.format("%s%s", "0", sec) : sec;
        return String.format("%s%s%s", min, ":", sec);
    }

    private boolean isVideo(String mediaType) {
        return type.contains(mediaType.toUpperCase());
    }
}
