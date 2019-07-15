package com.lf.shoppingmall.activity.order;

import android.os.Bundle;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.adapter.OrderPayListAdapter;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lr.baseview.utils.ImmersionStatus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品清单
 * Created by Administrator on 2017/9/2.
 */

public class OrderPayListActivity extends BaseActivity {

    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    private OrderPayListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_pay_list;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        ArrayList<GoodsVo> goodsVoList = (ArrayList<GoodsVo>) getIntent().getSerializableExtra("goodsVoList");
        if (goodsVoList == null || goodsVoList.isEmpty()) {
            showToast("信息有误");
            finish();
            return;
        }

        adapter = new OrderPayListAdapter(context , goodsVoList);
        swipeTarget.setAdapter(adapter);
    }

    @Override
    protected Object getTitleText() {
        return "商品清单";
    }
}
