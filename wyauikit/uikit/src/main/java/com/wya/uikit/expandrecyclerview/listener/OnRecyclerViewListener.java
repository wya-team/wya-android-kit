package com.wya.uikit.expandrecyclerview.listener;

import android.view.View;

 /**
  * 创建日期：2018/12/12 18:05
  * 作者： Mao Chunjiang
  * 文件名称：OnRecyclerViewListener
  * 类说明：OnRecyclerViewListener
  */

public interface OnRecyclerViewListener {

    /**
     * 单击事件
     */
    interface OnItemClickListener {
        /** position 当前在列表中的position*/
        void onGroupItemClick(int position, int groupPosition, View view);

        void onChildItemClick(int position, int groupPosition, int childPosition, View view);
    }

    /**
     * 双击事件
     */
    interface OnItemLongClickListener {
        void onGroupItemLongClick(int position, int groupPosition, View view);

        void onChildItemLongClick(int position, int groupPosition, int childPosition, View view);
    }


}
