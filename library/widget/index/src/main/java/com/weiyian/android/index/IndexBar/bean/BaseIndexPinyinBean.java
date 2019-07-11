package com.weiyian.android.index.IndexBar.bean;

/**
 * 介绍：索引类的汉语拼音的接口
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/04.
 *
 * @author :
 */

public abstract class BaseIndexPinyinBean extends BaseIndexBean {
    private String baseIndexPinyin;
    
    public String getBaseIndexPinyin() {
        return baseIndexPinyin;
    }
    
    public BaseIndexPinyinBean setBaseIndexPinyin(String baseIndexPinyin) {
        this.baseIndexPinyin = baseIndexPinyin;
        return this;
    }
    
    /**
     * @return : 是否需要被转化成拼音
     */
    public boolean isNeedToPinyin() {
        return true;
    }
    
    /**
     * @return :需要转化成拼音的目标字段
     */
    public abstract String getTarget();
    
}
