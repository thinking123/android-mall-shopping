package com.lf.shoppingmall.activity.order;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.red_bag.RedBagFragment;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseFragment;
import com.lf.shoppingmall.base.MyPagerAdapter;
import com.lr.baseview.utils.ImmersionStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的订单
 * Created by Administrator on 2017/8/11.
 */

public class MyOrderActivity extends BaseActivity {

    @Bind(R.id.tl_item)
    TabLayout tlItem;
    @Bind(R.id.vp_list)
    ViewPager vpList;
    private MyPagerAdapter pageAdapter;
    public List<MyOrderFragment> fragments;

    private int page;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        page = getIntent().getIntExtra("page", 0);
        String[] titles = new String[]{"全部", "待配货", "待发货", "待收货", "已完成", "已取消"};
        List<String> titleList = new ArrayList<>();
        fragments = new ArrayList<>();
        int i = 0;
        for (String title : titles) {
            tlItem.addTab(tlItem.newTab());
            titleList.add(title);
            fragments.add(MyOrderFragment.Instanst(i));
            i++;
        }
        pageAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, titleList);
        vpList.setAdapter(pageAdapter);
        tlItem.setupWithViewPager(vpList);
        if (page > 0 && page < titles.length) {
            vpList.setCurrentItem(page, false);
        }
        vpList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected Object getTitleText() {
        return "我的订单";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        fragments.get(page).onActivityResult(requestCode, resultCode, data);
    }
}
