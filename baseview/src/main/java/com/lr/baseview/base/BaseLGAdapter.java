package com.lr.baseview.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import com.lr.baseview.common.OnRvItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView GridView 的basedapter
 *
 * @author liushubao
 *         Created by admin on 2017/3/6.
 */

public abstract class BaseLGAdapter<T> extends BaseAdapter {

    protected Context context;
    protected LayoutInflater inflater;
    protected List<T> datas;
    protected OnRvItemClickListener listener;

    public BaseLGAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }

    /**
     * 创建adapter时带有数据
     *
     * @param context
     * @param datas
     */
    public BaseLGAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
        this.inflater = LayoutInflater.from(this.context);
    }


    public BaseLGAdapter(Context context,List<T> list, OnRvItemClickListener listener) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.datas = list;
        this.listener = listener;
    }

    /**
     * 获取datas
     *
     * @return
     */
    public List<T> getList() {
        return datas;
    }

    /**
     * 添加数据
     *
     * @param datas
     * @param type  等于1为赋值，大于1为添加数据
     */
    public void setList(List<T> datas, int type) {
        if (this.datas == null) {
            this.datas = new ArrayList<>();
        }

        if (type <= 1) {
            this.datas.clear();
        }

        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 通用的ViewHolder
     * 使用方法：在getView中使用
     * convertView = inflater.inflate(layoutId, null);
     * TextView tv = ViewHolder.get(convertView, tvId);
     */
    public static class ViewHolder {
        public static <T extends View> T get(View convertView, int resId) {
            SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<>();
                convertView.setTag(viewHolder);
            }

            View childView = viewHolder.get(resId);
            if (childView == null) {
                childView = convertView.findViewById(resId);
                viewHolder.put(resId, childView);
            }
            return (T) childView;
        }
    }

    protected void commonViewOnClick(View view, final  int position, final int type){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onItemClick(position, type);
                }
            }
        });
    }
}
