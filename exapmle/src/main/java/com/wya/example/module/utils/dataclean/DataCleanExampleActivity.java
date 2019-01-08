package com.wya.example.module.utils.dataclean;

import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.utils.utils.DataCleanUtil;

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
