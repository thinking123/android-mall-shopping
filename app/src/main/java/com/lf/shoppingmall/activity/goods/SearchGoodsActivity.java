package com.lf.shoppingmall.activity.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.main.MainActivity;
import com.lf.shoppingmall.adapter.ComonOrderListAdapter;
import com.lf.shoppingmall.adapter.GoodsDetailCateGridAdapter;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
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
import com.lf.shoppingmall.utils.ComUtils;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.CommonUilts;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.utils.SharePreferenceUtil;
import com.lr.baseview.widget.AlertDialog;
import com.lr.baseview.widget.AlertEditDialog;
import com.lr.baseview.widget.CheckableTextView;
import com.lr.baseview.widget.CircleImageView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索商品界面
 * Created by Administrator on 2017/8/29.
 */

public class SearchGoodsActivity extends BaseActivity implements TextWatcher, OnRefreshListener, OnExpandItemClidkListener {

    @BindView(R.id.tv_right)
    CheckableTextView tv_right;
    @BindView(R.id.et_search)
    EditText etSearch;
    //    @BindView(R.id.tv_right)
//    CheckableTextView tvRight;
    @BindView(R.id.tv_hos_no)
    TextView tvHosNo;
    @BindView(R.id.rv_cate)
    GridView rvCate;
    @BindView(R.id.ll_list)
    FrameLayout ll_list;
    @BindView(R.id.ll_search_hos)
    LinearLayout llSearchHos;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    ListView swipe_target;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_operation)
    TextView tvOperation;
    @BindView(R.id.ll_item)
    LinearLayout llItem;
//    @BindView(R.id.iv_shop)
//    CircleImageView ivShop;
    @BindView(R.id.tv_notice_num)
    TextView tvNoticeNum;
    @BindView(R.id.tv_none_hint)
    TextView tv_none_hint;
    private GoodsDetailCateGridAdapter cateGridAdapter;
    private ComonOrderListAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_goods;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setOnRefreshListener(this);
        etSearch.addTextChangedListener(this);
        adapter = new ComonOrderListAdapter(context, null, this);
        adapter.status = 1;
        swipe_target.setAdapter(adapter);

        if (ComParams.shop_cat_num > 0) {
            tvNoticeNum.setText(String.valueOf(ComParams.shop_cat_num));
            tvNoticeNum.setVisibility(View.VISIBLE);
        } else {
            tvNoticeNum.setVisibility(View.GONE);
        }
        tvTotalPrice.setText("总价:￥" + CommonUilts.getDoubleTwo(ComParams.total_price));

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (EditorInfo.IME_ACTION_SEARCH == actionId) {
                    ComUtils.hideSoftKeyboard(context, v);
                    String search = v.getText().toString();
                    if (TextUtils.isEmpty(search)) {
                        showToast("请输入商品名称");
                    } else {
                        searchGoods(search);
                    }
                    return true;
                }
                return false;
            }

        });

        cateGridAdapter = new GoodsDetailCateGridAdapter(context, null, null);
        cateGridAdapter.curruntCate = -1;
        rvCate.setAdapter(cateGridAdapter);
        rvCate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchGoods(cateGridAdapter.strings[position]);
            }
        });
        setHosInfo();
    }

    private void searchGoods(String search) {
        llSearchHos.setVisibility(View.GONE);
        ll_list.setVisibility(View.VISIBLE);
        String searchHos = "";
        String hos = (String) SharePreferenceUtil.get(context, ComParams.SEARCH_HOS, "");
        if (!TextUtils.isEmpty(hos)) {
            if (hos.indexOf(search) == -1) {
                searchHos = hos + "," + search;
            }
        } else {
            searchHos = search;
        }
        SharePreferenceUtil.put(context, ComParams.SEARCH_HOS, searchHos);
        selectGoodsByName(search);
    }

    @Override
    protected Object getTitleText() {
        return null;
    }

    @OnClick({R.id.tv_right, R.id.tv_operation, R.id.iv_shop, R.id.tv_none_hint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                String tvRight = tv_right.getText().toString();
                if (tvRight.equals("搜索")) {
                    String search = etSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(search)) {
                        showToast("请输入商品名称");
                    } else {
                        searchGoods(search);
                    }
                } else {
                    etSearch.setText("");
                }
                break;


            case R.id.tv_operation:
            case R.id.iv_shop:
                startActivity(new Intent(this, ShopCateActivity.class));
                break;

            case R.id.tv_none_hint:
                swipeToLoadLayout.startRefresh();
                break;
        }
    }

    /**
     * 搜索接口
     */
    private void selectGoodsByName(String search) {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            UserVo userVo = ((BaseApplication) getApplication()).getUserVo();

            map.put("token", userVo.getToken());
            map.put("cityCode", userVo.getCityCode());
            map.put("goodName", search);
            String des = new Gson().toJson(map);
            LogUtils.e("搜索接口", "des-->" + des);
            params = DES.encryptDES(des);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("搜索接口", "Exception-->" + e.toString());
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.selectGoodsByName)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                swipeToLoadLayout.stopRefresh();

                LogUtils.e("搜索接口", "body---->" + body);
                GoodsListInfoListVo listVo = new Gson().fromJson(body, GoodsListInfoListVo.class);
                if (listVo != null && listVo.getGoodsListInfoList() != null && !listVo.getGoodsListInfoList().isEmpty()) {
                    tv_right.setText("取消");
                    tv_none_hint.setVisibility(View.GONE);
                    adapter.setList(listVo.getGoodsListInfoList(), 0);

                } else {
                    if (adapter.getCount() > 0) {
                        adapter.getList().clear();
                        adapter.notifyDataSetChanged();
                    }
                    tv_none_hint.setVisibility(View.VISIBLE);
                    showToast("暂无该类型商品，请重新输入搜索！");
                }


            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                swipeToLoadLayout.stopRefresh();
                showToast(errorMsg);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())) {
            setHosInfo();
            llSearchHos.setVisibility(View.VISIBLE);
            ll_list.setVisibility(View.GONE);
            tv_right.setText("搜索");
        }
    }

    private void setHosInfo() {
        String hos = (String) SharePreferenceUtil.get(context, ComParams.SEARCH_HOS, "");
        if (!TextUtils.isEmpty(hos)) {
            tvHosNo.setVisibility(View.GONE);
            rvCate.setVisibility(View.VISIBLE);
            cateGridAdapter.strings = hos.split(",");
            cateGridAdapter.notifyDataSetChanged();

        } else {
            rvCate.setVisibility(View.GONE);
            tvHosNo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        String search = etSearch.getText().toString().trim();
        selectGoodsByName(search);
    }

    @Override
    public void onItemClick(int position, final int childPosition, int type) {
        final GoodsVo goodsVo = (GoodsVo) adapter.getList().get(position);
        switch (type) {
            case -1:
                selectGoods(goodsVo.getId());
                break;

            case 0:
//                new AlertDialog(context).builder().setTitle("提示")
//                        .setMsg("确定从常用清单中移除该商品吗?")
//                        .setNegativeButton("取消", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//                        })
//                        .setPositiveButton("确定", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                removeList(goodsVo.getId(), position);
//                            }
//                        })
//                        .show();
                break;

            case 1:
                goodsVo.setGuige(!goodsVo.isGuige());
                adapter.notifyDataSetChanged();
                break;

            case 2: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                setCatNum(goodsVo.getId(), guigeVo, 1, 1);
            }
            break;

            case 3: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                setCatNum(goodsVo.getId(), guigeVo, 1, 1);
            }
            break;

            case 4: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                int carNum = guigeVo.getCarGoodNum();
                setCatNum(goodsVo.getId(), guigeVo, carNum + 1, 1);
            }
            break;

            case 5: {
                GuigeVo guigeVo = goodsVo.getGuige().get(childPosition);
                setCatNum(goodsVo.getId(), guigeVo, guigeVo.getCarGoodNum() - 1, -1);
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
     * 添加或减少购物车的数量
     *
     * @param parentIdn
     * @param guigeVo
     * @param num
     * @param shop_cat_num
     */
    private void setCatNum(String parentIdn, final GuigeVo guigeVo, final int num, final int shop_cat_num) {
        UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
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
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("添加或减少购物车的数量", "body---->" + body);
                int oldnum = guigeVo.getCarGoodNum();
                int chajia = num - oldnum;//计算加减的数量

                guigeVo.setCarGoodNum(num);
                adapter.notifyDataSetChanged();
                ComParams.shop_cat_num = ComParams.shop_cat_num + shop_cat_num;
                setShopCat(chajia * Float.valueOf(guigeVo.getCurrentPrice()));

            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    private void setShopCat(float price) {
//        float currentPrice = Float.valueOf(guigeVo.getCurrentPrice());
        ComParams.total_price += price;
        tvTotalPrice.setText("总价:￥" +  CommonUilts.getDoubleTwo(ComParams.total_price));
        if (ComParams.shop_cat_num > 0) {
            tvNoticeNum.setText(String.valueOf(ComParams.shop_cat_num));
            tvNoticeNum.setVisibility(View.VISIBLE);
        } else {
            tvNoticeNum.setVisibility(View.GONE);
        }
    }

    /**
     * 商品详情
     *
     * @param id
     */
    private void selectGoods(String id) {
        UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
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
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                GoodsDetailVo goods = new Gson().fromJson(body, GoodsDetailVo.class);

                if (goods != null && goods.getGoods() != null) {
                    LogUtils.e("商品详情", "goods---->" + goods.toString());
                    startActivityForResult(new Intent(SearchGoodsActivity.this, GoodsDetail0Activity.class)
                                    .putExtra("GoodsVo", goods.getGoods()),
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
