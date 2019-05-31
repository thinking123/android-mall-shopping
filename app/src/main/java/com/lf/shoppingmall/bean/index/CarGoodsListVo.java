package com.lf.shoppingmall.bean.index;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */

public class CarGoodsListVo extends Body implements Serializable {

    private List<GoodsVo> CarInfoList;
    private int carGoodsNum;
    private float totalPrice;

    @Override
    public String toString() {
        return "CarGoodsListVo{" +
                "CarInfoList=" + CarInfoList +
                ", carGoodsNum=" + carGoodsNum +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<GoodsVo> getCarInfoList() {
        return CarInfoList;
    }

    public void setCarInfoList(List<GoodsVo> carInfoList) {
        CarInfoList = carInfoList;
    }

    public int getCarGoodsNum() {
        return carGoodsNum;
    }

    public void setCarGoodsNum(int carGoodsNum) {
        this.carGoodsNum = carGoodsNum;
    }
}
