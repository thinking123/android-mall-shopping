package com.lf.shoppingmall.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.login.ActivityPerfect;
import com.lf.shoppingmall.activity.login.LoginActivity;
import com.lf.shoppingmall.activity.login.WelcomeActivity;
import com.lf.shoppingmall.activity.main.MainActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.other.StartBean;
import com.lf.shoppingmall.bean.other.WelcomeBean;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.ImageLoadUtils;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.utils.SharePreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 启动界面
 *
 * @author liushubao
 *         Created by admin on 2017/3/21.
 */

public class SplashActivity extends BaseActivity {

    @Bind(R.id.iv_start)
    ImageView iv_start;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.rl_root)
    RelativeLayout rl_root;

    private StartBean start;
    private WelcomeBean welcomeBean;
    private boolean isTime = false;
    private boolean frist_goin;
    private boolean frist_goin_loading;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand);// 设置图片的沉浸式
        ButterKnife.bind(this);

        startImageList();
        SharePreferenceUtil spUtil = new SharePreferenceUtil(context);
         frist_goin = spUtil.getBoolean(context, ComParams.FRIST_GOIN, false);
        if(!frist_goin){
            welcomeImageList();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isTime = true;
                if (start == null){
                    showLoading("");
                }else {
                    Glide.with(context)
                            .load(start.getStartImg())
                            .asBitmap()
                            .placeholder(R.mipmap.splash_page)
                            .error(R.mipmap.splash_page)
                            .into(iv_start);
                    startTimer();
                }
            }
        }, ComParams.STARTUP_TIME);

    }

    @Override
    protected CharSequence getTitleText() {
        return null;
    }


    /**
     * 启动页图片
     */
    private void startImageList() {
        OkHttpUtils.get().url(Constans.startImageList)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("启动页图片", "body-->" + body);
                start = new Gson().fromJson(body, StartBean.class);
                if (isTime){//启动页时间到了
                    Glide.with(context)
                            .load(start.getStartImg())
                            .asBitmap()
                            .placeholder(R.mipmap.splash_page)
                            .error(R.mipmap.splash_page)
                            .into(iv_start);
                    startTimer();
                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 欢迎页图片
     */
    private void welcomeImageList() {
        OkHttpUtils.get().url(Constans.welcomeImageList)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("欢迎页图片", "body-->" + body);
                welcomeBean = new Gson().fromJson(body, WelcomeBean.class);
                if (frist_goin_loading) {
                    startActivity(new Intent(context, WelcomeActivity.class)
                            .putExtra("WelcomeBean", welcomeBean));
                    finish();
                }
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
        tv_time.setVisibility(View.VISIBLE);
        tv_time.setText(time + "秒");
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
    private final int TIME = 3;
    private int time;
    /**
     * 倒计时handler
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ENABLE:
                    //检查是否跳转欢迎界面
                    if (!frist_goin ) {
                        if (welcomeBean != null){
                            startActivity(new Intent(context, WelcomeActivity.class)
                                    .putExtra("WelcomeBean", welcomeBean));
                            finish();
                        }else {
                            frist_goin_loading = true;
                            showLoading("");
                        }
                        return;
                    }

                    UserVo userVo = ((BaseApplication)getApplication()).getUserVo();
                    ComParams.login_status = userVo == null ? 0 : 1;
                    Intent intent = new Intent();
                    if (userVo != null && userVo.isLogin()){
                        if (!TextUtils.isEmpty(userVo.getIsFinish()) && !userVo.getIsFinish().equals(ComParams.LOGIN_ISFINISH)) {
                            intent.setClass(context, ActivityPerfect.class);
                            intent.putExtra("type", "1");
                        }else {
                            intent.setClass(context, MainActivity.class);
                        }
                    }else {
                        intent.setClass(context, LoginActivity.class);
                    }
                    context.startActivity(intent);
                    back();
                    break;
                case CURRUNT:
                    tv_time.setText(time + "秒");
                    break;

                default:
                    break;
            }
        }
    };
}
