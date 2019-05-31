package com.lf.shoppingmall.bean.AllOrder;

import com.lf.shoppingmall.bean.index.GoodsVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */

public class CarImageInfoBean implements Serializable {
//    {
//        "num": 1,
//                "carInfo": [
//        {
//            "id": 2,
//                "cityCode": 141000,
//                "goodsAlias": "",
//                "fullName": "法香",
//                "createDate": "Aug 1, 2017 4:00:21 PM",
//                "image": "http:\/\/47.92.117.38:8088\/upload\/image\/1.jpg",
//                "introduction": "叶为深绿色指深青色",
//                "detailsImage": "http:\/\/47.92.117.38:8088\/upload\/image\/1.jpg,http:\/\/47.92.117.38:8088\/upload\/image\/2.jpg,http:\/\/47.92.117.38:8088\/upload\/image\/4.jpg",
//                "feature": "绿",
//                "productCategory": 2,
//                "modifyDate": "Aug 4, 2017 10:37:30 PM",
//                "baseSpec": "两",
//                "goodsBaseType": "2",
//                "goodsState": "1",
//                "goodsSpec": [
//            {
//                "id": 4,
//                    "goodsId": 2,
//                    "baseSpec": "两",
//                    "spec": "1*袋（5两）",
//                    "specNum": 5,
//                    "totalWeight": 0.5,
//                    "oldPrice": 6.45,
//                    "currentPrice": 6.45,
//                    "avgPrice": 1.29,
//                    "discount": 1,
//                    "cashPledge": 0,
//                    "carGoodNum": 52
//            }
//                ],
//            "totalWeight": 26,
//                "goodPrice": 335.4
//        }
//        ]
    private int num;
    private List<GoodsVo> carInfo;

    @Override
    public String toString() {
        return "CarImageInfoBean{" +
                "num=" + num +
                ", carInfo=" + carInfo +
                '}';
    }

    public List<GoodsVo> getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(List<GoodsVo> carInfo) {
        this.carInfo = carInfo;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
