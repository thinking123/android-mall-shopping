package com.lf.shoppingmall.bean.other;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class MyOrderList extends Body implements Serializable {

    private List<MyOrder> orderList;

    @Override
    public String toString() {
        return "MyOrderList{" +
                "orderList=" + orderList +
                '}';
    }

    public List<MyOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<MyOrder> orderList) {
        this.orderList = orderList;
    }
}
