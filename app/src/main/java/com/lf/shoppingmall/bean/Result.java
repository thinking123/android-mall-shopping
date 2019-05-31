package com.lf.shoppingmall.bean;

import java.io.Serializable;

/**
 * 解析类父类
 * Created by Administrator on 2017/8/7.
 */

public class Result<T> implements Serializable {
    private Header header;

    private T body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Result{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
