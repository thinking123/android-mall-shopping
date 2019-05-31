package com.lf.shoppingmall.activity.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.address.CityItemPYVo;
import com.lf.shoppingmall.bean.address.CityListVo;
import com.lf.shoppingmall.bean.address.CityVo;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.ComUtils;
import com.lf.shoppingmall.utils.LogUtils;
import com.lf.shoppingmall.utils.MapUtils;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.widget.AlertDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择地址
 * todo  copy中爆定位 需要搜索位置不？？
 * Created by Administrator on 2017/8/7.
 */

public class ChooseAddresActivity extends BaseActivity implements TextWatcher, OnGetGeoCoderResultListener {

    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    private ImageButton requestLocButton;
    //百度地图对象
    private BaiduMap mBaiduMap;
    //定位SDK的核心类
    private LocationClient mLocClient;
    private GeoCoder mSearch = null;
    //定位图层显示模式 (普通-跟随-罗盘)
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private Marker marker; // 选点位置
    //当前位置经纬度
    private double latitude;
    private double longitude;
//    private String addr;
    private String cityCode;
    private final int PERMISSION_REQUEST_CODE_LOCATION = 0X200;
    private int loadType = 0;// 2为首次定位完成 加载城市列表完成
    private String localcity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_address;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            };
            if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, 0);
            }
        }

        et_search.addTextChangedListener(this);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ComUtils.hideSoftKeyboard(context, v);
                    search();
                    return true;
                }
                return false;
            }
        });
        init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            };
            if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE_LOCATION);
            } else {
                initMap();
            }
        } else {
            initMap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                } else {
                    new AlertDialog(context).builder()
                            .setTitle("提示")
                            .setMsg("当前应用必须打开定位方可使用")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    back();
                                }
                            })
                            .show();
                }
                break;

            default:
                break;
        }
    }

    /**
     * 搜索地址
     */
    private void search() {
        String search = et_search.getText().toString();
        if (!TextUtils.isEmpty(search)) {
            GeoCodeOption option = new GeoCodeOption();
            option.city(tvCity.getText().toString());
            option.address(search);
            showToast(" 定位中...");
            if (!mSearch.geocode(option)) {
                showToast("搜索失败，请重新搜索");
            }
        }
    }

    @Override
    protected Object getTitleText() {
        return null;
    }

    @OnClick({R.id.tv_city, R.id.tv_cancle, R.id.btn_submit, R.id.img_keySearch, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                back();
                break;

            case R.id.tv_city:
                ArrayList<CityItemPYVo> cityItemPYVos = (ArrayList<CityItemPYVo>) tvCity.getTag();
                if (cityItemPYVos != null && !cityItemPYVos.isEmpty()) {
                    chooseCity(cityItemPYVos);
                } else {
                    getCityList(1);
                }
                break;

            case R.id.tv_cancle:
                et_search.setText("");
                break;

            case R.id.img_keySearch:
                search();
                break;

            case R.id.btn_submit:
                if (TextUtils.isEmpty(cityCode)) {
                    showToast("当前城市暂无服务，请根据城市列表选择");

                } else {
                    Intent intent = new Intent();
                    intent.putExtra(ComParams.ADDR_CITY_CODE, cityCode);
                    intent.putExtra(ComParams.ADDR_TEXT, tvCity.getText().toString() + et_search.getText().toString());
                    intent.putExtra(ComParams.ADDR_CITY, tvCity.getText().toString());
                    intent.putExtra(ComParams.ADDR_LIN, latitude);
                    intent.putExtra(ComParams.ADDR_LOG, longitude);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }

    private void chooseCity(ArrayList<CityItemPYVo> cityItemPYVos) {
        startActivityForResult(new Intent(ChooseAddresActivity.this, CityListActivity.class)
                .putExtra(ComParams.ADDR_CITY_CODE, cityCode)
                .putExtra("localcity", localcity)
                .putExtra("CityItemPYVos", cityItemPYVos), 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())) {
//            llContent.setVisibility(View.VISIBLE);
            tvCancle.setVisibility(View.GONE);
        } else {
//            llContent.setVisibility(View.GONE);
            tvCancle.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        requestLocButton = (ImageButton) findViewById(R.id.request_loc_button);
        requestLocButton.setOnClickListener(new LocationButtonListenner());
    }

    private void initMap() {
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        requestLocButton = (ImageButton) findViewById(R.id.request_loc_button);
        requestLocButton.setOnClickListener(new LocationButtonListenner());
        mBaiduMap = mMapView.getMap();
        //设置缩放级别 18对应比例尺50米
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(Constans.BAIDU_ZOOM_LEVEL));//设置显示的比例
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // 反Geo搜索
                showToast(" 定位中...");
                mBaiduMap.hideInfoWindow();
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }

        });
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(new MyLocationListenner());
        mLocClient.setLocOption(MapUtils.initLCO());
        showToast(" 定位中...");
        mLocClient.start();
        getCityList(0);
    }

    /**
     * 通过地址获取经纬度
     *
     * @param geoCodeResult 返回结果
     */
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        dissmissLoading();
        LatLng location = geoCodeResult.getLocation();
        if (location != null) {
            MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.latitude)
                    .longitude(location.longitude).build();
            mBaiduMap.setMyLocationData(locData);
            LatLng ll = new LatLng(location.latitude, location.longitude);
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(u);
            et_search.setText(geoCodeResult.getAddress());
            latitude = geoCodeResult.getLocation().latitude;
            longitude = geoCodeResult.getLocation().longitude;
        } else {
            showToast("暂无此地址");
        }
    }

    /**
     * 通过经纬度返回地址
     *
     * @param reverseGeoCodeResult 返回结果
     */
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (loadType < 2) {
            loadType++;
            checkLoadingStatus();
        } else {
            dissmissLoading();
        }
        //设置详细地址
        reverseGeoCodeResult.getBusinessCircle();
        ReverseGeoCodeResult.AddressComponent component = reverseGeoCodeResult.getAddressDetail();
//        addr = component.street;
        localcity = component.city;
        tvCity.setText(component.city);
        et_search.setText(component.street);
        latitude = reverseGeoCodeResult.getLocation().latitude;
        longitude = reverseGeoCodeResult.getLocation().longitude;
        initMarker(latitude, longitude);
        LogUtils.e("ActivitySelectPointInMap", "通过经纬度返回地址--------------");
    }

    /**
     * 在地图上给点位添加标记
     *
     * @param lat
     * @param lng
     **/
    private void initMarker(double lat, double lng) {
        // 清除指定点的标记
        if (marker != null) {
            marker.remove();
        }
        // 构建Marker图标
        final BitmapDescriptor blue_BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_in); // 普通Marker图标
        //定义Marker坐标点
        LatLng point = new LatLng(lat, lng);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point)); // 设置位置中心
        //构建MarkerOption,用于在地图上添加Marker
        OverlayOptions options = new MarkerOptions().position(point).icon(blue_BitmapDescriptor);
        //在地图上添加Marker,并显示
        marker = (Marker) mBaiduMap.addOverlay(options);
    }

    public class LocationButtonListenner implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (!mLocClient.isStarted())
                mLocClient.start();

            mLocClient.requestLocation();
            switch (mCurrentMode) {
                case NORMAL:
                    requestLocButton.setImageResource(R.mipmap.main_icon_follow);
                    mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                    mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
                    break;

                case FOLLOWING:
                    requestLocButton.setImageResource(R.mipmap.main_icon_compass);
                    mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                    mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
                    break;

                case COMPASS:
                    requestLocButton.setImageResource(R.mipmap.main_icon_location);
                    mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                    mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            CityVo addr = (CityVo) data.getSerializableExtra("CityVo");
            if (addr != null) {
                cityCode = addr.getAdCode();
                String city = addr.getCityCaption();
                String oldCity = tvCity.getText().toString();
                LogUtils.e("城市选择", "city-->" + city);
                if (city.indexOf(oldCity) == -1) {
                    LogUtils.e("城市选择", "city1111-->" + city);
                    tvCity.setText(addr.getCityCaption());
                    GeoCodeOption option = new GeoCodeOption();
                    option.city(city);
                    option.address(city);
                    showToast(" 定位中...");
                    if (!mSearch.geocode(option)) {
                        showToast("暂无此地址");
                    }
                }
            }
        }
    }

    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            int type = location.getLocType();
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(u);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            String addr = location.getStreet();
            localcity = location.getCity();
            tvCity.setText(location.getCity());
//            cityCode = location.getCityCode();
            LogUtils.e("定位", "location.getStreet()-->" + location.getStreet() + "  location.getCity()--->" + location.getCity() + "  location.getCityCode()-->" + location.getCityCode());
            et_search.setText(addr);
            if (TextUtils.isEmpty(addr)) {
                mBaiduMap.hideInfoWindow();
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
            } else {
                if (loadType < 2) {
                    loadType++;
                    checkLoadingStatus();
                } else {
                    dissmissLoading();
                }
            }
            mLocClient.stop();
        }
    }

    private void checkLoadingStatus() {
        if (loadType >= 2) {
            dissmissLoading();
            List<CityItemPYVo> cityItemPYVos = (List<CityItemPYVo>) tvCity.getTag();
            String city = tvCity.getText().toString();
            if (cityItemPYVos != null && !cityItemPYVos.isEmpty() && !TextUtils.isEmpty(city)) {
                initCityCode(cityItemPYVos);
            }
        }
    }

    /**
     * 获取城市列表
     *
     * @param type 0是加载数据 1跳转界面
     */
    private void getCityList(final int type) {
        UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
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

        if (type == 1) {
            showLoading("");
        }
        OkHttpUtils.get().url(Constans.CITY_LIST)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                if (type == 1) {
                    dissmissLoading();
                } else {
                    loadType++;
                    checkLoadingStatus();
                }
                CityListVo cityListVo = new Gson().fromJson(body, CityListVo.class);

                if (cityListVo != null && cityListVo.getCityList() != null && !cityListVo.getCityList().isEmpty()) {
                    List<CityItemPYVo> cityItemPYVos = cityListVo.getCityList();
                    tvCity.setTag(cityItemPYVos);

                    if (type == 0) {//&& !TextUtils.isEmpty(cityCode
                        String city = tvCity.getText().toString();
                        if (!TextUtils.isEmpty(city)) {
                            initCityCode(cityItemPYVos);
                        }
                    } else {
                        initCityCode(cityItemPYVos);
                        chooseCity((ArrayList<CityItemPYVo>) cityItemPYVos);
                    }

                } else {
                    showToast(R.string.loading_no_more);
                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                if (type == 1) {
                    dissmissLoading();
                } else {
                    loadType++;
                    checkLoadingStatus();
                }
                showToast(errorMsg);
            }
        });
    }

    private void initCityCode(List<CityItemPYVo> cityItemPYVos) {
        String city = tvCity.getText().toString();
        if (!TextUtils.isEmpty(city)) {
            for (CityItemPYVo cityItemPYVo : cityItemPYVos) {
                for (CityVo cityVo : cityItemPYVo.getInfo()) {
                    if (cityVo.getCityCaption().indexOf(city) != -1) {
                        cityCode = cityVo.getAdCode();
                    }
                }
            }
        }
    }
}
