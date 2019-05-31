package com.lf.shoppingmall.bean.AllOrder;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/17.
 */

public class GoodsCateVo implements Serializable, Parcelable {
    //     "categoryId":2,
//            "categoryName":"叶菜",
//            "parentId":1,
//            "categoryOrder":2,
//            "categoryState":1,
//            "addTime":"Aug 1, 2017 3:00:34 PM"
    private String categoryId;
    private String categoryName;
    private String parentId;
    private String categoryOrder;
    private String categoryState;
    private String addTime;

    public GoodsCateVo() {
    }

    protected GoodsCateVo(Parcel in) {
        categoryId = in.readString();
        categoryName = in.readString();
        parentId = in.readString();
        categoryOrder = in.readString();
        categoryState = in.readString();
        addTime = in.readString();
    }

    public static final Creator<GoodsCateVo> CREATOR = new Creator<GoodsCateVo>() {
        @Override
        public GoodsCateVo createFromParcel(Parcel in) {
            return new GoodsCateVo(in);
        }

        @Override
        public GoodsCateVo[] newArray(int size) {
            return new GoodsCateVo[size];
        }
    };

    @Override
    public String toString() {
        return "GoodsCateVo{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", categoryOrder='" + categoryOrder + '\'' +
                ", categoryState='" + categoryState + '\'' +
                ", addTime='" + addTime + '\'' +
                '}';
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryId);
        dest.writeString(categoryName);
        dest.writeString(parentId);
        dest.writeString(categoryOrder);
        dest.writeString(categoryState);
        dest.writeString(addTime);
    }
}
