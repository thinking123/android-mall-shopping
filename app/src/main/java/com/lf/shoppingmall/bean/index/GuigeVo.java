package com.lf.shoppingmall.bean.index;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 规格
 * Created by Administrator on 2017/8/10.
 */

public class GuigeVo implements Serializable/*, Parcelable */{
//                    "id":56,
//                    "spec":"1*袋（2斤）",
//                    "totalWeight":2,
//                    "oldPrice":4.78,
//                    "currentPrice":4.78,
//                    "avgPrice":2.39,
//                    "discount":0,
//                    "carGoodNum":0
//               "cashPledge": 0,
//               "goodsAmount": 1

    private String id;
    private String spec;
    private String totalWeight;
    private String oldPrice;
    private String currentPrice;
    private String avgPrice;
    private String discount;
    private String showState;
    private int delStatus;//自定义删除状态
    private int carGoodNum;
    private int carGoodState;
    //订单字段
    private int cashPledge;
    private int goodsAmount;

    public GuigeVo(){
    }

    @Override
    public String toString() {
        return "GuigeVo{" +
                "id='" + id + '\'' +
                ", spec='" + spec + '\'' +
                ", totalWeight='" + totalWeight + '\'' +
                ", oldPrice='" + oldPrice + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", avgPrice='" + avgPrice + '\'' +
                ", discount='" + discount + '\'' +
                ", showState='" + showState + '\'' +
                ", delStatus=" + delStatus +
                ", carGoodNum=" + carGoodNum +
                ", carGoodState=" + carGoodState +
                ", cashPledge=" + cashPledge +
                ", goodsAmount=" + goodsAmount +
                '}';
    }

    public String getShowState() {
        return showState;
    }

    public void setShowState(String showState) {
        this.showState = showState;
    }

    public int getCashPledge() {
        return cashPledge;
    }

    public void setCashPledge(int cashPledge) {
        this.cashPledge = cashPledge;
    }

    public int getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(int goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public int getCarGoodState() {
        return carGoodState;
    }

    public void setCarGoodState(int carGoodState) {
        this.carGoodState = carGoodState;
    }

    public int getStatus() {
        return carGoodState;
    }

    public void setStatus(int status) {
        this.carGoodState = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getCarGoodNum() {
        return carGoodNum;
    }

    public void setCarGoodNum(int carGoodNum) {
        this.carGoodNum = carGoodNum;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
}
