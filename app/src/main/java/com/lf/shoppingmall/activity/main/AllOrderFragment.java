package com.lf.shoppingmall.activity.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.custom_service.CustomServiceMainActivity;
import com.lf.shoppingmall.activity.goods.SearchGoodsActivity;
import com.lf.shoppingmall.adapter.ComonOrderListAdapter;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.base.BaseFragment;
import com.lf.shoppingmall.base.MyPagerAdapter;
import com.lf.shoppingmall.bean.AllOrder.GoodsCateVo;
import com.lf.shoppingmall.bean.AllOrder.GoodsCategoryListVo;
import com.lf.shoppingmall.bean.AllOrder.GoodsCategoryVo;
import com.lf.shoppingmall.bean.AllOrder.MarqueeBean;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.index.BannerVo;
import com.lf.shoppingmall.bean.index.IndexVo;
import com.lf.shoppingmall.bean.index.ProductionInfoVo;
import com.lf.shoppingmall.bean.index.PromotionVo;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/8.
 */

public class AllOrderFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.iv_custom_service)
    ImageView ivCustomService;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.tv_search_hint)
    TextView tvSearchHint;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_notice)
    TextView tv_notice;
    @BindView(R.id.tl_item)
    TabLayout tlItem;
    @BindView(R.id.vp_list)
    ViewPager vpList;
    private MyPagerAdapter pageAdapter;

    private List<AllOrderListFragment> fragments;
    private int curruntPage = 0;

    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_fragment;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

        view.findViewById(R.id.search_bar).setVisibility(View.VISIBLE);
//        tvRight.setBackgroundResource(R.drawable.bg_cricle_red_25);
//        tvRight.setText("红包");
        tvRight.setVisibility(View.GONE);
        ivCustomService.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner25_green));
        ll_search.setBackgroundResource(R.drawable.bg_corner25_storke_grey);
        ivSearchIcon.setImageResource(R.mipmap.search);
        tvSearchHint.setTextColor(ContextCompat.getColor(context, R.color.text_norm_8));

        marquee();
//        initVP();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (isPrepared && !isLoaded) {
            goodslistAll();
        }
    }

    private void initVP(List<GoodsCategoryVo> goodsCategoryList) {

//        String[] titles = new String[]{"肉禽类", "新鲜蔬菜", "米面粮油","水产特产","蛋类","休闲酒饮"};
        List<String> titleList = new ArrayList<>();
        fragments = new ArrayList<>();
        int i = 0;
        for (GoodsCategoryVo categoryVo : goodsCategoryList) {
            tlItem.addTab(tlItem.newTab());
            titleList.add(categoryVo.getCategoryName());
            fragments.add(AllOrderListFragment.Instanst(categoryVo.getCategoryId(), (ArrayList<GoodsCateVo>) categoryVo.getGoodsCategories()));
            i++;
        }
        pageAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments, titleList);
        vpList.setAdapter(pageAdapter);
        tlItem.setupWithViewPager(vpList);

        vpList.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        curruntPage = position;
        fragments.get(curruntPage).onRefresh();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void onRefresh() {
        if (fragments != null) {
            fragments.get(curruntPage).onRefresh();
        }
    }

    @OnClick({R.id.ll_costom_service, R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_costom_service:
                startActivity(new Intent(getActivity(), CustomServiceMainActivity.class));
                break;

            case R.id.ll_search:
                startActivity(new Intent(getActivity(), SearchGoodsActivity.class));
                break;
        }
    }

    /**
     * 跑马灯
     */
    private void marquee() {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("cityCode", userVo.getCityCode());
            String des = new Gson().toJson(map);
            params = DES.encryptDES(des);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("跑马灯", "Exception-->" + e.toString());
        }
        OkHttpUtils.get().url(Constans.marquee)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MainActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                LogUtils.e("跑马灯", "body-->" + body);
                MarqueeBean bean = new Gson().fromJson(body, MarqueeBean.class);
                if (bean != null && bean.getMarquee() != null) {
                    tv_notice.setText(bean.getMarquee().getContent());
                    tv_notice.setTag(bean.getMarquee().getId());
                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                showToast(errorMsg);
            }
        });
    }

    /**
     * 品类接口
     */
    private void goodslistAll() {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            String des = new Gson().toJson(map);
            params = DES.encryptDES(des);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("品类接口", "Exception-->" + e.toString());
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.goodslistAll)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MainActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                isLoaded = true;
                GoodsCategoryListVo goodsCategoryVo = new Gson().fromJson(body, GoodsCategoryListVo.class);

                if (goodsCategoryVo != null && goodsCategoryVo.getGoodsCategoryList() != null && !goodsCategoryVo.getGoodsCategoryList().isEmpty()) {
                    initVP(goodsCategoryVo.getGoodsCategoryList());
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
