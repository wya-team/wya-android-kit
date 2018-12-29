package com.wya.uikit.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wya.uikit.R;
import com.wya.uikit.imagepicker.LocalMedia;

import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/28
 * desc   :
 * version: 1.0
 */
public class SelectedRecyclerAdapter extends RecyclerView.Adapter<SelectedRecyclerAdapter.SelectViewHolder> {

    private List<LocalMedia> mSelectedList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<Integer> mIndexList;
    private int mSelectedIndex;

    public SelectedRecyclerAdapter(List<LocalMedia> selectedList, Context
            context) {
        mSelectedList = selectedList;
        mContext = context;
    }

    public void updateSelected(int selectedIndex, List<Integer> indexList) {
        mIndexList = indexList;
        mSelectedIndex = selectedIndex;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_recycler_item, viewGroup, false);
        return new SelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder selectViewHolder, int position) {
        String mediaPath = mSelectedList.get(position).getPath();
        String cropPath = mSelectedList.get(position).getCropPath();
        if (!TextUtils.isEmpty(cropPath)) {
            Glide.with(mContext).load(cropPath).into(selectViewHolder.select_pic);
        } else {
            Glide.with(mContext).load(mediaPath).into(selectViewHolder.select_pic);
        }
        String[] split = mediaPath.split("[.]");
        String mediaType = split[split.length - 1];
        selectViewHolder.select_is_video.setVisibility(isVideo(mediaType) ? View.VISIBLE : View.INVISIBLE);
        selectViewHolder.select_view.setVisibility(mIndexList.get(position) == mSelectedIndex ? View.VISIBLE : View.INVISIBLE);
        selectViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mSelectedList != null) {
            return mSelectedList.size();
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    class SelectViewHolder extends RecyclerView.ViewHolder {
        ImageView select_pic;
        ImageView select_is_video;
        View select_view;

        public SelectViewHolder(@NonNull View itemView) {
            super(itemView);
            select_pic = itemView.findViewById(R.id.select_pic);
            select_is_video = itemView.findViewById(R.id.select_is_video);
            select_view = itemView.findViewById(R.id.select_view);
        }
    }

    private static final String type = "MPEG/MPG/DAT/AVI/MOV/ASF/WMV/NAVI/3GP/MKV/FLV/F4V/RMVB/WEBM/MP4";

    private boolean isVideo(String mediaType) {
        return type.contains(mediaType.toUpperCase());
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }
}
