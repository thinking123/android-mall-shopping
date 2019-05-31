package com.lf.shoppingmall.wxapi;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
//        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Constans.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        LogUtils.e(TAG, "onPayFinish, req = " + req.toString());
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtils.e("微信支付", "resp------->" + resp.toString());
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = resp.errCode;
            switch (code) {
                case 0:
                    ToastUtils.showToast(context, "支付成功");
                    finishOrder(ComParams.PAY_ID);
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
//			setResult(RESULT_OK, new Intent().putExtra("errCode",  resp.errCode));
//			finish();

//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
//		}
    }

    private Dialog mydialog;

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

        LayoutInflater inflater = this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_loading, null);
        TextView tv = (TextView) layout.findViewById(R.id.txt_text);
        ImageView progressBar1 = (ImageView) layout.findViewById(R.id.progressBar1);
        Glide.with(this).load(R.drawable.gif_loading).diskCacheStrategy(DiskCacheStrategy.ALL).into(progressBar1);
        mydialog = new Dialog(this, R.style.loadingDialog);
        mydialog.setContentView(layout);
        mydialog.setCanceledOnTouchOutside(false);
        if (!mydialog.isShowing()) {
            mydialog.show();
        }

        mydialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // 点击非主页（ActivityMain）界面的返回键，都结束当前Activity
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dissmissLoading();
                    finish();
                }
                return true;
            }
        });


        OkHttpUtils.get().url(Constans.finishOrder)
                .addParams("params", params)
                .build().execute(new MyStringCallback(null) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                LogUtils.e("确认收货", "body-->" + body);
                dissmissLoading();
                Intent intent = new Intent();
                intent.setAction(ComParams.BRONDCASE_PAY_WECHAT);
                sendBroadcast(intent);
                finish();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                ToastUtils.showToast(context, errorMsg);
                finish();
            }
        });
    }

    protected void dissmissLoading() {
        try {
            if (null != mydialog && mydialog.isShowing()) {
                mydialog.dismiss();
                mydialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}