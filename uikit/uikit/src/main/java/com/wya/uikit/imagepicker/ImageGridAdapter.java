package com.wya.uikit.imagepicker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wya.uikit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/30
 * desc   :	ImageGridAdapter
 * version: 1.0
 */
public class ImageGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private Context mContext;
	private List<LocalImage> mImages;
	private List<LocalImage> mSelectedImages = new ArrayList<>();
	private boolean hasPhoto;
	private OnImageSelectedChangedListener mOnImageSelectedChangedListener;
	private OnImageClickListener mImageClickListener;
	private OnTakePhotoClickListener mPhotoClickListener;
	private int max;

	public ImageGridAdapter(Context context, OnImageSelectedChangedListener listener, int max) {
		mContext = context;
		this.max = max;
		mImages = new ArrayList<>();
		mOnImageSelectedChangedListener = listener;
	}

	/**
	 * update data
	 * @param hasPhoto take photo item  is Visible
	 * @param images data list
	 */
	public void bindData(boolean hasPhoto, List<LocalImage> images) {
		this.hasPhoto = hasPhoto;
		this.mImages = images;
		notifyDataSetChanged();
	}

	/**
	 * update selected iamges
	 * @param imageSelected
	 */
	public void notifySelectedData(List<LocalImage> imageSelected) {
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
					mImageClickListener.onClick(currentPosition, mImages, mSelectedImages, "path");
				}
			});
			final LocalImage localImage = mImages.get(currentPosition);
			imageViewHolder.mCheckBox.setChecked(mSelectedImages.contains(localImage));
			Glide.with(mContext).load(localImage.getPath()).into(imageViewHolder.mImageView);

			//add checkBox listener  change selected images
			imageViewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (((CheckBox) v).isChecked()) {
						if (max == mSelectedImages.size()) {
							((CheckBox) v).setChecked(false);
							Toast.makeText(mContext, "最多选择" + max + "张图片", Toast.LENGTH_SHORT)
									.show();
							return;
						}
						mSelectedImages.add(localImage);
					} else {
						mSelectedImages.remove(localImage);
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
		LinearLayout check;
		ImageView mImageView;
		CheckBox mCheckBox;

		public ImageViewHolder(@NonNull View itemView) {
			super(itemView);
			mImageView = itemView.findViewById(R.id.iv_picture);
			check = itemView.findViewById(R.id.ll_check);
			mCheckBox = itemView.findViewById(R.id.check_box);
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
		void change(List<LocalImage> mSelectedImages);
	}

	/**
	 * image click listener interface that go to gallery
	 */
	public interface OnImageClickListener {
		void onClick(int position, List<LocalImage> mImages, List<LocalImage> mImageSelected,
					 String field);
	}

	/**
	 * take photo listener interface
	 */
	public interface OnTakePhotoClickListener {
		void onClick();
	}

}
