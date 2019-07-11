package com.weiyian.android.index.suspension;

/**
 * 介绍：分类悬停的接口
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/7.
 */

public interface ISuspensionInterface {
    
    /**
     * 是否展示悬停标题
     *
     * @return :
     */
    boolean isShowSuspension();
    
    /**
     * 获取悬停tag
     *
     * @return :
     */
    String getSuspensionTag();
    
}
