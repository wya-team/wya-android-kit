package com.wya.example.module.uikit.paginationview;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.paginationview.WYAPaginationBottomView;
import com.wya.uikit.paginationview.WYAPaginationDot;
import com.wya.utils.utils.StringUtil;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class PaginationViewExampleActivity extends BaseActivity {
    
    private WYAPaginationBottomView normal;
    private WYAPaginationBottomView withIcon;
    private WYAPaginationBottomView withoutNum;
    private WYAPaginationBottomView withoutBtn;
    private WYAPaginationDot mDot;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pagination_view_example;
    }
    
    @Override
    protected void initView() {
        setTitle("分页器(paginationview)");
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setSecondRightIconClickListener(view -> {
            startActivity(new Intent(PaginationViewExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setSecondRightIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(PaginationViewExampleActivity.this, url);
        });
        Drawable drawableLeft = getResources().getDrawable(R.drawable.icon_row_right);
        Drawable drawableRight = getResources().getDrawable(R.drawable.icon_row_left);
        
        normal = (WYAPaginationBottomView) findViewById(R.id.pagination_view);
        normal.setAllNum(5);
        withIcon = (WYAPaginationBottomView) findViewById(R.id.pagination_view_with_icon);
        withIcon.setAllNum(5);
        withIcon.setButtonRightDrawable(drawableLeft);
        withIcon.setButtonLeftDrawable(drawableRight);
        
        withoutNum = (WYAPaginationBottomView) findViewById(R.id.pagination_view_without_num);
        withoutNum.setAllNum(5);
        withoutNum.setNumberVisible(false);
        withoutBtn = (WYAPaginationBottomView) findViewById(R.id.pagination_view_without_btn);
        withoutBtn.setAllNum(5);
        withoutBtn.setButtonVisible(false);
        
        mDot = (WYAPaginationDot) findViewById(R.id.pagination_dot);
        mDot.setPointNumber(5);
        
    }
    
}
