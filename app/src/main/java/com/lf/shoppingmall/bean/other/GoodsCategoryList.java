package com.lf.shoppingmall.bean.other;

import com.lf.shoppingmall.bean.AllOrder.GoodsCateVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */

public class GoodsCategoryList implements Serializable {
    private List<GoodsCateVo> GoodsCategory;

    public List<GoodsCateVo> getGoodsCategory() {
        return GoodsCategory;
    }

    public void setGoodsCategory(List<GoodsCateVo> goodsCategory) {
        GoodsCategory = goodsCategory;
    }

    @Override
    public String toString() {
        return "GoodsCategoryList{" +
                "GoodsCategory=" + GoodsCategory +
                '}';
    }
}
