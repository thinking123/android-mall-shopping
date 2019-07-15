package com.lf.shoppingmall.bean.AllOrder;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/6.
 */

public class Marquee implements Serializable {
//    "marquee":{"id":1,"name":"111111","positon":"3333333333333",
//            "content":"12121121111331211","addTime":"Aug 29, 2017 6:34:07 PM","state":"1"}
    private String id;
    private String name;
    private String positon;
    private String content;
    private String addTime;
    private String state;

    @Override
    public String toString() {
        return "Marquee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", positon='" + positon + '\'' +
                ", content='" + content + '\'' +
                ", addTime='" + addTime + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositon() {
        return positon;
    }

    public void setPositon(String positon) {
        this.positon = positon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
