package com.lf.shoppingmall.bean.AllOrder;

import com.lf.shoppingmall.bean.Body;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.index.ProductionInfoVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class GoodsListInfoListVo extends Body implements Serializable {

    private List<GoodsVo> goodsListInfoList;
    private List<ProductionInfoVo> ProductionInfoList;

    @Override
    public String toString() {
        return "GoodsListInfoListVo{" +
                "goodsListInfoList=" + goodsListInfoList +
                ", ProductionInfoList=" + ProductionInfoList +
                '}';
    }

    public List<ProductionInfoVo> getProductionInfoList() {
        return ProductionInfoList;
    }

    public void setProductionInfoList(List<ProductionInfoVo> productionInfoList) {
        ProductionInfoList = productionInfoList;
    }

    public List<GoodsVo> getGoodsListInfoList() {
        return goodsListInfoList;
    }

    public void setGoodsListInfoList(List<GoodsVo> goodsListInfoList) {
        this.goodsListInfoList = goodsListInfoList;
    }
}
