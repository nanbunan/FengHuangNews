package com.example.administrator.news.utils;

import android.app.Activity;
import android.content.Context;

import com.example.administrator.news.view.CommonProgressDialog;

/**
 * Created by Administrator on 2017/4/19.
 */

public class ProgressDialogUtils {
    private CommonProgressDialog dialog;
    private Activity context;
    //显示方法
    public void showProgressDialog(Activity context, String msg){
        this.context=context;

            if(dialog==null){
                dialog=new CommonProgressDialog(context);
            }
            if(msg==null){
                msg="正在加载...";

            dialog.setMessage(msg);
        }
       if(!context.isFinishing()&&!dialog.isShowing()){
           dialog.show();
       }
    }

    public void closeProgressDialog(){
        if(dialog!=null&&!context.isFinishing()){
            dialog.dismiss();
            dialog=null;
        }
    }

}
