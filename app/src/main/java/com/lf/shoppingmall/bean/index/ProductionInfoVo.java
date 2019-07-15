package com.lf.shoppingmall.bean.index;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * 商品列表
 * Created by Administrator on 2017/8/10.
 */

public class ProductionInfoVo implements Serializable, Parcelable {
//       "goodsBaseType": "新鲜蔬菜",
//               "goodsList": [

    private String goodsBaseType;
    private List<GoodsVo> goodsList;

    protected ProductionInfoVo(Parcel in) {
        goodsBaseType = in.readString();
        goodsList = in.readArrayList(ProductionInfoVo.class.getClassLoader());
    }

    public static final Creator<ProductionInfoVo> CREATOR = new Creator<ProductionInfoVo>() {
        @Override
        public ProductionInfoVo createFromParcel(Parcel in) {
            return new ProductionInfoVo(in);
        }

        @Override
        public ProductionInfoVo[] newArray(int size) {
            return new ProductionInfoVo[size];
        }
    };

    @Override
    public String toString() {
        return "ProductionInfoVo{" +
                "goodsBaseType='" + goodsBaseType + '\'' +
                ", goodsList=" + goodsList +
                '}';
    }

    public String getGoodsBaseType() {
        return goodsBaseType;
    }

    public void setGoodsBaseType(String goodsBaseType) {
        this.goodsBaseType = goodsBaseType;
    }

    public List<GoodsVo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsVo> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goodsBaseType);
        dest.writeList(goodsList);
    }
}
