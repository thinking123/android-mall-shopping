package com.lf.shoppingmall.utils;

import com.baidu.location.LocationClientOption;

/**
 * Created by Administrator on 2017/8/9.
 */

public class MapUtils {

    public static LocationClientOption initLCO() {
        LocationClientOption option = new LocationClientOption();
        // 可选，默认gcj02，设置返回的定位结果坐标系
        // gcj02：国测局坐标；
        // bd09：百度墨卡托坐标；
        // bd09ll：百度经纬度坐标；
        option.setCoorType("bd09ll");
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setScanSpan(1000);// 可选，默认0,设置发起定位请求的间隔时间为
        option.setAddrType("all");//返回的定位结果包含地址信息
        option.setIsNeedAddress(true); // 可选，设置是否需要地址信息，默认不需要
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        return option;
    }
}
