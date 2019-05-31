package com.lf.shoppingmall.http_utils;

import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lf.shoppingmall.activity.login.LoginActivity;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.bean.Body;
import com.lf.shoppingmall.bean.Header;
import com.lf.shoppingmall.bean.Result;
import com.lf.shoppingmall.utils.LogUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/7.
 */

public abstract class MyStringCallback extends StringCallback {

    //为单点登录 登录超时准备
    private BaseActivity activity;

    public MyStringCallback(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(String response) {
        LogUtils.e("response", "response-->" + response);
        if (TextUtils.isEmpty(response)) {
            onResponseError("-100", "-100");
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject != null) {
                JSONObject head = jsonObject.getJSONObject("header");
                Gson gson = new Gson();
                Header header = gson.fromJson(head.toString(), Header.class);

                if (header != null) {
                    String errorCode = head.getString("errorCode");
                    String errorMsg = head.getString("errorMsg");
                    if (!TextUtils.isEmpty(errorCode) && errorCode.equals("200")) {
                        JSONObject body = jsonObject.getJSONObject("body");
                        if (!TextUtils.isEmpty(body.toString())) {
                            LogUtils.e("isSucceed", "Body-->" + body.toString());
                            onSuccess(errorMsg, errorCode, body.toString());
                        } else {
                            onResponseError(errorMsg, errorCode);
                        }

                    }
                    else if (!TextUtils.isEmpty(errorCode) && errorCode.equals("-4") && errorMsg.equals("登录过期")) {
                        if (activity != null) {
                            activity.startActivity(new Intent(activity, LoginActivity.class));
                            activity.showToast("你的帐号在其他地方登录，请重新登录！");
                            activity.finish();
                        }
                    }
                    else if (!TextUtils.isEmpty(errorCode) && errorCode.equals("-6")) {
                        if (activity != null) {
                            activity.showToast("官方已限制下单，请联系客服");
                        }
                    }
                    else if (!TextUtils.isEmpty(errorCode) && errorCode.equals("-7")) {
                        if (activity != null) {
                            activity.showToast("商店未审核，暂时不能下单");
                        }
                }
                    else {
                        onResponseError(errorMsg, "-100");
                    }
                } else {
                    onResponseError("-100", "-100");
                }

            } else {
                onResponseError("-100", "-100");
            }

        } catch (JSONException e){
            e.printStackTrace();
            onResponseError(e.toString(), null);
        }
    }

    @Override
    public void onError(Call call, Exception e) {
        LogUtils.e("onError", "Exception-->" + e.toString());
        onResponseError(e.toString(), null);
    }

    public abstract void onSuccess(String errorCode, String errorMsg, String body) throws JSONException;

    public abstract void onResponseError(String errorMsg, String errorCode);
}
