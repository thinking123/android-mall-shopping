package com.lf.shoppingmall.activity.red_bag;

import android.os.Bundle;
import android.view.View;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.main.CommonOrderListFragment;
import com.lf.shoppingmall.base.BaseFragment;
import com.lf.shoppingmall.bean.index.ProductionInfoVo;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 我的红包片段
 * Created by Administrator on 2017/8/11.
 */

public class RedBagFragment extends BaseFragment {

    private int type;


    public static RedBagFragment Instanst(int type) {
        RedBagFragment mInstance = new RedBagFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        mInstance.setArguments(bundle);
        return mInstance;
    }

    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_red_bag;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

        type = getArguments().getInt("type", 0);
    }
}
