package com.lf.shoppingmall.activity.order;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.adapter.MyOrderDetailAdapter;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.other.MyOrder;
import com.lf.shoppingmall.utils.ComUtils;
import com.lr.baseview.utils.DateUtils;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.widget.CustomListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单详情
 * Created by Administrator on 2017/9/3.
 */

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_more)
    TextView tv_more;
    @BindView(R.id.clv_goods)
    CustomListView clvGoods;
    @BindView(R.id.ll_more_icon)
    LinearLayout llMoreIcon;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_yunfei)
    TextView tvYunfei;
    @BindView(R.id.tv_yunfei_youhui)
    TextView tvYunfeiYouhui;
    @BindView(R.id.tv_qiankuan)
    TextView tvQiankuan;
    @BindView(R.id.tv_now_price)
    TextView tvNowPrice;
    @BindView(R.id.tv_hsouhuoren)
    TextView tvHsouhuoren;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_buy_date)
    TextView tvBuyDate;
    @BindView(R.id.tv_date_recever)
    TextView tvDateRecever;
    @BindView(R.id.btn_quxiao)
    TextView btnQuxiao;
    @BindView(R.id.btn_agin)
    TextView btnAgin;

    private MyOrderDetailAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        MyOrder order = (MyOrder) getIntent().getSerializableExtra("MyOrder");
        if (order == null) {
            showToast("信息有误");
            finish();
            return;
        }

        tvOrderNumber.setText("订单编号：" + order.getOrderId());
        tvOrderStatus.setText(ComUtils.getOrderStatus(order.getOrderStatus()));
        List<GoodsVo> goodsVos = order.getGoods();
        if (goodsVos != null && !goodsVos.isEmpty()) {
            adapter = new MyOrderDetailAdapter(context, goodsVos);
            int size = goodsVos.size();
            if (size > 2) {
                llMoreIcon.setVisibility(View.VISIBLE);
                tv_more.setText("展开其余"+(size - 2)+"件商品");
                adapter.size = 2;
            } else {
                llMoreIcon.setVisibility(View.GONE);
                adapter.size = goodsVos.size();
            }
            clvGoods.setAdapter(adapter);
        }
        tvGoodsPrice.setText("￥" + order.getTotalPrice());
        tvYunfei.setText("￥" + order.getShipPrice());
        tvNowPrice.setText("￥" + (order.getShipPrice() + order.getTotalPrice()));
        tvHsouhuoren.setText(order.getTrueName() + "\t\t" + order.getTelPhone());
        tvAddress.setText(order.getStoreAddress());
        tvBuyDate.setText(DateUtils.timedate(order.getAddTime()));
    }

    @Override
    protected Object getTitleText() {
        return "订单详情";
    }

    @OnClick({R.id.ll_more_icon, R.id.btn_quxiao, R.id.btn_agin,R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_more_icon:
                adapter.size = adapter.getList().size();
                adapter.notifyDataSetChanged();
                llMoreIcon.setVisibility(View.GONE);
                break;

            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_quxiao:
                break;

            case R.id.btn_agin:
                break;
        }
    }
}
