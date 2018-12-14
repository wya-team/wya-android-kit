package com.wya.example.module.utils.fliedownload;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arialyy.aria.core.download.DownloadTarget;
import com.arialyy.aria.core.download.DownloadTask;
import com.arialyy.aria.util.CommonUtil;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.utils.utils.AriaManagerUtil;

import static com.wya.utils.utils.AriaManagerUtil.TASK_CANCEL;
import static com.wya.utils.utils.AriaManagerUtil.TASK_COMPLETE;
import static com.wya.utils.utils.AriaManagerUtil.TASK_RUNNING;
import static com.wya.utils.utils.AriaManagerUtil.TASK_START;
import static com.wya.utils.utils.AriaManagerUtil.TASK_STOP;

public class FileDownloadExampleActivity extends BaseActivity implements View.OnClickListener {
    private TextView percent;
    private TextView all_file;
    private TextView down_file;
    private Button start, pause, delete;
    private ProgressBar progress;
    private TextView percent2;
    private TextView all_file2;
    private TextView down_file2;
    private Button start2, pause2, delete2;
    private ProgressBar progress2;

    private String url = "http://221.228.226.5/14/z/w/y/y/zwyyobhyqvmwslabxyoaixvyubmekc/sh.yinyuetai" +
            ".com/4599015ED06F94848EBF877EAAE13886.mp4";
    private String filepath = "/sdcard/testdownload.mp4";
    private String url2 = "https://video.pc6.com/v/1810/pyqxxjc3.mp4";
    private String filepath2 = "/sdcard/testdownload2.mp4";


    @Override
    protected int getLayoutID() {
        return R.layout.activity_file_download_example;
    }


    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        AriaManagerUtil.getInstance().register();
        percent = findViewById(R.id.progress_percent);
        start = findViewById(R.id.start);
        pause = findViewById(R.id.pause);
        delete = findViewById(R.id.delete);
        progress = findViewById(R.id.progress);
        all_file = findViewById(R.id.all_file);
        down_file = findViewById(R.id.down_file);

        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        delete.setOnClickListener(this);

        percent2 = findViewById(R.id.progress_percent2);
        start2 = findViewById(R.id.start2);
        pause2 = findViewById(R.id.pause2);
        delete2 = findViewById(R.id.delete2);
        progress2 = findViewById(R.id.progress2);
        all_file2 = findViewById(R.id.all_file2);
        down_file2 = findViewById(R.id.down_file2);

        start2.setOnClickListener(this);
        pause2.setOnClickListener(this);
        delete2.setOnClickListener(this);

        initHasDown();

        initListener();

    }

    private void initListener() {
        AriaManagerUtil.getInstance().setOnDownLoaderListener(new AriaManagerUtil.OnDownLoaderListener() {
            @Override
            public void onDownloadState(int state, DownloadTask task, Exception e) {
                String key = task.getKey();
                switch (state) {
                    case TASK_START:
                        if (url.equals(key)) {
                            all_file.setText(task.getConvertFileSize());
                        }
                        if (url2.equals(key)) {
                            all_file2.setText(task.getConvertFileSize());
                        }

                        break;
                    case TASK_RUNNING:
                        if (url.equals(key)) {
                            if (task.getFileSize() == 0) {
                                progress.setProgress(0);
                                percent.setText(0 + "%");
                            } else {
                                progress.setProgress(task.getPercent());
                                percent.setText(task.getPercent() + "%");
                                down_file.setText(task.getConvertCurrentProgress());
                            }
                        }
                        if (url2.equals(key)) {
                            if (task.getFileSize() == 0) {
                                progress2.setProgress(0);
                                percent2.setText(0 + "%");
                            } else {
                                progress2.setProgress(task.getPercent());
                                percent2.setText(task.getPercent() + "%");
                                down_file2.setText(task.getConvertCurrentProgress());
                            }
                        }
                        break;
                    case TASK_COMPLETE:
                        if (url.equals(key)) {
                            progress.setProgress(100);
                            percent.setText("100%");
                            down_file.setText(task.getConvertFileSize());
                            Toast.makeText(FileDownloadExampleActivity.this, "文件1：下载完成", Toast.LENGTH_SHORT).show();
                        }
                        if (url2.equals(key)) {
                            progress2.setProgress(100);
                            percent2.setText("100%");
                            down_file2.setText(task.getConvertFileSize());
                            Toast.makeText(FileDownloadExampleActivity.this, "文件2：下载完成", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case TASK_STOP:
                        if (url.equals(key)) {
                            Toast.makeText(FileDownloadExampleActivity.this, "文件1：暂停下载", Toast.LENGTH_SHORT).show();
                            setBtnState(false);
                        }
                        if (url2.equals(key)) {
                            Toast.makeText(FileDownloadExampleActivity.this, "文件2：暂停下载", Toast.LENGTH_SHORT).show();
                            setBtnState2(false);
                        }
                        break;
                    case TASK_CANCEL:
                        if (url.equals(key)) {
                            setBtnState(false);
                            percent.setText(0 + "%");
                            progress.setProgress(0);
                            down_file.setText("0");
                            Toast.makeText(FileDownloadExampleActivity.this, "文件1：取消下载,并删除了", Toast.LENGTH_SHORT)
                                    .show();
                        }
                        if (url2.equals(key)) {
                            setBtnState2(false);
                            percent2.setText(0 + "%");
                            progress2.setProgress(0);
                            down_file2.setText("0");
                            Toast.makeText(FileDownloadExampleActivity.this, "文件2：取消下载,并删除了", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
    }

    private void initHasDown() {
        DownloadTarget load = AriaManagerUtil.getInstance().getDownloadReceiver().load(url);
        progress.setProgress(load.getPercent());
        down_file.setText(CommonUtil.formatFileSize(load.getCurrentProgress()));
        all_file.setText(load.getConvertFileSize());

        DownloadTarget load2 = AriaManagerUtil.getInstance().getDownloadReceiver().load(url2);
        progress2.setProgress(load2.getPercent());
        down_file2.setText(CommonUtil.formatFileSize(load2.getCurrentProgress()));
        all_file2.setText(load2.getConvertFileSize());
    }


    private void setBtnState(boolean isDown) {
        start.setEnabled(!isDown);
        pause.setEnabled(isDown);

        start.setTextColor(getResources().getColor(isDown ? R.color.light_gray : R.color.red));
        pause.setTextColor(getResources().getColor(!isDown ? R.color.light_gray : R.color.red));
    }

    private void setBtnState2(boolean isDown) {
        start2.setEnabled(!isDown);
        pause2.setEnabled(isDown);

        start2.setTextColor(getResources().getColor(isDown ? R.color.light_gray : R.color.red));
        pause2.setTextColor(getResources().getColor(!isDown ? R.color.light_gray : R.color.red));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AriaManagerUtil.getInstance().unRegister();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start:
                start();
                setBtnState(true);
                break;
            case R.id.pause:
                AriaManagerUtil.getInstance().getDownloadReceiver().load(url).stop();

                break;
            case R.id.delete:
                AriaManagerUtil.getInstance().getDownloadReceiver().load(url).cancel(true);
                break;
            case R.id.start2:
                start2();
                setBtnState2(true);
                break;
            case R.id.pause2:
                AriaManagerUtil.getInstance().getDownloadReceiver().load(url2).stop();
                break;
            case R.id.delete2:
                AriaManagerUtil.getInstance().getDownloadReceiver().load(url2).cancel(true);
                break;
        }
    }

    private void start() {
        AriaManagerUtil.getInstance().getDownloadReceiver().load(url)
                .setFilePath(filepath)
                .start();
    }

    private void start2() {
        AriaManagerUtil.getInstance().getDownloadReceiver().load(url2)
                .setFilePath(filepath2)
                .start();
    }
}
