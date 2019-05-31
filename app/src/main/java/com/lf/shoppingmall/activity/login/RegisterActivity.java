package com.lf.shoppingmall.activity.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.Body;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.other.LoginBean;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.ComUtils;
import com.lr.baseview.utils.CommonUilts;
import com.lr.baseview.utils.ImmersionStatus;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.SharePreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * todo 注册接口 获取短信 电话短信
 * 注册界面
 * Created by Administrator on 2017/8/3.
 */

public class RegisterActivity extends BaseActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_get_verificode)
    TextView tvGetVerificode;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.box_ped_clear)
    CheckBox boxPedClear;
    @BindView(R.id.tv_get_voice)
    TextView tv_get_voice;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand);
        ButterKnife.bind(this);

        btnLogin.setEnabled(false);
        etPhone.addTextChangedListener(this);
        etPwd.addTextChangedListener(this);
        etVerificationCode.addTextChangedListener(this);
        boxPedClear.setOnCheckedChangeListener(this);
    }

    @Override
    protected Object getTitleText() {
        return getResources().getString(R.string.hint_register);
    }

    @Override
    protected void setTitleBarStatus(ImageView iv_back, TextView tv_title, TextView tv_right, ImageView iv_right) {
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(getResources().getString(R.string.costom_service));
        Drawable customService = getResources().getDrawable(R.mipmap.register_customer);
        customService.setBounds(0, 0, customService.getMinimumWidth(), customService.getMinimumHeight());
        tv_right.setCompoundDrawables(null, null, customService, null);
        tv_right.setOnClickListener(new MyRightTabListener());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        checkLoginBtnStatus();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    /**
     * 登录按钮状态
     */
    private void checkLoginBtnStatus() {
        if (!TextUtils.isEmpty(etPhone.getText().toString().trim()) && !TextUtils.isEmpty(etPwd.getText().toString().trim())
                && !TextUtils.isEmpty(etVerificationCode.getText().toString().trim())) {
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundResource(R.drawable.bg_green_corner5);
            btnLogin.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    @OnClick({R.id.tv_get_verificode, R.id.rl_term, R.id.tv_get_voice, R.id.btn_login})
    public void onViewClicked(View view) {
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

            case R.id.rl_term:
                startActivity(new Intent(this, TermActivity.class));
                break;

            case R.id.tv_get_voice:
                //TODO 调用接口
                showToast(R.string.get_voice_hine);
                break;
        }
    }

    private class MyRightTabListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            CommonUilts.showTellDialog(RegisterActivity.this, ComParams.TELL_COUSTOM_SERVICE);
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

        final String password = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            showToast(R.string.please_input_use_pwd);
            return;
        }
        if (password.length() < ComParams.PWD_SHORT || password.length() > ComParams.PWD_LONG) {
            showToast(R.string.please_input_use_pwd_format);
            return;
        }

        register(userName, password, verification);
    }

    /**
     * 验证接口
     */
    private void verifyCodeTelUpdate(final String phone, final String pwd, String code) {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("phoneNumber", phone);
            map.put("verifyCode", code);
            String des = new Gson().toJson(map);
            LogUtils.e("验证接口", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.verifyCodeTelUpdate)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
//                dissmissLoading();
                LogUtils.e("验证接口", "body-->" + body);
//                register(phone,pwd);
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

    private void register(final String phone, final String pwd, String code) {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("storeTelephone", phone);
            map.put("verifyCode", code);
            map.put("storePwd", pwd);
            String des = new Gson().toJson(map);
            LogUtils.e("注册", "des-->" + des);
            params = DES.encryptDES(des);
            LogUtils.e("注册", "params-->" + params);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("注册", "Exception-->" + e.toString());
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.REGISTER)
//                        .mediaType(MediaType.parse(getString(R.string.media_type)))
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
//                dissmissLoading();
                UserVo userVo = new UserVo();
                userVo.setPwd(pwd);
                userVo.setStoreTelephone(phone);
                saveUser(userVo);
                doLogin(phone, pwd);
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 注册成功后自动登录
     * @param userName
     * @param password
     */
    private void doLogin(final String userName, final String password) {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("storeTelephone", userName);
            map.put("storePwd", password);
            map.put("uuid", ComUtils.getUUID(context));
            String des = new Gson().toJson(map);
            LogUtils.e("login", "des-->" + des);
            params = DES.encryptDES(des);
            LogUtils.e("login", "params-->" + params);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("login", "Exception-->" + e.toString());
        }
        OkHttpUtils.get().url(Constans.LOGIN)
//                        .mediaType(MediaType.parse(getString(R.string.media_type)))
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                try {
                    LoginBean loginBean = new Gson().fromJson(body, LoginBean.class);
                    UserVo userVo = loginBean.getStore();
                    userVo.setToken(loginBean.getToken());
                    userVo.setIsFinish(loginBean.getIsFinish());
                    userVo.setPwd(password);
                    userVo.setStoreTelephone(userName);
                    userVo.setLogin(true);
                    Map<String, String> map = new HashMap<>();
                    map.put("storeTelephone", userName);
                    map.put("storePwd", password);
                    map.put("uuid", ComUtils.getUUID(context));
                    map.put("token", userVo.getToken());
                    String des = new Gson().toJson(map);
                    LogUtils.e("login", "setDesParams-->" + des);
                    userVo.setDesParams(DES.encryptDES(des));
                    ComParams.token = userVo.getToken();
                    ComParams.desParams = userVo.getDesParams();
                    ((BaseApplication) getApplication()).setUserVo(userVo);
                    setResult(RESULT_OK);
                    startActivityForResult(new Intent(RegisterActivity.this, ActivityPerfect.class), 0);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast("注册成功，请重新登录");
                finish();
            }
        });
    }

    /**
     * 缓存bean
     *
     * @param userVo
     */
    private void saveUser(UserVo userVo) {
        ((BaseApplication) getApplication()).setUserVo(userVo);
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
