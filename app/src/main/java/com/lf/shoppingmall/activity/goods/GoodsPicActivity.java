package com.lf.shoppingmall.activity.goods;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lr.baseview.utils.ImageLoadUtils;
import com.lr.baseview.utils.ImmersionStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 详情图片
 * Created by Administrator on 2017/9/25.
 */

public class GoodsPicActivity extends BaseActivity {
    @BindView(R.id.vp_banner)
    ViewPager vpBanner;
    @BindView(R.id.tv_bannar_num)
    TextView tvBannarNum;

    private int bannerSize;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_pic;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.black); // 设置图片的沉浸式
        ButterKnife.bind(this);

        List<View> views = new ArrayList<>();
        final List<String> banners = getIntent().getStringArrayListExtra("banners");
        int currentIndex = getIntent().getIntExtra("currentIndex", 0);
        bannerSize = banners.size();
        for (String image : banners) {
            ImageView imageView = new ImageView(context);
            ImageLoadUtils.loadingCommonPicBig(context, image, imageView);
            views.add(imageView);
        }
        vpBanner.setAdapter(new MyPagerAdapter(views));
        vpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvBannarNum.setText((position + 1) + "/" + bannerSize);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpBanner.setCurrentItem(currentIndex);
    }

    @Override
    protected Object getTitleText() {
        return null;
    }

    @OnClick(R.id.fl_content)
    public void onViewClicked() {
        finish();
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
