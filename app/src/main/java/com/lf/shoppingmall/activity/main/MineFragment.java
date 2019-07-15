package com.lf.shoppingmall.activity.main;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.custom_service.ActivitySaoma;
import com.lf.shoppingmall.activity.custom_service.CommonProlemActivity;
import com.lf.shoppingmall.activity.custom_service.CustomServiceMainActivity;
import com.lf.shoppingmall.activity.custom_service.MyYaoshi;
import com.lf.shoppingmall.activity.custom_service.NyFapiao;
import com.lf.shoppingmall.activity.custom_service.ServiceCenterActivity;
import com.lf.shoppingmall.activity.custom_service.SubmitGoodsActivity;
import com.lf.shoppingmall.activity.custom_service.UserManagerActivity;
import com.lf.shoppingmall.activity.order.MyOrderActivity;
import com.lf.shoppingmall.activity.red_bag.RedBagActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.base.BaseFragment;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.common.ComParams;
import com.lr.baseview.utils.CommonUilts;
import com.lr.baseview.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/8.
 */

public class MineFragment extends BaseFragment {
//    @BindView(R.id.civ_my_pic)
//    CircleImageView civMyPic;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;
    @BindView(R.id.tv_wallet_num)
    TextView tvWalletNum;
    @BindView(R.id.tv_red_bag_num)
    TextView tvRedBagNum;
    @BindView(R.id.tv_advance_charge)
    TextView tvAdvanceCharge;

    @Override
    protected void init() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

        initUserInfo();
    }

    private void initUserInfo() {
        UserVo userVo = ((BaseApplication) getActivity().getApplication()).getUserVo();
        tvUserName.setText(userVo.getStoreName());
        tvUserLevel.setText(userVo.getStoreAddress());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_notice, R.id.civ_my_pic, R.id.tv_user_name, R.id.tv_user_level, R.id.tv_account_manager,
            R.id.ll_red_bag, R.id.ll_advance_charge, R.id.tv_order_check, R.id.ll_order_advance, R.id.ll_wait_laungch,
            R.id.ll_wait_recever, R.id.ll_my_yaoshi, R.id.ll_common_order, R.id.ll_tuiyanjin, R.id.ll_xinpinxuqiu, R.id.ll_My_fapiao,
            R.id.ll_jingying_fenxi, R.id.tv_custom_service, R.id.ll_common_problem, R.id.ll_service_center, R.id.ll_online_custom,
            R.id.ll_service_phone, R.id.ll_xiaoshou, R.id.ll_saoma, R.id.ll_wallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_notice:
                break;

            case R.id.civ_my_pic://帐号管理
            case R.id.tv_user_name:
            case R.id.tv_user_level:
            case R.id.tv_account_manager:
                getActivity().startActivityForResult(new Intent(getActivity(), UserManagerActivity.class), 0);
                break;

            case R.id.ll_wallet:
                break;

            case R.id.ll_saoma://扫码下载
                startActivity(new Intent(getActivity(), ActivitySaoma.class));
                break;

            case R.id.ll_red_bag://优惠券
                startActivity(new Intent(getActivity(), RedBagActivity.class));
                break;

            case R.id.ll_advance_charge:
                break;

            case R.id.tv_order_check://我的全部订单
                startActivity(new Intent(getActivity(), MyOrderActivity.class).putExtra("page", 0));
                break;

            case R.id.ll_order_advance://待付款
                startActivity(new Intent(getActivity(), MyOrderActivity.class).putExtra("page", 1));
                break;

            case R.id.ll_wait_laungch://已发货
                startActivity(new Intent(getActivity(), MyOrderActivity.class).putExtra("page", 2));
                break;

            case R.id.ll_wait_recever://待收货
                startActivity(new Intent(getActivity(), MyOrderActivity.class).putExtra("page", 3));
                break;

            case R.id.ll_my_yaoshi:
                startActivity(new Intent(getActivity(), MyYaoshi.class));
                break;

            case R.id.ll_common_order://常用清单
                ((MainActivity) getActivity()).setCurrntPager(2);
                break;

            case R.id.ll_tuiyanjin:
                break;

            case R.id.ll_xinpinxuqiu://新品需求
                startActivity(new Intent(getActivity(), SubmitGoodsActivity.class));
                break;

            case R.id.ll_My_fapiao:
                startActivity(new Intent(getActivity(), NyFapiao.class));
                break;
            case R.id.ll_jingying_fenxi:
                break;

            case R.id.tv_custom_service://客服中心
                startActivity(new Intent(getActivity(), CustomServiceMainActivity.class));
                break;

            case R.id.ll_common_problem:
                startActivity(new Intent(getActivity(), CommonProlemActivity.class));
                break;

            case R.id.ll_service_center:
                startActivity(new Intent(getActivity(), ServiceCenterActivity.class));
                break;

            case R.id.ll_online_custom:
                break;

            case R.id.ll_service_phone://客服电话
                CommonUilts.showTellDialog(getActivity(), ComParams.TELL_COUSTOM_SERVICE);
                break;
            case R.id.ll_xiaoshou://联系销售
                CommonUilts.showTellDialog(getActivity(), ComParams.TELL_xiaoshou);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == getActivity().RESULT_OK) {
            initUserInfo();
        }
    }
}
