package com.wya.example.module.utils.fliedownload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadTarget;
import com.arialyy.aria.core.download.DownloadTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wya.example.R;
import com.wya.utils.utils.FileManagerUtil;
import com.wya.utils.utils.StringUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.wya.example.module.utils.fliedownload.FileDownloadExampleActivity.FILE_IMG_DIR;
import static com.wya.utils.utils.FileManagerUtil.TASK_CANCEL;
import static com.wya.utils.utils.FileManagerUtil.TASK_COMPLETE;
import static com.wya.utils.utils.FileManagerUtil.TASK_FAIL;
import static com.wya.utils.utils.FileManagerUtil.TASK_RESUME;
import static com.wya.utils.utils.FileManagerUtil.TASK_RUNNING;
import static com.wya.utils.utils.FileManagerUtil.TASK_START;
import static com.wya.utils.utils.FileManagerUtil.TASK_STOP;

/**
 * @author : XuDonglin
 * @time : 2019-01-07
 * @Description : 文件下载中
 */
public class FileDownFragment extends Fragment {
    private static final int LENGTH = 1024;
    private static final int MIN_DELAY_TIME = 1000;
    @BindView(R.id.down_file_recycler)
    RecyclerView mDownFileRecycler;
    Unbinder unbinder;
    private View mView;
    private BaseQuickAdapter<DownloadEntity, BaseViewHolder> mAdapter;
    private List<DownloadEntity> mData = new ArrayList<>();
    private FileManagerUtil mFileManagerUtil;
    private long lastClickTime;
    private IRomUpdateCallback mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_file_down, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCallback = (FileDownloadExampleActivity) getActivity();
        initDown();
        initView();
    }

    private void initDown() {
        mFileManagerUtil = new FileManagerUtil();
        mFileManagerUtil.setOnDownLoaderListener(new FileManagerUtil.OnDownLoaderListener() {
            @Override
            public void onDownloadState(int state, DownloadTask task, Exception e) {
                for (int i = 0; i < mData.size(); i++) {
                    if (mData.get(i).getKey().equals(task.getKey())) {
                        switch (state) {
                            case TASK_START:
                                mCallback.update();
                                break;
                            case TASK_RUNNING:
                                mData.set(i, task.getEntity());
                                mAdapter.notifyItemChanged(i, task.getEntity());
                                break;
                            case TASK_RESUME:
                                break;
                            case TASK_COMPLETE:
                                mData.remove(i);
                                mAdapter.notifyDataSetChanged();
                                break;
                            case TASK_FAIL:
                                break;
                            case TASK_STOP:
                                mData.set(i, task.getEntity());
                                mAdapter.notifyItemChanged(i);
                                break;
                            case TASK_CANCEL:
                                break;
                            default:
                                break;
                        }
                    }
                }

            }
        });
    }

    private void initView() {
        List<DownloadEntity> allNotCompleteTask = mFileManagerUtil.getDownloadReceiver()
                .getAllNotCompletTask();

        mData.clear();
        if (allNotCompleteTask != null) {
            mData.addAll(allNotCompleteTask);
        }
        mAdapter = new BaseQuickAdapter<DownloadEntity, BaseViewHolder>(R.layout.down_file_item,
                mData) {
            @Override
            protected void convert(BaseViewHolder helper, DownloadEntity item) {
                ProgressBar progressBar = helper.getView(R.id.progress);
                switch (item.getState()) {
                    //暂停和启动之前
                    case TASK_START:
                    case TASK_STOP:
                        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable
                                .down_progress_stop));
                        helper.setText(R.id.speed_pause, "暂停");
                        break;
                    //运行中和重启了
                    case TASK_RUNNING:
                    case TASK_RESUME:
                        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable
                                .down_progress_res));
                        helper.setText(R.id.speed_pause, convertSpeed(item.getSpeed()));
                        break;
                    case TASK_COMPLETE:
                        break;
                    case TASK_FAIL:
                        break;
                    case TASK_CANCEL:
                        break;
                    default:
                        break;
                }
                helper.setText(R.id.file_title, TextUtils.isEmpty(item.getFileName()) ?
                        "文件一" : item.getFileName())
                        .setProgress(R.id.progress, (int) (item.getCurrentProgress() * 100 / item
                                .getFileSize()))
                        .setText(R.id.file_capacity, item.getConvertFileSize());

                ImageView imageView = helper.getView(R.id.down_file_image);
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.color.dddddd)
                        .error(R.color.dddddd)
                        .centerCrop();
                File file = new File(FILE_IMG_DIR + "/" + "IMG_" + StringUtil.getSign(item.getKey
                        ()) +
                        ".jpg");
                Glide.with(getActivity()).load(file).apply(requestOptions).into(imageView);
                helper.addOnClickListener(R.id.down_layout);
            }
        };
        mDownFileRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        mDownFileRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DownloadTarget load = mFileManagerUtil.getDownloadReceiver().load(mData.get
                        (position).getKey());
                if (!isFastClick()) {
                    if (load.isRunning()) {
                        load.stop();
                    } else {
                        load.start();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFileManagerUtil.unRegister();
        unbinder.unbind();
    }

    private String convertSpeed(long speed) {
        if (speed < LENGTH) {
            return speed + "b/s";
        }
        if (speed / LENGTH < LENGTH) {
            return speed / LENGTH + "kb/s";
        }
        float s = speed / 1024f / 1024f;
        DecimalFormat df = new DecimalFormat(".00");
        return df.format(s) + "mb/s";
    }

    public boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
