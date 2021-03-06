package com.wya.uikit.toast;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.uikit.R;

/**
 * 创建日期：2018/11/22 11:58
 * 作者： Mao Chunjiang
 * 文件名称： WYAToast
 * 类说明：自定义Toast
 */

public class WYAToast {
    private static Toast toast;
    private static Toast toast2;
    private Context context;

    public WYAToast(Context context) {
        this.context = context;
    }

    /**
     * 初始化Toast(消息，时间)
     */
    private Toast initToast(CharSequence message, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, message, duration);
        } else {
            //设置文字
            toast.setText(message);
            //设置存续期间
            toast.setDuration(duration);
        }
        return toast;
    }

    /**
     * 短时间显示Toast(消息 String等)
     */
    public void showShort(CharSequence message) {
        initToast(message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 短时间显示Toast（资源id)
     */
    public void showShort(int strResId) {
        initToast(context.getResources().getText(strResId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast(消息 String等)
     */
    public void showLong(CharSequence message) {
        initToast(message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast（资源id)
     */
    public void showLong(int strResId) {
        initToast(context.getResources().getText(strResId), Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间(消息 String等，时间)
     */
    public void show(CharSequence message, int duration) {
        initToast(message, duration).show();
    }

    /**
     * 自定义显示Toast时间(消息 资源id，时间)
     */
    public void show(int strResId, int duration) {
        initToast(context.getResources().getText(strResId), duration).show();
    }

    /**
     * 显示有image的toast 这是个view
     */
    public Toast showToastWithImg(final String tvStr, final int imageResource, int gravity) {
        if (toast2 == null) {
            toast2 = new Toast(context);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.wya_custom_toast_layout, null);
        TextView tv = view.findViewById(R.id.tv_toast_custom);
        tv.setText(TextUtils.isEmpty(tvStr) ? "" : tvStr);
        ImageView iv = view.findViewById(R.id.img_toast_custom);
        if (imageResource > 0) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(imageResource);
        } else {
            iv.setVisibility(View.GONE);
        }
        toast2.setView(view);
        if(gravity == Gravity.CENTER){
            toast2.setGravity(gravity, 0, 0);
        } else if(gravity == Gravity.BOTTOM){
            toast2.setGravity(gravity, 0, 0);
        }
        toast2.show();
        return toast2;

    }
}

