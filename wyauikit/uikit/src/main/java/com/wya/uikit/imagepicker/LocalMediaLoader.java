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
import java.util.Locale;

/**
 *  @author : XuDonglin
 *  @time   : 2019-01-10
 *  @description     : 本地媒体数据加载类
 */
public class LocalMediaLoader {
    private FragmentActivity mActivity;
    
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    private static final String ORDER_BY = MediaStore.Files.FileColumns.DATE_ADDED;
    /**
     * 时间长度
     */
    private static final String DURATION = "duration";
    /**
     * 媒体文件数据库字段
     */
    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.MIME_TYPE,
            DURATION};
    
    /**
     * 图片
     */
    private static final String SELECTION = MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + " AND " + MediaStore.MediaColumns.SIZE + ">0";
    private long videoMaxS = 0;
    private long videoMinS = 0;
    
    private OnLoadImageListener mListener;
    
    private static final String VIDEO = "video";
    
    /**
     * 全部模式下条件
     *
     * @param timeCondition
     *
     * @return
     */
    private static String getSelectionArgsForAllMediaCondition(String timeCondition) {
        String condition = "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                + " OR "
                + (MediaStore.Files.FileColumns.MEDIA_TYPE + "=? AND " + timeCondition) + ")"
                + " AND " + MediaStore.MediaColumns.SIZE + ">0";
        return condition;
    }
    
    private LocalMediaLoader(FragmentActivity activity) {
        this.mActivity = activity;
    }
    
    public static LocalMediaLoader create(FragmentActivity activity) {
        return new LocalMediaLoader(activity);
    }
    
    public void loadImage(final OnLoadImageListener imageListener) {
        mActivity.getSupportLoaderManager().initLoader(PickerConfig.LOADER_IMAGE, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {
                    
                    @NonNull
                    @Override
                    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
                        CursorLoader cursorLoader = new CursorLoader(mActivity, QUERY_URI,
                                PROJECTION, getSelectionArgsForAllMediaCondition(getDurationCondition(0, 0)), new
                                String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE), String.valueOf
                                (MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)}, ORDER_BY);
                        return cursorLoader;
                    }
                    
                    @Override
                    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor
                            cursor) {
                        //add data
                        List<LocalMedia> mAllLocalMedia = new ArrayList<>();
                        List<LocalMediaFolder> mReturnFolders = new ArrayList<>();
                        LocalMediaFolder allImageFolder = new LocalMediaFolder();
                        if (cursor != null) {
                            if (cursor.getCount() > 0) {
                                cursor.moveToLast();
                                do {
                                    String path = cursor.getString
                                            (cursor.getColumnIndexOrThrow(PROJECTION[1]));
                                    String pictureType = cursor.getString
                                            (cursor.getColumnIndexOrThrow(PROJECTION[2]));
                                    String duration = cursor.getString
                                            (cursor.getColumnIndexOrThrow(PROJECTION[3]));
                                    
                                    LocalMedia localMedia = new LocalMedia(path, pictureType);
                                    //check video is damaged
                                    boolean isDamage = false;
                                    if (pictureType.contains(VIDEO)) {
                                        try {
                                            localMedia.setVideoDuration(duration);
                                        } catch (IllegalArgumentException e) {
                                            e.printStackTrace();
                                            isDamage = true;
                                        }
                                    }
                                    
                                    if (!isDamage) {
                                        //add all
                                        mAllLocalMedia.add(localMedia);
                                        
                                        //add single folder
                                        LocalMediaFolder imageFolder = getImageFolder(path,
                                                mReturnFolders);
                                        List<LocalMedia> images = imageFolder.getImages();
                                        images.add(localMedia);
                                        int imageNum = imageFolder.getImageNum();
                                        imageFolder.setImageNum(imageNum + 1);
                                    }
                                    
                                } while (cursor.moveToPrevious());
                                
                                allImageFolder.setImages(mAllLocalMedia);
                                allImageFolder.setName("相册");
                                allImageFolder.setImageNum(mAllLocalMedia.size());
                                allImageFolder.setFirstImagePath(mAllLocalMedia.get(0).getPath());
                                mReturnFolders.add(0, allImageFolder);
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
     * create LocalMediaFolder to save LocalMedia
     *
     * @param path image path
     * @param imageFolders folder list
     *
     * @return
     */
    private LocalMediaFolder getImageFolder(String path, List<LocalMediaFolder> imageFolders) {
        File imageFile = new File(path);
        File folderFile = imageFile.getParentFile();
        for (LocalMediaFolder folder : imageFolders) {
            if (folder.getName().equals(folderFile.getName())) {
                return folder;
            }
        }
        //create new folder and set the first image's path
        LocalMediaFolder newFolder = new LocalMediaFolder();
        newFolder.setName(folderFile.getName());
        newFolder.setFirstImagePath(path);
        imageFolders.add(newFolder);
        return newFolder;
    }
    
    public interface OnLoadImageListener {
        /**
         * completed
         *
         * @param localMediaFolders
         */
        void completed(List<LocalMediaFolder> localMediaFolders);
    }
    
    /**
     * 获取视频(最长或最小时间)
     *
     * @param exMaxLimit
     * @param exMinLimit
     *
     * @return
     */
    private String getDurationCondition(long exMaxLimit, long exMinLimit) {
        long maxS = videoMaxS == 0 ? Long.MAX_VALUE : videoMaxS;
        if (exMaxLimit != 0) {
            maxS = Math.min(maxS, exMaxLimit);
        }
        
        return String.format(Locale.CHINA, "%d <%s duration and duration <= %d",
                Math.max(exMinLimit, videoMinS),
                Math.max(exMinLimit, videoMinS) == 0 ? "" : "=",
                maxS);
    }
}
