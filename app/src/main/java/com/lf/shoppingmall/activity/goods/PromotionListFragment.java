package com.lf.shoppingmall.activity.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.main.CommonOrderListFragment;
import com.lf.shoppingmall.activity.main.MainActivity;
import com.lf.shoppingmall.adapter.ComonOrderListAdapter;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.base.BaseFragment;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.index.GuigeVo;
import com.lf.shoppingmall.bean.index.IndexVo;
import com.lf.shoppingmall.bean.index.ProductionInfoVo;
import com.lf.shoppingmall.bean.other.GoodsDetailVo;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.common.OnExpandItemClidkListener;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.widget.AlertDialog;
import com.lr.baseview.widget.AlertEditDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/3.
 */

public class PromotionListFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener, OnExpandItemClidkListener {

    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.tv_none_hint)
    TextView tv_none_hint;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private ComonOrderListAdapter adapter;

    private String type;
    private int page;
    private int exeAddtotal;
    private int exeSubtotal;

    public static PromotionListFragment Instanst(String type, ArrayList<GoodsVo> ProductionInfoList) {
        PromotionListFragment mInstance = new PromotionListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putSerializable("ProductionInfoList", ProductionInfoList);
        mInstance.setArguments(bundle);
        return mInstance;
    }

    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common_order_list;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        type = bundle.getString("type");
        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        ArrayList<GoodsVo> ProductionInfoList = (ArrayList<GoodsVo>) bundle.getSerializable("ProductionInfoList");
        adapter = new ComonOrderListAdapter(context, ProductionInfoList, this);
        adapter.status = 1;
        swipeTarget.setAdapter(adapter);
        tv_none_hint.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void refresh(List<GoodsVo> goodsVos){
        adapter.setList(goodsVos, 0);
    }

    @Override
    public void onRefresh() {
        page = ComParams.PAGE;
//        ((MainActivity) getActivity()).refreshCommonOrder();
        getIndex();
    }

    @Override
    public void onLoadMore() {
        page++;
//        ((MainActivity) getActivity()).refreshCommonOrder();
        getIndex();
    }

    /**
     * @param position
     * @param type     支持自定义事件的多个类型
     *                 0删除 1规格 2现在购买 3购买 4加 5减  todo6输入数字
     */
    @Override
    public void onItemClick(final int position, final int childPosition, int type) {
        final GoodsVo goodsVo = (GoodsVo) adapter.getList().get(position);
        switch (type) {
            case -1:
                selectGoods(goodsVo.getId());
//                startActivityForResult(new Intent(getActivity(), GoodsDetailActivity.class), ComParams.RESUTLT_SHOP_CAT);
                break;

            case 0:
                new AlertDialog(context).builder().setTitle("提示")
                        .setMsg("确定从常用清单中移除该商品吗?")
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                removeList(goodsVo.getId() ,position);
                            }
                        })
                        .show();

                break;

            case 1:
                goodsVo.setGuige(!goodsVo.isGuige());
//                adapter.notifyItemChanged(position);
                adapter.notifyDataSetChanged();
                break;

            case 2: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                setCatNum(goodsVo.getId(), guigeVo, 1, 1);
//                adapter.notifyItemChanged(position);
//                guigeVo.setCarGoodNum(1);
//                ComParams.shop_cat_num++;
//                adapter.notifyDataSetChanged();
            }
            break;

            case 3: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                setCatNum(goodsVo.getId(), guigeVo, 1, 1);
//                adapter.notifyItemChanged(position);
//                guigeVo.setCarGoodNum(1);
//                ComParams.shop_cat_num++;
//                adapter.notifyDataSetChanged();
            }
            break;

            case 4: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                int carNum = guigeVo.getCarGoodNum();
//                int totalweight = Integer.valueOf(guigeVo.getTotalWeight());
//                if (carNum + 1 > totalweight) {
//                    showToast("最多可购" + totalweight + "件");
//                } else {
                setCatNum(goodsVo.getId(), guigeVo, carNum + 1, 1);
//                adapter.notifyItemChanged(position);
//                    guigeVo.setCarGoodNum(carNum + 1);
//                    ComParams.shop_cat_num++;
//                    adapter.notifyDataSetChanged();
//                }
            }
            break;

            case 5: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                setCatNum(goodsVo.getId(), guigeVo, guigeVo.getCarGoodNum() - 1, -1);
//                adapter.notifyItemChanged(position);
//                guigeVo.setCarGoodNum(guigeVo.getCarGoodNum() - 1);
//                ComParams.shop_cat_num--;
//                adapter.notifyDataSetChanged();
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

    /**
     * 移除清单
     * @param id
     */
    private void removeList(String id, final int position) {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("goodsId", id);
            String des = new Gson().toJson(map);
            LogUtils.e("移除清单", "des-->" + des);
            params = DES.encryptDES(des);

        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.removeList)
                .addParams("params", params)
                .build().execute(new MyStringCallback((PromotionListActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("移除清单", "body-->" + body);
                adapter.getList().remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 添加或减少购物车的数量
     * @param parentIdn
     * @param guigeVo
     * @param num
     * @param shop_cat_num
     */
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
                .build().execute(new MyStringCallback((PromotionListActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("添加或减少购物车的数量", "body---->" + body);
                //// TODO: 2017/8/22
                guigeVo.setCarGoodNum(num);
                adapter.notifyDataSetChanged();
                ComParams.shop_cat_num = ComParams.shop_cat_num + shop_cat_num;
                ((PromotionListActivity) getActivity()).setShoppingCatNum();
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

    @OnClick(R.id.tv_none_hint)
    public void onViewClicked() {
        //TODO 通知主界面加载数据
//        ((MainActivity) getActivity()).refreshCommonOrder();
        swipeToLoadLayout.startRefresh();
    }


    private void getIndex() {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            String des = new Gson().toJson(map);
            LogUtils.e("首页", "des-->" + des);
            params = DES.encryptDES(des);
            LogUtils.e("首页", "params-->" + params);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("首页", "Exception-->" + e.toString());
        }
        OkHttpUtils.get().url(Constans.INDEX_LIST)
                .addParams("params", params)
                .build().execute(new MyStringCallback((PromotionListActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                swipeToLoadLayout.stopRefresh();
                swipeToLoadLayout.stopLoadMore();

                IndexVo indexVo = new Gson().fromJson(body, IndexVo.class);

                if (indexVo != null) {
                    ArrayList<ProductionInfoVo> ProductionInfoList = (ArrayList<ProductionInfoVo>) indexVo.getProductionInfoList();
                    if (ProductionInfoList != null && !ProductionInfoList.isEmpty()) {
                        for (ProductionInfoVo productionInfoVo : ProductionInfoList) {
                            if (productionInfoVo.getGoodsBaseType().equals(type)) {
                                ArrayList goodsList = new ArrayList();
                                goodsList.add(productionInfoVo.getGoodsBaseType());
                                if (productionInfoVo.getGoodsList() != null && !productionInfoVo.getGoodsList().isEmpty()) {
                                    goodsList.addAll(productionInfoVo.getGoodsList());
                                    adapter.setList(goodsList, 0);
                                }
                            }
                        }
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


    public void setOrderList(ArrayList<ProductionInfoVo> ProductionInfoList) {
        if (ProductionInfoList != null && !ProductionInfoList.isEmpty()) {
            adapter.setList(ProductionInfoList, 0);
            tv_none_hint.setVisibility(View.GONE);
        } else {
            tv_none_hint.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 商品详情
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
                .build().execute(new MyStringCallback((PromotionListActivity) getActivity()) {
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
