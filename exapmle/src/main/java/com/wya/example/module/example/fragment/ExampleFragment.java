package com.wya.example.module.example.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.wya.example.R;
import com.wya.example.module.example.adapter.ExampleExpandableListAdapter;
import com.wya.example.module.example.bean.ExampleItem;
import com.wya.example.module.example.view.CustomerExpandableListView;
import com.wya.example.module.hardware.camera.StartCameraExampleActivity;
import com.wya.example.module.hardware.videoplayer.VideoPlayerExampleActivity;
import com.wya.example.module.uikit.badge.BadgeExampleActivity;
import com.wya.example.module.uikit.banner.BannerExampleActivity;
import com.wya.example.module.uikit.button.ButtonExampleActivity;
import com.wya.example.module.uikit.choicemenu.ChoiceMenuExampleActivity;
import com.wya.example.module.uikit.customedittext.CustomEditTextExampleActivity;
import com.wya.example.module.uikit.customitems.cardview.CardViewExampleActivity;
import com.wya.example.module.uikit.customitems.grid.GridExampleActivity;
import com.wya.example.module.uikit.customitems.inputitem.InputItemExampleActivity;
import com.wya.example.module.uikit.dialog.DialogExampleActivity;
import com.wya.example.module.uikit.drawerlayout.DrawerLayoutExampleActivity;
import com.wya.example.module.uikit.imagepicker.ImagePickerExampleActivity;
import com.wya.example.module.uikit.notice.NoticeExampleActivity;
import com.wya.example.module.uikit.paginationview.PaginationViewExampleActivity;
import com.wya.example.module.uikit.pickerview.PickerViewExampleActivity;
import com.wya.example.module.uikit.popupwindow.PopupWindowExampleActivity;
import com.wya.example.module.uikit.progress.ProgressExampleActivity;
import com.wya.example.module.uikit.searchbar.SearchBarExampleActivity;
import com.wya.example.module.uikit.segmentedcontrol.SegmentedControlExampleActivity;
import com.wya.example.module.uikit.slidder.SlideViewExampleActivity;
import com.wya.example.module.uikit.stepper.StepperExampleActivity;
import com.wya.example.module.uikit.tabbar.TabBarExampleActivity;
import com.wya.example.module.uikit.tablayout.TabLayoutExampleActivity;
import com.wya.example.module.uikit.toast.ToastExampleActivity;
import com.wya.example.module.uikit.toolbar.ToolBarExampleActivity;
import com.wya.example.module.utils.fliedownload.FileDownloadExampleActivity;
import com.wya.example.module.utils.image.QRCodeExampleActivity;
import com.wya.example.module.utils.realm.RealmExampleActivity;
import com.wya.uikit.dialog.WYACustomDialog;
import com.wya.utils.utils.DataCleanUtil;

import java.util.ArrayList;
import java.util.List;


public class ExampleFragment extends Fragment {

    CustomerExpandableListView expendList;
    private List<ExampleItem> mDatas;
    private ExampleExpandableListAdapter adapter;
    private View view;
    WYACustomDialog wyaCustomDialog = null;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.example_fragment, null);
        initView();
        return view;
    }

    protected void initView() {
        initItems();
        initExpandList();
    }


    private void initItems() {
        mDatas = new ArrayList<>();
        ExampleItem item1 = new ExampleItem();
        item1.setTitle("导航");
        List<String> bean1 = new ArrayList<>();
        bean1.add("抽屉(drawerlayout)");
        bean1.add("菜单(choicemenu)");
        bean1.add("导航栏(toolbar)");
        bean1.add("分页器(paginationview)");
        bean1.add("分段控制器(segmentedcontrol)");
        bean1.add("底部导航(tabbar)");
        bean1.add("分页控制器(tablayout)");
        bean1.add("搜索栏(searchBar)");
        bean1.add("气泡(popupwindow)");
        item1.setChild(bean1);

        ExampleItem item2 = new ExampleItem();
        item2.setTitle("数据录入");
        List<String> bean2 = new ArrayList<>();
        bean2.add("按钮(button)");
        bean2.add("日期选择(pickerview)");
        bean2.add("图片选择器(imagepicker)");
        bean2.add("多行输入(customeditext)");
        bean2.add("文本输入(customitems(WYAInputItem))");
        bean2.add("滑动输入条(slideview)");
        bean2.add("步进器(stepper)");
        bean2.add("拍照录制视频(camera)");
        bean2.add("扫码(camera)");
        item2.setChild(bean2);


        ExampleItem item3 = new ExampleItem();
        item3.setTitle("数据展示");
        List<String> bean3 = new ArrayList<>();
        bean3.add("徽标数(badge)");
        bean3.add("宫格(grid)");
        bean3.add("卡片(customitems(WYACardView))");
        bean3.add("轮播图(banner)");
        bean3.add("列表(list)");
        bean3.add("通告栏(notice)");
        bean3.add("二维码(utils(QRCodeUtil))");
        item3.setChild(bean3);

        ExampleItem item4 = new ExampleItem();
        item4.setTitle("操作反馈");
        List<String> bean4 = new ArrayList<>();
        bean4.add("弹框(dialog(WYACustomDialog))");
        bean4.add("活动指示器(dialog(WYALoadingDialog))");
        bean4.add("进度条(progressview)");
        bean4.add("轻提示(toast)");
        item4.setChild(bean4);

        ExampleItem item5 = new ExampleItem();
        item5.setTitle("其他");
        List<String> bean5 = new ArrayList<>();
        bean5.add("下载(utils(FileManagerUtil)");
        bean5.add("视频播放(dvideoplayer)");
        bean5.add("清理缓存(utils(DataCleanUtil))");
        bean5.add("数据库基本使用(realm)");
        item5.setChild(bean5);

        mDatas.add(item1);
        mDatas.add(item2);
        mDatas.add(item3);
        mDatas.add(item4);
        mDatas.add(item5);
    }

    private void initExpandList() {
        expendList = view.findViewById(R.id.expend_list);
        adapter = new ExampleExpandableListAdapter(getActivity(), mDatas);
        expendList.setAdapter(adapter);
        expendList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                mDatas.get(groupPosition).setOpen(!mDatas.get(groupPosition).isOpen());
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
            case "抽屉(drawerlayout)":
                startActivity(new Intent(getActivity(), DrawerLayoutExampleActivity.class));
                break;
            case "菜单(choicemenu)":
                startActivity(new Intent(getActivity(), ChoiceMenuExampleActivity.class));
                break;
            case "导航栏(toolbar)":
                startActivity(new Intent(getActivity(), ToolBarExampleActivity.class));
                break;
            case "分页器(paginationview)":
                startActivity(new Intent(getActivity(), PaginationViewExampleActivity.class));
                break;
            case "分段控制器(segmentedcontrol)":
                startActivity(new Intent(getActivity(), SegmentedControlExampleActivity.class));
                break;
            case "底部导航(tabbar)":
                startActivity(new Intent(getActivity(), TabBarExampleActivity.class));
                break;
            case "分页控制器(tablayout)":
                startActivity(new Intent(getActivity(), TabLayoutExampleActivity.class));
                break;
            case "搜索栏(searchBar)":
                startActivity(new Intent(getActivity(), SearchBarExampleActivity.class));
                break;
            case "气泡(popupwindow)":
                startActivity(new Intent(getActivity(), PopupWindowExampleActivity.class));
                break;

            case "按钮(button)":
                startActivity(new Intent(getActivity(), ButtonExampleActivity.class));
                break;
            case "日期选择(pickerview)":
                startActivity(new Intent(getActivity(), PickerViewExampleActivity.class));
                break;
            case "图片选择器(imagepicker)":
                startActivity(new Intent(getActivity(), ImagePickerExampleActivity.class));
                break;
            case "多行输入(customeditext)":
                startActivity(new Intent(getActivity(), CustomEditTextExampleActivity.class));
                break;
            case "文本输入(customitems(WYAInputItem))":
                startActivity(new Intent(getActivity(), InputItemExampleActivity.class));
                break;
            case "滑动输入条(slideview)":
                startActivity(new Intent(getActivity(), SlideViewExampleActivity.class));
                break;
            case "步进器(stepper)":
                startActivity(new Intent(getActivity(), StepperExampleActivity.class));
                break;
            case "拍照录制视频(camera)":
                startActivity(new Intent(getActivity(), StartCameraExampleActivity.class));
                break;
            case "扫码(camera)":
//                startActivity(new Intent(getActivity(), SearchBarExampleActivity.class));
                break;


            case "徽标数(badge)":
                startActivity(new Intent(getActivity(), BadgeExampleActivity.class));
                break;
            case "宫格(grid)":
                startActivity(new Intent(getActivity(), GridExampleActivity.class));
                break;
            case "卡片(customitems(WYACardView))":
                startActivity(new Intent(getActivity(), CardViewExampleActivity.class));
                break;
            case "轮播图(banner)":
                startActivity(new Intent(getActivity(), BannerExampleActivity.class));
                break;
            case "列表(list)":
//                startActivity(new Intent(getActivity(), InputItemExampleActivity.class));
                break;
            case "通告栏(notice)":
                startActivity(new Intent(getActivity(), NoticeExampleActivity.class));
                break;
            case "二维码(utils(QRCodeUtil))":
                startActivity(new Intent(getActivity(), QRCodeExampleActivity.class));
                break;


            case "弹框(dialog(WYACustomDialog))":
                startActivity(new Intent(getActivity(), DialogExampleActivity.class).putExtra("type", 1));
                break;
            case "活动指示器(dialog(WYALoadingDialog))":
                startActivity(new Intent(getActivity(), DialogExampleActivity.class).putExtra("type", 2));
                break;
            case "进度条(progressview)":
                startActivity(new Intent(getActivity(), ProgressExampleActivity.class));
                break;
            case "轻提示(toast)":
                startActivity(new Intent(getActivity(), ToastExampleActivity.class));
                break;


            case "下载(utils(FileManagerUtil)":
                startActivity(new Intent(getActivity(), FileDownloadExampleActivity.class));
                break;
            case "视频播放(dvideoplayer)":
                startActivity(new Intent(getActivity(), VideoPlayerExampleActivity.class));
                break;
            case "清理缓存(utils(DataCleanUtil))":
                try {
                    wyaCustomDialog = new WYACustomDialog.Builder(getActivity())
                            .title("清理缓存")
                            .message(String.format("%s%s%s","当前缓存",DataCleanUtil
                                    .getTotalCacheSize(getActivity()),",是否清理"))
                            .cancelable(true)
                            .build();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                wyaCustomDialog.setNoOnclickListener(() -> {
                    wyaCustomDialog.dismiss();
                });
                wyaCustomDialog.setYesOnclickListener(() -> {
                    DataCleanUtil.cleanTotalCache(getActivity());
                    wyaCustomDialog.dismiss();
                });
                wyaCustomDialog.show();
                break;
            case "数据库基本使用(realm)":
                startActivity(new Intent(getActivity(), RealmExampleActivity.class));
                break;

        }
    }
}
