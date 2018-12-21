package com.wya.example.module.uikit.paginationview;

import android.graphics.drawable.Drawable;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.paginationview.WYAPaginationBottomView;
import com.wya.uikit.paginationview.WYAPaginationDot;

public class PaginationViewExampleActivity extends BaseActivity {

    private WYAPaginationBottomView normal;
    private WYAPaginationBottomView withIcon;
    private WYAPaginationBottomView withoutNum;
    private WYAPaginationBottomView withoutBtn;
    private WYAPaginationDot mDot;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_pagination_view_example;
    }


    @Override
    protected void initView() {
        setToolBarTitle("PaginationView");
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
