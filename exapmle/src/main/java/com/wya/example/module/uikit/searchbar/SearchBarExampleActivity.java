package com.wya.example.module.uikit.searchbar;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.searchbar.WYASearchBar;
import com.wya.uikit.toast.WYAToast;

import butterknife.BindView;

/**
 * 创建日期：2018/11/22 16:13
 * 作者： Mao Chunjiang
 * 文件名称：SearchBarExampleActivity
 * 类说明：SearchBar例子
 */

public class SearchBarExampleActivity extends BaseActivity {

    @BindView(R.id.wya_search_bar)
    WYASearchBar wyaSearchBar;

    @Override
    protected void initView() {
        setToolBarTitle("SearchBar");
        setWYASearchBar();
    }

    private void setWYASearchBar() {
        wyaSearchBar.setEditHint("我是提示语");
        wyaSearchBar.setOnClickCancelListener(() -> {
            getWyaToast().showShort("点击右边文字");
        });
        wyaSearchBar.setOnTextChangeListener(s -> {
            if (!s.toString().trim().equals("")) {
                getWyaToast().showShort(s.toString().trim());
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_search_bar_example;
    }
}
