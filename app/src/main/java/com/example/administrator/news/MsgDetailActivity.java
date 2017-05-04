package com.example.administrator.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MsgDetailActivity extends AppCompatActivity {
    WebView webView;
    private int position;//当前点击的页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_detail);
        webView= (WebView) findViewById(R.id.webView_msgDetail);
        webView.setWebViewClient(new webViewClient());

        //打开页面面时自适应屏幕
        WebSettings webSettings=webView.getSettings();
        webSettings.setUseWideViewPort(true);//可任意比例缩放
        webSettings.setJavaScriptEnabled(true);

        ;
        position=getIntent().getIntExtra("position",0);
        String url=getUrlByPosition(position);
        webView.loadUrl(url);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_msg);
        //设置返回键可用
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置返回按钮的事件

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String getUrlByPosition(int position) {
     String oldUrl=getIntent().getStringExtra("url");
        StringBuilder url=new StringBuilder();
        //获取网页形式路径中的数字

        String num=getMainNum(oldUrl);
      //  Log.d("jiance",num);
        switch (position){
            case 0://要闻http://inews.ifeng.com/50977577/news.shtml
                url=url.append("http://inews.ifeng.com/").append(num).append("/news.shtml");

                break;

            case 1://财经
                url=url.append("http://ifinance.ifeng.com/").append(num).append("/news.shtml");

                break;

            case 2://体育
                url=url.append("http://isports.ifeng.com/").append(num).append("/news.shtml");

                break;
            case 3://军事
                url=url.append("http://imil.ifeng.com/").append(num).append("/news.shtml");

                break;
            case 4://科技
                url=url.append("http://itech.ifeng.com/").append(num).append("/news.shtml");

                break;
            case 5://历史
                url=url.append("http://ihistory.ifeng.com/").append(num).append("/news.shtml");

                break;
            case 6://娱乐http://ifenghuanghao.ifeng.com/13263804/news.shtml
                url=url.append("http://ifenghuanghao.ifeng.com/").append(num).append("/news.shtml");

                break;

        }

        return  url.toString();
    }

    private String getMainNum(String oldUrl) {
        if(position<6){
        oldUrl=oldUrl.substring(oldUrl.lastIndexOf("/")+1,oldUrl.lastIndexOf("."));
        //Log.d("jiance",oldUrl);
        if(oldUrl.contains("_")){
            oldUrl=oldUrl.substring(0,oldUrl.lastIndexOf("_"));
        }}else if(position==6){
            oldUrl=oldUrl.substring(oldUrl.indexOf("com")+4,oldUrl.lastIndexOf("/"));
        }

        return oldUrl;
    }
    class webViewClient extends WebViewClient {

        //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。

        @Override

        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

            //如果不需要其他对点击链接事件的处理返回true，否则返回false

            return true;

        }


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
