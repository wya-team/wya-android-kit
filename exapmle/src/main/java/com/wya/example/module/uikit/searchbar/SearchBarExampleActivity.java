package com.wya.example.module.uikit.searchbar;

import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.searchbar.WYASearchBar;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;

/**
 * @date: 2018/11/22 16:13
 * @author: Chunjiang Mao
 * @classname: SearchBarExampleActivity
 * @describe: SearchBar例子
 */

public class SearchBarExampleActivity extends BaseActivity {
    
    @BindView(R.id.wya_search_bar)
    WYASearchBar wyaSearchBar;
    @BindView(R.id.parent)
    LinearLayout parent;
    
    @Override
    protected void initView() {
        setTitle("搜索栏(searchBar)");
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setSecondRightIconClickListener(view -> {
            startActivity(new Intent(SearchBarExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setSecondRightIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(SearchBarExampleActivity.this, url);
        });
        setWYASearchBar();
    }
    
    private void setWYASearchBar() {
        wyaSearchBar.setOnClickCancelListener(new WYASearchBar.OnTextClickListener() {
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
            if (!"".equals(s.toString().trim())) {
            }
        });
        parent.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        });
        
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_bar_example;
    }
    
}
