package com.lf.shoppingmall.activity.custom_service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.adapter.ReceiverAdapter;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.address.MyAddress;
import com.lf.shoppingmall.bean.address.MyAddressListBean;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.common.OnRvItemClickListener;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.widget.CheckableTextView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收件人列表
 * Created by Administrator on 2017/8/27.
 */

public class ReceiverActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, OnRvItemClickListener {
    @BindView(R.id.tv_right)
    CheckableTextView tvRight;
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.tv_none_hint)
    TextView tvNoneHint;
    private ReceiverAdapter receiverAdapter;

    private int type = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receiver;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        type = getIntent().getIntExtra("type", 0);

        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("添加");
        tvRight.setOnClickListener(this);

        swipeToLoadLayout.setLoadingMore(false);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);

        tvNoneHint.setText("暂无收件人信息，点击添加");
        tvNoneHint.setOnClickListener(this);

//        swipeTarget.setLayoutManager(new LinearLayoutManager(context));
        receiverAdapter = new ReceiverAdapter(context, null, this);
        swipeTarget.setAdapter(receiverAdapter);

        MyAddressListBean listBean = (MyAddressListBean) getIntent().getSerializableExtra("MyAddressListBean");
        if (listBean != null && listBean.getStoreAddressList() != null && !listBean.getStoreAddressList().isEmpty()) {
            LogUtils.e("收获人列表", "listBean.getStoreAddressList()-->" + listBean.getStoreAddressList());
            receiverAdapter.setList(listBean.getStoreAddressList(), 0);
            tvNoneHint.setVisibility(View.GONE);
        } else {
            swipeToLoadLayout.startRefresh();
        }
    }

    @Override
    protected Object getTitleText() {
        return "收货人列表";
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == tvNoneHint || v == tvRight) {
            startActivityForResult(new Intent(ReceiverActivity.this, ReceiverAddActivity.class), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            swipeToLoadLayout.startRefresh();
            setResult(RESULT_OK);
        }
    }

    @Override
    public void onRefresh() {
        addresslistByStoreId();
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.stopLoadMore();
    }

    @Override
    public void onItemClick(int position, int type) {
        MyAddress address = receiverAdapter.getList().get(position);
        if (type == 0) {
            updateforaddress(address.getAddressId(), position);
        } else if (this.type == 0) {
            Intent intent = new Intent();
            intent.putExtra("MyAddress", address);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * 收获人列表
     */
    private void addresslistByStoreId() {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
            map.put("token", userVo.getToken());
            String des = new Gson().toJson(map);
            LogUtils.e("收获人列表", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        showLoading("");
        OkHttpUtils.get().url(Constans.addresslistByStoreId)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
//                dissmissLoading();
                swipeToLoadLayout.stopRefresh();
                LogUtils.e("收获人列表", "body---->" + body);
                MyAddressListBean listBean = new Gson().fromJson(body, MyAddressListBean.class);
                if (listBean != null && listBean.getStoreAddressList() != null && !listBean.getStoreAddressList().isEmpty()) {
                    receiverAdapter.setList(listBean.getStoreAddressList(), 0);
                    tvNoneHint.setVisibility(View.GONE);
                } else {
                    tvNoneHint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
//                dissmissLoading();
                swipeToLoadLayout.stopRefresh();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 设置默认收获地址
     */
    private void updateforaddress(String addressId, final int position) {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("addressId", addressId);
            UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
            map.put("token", userVo.getToken());
            String des = new Gson().toJson(map);
            LogUtils.e("设置默认收获地址", "des-->" + des);
            params = DES.encryptDES(des);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("设置默认收获地址", "Exception-->" + e.toString());
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.updateforaddress)
//                        .mediaType(MediaType.parse(getString(R.string.media_type)))
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                swipeToLoadLayout.startRefresh();
                if (type == 1){
                    Intent intent = new Intent();
                    intent.putExtra("MyAddress", receiverAdapter.getItem(position));
                    setResult(RESULT_OK, intent);
                }else {
                    setResult(RESULT_OK);
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
