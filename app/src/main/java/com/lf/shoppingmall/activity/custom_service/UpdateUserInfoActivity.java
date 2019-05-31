package com.lf.shoppingmall.activity.custom_service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.login.ActivityPerfect;
import com.lf.shoppingmall.activity.login.ChooseAddresActivity;
import com.lf.shoppingmall.activity.main.MainActivity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑商户信息 todo 点击事件 检测信息
 * Created by Administrator on 2017/9/5.
 */

public class UpdateUserInfoActivity extends BaseActivity {

//    @BindViewView()
    @BindView(R.id.et_id)
    TextView etId;
    @BindView(R.id.et_store_name)
    EditText etStoreName;
    @BindView(R.id.et_head)
    EditText etHead;
    @BindView(R.id.tv_store_icon)
    TextView tvStoreIcon;
    @BindView(R.id.tv_choose_addr)
    TextView tvChooseAddr;
    @BindView(R.id.et_address_more)
    EditText etAddressMore;
    @BindView(R.id.tv_recever_time)
    TextView tvReceverTime;
    @BindView(R.id.tv_louceng)
    TextView tvLouceng;
    @BindView(R.id.rbtn_dianti_have)
    RadioButton rbtnDiantiHave;
    @BindView(R.id.rbtn_dianti_no)
    RadioButton rbtnDiantiNo;
    @BindView(R.id.rgl_dianti)
    RadioGroup rglDianti;

    private final int request_addr = 0;
    private String cityCode;
    private double latitude;
    private double longitude;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_info;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        rglDianti.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (rbtnDiantiHave.isChecked()) {
                    rbtnDiantiHave.setTextColor(ContextCompat.getColor(context, R.color.text_green));
                    rbtnDiantiNo.setTextColor(ContextCompat.getColor(context, R.color.text_norm_6));
                } else {
                    rbtnDiantiHave.setTextColor(ContextCompat.getColor(context, R.color.text_norm_6));
                    rbtnDiantiNo.setTextColor(ContextCompat.getColor(context, R.color.text_green));

                }
            }
        });

        UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
        etId.setText(userVo.getId());
        etStoreName.setText(userVo.getStoreName());
        etHead.setText(userVo.getManagerName());
        tvChooseAddr.setText(userVo.getStoreAddress());
    }

    @Override
    protected Object getTitleText() {
        return "编辑商户信息";
    }

    @OnClick({R.id.tv_store_icon, R.id.tv_choose_addr, R.id.tv_recever_time, R.id.tv_louceng, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_store_icon:
                break;

            case R.id.tv_choose_addr:
                startActivityForResult(new Intent(this, ChooseAddresActivity.class), request_addr);
                break;

            case R.id.tv_recever_time:
                break;

            case R.id.tv_louceng:
                break;

            case R.id.btn_login:
                checkInfo();
                break;
        }
    }

    private void checkInfo() {
        String shopName = etStoreName.getText().toString().trim();
        if (TextUtils.isEmpty(shopName)) {
            showToast(R.string.input_shop_name);
            return;
        }
        if (shopName.length() < 3 || shopName.length() > 20) {
            showToast(R.string.input_shop_name);
            return;
        }

        String head = etHead.getText().toString().trim();
        if (TextUtils.isEmpty(head)) {
            showToast(R.string.head_hint);
            return;
        }
        if (/*head.length() < 3 || */ shopName.length() > 30) {
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

        updateInfo(shopName, head, tvChooseAddr.getText().toString() + addr_more);
    }

    /**
     * 编辑商户信息
     * storeName,managerName,longitude,latitude,storeAdress,cityCode
     */
    private void updateInfo(final String storeName, final String managerName, final String storeAdress) {
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
            LogUtils.e("编辑商户信息", "des-->" + des);
            params = DES.encryptDES(des);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("编辑商户信息", "Exception-->" + e.toString());
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.UPDATE)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("编辑商户信息", "body-->" + body);
                showToast("修改成功");
                userVo.setStoreAddress(storeAdress);
                userVo.setStoreName(storeName);
                userVo.setManagerName(managerName);
                ((BaseApplication) getApplication()).setUserVo(userVo);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case request_addr:
                    String addr = data.getStringExtra(ComParams.ADDR_TEXT);
                    cityCode = data.getStringExtra(ComParams.ADDR_CITY_CODE);
                    latitude = data.getDoubleExtra(ComParams.ADDR_LIN, 0);
                    longitude = data.getDoubleExtra(ComParams.ADDR_LOG, 0);
                    tvChooseAddr.setText(addr);
                    break;
            }
        }
    }
}
