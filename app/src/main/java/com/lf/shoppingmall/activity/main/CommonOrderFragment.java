package com.lf.shoppingmall.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.custom_service.CustomServiceMainActivity;
import com.lf.shoppingmall.activity.goods.SearchGoodsActivity;
import com.lf.shoppingmall.base.BaseFragment;
import com.lf.shoppingmall.base.MyPagerAdapter;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.index.ProductionInfoVo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 常用清单
 * Created by Administrator on 2017/8/5.
 */

public class CommonOrderFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

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
    @BindView(R.id.tl_item)
    TabLayout tlItem;
    @BindView(R.id.tv_tab_hint)
    TextView tvTabHint;
    @BindView(R.id.cbox_arrow)
    CheckBox cboxArrow;
    @BindView(R.id.vp_list)
    ViewPager vpList;
    private MyPagerAdapter pageAdapter;
    private List<CommonOrderListFragment> fragments;

    private int type;//0显示搜索框 1不显示
    private int currnetPage = 0;

    public static CommonOrderFragment Instanst(int type, ArrayList<ProductionInfoVo> ProductionInfoList) {
        CommonOrderFragment mInstance = new CommonOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putParcelableArrayList("ProductionInfoList", ProductionInfoList);
        mInstance.setArguments(bundle);
        return mInstance;
    }

    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common_order;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        type = bundle.getInt("type", 0);
        if (type == 0) {
            view.findViewById(R.id.search_bar).setVisibility(View.VISIBLE);
            tvRight.setVisibility(View.GONE);
            ivCustomService.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner25_green));
            ll_search.setBackgroundResource(R.drawable.bg_corner25_storke_grey);
            ivSearchIcon.setImageResource(R.mipmap.search);
            tvSearchHint.setTextColor(ContextCompat.getColor(context, R.color.text_norm_8));
        }

        cboxArrow.setOnCheckedChangeListener(this);
        ArrayList<ProductionInfoVo> ProductionInfoList = bundle.getParcelableArrayList("ProductionInfoList");
        setOrderList(ProductionInfoList);


        vpList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currnetPage = position;
                fragments.get(currnetPage).onRefresh();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void onRefresh() {
        if (fragments != null) {
            fragments.get(currnetPage).onRefresh();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {

        } else {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    public void setOrderList(ArrayList<ProductionInfoVo> ProductionInfoList) {
        if (ProductionInfoList != null && !ProductionInfoList.isEmpty()) {
            if (fragments == null) {
                fragments = new ArrayList<>();
            }
            List<String> titleList = new ArrayList<>();

            for (ProductionInfoVo productionInfoVo : ProductionInfoList) {

                if (productionInfoVo.getGoodsList() != null && !productionInfoVo.getGoodsList().isEmpty()) {
                    ArrayList goodsList = new ArrayList();
                    goodsList.add(productionInfoVo.getGoodsBaseType());
                    goodsList.addAll(productionInfoVo.getGoodsList());
                    titleList.add(productionInfoVo.getGoodsBaseType());
                    tlItem.addTab(tlItem.newTab());
                    fragments.add(CommonOrderListFragment.Instanst(productionInfoVo.getGoodsBaseType(), goodsList));
                }
            }
            pageAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments, titleList);
            vpList.setAdapter(pageAdapter);
            tlItem.setupWithViewPager(vpList);

        }
        //TODO 处理
//        for (BaseFragment fragment : fragments){
//            ((CommonOrderListFragment)fragment).setOrderList(ProductionInfoList);
//        }
//        String[] titles = new String[]{"肉禽类", "新鲜蔬菜", "米面粮油"};
//
//        fragments = new ArrayList<>();
//        for (String title : titles) {
//            tlItem.addTab(tlItem.newTab());
//            titleList.add(title);
//            fragments.add(CommonOrderListFragment.Instanst(1, ProductionInfoList));
//        }
//        pageAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments, titleList);
//        vpList.setAdapter(pageAdapter);
//        tlItem.setupWithViewPager(vpList);
    }
}
