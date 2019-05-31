package com.lf.shoppingmall.activity.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.goods.GoodsDetail0Activity;
import com.lf.shoppingmall.activity.goods.GoodsDetailActivity;
import com.lf.shoppingmall.activity.goods.ShopCateActivity;
import com.lf.shoppingmall.activity.order.OrderPayActivity;
import com.lf.shoppingmall.activity.order.OrderSuccessActivity;
import com.lf.shoppingmall.adapter.ShopCatAdapter;
import com.lf.shoppingmall.adapter.ShopCatLiListAdapter;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.base.BaseFragment;
import com.lf.shoppingmall.bean.AllOrder.CatSubmitBean;
import com.lf.shoppingmall.bean.AllOrder.GoodsPriceVo;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.index.CarGoodsListVo;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.index.GuigeVo;
import com.lf.shoppingmall.bean.other.GoodsDetailVo;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.common.OnExpandItemClidkListener;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.CommonUilts;
import com.lr.baseview.widget.AlertDialog;
import com.lr.baseview.widget.AlertEditDialog;
import com.lr.baseview.widget.CheckableTextView;
import com.lr.baseview.widget.CustomRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;

import org.apache.commons.logging.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/8.
 */

public class ShopCatFragemnt extends BaseFragment implements OnLoadMoreListener, OnRefreshListener, OnExpandItemClidkListener {
    @Bind(R.id.swipe_target)
    ListView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @Bind(R.id.tv_none_hint)
    TextView tvNoneHint;
    //    @Bind(R.id.ll_des)
//    LinearLayout llDes;
    @Bind(R.id.tv_select)
    TextView tv_select;
    @Bind(R.id.tv_total_price)
    TextView tvTotalPrice;
    @Bind(R.id.tv_operation)
    TextView tvOperation;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ll_opertion)
    LinearLayout ll_opertion;
    @Bind(R.id.ll_price_low_remark)
    LinearLayout ll_price_low_remark;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    CheckableTextView tvRight;
    //    private ShopCatAdapter catAdapter;
    private ShopCatLiListAdapter catAdapter;

    public static ShopCatFragemnt mInstance = null;

    public static ShopCatFragemnt getInstance() {
        if (null == mInstance) {
            synchronized (ShopCatFragemnt.class) {
                if (null == mInstance) {
                    mInstance = new ShopCatFragemnt();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_cat_1;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);

        ivBack.setVisibility(View.GONE);
        tvTitle.setText("购物车");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("编辑");
        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
//        swipeTarget.setLayoutManager(new LinearLayoutManager(context));
        catAdapter = new ShopCatLiListAdapter(context, null, this);
        swipeTarget.setAdapter(catAdapter);

        swipeToLoadLayout.startRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_none_hint, R.id.tv_operation, R.id.tv_right, R.id.tv_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_none_hint:
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).setCurrntPager(1);
                } else {
                    getActivity().finish();
                }

                break;

            case R.id.tv_select:
                String tag = (String) tv_select.getTag();
                if (tag.equals("no")) {
                    priceTatal(true);

                } else {
                    priceTatal(false);
                }
                break;

            case R.id.tv_operation:
                if (catAdapter.isDel) {
                    delOperation();
                } else {
                    //结算
                    ArrayList<GoodsVo> goodsVos = new ArrayList<>();
                    for (GoodsVo goodsVo : catAdapter.getList()) {
                        ArrayList<GuigeVo> guigeVos = new ArrayList<>();
                        for (GuigeVo guigeVo : goodsVo.getGuige()) {
                            if (guigeVo.getCarGoodState() == 1) {
                                guigeVos.add(guigeVo);
                            }
                        }
                        if (!guigeVos.isEmpty()) {
                            goodsVo.setGuige(guigeVos);
                            goodsVos.add(goodsVo);
                        }
                    }
                    if (goodsVos.isEmpty()) {
                        showToast("请选择购买的商品");
                    } else {
                        submitOrder(goodsVos);
                    }
                }
                break;

            case R.id.tv_right:
                String rightText = tvRight.getText().toString();
                if (rightText.equals("编辑")) {
                    tvRight.setText("完成");
                    tvTotalPrice.setVisibility(View.GONE);
                    //必须设置字体值改变 使用此方法
                    setTvOpertionStatus(true);
//                    tvOperation.setText("删除");
//                    tvOperation.setBackgroundColor(getResources().getColor(R.color.red));
                    catAdapter.isDel = true;
                    catAdapter.notifyDataSetChanged();
                    int delCount = 0;
                    for (GoodsVo goodsVo : catAdapter.getList()) {
                        if (goodsVo.getDelStatus() == 1) {
                            delCount++;
                        }
                    }
                    if (delCount >= catAdapter.getCount()) {
                        setTvSelectStatus(true);
                    } else {
                        setTvSelectStatus(false);
                    }

                } else {
                    tvRight.setText("编辑");
                    tvTotalPrice.setVisibility(View.VISIBLE);
                    //必须设置字体值改变 使用此方法
                    setTvOpertionStatus(true);
//                    tvOperation.setText("去结算");
//                    tvOperation.setBackgroundColor(getResources().getColor(R.color.green));
                    catAdapter.isDel = false;
                    catAdapter.notifyDataSetChanged();
                    int carCount = 0;
                    for (GoodsVo goodsVo : catAdapter.getList()) {
                        if (goodsVo.getCarSstatus() == 1) {
                            carCount++;
                        }
                    }
                    if (carCount >= catAdapter.getCount()) {
                        setTvSelectStatus(true);
                    } else {
                        setTvSelectStatus(false);
                    }
                }
                break;
        }
    }

    /**
     * 计算价格
     *
     * @param b true为全选 false全不选
     */
    private void priceTatal(boolean b) {
        JSONObject add = new JSONObject();
        JSONObject sub = new JSONObject();
        try {
            UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
            JSONArray goodsListadd = new JSONArray();

            JSONArray goodsListsub = new JSONArray();
            for (int i = 0; i < catAdapter.getCount(); i++) {
                GoodsVo items = catAdapter.getList().get(i);
                JSONArray goodsSpecadd = new JSONArray();
                JSONArray goodsSpecsub = new JSONArray();

                for (GuigeVo guigeVo : items.getGuige()) {
                    JSONObject guige = new JSONObject();
                    guige.put("carGoodNum", guigeVo.getCarGoodNum());
                    guige.put("id", guigeVo.getId());
                    goodsSpecadd.put(guige);
                }
                JSONObject goodsObjectadd = new JSONObject();
                goodsObjectadd.put("id", items.getGoodsId());
                goodsObjectadd.put("goodsSpec", goodsSpecadd);
                goodsListadd.put(goodsObjectadd);

                JSONObject goodsObjectsub = new JSONObject();
                goodsObjectsub.put("id", items.getGoodsId());
                goodsObjectsub.put("goodsSpec", goodsSpecsub);
                goodsListsub.put(goodsObjectsub);
            }

            add.put("token", userVo.getToken());
            add.put("status", String.valueOf(b ? 1 : 0));//区分是否全选
            add.put("goodsList", goodsListadd);

            sub.put("token", userVo.getToken());
            sub.put("status", String.valueOf(b ? 0 : 1));
            sub.put("goodsList", goodsListsub);

            if (b) {
                countPrice(add, true, b);
                countPrice(sub, false, b);
            } else {
                countPrice(sub, true, b);
                countPrice(add, false, b);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAdapterSelectStatus(int s) {
        if (catAdapter.getCount() > 0) {
            for (GoodsVo goodsVo : catAdapter.getList()) {
                if (catAdapter.isDel) {
                    goodsVo.setDelStatus(s);
                } else {
                    goodsVo.setCarSstatus(s);
                }
                for (GuigeVo guigeVo : goodsVo.getGuige()) {
                    if (catAdapter.isDel) {
                        guigeVo.setDelStatus(s);
                    } else {
                        guigeVo.setCarGoodState(s);
                    }
                }
            }
            catAdapter.notifyDataSetChanged();
        }
    }

    private void setTvSelectStatus(boolean b) {
        if (b) {
            tv_select.setTag("yes");
            Drawable drawable = getResources().getDrawable(R.mipmap.img_pay_selected);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_select.setCompoundDrawables(drawable, null, null, null);
            tv_select.setTextColor(ContextCompat.getColor(context, R.color.text_green));
        } else {
            tv_select.setTag("no");
            Drawable drawable = getResources().getDrawable(R.mipmap.img_pay_unselected);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_select.setCompoundDrawables(drawable, null, null, null);
            tv_select.setTextColor(ContextCompat.getColor(context, R.color.text_norm_6));
        }
    }

    /**
     * 删除操作
     */
    private void delOperation() {
        final JsonArray goodsList = new JsonArray();
        int delcount = 0;
        for (GoodsVo goodsVo : catAdapter.getList()) {
            boolean isHave = false;
            JsonArray jsonArray = null;
            for (GuigeVo guigeVo : goodsVo.getGuige()) {
                if (guigeVo.getDelStatus() == 1) {
                    if (!isHave) {
                        isHave = true;
                        jsonArray = new JsonArray();
                        JsonObject guiige = new JsonObject();
                        guiige.addProperty("id", guigeVo.getId());
                        jsonArray.add(guiige);
                    } else {
                        JsonObject guiige = new JsonObject();
                        guiige.addProperty("id", guigeVo.getId());
                        jsonArray.add(guiige);
                    }
                    delcount++;
                }
            }

            if (jsonArray != null) {
                JsonObject goods = new JsonObject();
                goods.addProperty("id", goodsVo.getGoodsId());
                goods.add("goodsSpec", jsonArray);
                goodsList.add(goods);
            }
        }

        if (delcount == 0) {
            showToast("请选择商品");
        } else {
            new AlertDialog(context).builder()
                    .setMsg("确定删除选中的" + delcount + "件商品吗？")
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //联网
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.add("goodsList", goodsList);
                            UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
                            jsonObject.addProperty("token", userVo.getToken());
                            deleteCar(jsonObject.toString());
                        }
                    })
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onLoadMore() {
        selectCar();
    }

    @Override
    public void onRefresh() {
        selectCar();
    }

    @Override
    public void onResume() {
        super.onResume();
        swipeToLoadLayout.startRefresh();
    }

    @Override
    public void onItemClick(final int position, final int childPosition, int type) {
        final GoodsVo items = catAdapter.getList().get(position);
        switch (type) {
            case -1:
                selectGoods(items.getGoodsId());
                break;

            case 1:
                if (catAdapter.isDel) {//编辑 否更改状态刷新价格
                    if (childPosition == -1) {
                        int status = items.getDelStatus();
                        if (status == 0) {
                            setStatus(items, 1, position);
                        } else {
                            setStatus(items, 0, position);
                        }
                    } else {
                        GuigeVo guigeVo = items.getGuige().get(childPosition);
                        int status = guigeVo.getDelStatus();
                        if (status == 0) {
                            setGuigeState(items, guigeVo, 1, position);
                        } else {
                            setGuigeState(items, guigeVo, 0, position);
                        }
                    }

                } else {
                    JSONObject priceadd = getPrice(1, position, childPosition);
                    countPrice(priceadd, items, position, childPosition, true);
                    JSONObject pricesub = getPrice(0, position, childPosition);
                    countPrice(pricesub, items, position, childPosition, false);
                }
                break;

            case 4: {
                GuigeVo guigeVo = items.getGuige().get(childPosition);
                int carNum = guigeVo.getCarGoodNum();
                setCatNum(items, guigeVo, carNum + 1, 1, position, childPosition);
            }
            break;

            case 5: {//为0 删除
                final GuigeVo guigeVo = items.getGuige().get(childPosition);
                int count = guigeVo.getCarGoodNum() - 1;
                if (count <= 0) {
                    new AlertDialog(context).builder()
                            .setTitle("提示")
                            .setMsg("确定删除选该件商品吗？")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //联网
                                    Guige guige = new Guige();
                                    guige.id = guigeVo.getId();
                                    guige.carGoodNum = 0;
                                    GoodsList goodsList = new GoodsList();
                                    goodsList.id = items.getGoodsId();
                                    goodsList.goodsSpec = new ArrayList<Guige>();
                                    goodsList.goodsSpec.add(guige);
                                    Jsons jsons = new Jsons();
                                    UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
                                    jsons.token = userVo.getToken();
                                    jsons.goodsList = new ArrayList<GoodsList>();
                                    jsons.goodsList.add(goodsList);
                                    deleteCar(new Gson().toJson(jsons));
                                }
                            })
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            })
                            .show();
                } else {
                    setCatNum(items, guigeVo, guigeVo.getCarGoodNum() - 1, -1, position, childPosition);
                }
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
                                GuigeVo guigeVo = items.getGuige().get(childPosition);
                                setCatNum(items, guigeVo, num, -1, position, childPosition);
                            }
                        })
                        .show();
                break;
        }
    }

    /**
     * 计算价格  全选按钮专用
     */
    private void countPrice(JSONObject jsons, final boolean status, final boolean isS) {
        String params = null;
        try {
            LogUtils.e("计算价格", "jsons-->" + jsons.toString());
            params = DES.encryptDES(jsons.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("计算价格", "Exception-->" + e.toString());
        }
        if (status) {
            setTvOpertionStatus(false);
        }
        OkHttpUtils.get().url(Constans.countPrice)
                .addParams("params", params)
                .build().execute(new MyStringCallback((BaseActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("计算价格", "body---->" + body);
                if (status) {
                    setTvOpertionStatus(true);
                    GoodsPriceVo goodsPriceVo = new Gson().fromJson(body, GoodsPriceVo.class);
                    setTotalPrice(goodsPriceVo.getTotalPrice());

                    if (isS) {
                        setTvSelectStatus(true);
                        setAdapterSelectStatus(1);
                    } else {
                        setTvSelectStatus(false);
                        setAdapterSelectStatus(0);
                    }
                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
                setTvOpertionStatus(true);
            }
        });
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
                .build().execute(new MyStringCallback((BaseActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("商品详情", "body---->" + body);
                GoodsDetailVo goods = new Gson().fromJson(body, GoodsDetailVo.class);
                if (goods != null && goods.getGoods() != null) {
                    Intent intent = new Intent(getActivity(), GoodsDetail0Activity.class);
                    intent.putExtra("GoodsVo", (Serializable) goods.getGoods());
                    if (getActivity() instanceof ShopCateActivity) {
                        intent.putExtra("type", 1);
                    }
                    startActivityForResult(intent,
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

    private JSONObject getPrice(int s, int position, int childPosition) {
        JSONObject jsons = new JSONObject();
        try {
            UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
            JSONArray goodsList = new JSONArray();
            for (int i = 0; i < catAdapter.getCount(); i++) {
                GoodsVo items = catAdapter.getList().get(i);
                JSONArray goodsSpec = new JSONArray();

                if (childPosition == -1) {//商品项目的点击
                    if ((position != i && items.getCarSstatus() == s) || (position == i && items.getCarSstatus() != s)) {//非当前项状态相同
                        //或者当前项状态不相同
                        for (GuigeVo guigeVo : items.getGuige()) {
                            JSONObject guige = new JSONObject();
                            guige.put("carGoodNum", guigeVo.getCarGoodNum());
                            guige.put("id", guigeVo.getId());
                            goodsSpec.put(guige);
                        }
                    }
                } else {//规格的点击
                    int y = 0;
                    for (GuigeVo guigeVo : items.getGuige()) {
                        if (position == i) {//
                            if ((childPosition != y && guigeVo.getStatus() == s) || (childPosition == y && guigeVo.getStatus() != s)) {
                                JSONObject guige = new JSONObject();
                                guige.put("carGoodNum", guigeVo.getCarGoodNum());
                                guige.put("id", guigeVo.getId());
                                goodsSpec.put(guige);
                            }
                        } else {
                            if (guigeVo.getStatus() == s) {
                                JSONObject guige = new JSONObject();
                                guige.put("carGoodNum", guigeVo.getCarGoodNum());
                                guige.put("id", guigeVo.getId());
                                goodsSpec.put(guige);
                            }
                        }
                        y++;
                    }

                }

                JSONObject goodsObject = new JSONObject();
                goodsObject.put("id", items.getGoodsId());
                goodsObject.put("goodsSpec", goodsSpec);
                goodsList.put(goodsObject);
            }

            jsons.put("token", userVo.getToken());
            jsons.put("status", String.valueOf(s));
            jsons.put("goodsList", goodsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsons;
    }

    private JSONObject getPriceAddSub(int s) {
        JSONObject jsons = new JSONObject();
        try {
            UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
            JSONArray goodsList = new JSONArray();
            for (int i = 0; i < catAdapter.getCount(); i++) {
                GoodsVo items = catAdapter.getList().get(i);
                JSONArray goodsSpec = new JSONArray();
                for (GuigeVo guigeVo : items.getGuige()) {
                    if (guigeVo.getCarGoodState() == s) {
                        JSONObject guige = new JSONObject();
                        guige.put("carGoodNum", guigeVo.getCarGoodNum());
                        guige.put("id", guigeVo.getId());
                        goodsSpec.put(guige);
                    }
                }
                JSONObject goodsObject = new JSONObject();
                goodsObject.put("id", items.getGoodsId());
                goodsObject.put("goodsSpec", goodsSpec);
                goodsList.put(goodsObject);
            }
            jsons.put("token", userVo.getToken());
            jsons.put("status", String.valueOf(s));
            jsons.put("goodsList", goodsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsons;
    }

    /**
     * 计算价格 item点击选择和加减商品使用
     *
     * @param items
     * @param childPosition
     */
    private void countPrice(JSONObject jsons, final GoodsVo items, final int position, final int childPosition, final boolean status) {
        String params = null;
        try {
            LogUtils.e("计算价格", "jsons-->" + jsons.toString());
            params = DES.encryptDES(jsons.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("计算价格", "Exception-->" + e.toString());
        }
        if (status) {
            setTvOpertionStatus(false);
        }
        OkHttpUtils.get().url(Constans.countPrice)
                .addParams("params", params)
                .build().execute(new MyStringCallback((BaseActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("计算价格", "body---->" + body);
                if (status) {
                    setTvOpertionStatus(true);
                    GoodsPriceVo goodsPriceVo = new Gson().fromJson(body, GoodsPriceVo.class);
                    setTotalPrice(goodsPriceVo.getTotalPrice());
                    //设置选中状态
                    if (items != null) {
                        if (childPosition == -1) {
                            int status = items.getCarSstatus();
                            if (status == 0) {
                                setStatus(items, 1, position);
                            } else {
                                setStatus(items, 0, position);
                            }
                        } else {
                            GuigeVo guigeVo = items.getGuige().get(childPosition);
                            int status = guigeVo.getStatus();
                            if (status == 0) {
                                setGuigeState(items, guigeVo, 1, position);
                            } else {
                                setGuigeState(items, guigeVo, 0, position);
                            }
                        }
                    }

                    //设置购物车数量
                    int totalNUm = 0;
                    for (GoodsVo goodsVo : catAdapter.getList()) {
                        for (GuigeVo guigeVo : goodsVo.getGuige()) {
                            if (guigeVo.getCarGoodState() == 1) {
                                totalNUm += guigeVo.getCarGoodNum();
                            }
                        }
                    }
                    setCarNum(totalNUm);
                }
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
                setTvOpertionStatus(true);
            }
        });
    }

    /**
     * 计算价格中按钮状态
     *
     * @param b 联网状态控制 true为非联网状态 false是联网状态不可点击
     */
    private void setTvOpertionStatus(boolean b) {
        if (b) {
            if (tvRight.getText().toString().equals("完成")) {
                tvOperation.setText("删除");
                tvOperation.setBackgroundColor(getResources().getColor(R.color.red));
                tvOperation.setEnabled(true);

            } else {
                if (ComParams.total_price >= ComParams.total_price_low) {//结算：根据总价判断
                    tvOperation.setEnabled(true);
                    tvOperation.setText("去结算");
                    tvOperation.setBackgroundColor(getResources().getColor(R.color.green));
                } else {
                    float price = ComParams.total_price_low - ComParams.total_price;
                    tvOperation.setText("差￥" + CommonUilts.getDoubleTwo(price) + "起送");
                    tvOperation.setEnabled(false);
                    tvOperation.setBackgroundColor(getResources().getColor(R.color.bg_color));
                }
            }
        } else {
            tvOperation.setText("计算中...");
            tvOperation.setEnabled(false);
            tvOperation.setBackgroundColor(getResources().getColor(R.color.bg_color));
        }
    }

    private void setTotalPrice(float price) {
        ComParams.total_price = price;
        if (price < ComParams.total_price_low) {
            ll_price_low_remark.setVisibility(View.VISIBLE);
        } else {
            ll_price_low_remark.setVisibility(View.GONE);
        }

        setTvOpertionStatus(true);

        String text = "总价:￥" + CommonUilts.getDoubleTwo(price);
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        spannable.setSpan(new ForegroundColorSpan(Color.RED), 3, text.length(),
                //setSpan时需要指定的 flag,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括).
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTotalPrice.setText(spannable);
    }

    /**
     * 设置规格选中状态，并同步商品状态
     *
     * @param goods
     * @param guigeVo
     * @param i
     * @param position
     */
    private void setGuigeState(GoodsVo goods, GuigeVo guigeVo, int i, int position) {
        if (catAdapter.isDel) {
            guigeVo.setDelStatus(i);
        } else {
            guigeVo.setStatus(i);
        }
        int statusGuige = 0;
        for (GuigeVo guige : goods.getGuige()) {
            if (catAdapter.isDel && guige.getDelStatus() == i) {
                statusGuige++;
            } else if (guige.getStatus() == i) {
                statusGuige++;
            }
        }
        if (statusGuige == goods.getGuige().size()) {
            if (catAdapter.isDel) {
                goods.setDelStatus(i);
            } else {
                goods.setCarSstatus(i);
            }
        }
//        catAdapter.notifyItemChanged(position);
        catAdapter.notifyDataSetChanged();

        int countStatus = 0;
        for (GoodsVo items : catAdapter.getList()) {
            if (catAdapter.isDel && items.getDelStatus() == 1) {
                countStatus++;
            } else if (items.getCarSstatus() == 1) {
                countStatus++;
            }
        }
        setTvSelectStatus(countStatus >= catAdapter.getCount());
    }

    /**
     * 设置商品选中是否
     *
     * @param goodsVo
     * @param i
     */
    private void setStatus(GoodsVo goodsVo, int i, int position) {
        if (catAdapter.isDel) {
            goodsVo.setDelStatus(i);
        } else {
            goodsVo.setCarSstatus(i);
        }

        for (GuigeVo guigeVo : goodsVo.getGuige()) {
            if (catAdapter.isDel) {
                guigeVo.setDelStatus(i);
            } else {
                guigeVo.setStatus(i);
            }
        }
        //全选按钮设置
//        catAdapter.notifyItemChanged(position);
        catAdapter.notifyDataSetChanged();
        int countStatus = 0;
        for (GoodsVo items : catAdapter.getList()) {
            if (catAdapter.isDel && items.getDelStatus() == 1) {
                countStatus++;
            } else if (items.getCarSstatus() == 1) {
                countStatus++;
            }
        }
        setTvSelectStatus(countStatus >= catAdapter.getCount());
    }

    /**
     * 添加或减少购物车的数量
     *
     * @param guigeVo
     * @param num
     * @param shop_cat_num
     */
    private void setCatNum(final GoodsVo items, final GuigeVo guigeVo, final int num, final int shop_cat_num, final int position, final int childPosition) {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("goodsId", items.getGoodsId());
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
                .build().execute(new MyStringCallback((BaseActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("添加或减少购物车的数量", "body---->" + body);
                guigeVo.setCarGoodNum(num);
                if (guigeVo.getCarGoodState() != 1) {
                    guigeVo.setCarGoodState(1);
                }
                catAdapter.notifyDataSetChanged();

                JSONObject priceadd = getPriceAddSub(1);
                JSONObject pricesub = getPriceAddSub(0);
                countPrice(priceadd, null, position, childPosition, true);
                countPrice(pricesub, null, position, childPosition, false);
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 删除购物车
     */
    private void deleteCar(String jsonObject) {
        String params = null;
        try {
            params = DES.encryptDES(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.e("删除购物车", "jsonObject-->" + jsonObject);
        showLoading("");
        OkHttpUtils.get().url(Constans.deleteCar)
                .addParams("params", params)
                .build().execute(new MyStringCallback((BaseActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("删除购物车", "body---->" + body);
                swipeToLoadLayout.startRefresh();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 查询购物车1
     */
    private void selectCar() {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("cityCode", userVo.getCityCode());
            String des = new Gson().toJson(map);
            LogUtils.e("查询购物车1", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.get().url(Constans.selectCar)
                .addParams("params", params)
                .build().execute(new MyStringCallback((BaseActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                isLoaded = true;
                swipeToLoadLayout.stopRefresh();
                swipeToLoadLayout.stopLoadMore();
                CarGoodsListVo goodsCategoryVo = new Gson().fromJson(body, CarGoodsListVo.class);
                LogUtils.e("查询购物车1", "goodsCategoryVo-->" + goodsCategoryVo.toString());

                if (goodsCategoryVo != null && goodsCategoryVo.getCarInfoList() != null && !goodsCategoryVo.getCarInfoList().isEmpty()) {
                    LogUtils.e("查询购物车1", "size-->" + goodsCategoryVo.getCarInfoList().size());
                    tvNoneHint.setVisibility(View.GONE);
                    ll_opertion.setVisibility(View.VISIBLE);
                    ll_price_low_remark.setVisibility(View.VISIBLE);
                    float totalPrice = 0;//计算价格
                    int totalNum = 0;//购物车数量
                    int selectNum = 0; //根据选中数量判断是否全选
                    for (GoodsVo goodsVo : goodsCategoryVo.getCarInfoList()) {
                        int statusCount = 0;
                        for (GuigeVo guigeVo : goodsVo.getGuige()) {
                            if (guigeVo.getStatus() == 1) {
                                totalNum += guigeVo.getCarGoodNum();
                                totalPrice += guigeVo.getCarGoodNum() * Float.valueOf(guigeVo.getCurrentPrice());
                                statusCount++;
                            }
                        }
                        if (statusCount >= goodsVo.getGuige().size()) {
                            goodsVo.setCarSstatus(1);
                        }
                        if (goodsVo.getCarSstatus() == 1) {
                            selectNum++;
                        }
                    }
                    //全选按钮设置
                    if (tvRight.getText().toString().equals("编辑") && selectNum >= goodsCategoryVo.getCarInfoList().size()) {
                        setTvSelectStatus(true);
                    } else {
                        setTvSelectStatus(false);
                    }
                    setTotalPrice(totalPrice);
                    catAdapter.setList(goodsCategoryVo.getCarInfoList(), 0);
                    setCarNum(totalNum);

                } else {
                    if (catAdapter.getCount() > 0) {
                        catAdapter.getList().clear();
                        catAdapter.notifyDataSetChanged();
                    }
                    setCarNum(0);
                    tvNoneHint.setVisibility(View.VISIBLE);
                    ll_opertion.setVisibility(View.GONE);
                    ll_price_low_remark.setVisibility(View.GONE);
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

    private void setCarNum(int num) {
        ComParams.shop_cat_num = num;
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setShoppingCatNum();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == getActivity().RESULT_OK) {
                    swipeToLoadLayout.startRefresh();
                }
                break;
        }
    }

    /**
     * 去结算接口
     */
    private void submitOrder(ArrayList<GoodsVo> goodsVoList) {

        String params = null;
        try {
            JSONArray jsonArray1 = new JSONArray();
            for (GoodsVo goodsVo : goodsVoList) {
                JSONArray jsonArray = new JSONArray();
                for (GuigeVo guigeVo : goodsVo.getGuige()) {
                    JSONObject guige = new JSONObject();
                    guige.put("id", guigeVo.getId());
                    guige.put("carGoodNum", guigeVo.getCarGoodNum());
                    jsonArray.put(guige);
                }
                JSONObject goods = new JSONObject();
                goods.put("id", goodsVo.getGoodsId());
                goods.put("goodsSpec", jsonArray);
                jsonArray1.put(goods);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("goodsList", jsonArray1);
            UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
            jsonObject.put("token", userVo.getToken());
            String des = jsonObject.toString();
            LogUtils.e("去结算接口", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.submitOrder)
                .addParams("params", params)
                .build().execute(new MyStringCallback((BaseActivity) getActivity()) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("去结算接口", "body---->" + body);
                CatSubmitBean catSubmitBean = new Gson().fromJson(body, CatSubmitBean.class);
                startActivityForResult(new Intent(getActivity(), OrderPayActivity.class)
                        .putExtra("CatSubmitBean", catSubmitBean), 0);
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    private class Jsons {
        public String specNum;
        public String token;
        public String status;
        public List<GoodsList> goodsList;

        @Override
        public String toString() {
            return "Jsons{" +
                    "specNum='" + specNum + '\'' +
                    ", token='" + token + '\'' +
                    ", status='" + status + '\'' +
                    ", goodsList=" + goodsList +
                    '}';
        }
    }

    private class GoodsList {
        public String id;
        public List<Guige> goodsSpec;

        @Override
        public String toString() {
            return "GoodsList{" +
                    "id='" + id + '\'' +
                    ", goodsSpec=" + goodsSpec +
                    '}';
        }
    }

    private class Guige {
        public String id;
        public String specNum;
        public int carGoodNum;

        @Override
        public String toString() {
            return "Guige{" +
                    "id='" + id + '\'' +
                    ", specNum='" + specNum + '\'' +
                    ", carGoodNum=" + carGoodNum +
                    '}';
        }
    }
}
