package com.lf.shoppingmall.zfb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 支付宝的工具类
 *
 * @author Administrator
 */
public class PayDemoUtil {

    /*==========================================================================*/
    /*=====================需要填写商户app申请的商户ID和私钥========================*/
    /*===========================================================================*/
    //商户PID
    public static final String PARTNER = "2017110209686882";
//    public static final String PARTNER = "2088821084876060";
    //商户收款账号
    public static final String SELLER = "2017110209686882";
//    public static final String SELLER = "2088821084876060";
    //商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPECCrLMeh19W4GuFLCUNWPD" +
            "IcGkK5GF0Dhkxby4Dh49QQaYy1wcpAqoZjDuBHctdbwS1+HQabcQF93uVGSDk3p2qazf5FLjvpIRDeEXuLqjMsNvD/CijBJcs78+htwjzV9wJQy" +
            "UimK8czwDZf5UtxToyQN9+fZn5GvM8mK7HgmzmV1xVzFmnugHwCZ0Qx8OwEFl/AzATIw6tyeZAISkAQ2YOSUEFSuOgBVqpDYU3crLzom1NH3lxGds/" +
            "73BR3/rUoIXhvBUzjw4ULm4CKih0+nrP6naZul7UDlXTqh01eg2oZpmIvCxBmbVyi9QLN+YCXhFn9PGmoa6J2AJsMUAz7AgMBAAECggEACuzzYb84u75s/" +
            "vNmk0Fw2Awe05CZ2eLnb5Z5DGWdQSMSFfLJoTPci1W6j6QaMrqtgb9eBwyWbZaSw5cbMWPgosRxWsMykPv37VTuIdd59O+Zmt3TR9bxx9ZoD/sPnBpCZSD" +
            "JF3xXxQEA5I3gWTB8s3mJOJZbyKOeLy7KLyMVIY764wr85gtxD6dLJFQyn4nMEfV8yWyvE6Z2r8NcrhDriken6CYdWbmBaSMrOzl+f/8XWw/HLAKrTjwV9wWZ" +
            "MWKEuOIJ9IMWKe3cQVc2W87yit0rze7LucMvioR4ZrH5AX/LF+AbUhlw87CHqwhZtfo4tTEtsNuOC843iSH0RIDWiQKBgQD7WTnmyzVyBRVKqc+VgIJ43mzoENUhS/" +
            "IKxTFAf/P9IhBGdPkxYBnVc3CBCWVsXDJ4fei6IwRBF+sLpjn6DKc3M2OGQ1okDy8gQxqXfZKa80foW7emWU2b3sPE8Q0JtOtuMPT8l9K2XZRjHX770JS+oqbRN/" +
            "7h2hGtvWs43WhQVwKBgQCRteTyc7CGaFoel//tG7J/b3D2B47ts0PGT5NFsKmney3+Dt0mJ5Fbj90+BiINT7PXxNCpa4w90IOs2gdd78fpZjPNHFJhtQUDlxHhry" +
            "TpRD53KytF+Z9ZCz58NBM29BOhswcPpmxrKakUwUDLYneYtZmdVJkXhBHeqHJTeugx/QKBgQDAx+zrIc06SL/N5nS6/DgWMLNjQxXVVgStUhW2nO0sp+XHxIwk3Mqztrk" +
            "Bn41VEVn8pMXNjnIC4y96XeJ18SKH6jhZkGsg4SyKGC3bJ4s8oX/zrfUr967Y8IFDGoeffE5e09nzqxcInLnlU4hD5/VOSj6cldA+th19Hp2QnfEWPQKBgEr9EZa1c2O40" +
            "KhuEYg1eOecssXrneM3HuowidqT0Lwg6MqXW7kHJAe7QmFpRp0TJ7sjzz9h2gr2BM4wZxoxDPFRZ9dne/5M6wet98XuHVDvjjZGQT8B+MAayU2if0EI3BGb0UZ8n8+G4T2" +
            "NN4t0YZh13Vja8O7pSUkN58iOuHEBAoGAI3HLFyqmlq7FBb7w9auVUJ+lXI1s+OXkf2pmFGHDB/v57Hyyi/kTY3Xk2iFzfYM7mLvBJbhiVxhGsXoVM8UvU6AHuGmlXh/" +
            "DZvxZqZUVzDoSyj0ev2mPkeGLp+voTK6j7M/BWa71RFtIYkWo2zczxIC0CSRPJKzVH4G1R/gxqLY=";
	/*===========================================================================*/
    /*===========================================================================*/
    /*===========================================================================*/


    //支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDfmCxqsodsM5c6a2Y0bAS2Vlg9" +
            "KiU0ze8ETn36y9DJz9gxnKKW8ML/a1uX30wQ1+XUS6AUbeiHePIpGnBA9nHKOG11" +
            "P4/093K9nZCapjE//3Bhir8F9HdmI2RM4SeRq6mJ2kqvzFU7XQpqK9C5cJL+EgG6" +
            "FRAVSd4tDBgxuLtuqQIDAQAB";


    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private PayOkListener payOkListener;
    private PayFailureListener payFailureListener;
    private PayCheckListener payCheckListener;
    //上下本
    private Context mContext;
    private AlipayBean alipayBean;
    private String orderInfoStr;

    /**
     * @param mContext 上下文
     */
    public PayDemoUtil(Context mContext, AlipayBean alipayBean, String orderInfoStr) {
        super();
        ToastUtils.showToast(mContext,"正在加载支付宝..."); //需支付宝支付
        this.mContext = mContext;
        this.alipayBean = alipayBean;
        this.orderInfoStr = orderInfoStr;

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        payOkListener.onPaySuccess();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                            payFailureListener.onPayFailureL();
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    payCheckListener.onPayCheckSuccess();
                    Toast.makeText(mContext, "(支付宝)检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(PayOkListener payOkListener, PayFailureListener payFailureListener) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(mContext).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {

                        }
                    }).show();
            return;
        }
        this.payOkListener = payOkListener;
        this.payFailureListener = payFailureListener;

        if (TextUtils.isEmpty(this.orderInfoStr)) {
            // 订单
            String orderInfo = getOrderInfo();
            // 对订单做RSA 签名
            String sign = sign(orderInfo);
            try {
                // 仅需对sign 做URL编码
                sign = URLEncoder.encode(sign, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // 完整的符合支付宝参数规范的订单信息
            orderInfoStr= orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
            Log.e("支付宝请求参数", "orderInfo----------------" + orderInfo);
            Log.e("支付宝请求参数", "getSignType----------------" + getSignType());
        }
        Log.e("支付宝请求参数", "orderInfoStr----------------" + orderInfoStr);

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) mContext);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(orderInfoStr, true);
                LogUtils.e("支付结果", "result--->" + result);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     *
     * @return
     */
//    public boolean check(PayCheckListener payCheckListener) {
//        this.payCheckListener = payCheckListener;
//        Runnable checkRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                // 构造PayTask 对象
//                PayTask payTask = new PayTask((Activity) mContext);
//                // 调用查询接口，获取查询结果
//                boolean isExist = payTask.checkAccountIfExist();
//
//                Message msg = new Message();
//                msg.what = SDK_CHECK_FLAG;
//                msg.obj = isExist;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        Thread checkThread = new Thread(checkRunnable);
//        checkThread.start();
//        return true;
//    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask((Activity) mContext);
        String version = payTask.getVersion();
        Toast.makeText(mContext, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo() {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + alipayBean.getPayid() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + alipayBean.getTitle() + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + alipayBean.getOrderid() + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + alipayBean.getPrice() + "\"";

        // 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";  // demo
//        orderInfo += "&notify_url=" + "\"" + alipayBean.getNotifyUrl() + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    // 支付成功后回调此方法（可在此方法中进行刷新支付界面）
    public interface PayOkListener {
        public void onPaySuccess();
    }

    // 支付失败后回调此方法（可在此方法中进行刷新支付界面）
    public interface PayFailureListener {
        public void onPayFailureL();
    }

    // 检查移动设备终端是否存在支付认证宝账号
    public interface PayCheckListener {
        public void onPayCheckSuccess();
    }
}
