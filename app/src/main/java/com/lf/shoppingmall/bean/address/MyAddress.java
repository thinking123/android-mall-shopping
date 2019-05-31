package com.lf.shoppingmall.bean.address;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/31.
 */

public class MyAddress implements Serializable {

//     "storeName": "哈哈小吃店",
//            "trueName": "12",
//            "telPhone": "12",
//            "address": "北京东城区",
//            "addrDefault": 1,
//            "regionValue": "12",
//            "storeId": 10,
//            "addressId": 10,
//            "addTime": "Dec 29, 1899 12:00:00 AM",
//            "regionId": 12

    private String storeName;
    private String trueName;
    private String telPhone;
    private String address;
    private String addrDefault;
    private String regionValue;
    private String storeId;
    private String addressId;
    private String addTime;
    private String regionId;

    @Override
    public String toString() {
        return "MyAddress{" +
                "storeName='" + storeName + '\'' +
                ", trueName='" + trueName + '\'' +
                ", telPhone='" + telPhone + '\'' +
                ", address='" + address + '\'' +
                ", addrDefault='" + addrDefault + '\'' +
                ", regionValue='" + regionValue + '\'' +
                ", storeId='" + storeId + '\'' +
                ", addressId='" + addressId + '\'' +
                ", addTime='" + addTime + '\'' +
                ", regionId='" + regionId + '\'' +
                '}';
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddrDefault() {
        return addrDefault;
    }

    public void setAddrDefault(String addrDefault) {
        this.addrDefault = addrDefault;
    }

    public String getRegionValue() {
        return regionValue;
    }

    public void setRegionValue(String regionValue) {
        this.regionValue = regionValue;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}
