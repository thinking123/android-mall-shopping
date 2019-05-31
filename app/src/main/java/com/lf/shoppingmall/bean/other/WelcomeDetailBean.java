package com.lf.shoppingmall.bean.other;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/13.
 */

public class WelcomeDetailBean implements Serializable {
    private String id;
    private String url;
    private String introduce;
    private String state;
    private String addTime;
    private String reservedField;

    @Override
    public String toString() {
        return "WelcomeDetailBean{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", introduce='" + introduce + '\'' +
                ", state='" + state + '\'' +
                ", addTime='" + addTime + '\'' +
                ", reservedField='" + reservedField + '\'' +
                '}';
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

    public String getReservedField() {
        return reservedField;
    }

    public void setReservedField(String reservedField) {
        this.reservedField = reservedField;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    //                {"welcomeList":[{"id":22,
// "url":"http:\/\/47.92.117.38:8088\/upload\/image\/activity\/20170903\/b815e5db-26d2-4604-82d9-266ebd2a592f.jpg",
// "introduce":"1",
// "state":"1",
// "addTime":"Sep 3, 2017 9:16:22 AM"
// ,"reservedField":"0"},
}
