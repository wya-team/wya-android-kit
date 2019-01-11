package com.wya.example.module.utils.fliedownload;

import android.os.Environment;
import android.os.StatFs;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arialyy.aria.core.download.DownloadEntity;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wya.example.module.utils.fliedownload.FlieConfig.FILE_IMG_DIR;
import static com.wya.example.module.utils.fliedownload.FlieConfig.FILE_VIDEO_DIR;
import static com.wya.utils.utils.FileManagerUtil.TASK_CANCEL;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : 已完成
 */
public class DownCompleteActivity extends BaseActivity implements BaseToolBarActivity.FirstRightTextClickListener {

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
    private FileManagerUtil mFileManagerUtil = new FileManagerUtil();
    private boolean edit = false;
    /**
     * -1 没有状态，0全选 1取消全选
     */
    private int selectState = -1;
    private List<String> mDeleteList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_down_complete;
    }

    @Override
    protected void initView() {
        setTitle("下载完成");
        setFirstRightText("管理");
        setFirstRightTextClickListener(this);
        showFirstRightText(true);

        initDown();
        initRecycler();
        initData();
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

    private void initData() {
        mDownList.clear();
        List<DownloadEntity> allCompleteTask = mFileManagerUtil.getDownloadReceiver()
                .getAllCompleteTask();
        if (allCompleteTask != null) {
            mDownList.addAll(allCompleteTask);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initDown() {
        mFileManagerUtil.setOnDownLoaderListener(new FileManagerUtil.OnDownLoaderListener() {
            @Override
            public void onDownloadState(int state, DownloadTask task, Exception e) {
                for (int i = 0; i < mDownList.size(); i++) {
                    if (mDownList.get(i).getKey().equals(task.getKey())) {
                        switch (state) {
                            case TASK_CANCEL:
                                initData();
                                break;
                            default:
                                break;
                        }
                    }
                }

            }
        });
    }

    private void initRecycler() {
        mAdapter = new BaseQuickAdapter<DownloadEntity, BaseViewHolder>(R.layout
                .has_down_file_item, mDownList) {
            @Override
            protected void convert(BaseViewHolder helper, DownloadEntity item) {
                ImageView imageView = helper.getView(R.id.down_file_image);
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.color.dddddd)
                        .error(R.color.dddddd)
                        .centerCrop();
                File file = new File(FILE_IMG_DIR + "/" + "IMG_" + StringUtil.getSign(item.getKey
                        ()) + ".jpg");
                Glide.with(DownCompleteActivity.this).load(file).apply(requestOptions).into(imageView);

                helper.setText(R.id.file_title, item.getFileName())
                        .setText(R.id.file_capacity, item.getConvertFileSize());

                helper.setGone(R.id.edit_check, edit);
                helper.setOnCheckedChangeListener(R.id.edit_check, new CompoundButton.OnCheckedChangeListener() {
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

    }

    @Override
    public void rightFirstTextClick(View view) {
        if (mDownList.size() > 0) {
            selectState = -1;
            edit = !edit;
            setFirstRightText(edit ? "取消" : "管理");
            mAdapter.notifyDataSetChanged();
            mEditLayout.setVisibility(edit ? View.VISIBLE : View.GONE);
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
                edit = false;
                setFirstRightText(edit ? "取消" : "管理");
                mEditLayout.setVisibility(edit ? View.VISIBLE : View.GONE);
                for (String url : mDeleteList) {
                    mFileManagerUtil.getDownloadReceiver().load(url).cancel(true);
                }
                mDeleteList.clear();
                initSpace();
                break;
            default:
                break;
        }
    }

}
