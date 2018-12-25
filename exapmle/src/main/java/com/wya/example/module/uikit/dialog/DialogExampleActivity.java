package com.wya.example.module.uikit.dialog;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.example.module.example.view.CustomerExpandableListView;
import com.wya.example.module.uikit.dialog.adapter.DialogExpandableListAdapter;
import com.wya.example.module.uikit.dialog.adapter.DialogListAdapter;
import com.wya.example.module.uikit.dialog.adapter.ShareDialogListAdapter;
import com.wya.example.module.uikit.dialog.bean.Item;
import com.wya.example.module.uikit.dialog.bean.ShareItem;
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
    CustomerExpandableListView expendList;
    @BindView(R.id.tab_loading)
    TableRow tabLoading;

    private List<Item> mDatas;
    private DialogExpandableListAdapter adapter;
    private List<String> data = new ArrayList<>();

    private List<ShareItem> shareItems = new ArrayList<>();
    private String[] shareName = {"新浪微博", "微信好友", "生活圈", "QQ", "字号", "刷新", "复制链接", "投诉"};
    private int[] image = {R.drawable.icon_weibo, R.drawable.icon_weixin, R.drawable.icon_pengyouquan, R.drawable.icon_qq, R.drawable.icon_font, R.drawable.icon_refresh, R.drawable.icon_link, R.drawable.icon_complain};

    private DialogListAdapter dialogListAdapter;

    private WYACustomDialog wyaCustomDialog;
    private WYALoadingDialog wyaLoadingDialog;
    private boolean canceledOnTouch = true;
    private boolean cancelable = true;
    private int type;


    @Override
    protected int getLayoutID() {
        return R.layout.activity_dialog_example;
    }


    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 1);
        if (type == 1) {
            setToolBarTitle("弹框(dialog)");
        } else {
            setToolBarTitle("活动指示器(dialog)");
            tabLoading.setVisibility(View.VISIBLE);
        }
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help, true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(DialogExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
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
        expendList.setGroupIndicator(null);
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
                                    data.add("列表" + i);
                                }
                                if (data != null && data.size() > 0) {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(DialogExampleActivity.this));
                                    dialogListAdapter = new DialogListAdapter(DialogExampleActivity.this, R.layout.wya_custom_dialog_item_layout, data);
                                    recyclerView.setAdapter(dialogListAdapter);

                                    //RecyclerView条目点击事件
                                    dialogListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                            getWyaToast().showShort(data.get(position));
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
                                    data.add("列表" + i);
                                }
                                if (data != null && data.size() > 0) {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(DialogExampleActivity.this));
                                    dialogListAdapter = new DialogListAdapter(DialogExampleActivity.this, R.layout.wya_custom_dialog_item_layout, data);
                                    recyclerView.setAdapter(dialogListAdapter);

                                    //RecyclerView条目点击事件
                                    dialogListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                            getWyaToast().showShort(data.get(position));
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
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void customLayout(View v) {
                                LinearLayout parent = v.findViewById(R.id.parent);
                                RecyclerView recyclerView = v.findViewById(R.id.recycle_view);
                                parent.setBackground(null);
                                parent.setBackgroundColor(getResources().getColor(R.color.white));
                                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) parent.getLayoutParams();
                                lp.setMargins(0, 0, 0, 0);
                                parent.setLayoutParams(lp);
                                TextView title = v.findViewById(R.id.title);
                                title.setText("标题");
                                title.setVisibility(View.VISIBLE);
                                data = new ArrayList<>();
                                for (int i = 0; i < 50; i++) {
                                    data.add("列表" + i);
                                }
                                if (data != null && data.size() > 0) {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(DialogExampleActivity.this));
                                    dialogListAdapter = new DialogListAdapter(DialogExampleActivity.this, R.layout.wya_custom_dialog_item_layout, data);
                                    recyclerView.setAdapter(dialogListAdapter);

                                    //RecyclerView条目点击事件
                                    dialogListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                            getWyaToast().showShort(data.get(position));
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
                        .setLayoutRes(R.layout.way_dialog_custom_share_layout, new CustomListener() {
                            @Override
                            public void customLayout(View v) {
                                RecyclerView recyclerView = v.findViewById(R.id.recycle_view);
                                TextView title = v.findViewById(R.id.title);
                                TextView cancel = v.findViewById(R.id.cancel);
                                shareItems = new ArrayList<>();
                                for (int i = 0; i < shareName.length; i++) {
                                    ShareItem shareItem = new ShareItem();
                                    shareItem.setName(shareName[i]);
                                    shareItem.setImage(image[i]);
                                    shareItems.add(shareItem);
                                }
                                if (shareItems != null && shareItems.size() > 0) {
                                    recyclerView.setLayoutManager(new GridLayoutManager(DialogExampleActivity.this, 4));
                                    ShareDialogListAdapter shareDialogListAdapter = new ShareDialogListAdapter(DialogExampleActivity.this, R.layout.wya_share_item, shareItems);
                                    recyclerView.setAdapter(shareDialogListAdapter);

                                    //RecyclerView条目点击事件
                                    shareDialogListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                            getWyaToast().showShort(shareItems.get(position).getName() + "");
                                        }
                                    });
                                }
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        wyaCustomDialog.dismiss();
                                    }
                                });
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
                break;

            case "菊花加载":
                showWYADialogLoading();
                break;

            case "系统加载":
                wyaCustomDialog = new WYACustomDialog.Builder(this)
                        .setLayoutRes(R.layout.wya_dialog_system_loading, new CustomListener() {
                            @Override
                            public void customLayout(View v) {
                            }
                        })
                        .cancelable(true)
                        .cancelTouchout(true)
                        .build();
                wyaCustomDialog.show();
                break;
            case "自定义加载":
                wyaCustomDialog = new WYACustomDialog.Builder(this)
                        .setLayoutRes(R.layout.wya_dialog_custom_loading, new CustomListener() {
                            @Override
                            public void customLayout(View v) {
                            }
                        })
                        .cancelable(true)
                        .cancelTouchout(true)
                        .build();
                wyaCustomDialog.show();
                break;

        }
    }

    private void initItems() {
        mDatas = new ArrayList<>();
        List<String> bean1 = new ArrayList<>();
        List<String> bean2 = new ArrayList<>();
        List<String> bean3 = new ArrayList<>();
        List<String> bean4 = new ArrayList<>();
        // id , pid , label , 其他属性
        bean1.add("一个按钮");
        bean1.add("两个按钮");
        bean1.add("有文本编辑");
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


        bean4.add("系统加载");
        bean4.add("菊花加载");
        bean4.add("自定义加载");
        Item item4 = new Item();
        item4.setTitle("加载");
        item4.setChild(bean4);

        if (type == 1) {
            mDatas.add(item1);
            mDatas.add(item2);
            mDatas.add(item3);
        } else {
            mDatas.add(item4);
        }
    }

    private void showWYADialogLoading() {
        wyaLoadingDialog = new WYALoadingDialog(this, canceledOnTouch, cancelable);
        wyaLoadingDialog.show();
    }

}
