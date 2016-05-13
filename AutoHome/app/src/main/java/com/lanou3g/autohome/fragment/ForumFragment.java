package com.lanou3g.autohome.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.fragmentadapter.ForumAdapter;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.forumfragment.Common;
import com.lanou3g.autohome.forumfragment.Hot;
import com.lanou3g.autohome.forumfragment.Selection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/5/9.
 * 论坛
 */
public class ForumFragment extends BaseFragment {

    private ViewPager forumVp;
    private TabLayout forumTab;
    private ForumAdapter forumAdapter;
    private List<Fragment> fragments;
    @Override
    public int initLayout() {
        return R.layout.fragment_forum;
    }

    @Override
    public void initView() {
        forumVp = bindView(R.id.forum_vp);
        forumTab = bindView(R.id.forum_tab);
    }

    @Override
    public void initData() {
        initFragments();
        forumAdapter = new ForumAdapter(getChildFragmentManager());
        forumAdapter.setFragments(fragments);
        forumVp.setAdapter(forumAdapter);
        forumTab.setupWithViewPager(forumVp);
    }
    private void initFragments(){
        fragments = new ArrayList<>();
        fragments.add(new Selection());
        fragments.add(new Hot());
        fragments.add(new Common());
    }
}
