package com.lf.shoppingmall.activity.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.custom_service.UserManagerActivity;
import com.lf.shoppingmall.activity.login.ActivityPerfect;
import com.lf.shoppingmall.activity.login.DownLoadActivity;
import com.lf.shoppingmall.activity.login.LoginActivity;
import com.lf.shoppingmall.activity.login.WelcomeActivity;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.base.BaseFragment;
import com.lf.shoppingmall.base.MyPagerAdapter;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.index.ProductionInfoVo;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.ComUtils;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.common.LoginSucListener;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.utils.SharePreferenceUtil;
import com.lr.baseview.utils.ToastUtils;
import com.lr.baseview.widget.AlertDialog;
import com.lr.baseview.widget.CustomViewPager;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.home_vp_content)
    CustomViewPager homeVpContent;
    @Bind(R.id.iv1)
    ImageView iv1;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.iv2)
    ImageView iv2;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.iv3)
    ImageView iv3;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.iv4)
    ImageView iv4;
    @Bind(R.id.tv4)
    TextView tv4;
    @Bind(R.id.iv5)
    ImageView iv5;
    @Bind(R.id.tv5)
    TextView tv5;
    @Bind(R.id.tv_notice_num)
    TextView tvNoticeNum;

    List<BaseFragment> fragments;
    private final int VP_PAGE_LIMIT = 5;
    private int currunt = 0;
    private long firstTime;//点击返回键第一次时间
    private final int REQUEST_WELCOME = 0;//欢迎界面
    private final int REQUEST_LOGIN = REQUEST_WELCOME + 1;//登录界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            };
            if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, 0);
            }
        }
        welcomeImageList();
        initVP();
        setShoppingCatNum();
        questionList();
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

            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                } else {
                    new AlertDialog(context).builder()
                            .setTitle("提示")
                            .setMsg("当前应用必须打开定位方可使用")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    back();
                                }
                            })
                            .show();
                }
                break;

            default:
                break;
        }
    }

    private void initVP() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new AllOrderFragment());
        fragments.add(CommonOrderFragment.Instanst(0, null));
        fragments.add(new ShopCatFragemnt());
        fragments.add(new MineFragment());
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, null);
        homeVpContent.setAdapter(pagerAdapter);
        homeVpContent.setPagingEnabled(false);
        homeVpContent.setOffscreenPageLimit(VP_PAGE_LIMIT);
    }

    @Override
    protected Object getTitleText() {
        return null;
    }

    @OnClick({R.id.ll_tab1, R.id.ll_tab2, R.id.ll_tab3, R.id.ll_tab4, R.id.ll_tab5})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ll_tab1:
                currunt = 0;
                ComParams.main_current_page = currunt;
                ((HomeFragment) fragments.get(0)).getIndex(1);
                setTabStatus();
                break;

            case R.id.ll_tab2:
                currunt = 1;
                ComParams.main_current_page = currunt;
                ((AllOrderFragment) fragments.get(1)).onRefresh();
                setTabStatus();
                break;

            case R.id.ll_tab3:
                currunt = 2;
                ComParams.main_current_page = currunt;
                ((CommonOrderFragment) fragments.get(2)).onRefresh();
                setTabStatus();
                break;

            case R.id.ll_tab4:
                currunt = 3;
                ComParams.main_current_page = currunt;
                ((ShopCatFragemnt) fragments.get(3)).onRefresh();
                setTabStatus();
                break;
            case R.id.ll_tab5:
                currunt = 4;
                ComParams.main_current_page = currunt;
                setTabStatus();
                break;
        }
    }

    private void setTabStatus() {
        iv1.setImageResource(R.mipmap.listpage_icon_home_formal);
        tv1.setTextColor(ContextCompat.getColor(context, R.color.text_norm_8));
        iv2.setImageResource(R.mipmap.listpage_icon_detail_formal);
        tv2.setTextColor(ContextCompat.getColor(context, R.color.text_norm_8));
        iv3.setImageResource(R.mipmap.listpage_icon_recommend_formal);
        tv3.setTextColor(ContextCompat.getColor(context, R.color.text_norm_8));
        iv4.setImageResource(R.mipmap.listpage_icon_cart_formal);
        tv4.setTextColor(ContextCompat.getColor(context, R.color.text_norm_8));
        iv5.setImageResource(R.mipmap.listpage_icon_person_formal);
        tv5.setTextColor(ContextCompat.getColor(context, R.color.text_norm_8));
        switch (currunt) {
            case 0:
                iv1.setImageResource(R.mipmap.listpage_icon_home_pressed);
                tv1.setTextColor(ContextCompat.getColor(context, R.color.text_green));
                break;

            case 1:
                iv2.setImageResource(R.mipmap.listpage_icon_detail_pressed);
                tv2.setTextColor(ContextCompat.getColor(context, R.color.text_green));
                break;

            case 2:
                iv3.setImageResource(R.mipmap.listpage_icon_recommend_pressed);
                tv3.setTextColor(ContextCompat.getColor(context, R.color.text_green));
                break;

            case 3:
                iv4.setImageResource(R.mipmap.listpage_icon_cart_pressed);
                tv4.setTextColor(ContextCompat.getColor(context, R.color.text_green));
                break;

            case 4:
                iv5.setImageResource(R.mipmap.listpage_icon_person_pressed);
                tv5.setTextColor(ContextCompat.getColor(context, R.color.text_green));
                break;
        }
        homeVpContent.setCurrentItem(currunt, false);
    }

    /**
     * 设置当前fargment界面
     *
     * @param currntPager
     */
    public void setCurrntPager(int currntPager) {
        this.currunt = currntPager;
        ComParams.main_current_page = currunt;
        setTabStatus();
    }

    public void setShoppingCatNum() {
        if (ComParams.shop_cat_num > 0) {
            tvNoticeNum.setText(String.valueOf(ComParams.shop_cat_num));
            tvNoticeNum.setVisibility(View.VISIBLE);
        } else {
            tvNoticeNum.setVisibility(View.GONE);
        }
//        ((ShopCatFragemnt) fragments.get(3)).onRefresh();
    }

    public void setCommonOrder(ArrayList<ProductionInfoVo> ProductionInfoList) {
        ((CommonOrderFragment) fragments.get(2)).setOrderList(ProductionInfoList);
    }

    public void refreshCommonOrder() {
        ((HomeFragment) fragments.get(0)).refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setShoppingCatNum();
        if (ComParams.main_current_page != currunt) {
            currunt = ComParams.main_current_page;
            setTabStatus();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        fragments.get(currunt).onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_WELCOME:
                if (resultCode == RESULT_OK) {
                    finish();
                }
                break;

            case REQUEST_LOGIN:
                if (resultCode == RESULT_OK) {
                    finish();
                }
                break;

            case ComParams.RESUTLT_SHOP_CAT:
//                setShoppingCatNum();
                break;

            default:
                break;
        }
        setShoppingCatNum();
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - firstTime > 2000) {
            ToastUtils.showToast(this, "再按一次退出");
            firstTime = currentTime;

        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //更新
        boolean isUpdateVersion = intent.getBooleanExtra("update", false);
        if (isUpdateVersion) {
            ComParams.login_status = 0;
            ComParams.main_current_page = 0;
            finish();
            return;
        }
        //退出登录
        boolean isOut = intent.getBooleanExtra("isOut", false);
        if (isOut) {
            ComParams.login_status = 0;
            ComParams.main_current_page = 0;
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        finish();
    }

    private String versionName = "";
    /**
     * 版本更新
     */
    private void questionList() {
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            versionName = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.getVersion)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("版本更新", "body---->" + body);
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    String version = jsonObject.getString("version");
                    if (!version.equals(versionName)) {
                        new android.app.AlertDialog.Builder(MainActivity.this).setTitle("版本更新").setMessage("当前版本为:V" + versionName + "\n最新版本为:V"
                                + 1.1 + "\n是否更新？" + "\n建议WIFI下更新。")
                                .setPositiveButton("更新", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(MainActivity.this, DownLoadActivity.class);
//                        Intent intent = new Intent(SplashActivity.this, DownLoadActivity.class);
                                        startActivityForResult(intent, 0);
                                    }

                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();

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
}
