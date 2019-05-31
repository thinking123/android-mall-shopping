package com.lf.shoppingmall.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.custom_service.ReceiverActivity;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.AllOrder.CatSubmitBean;
import com.lf.shoppingmall.bean.AllOrder.PayInfoBean;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.address.MyAddress;
import com.lf.shoppingmall.bean.address.MyAddressListBean;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.index.GuigeVo;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lf.shoppingmall.zfb.AlipayBean;
import com.lf.shoppingmall.zfb.PayDemoUtil;
import com.lr.baseview.utils.CommonUilts;
import com.lr.baseview.utils.ImageLoadUtils;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.utils.ToastUtils;
import com.lr.baseview.widget.AlertDialog;
import com.lr.baseview.widget.CheckableTextView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 付款界面
 * Created by Administrator on 2017/8/22.
 */

public class OrderPayActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_user_info)
    TextView tvUserInfo;
    @BindView(R.id.tv_send_type)
    TextView tvSendType;
    @BindView(R.id.tv_send_date)
    TextView tvSendDate;
    @BindView(R.id.iv_goods0)
    ImageView ivGoods0;
    @BindView(R.id.iv_goods1)
    ImageView ivGoods1;
    @BindView(R.id.iv_goods2)
    ImageView ivGoods2;
    @BindView(R.id.iv_goods3)
    ImageView ivGoods3;
    @BindView(R.id.rbtn_online)
    RadioButton rbtnOnline;
    @BindView(R.id.rbtn_pay)
    CheckableTextView rbtnPay;
    @BindView(R.id.rgroup_pay_type)
    RadioGroup rgroupPayType;
    @BindView(R.id.tv_goods_cate)
    TextView tvGoodsCate;
    @BindView(R.id.tv_send_youhui)
    TextView tvSendYouhui;
    @BindView(R.id.tv_goods_total_price)
    TextView tvGoodsTotalPrice;
    @BindView(R.id.tv_send_price)
    TextView tvSendPrice;
    @BindView(R.id.tv_send_price_youhui)
    TextView tvSendPriceYouhui;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_goods_size)
    TextView tv_goods_size;
    @BindView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.tv_operation)
    TextView tvOperation;

    private final int RECEIVER = 10;
    private MyAddressListBean listBean;
    private List<GoodsVo> goodsVoList;
    private MyAddress address;
    private float totalPrice;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_pay;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        CatSubmitBean catSubmitBean = (CatSubmitBean) getIntent().getSerializableExtra("CatSubmitBean");
        if (catSubmitBean == null) {
            showToast("信息有误");
            finish();
            return;
        }
        goodsVoList = catSubmitBean.getCarImageInfo().getCarInfo();
        address = catSubmitBean.getAddress();

        tvUserInfo.setText(address.getTrueName() + "\t\t\t\t" + address.getTelPhone()
                + "\n" + address.getAddress());
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        addresslistByStoreId();
        rbtnPay.setChecked(true);
        setGoodsImage();
        totalPrice = catSubmitBean.getTotalPrice();
        tvGoodsTotalPrice.setText("￥" + totalPrice);
//        tvSendPrice.setText("￥" + catSubmitBean.getTotalcashPeldge());
        tvTotalPrice.setText("￥" + catSubmitBean.getTotalPrice());
        tvPayPrice.setText("￥" + catSubmitBean.getTotalcurrentPrice());
    }

    private void setGoodsImage() {
        String ivUrl0 = goodsVoList.get(0).getImage();
        ImageLoadUtils.loadingCommonPic(context, ivUrl0, ivGoods0);
        int size = goodsVoList.size();
        tv_goods_size.setText("共" + size + "件");
        if (size > 1) {
            ivGoods1.setVisibility(View.VISIBLE);
            String ivUrl1 = goodsVoList.get(1).getImage();
            ImageLoadUtils.loadingCommonPic(context, ivUrl1, ivGoods1);
        }
        if (size > 2) {
            ivGoods2.setVisibility(View.VISIBLE);
            String ivUr2 = goodsVoList.get(2).getImage();
            ImageLoadUtils.loadingCommonPic(context, ivUr2, ivGoods2);
        }
        if (size > 3) {
            ivGoods3.setVisibility(View.VISIBLE);
            String ivUrl3 = goodsVoList.get(3).getImage();
            ImageLoadUtils.loadingCommonPic(context, ivUrl3, ivGoods3);
        }
    }

    @Override
    protected Object getTitleText() {
        return "填写订单";
    }

    @OnClick({R.id.ll_addr, R.id.ll_goods, R.id.ll_choose_redbag, R.id.ll_choose_send_youhui, R.id.tv_operation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_addr:
                startActivityForResult(new Intent(this, ReceiverActivity.class)
                        .putExtra("MyAddressListBean", listBean), RECEIVER);
                break;

            case R.id.ll_goods:
                startActivity(new Intent(this, OrderPayListActivity.class)
                        .putExtra("goodsVoList", (ArrayList) goodsVoList));
                break;

            case R.id.ll_choose_redbag:
                break;
            case R.id.ll_choose_send_youhui:
                break;

            case R.id.tv_operation:
//                if (rbtnOnline.isChecked()) {
//                    startActivityForResult(new Intent(this, PayTypeActivity.class), 11);
////                    getOrderInfo();
//                } else {
                    orderAPIbuildOrder();
//                }
                break;
        }
    }

    private void alipay() {
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setTitle("四季鲜订单");
        alipayBean.setPrice("0.01");
        alipayBean.setPayid("00000000001");
//        alipayBean.setNotifyUrl(APIConstant.ALIPAY_NOTIFY_APP);
        PayDemoUtil payDemoUtil = new PayDemoUtil(context, alipayBean, null);
        payDemoUtil.pay(new PayDemoUtil.PayOkListener() {
            @Override
            public void onPaySuccess() {
                orderAPIbuildOrder();
            }
        }, new PayDemoUtil.PayFailureListener() {
            @Override
            public void onPayFailureL() {
                ToastUtils.showToast(context, "支付宝支付失败");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECEIVER) {
            if (resultCode == RESULT_OK) {//收件人
                if (data != null) {
                    address = (MyAddress) data.getSerializableExtra("MyAddress");
                    tvUserInfo.setText(address.getTrueName() + "\t\t\t\t" + address.getTelPhone()
                            + "\n" + address.getAddress());
                } else {
                    addresslistByStoreId();
                }
            }

        } else if (requestCode == 11 && resultCode == RESULT_OK) {//支付选择
            boolean isAppliy = data.getBooleanExtra("isAppliy", true);
            if (isAppliy) {
                getOrderInfo();
            } else {
                pay_wechat();
            }
        } else if (resultCode == RESULT_OK) {//weixin
            if (data != null) {
                int code = data.getIntExtra("errCode", -1);
                switch (code) {
                    case 0:
                        ToastUtils.showToast(context, "支付成功");
                        orderAPIbuildOrder();
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
            map.put("biz", String.valueOf(totalPrice));
            String des = new Gson().toJson(map);
            LogUtils.e("微信支付", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }

        showLoading("");
        OkHttpUtils.get().url(Constans.pay_wechat)
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
                LogUtils.e("微信支付", "response---->" + response);
                try {
                    JSONObject json = new JSONObject(response);
                    IWXAPI api = WXAPIFactory.createWXAPI(OrderPayActivity.this, Constans.APP_ID);
                    PayReq req = new PayReq();
                    req.appId = json.getString("appid");
                    req.partnerId = json.getString("partnerid");
                    req.prepayId = json.getString("prepayid");
                    req.nonceStr = json.getString("noncestr");
                    req.timeStamp = json.getString("timestamp");
                    req.packageValue = json.getString("package");
                    req.sign = json.getString("sign");
                    req.extData = "app data"; // optional
                    api.sendReq(req);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
            map.put("biz", String.valueOf(totalPrice));
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
//                    PayInfoBean infoBean = new Gson().fromJson(response, PayInfoBean.class);
                    String order = jsonObject.getString("response");
                    LogUtils.e("支付宝信息", "order---->" + order);
                    PayDemoUtil payDemoUtil = new PayDemoUtil(context, null, order);
                    payDemoUtil.pay(new PayDemoUtil.PayOkListener() {
                        @Override
                        public void onPaySuccess() {
                            orderAPIbuildOrder();
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
     * 收获人列表
     */
    private void addresslistByStoreId() {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
            map.put("token", userVo.getToken());
            String des = new Gson().toJson(map);
            LogUtils.e("收获人列表", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.addresslistByStoreId)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("收获人列表", "body---->" + body);
                listBean = new Gson().fromJson(body, MyAddressListBean.class);
                if (listBean != null && listBean.getStoreAddressList() != null && !listBean.getStoreAddressList().isEmpty()) {
                    boolean isNo = true;
                    for (MyAddress address : listBean.getStoreAddressList()) {
                        if (address.getAddrDefault().equals("1")) {
                            OrderPayActivity.this.address = address;
                            isNo = false;
                            tvUserInfo.setText(address.getTrueName() + "\t\t\t\t" + address.getTelPhone()
                                    + "\n" + address.getAddress());
                        }
                    }
                    if (isNo) {
                        tvUserInfo.setText("暂无地址信息\n点击添加地址");
                    }
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
     * 下订单接口
     */
    private void orderAPIbuildOrder() {
        if (address == null) {
            showToast("当前暂无默认收货地址，请添加或设置！");
            return;
        }
        String params = null;
        try {
            JSONArray jsonArray1 = new JSONArray();
            LogUtils.e("下订单接口", "goodsVoList-->" + goodsVoList.toString());
            for (GoodsVo goodsVo : goodsVoList) {
                JSONArray jsonArray = new JSONArray();
                for (GuigeVo guigeVo : goodsVo.getGoodsSpec()) {
                    JSONObject guige = new JSONObject();
                    guige.put("id", guigeVo.getId());
                    guige.put("carGoodNum", guigeVo.getCarGoodNum());
                    jsonArray.put(guige);
                }
                JSONObject goods = new JSONObject();
                goods.put("id", goodsVo.getId());
                goods.put("goodsSpec", jsonArray);
                jsonArray1.put(goods);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("addressId", address.getAddressId());
            jsonObject.put("carImageInfo", jsonArray1);
            UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
            jsonObject.put("token", userVo.getToken());
            jsonObject.put("orederType", 0);
            String des = jsonObject.toString();
            LogUtils.e("下订单接口", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            LogUtils.e("下订单接口", "Exception-->" + e.toString());
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.orderAPIbuildOrder)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("下订单接口", "body---->" + body);
//                showToast("下单成功，请保持电话畅通，注意收货");
                startActivity(new Intent(OrderPayActivity.this, OrderSuccessActivity.class));
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
    public void onBackPressed() {
        new AlertDialog(context).builder()
                .setTitle("您还没有下单呢")
                .setMsg("确定要离开吗？")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrderPayActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .show();
    }
}
