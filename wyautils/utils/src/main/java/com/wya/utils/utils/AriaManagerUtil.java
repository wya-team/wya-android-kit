package com.wya.utils.utils;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadReceiver;
import com.arialyy.aria.core.download.DownloadTask;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/14
 * desc   : AriaManagerUtil
 * version: 1.0
 */
public class AriaManagerUtil {
    public final static  int WAIT = 0;
    public static final int PREVIOUS = 1;
    public static final int TASK_START = 2;
    public static final int TASK_RUNNING = 3;
    public static final int TASK_RESUME = 4;
    public static final int TASK_STOP = 5;
    public static final int TASK_CANCEL = 6;
    public static final int TASK_FAIL = 7;
    public static final int TASK_COMPLETE = 8;
    public static final int TASK_NO_POINT = 9;

    private static AriaManagerUtil sAriaManagerUtil;
    private DownloadReceiver mDownload;

    private AriaManagerUtil() {
        mDownload = Aria.download(this);
    }

    public static AriaManagerUtil getInstance() {
        if (sAriaManagerUtil == null) {
            sAriaManagerUtil = new AriaManagerUtil();
        }
        return sAriaManagerUtil;
    }


    private OnDownLoaderListener mOnDownLoaderListener;



    public void register() {
        mDownload.register();
    }

    public void unRegister() {
        mDownload.unRegister();
    }

    public DownloadReceiver getDownloadReceiver() {
        return mDownload;
    }

    @Download.onWait
    void onWait(DownloadTask task) {
        if (mOnDownLoaderListener != null) {
            mOnDownLoaderListener.onDownloadState(WAIT, task, null);
        }
    }

    @Download.onPre
    void onPre(DownloadTask task) {
        if (mOnDownLoaderListener != null) {
            mOnDownLoaderListener.onDownloadState(PREVIOUS, task, null);
        }
    }

    @Download.onTaskStart
    void taskStart(DownloadTask task) {
        if (mOnDownLoaderListener != null) {
            mOnDownLoaderListener.onDownloadState(TASK_START, task, null);
        }
    }

    @Download.onTaskRunning
    void running(DownloadTask task) {
        if (mOnDownLoaderListener != null) {
            mOnDownLoaderListener.onDownloadState(TASK_RUNNING, task, null);
        }
    }

    @Download.onTaskResume
    void taskResume(DownloadTask task) {
        if (mOnDownLoaderListener != null) {
            mOnDownLoaderListener.onDownloadState(TASK_RESUME, task, null);
        }
    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        if (mOnDownLoaderListener != null) {
            mOnDownLoaderListener.onDownloadState(TASK_STOP, task, null);
        }
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
        if (mOnDownLoaderListener != null) {
            mOnDownLoaderListener.onDownloadState(TASK_CANCEL, task, null);
        }
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task, Exception e) {
        if (mOnDownLoaderListener != null) {
            mOnDownLoaderListener.onDownloadState(TASK_FAIL, task, e);
        }
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        if (mOnDownLoaderListener != null) {
            mOnDownLoaderListener.onDownloadState(TASK_COMPLETE, task, null);
        }
    }

    @Download.onNoSupportBreakPoint
    public void onNoSupportBreakPoint(DownloadTask task) {
        if (mOnDownLoaderListener != null) {
            mOnDownLoaderListener.onDownloadState(TASK_NO_POINT, task, null);
        }
    }

    public OnDownLoaderListener getOnDownLoaderListener() {
        return mOnDownLoaderListener;
    }

    public void setOnDownLoaderListener(OnDownLoaderListener onDownLoaderListener) {
        mOnDownLoaderListener = onDownLoaderListener;
    }

    public interface OnDownLoaderListener {
        void onDownloadState(int state, DownloadTask task, Exception e);
    }


}
