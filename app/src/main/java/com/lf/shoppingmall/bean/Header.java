package com.lf.shoppingmall.bean;

/**
 * 解析类头部
 * Created by Administrator on 2017/8/7.
 */

public class Header {
//    {"header":{"errorCode":"200","errorMsg":"success","serverTime":"2017-08-03 22:10:23"}
    private String errorCode;
    private String errorMsg;
    private String serverTime;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public String toString() {
        return "Header{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", serverTime='" + serverTime + '\'' +
                '}';
    }
}
