package com.wya.example.module.example.bean;

import java.util.List;

/**
 * @date: 2018/12/17 10:47
 * @author: Chunjiang Mao
 * @classname: Item
 * @describe:
 */

public class ExampleItem {
    private String title;
    private List<String> child;
    private boolean isOpen;
    
    public boolean isOpen() {
        return isOpen;
    }
    
    public void setOpen(boolean open) {
        isOpen = open;
    }
    
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
