# Dialog

# 1、WYACustomDialog
## 功能说明
- 自定义提示框，包括编辑文本、提示、提示框位置以及自定义内容等

## 属性说明
属性 | 说明 | 类型 | 默认值
---|---|---|---
canceledOnTouch|点击空白是否隐藏dialog|boolean|false
cancelable|点击返回按钮是否隐藏dialog|boolean|false
layoutRes|布局id|int|R.layout.wya_custom_dialog
title|默认标题|String|""
text_color|默认标题文字颜色|int|R.color.black
message|默认提示内容|String|""
hintEditTextStr|默认编辑框提示内容|String|""
editTextStr|默认编辑框内容|String|""
canEdit|编辑框是否显示|boolean|""
confirmShow|确定按钮是否显示|boolean|true
cancelTextStr|取消按钮是否显示|boolean|true
confirmTextStr|确定按钮文字|String|确定
cancelTextStr|取消按钮文字|String|取消
confirmColor|确定按钮文字颜色|int|R.drawable.btn_c00bfff_click_color
cancelColor|取消按钮文字颜色|int|R.drawable.btn_c333333_click_color
gravity|弹出框位置|int|Gravity.CENTER




## 用法说明

- 项目中的使用
```
    wyaCustomDialog = new WYACustomDialog.Builder(this)
                    .Gravity(Gravity.BOTTOM)
                    .cancelTouchout(boolean val)
                    .cancelTouchout(boolean val) 
                    .cancelable(boolean val) 
                    .title(String title) 
                    .titleTextColor(int text_color) 
                    .message(String message)
                    .canEdit(boolean canEdit)
                    .hintEditTextStr(String hintEditTextStr)
                    .editTextStr(String editTextStr)
                    .confirmShow(boolean confirmShow)
                    .cancelShow(boolean cancelShow) 
                    .confirmTextStr(String confirmTextStr) 
                    .cancelTextStr(String cancelTextStr) 
                    .confirmColor(int confirmColor)
                    .cancelColor(int cancelColor) 
                    .Gravity(int gravity) 
                    .setLayoutRes(R.layout.way_dialog_custom_list_layout, new CustomListener() {
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
setTitle(String title)|设置标题，标题文字，为空或者空的字符串时不显示标题
setTitleColor(int color, int text_color)|修改背景颜色和标题颜色
setMessage(String message)|设置提示内容
setCanEdit(boolean canEdit)|设置是否显示编辑框提示内容
setEditHintText(String hintEditTextStr)|设置编辑框提示内容
setEditText(String editTextStr)|设置编辑框内容
setCancelText(String cancelText)|设置取消按钮文字内容
setCancelColor(int text_color)|设置取消按钮文字颜色
setConfirmText(String confirmText)|设置确定文字内容
setConfirmColor(int text_color)|设置确定文字颜色
setButton(boolean confirmShow, boolean cancelShow) |设置按钮显示
setRecyclerView(List<String> data, Context context)|设置选择列表
setNoOnclickListener(onNoOnclickListener onNoOnclickListener)|取消按钮的监听
setYesOnclickListener(onYesOnclickListener onYesOnclickListener)|确定按钮的监听
setListOnclickListener(ListOnclickListener listOnclickListener)|列表点击监听器


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
