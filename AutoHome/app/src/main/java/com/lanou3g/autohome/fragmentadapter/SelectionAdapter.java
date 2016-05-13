package com.lanou3g.autohome.fragmentadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dllo on 16/5/10.
 */
public class SelectionAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    String[] titles = {"全部","媳妇当车模","美人"+"“"+"记"+"”","论坛名人堂","论坛讲师","汽车之家十年","精挑细选","现身说法","高端阵地","电动车"};

    public SelectionAdapter(FragmentManager fm) {
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
