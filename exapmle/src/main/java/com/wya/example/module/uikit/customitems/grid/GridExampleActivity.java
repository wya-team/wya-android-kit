package com.wya.example.module.uikit.customitems.grid;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.popupwindow.WYAPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GridExampleActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    private List<String> data = new ArrayList<>();
    private GridAdapter gridAdapter;
    private List<String> list;
    private WYAPopupWindow wyaPopupWindow;
    private GridLayoutManager gridLayoutManager;
    private int spanCount = 4;

    @Override
    protected void initView() {
        setToolBarTitle("Grid");
        initImgRight( R.mipmap.icon_nav_more, true, 0, false);
        setPopupWindow();
        setGrid();
    }

    private void setGrid() {
        for (int i = 0; i < 10; i++) {
            data.add("标题" + i);
        }
        gridLayoutManager = new GridLayoutManager(this, spanCount);
        recycleView.setLayoutManager(gridLayoutManager);
        gridAdapter = new GridAdapter(this, R.layout.wya_grid_item, data);
        recycleView.setAdapter(gridAdapter);
    }

    private void setPopupWindow() {
        list = new ArrayList<>();
        list.add("1列");
        list.add("2列");
        list.add("3列");
        list.add("4列");
        setRightOnclickListener(new onRightOnclickListener() {
            @Override
            public void onRightClick(View view) {
                wyaPopupWindow.show(view, -100, 0);
            }
        });
        wyaPopupWindow = new WYAPopupWindow(GridExampleActivity.this, list);
        wyaPopupWindow.setPopupWindowListOnclickListener(position -> {
            wyaPopupWindow.dismiss();
            spanCount = position + 1;
            gridLayoutManager.setSpanCount(spanCount);
            gridAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_grid_example;
    }

}
