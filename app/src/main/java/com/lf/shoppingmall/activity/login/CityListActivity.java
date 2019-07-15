package com.lf.shoppingmall.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.adapter.CommonListAdapter;
import com.lf.shoppingmall.adapter.HotCityAdapter;
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
import com.lr.baseview.common.OnRvItemClickListener;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.widget.CustomGridView;
import com.lr.baseview.widget.CustomListView;
import com.lr.baseview.widget.RVCommonItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 城市选择列表
 * Created by Administrator on 2017/8/7.
 */

public class CityListActivity extends BaseActivity implements TextWatcher, OnRvItemClickListener {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_hot_hint)
    TextView tv_hot_hint;
    @BindView(R.id.tv_cancle)
    TextView tv_cancle;
    @BindView(R.id.tv_loc_city)
    TextView tvLocCity;
    @BindView(R.id.rv_hot_city)
    CustomGridView rvHotCity;
    @BindView(R.id.rv_list_city)
    CustomListView rvListCity;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private HotCityAdapter hotCityAdapter;
    private CommonListAdapter commonListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_list;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand);
        ButterKnife.bind(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvLocCity.setText(getIntent().getStringExtra("localcity"));
        etSearch.addTextChangedListener(this);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (EditorInfo.IME_ACTION_SEARCH == actionId) {
                    searchCity();
                    return true;
                }
                return false;
            }
        });

//        rvHotCity.setLayoutManager(new GridLayoutManager(context, 2));
        hotCityAdapter = new HotCityAdapter(context, null, this);
        rvHotCity.setAdapter(hotCityAdapter);
//        rvListCity.setLayoutManager(new LinearLayoutManager(context));
//        rvListCity.addItemDecoration(new RVCommonItemDecoration(context, LinearLayoutManager.VERTICAL));
        commonListAdapter = new CommonListAdapter(context, null, this);
        rvListCity.setAdapter(commonListAdapter);

        ArrayList<CityItemPYVo> cityItemPYVos = getIntent().getParcelableArrayListExtra("CityItemPYVos");
        if (cityItemPYVos != null && !cityItemPYVos.isEmpty()) {
            LogUtils.e("传递城市", "cityItemPYVos-->" + cityItemPYVos.toString());
            initRv(cityItemPYVos);
        } else {
            getCityList();
        }
    }

    private void initRv(List<CityItemPYVo> cityItemPYVos) {
        List<CityVo> hotCity = new ArrayList();
        List commonList = new ArrayList();
        String cityCode = getIntent().getStringExtra(ComParams.ADDR_CITY_CODE);
        for (CityItemPYVo cityItemPYVo : cityItemPYVos) {
            CityItemPYVo pyVo = new CityItemPYVo();
            pyVo.setName(cityItemPYVo.getName());
            commonList.add(pyVo);
            for (CityVo cityVo : cityItemPYVo.getInfo()) {
                if (!TextUtils.isEmpty(cityCode) && cityVo.getCityCode().equals(cityCode)) {
                    cityVo.setSelect(true);
                }
                commonList.add(cityVo);
                if (!TextUtils.isEmpty(cityVo.getCityHot()) && cityVo.getCityHot().equals(ComParams.HOT_CITY)) {
                    hotCity.add(cityVo);
                }
            }
        }

        if (hotCity.isEmpty()) {
            rvHotCity.setVisibility(View.GONE);
            tv_hot_hint.setVisibility(View.GONE);
        } else {
            rvHotCity.setTag(hotCity);
            hotCityAdapter.setList(hotCity, 0);
        }
        commonListAdapter.setList(commonList, 0);
        etSearch.setTag(commonList);
    }

    private void searchCity() {
        String searchStr = etSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(searchStr)) {
            tv_cancle.setVisibility(View.VISIBLE);
            rvHotCity.setVisibility(View.GONE);
            tv_hot_hint.setVisibility(View.GONE);

            List cityList = (List) etSearch.getTag();
            List resultList = new ArrayList();
            for (Object object : cityList) {
                if (object instanceof CityVo) {
                    CityVo cityVo = (CityVo) object;
                    if (cityVo.getCityCaption().indexOf(searchStr) != -1 || searchStr.indexOf(cityVo.getCityCaption()) != -1) {
                        resultList.add(cityVo);
                    }
                }
            }
            if (resultList.isEmpty()) {
                showToast(R.string.address_no_more);
            } else {
                commonListAdapter.setList(resultList, 0);
            }
        }
    }

    @Override
    protected Object getTitleText() {
        return getString(R.string.location);
    }

    @OnClick({R.id.img_keySearch, R.id.tv_cancle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_keySearch:
                searchCity();
                break;

            case R.id.tv_cancle:
                etSearch.setText("");
                tv_cancle.setVisibility(View.GONE);
                break;
        }
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
            if (rvHotCity.getTag() != null) {
                rvHotCity.setVisibility(View.VISIBLE);
                tv_hot_hint.setVisibility(View.VISIBLE);
            }
            commonListAdapter.setList((List) etSearch.getTag(), 0);
        }
    }

    @Override
    public void onItemClick(int position, int type) {
        CityVo cityVo = null;
        if (type == 0) {
            cityVo = hotCityAdapter.getList().get(position);
        } else {
            cityVo = (CityVo) commonListAdapter.getList().get(position);
        }

        setResult(RESULT_OK, new Intent().putExtra("CityVo", (Parcelable) cityVo));
        finish();
    }

    private void getCityList() {
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

        showLoading("");
        OkHttpUtils.get().url(Constans.CITY_LIST)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                CityListVo cityListVo = new Gson().fromJson(body, CityListVo.class);

                if (cityListVo != null && cityListVo.getCityList() != null && !cityListVo.getCityList().isEmpty()) {
                    List<CityItemPYVo> cityItemPYVos = cityListVo.getCityList();
                    initRv(cityItemPYVos);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
