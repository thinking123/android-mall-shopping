package com.lf.shoppingmall.http_utils;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 处理返回值（okhttp ）
 *
 * @author xiezhenyu 2016/5/27.
 */
public class ResponseUtil {
//    /**
//     * @param response 待解析的json
//     * @param tClass   javabean  的class
//     * @param <T>      javabean
//     * @return 用ArrayList去接收解析后的结果  如Result<ArrayList<XXXbean> xxx  =ResponseUtil.parserArray(string,XXXbean.class);
//     * @throws MYERROPNOTZEROException 返回值error不为0抛出的自定义异常   getmsg得到的是服务器返回的msg
//     * @throws JSONException
//     */
//    public static <T> Result parserArray(final String response, Class<T> tClass) /*throws MYERROPNOTZEROException, JSONException*/ {
//        ArrayList<T> tArrayList = new ArrayList<>();
//        JSONObject jsonObject = null;
//        Result<ArrayList<T>> tResult = null;
//        try {
//            jsonObject = new JSONObject(response);
//            tResult = GsonHelper.getGson().fromJson(jsonObject.toString(), Result.class);
//
//            if (tResult.getError() == 0) {
//                JsonParser jsonParser = new JsonParser();
//                JsonElement jsonElement = jsonParser.parse(jsonObject.getJSONArray("items").toString());
//                for (final JsonElement elem : jsonElement.getAsJsonArray()) {
//                    tArrayList.add(GsonHelper.getGson().fromJson(elem, tClass));
//                }
//                tResult.setItems(tArrayList);
//            }  else {
//                tResult.setItems(tArrayList);
//                Log.e("lifei", "response============" + response);
//                ToastUtil.showToast(tResult.getMsg());
//            }
//        } catch (JSONException e) {
//            ToastUtil.showToast(R.string.http_sjjxsb);
//        }
//
//        return tResult;
//    }
//
//    /**
//     * 已经处理了返回值不为0的弹msg的情况    用法如 Result<XXXbean>  xxx =ResponseUtil.parserObject(string,XXXbean);
//     *
//     * @param response 待处理的json字符串
//     * @param <T>      返回值的javabean类
//     * @return javabean对象
//     */
//    public static <T> Result parserObject(String response, Class<T> tClass) {
//        Result<T> tResult = null;
//        T t;
//        try {
//            JSONObject jsonObject1 = new JSONObject(response);
//            tResult = new Result<>();
//
//            tResult.setError(jsonObject1.getInt("error"));
//            tResult.setMsg(jsonObject1.getString("msg"));
//            tResult.setTotal(jsonObject1.getInt("total"));
//
//            if (jsonObject1.getInt("error") == 0) {
//                if (jsonObject1.has("items")) {
//                    t = GsonHelper.getGson().fromJson(jsonObject1.getJSONObject("items").toString(), tClass);
//                } else {
//                    t = null;
//                }
//                tResult.setItems(t);
//            } else if (jsonObject1.getInt("error") == 401) {
//
//            } else {
//                tResult.setItems(null);
//                throw new Exception(tResult.getMsg());
//            }
//        } catch (JSONException e) {
//            ToastUtil.showToast("数据解析失败");
//        } catch (Exception e) {
//            ToastUtil.showToast(e.getMessage());
//        }
//
//        return tResult;
//    }
//
//    /**
//     * 服务端有返回items字段
//     */
//    public static void dealResponse(String response, DealCallBacks callbacks) {
//        if (callbacks != null) {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                int error = jsonObject.getInt("error");
//                String msg = jsonObject.getString("msg");
//                if (error == 0) {
//                    int total = jsonObject.getInt("total");
//                    String items = jsonObject.getString("items");
//                    callbacks.onSuccess(items, total);
//                } else if (error == 401) {
////                    login();
//                } else {
//                    callbacks.onFailure(error, msg);
//                }
//            } catch (JSONException e) {
//                dealResponseNoItems(response, callbacks);
////                e.printStackTrace();
//            }
//        }
//    }
//
//
//    /**
//     * 服务端没有返回items字段（也可调用dealResponse）
//     */
//    public static void dealResponseNoItems(String response, DealCallBacks callbacks) {
//        if (callbacks != null) {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                int error = jsonObject.getInt("error");
//                String msg = jsonObject.getString("msg");
//                if (error == 0) {
//                    int total = jsonObject.getInt("total");
//                    callbacks.onSuccess(msg, total);
//                } else if (error == 401) {
////                    login();
//                } else {
//                    callbacks.onFailure(error, msg);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 服务端有返回items字段，在失败的回调也需要items字段
//     */
//    public static void dealResponseWithItemsInFailure(String response, DealCallBacksWithItemsInFailure callbacks) {
//        if (callbacks != null) {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                int error = jsonObject.getInt("error");
//                String msg = jsonObject.getString("msg");
//                String items = jsonObject.getString("items");
//                if (error == 0) {
//                    int total = jsonObject.getInt("total");
//                    callbacks.onSuccess(items, total);
//                } else if (error == 401) {
////                    login();
//                } else {
//                    callbacks.onFailure(error, msg, items);
//                }
//            } catch (JSONException e) {
////                dealResponseNoItems(response, callbacks);
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 登录
//     */
//    private static void login() {
//        SharePreferenceUtil spUtil = new SharePreferenceUtil(BaseApplication.getInstance());
//        String userName = spUtil.getPreference(APPConstants.SHARE_LOGINNAME_AND_PAW, APPConstants.SHARE_LOGIN_NAME);
//        final String userPwd = spUtil.getPreference(APPConstants.SHARE_LOGINNAME_AND_PAW, APPConstants.SHARE_LOGIN_PWD);
//        final JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("MobilePhone", userName);
//            jsonObject.put("Password", MD5Helper.Encrypt5(userPwd));
//            jsonObject.put("Version", SystemUtil.getVersionName(BaseApplication.getInstance()));
//            jsonObject.put("UsClientId", SystemUtil.getIMEI(BaseApplication.getInstance()));
//
//            JSONObject jsonObject_PushSet = new JSONObject();
//            jsonObject_PushSet.put("DeviceType", 0);
//            jsonObject.put("PushSet", jsonObject_PushSet);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String url = APIConstant.USUNG_HOST + APIConstant.Login;
//        OkHttpUtils.postString().url(url).mediaType(MediaType.parse("application/json; " +
//                "charset=utf-8")).content(jsonObject.toString()).build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e) {
//                ToastUtil.showToast(R.string.http_failure);
//            }
//
//            @Override
//            public void onResponse(String response) {
//                ResponseUtil.dealResponse(response, new DealCallBacks() {
//                    @Override
//                    public void onSuccess(String items, int total) {
//                        User user = GsonHelper.getGson().fromJson(items, User.class);
//                        //存入全局用户信息
//                        BaseApplication.getInstance().setUser(user);
//                        BaseApplication.getInstance().setPwd(userPwd);
//                        ToastUtil.showToast(R.string.reconnection_success);
//                    }
//
//                    @Override
//                    public void onFailure(int error, String msg) {
//                        ToastUtil.showToast(msg);
//                    }
//                });
//            }
//        });
//    }
}

