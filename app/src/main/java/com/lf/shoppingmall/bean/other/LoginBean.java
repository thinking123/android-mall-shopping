package com.lf.shoppingmall.bean.other;

import com.lf.shoppingmall.bean.Body;
import com.lf.shoppingmall.bean.UserVo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/4.
 */

public class LoginBean extends Body implements Serializable {
    private UserVo store;
    private String token;
    private String isFinish;

    @Override
    public String toString() {
        return "LoginBean{" +
                "store=" + store +
                ", token='" + token + '\'' +
                ", isFinish='" + isFinish + '\'' +
                '}';
    }

    public UserVo getStore() {
        return store;
    }

    public void setStore(UserVo store) {
        this.store = store;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }
}
