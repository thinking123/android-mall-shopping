package com.lf.shoppingmall.activity.main;

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
import com.lf.shoppingmall.activity.goods.GoodsDetailActivity;
import com.lf.shoppingmall.adapter.ComonOrderListAdapter;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.base.BaseFragment;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.index.GuigeVo;
import com.lf.shoppingmall.bean.index.IndexVo;
import com.lf.shoppingmall.bean.index.ProductionInfoVo;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.common.OnExpandItemClidkListener;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.widget.AlertDialog;
import com.lr.baseview.widget.CustomListView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/18.
 */

public class CommonOrderListForScroll extends BaseFragment implements OnExpandItemClidkListener {

    @BindView(R.id.swipe_target)
    CustomListView swipeTarget;
//    @BindView(R.id.tv_none_hint)
//    TextView tv_none_hint;
    private ComonOrderListAdapter adapter;

    private String type;
    private int page;
    private int exeAddtotal;
    private int exeSubtotal;

    public static CommonOrderListForScroll Instanst(String type, ArrayList ProductionInfoList) {
        CommonOrderListForScroll mInstance = new CommonOrderListForScroll();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putParcelableArrayList("ProductionInfoList", ProductionInfoList);
        mInstance.setArguments(bundle);
        return mInstance;
    }

    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fargment_common_list_forscroll;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        type = bundle.getString("type");

//        tv_none_hint.setVisibility(View.GONE);
        adapter = new ComonOrderListAdapter(context, null, this);
        swipeTarget.setAdapter(adapter);

        ArrayList<ProductionInfoVo> ProductionInfoList = bundle.getParcelableArrayList("ProductionInfoList");
        if (ProductionInfoList != null && !ProductionInfoList.isEmpty()) {
            adapter.setList(ProductionInfoList, 0);
//            tv_none_hint.setVisibility(View.GONE);
        } else {
//            tv_none_hint.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * @param position
     * @param type     支持自定义事件的多个类型
     *                 0删除 1规格 2现在购买 3购买 4加 5减  todo6输入数字
     */
    @Override
    public void onItemClick(final int position, int childPosition, int type) {
        GoodsVo goodsVo = (GoodsVo) adapter.getList().get(position);
        switch (type) {
            case -1:
                startActivityForResult(new Intent(getActivity(), GoodsDetailActivity.class), ComParams.RESUTLT_SHOP_CAT);
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
                                adapter.getList().remove(position);
                                adapter.notifyDataSetChanged();
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
                guigeVo.setCarGoodNum(1);
                ComParams.shop_cat_num++;
                adapter.notifyDataSetChanged();
            }
            break;

            case 3: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                setCatNum(goodsVo.getId(), guigeVo, 1, 1);
//                adapter.notifyItemChanged(position);
                guigeVo.setCarGoodNum(1);
                ComParams.shop_cat_num++;
                adapter.notifyDataSetChanged();
            }
            break;

            case 4: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                int carNum = guigeVo.getCarGoodNum();
                int totalweight = Integer.valueOf(guigeVo.getTotalWeight());
                if (carNum + 1 > totalweight) {
                    showToast("最多可购" + totalweight + "件");
                } else {
                    setCatNum(goodsVo.getId(), guigeVo, carNum + 1, 1);
//                adapter.notifyItemChanged(position);
                    guigeVo.setCarGoodNum(carNum + 1);
                    ComParams.shop_cat_num++;
                    adapter.notifyDataSetChanged();
                }
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
                //TODO  判断可购数量  购物车数量的增加，
//                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
//                int oldNum =

                break;
        }
    }

    private void setCatNum(String parentIdn, GuigeVo guigeVo, int num, int shop_cat_num) {
//        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
//        String params = null;
//        try {
//            Map<String, String> map = new HashMap<>();
//            map.put("token", userVo.getToken());
//            String des = new Gson().toJson(map);
//            LogUtils.e("首页", "des-->" + des);
//            params = DES.encryptDES(des);
//            LogUtils.e("首页", "params-->" + params);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogUtils.e("首页", "Exception-->" + e.toString());
//        }
//        showLoading("");
//        OkHttpUtils.get().url(Constans.INDEX_LIST)
//                .addParams("params", params)
//                .build().execute(new MyStringCallback((MainActivity) getActivity()) {
//            @Override
//            public void onSuccess(String isSucceed, String info, String body) {
//                dissmissLoading();
//                IndexVo indexVo = new Gson().fromJson(body, IndexVo.class);
//
//                if (indexVo != null) {
//
//                }
//            }
//
//            @Override
//            public void onResponseError(String errorMsg, String isSucceed) {
//                dissmissLoading();
//                showToast(errorMsg);
//            }
//        });
        guigeVo.setCarGoodNum(num);
        adapter.notifyDataSetChanged();
        ComParams.shop_cat_num = ComParams.shop_cat_num + shop_cat_num;
        ((MainActivity) getActivity()).setShoppingCatNum();
    }

//    @OnClick(R.id.tv_none_hint)
//    public void onViewClicked() {
//        //TODO 通知主界面加载数据
////        ((MainActivity) getActivity()).refreshCommonOrder();
//        getIndex();
//    }


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
        showLoading("");
        OkHttpUtils.get().url(Constans.INDEX_LIST)
                .addParams("params", params)
                .build().execute(new MyStringCallback((MainActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
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
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }


    public void setOrderList(ArrayList<ProductionInfoVo> ProductionInfoList) {
        if (ProductionInfoList != null && !ProductionInfoList.isEmpty()) {
            adapter.setList(ProductionInfoList, 0);
//            tv_none_hint.setVisibility(View.GONE);
        } else {
//            tv_none_hint.setVisibility(View.VISIBLE);
        }
    }
}

