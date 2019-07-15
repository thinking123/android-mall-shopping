package com.lf.shoppingmall.bean.AllOrder;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class GoodsCategoryListVo extends Body implements Serializable {

    private List<GoodsCategoryVo> GoodsCategoryList;

    @Override
    public String toString() {
        return "GoodsCategoryListVo{" +
                "GoodsCategoryList=" + GoodsCategoryList +
                '}';
    }

    public List<GoodsCategoryVo> getGoodsCategoryList() {
        return GoodsCategoryList;
    }

    public void setGoodsCategoryList(List<GoodsCategoryVo> goodsCategoryList) {
        GoodsCategoryList = goodsCategoryList;
    }
}
