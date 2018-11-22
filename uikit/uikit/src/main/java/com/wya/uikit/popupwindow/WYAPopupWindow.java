package com.wya.uikit.popupwindow;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wya.uikit.R;

import java.util.List;

/**
 * 创建日期：2018/11/21 16:22
 * 作者： Mao Chunjiang
 * 文件名称： WYAPopupWindow
 * 类说明：自定义popupwindow
 */

public class WYAPopupWindow extends PopupWindow {
    private View conentView;
    private RecyclerView recyclerView;
    private AnimUtil animUtil;

    private static final long DURATION = 500;
    private static final float START_ALPHA = 0.7f;
    private static final float END_ALPHA = 1f;
    private float bgAlpha = 1f;
    private boolean bright = false;

    private Activity context;

    private PopupWindListAdapter popupWindListAdapter;
    private PopupWindowListOnclickListener popupWindowListOnclickListener;//列表点击监听


    /**
     * 设置确定按钮被点击的接口
     */
    public interface PopupWindowListOnclickListener {
        void onPopupWindowListClick(int position);
    }

    /**
     * 设置列表点击事件
     * @param popupWindowListOnclickListener
     */
    public void setPopupWindowListOnclickListener(PopupWindowListOnclickListener popupWindowListOnclickListener) {
        this.popupWindowListOnclickListener = popupWindowListOnclickListener;
    }

    public WYAPopupWindow(Activity context, List<String> list) {
        this.context = context;
        // 设置布局文件
        conentView = LayoutInflater.from(context).inflate(R.layout.wya_pop_add, null);
        this.setContentView(conentView);
        recyclerView = conentView.findViewById(R.id.popup_window_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        popupWindListAdapter = new PopupWindListAdapter(context, R.layout.popwindow_menu_item,list);
        recyclerView.setAdapter(popupWindListAdapter);
        //RecyclerView条目点击事件
        popupWindListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                popupWindowListOnclickListener.onPopupWindowListClick(position);
            }
        });
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
                toggleBright();
            }
        });
    }

    /**
     * 显示popupwindow
     * @param view
     * @param xoff
     * @param yoff
     */
    public void show(View view, int xoff, int yoff){
        toggleBright();
        this.showAsDropDown(view,  xoff, yoff);
    }

    /**
     * 根据popupwindow修改背景颜色
     */
    private void toggleBright() {
        animUtil = new AnimUtil();
        // 三个参数分别为：起始值 结束值 时长，那么整个动画回调过来的值就是从0.5f--1f的
        animUtil.setValueAnimator(START_ALPHA, END_ALPHA, DURATION);
        animUtil.addUpdateListener(new AnimUtil.UpdateListener() {
            @Override
            public void progress(float progress) {
                // 此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                bgAlpha = bright ? progress : (START_ALPHA + END_ALPHA - progress);
                backgroundAlpha(bgAlpha);
            }
        });
        animUtil.addEndListner(new AnimUtil.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                // 在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });
        animUtil.startAnimator();
    }

    /**
     * 此方法用于改变背景的透明度，从而达到“变暗”的效果
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        // 0.0-1.0
        lp.alpha = bgAlpha;
        context.getWindow().setAttributes(lp);
        // everything behind this window will be dimmed.
        // 此方法用来设置浮动层，防止部分手机变暗无效
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
