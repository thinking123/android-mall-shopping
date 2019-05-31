package com.lf.shoppingmall.weight.mybannr;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/14.
 */

public class MyBannerAdapter extends PagerAdapter {

    private ArrayList<ImageView> vpList;

    public MyBannerAdapter(ArrayList<ImageView> vpList) {
        this.vpList = vpList;
    }

    @Override
    public int getCount() {
        return vpList != null ? vpList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView iv = vpList.get(position);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
