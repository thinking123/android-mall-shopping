package com.lf.shoppingmall.activity.login;

import android.content.Intent;
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
import com.lr.baseview.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * todo 登录接口 获取短信 获取语音 登录信息返回完善信息状态
 * 登录界面
 * Created by Administrator on 2017/8/3.
 */

public class LoginActivity extends BaseActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.box_ped_clear)
    CheckBox boxPedClear;
    @BindView(R.id.tv_get_verificode)
    TextView tvGetVerificode;
    @BindView(R.id.view_line)
    View view_line;
    @BindView(R.id.tv_get_voice)
    TextView tv_get_voice;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_pwd)
    TextView tv_pwd;
    @BindView(R.id.tv_forget_password)
    TextView tv_forget_password;
    @BindView(R.id.tv_login_type)
    TextView tv_login_type;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private long firstTime;//点击返回键第一次时间
    private final int REQUEST_REG = 0;
    private boolean isVerification = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        btnLogin.setEnabled(false);
        etPhone.addTextChangedListener(this);
        etPwd.addTextChangedListener(this);
        boxPedClear.setOnCheckedChangeListener(this);
    }

    @Override
    protected Object getTitleText() {
        return getResources().getString(R.string.hint_login);
    }

    @Override
    protected void setTitleBarStatus(ImageView iv_back, TextView tv_title, TextView tv_right, ImageView iv_right) {
        super.setTitleBarStatus(iv_back, tv_title, tv_right, iv_right);
        iv_back.setImageResource(R.mipmap.btn_back);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(getResources().getString(R.string.hint_register));
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), REQUEST_REG);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_clear_latest_phone, R.id.btn_login, R.id.tv_login_type, R.id.tv_get_voice, R.id.tv_forget_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_clear_latest_phone:
                etPhone.setText("");
                break;

            case R.id.btn_login:
                checkLoginInfo();
                break;

            case R.id.tv_login_type:
                isVerification = !isVerification;
                setLoginStatus();
                break;

            case R.id.tv_get_voice:
                startActivity(new Intent(this, ActivityForgetPwd.class));
//                String userName = etPhone.getText().toString();
//                if (TextUtils.isEmpty(userName)) {
//                    showToast(R.string.please_login_logo_username);
//                    return;
//                }
//                if (userName.length() < 11 || userName.length() > 16) {
//                    showToast(R.string.please_login_logo_username_format);
//                    return;
//                }
//                tv_get_voice.setVisibility(View.VISIBLE);
//                startTimer();
                break;

            case R.id.tv_forget_password:
                startActivity(new Intent(this, ManagerCenterActivity.class));
                break;
        }
    }

    /**
     * 设置登录方式
     */
    private void setLoginStatus() {
        if (isVerification) {
            view_line.setVisibility(View.VISIBLE);
            tvGetVerificode.setVisibility(View.VISIBLE);
            tv_name.setText(getString(R.string.phone_number));
            tv_pwd.setText(getString(R.string.verification_code));
            boxPedClear.setVisibility(View.GONE);
            etPwd.setHint(getString(R.string.hint_verification_code));
            etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            tv_login_type.setText(getString(R.string.login_type_number));
            tv_forget_password.setVisibility(View.GONE);


        } else {
            view_line.setVisibility(View.GONE);
            tvGetVerificode.setVisibility(View.GONE);
            tv_get_voice.setVisibility(View.GONE);
            tv_name.setText(getString(R.string.hint_user_name));
            tv_pwd.setText(getString(R.string.hint_pwd));
            boxPedClear.setVisibility(View.VISIBLE);
            etPwd.setHint(getString(R.string.input_use_pwd));
            if (boxPedClear.isChecked()) {
                etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            tv_forget_password.setVisibility(View.VISIBLE);

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

        final String password = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            showToast(!isVerification ? R.string.please_input_use_pwd : R.string.please_input_verification_code_format);
            return;
        }
        if (isVerification) {
            if (password.length() != ComParams.VERIFICATION_LENGHT) {
                showToast(R.string.please_input_verification_code_format);
                return;
            }
        } else {
            if (password.length() < ComParams.PWD_SHORT || password.length() > ComParams.PWD_LONG) {
                showToast(R.string.please_input_use_pwd_format);
                return;
            }
        }
        login(userName, password);

    }

    private void login(final String userName, final String password) {
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
        showLoading("");
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
                    String isFinish = userVo.getIsFinish();
                    if (TextUtils.isEmpty(isFinish) || !isFinish.equals(ComParams.LOGIN_ISFINISH)) {
                        startActivity(new Intent(LoginActivity.this, ActivityPerfect.class).putExtra("type", "1"));
                    }else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
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

    /**
     * 登录按钮状态
     */
    private void checkLoginBtnStatus() {
        if (!TextUtils.isEmpty(etPhone.getText().toString().trim()) && !TextUtils.isEmpty(etPwd.getText().toString().trim())) {
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundResource(R.drawable.bg_green_corner5);
            btnLogin.setTextColor(ContextCompat.getColor(context, R.color.white));
        }else {
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundResource(R.drawable.bg_corner8_grey);
            btnLogin.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
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
//
//    @Override
//    public void onBackPressed() {
//
//        super.onBackPressed();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REG && resultCode == RESULT_OK) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        long currentTime = System.currentTimeMillis();
        if (currentTime - firstTime > 2000) {
            ToastUtils.showToast(this, "再按一次退出");
            firstTime = currentTime;

        } else {
            super.onBackPressed();
        }
    }
}
