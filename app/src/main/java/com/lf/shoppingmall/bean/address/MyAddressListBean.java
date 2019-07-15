package com.lf.shoppingmall.bean.address;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class MyAddressListBean extends Body implements Serializable {

    private List<MyAddress> storeAddressList;

    @Override
    public String toString() {
        return "MyAddressListBean{" +
                "storeAddressList=" + storeAddressList +
                '}';
    }

    public List<MyAddress> getStoreAddressList() {
        return storeAddressList;
    }

    public void setStoreAddressList(List<MyAddress> storeAddressList) {
        this.storeAddressList = storeAddressList;
    }
    //    "storeAddressList": [
//    {
//        "storeName": "哈哈小吃店",
//            "trueName": "12",
//            "telPhone": "12",
//            "address": "北京东城区",
//            "addrDefault": 1,
//            "regionValue": "12",
//            "storeId": 10,
//            "addressId": 10,
//            "addTime": "Dec 29, 1899 12:00:00 AM",
//            "regionId": 12
//    },
//    {
//        "storeName": "哈哈小吃店",
//            "trueName": "12",
//            "telPhone": "112",
//            "address": "北京东城区",
//            "addrDefault": 0,
//            "regionValue": "12",
//            "storeId": 10,
//            "addressId": 11,
//            "addTime": "Dec 30, 1899 9:00:00 PM",
//            "regionId": 21
//    },
//    {
//        "storeName": "哈哈小吃店",
//            "trueName": "34",
//            "telPhone": "qwqwqw",
//            "address": "北京东城区",
//            "addrDefault": 0,
//            "regionValue": "34",
//            "storeId": 10,
//            "addressId": 12,
//            "addTime": "Dec 30, 1899 12:00:00 PM",
//            "regionId": 34
//    }
//    ]
//}
}
