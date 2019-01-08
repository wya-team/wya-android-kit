package com.wya.example.module.uikit.customitems.cardview;

import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.utils.utils.StringUtil;
 /**
  * @date: 2019/1/8 9:53
  * @author: Chunjiang Mao
  * @classname: CardViewExampleActivity
  * @describe: CardViewExampleActivity
  */
 
public class CardViewExampleActivity extends BaseActivity {
    
    @Override
    protected void initView() {
        setTitle("卡片(customitems)");
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setRightSecondIconClickListener(view -> {
            startActivity(new Intent(CardViewExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setRightSecondIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(CardViewExampleActivity.this, url);
        });
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_wyacard_view_example;
    }
    
}
