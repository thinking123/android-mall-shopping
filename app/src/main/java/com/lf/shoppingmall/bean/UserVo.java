package com.lf.shoppingmall.bean;

import java.io.Serializable;

/**
 * 登录用户信息
 * Created by Administrator on 2017/8/3.
 */

public class UserVo extends Body implements Serializable {
//      "id": 9,
//              "storeTelephone": "112233",
//              "storePwd": "XilSzznYyzw=",
//              "addtime": 1501493734000,
//              "deletestatus": 0,
//              "storeApprove": 0,
//              "storeStatus": 0,
//              "labelId": "2,3,1",
//              "labelName": "饺子,盖饭,拉面"

//     "storeName": "哈哈小吃店",
//             "storeApprove": 0,
//             "storePwd": "",
//             "managerName": "哈哈发个",
//             "cityCode": "141000",
//             "storeAddress": "北京东城区",
//             "deleteStatus": 0,
//             "id": 10,
//             "storeStatus": 0,
//             "token": "",
//             "isFinish": "1",
//             "uuid": "ffffffff-e859-bd52-ffff-ffff91e26eb0",
//             "storeTelephone": "13994797130",
//             "addTime": "Aug 7, 2017 10:09:01 AM"

    private String storeName;
    private String managerName;
    private String cityCode;
    private String storeAddress;
    private String deleteStatus;
    private String uuid;
    private String addTime;
    private String id;
    private String storeTelephone;
    private String storePwd;
    private String addtime;
    private String deletestatus;
    private String storeApprove;
    private String storeStatus;
    private String labelId;
    private String labelName;
    private String isFinish;
    private String token;
    private String cityName;


    private boolean isLogin;

    private String desParams;//加密params
    private String pwd;//明文密码

    @Override
    public String toString() {
        return "UserVo{" +
                "storeName='" + storeName + '\'' +
                ", managerName='" + managerName + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", deleteStatus='" + deleteStatus + '\'' +
                ", uuid='" + uuid + '\'' +
                ", addTime='" + addTime + '\'' +
                ", id='" + id + '\'' +
                ", storeTelephone='" + storeTelephone + '\'' +
                ", storePwd='" + storePwd + '\'' +
                ", addtime='" + addtime + '\'' +
                ", deletestatus='" + deletestatus + '\'' +
                ", storeApprove='" + storeApprove + '\'' +
                ", storeStatus='" + storeStatus + '\'' +
                ", labelId='" + labelId + '\'' +
                ", labelName='" + labelName + '\'' +
                ", isFinish='" + isFinish + '\'' +
                ", token='" + token + '\'' +
                ", cityName='" + cityName + '\'' +
                ", isLogin=" + isLogin +
                ", desParams='" + desParams + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    public String getDesParams() {
        return desParams;
    }

    public void setDesParams(String desParams) {
        this.desParams = desParams;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreTelephone() {
        return storeTelephone;
    }

    public void setStoreTelephone(String storeTelephone) {
        this.storeTelephone = storeTelephone;
    }

    public String getStorePwd() {
        return storePwd;
    }

    public void setStorePwd(String storePwd) {
        this.storePwd = storePwd;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getDeletestatus() {
        return deletestatus;
    }

    public void setDeletestatus(String deletestatus) {
        this.deletestatus = deletestatus;
    }

    public String getStoreApprove() {
        return storeApprove;
    }

    public void setStoreApprove(String storeApprove) {
        this.storeApprove = storeApprove;
    }

    public String getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(String storeStatus) {
        this.storeStatus = storeStatus;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

}
