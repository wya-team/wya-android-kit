package com.wya.example.module.uikit.popupwindow;

import android.view.View;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.popupwindow.WYAPopupWindow;

import java.util.ArrayList;
import java.util.List;

public class PopupWindowExampleActivity extends BaseActivity {


    private List<String> list;
    private WYAPopupWindow wyaPopupWindow;
    @Override
    protected void initView() {
        setToolBarTitle("PopupWindow");
        setPopupWindow();
    }

    private void setPopupWindow() {
        list = new ArrayList<>();
        list.add("我是第一个");
        list.add("第二个");
        list.add("第三个最帅");
        list.add("四");
        setRightOnclickListener(new onRightOnclickListener() {
            @Override
            public void onRightClick(View view) {
                wyaPopupWindow.show(view,  -100, 0);
            }
        });
        wyaPopupWindow = new WYAPopupWindow(PopupWindowExampleActivity.this, list);
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
