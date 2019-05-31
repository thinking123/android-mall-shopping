package com.lf.shoppingmall.activity.custom_service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.login.ActivityPerfect;
import com.lf.shoppingmall.activity.login.LoginActivity;
import com.lf.shoppingmall.activity.main.MainActivity;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.other.LoginBean;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改密码
 * Created by Administrator on 2017/9/4.
 */

public class UpdatePwdActivity extends BaseActivity {
    @Bind(R.id.et_old)
    EditText etOld;
    @Bind(R.id.et_pwd_f)
    EditText etPwdF;
    @Bind(R.id.et_pwd_s)
    EditText etPwdS;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_pwd;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

    }

    @Override
    protected Object getTitleText() {
        return "修改密码";
    }

    @OnClick({R.id.iv_back, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_submit:
                checkPwdInfo();
                break;
        }
    }

    private void checkPwdInfo() {
        UserVo userVo = ((BaseApplication)getApplication()).getUserVo();
        String old = etOld.getText().toString().trim();
        if (TextUtils.isEmpty(old)) {
            showToast("请输入当前密码");
            return;
        }

        if (!old.equals(userVo.getPwd())) {
            showToast("当前密码不正确，请重新输入");
            return;
        }

        final String password = etPwdF.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            showToast("请输入新密码");
            return;
        }

        final String passwords = etPwdS.getText().toString().trim();
        if (TextUtils.isEmpty(passwords)) {
            showToast("请确认新密码");
            return;
        }

        if (!password.equals(passwords)){
            showToast("新密码两次输入不一致，请重新输入");
            return;
        }

        update(userVo, old, password);
    }

    /**
     *
     * @param old
     * @param password
     */
    private void update(final UserVo userVo, final String old,final String password) {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("oldPwd", old);
            map.put("token", userVo.getToken());
            map.put("newPwd", password);
            String des = new Gson().toJson(map);
            LogUtils.e("修改密码", "des-->" + des);
            params = DES.encryptDES(des);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("修改密码", "Exception-->" + e.toString());
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.updatePwd)
//                        .mediaType(MediaType.parse(getString(R.string.media_type)))
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("修改密码", "body--->" +body);
                try {
                    userVo.setPwd(password);
                    ((BaseApplication) getApplication()).setUserVo(userVo);
                    showToast("修改密码成功，下次登录请使用新的密码");
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

}
