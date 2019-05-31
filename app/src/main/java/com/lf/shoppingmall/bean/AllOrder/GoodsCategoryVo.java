package com.lf.shoppingmall.bean.AllOrder;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class GoodsCategoryVo implements Serializable {

    private String categoryId;
    private String categoryName;
    private String parentId;
    private String categoryOrder;
    private String categoryState;
    private String addTime;
    private List<GoodsCateVo> goodsCategories;

    @Override
    public String toString() {
        return "GoodsCategoryVo{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", categoryOrder='" + categoryOrder + '\'' +
                ", categoryState='" + categoryState + '\'' +
                ", addTime='" + addTime + '\'' +
                ", goodsCategories=" + goodsCategories +
                '}';
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(String categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    public String getCategoryState() {
        return categoryState;
    }

    public void setCategoryState(String categoryState) {
        this.categoryState = categoryState;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public List<GoodsCateVo> getGoodsCategories() {
        return goodsCategories;
    }

    public void setGoodsCategories(List<GoodsCateVo> goodsCategories) {
        this.goodsCategories = goodsCategories;
    }

    ///       "categoryId":1,
//        "categoryName":"新鲜蔬菜",
//        "parentId":0,
//        "categoryOrder":1,
//        "categoryState":1,
//        "addTime":"Jul 31, 2017 5:44:04 PM",
//        "goodsCategories":[
//    {
//
//    },
//    {
//        "categoryId":5,
//            "categoryName":"菌类",
//            "parentId":1,
//            "categoryOrder":6,
//            "categoryState":1,
//            "addTime":"Aug 4, 2017 4:01:44 PM"
//    },
//    {
//        "categoryId":6,
//            "categoryName":"根茎类",
//            "parentId":1,
//            "categoryOrder":2,
//            "categoryState":1,
//            "addTime":"Aug 4, 2017 4:49:29 PM"
//    }
//                ]
//},
}
