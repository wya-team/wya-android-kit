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
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class SelectedRecyclerAdapter extends RecyclerView.Adapter<SelectedRecyclerAdapter.SelectViewHolder> {
    
    private static final String TYPE = "MPEG/MPG/DAT/AVI/MOV/ASF/WMV/NAVI/3GP/MKV/FLV/F4V/RMVB/WEBM/MP4";
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
            Glide.with(mContext).load(cropPath).into(selectViewHolder.selectPic);
        } else {
            Glide.with(mContext).load(mediaPath).into(selectViewHolder.selectPic);
        }
        String[] split = mediaPath.split("[.]");
        String mediaType = split[split.length - 1];
        selectViewHolder.selectIsVideo.setVisibility(isVideo(mediaType) ? View.VISIBLE : View.INVISIBLE);
        selectViewHolder.selectView.setVisibility(mIndexList.get(position) == mSelectedIndex ? View.VISIBLE : View.INVISIBLE);
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
    
    private boolean isVideo(String mediaType) {
        return TYPE.contains(mediaType.toUpperCase());
    }
    
    public interface OnItemClickListener {
        /**
         * onClick
         *
         * @param position
         */
        void onClick(int position);
    }
    
    class SelectViewHolder extends RecyclerView.ViewHolder {
        ImageView selectPic;
        ImageView selectIsVideo;
        View selectView;
    
        public SelectViewHolder(@NonNull View itemView) {
            super(itemView);
            selectPic = itemView.findViewById(R.id.select_pic);
            selectIsVideo = itemView.findViewById(R.id.select_is_video);
            selectView = itemView.findViewById(R.id.select_view);
        }
    }
}
