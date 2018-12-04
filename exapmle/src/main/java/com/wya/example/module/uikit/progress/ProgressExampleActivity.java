package com.wya.example.module.uikit.progress;

import android.widget.ProgressBar;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.progress.WYAProgress;
import com.wya.uikit.stepper.WYAStepper;

import butterknife.BindView;
import butterknife.OnClick;

public class ProgressExampleActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.wya_progress)
    WYAProgress wyaProgress;
    @BindView(R.id.stepper)
    WYAStepper stepper;
    private int progress = 0;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_progress_example;
    }

    @Override
    protected void initView() {
        setToolBarTitle(" Progress");
    }


    @OnClick(R.id.wya_button)
    public void onViewClicked() {
        progress = stepper.getValue();
        progressBar.setProgress(progress);
        wyaProgress.setCurrentProgress(progress);
    }
}
