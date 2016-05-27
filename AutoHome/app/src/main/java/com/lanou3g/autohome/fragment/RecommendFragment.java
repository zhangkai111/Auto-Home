package com.lanou3g.autohome.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.fragmentadapter.RecommendAdapter;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.recommendfragment.Culture;
import com.lanou3g.autohome.recommendfragment.Lobbyist;
import com.lanou3g.autohome.recommendfragment.Newest;
import com.lanou3g.autohome.recommendfragment.News;
import com.lanou3g.autohome.recommendfragment.NewsFlash;
import com.lanou3g.autohome.recommendfragment.OriginalVideo;
import com.lanou3g.autohome.recommendfragment.Refit;
import com.lanou3g.autohome.recommendfragment.Technology;
import com.lanou3g.autohome.recommendfragment.Travels;
import com.lanou3g.autohome.recommendfragment.UseCar;
import com.lanou3g.autohome.recommendfragment.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/5/9.
 * 推荐
 */
public class RecommendFragment extends BaseFragment {
    private ViewPager recommendVp;
    private TabLayout recommendTab;
    private RecommendAdapter recommendAdapter;
    private List<Fragment> fragments;

    @Override
    public int initLayout() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initView() {
        recommendVp = bindView(R.id.recommend_vp);
        recommendTab = bindView(R.id.recommend_tab);
    }

    @Override
    public void initData() {

        initFragments();
        recommendAdapter = new RecommendAdapter(getChildFragmentManager());
        recommendAdapter.setFragments(fragments);
        recommendVp.setAdapter(recommendAdapter);
        recommendTab.setupWithViewPager(recommendVp);
    }
    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new Newest());
        fragments.add(new NewsFlash());
        fragments.add(new Video());
        fragments.add(new News());
        fragments.add(new UseCar());
        fragments.add(new Technology());
        fragments.add(new Culture());
        fragments.add(new Refit());
        fragments.add(new Travels());
        fragments.add(new OriginalVideo());
        fragments.add(new Lobbyist());
    }
}
