package com.wya.example.module.uikit.popupwindow;

import android.content.Intent;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.popupwindow.WYAPopupWindow;

import java.util.ArrayList;
import java.util.List;

public class PopupWindowExampleActivity extends BaseActivity {

    private List<String> list;
    private WYAPopupWindow wyaPopupWindow;

    @Override
    protected void initView() {
        setToolBarTitle("PopupWindow");
        initImgRight(R.drawable.icon_notice, true);
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help,true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(PopupWindowExampleActivity.this, ReadmeActivity.class).putExtra("url",url));
        });
        setPopupWindow();
    }

    private void setPopupWindow() {
        list = new ArrayList<>();
        list.add("我是第一个");
        list.add("第二个");
        list.add("第三个最帅");
        list.add("四");
        setRightImageOnclickListener(view -> wyaPopupWindow.show(view, -100, 0));
        wyaPopupWindow = new WYAPopupWindow.Builder(PopupWindowExampleActivity.this).list(list).build();
        wyaPopupWindow.setPopupWindowListOnclickListener(position -> {
            wyaPopupWindow.dismiss();
            Toast.makeText(PopupWindowExampleActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_popup_window_example;
    }

}
