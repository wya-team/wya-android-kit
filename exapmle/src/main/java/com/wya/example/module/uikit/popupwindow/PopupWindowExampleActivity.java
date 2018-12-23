package com.wya.example.module.uikit.popupwindow;

import android.content.Intent;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.popupwindow.WYAPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用SwipebackHelper时需设置theme为translucent,否则部分机型会出现黑屏
 * 弹出PopupWindow时theme不能设置为trasnlucent
 * 故页面禁用SwipebackLayout的手势，即设置 getSwipeBackLayout().setEnableGesture(false);
 * 使用Gesture关闭Activity时设置动画的方式 即设置setSwipeBack(true);
 */
public class PopupWindowExampleActivity extends BaseActivity {
    
    private List<String> list;
    private WYAPopupWindow wyaPopupWindow;
    
    @Override
    protected void initView() {
        getSwipeBackLayout().setEnableGesture(false);
        setSwipeBack(true);
        setToolBarTitle("PopupWindow");
        initImgRight(R.drawable.icon_help, true);
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_notice, true);
        setRightImageOnclickListener(view -> {
            startActivity(new Intent(PopupWindowExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setPopupWindow();
    }
    
    private void setPopupWindow() {
        list = new ArrayList<>();
        list.add("我是第一个");
        list.add("第二个");
        list.add("第三个最帅");
        list.add("四");
        setRightImageAntherOnclickListener(view -> wyaPopupWindow.show(view, -100, 0));
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
