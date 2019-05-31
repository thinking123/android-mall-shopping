package com.lf.shoppingmall.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lr.baseview.utils.ImmersionStatus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/17.
 */

public class PayTypeActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_type;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);
    }

    @Override
    protected Object getTitleText() {
        return "选择支付方式";
    }

    @OnClick({R.id.btn_appliy, R.id.btn_wechat, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_appliy:
                setResult(RESULT_OK, new Intent().putExtra("isAppliy", true));
                finish();
                break;

            case R.id.btn_wechat:
                setResult(RESULT_OK, new Intent().putExtra("isAppliy", false));
                finish();
                break;
        }
    }
}
