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
 * @date: 2018/11/22 11:58
 * @author: Chunjiang Mao
 * @classname: WYAToast
 * @describe: 自定义Toast
 */

public class WYAToast {
    private static Toast toast;
    private static Toast toast_custom;

    /**
     * 初始化Toast(消息，时间)
     */
    public static Toast initToast(Context context, CharSequence message, int duration) {
        toast = Toast.makeText(context, message, duration);
        return toast;
    }
    
    /**
     * 短时间显示Toast(消息 String等)
     */
    public static void showShort(Context context, CharSequence message) {
        initToast(context, message, Toast.LENGTH_SHORT).show();
    }
    
    /**
     * 长时间显示Toast(消息 String等)
     */
    public static void showLong(Context context, CharSequence message) {
        initToast(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间(消息 String等，时间)
     */
    public static void show(Context context, CharSequence message, int duration) {
        initToast(context, message, duration).show();
    }

    /**
     * 显示有image的toast 这是个view
     */
    public static Toast showToastWithImage(Context context, final String tvStr, final int imageResource, int gravity) {
        if (toast_custom == null) {
            toast_custom = new Toast(context);
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
        toast_custom.setView(view);
        if (gravity == Gravity.CENTER) {
            toast_custom.setGravity(gravity, 0, 0);
        } else if (gravity == Gravity.BOTTOM) {
            toast_custom.setGravity(gravity, 0, 0);
        }
        toast_custom.show();
        return toast_custom;
    
    }
}

