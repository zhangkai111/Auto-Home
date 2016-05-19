package com.lanou3g.autohome.fragmentadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dllo on 16/5/9.
 */
public class RecommendAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    String[] titles = {"最新", "快报" , "视频" , "新闻" , "导购" , "行情" , "用车" , "技术" , "文化" , "改装" , "游记" , "原创视频" , "说客"};

    public RecommendAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 :fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
