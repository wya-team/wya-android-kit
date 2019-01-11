package com.wya.example.module.utils.fliedownload;

import android.os.Environment;
import android.os.StatFs;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadTarget;
import com.arialyy.aria.core.download.DownloadTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.toolbar.BaseToolBarActivity;
import com.wya.utils.utils.DataCleanUtil;
import com.wya.utils.utils.FileManagerUtil;
import com.wya.utils.utils.StringUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wya.example.module.utils.fliedownload.FlieConfig.FILE_IMG_DIR;
import static com.wya.example.module.utils.fliedownload.FlieConfig.FILE_VIDEO_DIR;
import static com.wya.utils.utils.FileManagerUtil.TASK_CANCEL;
import static com.wya.utils.utils.FileManagerUtil.TASK_COMPLETE;
import static com.wya.utils.utils.FileManagerUtil.TASK_FAIL;
import static com.wya.utils.utils.FileManagerUtil.TASK_RESUME;
import static com.wya.utils.utils.FileManagerUtil.TASK_RUNNING;
import static com.wya.utils.utils.FileManagerUtil.TASK_START;
import static com.wya.utils.utils.FileManagerUtil.TASK_STOP;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : 下载中
 */
public class DownLoadingActivity extends BaseActivity implements BaseToolBarActivity.RightFirstTextClickListener {

    private static final int LENGTH = 1024;
    private static final int MIN_DELAY_TIME = 500;
    @BindView(R.id.down_file_recycler)
    RecyclerView mDownFileRecycler;
    @BindView(R.id.down_space)
    TextView mDownSpace;
    @BindView(R.id.free_space)
    TextView mFreeSpace;
    @BindView(R.id.choose_all_text)
    TextView mChooseAllText;
    @BindView(R.id.delete_file_text)
    TextView mDeleteFileText;
    @BindView(R.id.edit_layout)
    LinearLayout mEditLayout;
    private BaseQuickAdapter<DownloadEntity, BaseViewHolder> mAdapter;
    private List<DownloadEntity> mDownList = new ArrayList<>();
    private FileManagerUtil mFileManagerUtil;
    private long lastClickTime;
    private boolean isEdit = false;
    private int selectState = -1;
    private List<String> mDeleteList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_down_loading;
    }

    @Override
    protected void initView() {
        setTitle("下载中");
        setFirstRightText("管理");
        setRightFirstTextClickListener(this);
        showFirstRightText(true);
        initFileDown();
        initRecyclerView();
        initSpace();
    }

    private void initSpace() {
        mDownSpace.setText(DataCleanUtil.getFormatSize(DataCleanUtil.getFolderSize(new File
                (FILE_VIDEO_DIR))));
        mFreeSpace.setText(getSDAvailableSize());
    }

    private String getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(this, blockSize * availableBlocks);
    }

    /**
     * 初始化文件下载
     */
    private void initFileDown() {
        mFileManagerUtil = new FileManagerUtil();
        mFileManagerUtil.setOnDownLoaderListener(new FileManagerUtil.OnDownLoaderListener() {
            @Override
            public void onDownloadState(int state, DownloadTask task, Exception e) {
                for (int i = 0; i < mDownList.size(); i++) {
                    if (mDownList.get(i).getKey().equals(task.getKey())) {
                        switch (state) {
                            case TASK_START:
                                break;
                            case TASK_RUNNING:
                                mDownList.set(i, task.getEntity());
                                mAdapter.notifyItemChanged(i, task.getEntity());
                                break;
                            case TASK_RESUME:
                                break;
                            case TASK_COMPLETE:
                                mDownList.remove(i);
                                mAdapter.notifyDataSetChanged();
                                if (isEdit) {
                                    mDeleteList.remove(task.getKey());
                                    if (mDownList.size() == 0) {
                                        isEdit = false;
                                        setFirstRightText(isEdit ? "取消" : "管理");
                                        mEditLayout.setVisibility(isEdit ? View.VISIBLE : View.GONE);
                                    } else {
                                        if (mDeleteList.size() > 0) {
                                            mDeleteFileText.setEnabled(true);
                                            mDeleteFileText.setText("删除(" + mDeleteList.size() + ")");
                                        } else {
                                            mDeleteFileText.setText("删除");
                                            mDeleteFileText.setEnabled(false);
                                        }
                                    }

                                }
                                break;
                            case TASK_FAIL:
                                break;
                            case TASK_STOP:
                                mDownList.set(i, task.getEntity());
                                mAdapter.notifyItemChanged(i);
                                break;
                            case TASK_CANCEL:
                                getNotComplete();
                                mAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                    }
                }

            }
        });
    }

    private void initRecyclerView() {
        getNotComplete();
        mAdapter = new BaseQuickAdapter<DownloadEntity, BaseViewHolder>(R.layout.down_file_item,
                mDownList) {
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
                Glide.with(DownLoadingActivity.this).load(file).apply(requestOptions).into
                        (imageView);
                helper.addOnClickListener(R.id.down_layout);
                helper.setGone(R.id.edit_check, isEdit);
                helper.setOnCheckedChangeListener(R.id.edit_check, new CompoundButton
                        .OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            mDeleteList.add(item.getKey());
                        } else {
                            mDeleteList.remove(item.getKey());
                        }
                        if (mDeleteList.size() == mDownList.size()) {
                            mChooseAllText.setText("取消全选");
                        } else {
                            mChooseAllText.setText("全选");
                        }
                        if (mDeleteList.size() > 0) {
                            mDeleteFileText.setEnabled(true);
                            mDeleteFileText.setText("删除(" + mDeleteList.size() + ")");
                        } else {
                            mDeleteFileText.setText("删除");
                            mDeleteFileText.setEnabled(false);
                        }

                    }
                });
                switch (selectState) {
                    case 0:
                        helper.setChecked(R.id.edit_check, true);
                        break;
                    case 1:
                        helper.setChecked(R.id.edit_check, false);
                        break;
                    default:
                        break;
                }
            }
        };
        mDownFileRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mDownFileRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DownloadTarget load = mFileManagerUtil.getDownloadReceiver().load(mDownList.get
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

    private void getNotComplete() {
        List<DownloadEntity> allNotCompleteTask = mFileManagerUtil.getDownloadReceiver()
                .getAllNotCompletTask();

        mDownList.clear();
        if (allNotCompleteTask != null) {
            mDownList.addAll(allNotCompleteTask);
        }
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

    @Override
    public void rightFirstTextClick(View view) {
        if (mDownList.size() > 0) {
            selectState = -1;
            isEdit = !isEdit;
            setFirstRightText(isEdit ? "取消" : "管理");
            mAdapter.notifyDataSetChanged();
            mEditLayout.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        }
    }

    @OnClick({R.id.choose_all_text, R.id.delete_file_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_all_text:
                selectState = selectState == 0 ? 1 : 0;
                mChooseAllText.setText(selectState == 0 ? "取消全选" : "全选");
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.delete_file_text:
                selectState = -1;
                isEdit = false;
                setFirstRightText(isEdit ? "取消" : "管理");
                mEditLayout.setVisibility(isEdit ? View.VISIBLE : View.GONE);
                for (String url : mDeleteList) {
                    mFileManagerUtil.getDownloadReceiver().load(url).cancel(true);
                }
                initSpace();
                break;
            default:
                break;
        }
    }
}
