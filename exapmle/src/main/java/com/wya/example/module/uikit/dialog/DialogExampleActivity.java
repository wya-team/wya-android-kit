package com.wya.example.module.uikit.dialog;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.uikit.dialog.adapter.DialogExpandableListAdapter;
import com.wya.example.module.uikit.dialog.bean.Item;
import com.wya.uikit.dialog.CustomListener;
import com.wya.uikit.dialog.WYACustomDialog;
import com.wya.uikit.dialog.WYALoadingDialog;
import com.wya.utils.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建日期：2018/11/23 16:23
 * 作者： Mao Chunjiang
 * 文件名称：DialogExampleActivity
 * 类说明：dialog例子
 */

public class DialogExampleActivity extends BaseActivity {

    @BindView(R.id.expend_list)
    ExpandableListView expendList;

    private List<Item> mDatas;
    private DialogExpandableListAdapter adapter;
    private List<String> data = new ArrayList<>();

    private DialogListAdapter dialogListAdapter;

    private WYACustomDialog wyaCustomDialog;
    private WYALoadingDialog wyaLoadingDialog;
    private boolean canceledOnTouch = true;
    private boolean cancelable = true;
    private boolean list = false;
    private String title = "我是标题";
    private String content = "我是内容";
    private String cancel = "取消";
    private String sure = "确定";
    private boolean isShowButton = true;
    private String edit_text_str = "我是编辑框内容";


    @Override
    protected int getLayoutID() {
        return R.layout.activity_dialog_example;
    }


    @Override
    protected void initView() {
        setToolBarTitle("Dialog");
        initItems();
        initExpandList();
    }

    private void initExpandList() {
        adapter = new DialogExpandableListAdapter(this, mDatas);
        expendList.setAdapter(adapter);
        expendList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        expendList.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            setClick(groupPosition, childPosition);
            return false;
        });
        adapter.notifyDataSetChanged();
    }

    private void setClick(int groupPosition, int childPosition) {
        String s = mDatas.get(groupPosition).getChild().get(childPosition);
        switch (s) {
            case "一个按钮":
                wyaCustomDialog = new WYACustomDialog.Builder(this)
                        .cancelable(true)
                        .cancelTouchout(true)
                        .title("标题")
                        .message("内容")
                        .cancelShow(false)
                        .build();
                wyaCustomDialog.setNoOnclickListener(() -> {
                    wyaCustomDialog.dismiss();
                });
                wyaCustomDialog.setYesOnclickListener(() -> wyaCustomDialog.dismiss());
                wyaCustomDialog.show();
                break;
            case "加载":
                showWYADialogLoading();
                break;
            case "两个按钮":
                wyaCustomDialog = new WYACustomDialog.Builder(this)
                        .cancelable(true)
                        .cancelTouchout(true)
                        .title("标题")
                        .message("内容")
                        .build();
                wyaCustomDialog.setNoOnclickListener(() -> {
                    wyaCustomDialog.dismiss();
                });
                wyaCustomDialog.setYesOnclickListener(() -> wyaCustomDialog.dismiss());
                wyaCustomDialog.show();
                break;
            case "有文本编辑":
                wyaCustomDialog = new WYACustomDialog.Builder(this)
                        .cancelable(true)
                        .cancelTouchout(true)
                        .title("标题")
                        .canEdit(true)
                        .editTextStr("编辑的内容")
                        .hintEditTextStr("提示内容")
                        .message("内容")
                        .build();
                wyaCustomDialog.setNoOnclickListener(() -> {
                    wyaCustomDialog.dismiss();
                });
                wyaCustomDialog.setYesOnclickListener(() -> wyaCustomDialog.dismiss());
                wyaCustomDialog.show();
                break;
            case "无标题列表":
                wyaCustomDialog = new WYACustomDialog.Builder(this)
                        .setLayoutRes(R.layout.way_dialog_custom_list_layout, new CustomListener() {
                            @Override
                            public void customLayout(View v) {
                                RecyclerView recyclerView = v.findViewById(R.id.recycle_view);
                                data = new ArrayList<>();
                                for (int i = 0; i < 50; i++) {
                                    data.add("" + i);
                                }
                                if (data != null && data.size() > 0) {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(DialogExampleActivity.this));
                                    dialogListAdapter = new DialogListAdapter(DialogExampleActivity.this, R.layout.wya_custom_dialog_item_layout, data);
                                    recyclerView.setAdapter(dialogListAdapter);

                                    //RecyclerView条目点击事件
                                    dialogListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                            getWyaToast().showShort(position + "");
                                        }
                                    });
                                }
                            }
                        })
                        .height(ScreenUtil.dip2px(DialogExampleActivity.this, 200))
                        .cancelable(true)
                        .Gravity(Gravity.CENTER)
                        .cancelTouchout(true)
                        .build();
                wyaCustomDialog.setNoOnclickListener(() -> {
                    wyaCustomDialog.dismiss();
                });
                wyaCustomDialog.setYesOnclickListener(() -> wyaCustomDialog.dismiss());
                wyaCustomDialog.show();
                break;
            case "有标题列表":
                wyaCustomDialog = new WYACustomDialog.Builder(this)
                        .setLayoutRes(R.layout.way_dialog_custom_list_layout, new CustomListener() {
                            @Override
                            public void customLayout(View v) {
                                RecyclerView recyclerView = v.findViewById(R.id.recycle_view);
                                TextView title = v.findViewById(R.id.title);
                                title.setText("标题");
                                title.setVisibility(View.VISIBLE);
                                data = new ArrayList<>();
                                for (int i = 0; i < 50; i++) {
                                    data.add("" + i);
                                }
                                if (data != null && data.size() > 0) {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(DialogExampleActivity.this));
                                    dialogListAdapter = new DialogListAdapter(DialogExampleActivity.this, R.layout.wya_custom_dialog_item_layout, data);
                                    recyclerView.setAdapter(dialogListAdapter);

                                    //RecyclerView条目点击事件
                                    dialogListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                            getWyaToast().showShort(position + "");
                                        }
                                    });
                                }
                            }
                        })
                        .height(ScreenUtil.dip2px(DialogExampleActivity.this, 200))
                        .cancelable(true)
                        .Gravity(Gravity.CENTER)
                        .cancelTouchout(true)
                        .build();
                wyaCustomDialog.setNoOnclickListener(() -> {
                    wyaCustomDialog.dismiss();
                });
                wyaCustomDialog.setYesOnclickListener(() -> wyaCustomDialog.dismiss());
                wyaCustomDialog.show();
                break;
            case "自定义":
                wyaCustomDialog = new WYACustomDialog.Builder(this)
                        .setLayoutRes(R.layout.way_dialog_custom_list_layout, new CustomListener() {
                            @Override
                            public void customLayout(View v) {
                                TextView title = v.findViewById(R.id.title);
                                title.setText("自定义");
                                title.setVisibility(View.VISIBLE);
                            }
                        })
                        .height(ScreenUtil.dip2px(DialogExampleActivity.this, 200))
                        .cancelable(true)
                        .Gravity(Gravity.CENTER)
                        .cancelTouchout(true)
                        .build();
                wyaCustomDialog.setNoOnclickListener(() -> {
                    wyaCustomDialog.dismiss();
                });
                wyaCustomDialog.setYesOnclickListener(() -> wyaCustomDialog.dismiss());
                wyaCustomDialog.show();
                break;
            case "底部弹出列表":
                wyaCustomDialog = new WYACustomDialog.Builder(this)
                        .setLayoutRes(R.layout.way_dialog_custom_list_layout, new CustomListener() {
                            @Override
                            public void customLayout(View v) {
                                RecyclerView recyclerView = v.findViewById(R.id.recycle_view);
                                TextView title = v.findViewById(R.id.title);
                                title.setText("标题");
                                title.setVisibility(View.VISIBLE);
                                data = new ArrayList<>();
                                for (int i = 0; i < 50; i++) {
                                    data.add("" + i);
                                }
                                if (data != null && data.size() > 0) {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(DialogExampleActivity.this));
                                    dialogListAdapter = new DialogListAdapter(DialogExampleActivity.this, R.layout.wya_custom_dialog_item_layout, data);
                                    recyclerView.setAdapter(dialogListAdapter);

                                    //RecyclerView条目点击事件
                                    dialogListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                            getWyaToast().showShort(position + "");
                                        }
                                    });
                                }
                            }
                        })
                        .height(ScreenUtil.dip2px(DialogExampleActivity.this, 200))
                        .cancelable(true)
                        .Gravity(Gravity.BOTTOM)
                        .cancelTouchout(true)
                        .build();
                wyaCustomDialog.setNoOnclickListener(() -> {
                    wyaCustomDialog.dismiss();
                });
                wyaCustomDialog.setYesOnclickListener(() -> wyaCustomDialog.dismiss());
                wyaCustomDialog.show();
                break;
            case "底部弹出分享":
                wyaCustomDialog = new WYACustomDialog.Builder(this)
                        .setLayoutRes(R.layout.way_dialog_custom_list_layout, new CustomListener() {
                            @Override
                            public void customLayout(View v) {
                                RecyclerView recyclerView = v.findViewById(R.id.recycle_view);
                                TextView title = v.findViewById(R.id.title);
                                title.setText("分享");
                                title.setVisibility(View.VISIBLE);
                                data = new ArrayList<>();
                                for (int i = 0; i < 50; i++) {
                                    data.add("" + i);
                                }
                                if (data != null && data.size() > 0) {
                                    recyclerView.setLayoutManager(new GridLayoutManager(DialogExampleActivity.this, 4));
                                    dialogListAdapter = new DialogListAdapter(DialogExampleActivity.this, R.layout.wya_grid_item, data);
                                    recyclerView.setAdapter(dialogListAdapter);

                                    //RecyclerView条目点击事件
                                    dialogListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                            getWyaToast().showShort(position + "");
                                        }
                                    });
                                }
                            }
                        })
                        .height(ScreenUtil.dip2px(DialogExampleActivity.this, 200))
                        .cancelable(true)
                        .Gravity(Gravity.BOTTOM)
                        .cancelTouchout(true)
                        .build();
                wyaCustomDialog.setNoOnclickListener(() -> {
                    wyaCustomDialog.dismiss();
                });
                wyaCustomDialog.setYesOnclickListener(() -> wyaCustomDialog.dismiss());
                wyaCustomDialog.show();
                break;

        }
    }

    private void initItems() {
        mDatas = new ArrayList<>();
        List<String> bean1 = new ArrayList<>();
        List<String> bean2 = new ArrayList<>();
        List<String> bean3 = new ArrayList<>();
        // id , pid , label , 其他属性
        bean1.add("一个按钮");
        bean1.add("两个按钮");
        bean1.add("有文本编辑");
        bean1.add("加载");
        Item item1 = new Item();
        item1.setTitle("提示框");
        item1.setChild(bean1);

        bean2.add("无标题列表");
        bean2.add("有标题列表");
        bean2.add("自定义");
        Item item2 = new Item();
        item2.setTitle("自定义");
        item2.setChild(bean2);

        bean3.add("底部弹出列表");
        bean3.add("底部弹出分享");
        Item item3 = new Item();
        item3.setTitle("底部弹出");
        item3.setChild(bean3);

        mDatas.add(item1);
        mDatas.add(item2);
        mDatas.add(item3);
    }

    private void showWYADialogLoading() {
        wyaLoadingDialog = new WYALoadingDialog(this, canceledOnTouch, cancelable);
        wyaLoadingDialog.show();
    }
}
