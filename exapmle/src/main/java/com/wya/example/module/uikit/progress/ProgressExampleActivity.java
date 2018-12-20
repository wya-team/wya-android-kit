package com.wya.example.module.uikit.progress;

import android.widget.ProgressBar;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
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

        progressBar.setProgress(progress);
        wyaProgress.setCurrentProgress(progress);
    }
}
