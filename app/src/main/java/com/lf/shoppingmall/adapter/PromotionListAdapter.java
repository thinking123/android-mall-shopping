package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.graphics.Paint;
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
import com.lr.baseview.utils.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3.
 */

public class PromotionListAdapter extends BaseLGAdapter {

    public OnExpandItemClidkListener expandItemClidkListener;
    public int status; //0为常用清单 1是搜索

    public PromotionListAdapter(Context context, ArrayList datas, OnExpandItemClidkListener listener) {
        super(context, datas);
        this.expandItemClidkListener = listener;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object object = datas.get(position);
        if (object instanceof String) {
            MyCateViewHolder cateViewHolder = null;
            if (convertView != null && convertView.getTag() instanceof MyCateViewHolder) {
                cateViewHolder = (MyCateViewHolder) convertView.getTag();
            } else {
                convertView = inflater.inflate(R.layout.include_item_common_order_list_cate, parent, false);
                cateViewHolder = new MyCateViewHolder();
                cateViewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(cateViewHolder);
            }
            cateViewHolder.tv_title.setText((String) object);

        } else {
            MyViewHolder myViewHolder = null;
            if (convertView != null && convertView.getTag() instanceof MyViewHolder) {
                myViewHolder = (MyViewHolder) convertView.getTag();

            } else {
                convertView = inflater.inflate(R.layout.item_common_order_list, parent, false);
                myViewHolder = new MyViewHolder();
                myViewHolder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
                myViewHolder.tv_jbj = (TextView) convertView.findViewById(R.id.tv_jbj);
                myViewHolder.iv_biz_pic = (ImageView) convertView.findViewById(R.id.iv_biz);
                myViewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                myViewHolder.tv_var = (TextView) convertView.findViewById(R.id.tv_var);
                myViewHolder.line_var = convertView.findViewById(R.id.lint_var);
                myViewHolder.iv_del = (ImageView) convertView.findViewById(R.id.iv_del);
                myViewHolder.ll_detail = (LinearLayout) convertView.findViewById(R.id.ll_detail);
                convertView.setTag(myViewHolder);
            }

            GoodsVo goodsVo = (GoodsVo) object;
            //0删除 1规格 2现在购买 3购买 4加 5减  todo6输入数字
            onClick(myViewHolder.ll_item, position, -1, -1);//item点击事件
            onClick(myViewHolder.iv_del, position, -1, 0);
            onClick(myViewHolder.tv_var, position, -1, 1);

            myViewHolder.iv_del.setVisibility(status == 1 ? View.GONE : View.VISIBLE);
//            myViewHolder.tv_jbj.setVisibility(View.GONE );
            ImageLoadUtils.loadingCommonPic(context, goodsVo.getImage(), myViewHolder.iv_biz_pic);

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
            myViewHolder.tv_title.setText(titleBuilder.toString());

            List<GuigeVo> guigeVos = goodsVo.getGuige();
            if (guigeVos != null && !guigeVos.isEmpty()) {
                if (guigeVos.size() > 1) {
                    myViewHolder.tv_var.setVisibility(View.VISIBLE);
                    myViewHolder.line_var.setVisibility(View.VISIBLE);
                    myViewHolder.tv_var.setText(goodsVo.isGuige() ? "收起" : "选规格");

                } else {
                    myViewHolder.tv_var.setVisibility(View.GONE);
                    myViewHolder.line_var.setVisibility(View.GONE);
                }

                myViewHolder.ll_detail.removeAllViews();
                if (goodsVo.isGuige()) {
                    int i = 0;
                    for (GuigeVo guigeVo : guigeVos) {
                        myViewHolder.ll_detail.addView(getGuigeView(guigeVo, goodsVo.getBaseSpec(), position, i));
                        i++;
                    }
                } else {
                    myViewHolder.ll_detail.addView(getGuigeView(guigeVos.get(0), goodsVo.getBaseSpec(), position, 0));
                }

            } else {
                myViewHolder.tv_var.setVisibility(View.GONE);
                myViewHolder.line_var.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    private class MyCateViewHolder {
        public TextView tv_title;
    }

    private class MyViewHolder {

        public LinearLayout ll_item;
        public TextView tv_jbj;
        public ImageView iv_biz_pic;
        public TextView tv_title;
        public TextView tv_var;
        public View line_var;
        public ImageView iv_del;
        public LinearLayout ll_detail;
    }

    private View getGuigeView(GuigeVo guigeVo, String uint, int position, int childPosition) {
        View view = inflater.inflate(R.layout.include_common_order_list_detail, null);
        // 0删除 1规格 2现在购买 3购买 4加 5减  todo6输入数字
        TextView tv_price_feature = (TextView) view.findViewById(R.id.tv_price_feature);
        tv_price_feature.setVisibility(guigeVo.getDiscount().equals("1") ? View.VISIBLE : View.GONE);

        TextView tv_biz_des = (TextView) view.findViewById(R.id.tv_biz_des);
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

        TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_price.setText("￥" + guigeVo.getAvgPrice() +"元");

        TextView tv_price_uint = (TextView) view.findViewById(R.id.tv_price_uint);
        tv_price_uint.setText("/斤");
        //需求不显示最低价标志
        TextView tv_price_old = (TextView) view.findViewById(R.id.tv_price_old);
        if (Float.valueOf(guigeVo.getOldPrice()) > 0) {
            tv_price_old.setText("￥" + guigeVo.getOldPrice());
            tv_price_old.setVisibility(View.VISIBLE);
            tv_price_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tv_price_old.setVisibility(View.GONE);
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
}
