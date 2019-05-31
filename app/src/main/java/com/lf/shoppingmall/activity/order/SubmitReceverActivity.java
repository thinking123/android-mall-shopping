package com.lf.shoppingmall.activity.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.other.MyOrder;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lf.shoppingmall.zfb.PayDemoUtil;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.utils.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 确认收货界面
 * Created by Administrator on 2017/9/19.
 */

public class SubmitReceverActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.rbtn_appliy)
    RadioButton rbtnAppliy;
    @BindView(R.id.rbtn_wechat)
    RadioButton rbtnWechat;
    @BindView(R.id.rgroup_pay_type)
    RadioGroup rgroupPayType;
    @BindView(R.id.btn_appliy)
    Button btnAppliy;

    private float totalPrice;
    private String id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_submit_recever;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        MyOrder order = (MyOrder) getIntent().getSerializableExtra("MyOrder");
        if (order == null) {
            showToast("信息有误");
            finish();
            return;
        }

//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(ComParams.BRONDCASE_PAY_WECHAT);
//        registerReceiver(broadcastReceiver, intentFilter);


        id = order.getId();
        tvNumber.setText(order.getOrderId());
        totalPrice = order.getTotalPrice();
        tvPrice.setText("￥" + totalPrice);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.e("微信支付", "action-->" + action);
            if (action.equals(ComParams.BRONDCASE_PAY_WECHAT)) {
                int code = intent.getIntExtra("errCode", -1);
                LogUtils.e("微信支付", "code-->" + code);
                switch (code) {
                    case 0:
                        ToastUtils.showToast(context, "支付成功");
                        finishOrder(id);
                        break;
                    case -1:
                        ToastUtils.showToast(context, "支付失败");
                        break;
                    case -2:
                        ToastUtils.showToast(context, "支付取消");
                        break;
                    default:
                        ToastUtils.showToast(context, "支付失败");
                        break;
                }
            }
        }
    };

    @Override
    protected Object getTitleText() {
        return "确认收货";
    }

    @OnClick({R.id.iv_back, R.id.btn_appliy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_appliy:
                if (rbtnAppliy.isChecked()) {
                    getOrderInfo();
                } else {
                    pay_wechat();
                }
                break;
        }
    }

    /**
     * 微信支付
     */
    private void pay_wechat() {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
            map.put("token", userVo.getToken());
            map.put("orderNum", id);
            map.put("totalPrice", String.valueOf(totalPrice));
            String des = new Gson().toJson(map);
            LogUtils.e("微信支付", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }

        showLoading("");
        OkHttpUtils.get().url(Constans.pay_wechat)
                .addParams("params", params)
                .build().execute(new MyStringCallback(getActivity()) {
                                     @Override
                                     public void onSuccess(String errorCode, String errorMsg, String body) throws JSONException {

                                         dissmissLoading();
                                         LogUtils.e("微信支付", "body---->" + body);
                                         try {
                                             JSONObject json = new JSONObject(body);
                                             IWXAPI api = WXAPIFactory.createWXAPI(SubmitReceverActivity.this, Constans.APP_ID);
                                             PayReq req = new PayReq();
                                             req.appId = json.getString("appid");
                                             req.partnerId = json.getString("partnerid");
                                             req.prepayId = json.getString("prepayid");
                                             req.nonceStr = json.getString("noncestr");
                                             req.timeStamp = json.getString("timestamp");
                                             req.packageValue = json.getString("package");
                                             req.sign = json.getString("sign");
                                             req.extData = "app data"; // optional
                                             ComParams.PAY_ID = id;
                                             api.sendReq(req);
                                             finish();
                                         } catch (JSONException e) {
                                             e.printStackTrace();
                                         }
                                     }

                                     @Override
                                     public void onResponseError(String errorMsg, String errorCode) {
                                         dissmissLoading();
                                         if (errorCode.equals("-100")) {
                                             showToast("该订单已支付，无须重复支付！");
                                         } else {
                                             showToast(errorMsg);
                                         }
                                     }
                                 }
        );
    }

    /**
     * 支付宝信息
     */
    private void getOrderInfo() {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
            map.put("token", userVo.getToken());
            map.put("orderNum", id);
            map.put("totalPrice", String.valueOf(totalPrice));
            String des = new Gson().toJson(map);
            LogUtils.e("支付宝信息", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }

        showLoading("");
        OkHttpUtils.get().url(Constans.getOrder)
                .addParams("params", params)
                .build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e) {
                dissmissLoading();
                showToast(e.toString());
            }

            @Override
            public void onResponse(String response) {
                dissmissLoading();
                LogUtils.e("支付宝信息", "response---->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    final String order = jsonObject.getString("body");
                    LogUtils.e("支付宝信息", "order---->" + order);
                    PayDemoUtil payDemoUtil = new PayDemoUtil(context, null, order);
                    payDemoUtil.pay(new PayDemoUtil.PayOkListener() {
                        @Override
                        public void onPaySuccess() {
                            finishOrder(id);
                        }
                    }, new PayDemoUtil.PayFailureListener() {
                        @Override
                        public void onPayFailureL() {
                            ToastUtils.showToast(context, "支付宝支付失败");
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 确认收货
     *
     * @param id
     */
    private void finishOrder(String id) {
        UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("id", id);
            String des = new Gson().toJson(map);
            LogUtils.e("确认收货", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.finishOrder)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("确认收货", "body-->" + body);
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
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(broadcastReceiver);
//        broadcastReceiver = null;
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        LogUtils.e("微信支付", "requestCode---->" + requestCode);
//        if (resultCode == RESULT_OK) {//weixin
//            if (data != null) {
//                int code = data.getIntExtra("errCode", -1);
//                switch (code) {
//                    case 0:
//                        ToastUtils.showToast(context, "支付成功");
//                        finishOrder(id);
//                        break;
//                    case -1:
//                        ToastUtils.showToast(context, "支付失败");
//                        break;
//                    case -2:
//                        ToastUtils.showToast(context, "支付取消");
//                        break;
//                    default:
//                        ToastUtils.showToast(context, "支付失败");
//                        break;
//                }
//            }
//        }
//    }
}
