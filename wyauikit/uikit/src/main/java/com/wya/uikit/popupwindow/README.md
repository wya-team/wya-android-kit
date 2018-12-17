# PopupWindow
## 功能说明
- PopupWindow弹出框

## 属性说明
属性 | 说明 | 类型 | 默认值
---|---|---|---
view|显示该view的下方 |View|-
xoff|横向偏移量 |int|0
yoff|纵向偏移量 |int|0


## 用法说明

- 项目中的使用
```
     //自定义样式
     wyaPopupWindow = new WYAPopupWindow.Builder(PopupWindowExampleActivity.this).setLayoutRes(R.layout.activity_main, new CustomListener() {
                @Override
                public void customLayout(View v) {
    
                }
            }).build();
     wyaPopupWindow.show(view, -100, 0);
     
     //默认列表
     list = new ArrayList<>();
     list.add("我是第一个");
     list.add("第二个");
     list.add("第三个最帅");
     list.add("四");
     wyaPopupWindow = new WYAPopupWindow.Builder(PopupWindowExampleActivity.this).list(list).build();
     wyaPopupWindow.setPopupWindowListOnclickListener(position -> {
         wyaPopupWindow.dismiss();
         Toast.makeText(PopupWindowExampleActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
     });
     wyaPopupWindow.show(view, -100, 0); 
```

- 方法

方法|说明
---|---
show(View view, int xoff, int yoff)|设置popupwindow显示位置
setPopupWindowListOnclickListener(PopupWindowListOnclickListener popupWindowListOnclickListener)|设置popupwindow列表点击事件监听





