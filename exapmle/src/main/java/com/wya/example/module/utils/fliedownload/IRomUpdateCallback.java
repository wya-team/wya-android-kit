package com.wya.example.module.utils.fliedownload;

import java.util.List;

/**
 * @author : XuDonglin
 * @time : 2019/01/10
 * @description : 内存更新回调
 */
public interface IRomUpdateCallback {
    /**
     * 更新内存使用情况
     */
    void update();

    /**
     * editlist
     *
     * @param urls
     * @param all
     */
    void deleteItems(List<String> urls, int all);
    
}
