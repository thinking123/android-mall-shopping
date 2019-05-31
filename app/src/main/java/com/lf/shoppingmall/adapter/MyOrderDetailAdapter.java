package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.index.GuigeVo;
import com.lr.baseview.base.BaseLGAdapter;
import com.lr.baseview.utils.ImageLoadUtils;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/9/4.
 */

public class MyOrderDetailAdapter extends BaseLGAdapter<GoodsVo> {

    public int size = 0;

    public MyOrderDetailAdapter(Context context, List<GoodsVo> datas) {
        super(context, datas);
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_my_order_detail, parent, false);
        }


        ImageView iv_biz=  ViewHolder.get(convertView, R.id.iv_biz);
        TextView tv_title=  ViewHolder.get(convertView, R.id.tv_title);
        LinearLayout ll_detail=  ViewHolder.get(convertView, R.id.ll_detail);

        GoodsVo goodsVo = datas.get(position);
        ImageLoadUtils.loadingCommonPic(context, goodsVo.getImage(), iv_biz);
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
        List<GuigeVo> guigeVos = goodsVo.getGoodsSpec();
        if (guigeVos != null && !guigeVos.isEmpty()) {
            ll_detail.removeAllViews();
            int i = 0;
            for (GuigeVo guigeVo : guigeVos) {
                ll_detail.addView(getGuigeView(guigeVo, goodsVo.getBaseSpec(), position, i));
                i++;
            }
        }
        return convertView;
    }

    private View getGuigeView(GuigeVo guigeVo, String uint, int position, int childPosition) {
        View view = inflater.inflate(R.layout.item_order_pay_list_guige, null);

        TextView tv_biz_des = (TextView) view.findViewById(R.id.tv_biz_des);
        StringBuilder desBuilder = new StringBuilder();
        desBuilder.append("￥");
        desBuilder.append(guigeVo.getCurrentPrice());
//        desBuilder.append(uint);
        if (!TextUtils.isEmpty(guigeVo.getSpec())){
            desBuilder.append("/");
            desBuilder.append(guigeVo.getSpec());
//            desBuilder.append(guigeVo.getTotalWeight());
//            desBuilder.append("斤)");
        }
        tv_biz_des.setText(desBuilder.toString());

        TextView tv_car_num = (TextView) view.findViewById(R.id.tv_car_num);
        tv_car_num.setText("X" + guigeVo.getGoodsAmount());

        return view;
    }
}
