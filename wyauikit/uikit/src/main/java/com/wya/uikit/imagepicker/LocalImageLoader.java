package com.wya.uikit.imagepicker;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/29
 * desc   :	LocalImageLoader
 * version: 1.0
 */
public class LocalImageLoader {
	private FragmentActivity mActivity;

	private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
	private static final String ORDER_BY = MediaStore.Files.FileColumns.DATE_ADDED;
	// 媒体文件数据库字段
	private static final String[] PROJECTION = {
			MediaStore.Files.FileColumns._ID,
			MediaStore.MediaColumns.DATA,
			MediaStore.MediaColumns.MIME_TYPE,
			MediaStore.MediaColumns.WIDTH,
			MediaStore.MediaColumns.HEIGHT};

	// 图片
	private static final String SELECTION = MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
			+ " AND " + MediaStore.MediaColumns.SIZE + ">0";

	private OnLoadImageListener mListener;

	private LocalImageLoader(FragmentActivity activity) {
		this.mActivity = activity;
	}

	public static LocalImageLoader create(FragmentActivity activity) {
		return new LocalImageLoader(activity);
	}

	public void LoadImage(final OnLoadImageListener imageListener) {
		mActivity.getSupportLoaderManager().initLoader(PickerConfig.LOADER_IMAGE, null,
				new LoaderManager.LoaderCallbacks<Cursor>() {


					@NonNull
					@Override
					public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
						CursorLoader cursorLoader = new CursorLoader(mActivity, QUERY_URI,
								PROJECTION, SELECTION, new String[]{String.valueOf(MediaStore
								.Files.FileColumns.MEDIA_TYPE_IMAGE)}, ORDER_BY);
						return cursorLoader;
					}

					@Override
					public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor
							cursor) {
						//add data
						List<LocalImage> mAllLocalImages = new ArrayList<>();
						List<LocalImageFolder> mReturnFolders = new ArrayList<>();
						LocalImageFolder allImageFolder = new LocalImageFolder();
						if (cursor != null) {
							if (cursor.getCount() > 0) {
								cursor.moveToLast();
								do {
									String path = cursor.getString
											(cursor.getColumnIndexOrThrow(PROJECTION[1]));
									String pictureType = cursor.getString
											(cursor.getColumnIndexOrThrow(PROJECTION[2]));
									int width = cursor.getInt
											(cursor.getColumnIndexOrThrow(PROJECTION[3]));
									int height = cursor.getInt
											(cursor.getColumnIndexOrThrow(PROJECTION[4]));

									LocalImage localImage = new LocalImage(path, pictureType,
											width, height);
									//add all
									mAllLocalImages.add(localImage);

									//add single folder
									LocalImageFolder imageFolder = getImageFolder(path,
											mReturnFolders);
									List<LocalImage> images = imageFolder.getImages();
									images.add(localImage);
									int imageNum = imageFolder.getImageNum();
									imageFolder.setImageNum(imageNum + 1);


								} while (cursor.moveToPrevious());

								allImageFolder.setImages(mAllLocalImages);
								allImageFolder.setName("所有图片");
								allImageFolder.setImageNum(mAllLocalImages.size());
								allImageFolder.setFirstImagePath(mAllLocalImages.get(0).getPath());
								mReturnFolders.add(0,allImageFolder);
							}

						}

						if (imageListener != null) {
							imageListener.completed(mReturnFolders);
						}

					}

					@Override
					public void onLoaderReset(@NonNull Loader<Cursor> loader) {

					}
				});
	}


	/**
	 * create LocalImageFolder to save LocalImage
	 *
	 * @param path image path
	 * @param imageFolders folder list
	 * @return
	 */
	private LocalImageFolder getImageFolder(String path, List<LocalImageFolder> imageFolders) {
		File imageFile = new File(path);
		File folderFile = imageFile.getParentFile();
		for (LocalImageFolder folder : imageFolders) {
			if (folder.getName().equals(folderFile.getName())) {
				return folder;
			}
		}
		//create new folder and set the first image's path
		LocalImageFolder newFolder = new LocalImageFolder();
		newFolder.setName(folderFile.getName());
		newFolder.setFirstImagePath(path);
		imageFolders.add(newFolder);
		return newFolder;
	}



	public interface OnLoadImageListener {
		void completed(List<LocalImageFolder> localImageFolders);
	}
}
