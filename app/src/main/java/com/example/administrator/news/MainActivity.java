package com.example.administrator.news;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.news.adapter.MainTabAdapter;
import com.example.administrator.news.fragment.FirstPageFragment;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;//顶部选项布局
    private ViewPager mViewPager;

    private FirstPageFragment mFragment;

    private String[] mList_title;//存放标题
    private List<FirstPageFragment> mFirstFragments;
    private MainTabAdapter mAdapter_title;//标题的适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);

        initData();
        initView();
        initListener();

    }
//单机事件的监听
    private void initListener() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                for(int i=0;i<mFirstFragments.size();i++){
                    mFirstFragments.get(position).setPosition(position);
                }
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initData() {
        mList_title=getResources().getStringArray(R.array.tab_title);
        mFirstFragments=new ArrayList<>();
        //通过标题个数创建Fragment
        for (int i=0;i<mList_title.length;i++){
            FirstPageFragment first=new FirstPageFragment();
            mFirstFragments.add(first);
        }
    }

    //初始化布局
    private void initView() {
        mTabLayout= (TabLayout) findViewById(R.id.tab_title);
        mViewPager= (ViewPager) findViewById(R.id.view_pager);
        mAdapter_title=new MainTabAdapter(getSupportFragmentManager(),mFirstFragments,mList_title);

        mViewPager.setAdapter(mAdapter_title);
        //TabLayout绑定ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
