package com.wya.example.module.uikit.choicemenu;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.choicemenu.ChoiceMenu;
import com.wya.uikit.choicemenu.ChoiceMenuViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoiceMenuExampleActivity extends BaseActivity {


    private RelativeLayout mRelativeLayout;
    private RelativeLayout showOne, showTwo;
    private int selectPositionOne = 0;
    private int selectPositionTwo = 0;
    private int selectPositionTwo1 = 0;
    private Map<String, Integer> selection = new HashMap<>();

    List<String> data = new ArrayList<>();
    List<List<String>> data2 = new ArrayList<>();

    @Override
    protected int getLayoutID() {
        return R.layout.activity_choice_menu_example;
    }

    @Override
    protected void initView() {
        getSwipeBackLayout().setEnableGesture(false);
        setSwipeBack(true);
        setToolBarTitle("菜单(choicemenu)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help,true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(ChoiceMenuExampleActivity.this, ReadmeActivity.class).putExtra("url",url));
        });

        showOne = (RelativeLayout) findViewById(R.id.show_one);
        showTwo = (RelativeLayout) findViewById(R.id.show_two);
        initData();
        mRelativeLayout = (RelativeLayout) findViewById(R.id.title_bar_bg);
        ChoiceMenu<String> choiceMenu2 = new ChoiceMenu<String>(this, data, data2, R.layout
                .choice_menu_item, R.layout.choice_menu_item2) {
            @Override
            public void setValueFirst(ChoiceMenuViewHolder helper, String item) {
                helper.setText(R.id.menu_title, item);
            }

            @Override
            public void setValueSecond(ChoiceMenuViewHolder helper, String item) {
                ImageView imageView = helper.getView(R.id.check);
                TextView textView = helper.getView(R.id.menu_titles);
                helper.setText(R.id.menu_titles, item);
                //注意这里只是为了模拟第五个数据时不可点击的
                if (helper.getLayoutPosition() != 4) {
                    Integer integer = selection.get("test");
                    if (integer == null) {
                        integer = 0;
                    }
                    if (integer==selectPositionTwo1&&selectPositionTwo == helper.getLayoutPosition()) {
                        textView.setTextColor(getResources().getColor(R.color.primary_color));
                        imageView.setVisibility(View.VISIBLE);
                    } else {
                        imageView.setVisibility(View.GONE);
                        textView.setTextColor(getResources().getColor(R.color.black));
                    }
                } else {
                    imageView.setVisibility(View.GONE);
                    textView.setTextColor(getResources().getColor(R.color.color_BEBEBE));
                }


                helper.itemView.setOnClickListener(v -> {
                    if (helper.getLayoutPosition()!=4) {
                        selectPositionTwo = helper.getLayoutPosition();
                        selection.put("test", selectPositionTwo1);
                        notifyAdapterData();
                    }
                });

            }
        };
        choiceMenu2.setOnFirstAdapterItemClickListener((position, v, menu) -> selectPositionTwo1 = position);

        ChoiceMenu<String> choiceMenu1 = new ChoiceMenu<String>(this, data, R.layout
                .one_choice_menu_item) {
            @Override
            public void setValueFirst(ChoiceMenuViewHolder helper, String item) {
                helper.setText(R.id.content, item);
                TextView view = helper.getView(R.id.content);
                view.setSelected(selectPositionOne == helper.getLayoutPosition());
                helper.itemView.setSelected(selectPositionOne == helper.getLayoutPosition());
            }

            @Override
            public void setValueSecond(ChoiceMenuViewHolder helper, String item) {

            }
        };

        choiceMenu1.setOnFirstAdapterItemClickListener((position, v, menu) -> {
            selectPositionOne = position;
            choiceMenu1.notifyAdapterData();
        });

        //设置高度
        choiceMenu2.setHeight(getResources().getDisplayMetrics().heightPixels / 2);

        showOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choiceMenu1.showAsDropDown(showOne);
            }
        });
        showTwo.setOnClickListener(v -> choiceMenu2.showAsDropDown(showTwo));

    }

    private void initData() {
        data.add("美食");
        List<String> list1 = new ArrayList<>();
        list1.add("谷类及制品");
        list1.add("薯类、淀粉及制品");
        list1.add("干豆类及制品");
        list1.add("蔬菜类及制品");
        list1.add("菌藻类");
        list1.add("水果类及制品");
        list1.add("坚果、种子类");
        list1.add("畜肉类及制品");
        list1.add("禽肉类及制品");
        list1.add("乳类及制品");
        list1.add("蛋类及制品");
        list1.add("鱼虾蟹贝类");
        data2.add(list1);


        data.add("娱乐");
        List<String> list2 = new ArrayList<>();
        list2.add("歌舞厅");
        list2.add("演艺厅");
        list2.add("迪厅");
        list2.add("KTV");
        list2.add("夜总会");
        list2.add("音乐茶座");
        list2.add("台球");
        list2.add("高尔夫球");
        list2.add("保龄球场");
        list2.add("游戏厅");
        data2.add(list2);


        data.add("丽人");
        List<String> list3 = new ArrayList<>();
        data2.add(list3);

    }
}
