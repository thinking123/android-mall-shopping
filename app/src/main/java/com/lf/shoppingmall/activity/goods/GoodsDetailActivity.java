package com.lf.shoppingmall.activity.goods;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.adapter.GoodsDetailAdapter;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.utils.LogUtils;
import com.lf.shoppingmall.weight.mybannr.MyBanner;
import com.lr.baseview.common.OnRvItemClickListener;
import com.lr.baseview.utils.ImmersionStatus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品详情
 * todo tablayout和recyclerview交互
 * Created by Administrator on 2017/8/11.
 */

public class GoodsDetailActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, OnRvItemClickListener {

    @BindView(R.id.view_myBanner)
    MyBanner viewMyBanner;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.page_top)
    PercentRelativeLayout pageTop;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.rv_detail)
    RecyclerView rvDetail;
    private GoodsDetailAdapter detailAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        ivRight.setImageResource(R.mipmap.icon_custom_service_green);
        tablayout.getBackground().setAlpha(255);
        tvTitle.getBackground().setAlpha(255);
        pageTop.getBackground().setAlpha(255);
        appBarLayout.addOnOffsetChangedListener(this);

        tablayout.addTab(tablayout.newTab().setText("商品"));
        tablayout.addTab(tablayout.newTab().setText("详细信息"));
        tablayout.addTab(tablayout.newTab().setText("规格参数"));
        initBananr();
        rvDetail.setLayoutManager(new LinearLayoutManager(context));
        detailAdapter = new GoodsDetailAdapter(context,null, this);
        rvDetail.setAdapter(detailAdapter);
//        tablayout.getChildAt(1).setSelected(true);
    }

    @Override
    protected Object getTitleText() {
        return "[龙海]鸡排骨 冻";
    }

    private void initBananr() {
        viewMyBanner.setImagesList(new int[]{R.mipmap.guide_2, R.mipmap.guide_3, R.mipmap.guide_4, R.mipmap.guide_2},
                null, new MyBanner.OnImageClickListener() {
                    @Override
                    public void onImageClick(int position) {

                    }
                });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        LogUtils.e("滑动", "verticalOffset---->" + verticalOffset);
        if (verticalOffset < 10){
            tvTitle.getBackground().setAlpha(0);
            tablayout.getBackground().setAlpha(0);
            pageTop.getBackground().setAlpha(0);
        }else {
            tvTitle.getBackground().setAlpha(255);
            tablayout.getBackground().setAlpha(255);
            pageTop.getBackground().setAlpha(255);
        }
    }

    @Override
    public void onItemClick(int position, int type) {

    }
}
