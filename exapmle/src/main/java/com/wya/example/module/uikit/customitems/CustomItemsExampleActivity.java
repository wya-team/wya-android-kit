package com.wya.example.module.uikit.customitems;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.uikit.customitems.cardview.CardViewExampleActivity;
import com.wya.example.module.uikit.customitems.grid.GridExampleActivity;
import com.wya.example.module.uikit.customitems.inputitem.InputItemExampleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CustomItemsExampleActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    private List<String> data = new ArrayList<>();
    private CustomItemsAdapter customItemsAdapter;

    @Override
    protected void initView() {
        data.clear();
        data.add("CardView");
        data.add("Grid");
        data.add("InputItem");
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        customItemsAdapter = new CustomItemsAdapter(this, R.layout.wya_custom_item, data);
        recycleView.setAdapter(customItemsAdapter);
        customItemsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if(position == 0){
                startActivity(new Intent(CustomItemsExampleActivity.this, CardViewExampleActivity.class));
            } else if(position == 1){
                startActivity(new Intent(CustomItemsExampleActivity.this, GridExampleActivity.class));
            } else if(position == 2){
                startActivity(new Intent(CustomItemsExampleActivity.this, InputItemExampleActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_custom_items;
    }

}
