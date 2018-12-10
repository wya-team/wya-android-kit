package com.wya.uikit.keyboard;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wya.uikit.R;

/**
 * 创建日期：2018/11/23 14:02
 * 作者： Mao Chunjiang
 * 文件名称： WYKCustomNumberKeyboard
 * 类说明：自定义数字键盘
 */

public class WYKCustomNumberKeyboard extends Dialog implements View.OnClickListener{
    private Context context;
    private ChooseInterface chooseInterface;
    private View view;
    private TextView tv_num0;
    private TextView tv_num1;
    private TextView tv_num2;
    private TextView tv_num3;
    private TextView tv_num4;
    private TextView tv_num5;
    private TextView tv_num6;
    private TextView tv_num7;
    private TextView tv_num8;
    private TextView tv_num9;
    private TextView tv_num_point;
    private TextView tv_hide;
    private TextView tv_del;
    private TextView tv_sure;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_num0) {
            chooseInterface.selectPosition(0);
        } else if (i == R.id.tv_num1) {
            chooseInterface.selectPosition(1);
        } else if (i == R.id.tv_num2) {
            chooseInterface.selectPosition(2);
        } else if (i == R.id.tv_num3) {
            chooseInterface.selectPosition(3);
        } else if (i == R.id.tv_num4) {
            chooseInterface.selectPosition(4);
        } else if (i == R.id.tv_num5) {
            chooseInterface.selectPosition(5);
        } else if (i == R.id.tv_num6) {
            chooseInterface.selectPosition(6);
        } else if (i == R.id.tv_num7) {
            chooseInterface.selectPosition(7);
        } else if (i == R.id.tv_num8) {
            chooseInterface.selectPosition(8);
        } else if (i == R.id.tv_num9) {
            chooseInterface.selectPosition(9);
        } else if (i == R.id.tv_num_point) {
            chooseInterface.selectPosition(10);
        } else if (i == R.id.tv_hide) {
            chooseInterface.selectPosition(11);
        } else if (i == R.id.tv_del) {
            chooseInterface.selectPosition(12);
        } else if (i == R.id.tv_sure) {
            chooseInterface.selectPosition(13);
        }
    }


    public interface ChooseInterface {
        void selectPosition(int position);
    }

    public WYKCustomNumberKeyboard(@NonNull Context context, final ChooseInterface chooseInterface) {
        super(context);
        this.context = context;
        this.chooseInterface = chooseInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        view = View.inflate(context, R.layout.wya_custom_number_keyboard, null);

        view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.keyboard_enter_bottom_to_top));
        setContentView(view);
        // 这句话起全屏的作用
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        initClick();
    }

    private void initClick() {
        tv_num0 = view.findViewById(R.id.tv_num0);
        tv_num1 = view.findViewById(R.id.tv_num1);
        tv_num2 = view.findViewById(R.id.tv_num2);
        tv_num3 = view.findViewById(R.id.tv_num3);
        tv_num4 = view.findViewById(R.id.tv_num4);
        tv_num5 = view.findViewById(R.id.tv_num5);
        tv_num6 = view.findViewById(R.id.tv_num6);
        tv_num7 = view.findViewById(R.id.tv_num7);
        tv_num8 = view.findViewById(R.id.tv_num8);
        tv_num9 = view.findViewById(R.id.tv_num9);
        tv_num_point = view.findViewById(R.id.tv_num_point);
        tv_hide = view.findViewById(R.id.tv_hide);
        tv_del = view.findViewById(R.id.tv_del);
        tv_sure = view.findViewById(R.id.tv_sure);

        tv_num0.setOnClickListener(this);
        tv_num1.setOnClickListener(this);
        tv_num2.setOnClickListener(this);
        tv_num3.setOnClickListener(this);
        tv_num4.setOnClickListener(this);
        tv_num5.setOnClickListener(this);
        tv_num6.setOnClickListener(this);
        tv_num7.setOnClickListener(this);
        tv_num8.setOnClickListener(this);
        tv_num9.setOnClickListener(this);
        tv_num_point.setOnClickListener(this);
        tv_hide.setOnClickListener(this);
        tv_del.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.dismiss();
        return super.onTouchEvent(event);
    }
}
