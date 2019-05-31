package com.lf.shoppingmall.common;

import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 联网常量存储类
 * Created by Administrator on 2017/8/1.
 */

public class Constans {

    /**
     * 081208
     */
    public static final boolean RELEASE = false;//是正式服，否测试服

    public static final String DES_KEY = "scguoshu";//des加密key

    public static final float BAIDU_ZOOM_LEVEL = 18.0f;//百度加载缩放度

    //    public static final String D_HTTP = "http://47.92.117.38:8088/gs";
//    public static final String D_HTTP = "http://sijix.cn/gs";
    //modidfy base url
    public static final String D_HTTP = "http://47.104.73.202:8080/gs";
    public static final String APP_ID = "wx4475250f68d9a5ae";

    /**
     * 注册接口
     * 参数 params
     * 手机号，密码放入map进行des加密
     * 加密:des  加密key:guoshuguoshu
     * storeTelephone
     * storePwd
     */
    public static final String REGISTER = D_HTTP + "/store/insert";

    /**
     * 忘记密码
     * newPwd
     * storeTelephone
     */
    public static final String forgetPwd = D_HTTP + "/store/forgetPwd";

    /**
     * 登录接口
     * storeTelephone
     * storePwd
     * uuid
     */
    public static final String LOGIN = D_HTTP + "/store/login";

    /**
     * 完善信息
     * storeName,managerName,longitude,latitude,storeAdress,cityCode
     */
    public static final String UPDATE = D_HTTP + "/store/update";

    /**
     * 开放地区接口
     * <p>
     */
    public static final String CITY_LIST = D_HTTP + "/city/listAPI";

    /**
     * 首页接口
     * <p>
     */
    public static final String INDEX_LIST = D_HTTP + "/index/indexList";

    /**
     * 全部菜品
     * 1 品类接口
     * 参数
     * Token
     */
    public static final String goodslistAll = D_HTTP + "/goods/goodslistAll";

    /**
     * 跑马灯
     * 参数
     * Token
     */
    public static final String marquee = D_HTTP + "/marquee/get";

    /**
     * 全部菜品
     * 2 商品查询接口
     * 参数
     * Token
     * parented 一级菜品
     * categoryIdTwo 二级
     */
    public static final String selectGoodsList = D_HTTP + "/goods/selectGoodsList";

    /**
     * 商品详情
     * 参数
     * 参数：token
     * 商品id goodsId
     */
    public static final String selectGoods = D_HTTP + "/goods/selectGoods";

    /**
     * 查询购物车1`
     * 参数
     * 参数：token
     * 商品id goodsId
     */
    public static final String selectCar = D_HTTP + "/shopCar/selectCar";


    /**
     * 添加或减少购物车的数量
     * 参数
     * Token
     * goodsId 商品id
     * specId  规格id
     * num   数量
     */
    public static final String addCar = D_HTTP + "/shopCar/addCar";

    /**
     * 删除购物车
     * 参数
     */
    public static final String deleteCar = D_HTTP + "/shopCar/deleteCar";

    /**
     * 加入清单
     * 参数
     * Token
     * goodsId 商品id
     */
    public static final String addList = D_HTTP + "/store/addList";

    /**
     * 移除清单
     * 参数
     * Token
     * goodsId 商品id
     */
    public static final String removeList = D_HTTP + "/store/removeList";

    /**
     * 订单接口
     * 参数
     * Token
     * State 1 待发货，2待收货，3已完成
     */
    public static final String orderList = D_HTTP + "/orderAPI/list";

    /**
     * 订单详情
     * 参数
     * token
     * ordered
     */
    public static final String orderDetail = D_HTTP + "/orderAPI/show";

    /**
     * 新品需求
     * 添加页面需要的菜品
     * 参数
     * token
     */
    public static final String newCategory = D_HTTP + "/commonTools/category";

    /**
     * 新品需求
     * 保存
     * 参数
     * token
     * goodsName  商品名称
     * goodsCategoryId  选择分类
     * goodsSpec  商品规格
     * upplier	供货商
     * price  参考价格
     * telephone  联系方式
     * remark   备注
     */
    public static final String quickFeedback = D_HTTP + "/commonTools/quickFeedback";

    /**
     * 我的发票
     * <p>
     * type		发票类型 0 电子普通发票，1普通发票，2专用发票
     * start			发票抬头
     * taxpayerId   纳税人识别号
     * regAddres		注册地址
     * regPhone			注册电话
     * telphone     手机号
     * account			银行账号
     * addTime     开票时间
     * startTime		首次开票起始日
     * mail     邮箱
     */
    public static final String mynvoice = D_HTTP + "/commonTools/invoice";

    /**
     * 计算价格
     */
    public static final String countPrice = D_HTTP + "/shopCar/countPrice";

    /**
     * 短信接口
     * phoneNum：手机号
     */
    public static final String sendVCodeUpdate = D_HTTP + "/store/sendVCodeUpdate";

    /**
     * 验证接口
     * phoneNumber：手机号
     * verifyCode：验证码
     */
    public static final String verifyCodeTelUpdate = D_HTTP + "/store/verifyCodeTelUpdate";

    /**
     * 下订单接口
     */
    public static final String orderAPIbuildOrder = D_HTTP + "/orderAPI/buildOrder";


    public static final String orderAPIfinishOrder = D_HTTP + "/orderAPI/finishOrder";

    /**
     * 搜索接口
     * goodName  商品名称
     */
    public static final String selectGoodsByName = D_HTTP + "/goods/selectGoodsByName";

    /**
     * 活动列表
     */
    public static final String activityAPI_list = D_HTTP + "/activityAPI/ list";

    /**
     * 添加收获人
     * <p>
     * token
     * telPhone：手机号
     * trueName： 姓名
     */
    public static final String addressAPIInsert = D_HTTP + "/addressAPI/insert";

    /**
     * 收获人列表
     * <p>
     * token
     */
    public static final String addresslistByStoreId = D_HTTP + "/addressAPI/listByStoreId";

    /**
     * Token
     * addressId
     * 设置默认收获地址
     */
    public static final String updateforaddress = D_HTTP + "/addressAPI/updateforaddress";

    /**
     * oldPwd 旧密码
     * newPwd 新密码
     * Token
     */
    public static final String updatePwd = D_HTTP + "/store/updatePwd";

    /**
     * 取消订单
     * 参数
     * Id 订单id
     */
    public static final String cancelOrder = D_HTTP + "/orderAPI/cancelOrder";

    /**
     * 确认收货
     * 参数Id 订单id
     */
    public static final String finishOrder = D_HTTP + "/orderAPI/finishOrder";
    /**
     * 去结算接口
     */
    public static final String submitOrder = D_HTTP + "/orderAPI/submitOrder";
    /**
     * 支付宝信息
     */
//    public static final String getOrderInfo = D_HTTP + "/config/getOrderInfo";
    public static final String getOrder = D_HTTP + "/config/getOrder";
    /**
     * 微信支付
     */
    public static final String pay_wechat = D_HTTP + "/config/pay";
    /**
     * 常见问题
     */
    public static final String questionList = D_HTTP + "/question/listAPI";

    /**
     * 版本更新
     */
    public static final String getVersion = D_HTTP + "/config/getVersion";

    /**
     * 下载地址
     */
    public static final String DownLoad = D_HTTP + "/config/apk/download";

    /**
     * 欢迎页图片
     */
    public static final String welcomeImageList = D_HTTP + "/welcomeAPI/imageList";
    /**
     * 启动页图片
     */
    public static final String startImageList = D_HTTP + "/startAPI/imageList";

//    UserAccount mUserAccount = GsonHelper.getGson().fromJson(items, UserAccount.class);
//    epAccounts = GsonHelper.getGson().fromJson(items, new TypeToken<List<EpAccount>>() {
//    }.getType());
}
