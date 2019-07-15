package com.lf.shoppingmall.bean.other;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/4.
 */

public class MyOrderDetailBean extends Body implements Serializable {
    private MyOrder orderDetails;

    @Override
    public String toString() {
        return "MyOrderDetailBean{" +
                "orderDetails=" + orderDetails +
                '}';
    }

    public MyOrder getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(MyOrder orderDetails) {
        this.orderDetails = orderDetails;
    }
}
