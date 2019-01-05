# Dialog

# 1、WYACustomDialog
## 功能说明
- 自定义提示框，包括编辑文本、提示、提示框位置以及自定义内容等

## 属性说明
属性 | 说明 | 类型 | 默认值
---|---|---|---
canceledOnTouch|点击空白是否隐藏dialog|boolean|false
cancelable|点击返回按钮是否隐藏dialog|boolean|false
layoutId|布局id|int|R.layout.wya_custom_dialog
title|默认标题|String|""
textColor|默认标题文字颜色|int|R.color.black
message|默认提示内容|String|""
hintEditText|默认编辑框提示内容|String|""
editText|默认编辑框内容|String|""
canEdit|编辑框是否显示|boolean|""
confirmShow|确定按钮是否显示|boolean|true
cancelText|取消按钮是否显示|boolean|true
confirmText|确定按钮文字|String|确定
cancelText|取消按钮文字|String|取消
confirmColor|确定按钮文字颜色|int|R.drawable.btn_c00bfff_click_color
cancelColor|取消按钮文字颜色|int|R.drawable.btn_c333333_click_color
gravity|弹出框位置|int|Gravity.CENTER
height|弹出框高度|int|WindowManager.LayoutParams.WRAP_CONTENT
width|弹出框宽度|int|WindowManager.LayoutParams.MATCH_PARENT




## 用法说明

- 项目中的使用
```
    wyaCustomDialog = new WYACustomDialog.Builder(this)
                    .gravity(Gravity.BOTTOM)
                    .cancelTouchout(boolean val)
                    .cancelTouchout(boolean val) 
                    .cancelable(boolean val) 
                    .title(String title) 
                    .titleTextColor(int text_color) 
                    .message(String message)
                    .canEdit(boolean canEdit)
                    .hintEditText(String hintEditTextStr)
                    .editText(String editTextStr)
                    .confirmShow(boolean confirmShow)
                    .cancelShow(boolean cancelShow) 
                    .confirmText(String confirmTextStr) 
                    .cancelText(String cancelTextStr) 
                    .confirmColor(int confirmColor)
                    .cancelColor(int cancelColor) 
                    .gravity(int gravity) 
                    .width(ScreenUtil.getScreenWidth(this) * 3/4)
                    .height(ScreenUtil.dip2px(DialogExampleActivity.this, 200))
                    .setLayoutId(R.layout.way_dialog_custom_list_layout, new CustomListener() {
                                        @Override
                                        public void customLayout(View v) {
                                        }
                                    })
                    .build();
            wyaCustomDialog.setNoOnclickListener(() -> {
                wyaCustomDialog.dismiss();
            });
            wyaCustomDialog.setYesOnclickListener(() -> wyaCustomDialog.dismiss());
            wyaCustomDialog.show();
```
- 方法

方法|说明
---|---
setTitle|设置标题，标题文字，为空或者空的字符串时不显示标题
setTitleColor|修改背景颜色和标题颜色
setMessage|设置提示内容
setCanEdit|设置是否显示编辑框提示内容
setEditHintText|设置编辑框提示内容
setEditText|设置编辑框内容
setCancelText|设置取消按钮文字内容
setCancelColor|设置取消按钮文字颜色
setConfirmText|设置确定文字内容
setConfirmColor|设置确定文字颜色
setButton |设置按钮显示
setNoClickListener|取消按钮的监听
setYesClickListener|确定按钮的监听


# 2、WYALoadingDialog
## 功能说明
- 加载提示框

## 属性说明
属性 | 说明 | 类型 | 默认值
---|---|---|---
canceledOnTouch|点击空白是否隐藏dialog|boolean|false
cancelable|点击返回按钮是否隐藏dialog|boolean|false

## 用法说明

- 项目中的使用
```
        wyaLoadingDialog = new WYALoadingDialog(this, canceledOnTouch, cancelable);
        wyaLoadingDialog.show();
```
