package com.example.administrator.news.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.example.administrator.news.R;

/**
 * Created by Administrator on 2017/4/19.
 */

public class CommonProgressDialog extends Dialog {
    public CommonProgressDialog(Context context) {
        super(context,R.style.commonProgressDialog);
        setContentView(R.layout.commonprogressdialog);

        //显示在屏幕中间
        getWindow().getAttributes().gravity= Gravity.CENTER;
    }
    public void setMessage(String s){
        TextView textview= (TextView) findViewById(R.id.id_tv_loading);
        textview.setText(s);
    }
}
