# Dialog

# 1、WYACustomDialog
## 功能说明
- 自定义提示框，包括编辑文本、列表、提示等

## 属性说明
属性 | 说明 | 类型 | 默认值
---|---|---|---
canceledOnTouch|点击空白是否隐藏dialog|boolean|false
cancelable|点击返回按钮是否隐藏dialog|boolean|false

## 用法说明

- 项目中的使用

        wyaCustomDialog = new WYACustomDialog(this, canceledOnTouch, cancelable);
        wyaCustomDialog.show();

- 方法

方法|说明
---|---
setTitle(String title)|设置标题，标题文字，为空或者空的字符串时不显示标题
setTitleColor(int color, int text_color)|修改背景颜色和标题颜色
setMessage(String message)|设置提示内容
setEdit_text(String hint_text_str, String edit_text_str)|设置编辑框内容
setCancelText(String cancelText)|设置取消按钮文字内容
setCancelColor(int text_color)|设置取消按钮文字颜色
setConfirmText(String confirmText)|设置确定文字内容
setConfirmColor(int text_color)|设置确定文字颜色
setNoButton() |设置按钮不显示
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

        wyaLoadingDialog = new WYALoadingDialog(this, canceledOnTouch, cancelable);
                wyaLoadingDialog.show();

