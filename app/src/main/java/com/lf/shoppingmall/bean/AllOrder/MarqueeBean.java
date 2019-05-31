package com.lf.shoppingmall.bean.AllOrder;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/6.
 */

public class MarqueeBean extends Body implements Serializable {
    private Marquee marquee;

    @Override
    public String toString() {
        return "MarqueeBean{" +
                "marquee=" + marquee +
                '}';
    }

    public Marquee getMarquee() {
        return marquee;
    }

    public void setMarquee(Marquee marquee) {
        this.marquee = marquee;
    }
}