package com.lf.shoppingmall.bean.AllOrder;

/**
 * Created by Administrator on 2017/9/16.
 */

public class PayInfoBean {
    private String code;
    private String orderInfo;

    @Override
    public String toString() {
        return "PayInfoBean{" +
                "code='" + code + '\'' +
                ", orderInfo='" + orderInfo + '\'' +
                '}';
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
