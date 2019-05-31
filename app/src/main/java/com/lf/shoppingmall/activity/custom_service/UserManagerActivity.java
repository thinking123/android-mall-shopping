package com.lf.shoppingmall.activity.custom_service;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.login.DownLoadActivity;
import com.lf.shoppingmall.activity.main.MainActivity;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.utils.ToastUtils;
import com.lr.baseview.utils.cache.CacheUtils;
import com.lr.baseview.widget.AlertDialog;
import com.lr.baseview.widget.CircleImageView;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 帐号管理
 * Created by Administrator on 2017/8/24.
 */

public class UserManagerActivity extends BaseActivity {
//    @Bind(R.id.civ_my_pic)
//    CircleImageView civMyPic;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_account_manager)
    TextView tvAccountManager;
    @Bind(R.id.tv_cache)
    TextView tv_cache;
    @Bind(R.id.tv_version)
    TextView tv_version;

    private String versionName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_manager;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
        tvUserName.setText(userVo.getStoreName());

        tv_cache.setText("当前缓存" + CacheUtils.caculateCacheSize(context));

        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            versionName = info.versionName;
            tv_version.setText("当前版本（v" +versionName + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//        int appcurrentapiVersion = android.os.Build.VERSION.SDK_INT;
//        String sysxinxi = "------------ 系统信息：----------------------------"
//                + "\n- 主板：					"
//                + android.os.Build.BOARD
//                + "\n- Android系统定制商：			"
//                + android.os.Build.BRAND
//                + "\n- CPU指令集：				"
//                + android.os.Build.CPU_ABI
//                + "\n- 设备参数：					"
//                + android.os.Build.DEVICE
//                + "\n- 显示屏参数：				"
//                + android.os.Build.DISPLAY
//                + "\n- 硬件名称：					"
//                + android.os.Build.FINGERPRINT
//                + "\n- 修订版本列表：				"
//                + android.os.Build.ID
//                + "\n- 硬件制造商：				"
//                + android.os.Build.MANUFACTURER
//                + "\n- 版本：					"
//                + android.os.Build.MODEL
//                + "\n- 手机制造商：				"
//                + android.os.Build.PRODUCT
//                + "\n- 描述build的标签：			"
//                + android.os.Build.TAGS
//                + "\n- build的类型：				"
//                + android.os.Build.TYPE
//                + "\n- 当前安卓系统版本为：			"
//                + appcurrentapiVersion;
//        String syskaifaxinxi = "------------ 系统开发信息：----------------------------"
//                + "\n- 当前开发代号：				"
//                + android.os.Build.VERSION.CODENAME
//                + "\n- 源码控制版本号：				"
//                + android.os.Build.VERSION.INCREMENTAL
//                + "\n- 字符串版本：				"
//                + android.os.Build.VERSION.RELEASE
//                + "\n- SDK版本号(int)：			" + android.os.Build.VERSION.SDK_INT;

    @Override
    protected Object getTitleText() {
        return "帐号管理";
    }

    @OnClick({R.id.ll_update_info, R.id.ll_update_pwd, R.id.ll_clear_cache, R.id.ll_update_version, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_update_info:
                startActivityForResult(new Intent(this, UserCountInfoActivity.class), 0);
                break;

            case R.id.ll_update_pwd:
                startActivity(new Intent(this, UpdatePwdActivity.class));
                break;

            case R.id.ll_clear_cache:
                new AlertDialog(context).builder()
                        .setTitle("提示")
                        .setMsg("是否清空缓存")
                        .setNegativeButton("", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clearAppCache();
                            }
                        })
                        .show();
                break;

            case R.id.ll_update_version:
                questionList();
                break;

            case R.id.btn_login:
                new AlertDialog(context).builder()
                        .setTitle("提示")
                        .setMsg("确定退出当前帐号？")
                        .setNegativeButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
                                userVo.setLogin(false);
                                ((BaseApplication) getApplication()).setUserVo(userVo);
                                startActivity(new Intent(UserManagerActivity.this, MainActivity.class)
                                        .putExtra("isOut", true));
                                finish();
                            }
                        })
                        .setPositiveButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();

                break;
        }
    }

    /**
     * 版本更新
     */
    private void questionList() {
        showLoading("");
        OkHttpUtils.get().url(Constans.getVersion)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("版本更新", "body---->" + body);
//                "body":{"isSucceed":"yes","version":"1.0.0"}
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    String version = jsonObject.getString("version");
                    if(!version.equals(versionName)){
                        new android.app.AlertDialog.Builder(UserManagerActivity.this).setTitle("版本更新").setMessage("当前版本为:V" + versionName + "\n最新版本为:V"
                                + 1.1 + "\n是否更新？" + "\n建议WIFI下更新。")
                                .setPositiveButton("更新", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(UserManagerActivity.this, DownLoadActivity.class);
//                        Intent intent = new Intent(SplashActivity.this, DownLoadActivity.class);
                                        startActivityForResult(intent, 0);
                                    }

                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();

                    }else {
                        showToast("当前为最新版本~");
                    }
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

    /**
     * 清除app缓存
     *
     * @param
     */
    public void clearAppCache() {
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    CacheUtils.myclearaAppCache(context);
                    msg.what = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case -1:
                    ToastUtils.showToast(context, "清除失败");
                    tv_cache.setText("当前缓存" + CacheUtils.caculateCacheSize(context));
                    break;
                case 0:
                    ToastUtils.showToast(context, "清除成功");
                    tv_cache.setText("当前缓存" + CacheUtils.caculateCacheSize(context));
                    break;
            }
        }

        ;
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
            }
            finish();
        }
    }
}
