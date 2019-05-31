package com.lf.shoppingmall.activity.custom_service;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.login.TermActivity;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.address.MyAddressListBean;
import com.lf.shoppingmall.bean.service.CommonProlem;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.CommonUilts;
import com.lr.baseview.utils.ImmersionStatus;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的服务主界面
 * Created by Administrator on 2017/8/16.
 */

public class CustomServiceMainActivity extends BaseActivity {

    @Bind(R.id.tv_service_phone)
    TextView tv_service_phone;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_service;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String text = "客服电话:" + ComParams.TELL_COUSTOM_SERVICE;
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 5, text.length(),
                //setSpan时需要指定的 flag,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括).
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_service_phone.setText(spannable);
    }

    @Override
    protected Object getTitleText() {
        return "客户服务";
    }

    @OnClick({R.id.tv_problem_com, R.id.tv_problem_just, R.id.tv_problem_hostory, R.id.tv_service_term, R.id.tv_shouhou, R.id.tv_yunfei, R.id.tv_service_online, R.id.tv_service_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_problem_com:
                startActivity(new Intent(CustomServiceMainActivity.this, CommonProlemActivity.class));
                break;

            case R.id.tv_problem_just:
                startActivity(new Intent(CustomServiceMainActivity.this, MyFankuiActivity.class));
                break;

            case R.id.tv_problem_hostory:
                break;

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

            case R.id.tv_service_online:
                break;

            case R.id.tv_service_phone:
                CommonUilts.showTellDialog(this, ComParams.TELL_COUSTOM_SERVICE);
                break;
        }
    }
}
