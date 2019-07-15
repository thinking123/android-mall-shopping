package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.index.GuigeVo;
import com.lf.shoppingmall.common.OnExpandItemClidkListener;
import com.lr.baseview.base.BaseLGAdapter;
import com.lr.baseview.base.BaseRvAdapter;
import com.lr.baseview.common.OnRvItemClickListener;
import com.lr.baseview.utils.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */

public class AllOrderListAdapter extends BaseLGAdapter<GoodsVo> {

    public OnExpandItemClidkListener expandItemClidkListener;

    public AllOrderListAdapter(Context context, List<GoodsVo> datas, OnExpandItemClidkListener expandItemClidkListener) {
        super(context, datas);
        this.expandItemClidkListener = expandItemClidkListener;
    }

    private View getGuigeView(GuigeVo guigeVo, String uint, int position, int childPosition) {
        View view = inflater.inflate(R.layout.incudle_all_order_list_cate, null);
        // 0删除 1规格 2现在购买 3购买 4加 5减  todo6输入数字
        TextView tv_price_feature = (TextView) view.findViewById(R.id.tv_price_feature);
        TextView tv_biz_des = (TextView) view.findViewById(R.id.tv_biz_des);
        TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
        TextView tv_price_uint = (TextView) view.findViewById(R.id.tv_price_uint);
        TextView tv_price_old = (TextView) view.findViewById(R.id.tv_price_low);

        StringBuilder desBuilder = new StringBuilder();
        desBuilder.append("￥");
        desBuilder.append(guigeVo.getCurrentPrice());
        desBuilder.append("元/");
        desBuilder.append(uint);
        if (!TextUtils.isEmpty(guigeVo.getTotalWeight())){
            desBuilder.append("(");
            desBuilder.append(guigeVo.getTotalWeight());
            desBuilder.append("斤)");

        }
        tv_biz_des.setText(desBuilder.toString());

        if (guigeVo.getShowState().equals("0")){
            tv_price_feature.setVisibility(View.GONE);
            tv_price.setVisibility(View.GONE);
            tv_price_uint.setVisibility(View.GONE);
            tv_price_old.setVisibility(View.GONE);
        }else {
            tv_price_feature.setVisibility(View.VISIBLE);
            tv_price.setVisibility(View.VISIBLE);
            tv_price_uint.setVisibility(View.VISIBLE);
            tv_price_old.setVisibility(View.VISIBLE);
            tv_price_feature.setVisibility(guigeVo.getDiscount().equals("1") ? View.VISIBLE : View.GONE);
            tv_price.setText("￥" + guigeVo.getAvgPrice() + "元");
            tv_price_uint.setText("/斤");
            //需求不显示最低价标志
            if (!TextUtils.isEmpty(guigeVo.getOldPrice())) {
                tv_price_old.setText("￥" + guigeVo.getOldPrice());
                tv_price_old.setVisibility(View.VISIBLE);
                tv_price_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                tv_price_old.setVisibility(View.GONE);
            }
        }

        //需求不显示
//        TextView tv_now_buy = (TextView) view.findViewById(R.id.tv_now_buy);
//        tv_now_buy.setVisibility(View.GONE);
//        onClick(tv_now_buy, position, childPosition, 2);

        int carGoodNum = guigeVo.getCarGoodNum();
        TextView tv_buy_add = (TextView) view.findViewById(R.id.tv_buy_add);
        LinearLayout ll_add_sub = (LinearLayout) view.findViewById(R.id.ll_add_sub);
        if (carGoodNum <= 0) {
            onClick(tv_buy_add, position, childPosition, 3);
            tv_buy_add.setVisibility(View.VISIBLE);
            ll_add_sub.setVisibility(View.GONE);

        } else {
            tv_buy_add.setVisibility(View.GONE);
            ll_add_sub.setVisibility(View.VISIBLE);
            TextView tv_add = (TextView) view.findViewById(R.id.tv_add);
            onClick(tv_add, position, childPosition, 4);
            TextView tv_sub = (TextView) view.findViewById(R.id.tv_sub);
            onClick(tv_sub, position, childPosition, 5);
            TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
            onClick(tv_num, position, childPosition, 6);
            tv_num.setText("" + carGoodNum);

            TextView tv_max_hint = (TextView) view.findViewById(R.id.tv_max_hint);
//            int totalWeight = Integer.valueOf(guigeVo.getTotalWeight());
//            if (totalWeight > 0){
//                tv_max_hint.setText("可购"+totalWeight+"件");
//                tv_max_hint.setVisibility(View.VISIBLE);
//            }else {
//                tv_max_hint.setVisibility(View.GONE);
//            }
        }
        return view;
    }

    private void onClick(View view, final int position, final int childPosition, final int type) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandItemClidkListener != null) {
                    expandItemClidkListener.onItemClick(position, childPosition, type);
                }
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_all_order_list, parent, false);
        }
        LinearLayout ll_item = ViewHolder.get(convertView, R.id.ll_item);
        LinearLayout ll_price_pj = ViewHolder.get(convertView, R.id.ll_price_pj);
        LinearLayout ll_detail = ViewHolder.get(convertView, R.id.ll_cate_detail);
        LinearLayout ll_add_sub = ViewHolder.get(convertView, R.id.ll_add_sub);
        ImageView iv_biz = ViewHolder.get(convertView, R.id.iv_biz);
        TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
        TextView tv_biz_alias = ViewHolder.get(convertView, R.id.tv_biz_alias);
        TextView tv_choose_cate = ViewHolder.get(convertView, R.id.tv_choose_cate);
        TextView tv_price_feature = ViewHolder.get(convertView, R.id.tv_price_feature);
        TextView tv_price = ViewHolder.get(convertView, R.id.tv_price);
        TextView tv_price_uint = ViewHolder.get(convertView, R.id.tv_price_uint);
        TextView tv_price_old = ViewHolder.get(convertView, R.id.tv_price_old);
        TextView tv_buy_add = ViewHolder.get(convertView, R.id.tv_buy_add);
        TextView tv_biz_des = ViewHolder.get(convertView, R.id.tv_biz_des);
        TextView tv_add = ViewHolder.get(convertView, R.id.tv_add);
        TextView tv_sub = ViewHolder.get(convertView, R.id.tv_sub);
        TextView tv_num = ViewHolder.get(convertView, R.id.tv_num);
        TextView tv_max_hint = ViewHolder.get(convertView, R.id.tv_max_hint);
        GoodsVo goodsVo = (GoodsVo) datas.get(position);
        //0删除 1规格 2现在购买 3购买 4加 5减  todo6输入数字
        onClick(ll_item, position, -1, -1);//item点击事件

        ImageLoadUtils.loadingCommonPic(context, goodsVo.getImage(), iv_biz);
        tv_price_old.setVisibility(View.GONE);

        StringBuilder titleBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(goodsVo.getBrand())){
            titleBuilder.append("[");
            titleBuilder.append(goodsVo.getBrand());
            titleBuilder.append("]");
        }
        titleBuilder.append(goodsVo.getFullName());
        if (!TextUtils.isEmpty(goodsVo.getFeature())){
            titleBuilder.append(" ");
            titleBuilder.append(goodsVo.getFeature());
        }
        tv_title.setText(titleBuilder.toString());

        if (!TextUtils.isEmpty(goodsVo.getGoodsAlias())) {
            tv_biz_alias.setText("别名: " + goodsVo.getGoodsAlias());
            tv_biz_alias.setVisibility(View.VISIBLE);
        } else {
            tv_biz_alias.setVisibility(View.GONE);
        }

        List<GuigeVo> guigeVos = goodsVo.getGuige();
        if (guigeVos != null && !guigeVos.isEmpty()) {
            GuigeVo guigeVo1 = goodsVo.getGuige().get(0);
        //均价显示
        if (guigeVo1.getShowState().equals("0")){
            ll_price_pj.setVisibility(View.GONE);
        }else {
            ll_price_pj.setVisibility(View.VISIBLE);
        }
        tv_price_feature.setVisibility(goodsVo.getDiscount().equals("1") ? View.VISIBLE : View.GONE);
//        myViewHolder.tv_biz_des.setText("￥" + goodsVo.getCurrentPrice() + "/" + goodsVo.getSpec());
        String[] avgPrice = goodsVo.getAvgPrice().split(",");
        tv_price.setText("￥" + avgPrice[0] + "元");
        tv_price_uint.setText("/斤" /*+ goodsVo.getBaseSpec()*/);
        if (!TextUtils.isEmpty(goodsVo.getOldPrice())) {
            String[] oldPrice = goodsVo.getOldPrice().split(",");
            tv_price_old.setText("￥" + oldPrice[0]);
//            tv_price_old.setVisibility(View.VISIBLE);
            tv_price_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tv_price_old.setVisibility(View.GONE);
        }

            StringBuilder desBuilder = new StringBuilder();
            desBuilder.append("￥");
            desBuilder.append(guigeVo1.getCurrentPrice());
            desBuilder.append("元/");
            desBuilder.append(goodsVo.getBaseSpec());

            if (!guigeVo1.getShowState().equals("0") && !TextUtils.isEmpty(guigeVo1.getTotalWeight())){
                desBuilder.append("(");
                desBuilder.append(guigeVo1.getTotalWeight());
                desBuilder.append("斤)");
            }
            tv_biz_des.setText(desBuilder.toString());

            if (guigeVos.size() > 1) {
                tv_choose_cate.setVisibility(View.VISIBLE);
                tv_buy_add.setVisibility(View.GONE);
//                tv_biz_des.setVisibility(View.GONE);
                ll_add_sub.setVisibility(View.GONE);
                tv_choose_cate.setText(goodsVo.isGuige() ? "收起" : "选规格");
                onClick(tv_choose_cate, position, -1, 1);
                ll_detail.removeAllViews();
                if (goodsVo.isGuige()) {
                    int i = 0;
                    for (GuigeVo guigeVo : guigeVos) {
                        ll_detail.addView(getGuigeView(guigeVo, goodsVo.getBaseSpec(), position, i));
                        i++;
                    }
                }
            } else {
                tv_choose_cate.setVisibility(View.GONE);
                tv_buy_add.setVisibility(View.VISIBLE);
                tv_biz_des.setVisibility(View.VISIBLE);
//                GuigeVo guigeVo = goodsVo.getGuige().get(0);
//                StringBuilder desBuilder = new StringBuilder();
//                desBuilder.append("￥");
//                desBuilder.append(guigeVo.getCurrentPrice());
//                desBuilder.append("/");
//                desBuilder.append(goodsVo.getBaseSpec());
//                if (!TextUtils.isEmpty(guigeVo.getTotalWeight())){
//                    desBuilder.append("(");
//                    desBuilder.append(guigeVo.getTotalWeight());
//                    desBuilder.append("斤)");
//                }
//                tv_biz_des.setText(desBuilder.toString());
//                myViewHolder.tv_biz_des.setText("￥" + goodsVo.getCurrentPrice() + "/" + goodsVo.getSpec());
                // 0删除 1规格 2现在购买 3购买 4加 5减  todo6输入数字
                int carGoodNum = guigeVo1.getCarGoodNum();
                if (carGoodNum <= 0) {
                    onClick(tv_buy_add, position, 0, 3);
                    tv_buy_add.setVisibility(View.VISIBLE);
                    ll_add_sub.setVisibility(View.GONE);

                } else {
                    tv_buy_add.setVisibility(View.GONE);
                    ll_add_sub.setVisibility(View.VISIBLE);
                    onClick(tv_add, position, 0, 4);
                    onClick(tv_sub, position, 0, 5);
                    onClick(tv_num, position, 0, 6);
                    tv_num.setText("" + carGoodNum);
//                        int totalWeight = Integer.valueOf(guigeVo.getTotalWeight());
//                        if (totalWeight > 0){
//                            myViewHolder.tv_max_hint.setText("可购"+totalWeight+"件");
//                            myViewHolder.tv_max_hint.setVisibility(View.VISIBLE);
//                        }else {
//                            myViewHolder.tv_max_hint.setVisibility(View.GONE);
//                        }
                }
            }

        } else {
            ll_price_pj.setVisibility(View.GONE);
            tv_choose_cate.setVisibility(View.GONE);
            tv_buy_add.setVisibility(View.GONE);
        }
        return convertView;
    }
}
