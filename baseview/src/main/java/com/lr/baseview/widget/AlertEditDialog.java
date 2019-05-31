package com.lr.baseview.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lr.baseview.utils.ToastUtils;
import com.yunlan.baseview.R;

/**
 * 仿IOS输入框
 */
public class AlertEditDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private TextView txt_title;
    private EditText edt_msg;
    private Button btn_neg, btn_pos;
    private LinearLayout lLayout_bg;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public AlertEditDialog(Context context) {

        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertEditDialog builder() {
        //获取dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_alerteditdialog, null);

        //获取自定义dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
//        txt_title.setVisibility(View.GONE);
        edt_msg = (EditText) view.findViewById(R.id.edit_msg);
//        edt_msg.setVisibility(View.GONE);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);


        //自定义dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        //调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT));
        return this;
    }


    public AlertEditDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public AlertEditDialog setMsg(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            edt_msg.setHint("提示文字");
        } else {
            edt_msg.setHint(msg);
        }
        return this;
    }

    //设置未fales按返回键不能退出，默认为true
    public AlertEditDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }


    public AlertEditDialog setPositiveButton(String text, final OnPClick clickListener) {
        showPosBtn = true;
        if (TextUtils.isEmpty(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }

        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    String msg = edt_msg.getText().toString().trim();
                    if (TextUtils.isEmpty(msg)) {
                        ToastUtils.showToast(context, "请输入内容");
                        return;
                    }
                    clickListener.onClick(v, msg);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertEditDialog setNegativeButton(String text, final View.OnClickListener clickListener) {
        showNegBtn = true;
        if (TextUtils.isEmpty(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }

        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null)
                    clickListener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

//    private void setLayout(){
//        if (!showTitle&&!showMsg){
//            txt_title.setText("提示");
//            txt_title.setVisibility(View.VISIBLE);
//        }
//        if (showTitle){
//            txt_title.setVisibility(View.VISIBLE);
//        }
//        if (showMsg){
//            edt_msg.setVisibility(View.VISIBLE);
//        }
//
//    }

    public void show() {
//        setLayout();
        dialog.show();
    }

    public interface OnPClick {
        void onClick(View v, String result);
    }

}
