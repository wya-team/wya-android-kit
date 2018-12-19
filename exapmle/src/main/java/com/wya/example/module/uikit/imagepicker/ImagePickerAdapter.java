package com.wya.example.module.uikit.imagepicker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    private List<String>images;
    private Context mContext;
    private static final int ADD = 1;
    private static final int IMG = 2;
    private OnItemClickListener mOnItemClickListener;

    public ImagePickerAdapter(List<String> images, Context context) {
        this.images = images;
        mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        if (viewType == ADD) {
            view = LayoutInflater.from(mContext).inflate(R.layout.picker_layout_add_item, viewGroup,false);
            return new AddViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.picker_layout_item, viewGroup,false);
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
            Glide.with(mContext).load(images.get(position)).into(((ImageViewHolder) viewHolder)
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
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_test);
            delete = itemView.findViewById(R.id.delete);
        }
    }



    public interface OnItemClickListener {
        void onDelete(int position);

        void onItemClick(int position);

        void onAddClick();
    }

}
