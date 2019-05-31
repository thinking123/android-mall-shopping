package com.lf.shoppingmall.activity.goods;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.login.LoginActivity;
import com.lf.shoppingmall.activity.login.WelcomeActivity;
import com.lf.shoppingmall.activity.main.MainActivity;
import com.lf.shoppingmall.adapter.GoodsDetailCateGridAdapter;
import com.lf.shoppingmall.adapter.GoodsDetailParamAdapter;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.base.MyPagerAdapter;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.index.GuigeVo;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.common.OnRvItemClickListener;
import com.lr.baseview.utils.CommonUilts;
import com.lr.baseview.utils.ImageLoadUtils;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.utils.SharePreferenceUtil;
import com.lr.baseview.widget.AlertEditDialog;
import com.lr.baseview.widget.CircleImageView;
import com.lr.baseview.widget.CustomGridView;
import com.lr.baseview.widget.CustomRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品详情0
 * Created by Administrator on 2017/8/23.
 */
@SuppressWarnings("ResourceType")
public class GoodsDetail0Activity extends BaseActivity implements OnRvItemClickListener {

    //    @Bind(R.id.iv_banner)
//    ImageView ivBanner;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_bannar_num)
    TextView tv_bannar_num;
    @Bind(R.id.vp_banner)
    ViewPager vp_banner;
    @Bind(R.id.tv_add_common)
    TextView tv_add_common;
    @Bind(R.id.iv_common)
    ImageView iv_common;
    @Bind(R.id.rv_cate)
    CustomGridView rv_cate;
    @Bind(R.id.page_top)
    PercentRelativeLayout pageTop;
    @Bind(R.id.tv_operation)
    TextView tvOperation;
    @Bind(R.id.ll_item)
    LinearLayout llItem;
    //    @Bind(R.id.iv_shop)
//    CircleImageView ivShop;
    //    private GoodsDetailCateAdapter cateAdapter;
    private GoodsDetailCateGridAdapter cateGridAdapter;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_price_uint)
    TextView tvPriceUint;
    @Bind(R.id.tv_uint)
    TextView tvUint;
    @Bind(R.id.tv_buy_add)
    TextView tvBuyAdd;
    @Bind(R.id.tv_sub)
    TextView tvSub;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.tv_max_hint)
    TextView tvMaxHint;
    @Bind(R.id.ll_add_sub)
    LinearLayout llAddSub;
    @Bind(R.id.tv_des_hint)
    TextView tvDesHint;
    @Bind(R.id.tv_des_content)
    TextView tvDesContent;
    @Bind(R.id.tv_total_price)
    TextView tv_total_price;
    @Bind(R.id.tv_notice_num)
    TextView tv_notice_num;
    @Bind(R.id.ll_des)
    LinearLayout llDes;
    @Bind(R.id.rv_param)
    CustomRecyclerView rv_param;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.tv_web_no)
    TextView tv_web_no;

    private int type;
    private GoodsVo goodsVo;
    private int bannerSize;
    private ArrayList<String> banners;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_detail_0;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        goodsVo = (GoodsVo) getIntent().getSerializableExtra("GoodsVo");
        type = getIntent().getIntExtra("type", 0);
        if (goodsVo == null) {
            showToast("参数有误");
            finish();
            return;
        }
        LogUtils.e("GoodsVo", "goodsVo-->" + goodsVo.toString());
        pageTop.setBackgroundResource(ContextCompat.getColor(context, R.color.transparent));
        StringBuilder titleBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(goodsVo.getBrand())) {
            titleBuilder.append("[");
            titleBuilder.append(goodsVo.getBrand());
            titleBuilder.append("]");
        }
        titleBuilder.append(goodsVo.getFullName());
        if (!TextUtils.isEmpty(goodsVo.getFeature())) {
            titleBuilder.append(" ");
            titleBuilder.append(goodsVo.getFeature());
        }
        tvName.setText(titleBuilder.toString());

        //banner

        List<View> views = new ArrayList<>();
        banners = (ArrayList<String>) goodsVo.getImageBanner();
        if (banners != null && !banners.isEmpty()) {
            bannerSize = banners.size();
            int i = 0;
            for (String image : banners) {
                ImageView imageView = new ImageView(context);
                ImageLoadUtils.loadingCommonPicBig(context, image, imageView);
                final int currentIndex = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(GoodsDetail0Activity.this, GoodsPicActivity.class)
                                .putStringArrayListExtra("banners", banners)
                                .putExtra("currentIndex", currentIndex));
                    }
                });
                views.add(imageView);
                i++;
            }
        } else {
            banners = new ArrayList<>();
            bannerSize = 1;
            ImageView imageView = new ImageView(context);
            banners.add(goodsVo.getImage());
            ImageLoadUtils.loadingCommonPicBig(context, goodsVo.getImage(), imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(GoodsDetail0Activity.this, GoodsPicActivity.class)
                            .putStringArrayListExtra("banners", banners)
                            .putExtra("currentIndex", 0));
                }
            });
            views.add(imageView);
        }
        vp_banner.setAdapter(new MyPagerAdapter(views));
        vp_banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_bannar_num.setText((position + 1) + "/" + bannerSize);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        ImageLoadUtils.loadingCommonPic(context, goodsVo.getImage(), ivBanner);

        tvDesContent.setText(goodsVo.getIntroduction());
        if (!goodsVo.getListId().equals("0")) {
            iv_common.setImageResource(R.mipmap.listpage_icon_recommend_formal);
            tv_add_common.setTag("yes");
            tv_add_common.setText("已加入");
            tv_add_common.setTextColor(ContextCompat.getColor(context, R.color.text_norm_8));
        }

        String[] datas = null;
        if (!TextUtils.isEmpty(goodsVo.getSpec())) {
            datas = goodsVo.getSpec().split(",");
        }
        cateGridAdapter = new GoodsDetailCateGridAdapter(context, null, datas);
        rv_cate.setAdapter(cateGridAdapter);
        rv_cate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cateGridAdapter.curruntCate = position;
                cateGridAdapter.notifyDataSetChanged();
                setPericeStatus();
            }
        });

        setPericeStatus();
        setShopCat(0);
        setParamsRv();
        if (TextUtils.isEmpty(goodsVo.getDetailsImage())) {
            webview.setVisibility(View.GONE);
            tv_web_no.setVisibility(View.VISIBLE);
        } else {
            tv_web_no.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);
            //支持javascript
            webview.getSettings().setJavaScriptEnabled(true);
            //自适应屏幕
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webview.getSettings().setLoadWithOverviewMode(true);
            //WebView加载web资源
            webview.loadUrl(goodsVo.getDetailsImage());//"http://baidu.com"
            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
            });
        }
    }

    /**
     * 设置价格显示
     */
    private void setPericeStatus() {
//        GuigeVo guigeVo = goodsVo.getGuige().get(cateAdapter.curruntCate);
        GuigeVo guigeVo = goodsVo.getGuige().get(cateGridAdapter.curruntCate);
        StringBuilder priceBuilder = new StringBuilder();
        priceBuilder.append("￥");
        priceBuilder.append(guigeVo.getCurrentPrice());
        priceBuilder.append("元/");
        priceBuilder.append(goodsVo.getBaseSpec());
        tvPrice.setText(priceBuilder.toString());

        if (!guigeVo.getShowState().equals("0") && !TextUtils.isEmpty(guigeVo.getTotalWeight())) {
            StringBuilder uintBuilder = new StringBuilder();
            uintBuilder.append("(");
            uintBuilder.append(guigeVo.getTotalWeight());
            uintBuilder.append("斤)");
            tvUint.setText(uintBuilder.toString());
        } else {
            tvUint.setText("");
        }

        if (guigeVo.getShowState().equals("0")) {
            tvPriceUint.setVisibility(View.GONE);
        } else {
            tvPriceUint.setVisibility(View.VISIBLE);
            tvPriceUint.setText("￥" + guigeVo.getAvgPrice() + "元/斤");
        }
        if (guigeVo.getCarGoodNum() > 0) {
            llAddSub.setVisibility(View.VISIBLE);
            tvBuyAdd.setVisibility(View.GONE);
            tvNum.setText("" + guigeVo.getCarGoodNum());
        } else {
            llAddSub.setVisibility(View.GONE);
            tvBuyAdd.setVisibility(View.VISIBLE);
        }
//        setShopCat(guigeVo);
    }

    private void setShopCat(float price) {
//        float currentPrice = Float.valueOf(guigeVo.getCurrentPrice());
        ComParams.total_price += price;
        tv_total_price.setText("总价:￥" + CommonUilts.getDoubleTwo(ComParams.total_price));
        if (ComParams.shop_cat_num > 0) {
            tv_notice_num.setText(String.valueOf(ComParams.shop_cat_num));
            tv_notice_num.setVisibility(View.VISIBLE);
        } else {
            tv_notice_num.setVisibility(View.GONE);
        }
    }

    private void setParamsRv() {
        rv_param.setLayoutManager(new LinearLayoutManager(context));
        String[] params = getResources().getStringArray(R.array.goods_detail_params);
        List<String> type = new ArrayList<>();
        List<String> typeStr = new ArrayList<>();
        String brand = goodsVo.getBrand();
        if (!TextUtils.isEmpty(brand)) {
            type.add(params[0]);
            typeStr.add(brand);
        }

        String basespse = goodsVo.getBaseSpec();
        String totalCount = goodsVo.getTotalCount();
        type.add(params[1]);
        typeStr.add(basespse + "(" + "斤"/*totalCount*/ + ")");

        String feature = goodsVo.getFeature();
        if (!TextUtils.isEmpty(feature)) {
            type.add(params[2]);
            typeStr.add(feature);
        }

        String place = goodsVo.getPlace();
        if (!TextUtils.isEmpty(place)) {
            type.add(params[4]);
            typeStr.add(place);
        }

        String producet = goodsVo.getProducer();
        if (!TextUtils.isEmpty(producet)) {
            type.add(params[5]);
            typeStr.add(producet);
        }

        rv_param.setAdapter(new GoodsDetailParamAdapter(context, null, type, typeStr));

    }

    @Override
    protected Object getTitleText() {
        return null;
    }

    @Override
    protected void setTitleBarStatus(ImageView iv_back, TextView tv_title, TextView tv_right, ImageView iv_right) {
        super.setTitleBarStatus(iv_back, tv_title, tv_right, iv_right);
        tv_title.setVisibility(View.GONE);
    }

    @OnClick({R.id.ll_add_common, R.id.tv_buy_add, R.id.tv_sub, R.id.tv_add, R.id.tv_num, R.id.iv_shop, R.id.tv_operation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_add_common:
                String tag = (String) tv_add_common.getTag();
                if (tag.equals("no")) {
                    addList();
                } else {
                    removeList();
                }
                break;

            case R.id.tv_buy_add: {
                GuigeVo guigeVo = goodsVo.getGuige().get(cateGridAdapter.curruntCate);
                setCatNum(goodsVo.getId(), guigeVo, 1, 1);
            }
            break;

            case R.id.tv_sub: {
                GuigeVo guigeVo = goodsVo.getGuige().get(cateGridAdapter.curruntCate);
                setCatNum(goodsVo.getId(), guigeVo, guigeVo.getCarGoodNum() - 1, -1);
            }
            break;

            case R.id.tv_add: {
                GuigeVo guigeVo = goodsVo.getGuige().get(cateGridAdapter.curruntCate);
                int carNum = guigeVo.getCarGoodNum();
                setCatNum(goodsVo.getId(), guigeVo, carNum + 1, 1);
            }
            break;

            case R.id.tv_num:
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
                                GuigeVo guigeVo = goodsVo.getGuige().get(cateGridAdapter.curruntCate);
                                setCatNum(goodsVo.getId(), guigeVo, num, -1);
                            }
                        })
                        .show();

                break;

            case R.id.tv_operation:
            case R.id.iv_shop:
                if (type == 1) {
                    finish();
                } else {
                    startActivity(new Intent(this, ShopCateActivity.class));
                }
                break;
        }
    }

    @Override
    public void onItemClick(int position, int type) {
        if (type == 0) {
            cateGridAdapter.curruntCate = position;
            cateGridAdapter.notifyDataSetChanged();
            //
            setPericeStatus();
        }
    }

    /**
     * 加入清单
     */
    private void addList() {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            UserVo userVo = ((BaseApplication) getApplication()).getUserVo();

            map.put("token", userVo.getToken());
            map.put("goodsId", goodsVo.getId());
            String des = new Gson().toJson(map);
            LogUtils.e("加入清单", "des-->" + des);
            params = DES.encryptDES(des);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("加入清单", "Exception-->" + e.toString());
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.addList)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("加入清单", "body---->" + body);
//                Drawable drawable =ContextCompat.getDrawable(context, R.mipmap.listpage_icon_recommend_pressed);
//                drawable.setBounds(drawable.getMinimumWidth(), drawable.getMinimumHeight(), 0, 0);
//                tv_add_common.setCompoundDrawables(null, drawable, null, null);
                iv_common.setImageResource(R.mipmap.listpage_icon_recommend_formal);
                tv_add_common.setTag("yes");
                tv_add_common.setText("已加入");
                tv_add_common.setTextColor(ContextCompat.getColor(context, R.color.text_norm_8));
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    /**
     * 移除清单
     */
    private void removeList() {
        UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("goodsId", goodsVo.getId());
            String des = new Gson().toJson(map);
            LogUtils.e("移除清单", "des-->" + des);
            params = DES.encryptDES(des);

        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.removeList)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();

                LogUtils.e("移除清单", "body---->" + body);
//                Drawable drawable =ContextCompat.getDrawable(context, R.mipmap.listpage_icon_recommend_formal);
//                drawable.setBounds(drawable.getMinimumWidth(), drawable.getMinimumHeight(), 0, 0);
//                tv_add_common.setCompoundDrawables(null, drawable, null, null);
                iv_common.setImageResource(R.mipmap.listpage_icon_recommend_pressed);
                tv_add_common.setTag("no");
                tv_add_common.setText("加入清单");
                tv_add_common.setTextColor(ContextCompat.getColor(context, R.color.text_green));
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
     *
     * @param parentIdn
     * @param guigeVo
     * @param num
     * @param shop_cat_num
     */
    private void setCatNum(String parentIdn, final GuigeVo guigeVo, final int num, final int shop_cat_num) {
        UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
        LogUtils.e("添加或减少购物车的数量", "guigeVo-->" + guigeVo.toString());
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
                int chajia = num - oldnum;
                guigeVo.setCarGoodNum(num);
                if (num > 0) {
                    llAddSub.setVisibility(View.VISIBLE);
                    tvNum.setText(String.valueOf(num));
                    tvBuyAdd.setVisibility(View.GONE);
                } else {
                    llAddSub.setVisibility(View.GONE);
                    tvBuyAdd.setVisibility(View.VISIBLE);
                }
                ComParams.shop_cat_num = ComParams.shop_cat_num + shop_cat_num;
                setShopCat(chajia * Float.valueOf(guigeVo.getCurrentPrice()));
                setPericeStatus();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    private class MyPagerAdapter extends PagerAdapter {
        private List<View> views = new ArrayList<>();

        public MyPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views == null ? 0 : views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
