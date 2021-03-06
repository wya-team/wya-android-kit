package com.wya.example.module.uikit.searchbar;

import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.searchbar.WYASearchBar;

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
    @BindView(R.id.parent)
    LinearLayout parent;
    
    @Override
    protected void initView() {
        setToolBarTitle("搜索栏(searchBar)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help, true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(SearchBarExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setWYASearchBar();
    }
    
    private void setWYASearchBar() {
        wyaSearchBar.setEditHint("我是提示语");
        wyaSearchBar.setOnClickCancelListener(new WYASearchBar.OnTvClickListener() {
            @Override
            public void onClickCancel() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
            
            @Override
            public void onClickSearch() {
                getWyaToast().showShort(wyaSearchBar.getEtSearch());
            }
        });
        wyaSearchBar.setOnTextChangeListener(s -> {
            if (!s.toString().trim().equals("")) {
            }
        });
        parent.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        });
        
    }
    
    @Override
    protected int getLayoutID() {
        return R.layout.activity_search_bar_example;
    }
    
}
