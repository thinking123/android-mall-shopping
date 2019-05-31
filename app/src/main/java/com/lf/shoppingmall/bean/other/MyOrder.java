package com.lf.shoppingmall.bean.other;

import com.lf.shoppingmall.bean.index.GoodsVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class MyOrder implements Serializable {
    private String id;
    private String addTime;
    private String orderId;
    private String goodList;
    private String trueName;
    private String telPhone;
    private String storeAddress;
    private String time;//   订单时间:
    private String okTime;//   完成，取消时显示
    private String yujiTime;//   待配货，待发货，待收货时显示
    private float shipPrice;
    private float totalPrice;
    private int orderStatus;
    private List<GoodsVo> goods;
//    time:
//    okTime:完成，取消时显示
//    yujiTime:
//  "trueName": "12",
//          "telPhone": "12",
//          "storeAddress": "北京东城区"
//      "shipPrice": 0,
//              "totalPrice": 20,

    @Override
    public String toString() {
        return "MyOrder{" +
                "id='" + id + '\'' +
                ", addTime='" + addTime + '\'' +
                ", orderId='" + orderId + '\'' +
                ", goodList='" + goodList + '\'' +
                ", trueName='" + trueName + '\'' +
                ", telPhone='" + telPhone + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", time='" + time + '\'' +
                ", okTime='" + okTime + '\'' +
                ", yujiTime='" + yujiTime + '\'' +
                ", shipPrice=" + shipPrice +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                ", goods=" + goods +
                '}';
    }

    public String getOkTime() {
        return okTime;
    }

    public void setOkTime(String okTime) {
        this.okTime = okTime;
    }

    public String getYujiTime() {
        return yujiTime;
    }

    public void setYujiTime(String yujiTime) {
        this.yujiTime = yujiTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public float getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(float shipPrice) {
        this.shipPrice = shipPrice;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodList() {
        return goodList;
    }

    public void setGoodList(String goodList) {
        this.goodList = goodList;
    }

    public List<GoodsVo> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsVo> goods) {
        this.goods = goods;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //      "id": 34,
//                            "addTime": "Aug 31, 2017 9:24:52 PM",
//                            "orderId": "20170831212452",
//                            "goodList": 1,
//                            "goods": [
//                        {
//                            "fullName": "甘蓝圆白菜",
//                                "image": "http://47.92.117.38:8088/upload/image/1.jpg",
//                                "goodsSpec": [],
//                            "totalWeight": 0
//                        }
}
