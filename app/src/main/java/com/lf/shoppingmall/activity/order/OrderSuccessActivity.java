package com.lf.shoppingmall.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.common.ComParams;
import com.lr.baseview.utils.ImmersionStatus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单成功界面
 * Created by Administrator on 2017/9/2.
 */

public class OrderSuccessActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_success;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);
        ivBack.setVisibility(View.INVISIBLE);
    }

    @Override
    protected Object getTitleText() {
        return "下单成功";
    }

    @OnClick({R.id.btn_neg, R.id.btn_pos})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_neg:
                ComParams.main_current_page = 1;
                finish();
                break;

            case R.id.btn_pos:
                showToast("界面待调整");
                startActivity(new Intent(this, MyOrderActivity.class));
                finish();
                break;
        }
    }
}
