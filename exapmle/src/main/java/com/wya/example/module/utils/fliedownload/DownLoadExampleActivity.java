package com.wya.example.module.utils.fliedownload;

import android.content.Intent;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import com.arialyy.aria.core.download.DownloadEntity;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.utils.utils.DataCleanUtil;
import com.wya.utils.utils.FileManagerUtil;
import com.wya.utils.utils.StringUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wya.example.module.example.fragment.ExampleFragment.EXTRA_URL;
import static com.wya.example.module.utils.fliedownload.FlieConfig.FILE_VIDEO_DIR;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class DownLoadExampleActivity extends BaseActivity {

    @BindView(R.id.loading_num)
    TextView mLoadingNum;
    @BindView(R.id.success_num)
    TextView mSuccessNum;
    @BindView(R.id.down_space)
    TextView mDownSpace;
    @BindView(R.id.free_space)
    TextView mFreeSpace;

    private FileManagerUtil mFileManagerUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_down_load_example;
    }

    @Override
    protected void initView() {
        setTitle("下载(util(FileManagerUtil)");

        String url = getIntent().getStringExtra(EXTRA_URL);
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setSecondRightIconClickListener(view -> {
            startActivity(new Intent(this, ReadmeActivity.class).putExtra(EXTRA_URL, url));
        });
        setSecondRightIconLongClickListener(view -> {
            showShort("链接地址复制成功");
            StringUtil.copyString(this, url);
        });
        mFileManagerUtil = new FileManagerUtil();
        initNum();
        initSpace();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initNum();
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

    private void initNum() {
        List<DownloadEntity> allNotCompletTask = mFileManagerUtil.getDownloadReceiver()
                .getAllNotCompleteTask();
        List<DownloadEntity> allCompleteTask = mFileManagerUtil.getDownloadReceiver()
                .getAllCompleteTask();
        if (allNotCompletTask != null) {
            mLoadingNum.setText(allNotCompletTask.size() + "个文件");
        } else {
            mLoadingNum.setText("0个文件");
        }

        if (allCompleteTask != null) {
            mSuccessNum.setText(allCompleteTask.size() + "个文件");
        } else {
            mSuccessNum.setText("0个文件");
        }
    }

    @OnClick({R.id.download_layout, R.id.complete_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.download_layout:
                startActivity(new Intent(this, DownLoadingActivity.class));
                break;
            case R.id.complete_layout:
                startActivity(new Intent(this, DownCompleteActivity.class));
                break;
            default:
                break;
        }
    }
}
