package com.lf.shoppingmall.bean.index;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;
import java.util.List;

/**
 * 主界面数据
 * Created by Administrator on 2017/8/10.
 */

public class IndexVo extends Body implements Serializable {

    private List<BannerVo> bannerList;

    private List<PromotionVo> activityList;

    private List<ProductionInfoVo> ProductionInfoList;

    @Override
    public String toString() {
        return "IndexVo{" +
                "bannerList=" + bannerList +
                ", activityList=" + activityList +
                ", ProductionInfoList=" + ProductionInfoList +
                '}';
    }

    public List<BannerVo> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerVo> bannerList) {
        this.bannerList = bannerList;
    }

    public List<PromotionVo> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<PromotionVo> activityList) {
        this.activityList = activityList;
    }

    public List<ProductionInfoVo> getProductionInfoList() {
        return ProductionInfoList;
    }

    public void setProductionInfoList(List<ProductionInfoVo> productionInfoList) {
        ProductionInfoList = productionInfoList;
    }
}
