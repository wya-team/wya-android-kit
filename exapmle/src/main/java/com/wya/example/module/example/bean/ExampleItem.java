package com.wya.example.module.example.bean;

import java.util.List;

/**
 * 创建日期：2018/12/17 10:47
 * 作者： Mao Chunjiang
 * 文件名称： Item
 * 类说明：
 */

public class ExampleItem {
    private String title;
    private List<String> child;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getChild() {
        return child;
    }

    public void setChild(List<String> child) {
        this.child = child;
    }
}
