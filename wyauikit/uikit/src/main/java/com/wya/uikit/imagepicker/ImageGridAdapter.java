package com.wya.uikit.imagepicker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wya.uikit.R;

import java.util.ArrayList;
import java.util.List;

/**
 *  @author : XuDonglin
 *  @time   : 2019-01-10
 *  @description     : 图片选择适配器
 */
public class ImageGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private Context mContext;
	private List<LocalMedia> mImages;
	private List<LocalMedia> mSelectedImages = new ArrayList<>();
	private boolean hasPhoto;
	private OnImageSelectedChangedListener mOnImageSelectedChangedListener;
	private OnImageClickListener mImageClickListener;
	private OnTakePhotoClickListener mPhotoClickListener;
	private int max;
	private static final String TAG="ImageGridAdapter";

	private static final String VIDEO = "video";
	private static final String IMAGE = "image";

	public ImageGridAdapter(Context context, OnImageSelectedChangedListener listener, int max) {
		mContext = context;
		this.max = max;
		mImages = new ArrayList<>();
		mOnImageSelectedChangedListener = listener;
	}

	/**
	 * updateIsShow data
	 * @param hasPhoto take photo item  is Visible
	 * @param images data list
	 */
	public void bindData(boolean hasPhoto, List<LocalMedia> images) {
		this.hasPhoto = hasPhoto;
		this.mImages = images;
		notifyDataSetChanged();
	}

	public void bindData(List<LocalMedia> images) {
		this.mImages = images;
		notifyDataSetChanged();
	}

	/**
	 * updateIsShow selected iamges
	 * @param imageSelected
	 */
	public void notifySelectedData(List<LocalMedia> imageSelected) {
		this.mSelectedImages = imageSelected;
		notifyDataSetChanged();
	}

	@Override
	public int getItemViewType(int position) {
		if (hasPhoto) {
			if (position == 0) {
				return PickerConfig.PHOTO;
			} else {
				return PickerConfig.IMAGE;
			}
		} else {
			return PickerConfig.IMAGE;
		}
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
		if (viewType == PickerConfig.PHOTO) {
			View view = LayoutInflater.from(mContext).inflate(R.layout.photo_adapter_item,
					viewGroup, false);
			return new PhotoViewHolder(view);
		} else {
			View view = LayoutInflater.from(mContext).inflate(R.layout.image_adapter_item,
					viewGroup, false);
			return new ImageViewHolder(view);
		}

	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
		if (getItemViewType(position) == PickerConfig.PHOTO) {
			PhotoViewHolder photoViewHolder = (PhotoViewHolder) viewHolder;
			photoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mPhotoClickListener.onClick();
				}
			});
		} else {
			final ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
			final int currentPosition = hasPhoto ? position - 1 : position;
			imageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mImageClickListener.onClick(currentPosition, mImages, mSelectedImages);
				}
			});
			final LocalMedia localMedia = mImages.get(currentPosition);
			imageViewHolder.mCheckBox.setChecked(mSelectedImages.contains(localMedia));

			//check is video type
			if (localMedia.getType().contains(VIDEO)) {
                imageViewHolder.mVideoLayout.setVisibility(View.VISIBLE);
                imageViewHolder.mDuration.setText(dateFormate(localMedia.getVideoDuration()));
			} else if (localMedia.getType().contains(IMAGE)) {
                imageViewHolder.mVideoLayout.setVisibility(View.GONE);
			}
			String cropPath = localMedia.getCropPath();
			if (TextUtils.isEmpty(cropPath)) {
				Glide.with(mContext).load(localMedia.getPath()).into(imageViewHolder.mImageView);
				imageViewHolder.cropTag.setVisibility(View.INVISIBLE);
			} else {
				Glide.with(mContext).load(cropPath).into(imageViewHolder.mImageView);
				imageViewHolder.cropTag.setVisibility(View.VISIBLE);
			}


			//add checkBox listener  change selected images
			imageViewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (((CheckBox) v).isChecked()) {
						if (max == mSelectedImages.size()) {
							((CheckBox) v).setChecked(false);
							Toast.makeText(mContext, "最多选择" + max + "张图片", Toast.LENGTH_SHORT).show();
							return;
						}
						mSelectedImages.add(localMedia);
					} else {
						mSelectedImages.remove(localMedia);
					}
					mOnImageSelectedChangedListener.change(mSelectedImages);

				}
			});

		}
	}

	@Override
	public int getItemCount() {
		return hasPhoto ? mImages.size() + 1 : mImages.size();
	}

	public void setImageClickListener(OnImageClickListener imageClickListener) {
		mImageClickListener = imageClickListener;
	}

	public void setPhotoClickListener(OnTakePhotoClickListener photoClickListener) {
		mPhotoClickListener = photoClickListener;
	}

	class ImageViewHolder extends RecyclerView.ViewHolder {
		ImageView mImageView;
		ImageView cropTag;
		CheckBox mCheckBox;
        LinearLayout mVideoLayout;
        TextView mDuration;

		public ImageViewHolder(@NonNull View itemView) {
			super(itemView);
			mImageView = itemView.findViewById(R.id.iv_picture);
			mCheckBox = itemView.findViewById(R.id.check_box);
            mVideoLayout = itemView.findViewById(R.id.video_msg);
            mDuration = itemView.findViewById(R.id.video_duration);
			cropTag = itemView.findViewById(R.id.crop_tag);
		}
	}

	class PhotoViewHolder extends RecyclerView.ViewHolder {

		public PhotoViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}

	/**
	 * selected images change listener interface
	 */
	public interface OnImageSelectedChangedListener {
		void change(List<LocalMedia> mSelectedImages);
	}

	/**
	 * image click listener interface that go to gallery
	 */
	public interface OnImageClickListener {
		void onClick(int position, List<LocalMedia> mImages, List<LocalMedia> mImageSelected);
	}

	/**
	 * take photo listener interface
	 */
	public interface OnTakePhotoClickListener {
		void onClick();
	}


    private String dateFormate(String duration) {
        long time = Long.parseLong(duration);
        String min = String.valueOf(time / 60000);
        String sec = String.valueOf((time % 60000) / 1000);
        min = min.length() == 1 ? String.format("%s%s", "0", min) : min;
        sec = sec.length() == 1 ? String.format("%s%s", "0", sec) : sec;
        return String.format("%s%s%s", min, ":", sec);
    }
}
