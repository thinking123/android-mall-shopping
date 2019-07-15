package com.lf.shoppingmall.activity.login;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lr.baseview.utils.ImmersionStatus;

import butterknife.ButterKnife;

/**
 * 管理者中心
 * Created by Administrator on 2017/8/3.
 */

public class ManagerCenterActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_manager_center;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);
    }

    @Override
    protected Object getTitleText() {
        return getString(R.string.manager_center);
    }
}
