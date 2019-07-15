package com.lf.shoppingmall.activity.custom_service;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lr.baseview.utils.ImmersionStatus;

import butterknife.ButterKnife;

/** 扫码下载
 * Created by Administrator on 2017/9/14.
 */

public class ActivitySaoma extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_saoma;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

    }

    @Override
    protected Object getTitleText() {
        return "扫码下载";
    }
}
