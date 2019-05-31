package com.lf.shoppingmall.activity.custom_service;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.adapter.BottomPopCommonAdapter;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.AllOrder.GoodsCateVo;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.other.GoodsCategoryList;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.widget.ComBottomPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新品需求
 * Created by Administrator on 2017/8/24.
 */

public class SubmitGoodsActivity extends BaseActivity {
    @Bind(R.id.tv_goods_name)
    EditText tvGoodsName;
    @Bind(R.id.tv_goods_cate)
    TextView tvGoodsCate;
    @Bind(R.id.tv_goods_brand)
    EditText tvGoodsBrand;
    @Bind(R.id.tv_goods_featrue)
    EditText tvGoodsFeatrue;
    @Bind(R.id.tv_goods_biz)
    EditText tvGoodsBiz;
    @Bind(R.id.tv_goods_price)
    EditText tvGoodsPrice;
    @Bind(R.id.tv_goods_des)
    EditText tvGoodsDes;
    @Bind(R.id.tv_goods_phone)
    EditText tvGoodsPhone;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    private ComBottomPopupWindow bottomPopupWindow;
    private BottomPopCommonAdapter bottomPopCommonAdapter;
    private String goodsCategoryId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_submit_goods;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

    }

    @Override
    protected Object getTitleText() {
        return "新品需求";
    }

    @OnClick({R.id.ll_choose_cate, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_choose_cate:
                if (bottomPopCommonAdapter == null) {
                    newCategory();
                } else {
                    bottomPopupWindow.showAsDropDown(tvTitle);
                }
                break;

            case R.id.btn_login:
                quickFeedback();
                break;
        }
    }

    /**
     * 新品需求
     */
    private void quickFeedback() {
        String goodsName = tvGoodsName.getText().toString().trim();
        if (TextUtils.isEmpty(goodsName)) {
            showToast("请商品名称");
            return;
        }
        if (TextUtils.isEmpty(goodsCategoryId)) {
            showToast("请选择商品");
            return;
        }

        UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("goodsName", goodsName);
            map.put("goodsCategoryId", goodsCategoryId);
            map.put("goodsSpec", tvGoodsFeatrue.getText().toString().trim());//商品规格
            map.put("upplier", tvGoodsBiz.getText().toString().trim()); //供货商
            map.put("price", tvGoodsPrice.getText().toString().trim()); //参考价格
            map.put("telephone", tvGoodsPhone.getText().toString().trim()); //联系方式
            map.put("remark", tvGoodsDes.getText().toString().trim()); //备注
            String des = new Gson().toJson(map);
            LogUtils.e("新品需求", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.quickFeedback)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("新品需求", "body---->" + body);
                showToast("需求提交成功，我们会尽快处理的");
                finish();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 添加页面需要的菜品
     */
    private void newCategory() {
        UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            String des = new Gson().toJson(map);
            LogUtils.e("添加页面需要的菜品", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.newCategory)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                GoodsCategoryList categoryList = new Gson().fromJson(body, GoodsCategoryList.class);
                if (categoryList != null) {
                    final List<GoodsCateVo> cateVos = categoryList.getGoodsCategory();
                    if (cateVos != null && !cateVos.isEmpty()) {
                        bottomPopCommonAdapter = new BottomPopCommonAdapter(context, cateVos);
                        bottomPopupWindow = new ComBottomPopupWindow(context, null, bottomPopCommonAdapter, new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                GoodsCateVo goodsCateVo = cateVos.get(position);
                                goodsCategoryId = goodsCateVo.getCategoryId();
                                tvGoodsCate.setText(goodsCateVo.getCategoryName());
                            }
                        });
                        bottomPopupWindow.showAsDropDown(tvTitle);
                    } else {
                        showToast("获取菜品失败，点击重新获取！");
                    }
                } else {
                    showToast("获取菜品失败，点击重新获取！");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
