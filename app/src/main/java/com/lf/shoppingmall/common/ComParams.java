package com.lf.shoppingmall.common;

/**
 * 常用参数
 * Created by Administrator on 2017/8/1.
 */

public class ComParams {

    public static final int PAGE = 1;//分页起始页
    public static final int SIZE = 10;//分页加载数据量

    public static final int STARTUP_TIME = 2000;//启动界面时间
    public static final String FRIST_GOIN = "frist_goin";//首次进入app
    public static final String SEARCH_HOS = "search_hos";//保存搜索记录

    //当前主界面显示的fargment
    public static int main_current_page = 0;
    //登录状态 -1未检查状态 0未登录 1登录
    public static int login_status = -1;
    //VCode
    public static String VCode ;
    //token
    public static String token ;
    //desParams
    public static String desParams ;
    //购物车数量
    public static int shop_cat_num = 0;
    //最低起送价
    public static int total_price_low = 100;
    //最低起送价
    public static float total_price = 0;
    //购物车返回的result
    public static final int RESUTLT_SHOP_CAT = 77;

    //客服电话
    public static final String TELL_COUSTOM_SERVICE = "028-26222908";
    public static final String TELL_xiaoshou = "18111109450";

    //登录密码长度
    public static final int PWD_SHORT = 1;
    public static final int PWD_LONG = 30;
    //验证码长度
    public static final int VERIFICATION_LENGHT = 4;
    //获取验证码时间间隔
    public static final int VERIFICATION_TIME = 60;
    //是否完善信息标志
    public static final String LOGIN_ISFINISH = "1";

    //地址返回的参数
    public static final String ADDR_CITY_CODE = "result_addr_city_code";
    public static final String ADDR_TEXT = "result_addr_text";
    public static final String ADDR_CITY = "result_addr_city";
    public static final String ADDR_LIN = "result_addr_lin";
    public static final String ADDR_LOG = "result_addr_log";
    //是否热门城市的判断
    public static final String HOT_CITY = "1";
    //微信支付成功的广播
    public static final String BRONDCASE_PAY_WECHAT = "bronscast_pay_wechat";
    public static String PAY_ID ;
}
