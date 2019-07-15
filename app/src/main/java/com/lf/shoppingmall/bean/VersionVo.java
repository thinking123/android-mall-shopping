package com.lf.shoppingmall.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/29.
 */

public class VersionVo implements Serializable {

    private static final long serialVersionUID = 2704487448445407370L;
    private String versionId;
    private String versionCode;
    private String updateContent;

    public VersionVo() {
    }

    public String getVersionId() {
        return this.versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getUpdateContent() {
        return this.updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }
}
