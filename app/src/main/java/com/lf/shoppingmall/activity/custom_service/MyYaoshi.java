package com.lf.shoppingmall.activity.custom_service;

import android.os.Bundle;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lr.baseview.utils.ImmersionStatus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的钥匙
 * todo 首次进入？？
 * Created by Administrator on 2017/9/4.
 */

public class MyYaoshi extends BaseActivity {

    @Bind(R.id.tv_id)
    TextView tvId;
    @Bind(R.id.tv_store_name)
    TextView tvStoreName;
    @Bind(R.id.tv_person)
    TextView tvPerson;
    @Bind(R.id.tv_person_phone)
    TextView tvPersonPhone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_yaoshi;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
        tvId.setText(userVo.getId());
        tvStoreName.setText(userVo.getStoreName());
        tvPerson.setText(userVo.getManagerName());
        tvPersonPhone.setText(userVo.getStoreTelephone());
    }

    @Override
    protected Object getTitleText() {
        return "商户钥匙托管电子协议";
    }
}
