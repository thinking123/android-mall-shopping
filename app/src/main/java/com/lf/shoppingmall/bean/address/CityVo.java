package com.lf.shoppingmall.bean.address;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 城市vo
 * Created by Administrator on 2017/8/8.
 */

public class CityVo implements Serializable, Parcelable {
//    {
//            "cityState": 1,
//                "cityAttribute": "3",
//                "cityHot": "0",
//                "cityPinyin": "Z",
//                "cityCode": "0355",
//                "cityOrder": 266,
//                "cityDesc": "长治市",
//                "cityCaption": "长治市",
//                "adCode": "140400",
//                "parentCode": "140000",
//                "parentLevel": 1
//        }

    private String cityState;
    private String cityAttribute;
    private String cityHot;
    private String cityPinyin;
    private String cityCode;
    private String cityOrder;
    private String cityDesc;
    private String cityCaption;
    private String adCode;
    private String parentCode;
    private String parentLevel;

    private boolean isSelect;

    protected CityVo(Parcel in) {
        cityState = in.readString();
        cityAttribute = in.readString();
        cityHot = in.readString();
        cityPinyin = in.readString();
        cityCode = in.readString();
        cityOrder = in.readString();
        cityDesc = in.readString();
        cityCaption = in.readString();
        adCode = in.readString();
        parentCode = in.readString();
        parentLevel = in.readString();
        isSelect = in.readByte() != 0;
    }

    public static final Creator<CityVo> CREATOR = new Creator<CityVo>() {
        @Override
        public CityVo createFromParcel(Parcel in) {
            return new CityVo(in);
        }

        @Override
        public CityVo[] newArray(int size) {
            return new CityVo[size];
        }
    };

    @Override
    public String toString() {
        return "CityVo{" +
                "cityState='" + cityState + '\'' +
                ", cityAttribute='" + cityAttribute + '\'' +
                ", cityHot='" + cityHot + '\'' +
                ", cityPinyin='" + cityPinyin + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", cityOrder='" + cityOrder + '\'' +
                ", cityDesc='" + cityDesc + '\'' +
                ", cityCaption='" + cityCaption + '\'' +
                ", adCode='" + adCode + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", parentLevel='" + parentLevel + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getCityState() {
        return cityState;
    }

    public void setCityState(String cityState) {
        this.cityState = cityState;
    }

    public String getCityAttribute() {
        return cityAttribute;
    }

    public void setCityAttribute(String cityAttribute) {
        this.cityAttribute = cityAttribute;
    }

    public String getCityHot() {
        return cityHot;
    }

    public void setCityHot(String cityHot) {
        this.cityHot = cityHot;
    }

    public String getCityPinyin() {
        return cityPinyin;
    }

    public void setCityPinyin(String cityPinyin) {
        this.cityPinyin = cityPinyin;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityOrder() {
        return cityOrder;
    }

    public void setCityOrder(String cityOrder) {
        this.cityOrder = cityOrder;
    }

    public String getCityDesc() {
        return cityDesc;
    }

    public void setCityDesc(String cityDesc) {
        this.cityDesc = cityDesc;
    }

    public String getCityCaption() {
        return cityCaption;
    }

    public void setCityCaption(String cityCaption) {
        this.cityCaption = cityCaption;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentLevel() {
        return parentLevel;
    }

    public void setParentLevel(String parentLevel) {
        this.parentLevel = parentLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityState);
        dest.writeString(cityAttribute);
        dest.writeString(cityHot);
        dest.writeString(cityPinyin);
        dest.writeString(cityCode);
        dest.writeString(cityOrder);
        dest.writeString(cityDesc);
        dest.writeString(cityCaption);
        dest.writeString(adCode);
        dest.writeString(parentCode);
        dest.writeString(parentLevel);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }
}
