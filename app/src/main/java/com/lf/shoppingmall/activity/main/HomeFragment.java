package com.lf.shoppingmall.activity.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.custom_service.CustomServiceMainActivity;
import com.lf.shoppingmall.activity.goods.GoodsDetail0Activity;
import com.lf.shoppingmall.activity.goods.PromotionListActivity;
import com.lf.shoppingmall.activity.goods.SearchGoodsActivity;
import com.lf.shoppingmall.activity.login.ChooseAddresActivity;
import com.lf.shoppingmall.activity.order.MyOrderActivity;
import com.lf.shoppingmall.activity.order.PingJiaActivity;
import com.lf.shoppingmall.activity.red_bag.RedBagActivity;
import com.lf.shoppingmall.adapter.ComonOrderListAdapter;
import com.lf.shoppingmall.adapter.HomePromotionAdapter;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.base.BaseFragment;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.address.CityItemPYVo;
import com.lf.shoppingmall.bean.address.CityListVo;
import com.lf.shoppingmall.bean.address.CityVo;
import com.lf.shoppingmall.bean.index.BannerVo;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.index.GuigeVo;
import com.lf.shoppingmall.bean.index.IndexVo;
import com.lf.shoppingmall.bean.index.ProductionInfoVo;
import com.lf.shoppingmall.bean.index.PromotionVo;
import com.lf.shoppingmall.bean.other.GoodsDetailVo;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.common.OnExpandItemClidkListener;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.ComUtils;
import com.lf.shoppingmall.utils.LogUtils;
import com.lf.shoppingmall.weight.mybannr.MyBanner;
import com.lr.baseview.common.OnRvItemClickListener;
import com.lr.baseview.widget.AlertDialog;
import com.lr.baseview.widget.AlertEditDialog;
import com.lr.baseview.widget.CustomListView;
import com.lr.baseview.widget.CustomRecyclerView;
//import com.lr.baseview.widget.CustomViewPagerForSrcoll;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页片段
 * Created by Administrator on 2017/8/4.
 */

public class HomeFragment extends BaseFragment implements OnRvItemClickListener, OnExpandItemClidkListener, OnGetGeoCoderResultListener, OnRefreshListener {

    @Bind(R.id.view_myBanner)
    MyBanner viewMyBanner;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.crv_promotion)
    CustomListView crv_promotion;
    private HomePromotionAdapter promotionAdapter;

    @Bind(R.id.tl_item)
    TabLayout tlItem;
    @Bind(R.id.tv_tab_hint)
    TextView tvTabHint;
    @Bind(R.id.cbox_arrow)
    CheckBox cboxArrow;
    @Bind(R.id.crv_common)
    CustomListView crv_common;
    private ComonOrderListAdapter adapter;

    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
//    @Bind(R.id.vp_list)
//    CustomViewPagerForSrcoll vpList;
//    private MyPagerAdapter pageAdapter;
//    private List<BaseFragment> fragments;

    private LocationClient client;
    private GeoCoder mSearch = null;
    private String cityCode;
    private String cityName;

    private void getLocation() {
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

        client = new LocationClient(getActivity().getApplicationContext());
        //注册监听接口
        client.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(10000);
        //开启描述性信息，不开启不会返回城市名等信息
        option.setIsNeedAddress(true);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000);
        client.setLocOption(option);
        client.start();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        String city = reverseGeoCodeResult.getAddressDetail().city;
        tvRight.setText(city);
        getCityList();
    }


    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                Toast.makeText(context, "定位失败", Toast.LENGTH_SHORT).show();
                return;
            }
            String city = bdLocation.getCity();
            if (TextUtils.isEmpty(city)) {
                LatLng ll = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
            } else {
//                tvRight.setText(city);
                getCityList();
            }
            client.stop();
        }
    }

    @Override
    protected void init() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);

        promotionAdapter = new HomePromotionAdapter(context, null, this);
//        crv_promotion.setLayoutManager(new LinearLayoutManager(context));
        crv_promotion.setAdapter(promotionAdapter);

        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        tvRight.setText(userVo.getCityName());
        adapter = new ComonOrderListAdapter(context, null, this);
        crv_common.setAdapter(adapter);
        getLocation();
        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.startRefresh();
    }


    @Override
    public void onRefresh() {
        getIndex(0);
    }

    private void initBananr(final List<BannerVo> bannerVos) {
        viewMyBanner.setImagesList(bannerVos,
                null, new MyBanner.OnImageClickListener() {
                    @Override
                    public void onImageClick(int position) {
                        String id = bannerVos.get(position).getId();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_right, R.id.ll_search, R.id.ll_costom_service, R.id.tv_0, R.id.tv_1, R.id.tv_2, R.id.tv_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                getActivity().startActivityForResult(new Intent(getActivity(), ChooseAddresActivity.class), 20);//区别主界面的requestCode
                break;

            case R.id.ll_search:
                startActivity(new Intent(getActivity(), SearchGoodsActivity.class));
                break;

            case R.id.ll_costom_service://客服中心
                startActivity(new Intent(getActivity(), CustomServiceMainActivity.class));
                break;

            case R.id.tv_0:
                ((MainActivity) getActivity()).setCurrntPager(1);
                break;

            case R.id.tv_1:
                ((MainActivity) getActivity()).setCurrntPager(2);
                break;

            case R.id.tv_2:
                startActivity(new Intent(getActivity(), RedBagActivity.class));
                break;

            case R.id.tv_3:
//                startActivity(new Intent(getActivity(), PingJiaActivity.class));
                startActivity(new Intent(getActivity(), MyOrderActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(int position, int type) {
        if (type == 0) {
            startActivity(new Intent(getActivity(), PromotionListActivity.class)
            .putExtra("PromotionVo", promotionAdapter.getList().get(position)));
        }
    }

    public void refresh() {
        getIndex(1);
    }

    public void getIndex(final int type) {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("cityCode", userVo.getCityCode());
            String des = new Gson().toJson(map);
            LogUtils.e("首页", "des-->" + des);
            params = DES.encryptDES(des);
            LogUtils.e("首页", "params-->" + params);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("首页", "Exception-->" + e.toString());
        }
//        showLoading("");
        OkHttpUtils.get().url(Constans.INDEX_LIST)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MainActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                swipeToLoadLayout.stopRefresh();
                IndexVo indexVo = new Gson().fromJson(body, IndexVo.class);

                if (indexVo != null) {
                    if (type == 0) {
                        List<BannerVo> bannerVos = indexVo.getBannerList();
                        if (bannerVos != null && !bannerVos.isEmpty()) {
                            initBananr(bannerVos);
                        }
                        List<PromotionVo> activityList = indexVo.getActivityList();
                        if (activityList != null && !activityList.isEmpty()) {
                            LogUtils.e("商品图片中", activityList.toString());
                            promotionAdapter.setList(activityList, 0);
                        }
                    }
                    ArrayList<ProductionInfoVo> ProductionInfoList = (ArrayList<ProductionInfoVo>) indexVo.getProductionInfoList();
                    if (ProductionInfoList != null && !ProductionInfoList.isEmpty()) {
//                        FragmentManager manager = getChildFragmentManager();
//                        manager.beginTransaction().replace(R.id.fl_common_order, CommonOrderFragment.Instanst(1, ProductionInfoList)).commit();

//                        if (fragments == null){
//                            fragments = new ArrayList<>();
//                        }
                        List<String> titleList = new ArrayList<>();
                        ArrayList goodsList = new ArrayList();
                        for (ProductionInfoVo productionInfoVo : ProductionInfoList) {
                            if (productionInfoVo.getGoodsList() != null && !productionInfoVo.getGoodsList().isEmpty()) {
                                goodsList.add(productionInfoVo.getGoodsBaseType());
                                goodsList.addAll(productionInfoVo.getGoodsList());
                                titleList.add(productionInfoVo.getGoodsBaseType());
                                tlItem.addTab(tlItem.newTab().setText(productionInfoVo.getGoodsBaseType()));
//                                fragments.add(CommonOrderListForScroll.Instanst(productionInfoVo.getGoodsBaseType(), goodsList));
                            }
                        }

                        adapter.setList(goodsList, 0);

//                        pageAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments, titleList);
//                        vpList.setAdapter(pageAdapter);
//                        tlItem.setupWithViewPager(vpList);
                        if (!isLoaded) {
                            ((MainActivity) getActivity()).setCommonOrder(ProductionInfoList);
                            isLoaded = true;
                        }
                    }
                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                swipeToLoadLayout.stopRefresh();
                showToast(errorMsg);
            }
        });
    }

    @Override
    public void onItemClick(final int position, final int childPosition, int type) {
        final GoodsVo goodsVo = (GoodsVo) adapter.getList().get(position);
        switch (type) {
            case -1:
                selectGoods(goodsVo.getId());
                break;

            case 0:
                new AlertDialog(context).builder().setTitle("提示")
                        .setMsg("确定从常用清单中移除该商品吗?")
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                removeList(goodsVo.getId(), position);
                            }
                        })
                        .show();
                break;

            case 1:
                goodsVo.setGuige(!goodsVo.isGuige());
                adapter.notifyDataSetChanged();
                break;

            case 2: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                setCatNum(goodsVo.getId(), guigeVo, 1, 1);
            }
            break;

            case 3: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                setCatNum(goodsVo.getId(), guigeVo, 1, 1);
            }
            break;

            case 4: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                int carNum = guigeVo.getCarGoodNum();
                setCatNum(goodsVo.getId(), guigeVo, carNum + 1, 1);
            }
            break;

            case 5: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                setCatNum(goodsVo.getId(), guigeVo, guigeVo.getCarGoodNum() - 1, -1);
            }
            break;

            case 6:
                new AlertEditDialog(context).builder()
                        .setMsg("请输入价格")
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton("确定", new AlertEditDialog.OnPClick() {
                            @Override
                            public void onClick(View v, String result) {
                                int num = Integer.valueOf(result);
                                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                                setCatNum(goodsVo.getId(), guigeVo, num, -1);
                            }
                        })
                        .show();

                break;
        }
    }

    /**
     * 添加或减少购物车的数量
     *
     * @param parentIdn
     * @param guigeVo
     * @param num
     * @param shop_cat_num
     */
    private void setCatNum(String parentIdn, final GuigeVo guigeVo, final int num, final int shop_cat_num) {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("goodsId", parentIdn);
            map.put("specId", guigeVo.getId());
            map.put("num", String.valueOf(num));
            String des = new Gson().toJson(map);
            LogUtils.e("添加或减少购物车的数量", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("添加或减少购物车的数量", "Exception-->" + e.toString());
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.addCar)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MainActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("添加或减少购物车的数量", "body---->" + body);
                guigeVo.setCarGoodNum(num);
                adapter.notifyDataSetChanged();
                ComParams.shop_cat_num = ComParams.shop_cat_num + shop_cat_num;
                ((MainActivity) getActivity()).setShoppingCatNum();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 移除清单
     *
     * @param id
     */
    private void removeList(String id, final int position) {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("goodsId", id);
            String des = new Gson().toJson(map);
            LogUtils.e("移除清单", "des-->" + des);
            params = DES.encryptDES(des);

        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.removeList)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MainActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("移除清单", "body-->" + body);
                adapter.getList().remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 商品详情
     *
     * @param id
     */
    private void selectGoods(String id) {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("goodsId", id);
            String des = new Gson().toJson(map);
            LogUtils.e("商品详情", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.selectGoods)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MainActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                GoodsDetailVo goods = new Gson().fromJson(body, GoodsDetailVo.class);

                if (goods != null && goods.getGoods() != null) {
                    LogUtils.e("商品详情", "goods---->" + goods.toString());
                    startActivityForResult(new Intent(getActivity(), GoodsDetail0Activity.class)
                                    .putExtra("GoodsVo", goods.getGoods()),
                            ComParams.RESUTLT_SHOP_CAT);
                } else {

                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 20:
                if (resultCode == getActivity().RESULT_OK && data != null) {
                    cityName = data.getStringExtra(ComParams.ADDR_CITY);
//                    latitude = data.getDoubleExtra(ComParams.ADDR_LIN, 0);
//                    longitude = data.getDoubleExtra(ComParams.ADDR_LOG, 0);
                    tvRight.setText(cityName);
                    String current = data.getStringExtra(ComParams.ADDR_CITY_CODE);
                    if (TextUtils.isEmpty(cityCode) || !cityCode.equals(current)) {
                        cityCode = data.getStringExtra(ComParams.ADDR_CITY_CODE);
                        BaseApplication application = ((BaseApplication) getActivity().getApplication());
                        application.getUserVo().setCityCode(cityCode);
                        application.getUserVo().setCityName(cityName);
                        application.setUserVo(application.getUserVo());
                        swipeToLoadLayout.startRefresh();
                    }
                }
                break;
        }
    }

    /**
     * 获取城市列表
     */
    private void getCityList() {
        final UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        if (!TextUtils.isEmpty(userVo.getDesParams())) {
            params = userVo.getDesParams();
            LogUtils.e("城市列表", "params-->" + params);
        } else {
            try {
                Map<String, String> map = new HashMap<>();
                map.put("storeTelephone", userVo.getStoreTelephone());
                map.put("storePwd", userVo.getPwd());
                map.put("uuid", ComUtils.getUUID(context));
                map.put("token", userVo.getToken());
                String des = new Gson().toJson(map);
                LogUtils.e("城市列表", "des-->" + des);
                params = DES.encryptDES(des);
                LogUtils.e("城市列表", "params-->" + params);

            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e("城市列表", "Exception-->" + e.toString());
            }
        }

        OkHttpUtils.get().url(Constans.CITY_LIST)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MainActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                CityListVo cityListVo = new Gson().fromJson(body, CityListVo.class);

                if (cityListVo != null && cityListVo.getCityList() != null && !cityListVo.getCityList().isEmpty()) {
                    List<CityItemPYVo> cityItemPYVos = cityListVo.getCityList();

                    String city = tvRight.getText().toString();
                    if (!TextUtils.isEmpty(city)) {
                        for (CityItemPYVo cityItemPYVo : cityItemPYVos) {
                            for (CityVo cityVo : cityItemPYVo.getInfo()) {
                                if (cityVo.getCityCaption().indexOf(city) != -1) {
                                    cityCode = cityVo.getAdCode();
                                    cityName = cityVo.getCityDesc();
                                }
                            }
                        }
                    }

                    if (TextUtils.isEmpty(cityCode)) {
                        new AlertDialog(context).builder()
                                .setTitle("提示")
                                .setMsg("当前城市暂无服务，已切换至你的收货人城市")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                })
                                .show();
                        tvRight.setText(userVo.getCityName());
                        return;
                    }

                    String userCityCode = userVo.getCityCode();
                    LogUtils.e("城市切换", "cityCode--》" + cityCode + "\nuserCityCode-->" + userCityCode);
                    if (!cityCode.equals(userCityCode)) {
                        new AlertDialog(context).builder()
                                .setTitle("提示")
                                .setMsg("是否切换至当前城市")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        userVo.setCityCode(cityCode);
                                        userVo.setCityName(cityName);
                                        ((BaseApplication) getActivity().getApplication()).setUserVo(userVo);
                                        swipeToLoadLayout.startRefresh();
                                    }
                                })
                                .setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        tvRight.setText(userVo.getCityName());
                                    }
                                })
                                .show();
                    }

                } else {
                    showToast(R.string.loading_no_more);
                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }
}
