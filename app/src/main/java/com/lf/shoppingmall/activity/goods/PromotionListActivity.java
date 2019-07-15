package com.lf.shoppingmall.activity.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.main.CommonOrderListFragment;
import com.lf.shoppingmall.adapter.ComonOrderListAdapter;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.base.MyPagerAdapter;
import com.lf.shoppingmall.bean.AllOrder.GoodsListInfoListVo;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.index.ProductionInfoVo;
import com.lf.shoppingmall.bean.index.PromotionVo;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.common.OnExpandItemClidkListener;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.CommonUilts;
import com.lr.baseview.utils.ImageLoadUtils;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.widget.CircleImageView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 活动界面
 * Created by Administrator on 2017/9/3.
 */

public class PromotionListActivity extends BaseActivity implements OnExpandItemClidkListener, OnRefreshListener {
    //    @BindView(R.id.swipe_target)
//    ListView swipeTarget;
    @BindView(R.id.tl_item)
    TabLayout tlItem;
    @BindView(R.id.vp_list)
    ViewPager vpList;
    private MyPagerAdapter pageAdapter;
    private List<PromotionListFragment> fragments;
//    @BindView(R.id.swipeToLoadLayout)
//    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.iv_banner)
    ImageView iv_banner;
//    @BindView(R.id.tv_none_hint)
//    TextView tvNoneHint;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_operation)
    TextView tvOperation;
    @BindView(R.id.iv_shop)
    CircleImageView ivShop;
    @BindView(R.id.tv_notice_num)
    TextView tvNoticeNum;
    @BindView(R.id.tv_title)
    TextView tv_title;
//    private ComonOrderListAdapter adapter;

    private String activityId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_promotion_list;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        PromotionVo promotionVo = (PromotionVo) getIntent().getSerializableExtra("PromotionVo");
        if (promotionVo == null) {
            showToast("信息有误");
            finish();
            return;
        }
        activityId = promotionVo.getId();
        tv_title.setText(promotionVo.getName());
        ImageLoadUtils.loadingCommonPic(context, promotionVo.getActivityImg(), iv_banner);
//        tvNoneHint.setText("点击刷新");
//        adapter = new ComonOrderListAdapter(context, null, this);
//        adapter.status = 1;
//        swipeTarget.setAdapter(adapter);
//        activityId
        if (ComParams.shop_cat_num > 0) {
            tvNoticeNum.setText(String.valueOf(ComParams.shop_cat_num));
            tvNoticeNum.setVisibility(View.VISIBLE);
        } else {
            tvNoticeNum.setVisibility(View.GONE);
        }
        tvTotalPrice.setText("总价:￥" + CommonUilts.getDoubleTwo(ComParams.total_price));
//        swipeToLoadLayout.setLoadMoreEnabled(false);
//        swipeToLoadLayout.setOnRefreshListener(this);
//        swipeToLoadLayout.startRefresh();
        selectGoodsByName();
    }

    @Override
    protected Object getTitleText() {
        return "超值特惠";
    }

    @OnClick({/*R.id.tv_none_hint,*/ R.id.tv_operation, R.id.iv_back, R.id.iv_shop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

//            case R.id.tv_none_hint:
//            selectGoodsByName();
////                swipeToLoadLayout.startRefresh();
//            break;

            case R.id.tv_operation:
            case R.id.iv_shop:
                startActivity(new Intent(this, ShopCateActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(int position, int childPosition, int type) {

    }

    @Override
    public void onRefresh() {
        selectGoodsByName();
    }

    /**
     * 活动列表
     */
    private void selectGoodsByName() {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            UserVo userVo = ((BaseApplication) getApplication()).getUserVo();

            map.put("token", userVo.getToken());
            map.put("cityCode", userVo.getCityCode());
            map.put("activityId", activityId);
            String des = new Gson().toJson(map);
            LogUtils.e("活动列表", "des-->" + des);
            params = DES.encryptDES(des);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("活动列表", "Exception-->" + e.toString());
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.activityAPI_list)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
//                swipeToLoadLayout.stopRefresh();

                LogUtils.e("活动列表", "body---->" + body);
                GoodsListInfoListVo listVo = new Gson().fromJson(body, GoodsListInfoListVo.class);
                if (listVo != null && listVo.getProductionInfoList() != null && !listVo.getProductionInfoList().isEmpty()) {
//                    tvNoneHint.setVisibility(View.GONE);
//                    swipeToLoadLayout.setRefreshEnabled(false);
                    ArrayList<String> title = new ArrayList();
                    if (fragments == null){
                        fragments = new ArrayList<PromotionListFragment>();
                    }else {
                        fragments.clear();
                    }

                    for(ProductionInfoVo productionInfoVo : listVo.getProductionInfoList()){
                        String goodsTitle = productionInfoVo.getGoodsBaseType();
                        title.add(goodsTitle);
                        fragments.add(PromotionListFragment.Instanst(goodsTitle, (ArrayList) productionInfoVo.getGoodsList()));
                    }
                    pageAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, title);
                    vpList.setAdapter(pageAdapter);
                    tlItem.setupWithViewPager(vpList);
//                    adapter.setList(listVo.getProductionInfoList(), 0);

                } else {
                    tlItem.removeAllViews();
                    vpList.removeAllViews();
//                    if (adapter.getCount() > 0) {
//                        adapter.getList().clear();
//                        adapter.notifyDataSetChanged();
//                    }
//                    tvNoneHint.setVisibility(View.VISIBLE);
                    showToast("该活动暂无商品！");
                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
//                swipeToLoadLayout.stopRefresh();
                showToast(errorMsg);
            }
        });
    }

    public void setShoppingCatNum() {
        if (ComParams.shop_cat_num > 0) {
            tvNoticeNum.setText(String.valueOf(ComParams.shop_cat_num));
            tvNoticeNum.setVisibility(View.VISIBLE);
        } else {
            tvNoticeNum.setVisibility(View.GONE);
        }
//        ((ShopCatFragemnt) fragments.get(3)).onRefresh();
    }
}
