package com.wya.example.module.utils.dataclean;

import android.content.Intent;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.utils.utils.DataCleanUtil;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : XuDonglin
 * @time : 2019-01-05
 * @desc : 清理缓存
 */
public class DataCleanExampleActivity extends BaseActivity {
    
    @BindView(R.id.cache_data)
    TextView mCacheData;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_clean;
    }
    
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setSecondRightIconClickListener(view -> {
            startActivity(new Intent(this, ReadmeActivity.class).putExtra("url", url));
        });
        setSecondRightIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(this, url);
        });
        setTitle("清理缓存");
        initCache();
    }
    
    private void initCache() {
        try {
            String totalCacheSize = DataCleanUtil.getTotalCacheSize(this);
            mCacheData.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @OnClick(R.id.clean_btn)
    public void onViewClicked() {
        DataCleanUtil.cleanTotalCache(this);
        initCache();
    }
}
