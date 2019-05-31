package com.lf.shoppingmall.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.goods.GoodsDetail0Activity;
import com.lf.shoppingmall.activity.goods.GoodsDetailActivity;
import com.lf.shoppingmall.adapter.AllOrderCateAdapter;
import com.lf.shoppingmall.adapter.AllOrderListAdapter;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.base.BaseFragment;
import com.lf.shoppingmall.bean.AllOrder.GoodsCateVo;
import com.lf.shoppingmall.bean.AllOrder.GoodsCategoryListVo;
import com.lf.shoppingmall.bean.AllOrder.GoodsListInfoListVo;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.index.GuigeVo;
import com.lf.shoppingmall.bean.other.GoodsDetailVo;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.common.OnExpandItemClidkListener;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.common.OnRvItemClickListener;
import com.lr.baseview.widget.AlertDialog;
import com.lr.baseview.widget.AlertEditDialog;
import com.lr.baseview.widget.RVCommonItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 所有订单的详细类页面
 * <p>
 * TODO 主界面侧滑
 * Created by Administrator on 2017/8/10.
 */

public class AllOrderListFragment extends BaseFragment implements OnRvItemClickListener, OnLoadMoreListener, OnRefreshListener, OnExpandItemClidkListener {

    @Bind(R.id.rv_cate)
    RecyclerView rvCate;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @Bind(R.id.swipe_target)
    ListView rvList;
    @Bind(R.id.cbox_promotion)
    TextView cboxPromotion;
    @Bind(R.id.cbox_sales_volume)
    TextView cboxSalesVolume;
    @Bind(R.id.cbox_screen)
    TextView cboxScreen;
    @Bind(R.id.ll_screen)
    LinearLayout llScreen;
    @Bind(R.id.line_screen)
    View line_screen;
    @Bind(R.id.tv_none_hint)
    TextView tv_none_hint;
    private AllOrderListAdapter listAdapter;
    private AllOrderCateAdapter cateAdapter;

    private String parentID;
    private ArrayList<GoodsCateVo> goodsCateVos;

    public static AllOrderListFragment Instanst(String parentID, ArrayList<GoodsCateVo> goodsCateVos) {
        AllOrderListFragment mInstance = new AllOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("parentID", parentID);
        bundle.putParcelableArrayList("goodsCateVos", goodsCateVos);
        mInstance.setArguments(bundle);
        return mInstance;
    }

    @Override
    protected void init() {
        Bundle bundle = getArguments();
        parentID = bundle.getString("parentID");
        goodsCateVos = bundle.getParcelableArrayList("goodsCateVos");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_order_list;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);

        tv_none_hint.setText("暂无数据，点击重新加载");
        cateAdapter = new AllOrderCateAdapter(context, goodsCateVos, this);
        rvCate.setLayoutManager(new LinearLayoutManager(context));
        rvCate.addItemDecoration(new RVCommonItemDecoration(context, LinearLayoutManager.VERTICAL));
        rvCate.setAdapter(cateAdapter);

        listAdapter = new AllOrderListAdapter(context, null, this);
//        rvList.setLayoutManager(new LinearLayoutManager(context));
        rvList.setAdapter(listAdapter);

        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.startRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(int position, int type) {
//        if (type == 0) {
//            llScreen.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
//            line_screen.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
//            if (cateAdapter.curruntSelect != position) {
        cateAdapter.curruntSelect = position;
        cateAdapter.notifyDataSetChanged();
        swipeToLoadLayout.startRefresh();
//            }
//        }
    }

    @OnClick({R.id.cbox_promotion, R.id.cbox_sales_volume, R.id.cbox_screen, R.id.tv_none_hint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cbox_promotion:
                if (cboxPromotion.getTag().equals("no")) {
                    cboxPromotion.setTag("yes");
                    cboxPromotion.setTextColor(ContextCompat.getColor(context, R.color.red_mid));
                } else {
                    cboxPromotion.setTag("no");
                    cboxPromotion.setTextColor(ContextCompat.getColor(context, R.color.text_norm_6));
                }
                swipeToLoadLayout.startRefresh();
                break;

            case R.id.cbox_sales_volume:
                if (cboxSalesVolume.getTag().equals("no")) {
                    cboxSalesVolume.setTag("yes");
                    cboxSalesVolume.setTextColor(ContextCompat.getColor(context, R.color.red_mid));
                } else {
                    cboxSalesVolume.setTag("no");
                    cboxSalesVolume.setTextColor(ContextCompat.getColor(context, R.color.text_norm_6));
                }
                swipeToLoadLayout.startRefresh();
                break;

            case R.id.cbox_screen:
                break;

            case R.id.tv_none_hint:
                swipeToLoadLayout.startRefresh();
                break;
        }
    }

    @Override
    public void onLoadMore() {
        searchGoods();
    }

    @Override
    public void onRefresh() {
        searchGoods();
    }

    /**
     * 全部菜品商品查询接口
     */
    private void searchGoods() {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            String cateTwo = cateAdapter.curruntSelect == 0 ? "1" : cateAdapter.getList().get(cateAdapter.curruntSelect).getCategoryId();
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("cityCode", userVo.getCityCode());
            map.put("parentId", parentID);
            map.put("categoryIdTwo", cateTwo);
            String des = new Gson().toJson(map);
            LogUtils.e("全部菜品商品查询接口", "params-->" + des);
            params = DES.encryptDES(des);

        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.get().url(Constans.selectGoodsList)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MainActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                swipeToLoadLayout.stopRefresh();
                swipeToLoadLayout.stopLoadMore();
                GoodsListInfoListVo infoListVo = null;
                try {
                    infoListVo = new Gson().fromJson(body, GoodsListInfoListVo.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("全部菜品商品查询接口", e.toString());
                }

                if (infoListVo != null && infoListVo.getGoodsListInfoList() != null && !infoListVo.getGoodsListInfoList().isEmpty()) {
                    listAdapter.setList(infoListVo.getGoodsListInfoList(), 0);
                    tv_none_hint.setVisibility(View.GONE);
                } else {
                    tv_none_hint.setVisibility(View.VISIBLE);
                    if (listAdapter.getCount() > 0) {
                        listAdapter.getList().clear();
                        listAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                swipeToLoadLayout.stopRefresh();
                swipeToLoadLayout.stopLoadMore();
                showToast(errorMsg);
            }
        });
    }

    @Override
    public void onItemClick(int position, final int childPosition, int type) {
        final GoodsVo goodsVo = (GoodsVo) listAdapter.getList().get(position);
        switch (type) {
            case -1:
                selectGoods(goodsVo.getId());
//                startActivityForResult(new Intent(getActivity(), GoodsDetailActivity.class), ComParams.RESUTLT_SHOP_CAT);
                break;

            case 0:
                break;

            case 1:
                goodsVo.setGuige(!goodsVo.isGuige());
//                adapter.notifyItemChanged(position);
                listAdapter.notifyDataSetChanged();
                break;

            case 2: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                setCatNum(goodsVo.getId(), guigeVo, 1, 1);
//                ComParams.shop_cat_num++;
//                setCatNum();
//                adapter.notifyItemChanged(position);
//                listAdapter.notifyDataSetChanged();
            }
            break;

            case 3: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                setCatNum(goodsVo.getId(), guigeVo, 1, 1);
//                ComParams.shop_cat_num++;
//                setCatNum();
//                adapter.notifyItemChanged(position);
//                listAdapter.notifyDataSetChanged();
            }
            break;

            case 4: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                int carNum = guigeVo.getCarGoodNum();
                setCatNum(goodsVo.getId(), guigeVo, carNum + 1, 1);
//                int totalweight = Integer.valueOf(guigeVo.getTotalWeight());
//                if (carNum + 1 > totalweight) {
//                    showToast("最多可购" + totalweight + "件");
//                } else {
//                    guigeVo.setCarGoodNum(carNum + 1);
//                    ComParams.shop_cat_num++;
//                    setCatNum();
//                adapter.notifyItemChanged(position);
//                    listAdapter.notifyDataSetChanged();
//                }
            }
            break;

            case 5: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                setCatNum(goodsVo.getId(), guigeVo, guigeVo.getCarGoodNum() - 1, -1);
//                guigeVo.setCarGoodNum(guigeVo.getCarGoodNum() - 1);
//                ComParams.shop_cat_num--;
//                setCatNum();
//                adapter.notifyItemChanged(position);
//                listAdapter.notifyDataSetChanged();
            }
            break;

            case 6:
                new AlertEditDialog(context).builder()
                        .setTitle("购买数量")
                        .setMsg("请输入数量")
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton("确定", new AlertEditDialog.OnPClick() {
                            @Override
                            public void onClick(View v, String result) {
                                int num = Integer.valueOf(result);
                                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                                setCatNum(goodsVo.getId(), guigeVo, num, -1);
                            }
                        })
                        .show();
                break;
        }
    }

    private void setCatNum(String parentIdn, final GuigeVo guigeVo, final int num, final int shop_cat_num) {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("goodsId", parentIdn);
            map.put("specId", guigeVo.getId());
            map.put("num", String.valueOf(num));
            String des = new Gson().toJson(map);
            LogUtils.e("添加或减少购物车的数量", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("添加或减少购物车的数量", "Exception-->" + e.toString());
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.addCar)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MainActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("添加或减少购物车的数量", "body---->" + body);
                guigeVo.setCarGoodNum(num);
                listAdapter.notifyDataSetChanged();
                ComParams.shop_cat_num = ComParams.shop_cat_num + shop_cat_num;
                ((MainActivity) getActivity()).setShoppingCatNum();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
//        guigeVo.setCarGoodNum(num);
//        adapter.notifyDataSetChanged();
//        ComParams.shop_cat_num = ComParams.shop_cat_num + shop_cat_num;
//        ((MainActivity) getActivity()).setShoppingCatNum();
    }

    private void setCatNum() {
        ((MainActivity) getActivity()).setShoppingCatNum();
    }

    /**
     * 商品详情
     *
     * @param id
     */
    private void selectGoods(String id) {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("goodsId", id);
            String des = new Gson().toJson(map);
            LogUtils.e("商品详情", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.selectGoods)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MainActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("商品详情", "body---->" + body);
                GoodsDetailVo goods = new Gson().fromJson(body, GoodsDetailVo.class);
//                LogUtils.e("商品详情", "goods---->" + goods.toString());

                if (goods != null && goods.getGoods() != null) {
                    startActivityForResult(new Intent(getActivity(), GoodsDetail0Activity.class)
                                    .putExtra("GoodsVo", (Serializable) goods.getGoods()),
                            ComParams.RESUTLT_SHOP_CAT);
                } else {

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
