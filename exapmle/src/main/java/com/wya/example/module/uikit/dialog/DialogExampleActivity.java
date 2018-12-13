package com.wya.example.module.uikit.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.uikit.customitems.expandrecyclerview.adapter.ItemAdapter;
import com.wya.example.module.uikit.customitems.expandrecyclerview.bean.Item;
import com.wya.uikit.dialog.CustomListener;
import com.wya.uikit.dialog.WYACustomDialog;
import com.wya.uikit.dialog.WYALoadingDialog;
import com.wya.uikit.expandrecyclerview.bean.RecyclerViewData;
import com.wya.uikit.expandrecyclerview.listener.OnRecyclerViewListener;
import com.wya.uikit.toast.WYAToast;
import com.wya.utils.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建日期：2018/11/23 16:23
 * 作者： Mao Chunjiang
 * 文件名称：DialogExampleActivity
 * 类说明：dialog例子
 */

public class DialogExampleActivity extends BaseActivity {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;


    private LinearLayoutManager linearLayoutManager;
    private ItemAdapter adapter;
    private List<RecyclerViewData> mDatas;


    private boolean canceledOnTouch = true;
    private boolean cancelable = true;
    private boolean list = false;
    private String title = "我是标题";
    private String content = "我是内容";
    private String cancel = "取消";
    private String sure = "确定";
    private boolean isShowButton = true;
    private String edit_text_str = "我是编辑框内容";
    private List<String> data = new ArrayList<>();


    private DialogListAdapter dialogListAdapter;


    @Override
    protected int getLayoutID() {
        return R.layout.activity_dialog_example;
    }


    @Override
    protected void initView() {
        setToolBarTitle("Dialog");
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
                setClick(groupPosition, childPosition);
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

    private void setClick(int groupPosition, int childPosition) {
        String s = ((Item)mDatas.get(groupPosition).getChild(childPosition)).getName();
        switch (s){
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
                                for (int i = 0; i <50; i++) {
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
                                            WYAToast.showShort(DialogExampleActivity.this, position + "");
                                        }
                                    });
                                }
                            }
                        })
                        .height(ScreenUtil.dip2px(DialogExampleActivity.this,200))
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
                                for (int i = 0; i <50; i++) {
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
                                            WYAToast.showShort(DialogExampleActivity.this, position + "");
                                        }
                                    });
                                }
                            }
                        })
                        .height(ScreenUtil.dip2px(DialogExampleActivity.this,200))
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
                        .height(ScreenUtil.dip2px(DialogExampleActivity.this,200))
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
                                for (int i = 0; i <50; i++) {
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
                                            WYAToast.showShort(DialogExampleActivity.this, position + "");
                                        }
                                    });
                                }
                            }
                        })
                        .height(ScreenUtil.dip2px(DialogExampleActivity.this,200))
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
                                for (int i = 0; i <50; i++) {
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
                                            WYAToast.showShort(DialogExampleActivity.this, position + "");
                                        }
                                    });
                                }
                            }
                        })
                        .height(ScreenUtil.dip2px(DialogExampleActivity.this,200))
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
        List<Item> bean1 = new ArrayList<>();
        List<Item> bean2 = new ArrayList<>();
        List<Item> bean3 = new ArrayList<>();
        List<Item> bean4 = new ArrayList<>();
        // id , pid , label , 其他属性
        bean1.add(new Item("一个按钮"));
        bean1.add(new Item("两个按钮"));
        bean1.add(new Item("有文本编辑"));
        bean1.add(new Item("加载"));


        bean2.add(new Item("无标题列表"));
        bean2.add(new Item("有标题列表"));
        bean2.add(new Item("自定义"));

        bean3.add(new Item("底部弹出列表"));
        bean3.add(new Item("底部弹出分享"));


        mDatas.add(new RecyclerViewData("提示框", bean1, true));
        mDatas.add(new RecyclerViewData("自定义", bean2, true));
        mDatas.add(new RecyclerViewData("底部弹出", bean3, true));
    }


    /**
     * 是否保存
     */
    private WYACustomDialog wyaCustomDialog;

    private void showWYADialog() {
        wyaCustomDialog = new WYACustomDialog.Builder(this)
                .setLayoutRes(R.layout.way_dialog_custom_list_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        RecyclerView recyclerView = v.findViewById(R.id.recycle_view);
                        data = new ArrayList<>();
                        for (int i = 0; i <4; i++) {
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
                                    WYAToast.showShort(DialogExampleActivity.this, position + "");
                                }
                            });
                        }
                    }
                })
                .cancelable(true)
                .Gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .build();
        wyaCustomDialog.setNoOnclickListener(() -> {
            wyaCustomDialog.dismiss();
        });
        wyaCustomDialog.setYesOnclickListener(() -> wyaCustomDialog.dismiss());
        wyaCustomDialog.show();
    }

//    @OnClick({R.id.tv_loading, R.id.radio_title_show, R.id.radio_title_unshow, R.id.radio_content_show, R.id.radio_content_unshow, R.id.radio_button_1, R.id.radio_button_2, R.id.radio_button_3, R.id.radio_edit_show, R.id.radio_edit_unshow, R.id.radio_list_show, R.id.radio_list_unshow, R.id.radio_canceled_on_touch, R.id.radio_uncanceled_on_touch, R.id.radio_cancelable, R.id.radio_un_cancelable, R.id.tv_show})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.radio_title_show:
//                title = "我是标题";
//                break;
//            case R.id.radio_title_unshow:
//                title = "";
//                break;
//            case R.id.radio_content_show:
//                content = "我是内容";
//                break;
//            case R.id.radio_content_unshow:
//                content = "";
//                break;
//            case R.id.radio_button_1:
//                isShowButton = false;
//                cancel = "";
//                sure = "";
//                break;
//            case R.id.radio_button_2:
//                isShowButton = true;
//                cancel = "";
//                sure = "确定";
//                break;
//            case R.id.radio_button_3:
//                isShowButton = true;
//                cancel = "取消";
//                sure = "确定";
//                break;
//            case R.id.radio_edit_show:
//                edit_text_str = "我是编辑框内容";
//                break;
//            case R.id.radio_edit_unshow:
//                edit_text_str = "";
//                break;
//            case R.id.radio_list_show:
//                data.add("第一个");
//                data.add("第二个");
//                data.add("第三个");
//                break;
//            case R.id.radio_list_unshow:
//                data.clear();
//                break;
//            case R.id.radio_canceled_on_touch:
//                canceledOnTouch = true;
//                break;
//            case R.id.radio_uncanceled_on_touch:
//                canceledOnTouch = false;
//                break;
//            case R.id.radio_cancelable:
//                cancelable = true;
//                break;
//            case R.id.radio_un_cancelable:
//                cancelable = false;
//                break;
//            case R.id.tv_show:
//                showWYADialog();
//                break;
//            case R.id.tv_loading:
//                showWYADialogLoading();
//                break;
//        }
//    }

    private WYALoadingDialog wyaLoadingDialog;

    private void showWYADialogLoading() {
        wyaLoadingDialog = new WYALoadingDialog(this, canceledOnTouch, cancelable);
        wyaLoadingDialog.show();
    }

}
