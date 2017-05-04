package com.example.administrator.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.administrator.news.R;
import com.example.administrator.news.bean.BannerBean;
import com.example.administrator.news.bean.Data;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 */

public class FirstPageAdapter extends RecyclerView.Adapter {
    private final int TYPE_HEAD=0;//recycleview的首个位置 显示轮播图片
    private final int TYPE_NORMAL=1;//正常item布局
    private final int TYPE_FOOTER=2;
    private BannerBean bean;
    private Context context;
    private List<Data> item_data;
public FirstPageAdapter(Context context, List<Data> item_data, BannerBean bean){
    this.item_data=item_data;
    this.context=context;
    this.bean=bean;
}
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder=null;
        if(viewType==TYPE_HEAD){
            //此处创建顶部banner的viewholder
            holder=new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.itemheader_banner,parent,false));
        }else if(viewType == TYPE_FOOTER){
            holder=new FootViewHolder(LayoutInflater.from(context).inflate(R.layout.itemfooter,parent,false));
        }
        else {
            //holder=new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_firstfragment,parent,false));
            holder=new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_firstfragment,parent,false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof BannerViewHolder){
            BannerViewHolder bannerViewHolder=(BannerViewHolder)holder;
            bannerViewHolder.banner.setImages(bean.getImage_url());
            bannerViewHolder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            bannerViewHolder.banner.setBannerTitle(bean.getImage_title());
            if(bannerlistener!=null) {
                bannerViewHolder.banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
                    @Override
                    public void OnBannerClick(View view, int position) {
                        bannerlistener.onClick(view,position);


                    }
                });
            }
        }else if(holder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder= (ItemViewHolder) holder;
            itemViewHolder.textView.setText(item_data.get(position-1).getTitle());
            itemViewHolder.simpleDraweeView.setImageURI(item_data.get(position-1).getThumbnail());

            if(listener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(view,position);
                    }
                });
            }

        }else if(holder instanceof FootViewHolder){
            if(item_data.size()>0){
                ((FootViewHolder)holder).progress_lin.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return item_data.size()+1+1;
    }
//告诉创建什么类型的viewholder
    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEAD;
        }else if(position+1==getItemCount()){
            return TYPE_FOOTER;
        }
        else {
            return TYPE_NORMAL;
        }
    }
//正常的item布局管理
    class ItemViewHolder extends RecyclerView.ViewHolder{
    SimpleDraweeView simpleDraweeView;
    TextView textView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView= (SimpleDraweeView) itemView.findViewById(R.id.simpleView);
            textView= (TextView) itemView.findViewById(R.id.news_text);
        }
    }
//首位的banner
    class BannerViewHolder extends RecyclerView.ViewHolder{
    Banner banner;
    public BannerViewHolder(View itemView) {
        super(itemView);
        banner= (Banner) itemView.findViewById(R.id.banner);
    }
}

    class FootViewHolder extends RecyclerView.ViewHolder{
        LinearLayout progress_lin;
        public FootViewHolder(View itemView) {
            super(itemView);
            progress_lin= (LinearLayout) itemView.findViewById(R.id.progress_lin);
        }
    }
    //recycleview banner的单击事件回调接口
    public interface  MyBannerClickListener {
        void onClick(View itemView,int position);
    };
    //本类中保存一个借口引用
    private MyBannerClickListener bannerlistener;

    public void setMyBannerClickListener(MyBannerClickListener bannerlistener){
        this.bannerlistener=bannerlistener;
    }
    //recycleview item的单击事件回调接口
    public interface  MyItemClickListener {
        void onClick(View itemView,int position);
    };
    //本类中保存一个借口引用
    private MyItemClickListener listener;

    public void setMyItemClickListener(MyItemClickListener listener){
        this.listener=listener;
    }
}
