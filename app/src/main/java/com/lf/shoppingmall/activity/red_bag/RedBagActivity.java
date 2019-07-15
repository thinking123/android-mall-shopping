package com.lf.shoppingmall.activity.red_bag;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.login.TermActivity;
import com.lf.shoppingmall.activity.main.CommonOrderListFragment;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseFragment;
import com.lf.shoppingmall.base.MyPagerAdapter;
import com.lr.baseview.utils.ImmersionStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的红包
 * Created by Administrator on 2017/8/11.
 */

public class RedBagActivity extends BaseActivity {
    @BindView(R.id.tl_item)
    TabLayout tlItem;
    @BindView(R.id.vp_list)
    ViewPager vpList;
    private MyPagerAdapter pageAdapter;
    private List<BaseFragment> fragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_red_bag;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        String[] titles = new String[]{"未使用", "已使用", "已过期"};
        List<String> titleList = new ArrayList<>();
        fragments = new ArrayList<>();
        int i = 0;
        for (String title : titles) {
            tlItem.addTab(tlItem.newTab());
            titleList.add(title);
            fragments.add(RedBagFragment.Instanst(i));
            i++;
        }
        pageAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, titleList);
        vpList.setAdapter(pageAdapter);
        tlItem.setupWithViewPager(vpList);
    }

    @Override
    protected void setTitleBarStatus(ImageView iv_back, TextView tv_title, TextView tv_right, ImageView iv_right) {
        super.setTitleBarStatus(iv_back, tv_title, tv_right, iv_right);
        tv_right.setText("规则");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(this);
    }

    @Override
    protected Object getTitleText() {
        return "我的红包";
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_right:
                startActivity(new Intent(this, TermActivity.class).putExtra("type", 2));
                break;
        }
    }
}
