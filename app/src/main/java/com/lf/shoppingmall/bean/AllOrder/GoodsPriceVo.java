package com.lf.shoppingmall.bean.AllOrder;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/30.
 */

public class GoodsPriceVo extends Body implements Serializable {
//    {"isSucceed":"yes","carNum":1,"totalcurrentPrice":599,"totalcashPeldge":0,"totalPrice":599}
    private String carNum;
    private String totalcurrentPrice;
    private String totalcashPeldge;
    private float totalPrice;

    @Override
    public String toString() {
        return "GoodsPriceVo{" +
                "carNum='" + carNum + '\'' +
                ", totalcurrentPrice='" + totalcurrentPrice + '\'' +
                ", totalcashPeldge='" + totalcashPeldge + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                '}';
    }

    public String getTotalcurrentPrice() {
        return totalcurrentPrice;
    }

    public void setTotalcurrentPrice(String totalcurrentPrice) {
        this.totalcurrentPrice = totalcurrentPrice;
    }

    public String getTotalcashPeldge() {
        return totalcashPeldge;
    }

    public void setTotalcashPeldge(String totalcashPeldge) {
        this.totalcashPeldge = totalcashPeldge;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }
}
