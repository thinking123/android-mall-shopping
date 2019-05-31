package com.lf.shoppingmall.activity.goods;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.main.ShopCatFragemnt;
import com.lf.shoppingmall.base.BaseActivity;
import com.lr.baseview.utils.ImmersionStatus;

import butterknife.ButterKnife;

/**
 * 商品详情进入的购物车界面
 * Created by Administrator on 2017/8/11.
 */

public class ShopCateActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_cate;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        ShopCatFragemnt shopCatFragemnt = ShopCatFragemnt.getInstance();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_content, shopCatFragemnt).commit();
    }

    @Override
    protected Object getTitleText() {
        return null;
    }
}
