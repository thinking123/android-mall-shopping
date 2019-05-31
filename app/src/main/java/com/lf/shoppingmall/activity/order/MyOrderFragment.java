package com.lf.shoppingmall.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.main.MainActivity;
import com.lf.shoppingmall.activity.red_bag.RedBagFragment;
import com.lf.shoppingmall.adapter.MyOrderAdapter;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.base.BaseFragment;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.other.MyOrder;
import com.lf.shoppingmall.bean.other.MyOrderDetailBean;
import com.lf.shoppingmall.bean.other.MyOrderList;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.common.OnRvItemClickListener;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单
 * Created by Administrator on 2017/8/11.
 */

public class MyOrderFragment extends BaseFragment implements OnLoadMoreListener, OnRefreshListener, OnRvItemClickListener {

    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.tv_none_hint)
    TextView tvNoneHint;
    private MyOrderAdapter orderAdapter;

    private int page = ComParams.PAGE;
    private int ordreType;

    public static MyOrderFragment Instanst(int type) {
        MyOrderFragment mInstance = new MyOrderFragment();
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
        return R.layout.fargment_my_order;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

        tvNoneHint.setText("去购买");
        ordreType = getArguments().getInt("type", 0);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
//        swipeTarget.setLayoutManager(new LinearLayoutManager(context));
        orderAdapter = new MyOrderAdapter(context, null, this, ordreType);
        swipeTarget.setAdapter(orderAdapter);

        swipeToLoadLayout.startRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_none_hint)
    public void onViewClicked() {
        ComParams.main_current_page = 1;
        getActivity().finish();
    }

    @Override
    public void onLoadMore() {
        page++;
        searchOrder();
    }

    @Override
    public void onRefresh() {
        page = ComParams.PAGE;
        searchOrder();
    }

    /**
     * 订单接口
     */
    private void searchOrder() {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("state", String.valueOf(ordreType - 1));
            String des = new Gson().toJson(map);
            LogUtils.e("订单接口" + ordreType, "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.get().url(Constans.orderList)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MyOrderActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                swipeToLoadLayout.stopLoadMore();
                swipeToLoadLayout.stopRefresh();

                LogUtils.e("订单接口" + ordreType, "body-->" + body);
                MyOrderList myOrderList = new Gson().fromJson(body, MyOrderList.class);
                if (myOrderList != null && myOrderList.getOrderList() != null && !myOrderList.getOrderList().isEmpty()) {
                    orderAdapter.setList(myOrderList.getOrderList(), 0);
                    tvNoneHint.setVisibility(View.GONE);

                } else {
                    if (orderAdapter.getCount() > 0) {
                        orderAdapter.getList().clear();
                        orderAdapter.notifyDataSetChanged();
                    }
                    tvNoneHint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                swipeToLoadLayout.stopLoadMore();
                swipeToLoadLayout.stopRefresh();
                showToast(errorMsg);
            }
        });
    }

    /**
     * @param position
     * @param type     支持自定义事件的多个类型 0订单详情 1再次购买 2取消购买 3评价配送员 4确认收货
     */
    @Override
    public void onItemClick(int position, int type) {
        MyOrder order = orderAdapter.getList().get(position);
        if (type == 0) {
            orderDetail(order.getId());

        } else if (type == 2) {
            cancleOrder(order.getId(), order);

        } else if (type == 3) {
            startActivity(new Intent(getActivity(), PingJiaActivity.class));

        } else if (type == 4) {
//            finishOrder(order.getId());
            getActivity().startActivityForResult(new Intent(getActivity(), SubmitReceverActivity.class)
                    .putExtra("MyOrder", order), 0);
        }
    }

    /**
     * 确认收货
     *
     * @param id
     */
    private void finishOrder(String id) {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("id", id);
            String des = new Gson().toJson(map);
            LogUtils.e("确认收货", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.finishOrder)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MyOrderActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("确认收货", "body-->" + body);
                swipeToLoadLayout.startRefresh();
                ((MyOrderActivity) getActivity()).fragments.get(3).onRefresh();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 取消订单
     *
     * @param id
     */
    private void cancleOrder(String id, final MyOrder order) {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("id", id);
            String des = new Gson().toJson(map);
            LogUtils.e("取消订单", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.cancelOrder)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MyOrderActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("取消订单", "body-->" + body);
                order.setOrderStatus(5);
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 订单详情
     *
     * @param s
     */
    private void orderDetail(String s) {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("orderId", s);
            String des = new Gson().toJson(map);
            LogUtils.e("订单详情", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.orderDetail)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MyOrderActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("订单详情", "body-->" + body);
                MyOrderDetailBean detailBean = new Gson().fromJson(body, MyOrderDetailBean.class);

                LogUtils.e("订单详情", "detailBean-->" + detailBean.toString());
                if (detailBean == null || detailBean.getOrderDetails() == null) {
                    showToast("获取详情失败，请点击重新获取");
                } else {
                    startActivity(new Intent(getActivity(), OrderDetailActivity.class)
                            .putExtra("MyOrder", detailBean.getOrderDetails()));
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == getActivity().RESULT_OK){
            swipeToLoadLayout.startRefresh();
        }
    }
}
