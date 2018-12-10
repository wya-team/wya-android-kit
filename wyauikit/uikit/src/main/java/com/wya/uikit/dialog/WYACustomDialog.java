package com.wya.uikit.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wya.uikit.R;

import java.util.List;

/**
 * 创建日期：2018/11/20 16:36
 * 作者： Mao Chunjiang
 * 文件名称：WYACustomDialog
 * 类说明：自定义的弹出框
 */

public class WYACustomDialog extends Dialog {
    private Context context;
    private android.app.AlertDialog ad;
    private TextView titleView;
    private TextView messageView;
    private EditText edit_text;
    private TextView cancel;
    private TextView confirm;
    private View view;
    private LinearLayout ll_cancel;
    private LinearLayout ll_button;
    private boolean canceledOnTouch;
    private boolean cancelable;
    private View line_view;
    private RecyclerView recyclerView;

    public WYACustomDialog(Context context, boolean canceledOnTouch, boolean cancelable) {
        super(context, R.style.WYACustomDialog);
        this.context = context;
        this.canceledOnTouch = canceledOnTouch;
        this.cancelable = cancelable;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(context, R.layout.wya_custom_dialog, null);
        titleView = view.findViewById(R.id.title);
        messageView = view.findViewById(R.id.message);
        confirm = view.findViewById(R.id.confirm);
        cancel = view.findViewById(R.id.cancel);
        edit_text = view.findViewById(R.id.edit_text);
        line_view = view.findViewById(R.id.line_view);
        ll_cancel = view.findViewById(R.id.ll_cancel);
        ll_button = view.findViewById(R.id.ll_button);
        recyclerView = view.findViewById(R.id.recycler_view);
        setContentView(view);
        setClick();
        this.setCanceledOnTouchOutside(canceledOnTouch);
        setCancelable(cancelable);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (title != null && !title.equals("")) {
            titleView.setText(title);
            titleView.setVisibility(View.VISIBLE);
        } else {
            titleView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题字体颜色
     *
     * @param color
     * @param text_color
     */
    public void setTitleColor(int color, int text_color) {
        titleView.setBackgroundColor(context.getResources().getColor(color));
        titleView.setTextColor(context.getResources().getColor(text_color));
    }

    /**
     * 设置消息内容
     *
     * @param message
     */
    public void setMessage(String message) {
        if (message != null && !message.equals("")) {
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(Html.fromHtml(message));
        } else {
            messageView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置编辑框内容
     *
     * @param hint_text_str
     * @param edit_text_str
     */
    public void setEdit_text(String hint_text_str, String edit_text_str) {
        if (edit_text_str != null && !edit_text_str.equals("")) {
            edit_text.setVisibility(View.VISIBLE);
            edit_text.setHint(hint_text_str);
        } else {
            edit_text.setVisibility(View.GONE);
        }
    }


    /**
     * 设置取消按钮的文字内容
     *
     * @param cancelText
     */
    public void setCancelText(String cancelText) {
        if (cancelText != null && !cancelText.equals("")) {
            ll_cancel.setVisibility(View.VISIBLE);
            cancel.setText(cancelText);
        } else {
            ll_cancel.setVisibility(View.GONE);
        }
    }

    /**
     * 设置确定按钮的文字内容
     *
     * @param confirmText
     */
    public void setConfirmText(String confirmText) {
        confirm.setText(confirmText);
    }

    public void setNoButton() {
        line_view.setVisibility(View.GONE);
        ll_button.setVisibility(View.GONE);
    }

    /**
     * 设置确定按钮字体颜色
     *
     * @param text_color
     */
    @SuppressLint("ResourceType")
    public void setConfirmColor(int text_color) {
        confirm.setTextColor(context.getResources().getColorStateList(text_color));
    }

    /**
     * 设置取消按钮字体颜色
     *
     * @param text_color
     */
    @SuppressLint("ResourceType")
    public void setCancelColor(int text_color) {
        cancel.setTextColor(context.getResources().getColorStateList(text_color));
    }

    /**
     * 设置按钮点击监听事件
     */
    public void setClick() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });

    }


    /**
     * 获取体况文字内容
     * @return
     */
    public String getMessage() {
        return messageView.getText().toString();
    }

    /**
     * 获取编辑框内容
     * @return
     */
    public String getEditText() {
        return edit_text.getText().toString();
    }

    /**
     * 设置确定按钮被点击的接口
     */
    public interface ListOnclickListener {
        void onListClick(int position);
    }
    /**
     * 设置列表
     * @param data
     */
    private ListOnclickListener listOnclickListener;//列表点击监听
    private DialogListAdapter dialogListAdapter;

    public void setRecyclerView(List<String> data, Context context){
        if(data != null && data.size() > 0){
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            dialogListAdapter = new DialogListAdapter(context, R.layout.wya_custom_dialog_item_layout, data);
            recyclerView.setAdapter(dialogListAdapter);

            //RecyclerView条目点击事件
            dialogListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    listOnclickListener.onListClick(position);
                }
            });
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }

    }

    /**
     * 设置确定按钮被点击的接口
     */
    public interface onYesOnclickListener {
        void onYesClick();
    }

    /**
     * 设置取消按钮被点击的接口
     */
    public interface onNoOnclickListener {
        void onNoClick();
    }

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(onNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }
    /**
     * 设置列表点击事件
     *
     * @param listOnclickListener
     */
    public void setListOnclickListener(ListOnclickListener listOnclickListener) {
        this.listOnclickListener = listOnclickListener;
    }

}
