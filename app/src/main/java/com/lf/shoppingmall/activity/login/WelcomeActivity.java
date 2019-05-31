package com.lf.shoppingmall.activity.login;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.bean.other.WelcomeBean;
import com.lf.shoppingmall.bean.other.WelcomeDetailBean;
import com.lf.shoppingmall.common.ComParams;
import com.lf.shoppingmall.weight.mybannr.MyDot;
import com.lr.baseview.utils.ImageLoadUtils;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.utils.SharePreferenceUtil;
import com.lr.baseview.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.list;

/**
 * 欢迎界面
 * 根据 SharePreferenceUtil spUtil = new SharePreferenceUtil(context);
 * boolean frist_goin = spUtil.getBoolean(context, ComParams.FRIST_GOIN, false);
 * 进入
 * <p>
 * 下载app首次进入显示
 * 升级后显示（注意升级后frist_goin置为false）
 * Created by Administrator on 2017/8/3.
 */

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.vp_welcome)
    ViewPager vpWelcome;
    @BindView(R.id.tv_do)
    TextView tv_do;
    @BindView(R.id.ll_myDot_biz)
    MyDot ll_myDot;

    private List<WelcomeDetailBean> list;
    private long firstTime;//点击返回键第一次时间

    private int[] images = new int[]{R.mipmap.guide_2, R.mipmap.guide_3, R.mipmap.guide_4};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        WelcomeBean welcomeBean = (WelcomeBean) getIntent().getSerializableExtra("WelcomeBean");
        list = welcomeBean.getWelcomeList();
        List<View> views = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            int i = 1;
            for (WelcomeDetailBean picUr : list) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                ImageLoadUtils.loadingWelcomePic(context, picUr.getUrl(), imageView);
                if (i == list.size()) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharePreferenceUtil spUtil = new SharePreferenceUtil(context);
                            spUtil.put(context, ComParams.FRIST_GOIN, true);
                            startActivityForResult(new Intent(WelcomeActivity.this, LoginActivity.class), 0);
                            back();
                        }
                    });
                }
                views.add(imageView);
                i++;
            }
            ll_myDot.setVisibility(View.VISIBLE);
            ll_myDot.setCountDot(list.size());

        } else {
            int i = 1;
            for (int image : images) {
                ImageView imageView = new ImageView(context);
                ImageLoadUtils.loadingCommonPic(context, image, imageView);
                if (i == images.length) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharePreferenceUtil spUtil = new SharePreferenceUtil(context);
                            spUtil.put(context, ComParams.FRIST_GOIN, true);
                            startActivityForResult(new Intent(WelcomeActivity.this, LoginActivity.class), 0);
                            back();
                        }
                    });
                }
                views.add(imageView);
                i++;
            }
        }

        vpWelcome.setAdapter(new MyPagerAdapter(views));
        vpWelcome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (list != null) {
                    ll_myDot.refreshStatus(position);
                    int size = list.size();
                    if (position + 1 == size) {
                        tv_do.setVisibility(View.VISIBLE);
                    } else {
                        tv_do.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected Object getTitleText() {
        return null;
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

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - firstTime > 2000) {
            ToastUtils.showToast(this, "再按一次退出");
            firstTime = currentTime;
        } else {
            setResult(RESULT_OK);
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
