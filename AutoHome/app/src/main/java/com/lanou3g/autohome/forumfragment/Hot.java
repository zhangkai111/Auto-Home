package com.lanou3g.autohome.forumfragment;

import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.forumadapter.HotAdapter;
import com.lanou3g.autohome.forumbean.HotBean;
import com.lanou3g.autohome.recommendbean.GsonRequest;

/**
 * Created by dllo on 16/5/9.
 */
public class Hot extends BaseFragment {

    private HotBean hotBean;
    private HotAdapter hotAdapter;
    private ListView listView;

    @Override
    public int initLayout() {
        return R.layout.forum_hot;
    }

    @Override
    public void initView() {
        listView = bindView(R.id.form_hot_lv);
        hotAdapter = new HotAdapter(context);
        hotAdapter.setHotBean(hotBean);
        listView.setAdapter(hotAdapter);
    }

    @Override
    public void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        GsonRequest<HotBean> gsonRequest = new GsonRequest<>(Request.Method.GET,
                "http://club.app.autohome.com.cn/club_v5.6.0/club/shotfoumlist-pm1-p1-s50.json",
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, new Response.Listener<HotBean>() {
            @Override
            public void onResponse(HotBean response) {
                hotAdapter.setHotBean(response);
            }
        }, HotBean.class);
        requestQueue.add(gsonRequest);
        listView.setAdapter(hotAdapter);
    }
}
