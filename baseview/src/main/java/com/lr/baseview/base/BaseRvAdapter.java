package com.lr.baseview.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;


import com.lr.baseview.common.OnRvItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/23.
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter {

    protected Context context;
    protected LayoutInflater inflater;
    protected List<T> list;
    protected OnRvItemClickListener listener;
    protected final int START_PAGE = 1;//起始页

    public BaseRvAdapter(Context context,List<T> list, OnRvItemClickListener listener) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.list = list;
        this.listener = listener;
    }

    public BaseRvAdapter(Context context, List<T> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    /**
     * @param list
     * @param page 和起始页相同为刷新， 否则为加载
     */
    public void setList(List<T> list, int page) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        if (page <= START_PAGE) {
            list.clear();
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
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
