package com.lanou3g.autohome.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.fragmentadapter.FindCarAdapter;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.findcarfragment.Brand;
import com.lanou3g.autohome.findcarfragment.Filter;
import com.lanou3g.autohome.findcarfragment.MarkDown;
import com.lanou3g.autohome.findcarfragment.UsedCar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/5/9.
 * 找车
 */
public class FindCarFragment extends BaseFragment {

    private ViewPager findcarVp;
    private TabLayout findcarTab;
    private List<Fragment> fragments;
    private FindCarAdapter findCarAdapter;
    @Override
    public int initLayout() {
        return R.layout.fragment_findcar;
    }

    @Override
    public void initView() {
        findcarVp = bindView(R.id.findcar_vp);
        findcarTab = bindView(R.id.findcar_tab);
    }

    @Override
    public void initData() {

        initFragment();
        findCarAdapter = new FindCarAdapter(getChildFragmentManager());
        findCarAdapter.setFragments(fragments);
        findcarVp.setAdapter(findCarAdapter);
        findcarTab.setupWithViewPager(findcarVp);
    }
    private void initFragment(){
        fragments = new ArrayList<>();
        fragments.add(new Brand());
        fragments.add(new Filter());
        fragments.add(new MarkDown());
        fragments.add(new UsedCar());

    }
}
