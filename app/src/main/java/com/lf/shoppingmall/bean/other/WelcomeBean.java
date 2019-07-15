package com.lf.shoppingmall.bean.other;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class WelcomeBean implements Serializable {
    private List<WelcomeDetailBean> welcomeList;

    @Override
    public String toString() {
        return "WelcomeBean{" +
                "welcomeList=" + welcomeList +
                '}';
    }

    public List<WelcomeDetailBean> getWelcomeList() {
        return welcomeList;
    }

    public void setWelcomeList(List<WelcomeDetailBean> welcomeList) {
        this.welcomeList = welcomeList;
    }
}
