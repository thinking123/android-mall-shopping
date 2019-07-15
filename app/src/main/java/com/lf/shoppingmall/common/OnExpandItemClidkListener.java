package com.lf.shoppingmall.common;

/**
 * Created by Administrator on 2017/8/12.
 */

public interface OnExpandItemClidkListener {

    /**
     * item的点击事件
     * @param position
     * @param type 支持自定义事件的多个类型
     */
    void onItemClick(int position, int childPosition, int type);
}
