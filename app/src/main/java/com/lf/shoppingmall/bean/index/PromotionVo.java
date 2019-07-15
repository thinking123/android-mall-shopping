package com.lf.shoppingmall.bean.index;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/10.
 */

public class PromotionVo implements Serializable {
//     "id": 1,
//             "activityClass": "活动1",
//             "name": "活动活动",
//             "introduce": "活动活动123456",
//             "activityImg": "/upload/image/companyProfile/banner\\20170804\\10a68c9e-f948-4e2e-bec8-bade46e00107.jpg",
//             "state": 1
private String id;
    private String activityImg;
    private String activityClass;
    private String name;
    private String state;
    private String introduce;

    @Override
    public String toString() {
        return "PromotionVo{" +
                "id='" + id + '\'' +
                ", activityImg='" + activityImg + '\'' +
                ", activityClass='" + activityClass + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityImg() {
        return activityImg;
    }

    public void setActivityImg(String activityImg) {
        this.activityImg = activityImg;
    }

    public String getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(String activityClass) {
        this.activityClass = activityClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
