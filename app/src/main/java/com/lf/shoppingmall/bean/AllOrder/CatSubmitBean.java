package com.lf.shoppingmall.bean.AllOrder;

import com.lf.shoppingmall.bean.Body;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.address.MyAddress;
import com.lf.shoppingmall.bean.index.GoodsVo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/5.
 */

public class CatSubmitBean extends Body implements Serializable {
//    "totalcurrentPrice": 335.4,
//            "totalcashPeldge": 0,
//            "totalPrice": 335.4,
//            "carImageInfo":
//    },
//            "store": {
//        "id": 10,
//                "storeTelephone": "13994797130",
//                "storePwd": "",
//                "addTime": "Aug 7, 2017 10:09:01 AM",
//                "deleteStatus": 0,
//                "storeApprove": 0,
//                "storeStatus": 0,
//                "storeName": "哈哈小吃店",
//                "managerName": "哈哈发个",
//                "storeAddress": "北京东城区",
//                "cityCode": "141000",
//                "uuid": "00000000-5348-0021-ffbc-be290033c587",
//                "token": "",
//                "isFinish": "1",
//                "expressTime": "2017-09-05 17:22:45"

    private MyAddress address;
    private float totalcurrentPrice;
    private float totalPrice;
    private String totalcashPeldge;
    private CarImageInfoBean carImageInfo;
    private UserVo store;

    @Override
    public String toString() {
        return "CatSubmitBean{" +
                "address=" + address +
                ", totalcurrentPrice=" + totalcurrentPrice +
                ", totalPrice=" + totalPrice +
                ", totalcashPeldge='" + totalcashPeldge + '\'' +
                ", carImageInfo=" + carImageInfo +
                ", store=" + store +
                '}';
    }

    public MyAddress getAddress() {
        return address;
    }

    public void setAddress(MyAddress address) {
        this.address = address;
    }

    public float getTotalcurrentPrice() {
        return totalcurrentPrice;
    }

    public void setTotalcurrentPrice(float totalcurrentPrice) {
        this.totalcurrentPrice = totalcurrentPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalcashPeldge() {
        return totalcashPeldge;
    }

    public void setTotalcashPeldge(String totalcashPeldge) {
        this.totalcashPeldge = totalcashPeldge;
    }

    public CarImageInfoBean getCarImageInfo() {
        return carImageInfo;
    }

    public void setCarImageInfo(CarImageInfoBean carImageInfo) {
        this.carImageInfo = carImageInfo;
    }

    public UserVo getStore() {
        return store;
    }

    public void setStore(UserVo store) {
        this.store = store;
    }
}
