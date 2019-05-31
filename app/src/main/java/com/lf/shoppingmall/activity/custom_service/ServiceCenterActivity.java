package com.lf.shoppingmall.activity.custom_service;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.login.TermActivity;
import com.lf.shoppingmall.base.BaseActivity;
import com.lr.baseview.utils.ImmersionStatus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 服务中心
 * Created by Administrator on 2017/9/6.
 */

public class ServiceCenterActivity extends BaseActivity {
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_center;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            tvVersion.setText("当前版本（v" + info.versionName + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object getTitleText() {
        return "服务中心";
    }

    @OnClick({R.id.tv_service_term, R.id.tv_shouhou, R.id.tv_yunfei})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_service_term:
                startActivity(new Intent(this, TermActivity.class)
                        .putExtra("type", 0));
                break;

            case R.id.tv_shouhou:
                startActivity(new Intent(this, TermActivity.class)
                        .putExtra("type", 3));
                break;

            case R.id.tv_yunfei:
                startActivity(new Intent(this, TermActivity.class)
                        .putExtra("type", 4));
                break;
        }
    }
}
