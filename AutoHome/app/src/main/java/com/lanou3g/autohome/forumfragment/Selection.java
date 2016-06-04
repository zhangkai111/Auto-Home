package com.lanou3g.autohome.forumfragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.forumfragment.selection.All;
import com.lanou3g.autohome.fragmentadapter.SelectionAdapter;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.forumfragment.selection.Beautiful;
import com.lanou3g.autohome.forumfragment.selection.Electrocar;
import com.lanou3g.autohome.forumfragment.selection.HOF;
import com.lanou3g.autohome.forumfragment.selection.HighEnd;
import com.lanou3g.autohome.forumfragment.selection.Lecturer;
import com.lanou3g.autohome.forumfragment.selection.Selective;
import com.lanou3g.autohome.forumfragment.selection.TenYear;
import com.lanou3g.autohome.forumfragment.selection.Testimonial;
import com.lanou3g.autohome.forumfragment.selection.Wife;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/5/9.
 * 精选
 */
public class Selection extends BaseFragment {

    private SelectionAdapter selectionAdapter;
    private ViewPager selectionVp;
    private TabLayout selectionTab;
    private List<Fragment> fragments;

    @Override
    public int initLayout() {
        return R.layout.forum_selection;
    }

    @Override
    public void initView() {

        selectionVp = bindView(R.id.forum_selection_vp);
        selectionTab = bindView(R.id.forum_selection_tab);
    }

    @Override
    public void initData() {

        initFragments();
        selectionAdapter = new SelectionAdapter(getChildFragmentManager());
        selectionAdapter.setFragments(fragments);
        selectionVp.setAdapter(selectionAdapter);
        selectionTab.setupWithViewPager(selectionVp);
    }
    private void initFragments(){
        fragments = new ArrayList<>();
        fragments.add(new All());
        fragments.add(new Wife());
        fragments.add(new Beautiful());
        fragments.add(new HOF());
        fragments.add(new Lecturer());
        fragments.add(new TenYear());
        fragments.add(new Selective());
        fragments.add(new Testimonial());
        fragments.add(new HighEnd());
        fragments.add(new Electrocar());
    }

}
