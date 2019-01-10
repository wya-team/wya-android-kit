package com.wya.uikit.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.wya.uikit.R;

/**
 * @date: 2018/11/23 16:13
 * @author: Chunjiang Mao
 * @classname: WYALoadingDialog
 * @describe: 加载动画
 */

public class WYALoadingDialog extends Dialog {
    private final AnimationDrawable animationDrawable;
    private View view;
    private ColorDrawable colorDrawable;
    private ImageView imgLoad;
    private TextView hintText;
    
    @SuppressLint("ResourceType")
    public WYALoadingDialog(Context activity, boolean canceledOnTouch, boolean cancelable) {
        super(activity);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        view = View.inflate(activity, R.layout.wya_dialog_loading, null);
        setContentView(view);
        colorDrawable = new ColorDrawable(0x00000000);
        getWindow().setBackgroundDrawable(colorDrawable);
        imgLoad = view.findViewById(R.id.img_load);
        hintText = view.findViewById(R.id.hintTv);
        animationDrawable = (AnimationDrawable) imgLoad.getBackground();
        getWindow().setDimAmount(0);
        //取消dialog空白处点击消失事件
        this.setCanceledOnTouchOutside(canceledOnTouch);
        setCancelable(cancelable);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animationDrawable.start();
    }
    
    public void setText(String text) {
        if (text != null && !"".equals(text)) {
            hintText.setText(text);
            hintText.setVisibility(View.VISIBLE);
        } else {
            hintText.setVisibility(View.GONE);
        }
    }
}
