package com.lanou3g.autohome.recommendfragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.recommendadapter.NewsAdapter;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.recommendbean.GsonRequest;
import com.lanou3g.autohome.recommendbean.NewsBean;
import com.lanou3g.autohome.recommenddetail.NewsDetail;
import com.lanou3g.autohome.swipe.SwipeRefreshLoadingLayout;
import com.lanou3g.autohome.utils.DividerItemDecoration;

/**
 * Created by dllo on 16/5/9.
 */
public class News extends BaseFragment implements RecyclerViewOnClickListener, SwipeRefreshLoadingLayout.OnLoadListener, SwipeRefreshLoadingLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private SwipeRefreshLoadingLayout swipeRefreshLoadingLayout;
    private NewsBean newsBean;

    @Override
    public int initLayout() {
        return R.layout.recommend_news;
    }

    @Override
    public void initView() {

        recyclerView = bindView(R.id.recommend_news_rv);
        swipeRefreshLoadingLayout = bindView(R.id.swipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        adapter = new NewsAdapter(context);
        swipeRefreshLoadingLayout.setOnLoadListener(this);
        swipeRefreshLoadingLayout.setOnRefreshListener(this);

    }

    @Override
    public void initData() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        GsonRequest<NewsBean> gsonRequest = new GsonRequest<>(Request.Method.GET, "http://app.api.autohome.com.cn/autov5.0.0/news/newslist-pm1-c0-nt1-p1-s30-l0.json",
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("News", "新闻页面数据拉取失败");
                    }
                }, new Response.Listener<NewsBean>() {
            @Override
            public void onResponse(NewsBean response) {
                Log.d("News", "新闻页面数据拉取数据成功");
                adapter.setNewsBean(response);
            }
        }, NewsBean.class);
        requestQueue.add(gsonRequest);
        recyclerView.setAdapter(adapter);
        adapter.setRecyclerViewOnClickListener(this);
    }

    @Override
    public void onClick(int ids) {
        Intent intent = new Intent(context, NewsDetail.class);
        intent.setAction(Intent.ACTION_VIEW);
        String url = "http://cont.app.autohome.com.cn/autov4.2.5/content/News/newscontent-a2-pm1-v4.2.5-n"+
                ids+"-lz0-sp0-nt0-sa1-p0-c1-fs0-cw320.html";
        intent.putExtra("url",url);
        startActivity(intent);
    }

    @Override
    public void onLoad() {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        GsonRequest<NewsBean> gsonRequest = new GsonRequest<>(Request.Method.GET, "http://app.api.autohome.com.cn/autov5.0.0/news/newslist-pm1-c0-nt1-p1-s30-l0.json",
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, new Response.Listener<NewsBean>() {
            @Override
            public void onResponse(NewsBean response) {
                adapter.setNewsBean(response);
            }
        }, NewsBean.class);
        requestQueue.add(gsonRequest);
        recyclerView.setAdapter(adapter);
        swipeRefreshLoadingLayout.setRefreshing(false);
    }
}
