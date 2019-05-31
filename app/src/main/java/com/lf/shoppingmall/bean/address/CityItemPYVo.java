package com.lf.shoppingmall.bean.address;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */

public class CityItemPYVo implements Serializable, Parcelable {
//    cityList": [
//    {
//        "name": "H",
//            "info": [
//        {
//            "cityState": 1,
//                "cityAttribute": "3",
//                "cityHot": "0",
//                "cityPinyin": "H",
//                "cityCode": "0318",
//                "cityOrder": 220,
//                "cityDesc": "衡水市",
//                "cityCaption": "衡水市",
//                "adCode": "131100",
//                "parentCode": "130000",
//                "parentLevel": 1
//        }
//            ]
//    },
//    {
//        "name": "L",
//            "info": [
//        {
//            "cityState": 1,
//                "cityAttribute": "3",
//                "cityPinyin": "L",
//                "cityCode": "0357",
//                "cityOrder": 341,
//                "cityDesc": "临汾市",
//                "cityCaption": "临汾市",
//                "adCode": "141000",
//                "parentCode": "140000",
//                "parentLevel": 1
//        }
//            ]
//    },
//    {
//        "name": "Z",
//            "info": [
//
//            ]
//    }
//    ],

    private String name;
    private List<CityVo> info;

    public CityItemPYVo() {
    }

    protected CityItemPYVo(Parcel in) {
        name = in.readString();
        info = in.readArrayList(CityItemPYVo.class.getClassLoader());
    }

    public static final Creator<CityItemPYVo> CREATOR = new Creator<CityItemPYVo>() {
        @Override
        public CityItemPYVo createFromParcel(Parcel in) {
            return new CityItemPYVo(in);
        }

        @Override
        public CityItemPYVo[] newArray(int size) {
            return new CityItemPYVo[size];
        }
    };

    @Override
    public String toString() {
        return "CityItemPYVo{" +
                "name='" + name + '\'' +
                ", info=" + info +
                '}';
    }

    public List<CityVo> getInfo() {
        return info;
    }

    public void setInfo(List<CityVo> info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeList(info);
    }
}
