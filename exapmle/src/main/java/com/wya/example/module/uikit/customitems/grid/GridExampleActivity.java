package com.wya.example.module.uikit.customitems.grid;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
 /**
  * @date: 2019/1/8 9:54
  * @author: Chunjiang Mao
  * @classname: GridExampleActivity
  * @describe: GridExampleActivity
  */
 
public class GridExampleActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.recycle_view2)
    RecyclerView recycleView2;
    @BindView(R.id.recycle_view3)
    RecyclerView recycleView3;
    @BindView(R.id.recycle_view4)
    RecyclerView recycleView4;
    private List<String> data = new ArrayList<>();
    private GridAdapter gridAdapter;
    private GridAdapter gridAdapter2;
    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager gridLayoutManager2;
    private GridLayoutManager gridLayoutManager3;
    private GridLayoutManager gridLayoutManager4;
    
    @Override
    protected void initView() {
        setTitle("宫格(grid)");
        setGrid();
    }
    
    private void setGrid() {
        for (int i = 0; i < 10; i++) {
            data.add("标题" + i);
        }
        gridLayoutManager = new GridLayoutManager(this, 4);
        gridLayoutManager2 = new GridLayoutManager(this, 3);
        gridLayoutManager3 = new GridLayoutManager(this, 2);
        gridLayoutManager4 = new GridLayoutManager(this, 5);
        recycleView.setLayoutManager(gridLayoutManager);
        recycleView2.setLayoutManager(gridLayoutManager2);
        recycleView3.setLayoutManager(gridLayoutManager4);
        recycleView4.setLayoutManager(gridLayoutManager3);
        gridAdapter = new GridAdapter(this, R.layout.wya_grid_item, data);
        gridAdapter2 = new GridAdapter(this, R.layout.wya_grid_item2, data);
        recycleView.setAdapter(gridAdapter);
        recycleView2.setAdapter(gridAdapter);
        recycleView3.setAdapter(gridAdapter);
        recycleView4.setAdapter(gridAdapter2);
        
        recycleView.setHasFixedSize(true);
        recycleView.setNestedScrollingEnabled(false);
        recycleView2.setHasFixedSize(true);
        recycleView2.setNestedScrollingEnabled(false);
        recycleView3.setHasFixedSize(true);
        recycleView3.setNestedScrollingEnabled(false);
        recycleView4.setHasFixedSize(true);
        recycleView4.setNestedScrollingEnabled(false);
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_grid_example;
    }
    
}
