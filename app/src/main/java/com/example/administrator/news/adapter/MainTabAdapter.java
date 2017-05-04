package com.example.administrator.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.news.fragment.FirstPageFragment;

import java.util.List;

/**
 * tablayout的适配器
 * Created by Administrator on 2017/4/19.
 */

public class MainTabAdapter extends FragmentPagerAdapter{
    List<FirstPageFragment> mList_fragment;
    String[] mList_title;


    public MainTabAdapter(FragmentManager fm, List<FirstPageFragment> list_fragment, String[] list_title) {
        super(fm);
        mList_fragment=list_fragment;
        mList_title=list_title;
    }

    @Override
    public Fragment getItem(int position) {
        return mList_fragment.get(position);
    }

    @Override
    public int getCount() {
        return mList_title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList_title[position];
    }
}
