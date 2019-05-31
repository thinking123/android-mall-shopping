package com.lf.shoppingmall.bean.other;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/13.
 */

public class StartBean implements Serializable {
    private String startImg;

    @Override
    public String toString() {
        return "StartBean{" +
                "startImg='" + startImg + '\'' +
                '}';
    }

    public String getStartImg() {
        return startImg;
    }

    public void setStartImg(String startImg) {
        this.startImg = startImg;
    }
}
