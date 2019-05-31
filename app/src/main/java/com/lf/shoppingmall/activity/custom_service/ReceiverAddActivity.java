package com.lf.shoppingmall.activity.custom_service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.login.ActivityPerfect;
import com.lf.shoppingmall.activity.login.LoginActivity;
import com.lf.shoppingmall.activity.main.MainActivity;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.ComUtils;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.ImmersionStatus;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收件人添加
 * Created by Administrator on 2017/8/27.
 */

public class ReceiverAddActivity extends BaseActivity implements TextWatcher {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_receiver;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        btnLogin.setEnabled(false);
        etName.addTextChangedListener(this);
        etPwd.addTextChangedListener(this);
    }

    @Override
    protected Object getTitleText() {
        return "新增收货人";
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        checkInfo();
    }

    private void checkInfo() {
        String userName = etName.getText().toString().trim();
//        if (TextUtils.isEmpty(userName)) {
////            showToast();
//            return;
//        }

        final String password = etPwd.getText().toString().trim();
//        if (TextUtils.isEmpty(password)) {
//            showToast("");
//            return;
//        }

        login(userName, password);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(etName.getText().toString().trim()) && !TextUtils.isEmpty(etPwd.getText().toString().trim())) {
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundResource(R.drawable.bg_green_corner5);
            btnLogin.setTextColor(ContextCompat.getColor(context, R.color.white));
        }else {
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundResource(R.drawable.bg_corner8_grey);
            btnLogin.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    /**
     * 添加收获人
     * @param userName
     * @param password
     */
    private void login(final String userName, final String password) {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("telPhone", password);
            map.put("trueName", userName);
            UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
            map.put("token", userVo.getToken());
            String des = new Gson().toJson(map);
            LogUtils.e("添加收获人", "des-->" + des);
            params = DES.encryptDES(des);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("添加收获人", "Exception-->" + e.toString());
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.addressAPIInsert)
//                        .mediaType(MediaType.parse(getString(R.string.media_type)))
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }
}
