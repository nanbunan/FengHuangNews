package com.example.administrator.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.news.MsgDetailActivity;
import com.example.administrator.news.R;
import com.example.administrator.news.adapter.FirstPageAdapter;
import com.example.administrator.news.bean.BannerBean;
import com.example.administrator.news.bean.Data;
import com.example.administrator.news.utils.JsonUtils;
import com.example.administrator.news.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class FirstPageFragment extends Fragment implements JsonUtils.CallBackListener,SwipeRefreshLayout.OnRefreshListener{
    ProgressDialogUtils utils;
    private List<Data> item_list;//记录当前真正显示的数据
    private int mPosition=0;//记录当前存在的页面

    private RecyclerView mRecycleVView;
    private JsonUtils jsonUtils;
    private BannerBean mBannerBean;
    private List<Data>[] msg_list;
    private FirstPageAdapter adapter;
    private int now_number=7;//记录当前存放的数据条数
    private SwipeRefreshLayout swipe;
    private int lastVisibleItem;//记录最后一个下标
    private final int LOADNUM=4;//每次刷新加载的条数
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_layout,(ViewGroup) getActivity().findViewById(R.id.view_pager),false);

       getData();
        swipe= (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        mRecycleVView= (RecyclerView) v.findViewById(R.id.recycle_view);
        mRecycleVView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipe.setColorSchemeResources(android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipe.setOnRefreshListener(this);
       adapter=new FirstPageAdapter(getActivity(),item_list,mBannerBean);
        mRecycleVView.setAdapter(adapter);
        initListener();
        return v;
    }
//上拉实现事件监听
    private void initListener() {
        mRecycleVView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            //状态发生变化时触发
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1==adapter.getItemCount()){
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            now_number+=LOADNUM;
                            initData();

                        }
                    },1500);
                }
            }
           //滚动时监听
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm=(LinearLayoutManager)recyclerView.getLayoutManager();
                lastVisibleItem=lm.findLastVisibleItemPosition();
            }
        });
        adapter.setMyItemClickListener(new FirstPageAdapter.MyItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                String url=item_list.get(position-1).getUrl();

                Intent intent=new Intent(getActivity(), MsgDetailActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("position",mPosition);
                startActivity(intent);

            }
        });
        adapter.setMyBannerClickListener(new FirstPageAdapter.MyBannerClickListener(){
            @Override
            public void onClick(View itemView, int position) {
                String url=mBannerBean.getToUrl()[position-1];
                Intent intent=new Intent(getActivity(),MsgDetailActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("position",mPosition);
                startActivity(intent);
            }
        });
    }

    private void getData(){
    item_list=new ArrayList<>();
    mBannerBean=new BannerBean();
    utils=new ProgressDialogUtils();
    //注册本类去监听
    jsonUtils=new JsonUtils(this);
    //启动加载数据方法
    jsonUtils.getResult();
    utils.showProgressDialog(getActivity(),"loading...");
}
    @Override
    public void upDate(List<Data>[] msg_list) {
        utils.closeProgressDialog();

        this.msg_list=msg_list;
        initData();//更新数据方法


    }
    //分配数据填充布局
    public void initData(){
        if(msg_list!=null) {
            //mBannerBean=new BannerBean();
            String[] img=new String[3];
            String[] title=new String[3];
            String[] toUrl=new String[3];
            item_list.clear();//清空缓存
            List<Data> data = msg_list[mPosition];
            for (int i=0;i<3;i++){
                img[i]=data.get(i).getThumbnail();
                //如果一个新闻中含有多张图片需要截取第一张图片
                if(data.get(i).getThumbnail().contains("[")){
                    String str=data.get(i).getThumbnail();
                    String aa=str.substring(str.indexOf("[")+2,str.indexOf("jpg")+3);
                    aa=aa.replace("\\","");
                    img[i]=aa;}
                title[i]=data.get(i).getTitle();
                toUrl[i]=data.get(i).getUrl();
            }
            mBannerBean.setImage_title(title);
            mBannerBean.setImage_url(img);
            mBannerBean.setToUrl(toUrl);

            for (int i = 3; i < now_number; i++) {
                item_list.add(data.get(i));
                if(data.get(i).getThumbnail().contains("[")){
                   String str=data.get(i).getThumbnail();
                   String aa=str.substring(str.indexOf("[")+2,str.indexOf("jpg")+3);
                    aa=aa.replace("\\","");
                    data.get(i).setThumbnail(aa);
                }

            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        now_number+=LOADNUM;//刷新操作执行后多显示的条目
        initData();
        swipe.setRefreshing(false);
    }
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
//滑动时更新数据
    public void setPosition(int position) {
        mPosition=position;
        initData();
    }
}
