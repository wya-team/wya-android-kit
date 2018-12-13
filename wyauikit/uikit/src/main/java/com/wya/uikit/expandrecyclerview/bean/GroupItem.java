package com.wya.uikit.expandrecyclerview.bean;

import java.util.List;

 /**
  * 创建日期：2018/12/12 18:05
  * 作者： Mao Chunjiang
  * 文件名称：GroupItem
  * 类说明：
  * T 为group数据对象
  * S 为child数据对象
  */


public class GroupItem<T,S> extends BaseItem{

    /**head data*/
    private T groupData;

    /** childDatas*/
    private List<S> childDatas;

    /** 是否展开,  默认展开*/
    private boolean isExpand = true;


    /** 返回是否是父节点*/
    @Override
    public boolean isParent() {
        return true;
    }

    public boolean isExpand(){
        return isExpand;
    }

    public void onExpand() {
        isExpand = !isExpand;
    }

    public GroupItem(T groupData, List<S> childDatas, boolean isExpand) {
        this.groupData = groupData;
        this.childDatas = childDatas;
        this.isExpand = isExpand;
    }

    public boolean hasChilds(){
        if(getChildDatas() == null || getChildDatas().isEmpty() ){
            return false;
        }
        return true;
    }

    public List<S> getChildDatas() {
        return childDatas;
    }

    public void setChildDatas(List<S> childDatas) {
        this.childDatas = childDatas;
    }

    public void removeChild(int childPosition){

    }

    public T getGroupData() {
        return groupData;
    }
}
