package com.lf.shoppingmall.bean.address;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;
import java.util.List;

/**
 * 地址解析类
 * Created by Administrator on 2017/8/8.
 */

public class CityListVo extends Body implements Serializable {

    private List<CityItemPYVo> cityList;

    @Override
    public String toString() {
        return "CityListVo{" +
                "cityList=" + cityList +
                '}';
    }

    public List<CityItemPYVo> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityItemPYVo> cityList) {
        this.cityList = cityList;
    }
}
