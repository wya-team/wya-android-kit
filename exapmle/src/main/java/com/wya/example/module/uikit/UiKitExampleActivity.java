package com.wya.example.module.uikit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.uikit.dialog.DialogExampleActivity;
import com.wya.example.module.uikit.popupwindow.PopupWindowExampleActivity;
import com.wya.example.module.uikit.tabbar.TabBarExampleActivity;
import com.wya.example.module.uikit.toast.ToastExampleActivity;
import com.wya.example.module.uikit.toolbar.ToolBarExampleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UiKitExampleActivity extends BaseActivity {

    @BindView(R.id.tv_tool_bar)
    TextView tvToolBar;
    @BindView(R.id.tv_dialog)
    TextView tvDialog;
    @BindView(R.id.tv_tab_bar)
    TextView tvTabBar;
    @BindView(R.id.tv_popupwindow)
    TextView tvPopupWindow;
    @BindView(R.id.tv_tab_layout)
    TextView tvTabLayout;
    @BindView(R.id.tv_toast)
    TextView tvToast;

    private Intent intent = null;

    @Override
    protected void initView() {
        initImgLeft(R.mipmap.icon_back_white, true);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_ui_kit_example;
    }


    @OnClick({R.id.tv_toast, R.id.tv_tool_bar, R.id.tv_popupwindow, R.id.tv_tab_bar, R.id.tv_tab_layout, R.id.tv_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tool_bar:
                intent = new Intent(UiKitExampleActivity.this, ToolBarExampleActivity.class);
                UiKitExampleActivity.this.startActivity(intent);
                break;
            case R.id.tv_tab_bar:
                Intent intent1 = new Intent(UiKitExampleActivity.this, TabBarExampleActivity
                        .class);
                startActivity(intent1);
                break;
            case R.id.tv_tab_layout:
                break;
            case R.id.tv_dialog:
                intent = new Intent(UiKitExampleActivity.this, DialogExampleActivity.class);
                UiKitExampleActivity.this.startActivity(intent);
                break;
            case R.id.tv_popupwindow:
                intent = new Intent(UiKitExampleActivity.this, PopupWindowExampleActivity.class);
                UiKitExampleActivity.this.startActivity(intent);
                break;
            case R.id.tv_toast:
                intent = new Intent(UiKitExampleActivity.this, ToastExampleActivity.class);
                UiKitExampleActivity.this.startActivity(intent);
                break;
        }
    }

}
