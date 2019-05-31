package com.lf.shoppingmall.bean.index;

import android.os.Parcel;
import android.os.Parcelable;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品vo
 * Created by Administrator on 2017/8/10.
 */

public class GoodsVo extends Body implements Serializable/*, Parcelable */ {

//     "id":1,
//             "avgPrice":"19.90,18.90,19.50",
//             "spec":"1*包（5斤）,1*袋（5包*5斤）,1*袋（2包*5斤）",
//             "oldPrice":"20.00,95.00,39.00",
//             "currentPrice":"20.00,95.00,39.00",
//             "totalWeight":"5,25,10",
//             "discount":"1,1,1",
//             "categoryIdTwo":2,
//             "categoryIdOne":1,
//             "goodsBaseName":"圆白菜",
//             "categoryName":"新鲜蔬菜",
//             "specId":"1,3,2",
//             "cityCode":141000,
//             "goodsAlias":"大头菜",
//             "fullName":"甘蓝圆白菜",
//             "createDate":"Aug 1, 2017 2:58:43 PM",
//             "introduction":"新鲜/去跟，根部上方白色",
//             "isGift":0,
//             "goodsFresh":0,
//             "feature":"松散",
//             "isTop":0,
//             "productCategory":2,
//             "modifyDate":"Aug 1, 2017 2:59:28 PM",
//             "seoKeywords":"圆白菜",
//             "baseSpec":"袋",
//             "goodsBaseType":"1",
//             "goodsState":"1",
//             "guige":[
//    {
//        "id":1,
//            "spec":"1*包（5斤）",
//            "totalWeight":5,
//            "oldPrice":20,
//            "currentPrice":20,
//            "avgPrice":19.9,
//            "discount":1,
//            "carGoodNum":10
//    },
//    {
//        "id":3,
//            "spec":"1*袋（5包*5斤）",
//            "totalWeight":25,
//            "oldPrice":95,
//            "currentPrice":95,
//            "avgPrice":18.9,
//            "discount":1,
//            "carGoodNum":0
//    },
//    {
//
//    }
//                ],
//                        "goodsNum":"10,0,5"
//},

//     "spec": "1*袋（2包*5斤）,1*包（5斤）",
//             "oldPrice": "39.00,20.00",
//             "goodsId": 1,
//             "carId": 147,
//             "baseSpec": "袋",
//             "carGoodState": "1,1",
//             "state": "1",
//             "image": "http:\/\/47.92.117.38:8088\/upload\/image\/1.jpg",
//             "currentPrice": "39.00,20.00",
//             "discount": "1,1",
//             "avgPrice": "19.50,19.90",
//             "goodSpecId": 2,
//             "totalWeight": "10,5",
//             "specId": "2,1",
//             "carGoodNum": "8,17",
//             "feature": "松散",
//             "fullName": "甘蓝圆白菜",

    private String listId;
    private List<String> imageBanner;//详情图片集合
    private String detailsImage;
    private String brand;
    private String place;
    private String producer;
    private String carId;
    private String goodsId;

    private String id;
    private String image;
    private String goodsState;
    private String oldPrice;
    private String avgPrice;
    private String spec;
    private String currentPrice;
    private String totalWeight;
    private String discount;
    private String categoryIdTwo;
    private String categoryIdOne;
    private String goodsBaseName;
    private String categoryName;
    private String specId;
    private String cityCode;
    private String goodsAlias;
    private String fullName;
    private String createDate;
    private String introduction;
    private String isGift;
    private String goodsFresh;
    private String feature;
    private String isTop;
    private String productCategory;
    private String modifyDate;
    private String seoKeywords;
    private String baseSpec;
    private String goodsBaseType;
    private ArrayList<GuigeVo> guige;
    private ArrayList<GuigeVo> goodsSpec;
    private String goodsNum;
    private String totalCount;

    //    public int total;
//    public int childTotal;
    private boolean isGuige;//false合并 true展开
    private int carStatus;//购物车是否选中
    private int delStatus;//删除状态

    public ArrayList<GuigeVo> getGoodsSpec() {
        return goodsSpec;
    }

    public List<String> getImageBanner() {
        return imageBanner;
    }

    public void setImageBanner(List<String> imageBanner) {
        this.imageBanner = imageBanner;
    }

    public String getDetailsImage() {
        return detailsImage;
    }

    public void setDetailsImage(String detailsImage) {
        this.detailsImage = detailsImage;
    }

    public void setGoodsSpec(ArrayList<GuigeVo> goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public GoodsVo() {
    }

    public int getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(int carStatus) {
        this.carStatus = carStatus;
    }

    @Override
    public String toString() {
        return "GoodsVo{" +
                "listId='" + listId + '\'' +
                ", imageBanner='" + imageBanner + '\'' +
                ", detailsImage='" + detailsImage + '\'' +
                ", brand='" + brand + '\'' +
                ", place='" + place + '\'' +
                ", producer='" + producer + '\'' +
                ", carId='" + carId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", goodsState='" + goodsState + '\'' +
                ", oldPrice='" + oldPrice + '\'' +
                ", avgPrice='" + avgPrice + '\'' +
                ", spec='" + spec + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", totalWeight='" + totalWeight + '\'' +
                ", discount='" + discount + '\'' +
                ", categoryIdTwo='" + categoryIdTwo + '\'' +
                ", categoryIdOne='" + categoryIdOne + '\'' +
                ", goodsBaseName='" + goodsBaseName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", specId='" + specId + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", goodsAlias='" + goodsAlias + '\'' +
                ", fullName='" + fullName + '\'' +
                ", createDate='" + createDate + '\'' +
                ", introduction='" + introduction + '\'' +
                ", isGift='" + isGift + '\'' +
                ", goodsFresh='" + goodsFresh + '\'' +
                ", feature='" + feature + '\'' +
                ", isTop='" + isTop + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", seoKeywords='" + seoKeywords + '\'' +
                ", baseSpec='" + baseSpec + '\'' +
                ", goodsBaseType='" + goodsBaseType + '\'' +
                ", guige=" + guige +
                ", goodsSpec=" + goodsSpec +
                ", goodsNum='" + goodsNum + '\'' +
                ", totalCount='" + totalCount + '\'' +
                ", isGuige=" + isGuige +
                ", carStatus=" + carStatus +
                ", delStatus=" + delStatus +
                '}';
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getCarSstatus() {
        return carStatus;
    }

    public void setCarSstatus(int carSstatus) {
        this.carStatus = carSstatus;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCategoryIdTwo() {
        return categoryIdTwo;
    }

    public void setCategoryIdTwo(String categoryIdTwo) {
        this.categoryIdTwo = categoryIdTwo;
    }

    public String getCategoryIdOne() {
        return categoryIdOne;
    }

    public void setCategoryIdOne(String categoryIdOne) {
        this.categoryIdOne = categoryIdOne;
    }

    public String getGoodsBaseName() {
        return goodsBaseName;
    }

    public void setGoodsBaseName(String goodsBaseName) {
        this.goodsBaseName = goodsBaseName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getGoodsAlias() {
        return goodsAlias;
    }

    public void setGoodsAlias(String goodsAlias) {
        this.goodsAlias = goodsAlias;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIsGift() {
        return isGift;
    }

    public void setIsGift(String isGift) {
        this.isGift = isGift;
    }

    public String getGoodsFresh() {
        return goodsFresh;
    }

    public void setGoodsFresh(String goodsFresh) {
        this.goodsFresh = goodsFresh;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    public String getBaseSpec() {
        return baseSpec;
    }

    public void setBaseSpec(String baseSpec) {
        this.baseSpec = baseSpec;
    }

    public String getGoodsBaseType() {
        return goodsBaseType;
    }

    public void setGoodsBaseType(String goodsBaseType) {
        this.goodsBaseType = goodsBaseType;
    }

    public List<GuigeVo> getGuige() {
        return guige;
    }

    public void setGuige(ArrayList<GuigeVo> guige) {
        this.guige = guige;
    }


    public boolean isGuige() {
        return isGuige;
    }

    public void setGuige(boolean guige) {
        isGuige = guige;
    }

//    public static Creator<GoodsVo> getCREATOR() {
//        return CREATOR;
//    }

}
