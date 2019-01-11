package com.wya.example.module.utils.fliedownload;

/**
 * @author : XuDonglin
 * @time : 2019/01/10
 * @describe :
 */
public interface IManagerInterface {
    /**
     * 显示编辑
     *
     * @param isShow
     */
    void showEdit(boolean isShow);

    /**
     * 选中所有
     *
     * @param state 0全选 1取消全选
     */
    void selectAll(int state);
}
