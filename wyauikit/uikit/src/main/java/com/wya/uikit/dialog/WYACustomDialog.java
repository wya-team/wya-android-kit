package com.wya.uikit.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wya.uikit.R;

/**
 * 创建日期：2018/11/20 16:36
 * 作者： Mao Chunjiang
 * 文件名称：WYACustomDialog
 * 类说明：自定义的弹出框
 */

public class WYACustomDialog extends Dialog {
    private Context context;
    private boolean canceledOnTouch;
    private boolean cancelable;
    private View view;
    private int layoutRes;

    private int height;
    private int width;


    private CustomListener customListener;

    private String title;
    private int text_color;

    private String message;

    private String hintEditTextStr;
    private String editTextStr;
    private boolean canEdit;


    private boolean confirmShow;
    private boolean cancelShow;
    private String confirmTextStr;
    private String cancelTextStr;
    private int confirmColor;
    private int cancelColor;

    private TextView titleView;
    private TextView messageView;
    private EditText edit_text;
    private TextView cancel;
    private TextView confirm;

    private LinearLayout ll_button;

    private View line_view;
    private View line_horizontal;

    private int gravity;

    private float amount;


    public WYACustomDialog(Builder builder) {
        super(builder.context, R.style.WYACustomDialog);
        this.context = builder.context;
        this.canceledOnTouch = builder.canceledOnTouch;
        this.cancelable = builder.cancelable;
        this.customListener = builder.customListener;
        this.layoutRes = builder.layoutRes;
        this.height = builder.height;
        this.width = builder.width;
        this.title = builder.title;
        this.text_color = builder.text_color;
        this.message = builder.message;
        this.hintEditTextStr = builder.hintEditTextStr;
        this.editTextStr = builder.editTextStr;
        this.canEdit = builder.canEdit;

        this.confirmShow = builder.confirmShow;
        this.cancelShow = builder.cancelShow;
        this.confirmTextStr = builder.confirmTextStr;
        this.cancelTextStr = builder.cancelTextStr;
        this.confirmColor = builder.confirmColor;
        this.cancelColor = builder.cancelColor;
        this.gravity = builder.gravity;
        this.amount = builder.amount;

        initView(builder.context);
    }

    private void initView(Context context) {
        view = LayoutInflater.from(context).inflate(layoutRes, null);
        setCanceledOnTouchOutside(canceledOnTouch);
        setCancelable(cancelable);
        if (customListener == null) {
            //标题
            titleView = view.findViewById(R.id.title);
            setTitle(title);
            setTitleTextColor(text_color);

            //提示内容
            messageView = view.findViewById(R.id.message);
            if (messageView != null) {
                setMessage(message);
            }

            //编辑框
            edit_text = view.findViewById(R.id.edit_text);
            if (edit_text != null) {
                setEditHintText(hintEditTextStr);
                setEditText(editTextStr);
                setCanEdit(canEdit);
            }

            line_view = view.findViewById(R.id.line_view);
            line_horizontal = view.findViewById(R.id.line_horizontal);
            confirm = view.findViewById(R.id.confirm);
            cancel = view.findViewById(R.id.cancel);
            ll_button = view.findViewById(R.id.ll_button);
            if (confirm != null) {
                setConfirmColor(confirmColor);
                setConfirmText(confirmTextStr);
            }
            if (cancel != null) {
                setCancelColor(cancelColor);
                setCancelText(cancelTextStr);
            }
            setButton(confirmShow, cancelShow);
            setClick();
        } else {
            customListener.customLayout(view);
        }
        setContentView(view);

        Window window = this.getWindow();
        if(gravity == Gravity.BOTTOM){
            window.setWindowAnimations(R.style.dialog_bottom_anim);
        }
        window.setGravity(gravity);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = height;
        window.setAttributes(params);
        if(amount != -1){
            getWindow().setDimAmount(amount);
        }
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
     * @param text_color
     */
    public void setTitleTextColor(int text_color) {
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
     * 获取体况文字内容
     *
     * @return
     */
    public String getMessage() {
        return messageView.getText().toString();
    }


    /**
     * 设置是否显示编辑框提示内容
     *
     * @param canEdit
     */
    public void setCanEdit(boolean canEdit) {
        if (canEdit) {
            edit_text.setVisibility(View.VISIBLE);
        } else {
            edit_text.setVisibility(View.GONE);
        }
    }

    /**
     * 设置编辑框提示内容
     *
     * @param hintEditTextStr
     */
    public void setEditHintText(String hintEditTextStr) {
        edit_text.setHint(hintEditTextStr);
    }

    /**
     * 设置编辑框内容
     *
     * @param editTextStr
     */
    public void setEditText(String editTextStr) {
        edit_text.setText(editTextStr);
    }

    /**
     * 获取编辑框内容
     *
     * @return
     */
    public String getEditText() {
        return edit_text.getText().toString();
    }


    /**
     * 是否显示确定按钮
     *
     * @param confirmShow
     */
    private void setButton(boolean confirmShow, boolean cancelShow) {
        if (confirmShow && cancelShow) {//都显示
            cancel.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
            line_horizontal.setVisibility(View.VISIBLE);
        } else if (cancelShow) {//显示取消按钮
            cancel.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.GONE);
            line_horizontal.setVisibility(View.GONE);
        } else if (confirmShow) {//显示确定按钮
            cancel.setVisibility(View.GONE);
            confirm.setVisibility(View.VISIBLE);
            line_horizontal.setVisibility(View.GONE);
        } else {//都不显示
            line_view.setVisibility(View.GONE);
            ll_button.setVisibility(View.GONE);
        }
    }


    /**
     * 设置取消按钮的文字内容
     *
     * @param cancelText
     */
    public void setCancelText(String cancelText) {
        cancel.setText(cancelText);
    }

    /**
     * 设置确定按钮的文字内容
     *
     * @param confirmText
     */
    public void setConfirmText(String confirmText) {
        confirm.setText(confirmText);
    }


    /**
     * 设置确定按钮字体颜色
     *
     * @param confirmColor
     */
    @SuppressLint("ResourceType")
    public void setConfirmColor(int confirmColor) {
        confirm.setTextColor(context.getResources().getColorStateList(confirmColor));
    }

    /**
     * 设置取消按钮字体颜色
     *
     * @param cancelColor
     */
    @SuppressLint("ResourceType")
    public void setCancelColor(int cancelColor) {
        cancel.setTextColor(context.getResources().getColorStateList(cancelColor));
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

    public static final class Builder {
        private Context context;
        private int layoutRes = R.layout.wya_custom_dialog;
        private CustomListener customListener;
        private boolean canceledOnTouch = true;
        private boolean cancelable = false;

        private String title = "";//默认标题
        private int text_color = R.color.black;//默认标题文字颜色

        private String message = "我是提示内容";//默认提示内容


        private String hintEditTextStr = "";
        private String editTextStr = "";
        private boolean canEdit = false;


        private boolean confirmShow = true;
        private boolean cancelShow = true;
        private String confirmTextStr = "确定";
        private String cancelTextStr = "取消";
        private int confirmColor = R.drawable.btn_c00bfff_click_color;
        private int cancelColor = R.drawable.btn_c333333_click_color;

        private int gravity = Gravity.CENTER;

        private int height = WindowManager.LayoutParams.WRAP_CONTENT;
        private int width = WindowManager.LayoutParams.MATCH_PARENT;

        private float amount = -1;//默认不设置,边框透明


        public Builder(Context context) {
            this.context = context;
        }


        public Builder cancelTouchout(boolean val) {
            canceledOnTouch = val;
            return this;
        }

        public Builder cancelable(boolean val) {
            cancelable = val;
            return this;
        }

        public Builder setLayoutRes(int res, CustomListener listener) {
            this.layoutRes = res;
            this.customListener = listener;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder titleTextColor(int text_color) {
            this.text_color = text_color;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder canEdit(boolean canEdit) {
            this.canEdit = canEdit;
            return this;
        }

        public Builder hintEditTextStr(String hintEditTextStr) {
            this.hintEditTextStr = hintEditTextStr;
            return this;
        }

        public Builder editTextStr(String editTextStr) {
            this.editTextStr = editTextStr;
            return this;
        }


        public Builder confirmShow(boolean confirmShow) {
            this.confirmShow = confirmShow;
            return this;
        }

        public Builder cancelShow(boolean cancelShow) {
            this.cancelShow = cancelShow;
            return this;
        }

        public Builder confirmTextStr(String confirmTextStr) {
            this.confirmTextStr = confirmTextStr;
            return this;
        }

        public Builder cancelTextStr(String cancelTextStr) {
            this.cancelTextStr = cancelTextStr;
            return this;
        }

        public Builder confirmColor(int confirmColor) {
            this.confirmColor = confirmColor;
            return this;
        }

        public Builder cancelColor(int cancelColor) {
            this.cancelColor = cancelColor;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }


        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder amount(float amount) {
            this.amount = amount;
            return this;
        }




        public WYACustomDialog build() {
            return new WYACustomDialog(this);
        }
    }

}