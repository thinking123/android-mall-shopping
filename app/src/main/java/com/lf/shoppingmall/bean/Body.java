package com.lf.shoppingmall.bean;

import java.io.Serializable;

/**
 * 解析实体类
 * Created by Administrator on 2017/8/7.
 */

public class Body implements Serializable{

    protected String isSucceed;

    public String getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(String isSucceed) {
        this.isSucceed = isSucceed;
    }

    @Override
    public String toString() {
        return "Body{" +
                "isSucceed='" + isSucceed + '\'' +
                '}';
    }
}
