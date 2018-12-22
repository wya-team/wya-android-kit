package com.wya.example.module.uikit.progress;

import android.content.Intent;
import android.widget.ProgressBar;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.progress.WYAProgress;

import butterknife.BindView;

public class ProgressExampleActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.wya_progress)
    WYAProgress wyaProgress;
    private int progress = 68;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_progress_example;
    }

    @Override
    protected void initView() {
        setToolBarTitle(" 进度条(progressview)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help,true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(ProgressExampleActivity.this, ReadmeActivity.class).putExtra("url",url));
        });
        progressBar.setProgress(progress);
        wyaProgress.setCurrentProgress(progress);
    }
}
