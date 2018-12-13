package com.wya.example.module.uikit.customitems.expandrecyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.uikit.customitems.expandrecyclerview.adapter.ItemAdapter;
import com.wya.example.module.uikit.customitems.expandrecyclerview.bean.Item;
import com.wya.uikit.expandrecyclerview.bean.RecyclerViewData;
import com.wya.uikit.expandrecyclerview.listener.OnRecyclerViewListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ExpandRecyclerViewActivity extends BaseActivity  {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private LinearLayoutManager linearLayoutManager;
    private ItemAdapter adapter;
    private List<RecyclerViewData> mDatas;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_expand_recycler_view;
    }

    @Override
    protected void initView() {
        initItems();
        initExpandRecyclerView();
    }

    private void initExpandRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recycleView.setLayoutManager(linearLayoutManager);

        adapter = new ItemAdapter(this, mDatas);
        adapter.setOnItemClickListener(new OnRecyclerViewListener.OnItemClickListener() {
            @Override
            public void onGroupItemClick(int position, int groupPosition, View view) {

            }

            @Override
            public void onChildItemClick(int position, int groupPosition, int childPosition, View view) {

            }
        });
        adapter.setOnItemLongClickListener(new OnRecyclerViewListener.OnItemLongClickListener() {
            @Override
            public void onGroupItemLongClick(int position, int groupPosition, View view) {

            }

            @Override
            public void onChildItemLongClick(int position, int groupPosition, int childPosition, View view) {

            }
        });
        recycleView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initItems() {
        mDatas = new ArrayList<>();
        List<Item> bean1 = new ArrayList<>();
        List<Item> bean2 = new ArrayList<>();
        List<Item> bean3 = new ArrayList<>();
        List<Item> bean4 = new ArrayList<>();
        // id , pid , label , 其他属性
        bean1.add(new Item("child1"));
        bean1.add(new Item("child2"));
        bean1.add(new Item("child3"));
        bean1.add(new Item("child4"));


        bean2.add(new Item("child1"));
        bean2.add(new Item("child2"));
        bean2.add(new Item("child3"));

        bean3.add(new Item("child1"));
        bean3.add(new Item("child2"));

        bean4.add(new Item("child1"));
        bean4.add(new Item("child2"));
        bean4.add(new Item("child3"));

        bean3.add(new Item("child1"));
        bean3.add(new Item("child2"));
        bean3.add(new Item("child3"));
        bean3.add(new Item("child4"));


        bean4.add(new Item("child1"));
        bean4.add(new Item("child2"));
        bean4.add(new Item("child3"));
        bean4.add(new Item("child4"));
        bean4.add(new Item("child5"));

        mDatas.add(new RecyclerViewData("分组0", bean1, true));
        mDatas.add(new RecyclerViewData("分组1", bean2, true));
        mDatas.add(new RecyclerViewData("分组2", bean3, true));
        mDatas.add(new RecyclerViewData("分组3", bean4, true));
    }

}
