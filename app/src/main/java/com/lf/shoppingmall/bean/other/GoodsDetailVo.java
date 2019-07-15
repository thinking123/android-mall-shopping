package com.lf.shoppingmall.bean.other;

import com.lf.shoppingmall.bean.Body;
import com.lf.shoppingmall.bean.index.GoodsVo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/25.
 */

public class GoodsDetailVo extends Body implements Serializable {

    private GoodsVo goods;

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "GoodsDetailVo{" +
                "goods=" + goods +
                '}';
    }
}
