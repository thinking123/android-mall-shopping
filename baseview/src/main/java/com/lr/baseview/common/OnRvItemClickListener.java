package com.lr.baseview.common;

/**
 * recycleView的item点击事件
 *
 * @author liushubao
 *         Created by admin on 2017/3/22.
 */

public interface OnRvItemClickListener {

    /**
     * item的点击事件
     * @param position
     * @param type 支持自定义事件的多个类型
     */
    void onItemClick(int position, int type);
}
