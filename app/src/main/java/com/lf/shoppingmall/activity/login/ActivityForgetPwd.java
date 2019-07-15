package com.lf.shoppingmall.activity.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.ImmersionStatus;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码
 * Created by Administrator on 2017/9/14.
 */

public class ActivityForgetPwd extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.et_first_pwd)
    EditText etFirstPwd;
    @BindView(R.id.box_ped_clear1)
    CheckBox boxPedClear1;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.box_ped_clear2)
    CheckBox boxPedClear2;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_get_verificode)
    TextView tvGetVerificode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        boxPedClear1.setOnCheckedChangeListener(this);
        boxPedClear2.setOnCheckedChangeListener(this);
    }

    @Override
    protected Object getTitleText() {
        return "忘记密码";
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.box_ped_clear2) {
            if (isChecked) {
                etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        } else {
            if (isChecked) {
                etFirstPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etFirstPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        }
    }

    @OnClick({R.id.tv_get_verificode, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_verificode:
                String userName = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    showToast(R.string.please_login_logo_username);
                    return;
                }
                if (userName.length() < 11 || userName.length() > 16) {
                    showToast(R.string.please_login_logo_username_format);
                    return;
                }
//                tv_get_voice.setVisibility(View.VISIBLE);
                sendVCodeUpdate(userName);
                break;

            case R.id.btn_login:
                checkLoginInfo();
                break;
        }
    }


    private void checkLoginInfo() {
        String userName = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            showToast(R.string.please_login_logo_username);
            return;
        }
        if (userName.length() < 11 || userName.length() > 16) {
            showToast(R.string.please_login_logo_username_format);
            return;
        }

        final String verification = etVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(verification)) {
            showToast(R.string.please_input_verification_code);
            return;
        }
        if (verification.length() != ComParams.VERIFICATION_LENGHT) {
            showToast(R.string.please_input_verification_code_format);
            return;
        }

        final String password1 = etFirstPwd.getText().toString().trim();
        if (TextUtils.isEmpty(password1)) {
            showToast("请输入您的新密码");
            return;
        }
        if (password1.length() < ComParams.PWD_SHORT || password1.length() > ComParams.PWD_LONG) {
            showToast("请输入1-30位的新密码");
            return;
        }

        final String password2 = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(password2)) {
            showToast("请确认您的新密码");
            return;
        }

        if (!password1.equals(password2)){
            showToast("两次密码输入不一致，请重新输入");
            return;
        }

        register(userName, password1, verification);
    }

    /**
     * 忘记密码
     * @param phone
     * @param pwd
     * @param code
     */
    private void register(final String phone, final String pwd, String code) {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("storeTelephone", phone);
            map.put("verifyCode", code);
            map.put("newPwd", pwd);
            String des = new Gson().toJson(map);
            LogUtils.e("忘记密码", "des-->" + des);
            params = DES.encryptDES(des);
            LogUtils.e("忘记密码", "params-->" + params);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("忘记密码", "Exception-->" + e.toString());
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.forgetPwd)
//                        .mediaType(MediaType.parse(getString(R.string.media_type)))
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
//                dissmissLoading();
                UserVo userVo = new UserVo();
                userVo.setPwd(pwd);
                userVo.setStoreTelephone(phone);
                ((BaseApplication) getApplication()).setUserVo(userVo);
                showToast("修改密码成功，请用新的密码登录！");
                finish();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 短信接口
     * @param userName
     */
    private void sendVCodeUpdate(String userName) {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("phoneNum", userName);
            String des = new Gson().toJson(map);
            LogUtils.e("短信接口", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.sendVCodeUpdate)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("短信接口", "body-->" + body);
                startTimer();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 倒计时工具类
     */
    private void startTimer() {
        time = TIME;
        tvGetVerificode.setText(getString(R.string.get_verification_code) + "(" + time + "s)");
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                --time;
                if (time > 0) {
                    handler.sendEmptyMessage(CURRUNT);
                } else {
                    handler.sendEmptyMessage(ENABLE);
                    if (timer != null) {
                        timer.cancel();
                    }
                }
            }
        }, 0, 1000);
    }

    private final int CURRUNT = 1;
    private final int ENABLE = -1;
    private final int TIME = ComParams.VERIFICATION_TIME;
    private int time;
    /**
     * 倒计时handler
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ENABLE:
                    tvGetVerificode.setEnabled(true);
                    tvGetVerificode.setText(getString(R.string.get_verification_agin));
                    tvGetVerificode.setTextColor(getResources().getColor(R.color.text_green));
                    break;
                case CURRUNT:
                    tvGetVerificode.setEnabled(false);
                    tvGetVerificode.setText(getString(R.string.get_verification_code) + "(" + time + "s)");
                    tvGetVerificode.setTextColor(getResources().getColor(R.color.text_norm_8));
                    break;

                default:
                    break;
            }
        }
    };
}
