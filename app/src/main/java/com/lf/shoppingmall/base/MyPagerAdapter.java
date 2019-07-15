package com.lf.shoppingmall.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.List;

/**
 * fargment的通用适配器
 *
 * @author liushubao
 *         Created by admin on 2017/3/21.
 */
public class MyPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private List<T> mFragments;//添加的Fragment的集合
    private List<String> mFragmentsTitles;//每个Fragment对应的title的集合
    private FragmentManager mFragmentManager;

    public MyPagerAdapter(FragmentManager fm, List<T> fragments, List<String> titles) {
        super(fm);
        mFragmentManager = fm;
        mFragments = fragments;
        mFragmentsTitles = titles;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = mFragments.get(position);
        fragment.setMenuVisibility(true);
        if (!fragment.isAdded()) { // 如果fragment还没有added
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
//            ft.commit();
            ft.commitAllowingStateLoss();
            /**
             * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
             * 会在进程的主线程中，用异步的方式来执行。 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
             * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             */
            mFragmentManager.executePendingTransactions();
        }

        if (fragment.getView() != null) {
            if (fragment.getView().getParent() == null) {
                container.addView(fragment.getView()); // 为viewpager增加布局
            }
        }
        return fragment/*.getView()*/;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mFragments.get(position).getView());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentsTitles == null ? null :  mFragmentsTitles.get(position);
    }

}
