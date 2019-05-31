package com.lf.shoppingmall.activity.custom_service;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.adapter.GoodsDetailCateGridAdapter;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lf.shoppingmall.weight.MyDatePop;
import com.lr.baseview.utils.DatePickerDialogFragment;
import com.lr.baseview.utils.DateUtils;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.widget.CustomGridView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的发票
 * Created by Administrator on 2017/8/28.
 */

public class NyFapiao extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.rv_cate)
    CustomGridView rv_cate;
    @Bind(R.id.tv_taitou)
    EditText tvTaitou;
    @Bind(R.id.tv_shibiehao)
    EditText tvShibiehao;
    @Bind(R.id.tv_address)
    EditText tvAddress;
    @Bind(R.id.tv_brand_account)
    EditText tvBrandAccount;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_date_frist)
    TextView tvDateFrist;
    @Bind(R.id.tv_email)
    EditText tvEmail;
    @Bind(R.id.tv_reg_phone)
    EditText tvRegPhone;
    @Bind(R.id.tv_tell_phone)
    EditText tvTellPhone;
    @Bind(R.id.ll_date)
    LinearLayout llDate;
    @Bind(R.id.ll_first_date)
    LinearLayout llFirstDate;
    @Bind(R.id.btn_login)
    Button btnLogin;
    private GoodsDetailCateGridAdapter cateGridAdapter;

    private int currentMonth = 0, currentDay = 0;
    private MyDatePop datePop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_fapiao;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String[] types = new String[]{"电子普通发票", "普通发票", "专用发票"};
        cateGridAdapter = new GoodsDetailCateGridAdapter(context, null, types);
        rv_cate.setAdapter(cateGridAdapter);
        rv_cate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cateGridAdapter.curruntCate = position;
                cateGridAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected Object getTitleText() {
        return "开票信息";
    }

    /**
     * 我的发票
     */
    private void mynvoice(String taitoou, String shibiehao, String date, String firstDate) {

        UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("token", userVo.getToken());
            map.put("type", String.valueOf(cateGridAdapter.curruntCate));
            map.put("start", taitoou);//发票抬头
            map.put("taxpayerId", shibiehao);//纳税人识别号
            map.put("regAddres", tvAddress.getText().toString().trim()); //注册地址
            map.put("regPhone", tvRegPhone.getText().toString().trim()); //注册电话
            map.put("telphone", tvTellPhone.getText().toString().trim()); //手机号
            map.put("account", tvBrandAccount.getText().toString().trim()); //银行账号
            map.put("addTime", date); //开票时间
            map.put("startTime", firstDate); //首次开票起始日
            map.put("mail", tvEmail.getText().toString().trim()); //邮箱
            String des = new Gson().toJson(map);
            LogUtils.e("我的发票", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.mynvoice)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("我的发票", "body---->" + body);
                showToast("提交成功!");
                finish();
            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }

    @OnClick({R.id.ll_date, R.id.ll_first_date, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_date:
                if (datePop == null) {
                    datePop = new MyDatePop(context, currentMonth, currentDay, new MyDatePop.OnDatePopListener() {
                        @Override
                        public void onDateClick(String month, String day, int currentMonth, int currentDay) {
                            tvDate.setText(month + "\t\t" + day);
                            NyFapiao.this.currentMonth = currentMonth;
                            NyFapiao.this.currentDay = currentMonth;
                        }
                    });
                }
                datePop.showAsDropDown(iv_back);
                break;

            case R.id.ll_first_date:
                DatePickerDialogFragment.showDialog(getSupportFragmentManager(), DateUtils.getTodayDateTime(),
                        new DatePickerDialogFragment.OnDateSettingListener() {
                            @Override
                            public void onDateSet(String date) {
                                tvDateFrist.setText(date);
                            }
                        });
                break;

            case R.id.btn_login:
                checkInfo();
                break;
        }
    }

    private void checkInfo() {
        String taitou = tvTaitou.getText().toString().trim();
        if (TextUtils.isEmpty(taitou)) {
            showToast("请输入营业执照注册名称");
            return;
        }

        String shibiehao = tvShibiehao.getText().toString().trim();
        if (TextUtils.isEmpty(shibiehao)) {
            showToast("请输入营业执照注册名称");
            return;
        }

        String date = tvDate.getText().toString().trim();
        if (TextUtils.isEmpty(date)) {
            showToast("请选择开票时间");
            return;
        }
        String dateFirst = tvDateFrist.getText().toString().trim();
        if (TextUtils.isEmpty(dateFirst)) {
            showToast("请选择首次开票起始日");
            return;
        }

        mynvoice(taitou, shibiehao, date, dateFirst);
    }
}
