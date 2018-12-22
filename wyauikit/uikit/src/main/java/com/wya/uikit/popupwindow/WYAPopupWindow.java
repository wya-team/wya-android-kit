package com.wya.uikit.popupwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wya.uikit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2018/11/21 16:22
 * 作者： Mao Chunjiang
 * 文件名称： WYAPopupWindow
 * 类说明：自定义popupwindow
 */

public class WYAPopupWindow extends PopupWindow {
    private Activity context;
    private CustomListener customListener;
    private int layoutRes;
    private View contentView;
    private int backgroundImg = R.drawable.popup_window_icon_other_9;
    private LinearLayout ll_popup_window;

    private List<String> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private PopupWindListAdapter popupWindListAdapter;
    private PopupWindowListOnclickListener popupWindowListOnclickListener;//列表点击监听

    private static final long DURATION = 500;
    private static final float START_ALPHA = 0.7f;
    private static final float END_ALPHA = 1f;
    private float bgAlpha = 1f;
    private boolean bright = false;
    private AnimUtil animUtil;


    /**
     * 设置确定按钮被点击的接口
     */
    public interface PopupWindowListOnclickListener {
        void onPopupWindowListClick(int position);
    }

    /**
     * 设置列表点击事件
     *
     * @param popupWindowListOnclickListener
     */
    public void setPopupWindowListOnclickListener(PopupWindowListOnclickListener popupWindowListOnclickListener) {
        this.popupWindowListOnclickListener = popupWindowListOnclickListener;
    }


    public WYAPopupWindow(Builder builder) {
        super(builder.context);
        this.context = builder.context;
        this.list = builder.list;
        this.customListener = builder.customListener;
        this.backgroundImg = builder.backgroundImg;
        this.layoutRes = builder.layoutRes;
        initView(builder.context);
    }

    @SuppressLint("NewApi")
    private void initView(Context context) {
        contentView = LayoutInflater.from(context).inflate(layoutRes, null);
        if (customListener == null) {
            recyclerView = contentView.findViewById(R.id.popup_window_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            popupWindListAdapter = new PopupWindListAdapter(context, R.layout.popwindow_menu_item, list);
            recyclerView.setAdapter(popupWindListAdapter);
            ll_popup_window = contentView.findViewById(R.id.ll_popup_window);
            ll_popup_window.setBackground(context.getResources().getDrawable(backgroundImg));
            //RecyclerView条目点击事件
            popupWindListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    popupWindowListOnclickListener.onPopupWindowListClick(position);
                }
            });
        } else {
            customListener.customLayout(contentView);
        }

        setContentView(contentView);

        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置pop透明效果
        this.setBackgroundDrawable(new ColorDrawable(0x0000));
        // 设置pop出入动画
        this.setAnimationStyle(R.style.pop_add);

        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        this.setFocusable(true);
        // 设置pop可点击，为false点击事件无效，默认为true
        this.setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        this.setOutsideTouchable(true);
        // 设置pop关闭监听，用于改变背景透明度
        this.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleBright(false);
            }
        });
    }

    public static final class Builder {
        private Activity context;
        private List<String> list = new ArrayList<>();
        private int layoutRes = R.layout.wya_pop_add;
        private CustomListener customListener;
        private int backgroundImg = R.drawable.popup_window_icon_other_9;

        public Builder(Activity context) {
            this.context = context;
        }

        public Builder setLayoutRes(int res, CustomListener listener) {
            this.layoutRes = res;
            this.customListener = listener;
            return this;
        }

        public Builder list(List<String> list) {
            this.list = list;
            return this;
        }

        public Builder backgroundImg(int backgroundImg) {
            this.backgroundImg = backgroundImg;
            return this;
        }


        public WYAPopupWindow build() {
            return new WYAPopupWindow(this);
        }
    }

    /**
     * 显示popupwindow
     *
     * @param view
     * @param xoff
     * @param yoff
     */
    public void show(View view, int xoff, int yoff) {
        toggleBright(true);
        this.showAsDropDown(view, xoff, yoff);
    }

    /**
     * 根据popupwindow修改背景颜色
     */
    private void toggleBright(final boolean isShow) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        if (isShow) {
            lp.alpha = (float) 0.5;
            context.getWindow().setAttributes(lp);
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            lp.alpha = (float) 1.0;
            context.getWindow().setAttributes(lp);
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        animUtil = new AnimUtil();
        animUtil.startAnimator();
    }
}
