package com.wya.example.module.utils.fliedownload;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.utils.utils.FileManagerUtil;

import static com.wya.utils.utils.FileManagerUtil.TASK_CANCEL;
import static com.wya.utils.utils.FileManagerUtil.TASK_COMPLETE;
import static com.wya.utils.utils.FileManagerUtil.TASK_FAIL;
import static com.wya.utils.utils.FileManagerUtil.TASK_RUNNING;
import static com.wya.utils.utils.FileManagerUtil.TASK_START;
import static com.wya.utils.utils.FileManagerUtil.TASK_STOP;

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
    private FileManagerUtil mFileManagerUtil;

    private String url = "http://221.228.226.5/14/z/w/y/y/zwyyobhyqvmwslabxyoaixvyubmekc/sh" +
            ".yinyuetai" +
            ".com/4599015ED06F94848EBF877EAAE13886.mp4";
    private String filepath = "/sdcard/testdownload.mp4";
        private String url2 = "https://video.pc6.com/v/1810/pyqxxjc3.mp4";
//    private String url2 = "https://ugcydzd.qq" +
//            ".com/uwMRJfz-r5jAYaQXGdGnC2_ppdhgmrDlPaRvaV7F2Ic/f0377ohr6ph" +
//            ".p712.1.mp4?sdtfrom=v1010&guid=190311d5693f25480207bbe135ce28b0&vkey" +
//            "=2DEC7E613D9B1A05D041731B9EF8045259DDD32301998410226BE956DDC3BCB1DA59CC3E5AC32AFD950B501330B1835D37EDA1881393A0476647FDE31B7D536C8E991B38DAA492EE01B23E2452ED5B075467398BB179DDBEFC04884F59E0009DD50B0440A8900CDCB62FC976552E8E73AB9D6627CD337911#t=4";
    private String filepath2 = "/sdcard/testdownload2.mp4";


    @Override
    protected int getLayoutID() {
        return R.layout.activity_file_download_example;
    }


    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        setToolBarTitle("Downloader");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help, true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(FileDownloadExampleActivity.this, ReadmeActivity.class)
                    .putExtra("url", url));
        });


        mFileManagerUtil = new FileManagerUtil();
        mFileManagerUtil.register();
        percent = (TextView) findViewById(R.id.progress_percent);
        start = (Button) findViewById(R.id.start);
        pause = (Button) findViewById(R.id.pause);
        delete = (Button) findViewById(R.id.delete);
        progress = (ProgressBar) findViewById(R.id.progress);
        all_file = (TextView) findViewById(R.id.all_file);
        down_file = (TextView) findViewById(R.id.down_file);

        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        delete.setOnClickListener(this);

        percent2 = (TextView) findViewById(R.id.progress_percent2);
        start2 = (Button) findViewById(R.id.start2);
        pause2 = (Button) findViewById(R.id.pause2);
        delete2 = (Button) findViewById(R.id.delete2);
        progress2 = (ProgressBar) findViewById(R.id.progress2);
        all_file2 = (TextView) findViewById(R.id.all_file2);
        down_file2 = (TextView) findViewById(R.id.down_file2);

        start2.setOnClickListener(this);
        pause2.setOnClickListener(this);
        delete2.setOnClickListener(this);

        initHasDown();

        initListener();
        getPermissions();
    }

    private void initListener() {
        mFileManagerUtil.setOnDownLoaderListener(new FileManagerUtil.OnDownLoaderListener() {
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
                            Toast.makeText(FileDownloadExampleActivity.this, "文件1：下载完成", Toast
                                    .LENGTH_SHORT).show();
                        }
                        if (url2.equals(key)) {
                            progress2.setProgress(100);
                            percent2.setText("100%");
                            down_file2.setText(task.getConvertFileSize());
                            Toast.makeText(FileDownloadExampleActivity.this, "文件2：下载完成", Toast
                                    .LENGTH_SHORT).show();
                        }
                        break;
                    case TASK_STOP:
                        if (url.equals(key)) {
                            Toast.makeText(FileDownloadExampleActivity.this, "文件1：暂停下载", Toast
                                    .LENGTH_SHORT).show();
                            setBtnState(false);
                        }
                        if (url2.equals(key)) {
                            Toast.makeText(FileDownloadExampleActivity.this, "文件2：暂停下载", Toast
                                    .LENGTH_SHORT).show();
                            setBtnState2(false);
                        }
                        break;
                    case TASK_CANCEL:
                        if (url.equals(key)) {
                            setBtnState(false);
                            percent.setText(0 + "%");
                            progress.setProgress(0);
                            down_file.setText("0");
                            Toast.makeText(FileDownloadExampleActivity.this, "文件1：取消下载,并删除了",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                        if (url2.equals(key)) {
                            setBtnState2(false);
                            percent2.setText(0 + "%");
                            progress2.setProgress(0);
                            down_file2.setText("0");
                            Toast.makeText(FileDownloadExampleActivity.this, "文件2：取消下载,并删除了",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case TASK_FAIL:
                        if (url.equals(key)) {
                            setBtnState(false);
                            percent.setText(0 + "%");
                            progress.setProgress(0);
                            down_file.setText("0");
                            Toast.makeText(FileDownloadExampleActivity.this, "文件1：下载失败！请确认文件地址是否可用!",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                        if (url2.equals(key)) {
                            setBtnState2(false);
                            percent2.setText(0 + "%");
                            progress2.setProgress(0);
                            down_file2.setText("0");
                            Toast.makeText(FileDownloadExampleActivity.this, "文件2：下载失败！请确认文件地址是否可用!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
    }

    private void initHasDown() {
        DownloadTarget load = mFileManagerUtil.getDownloadReceiver().load(url);
        progress.setProgress(load.getPercent());
        down_file.setText(CommonUtil.formatFileSize(load.getCurrentProgress()));
        all_file.setText(load.getConvertFileSize());

        DownloadTarget load2 = mFileManagerUtil.getDownloadReceiver().load(url2);
        progress2.setProgress(load2.getPercent());
        down_file2.setText(CommonUtil.formatFileSize(load2.getCurrentProgress()));
        all_file2.setText(load2.getConvertFileSize());


        percent.setText(load.getPercent() + "%");
        percent2.setText(load2.getPercent() + "%");

        setBtnState(load.isRunning());
        setBtnState2(load2.isRunning());
    }


    private void setBtnState(boolean isDown) {
        start.setEnabled(!isDown);
        pause.setEnabled(isDown);

        start.setTextColor(getResources().getColor(isDown ? R.color.light_gray : R.color
                .primary_color));
        pause.setTextColor(getResources().getColor(!isDown ? R.color.light_gray : R.color
                .primary_color));
    }

    private void setBtnState2(boolean isDown) {
        start2.setEnabled(!isDown);
        pause2.setEnabled(isDown);

        start2.setTextColor(getResources().getColor(isDown ? R.color.light_gray : R.color
                .primary_color));
        pause2.setTextColor(getResources().getColor(!isDown ? R.color.light_gray : R.color
                .primary_color));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFileManagerUtil.unRegister();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start:
                start();
                setBtnState(true);
                break;
            case R.id.pause:
                mFileManagerUtil.getDownloadReceiver().load(url).stop();

                break;
            case R.id.delete:
                mFileManagerUtil.getDownloadReceiver().load(url).cancel(true);
                break;
            case R.id.start2:
                start2();
                setBtnState2(true);
                break;
            case R.id.pause2:
                mFileManagerUtil.getDownloadReceiver().load(url2).stop();
                break;
            case R.id.delete2:
                mFileManagerUtil.getDownloadReceiver().load(url2).cancel(true);
                break;
        }
    }

    private void start() {
        mFileManagerUtil.getDownloadReceiver().load(url)
                .setFilePath(filepath)
                .start();
    }

    private void start2() {
        mFileManagerUtil.getDownloadReceiver().load(url2)
                .setFilePath(filepath2)
                .start();
    }

    /**
     * 获取权限
     */
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE) == PackageManager
                    .PERMISSION_GRANTED) {
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
            }
        } else {
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults.length >= 1) {
                int writeResult = grantResults[0];
                //读写内存权限
                if (writeResult == PackageManager.PERMISSION_GRANTED) {

                } else if (writeResult == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "读写权限被拒绝，无法进行下载，请开启读写权限！", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }
}
