package com.wya.example.module.utils.fliedownload;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;

import com.wya.utils.utils.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static com.wya.example.module.utils.fliedownload.FlieConfig.FILE_IMG_DIR;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : 下载缓存第一帧图片
 */
public class MediaUtils {
    
    public static File getOutputMediaFile(String videoPath) {
        
        File mediaFile = null;
        mediaFile = new File(FILE_IMG_DIR + File.separator +
                "IMG_" + StringUtil.getSign(videoPath) + ".jpg");
        return mediaFile;
    }
    
    /**
     * 获取视频的第一帧图片
     */
    public static void getImageForVideo(String videoPath, OnLoadVideoImageListener listener) {
        LoadVideoImageTask task = new LoadVideoImageTask(listener);
        task.execute(videoPath);
    }
    
    public interface OnLoadVideoImageListener {
        /**
         * 加载图片
         *
         * @param file
         */
        void onLoadImage(File file);
    }
    
    public static class LoadVideoImageTask extends AsyncTask<String, Integer, File> {
        private OnLoadVideoImageListener listener;
        
        public LoadVideoImageTask(OnLoadVideoImageListener listener) {
            this.listener = listener;
        }
        
        @Override
        protected File doInBackground(String... params) {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            String path = params[0];
            if (path.startsWith("http")) {
                //获取网络视频第一帧图片
                mmr.setDataSource(path, new HashMap());
            } else {
                //本地视频
                mmr.setDataSource(path);
            }
            Bitmap bitmap = mmr.getFrameAtTime();
            //保存图片
            File f = getOutputMediaFile(path);
            if (f.exists()) {
                f.delete();
            } else {
                f.getParentFile().mkdir();
            }
            try {
                FileOutputStream out = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmr.release();
            return f;
        }
        
        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            if (listener != null) {
                listener.onLoadImage(file);
            }
        }
    }
    
}
