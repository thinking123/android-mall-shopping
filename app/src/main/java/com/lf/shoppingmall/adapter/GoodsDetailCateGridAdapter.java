package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lr.baseview.base.BaseLGAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */

public class GoodsDetailCateGridAdapter extends BaseLGAdapter {

    public int curruntCate;
    public String[] strings;

    public GoodsDetailCateGridAdapter(Context context, List datas, String[] strings) {
        super(context, datas);
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings == null ? 0 : strings.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_goods_detail_cate, parent, false);
        }
        TextView tv_cate = ViewHolder.get(convertView, R.id.tv_cate);
        tv_cate.setText(strings[position]);
        if (curruntCate == position){
            tv_cate.setTextColor(ContextCompat.getColor(context, R.color.white));
            tv_cate.setBackgroundResource(R.drawable.bg_green_corner5);
        }else {
            tv_cate.setTextColor(ContextCompat.getColor(context, R.color.text_norm_6));
            tv_cate.setBackgroundResource(R.drawable.bg_corner8_grey);
        }
        return convertView;
    }
}
