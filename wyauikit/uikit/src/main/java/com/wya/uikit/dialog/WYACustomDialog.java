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
 * @date: 2018/11/20 16:36
 * @author: Chunjiang Mao
 * @classname: WYACustomDialog
 * @describe: 自定义的弹出框
 */

public class WYACustomDialog extends Dialog {
    private Context context;
    private boolean canceledOnTouch;
    private boolean cancelable;
    private View view;
    private int layoutId;

    private int height;
    private int width;

    private CustomListener customListener;

    private String title;
    private int textColor;

    private String message;
    private int messageTextColor;

    private String hintEditText;
    private String editText;
    private int editTextColor;
    private boolean canEdit;

    private boolean confirmShow;
    private boolean cancelShow;
    private String confirmText;
    private String cancelText;
    private int confirmColor;
    private int cancelColor;

    private TextView titleView;
    private TextView messageView;
    private EditText mEditText;
    private TextView cancel;
    private TextView confirm;

    private LinearLayout llButton;

    private View lineView;
    private View lineHorizontal;

    private int gravity;

    private float amount;
    /**
     * 取消按钮被点击了的监听器
     */
    private NoClickListener noClickListener;
    /**
     * 确定按钮被点击了的监听器
     */
    private YesClickListener yesClickListener;

    public WYACustomDialog(Builder builder) {
        super(builder.context, R.style.WYACustomDialog);
        this.context = builder.context;
        this.canceledOnTouch = builder.canceledOnTouch;
        this.cancelable = builder.cancelable;
        this.customListener = builder.customListener;
        this.layoutId = builder.layoutId;
        this.height = builder.height;
        this.width = builder.width;
        this.title = builder.title;
        this.textColor = builder.textColor;
        this.message = builder.message;
        this.messageTextColor = builder.messageTextColor;

        this.hintEditText = builder.hintEditText;
        this.editText = builder.editText;
        this.canEdit = builder.canEdit;
        this.editTextColor = builder.editTextColor;

        this.confirmShow = builder.confirmShow;
        this.cancelShow = builder.cancelShow;
        this.confirmText = builder.confirmText;
        this.cancelText = builder.cancelText;
        this.confirmColor = builder.confirmColor;
        this.cancelColor = builder.cancelColor;
        this.gravity = builder.gravity;
        this.amount = builder.amount;

        initView(builder.context);
    }

    private void initView(Context context) {
        view = LayoutInflater.from(context).inflate(layoutId, null);
        setCanceledOnTouchOutside(canceledOnTouch);
        setCancelable(cancelable);
        if (customListener == null) {
            //标题
            titleView = view.findViewById(R.id.title);
            setTitle(title);
            setTitleTextColor(textColor);

            //提示内容
            messageView = view.findViewById(R.id.message);
            if (messageView != null) {
                setMessage(message);
                setMessageTextColor(messageTextColor);
            }

            //编辑框
            mEditText = view.findViewById(R.id.edit_text);
            if (editText != null) {
                setEditHintText(hintEditText);
                setEditText(editText);
                setCanEdit(canEdit);
                setEditTextColor(editTextColor);
            }

            lineView = view.findViewById(R.id.line_view);
            lineHorizontal = view.findViewById(R.id.line_horizontal);
            confirm = view.findViewById(R.id.confirm);
            cancel = view.findViewById(R.id.cancel);
            llButton = view.findViewById(R.id.ll_button);
            if (confirm != null) {
                setConfirmColor(confirmColor);
                setConfirmText(confirmText);
            }
            if (cancel != null) {
                setCancelColor(cancelColor);
                setCancelText(cancelText);
            }
            setButton(confirmShow, cancelShow);
            setClick();
        } else {
            customListener.customLayout(view);
        }
        setContentView(view);

        Window window = this.getWindow();
        if (gravity == Gravity.BOTTOM) {
            window.setWindowAnimations(R.style.dialog_bottom_anim);
        }
        window.setGravity(gravity);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = height;
        window.setAttributes(params);
        if (amount != -1) {
            getWindow().setDimAmount(amount);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (title != null && !"".equals(title)) {
            titleView.setText(title);
            titleView.setVisibility(View.VISIBLE);
        } else {
            titleView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题字体颜色
     *
     * @param textColor
     */
    public void setTitleTextColor(int textColor) {
        titleView.setTextColor(context.getResources().getColor(textColor));
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
     * 设置消息内容
     *
     * @param message
     */
    public void setMessage(String message) {
        if (message != null && !"".equals(message)) {
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(Html.fromHtml(message));
        } else {
            messageView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置编辑文本字体颜色
     *
     * @param messageTextColor
     */
    private void setMessageTextColor(int messageTextColor) {
        messageView.setTextColor(context.getResources().getColor(messageTextColor));
    }

    /**
     * 设置是否显示编辑框提示内容
     *
     * @param canEdit
     */
    public void setCanEdit(boolean canEdit) {
        if (canEdit) {
            mEditText.setVisibility(View.VISIBLE);
        } else {
            mEditText.setVisibility(View.GONE);
        }
    }

    /**
     * 设置编辑框提示内容
     *
     * @param hintEditText
     */
    public void setEditHintText(String hintEditText) {
        mEditText.setHint(hintEditText);
    }

    /**
     * 获取编辑框内容
     *
     * @return
     */
    public String getEditText() {
        return mEditText.getText().toString();
    }

    /**
     * 设置编辑框内容
     *
     * @param editText
     */
    public void setEditText(String editText) {
        mEditText.setText(editText);
    }

    /**
     * 设置编辑文本字体颜色
     *
     * @param editTextColor
     */
    private void setEditTextColor(int editTextColor) {
        mEditText.setTextColor(context.getResources().getColor(editTextColor));
    }

    /**
     * 是否显示确定按钮
     *
     * @param confirmShow
     */
    private void setButton(boolean confirmShow, boolean cancelShow) {
        if (confirmShow && cancelShow) {
            //都显示
            cancel.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
            lineHorizontal.setVisibility(View.VISIBLE);
        } else if (cancelShow) {
            //显示取消按钮
            cancel.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.GONE);
            lineHorizontal.setVisibility(View.GONE);
        } else if (confirmShow) {
            //显示确定按钮
            cancel.setVisibility(View.GONE);
            confirm.setVisibility(View.VISIBLE);
            lineHorizontal.setVisibility(View.GONE);
        } else {//都不显示
            lineView.setVisibility(View.GONE);
            llButton.setVisibility(View.GONE);
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
                if (noClickListener != null) {
                    noClickListener.onNoClick();
                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yesClickListener != null) {
                    yesClickListener.onYesClick();
                }
            }
        });

    }

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param noClickListener
     */
    public void setNoClickListener(NoClickListener noClickListener) {
        this.noClickListener = noClickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param yesClickListener
     */
    public void setYesClickListener(YesClickListener yesClickListener) {
        this.yesClickListener = yesClickListener;
    }

    public interface YesClickListener {
        /**
         * 设置确定按钮被点击的接口
         */
        void onYesClick();
    }

    public interface NoClickListener {
        /**
         * 设置取消按钮被点击的接口
         */
        void onNoClick();
    }

    public static final class Builder {
        private Context context;
        private int layoutId = R.layout.wya_custom_dialog;
        private CustomListener customListener;
        private boolean canceledOnTouch = true;
        private boolean cancelable = false;
        /**
         * 默认标题
         */
        private String title = "";
        /**
         * 默认标题文字颜色
         */
        private int textColor = R.color.black;
        /**
         * 默认提示内容
         */
        private String message = "我是提示内容";
        private int messageTextColor = R.color.black;

        private String hintEditText = "";
        private String editText = "";
        private boolean canEdit = false;
        private int editTextColor = R.color.black;

        private boolean confirmShow = true;
        private boolean cancelShow = true;
        private String confirmText = "确定";
        private String cancelText = "取消";
        private int confirmColor = R.drawable.btn_c00bfff_click_color;
        private int cancelColor = R.drawable.btn_c333333_click_color;

        private int gravity = Gravity.CENTER;

        private int height = WindowManager.LayoutParams.WRAP_CONTENT;
        private int width = WindowManager.LayoutParams.MATCH_PARENT;
        /**
         * 默认不设置,边框透明
         */
        private float amount = -1;

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

        public Builder setLayoutId(int res, CustomListener listener) {
            this.layoutId = res;
            this.customListener = listener;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder titleTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder editTextColor(int editTextColor) {
            this.editTextColor = editTextColor;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder messageTxetColor(int messageTextColor) {
            this.messageTextColor = messageTextColor;
            return this;
        }

        public Builder canEdit(boolean canEdit) {
            this.canEdit = canEdit;
            return this;
        }

        public Builder hintEditText(String hintEditText) {
            this.hintEditText = hintEditText;
            return this;
        }

        public Builder editText(String editText) {
            this.editText = editText;
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

        public Builder confirmText(String confirmText) {
            this.confirmText = confirmText;
            return this;
        }

        public Builder cancelText(String cancelText) {
            this.cancelText = cancelText;
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