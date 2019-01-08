package com.wya.example.module.uikit.progress;

import android.content.Intent;
import android.widget.ProgressBar;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.progress.WYAProgress;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;
 /**
  * @date: 2019/1/8 9:56
  * @author: Chunjiang Mao
  * @classname: ProgressExampleActivity
  * @describe: ProgressExampleActivity
  */
 
public class ProgressExampleActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.wya_progress)
    WYAProgress wyaProgress;
    private int progress = 68;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_progress_example;
    }

    @Override
    protected void initView() {
        setTitle(" 进度条(progressview)");
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setRightSecondIconClickListener(view -> {
            startActivity(new Intent(ProgressExampleActivity.this, ReadmeActivity.class).putExtra("url",url));
        });
        setRightSecondIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(ProgressExampleActivity.this, url);
        });
        progressBar.setProgress(progress);
        wyaProgress.setCurrentProgress(progress);
    }
}
