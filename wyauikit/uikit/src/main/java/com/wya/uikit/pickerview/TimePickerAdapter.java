package com.wya.uikit.pickerview;

import com.wya.uikit.pickerview.wheelview.adapter.WheelAdapter;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/07
 * desc   :
 * version: 1.0
 */
public class TimePickerAdapter implements WheelAdapter<String> {

    private int start, end, space;
    private String type;

    public TimePickerAdapter(int start, int end, int space, String type) {
        this.start = start;
        this.end = end;
        this.space = space;
        this.type = type;
    }

    @Override
    public int getItemsCount() {
        return (end - start + 1) / space;
    }

    @Override
    public String getItem(int index) {
        int value = start + index * space;
        return value < 10 ? "0"+value + type : value + type;
    }

    @Override
    public int indexOf(String item) {
        String items = item.replaceAll(type, "");
        return (Integer.parseInt(items) - start) / space;
    }

}
