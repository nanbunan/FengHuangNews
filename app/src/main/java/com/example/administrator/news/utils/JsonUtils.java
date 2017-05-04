package com.example.administrator.news.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.news.bean.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/20.
 */

public class JsonUtils {
    private List<Data>[] msg_list;
    CallBackListener listener;
    public JsonUtils(){}
    public JsonUtils(CallBackListener listener){
        this.listener=listener;
    }
//获取网络数据
    public void getResult(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //1.创建一个okhttpclient对象,一个app最好实例化一个对象
                OkHttpClient client=new OkHttpClient();
                //新建一个请求
                Request request=new Request.Builder().url("http://news.ifeng.com/").build();
                //执行请求获得相应数据
                Call call=client.newCall(request);

                //加入调度执行回调处理
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()){
                            String str=response.body().string();
                            Message msg=new Message();
                            msg.obj=str;
                            msg.arg1=0x1;
                            handler.sendMessage(msg);

                        }

                    }
                });
            }
        }).start();
    }
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.arg1==0x1){
                //处理返回的字符串
                getJson(msg.obj.toString());
            }
        }
    };
    public void getJson(String msg){
        String json=null;
        if(msg!=null){
            json=msg.substring(msg.indexOf("[[{"),msg.indexOf("}]]")+3);
        }
        initMessageList(json);
    }

    public void initMessageList(String json){
        try {
            JSONArray array=new JSONArray(json);
            msg_list=new ArrayList[array.length()];

            for (int i=0;i<array.length();i++){
                JSONArray arr=array.getJSONArray(i);
                msg_list[i]=new ArrayList<>();
                for(int j=0;j<arr.length();j++){
                    JSONObject obj=arr.getJSONObject(j);
                    Data data=new Data();
                   data.setThumbnail( obj.getString("thumbnail"));
                    data.setTitle( obj.getString("title"));
                    data.setUrl( obj.getString("url"));
                    msg_list[i].add(data);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listener.upDate(msg_list);
    }
    //关注着数据加载类的父类接口
    public interface CallBackListener{
        void upDate(List<Data>[] msg_list);
    }
}
