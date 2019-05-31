package com.lf.shoppingmall.activity.login;

import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lr.baseview.utils.ImmersionStatus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 服务条款
 * Created by Administrator on 2017/8/3.
 */

public class TermActivity extends BaseActivity {
    @BindView(R.id.tv_term)
    TextView tv_term;

    private int type = 0;//0是服务条款 1帮助手册 2是优惠券使用规则 3售后规则 4运费规则
    @Override
    protected int getLayoutId() {
        type = getIntent().getIntExtra("type", 0);
        return R.layout.activity_term;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand);
        ButterKnife.bind(this);

        switch (type){
            case 0:
                tv_term.setText(getString(R.string.term));
                break;

            case 1:
                tv_term.setText(getString(R.string.term));
                break;

            case 2:
                tv_term.setText(getString(R.string.term_shouhou));
                break;

            case 3:
                tv_term.setText(getString(R.string.term_shouhou));
                break;

            case 4:
                tv_term.setText(getString(R.string.term_shouhou));
                break;
        }
    }

    @Override
    protected Object getTitleText() {
        String title =  null;
        switch (type){
            case 0:
                title = getResources().getString(R.string.hint_register_term2);
                break;

            case 1:
                title =  getResources().getString(R.string.help_book) ;
                break;

            case 2:
                title =  "优惠券使用说明" ;
                break;

            case 3:
                title =  "四季鲜售后准则" ;
                break;

            case 4:
                title =  "运费规则" ;
                break;
        }
        return title ;
    }
}
