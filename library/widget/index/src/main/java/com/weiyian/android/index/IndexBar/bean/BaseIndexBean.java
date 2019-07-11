package com.weiyian.android.index.IndexBar.bean;

import com.weiyian.android.index.suspension.ISuspensionInterface;

/**
 * @author :
 */

public abstract class BaseIndexBean implements ISuspensionInterface {
    private String baseIndexTag;
    
    public String getBaseIndexTag() {
        return baseIndexTag;
    }
    
    public BaseIndexBean setBaseIndexTag(String baseIndexTag) {
        this.baseIndexTag = baseIndexTag;
        return this;
    }
    
    @Override
    public String getSuspensionTag() {
        return baseIndexTag;
    }
    
    @Override
    public boolean isShowSuspension() {
        return true;
    }
}
