package com.lf.shoppingmall.bean.index;

import java.io.Serializable;

/**
 * 轮播图
 * Created by Administrator on 2017/8/10.
 */

public class BannerVo implements Serializable {
//          "id": 8,
//                  "url": "http://www.dqfckj.com/upload/image/companyHonor/honorImage/20170621/f4bf420a-60da-4fa4-8e37-88a07d0b5b4b.png",
//                  "introduce": "3333",
//                  "clickUrl": "1",
//                  "state": "1",
//                  "addTime": "Jul 15, 2017 5:05:01 PM"

    private String id;
    private String url;
    private String introduce;
    private String clickUrl;
    private String state;
    private String addTime;

    @Override
    public String toString() {
        return "BannerVo{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", introduce='" + introduce + '\'' +
                ", clickUrl='" + clickUrl + '\'' +
                ", state='" + state + '\'' +
                ", addTime='" + addTime + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
