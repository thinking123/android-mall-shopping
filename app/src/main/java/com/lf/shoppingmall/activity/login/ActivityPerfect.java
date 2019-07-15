package com.lf.shoppingmall.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lf.shoppingmall.R;
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
import com.lr.baseview.widget.AlertDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 完善信息界面
 * Created by Administrator on 2017/8/7.
 */

public class ActivityPerfect extends BaseActivity {

    @BindView(R.id.et_shop_name)
    EditText etShopName;
    @BindView(R.id.et_shop_head)
    EditText etShopHead;
    @BindView(R.id.tv_choose_addr)
    TextView tvChooseAddr;
    @BindView(R.id.et_address_more)
    EditText etAddressMore;
    @BindView(R.id.et_invitation)
    EditText etInvitation;

    private int type;
    private boolean isFirst = true;
    private String cityCode;
    private String cityName;
    private double latitude;
    private double longitude;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_prefect;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand);
        ButterKnife.bind(this);

        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    protected Object getTitleText() {
        return getString(R.string.choose_shop);
    }

    @Override
    protected void setTitleBarStatus(ImageView iv_back, TextView tv_title, TextView tv_right, ImageView iv_right) {
        super.setTitleBarStatus(iv_back, tv_title, tv_right, iv_right);
        iv_back.setVisibility(View.INVISIBLE);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(getString(R.string.help));
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityPerfect.this, TermActivity.class).putExtra("type", 1));
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirst && type == 1) {
            isFirst = false;
            new AlertDialog(context).builder()
                    .setTitle(getString(R.string.register_agin))
                    .setMsg(getString(R.string.register_agin_msg))
                    .setNegativeButton(getString(R.string.exit), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .setPositiveButton(getString(R.string.agin), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            String addr = data.getStringExtra(ComParams.ADDR_TEXT);
            cityCode = data.getStringExtra(ComParams.ADDR_CITY_CODE);
            cityName = data.getStringExtra(ComParams.ADDR_CITY);
            latitude = data.getDoubleExtra(ComParams.ADDR_LIN, 0);
            longitude = data.getDoubleExtra(ComParams.ADDR_LOG, 0);
            tvChooseAddr.setText(addr);
        }
    }

    @Override
    public void onBackPressed() {
        //默认不可退出
//        super.onBackPressed();
    }

    @OnClick({R.id.tv_choose_addr, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_addr:
                startActivityForResult(new Intent(this, ChooseAddresActivity.class), 0);
                break;

            case R.id.btn_login:
                checkInfo();
                break;
        }
    }

    private void checkInfo() {
        String shopName = etShopName.getText().toString().trim();
        if (TextUtils.isEmpty(shopName)) {
            showToast(R.string.input_shop_name);
            return;
        }
        if (/*shopName.length() < 3 || */shopName.length() > 30) {
            showToast(R.string.input_shop_name);
            return;
        }

        String head = etShopHead.getText().toString().trim();
        if (TextUtils.isEmpty(head)) {
            showToast(R.string.head_hint);
            return;
        }
        if (/*ead.length() < 3 ||*/ shopName.length() > 30) {
            showToast(R.string.head_hint);
            return;
        }

        String address = tvChooseAddr.getText().toString();
        if (TextUtils.isEmpty(address)) {
            showToast(R.string.address_choose);
            return;
        }

        String addr_more = etAddressMore.getText().toString().trim();
        if (TextUtils.isEmpty(addr_more)) {
            showToast(R.string.address_more);
            return;
        }
        if (/*addr_more.length() < 3 || */addr_more.length() > 30) {
            showToast(R.string.address_more);
            return;
        }

        String invitation = etInvitation.getText().toString().trim();

        updateInfo(shopName, head, tvChooseAddr.getText().toString() + addr_more, invitation);
    }

    /**
     * 完善信息
     * storeName,managerName,longitude,latitude,storeAdress,cityCode
     */
    private void updateInfo(String storeName, String managerName, String storeAdress, String invitation) {
        final UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("storeName", storeName);
            map.put("managerName", managerName);
            map.put("storeAddress", storeAdress);
            map.put("longitude", longitude + "");
            map.put("latitude", latitude + "");
            map.put("adCode", cityCode);
            String des = new Gson().toJson(map);
            LogUtils.e("完善信息", "des-->" + des);
            params = DES.encryptDES(des);
            LogUtils.e("完善信息", "params-->" + params);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("完善信息", "Exception-->" + e.toString());
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.UPDATE)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                userVo.setIsFinish(ComParams.LOGIN_ISFINISH);
                userVo.setCityName(cityName);
                userVo.setCityCode(cityCode);
                ((BaseApplication) getApplication()).setUserVo(userVo);
                startActivity(new Intent(ActivityPerfect.this, MainActivity.class));
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
