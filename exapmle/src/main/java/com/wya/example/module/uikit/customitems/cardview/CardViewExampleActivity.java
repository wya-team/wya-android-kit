package com.wya.example.module.uikit.customitems.cardview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CardViewExampleActivity extends BaseActivity {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    private CardViewAdapter cardViewAdapter;
    private List<String> contents = new ArrayList<>();


    @Override
    protected void initView() {
        for (int i = 0; i < 100; i++) {
            contents.add("名字"+i);
        }
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        cardViewAdapter = new CardViewAdapter(this, R.layout.wya_card_view_item, contents);
        recycleView.setAdapter(cardViewAdapter);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_wyacard_view_example;
    }

}
