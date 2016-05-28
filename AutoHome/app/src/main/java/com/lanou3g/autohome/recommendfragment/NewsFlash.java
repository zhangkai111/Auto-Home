package com.lanou3g.autohome.recommendfragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.recommendadapter.NewsFlashAdapter;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.recommendbean.GsonRequest;
import com.lanou3g.autohome.recommendbean.NewsFlashBean;
import com.lanou3g.autohome.recommenddetail.NewsFlashDetail;
import com.lanou3g.autohome.utils.DividerItemDecoration;

/**
 * Created by dllo on 16/5/9.
 * 快报
 */
public class NewsFlash extends BaseFragment implements RecyclerViewOnClickListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private NewsFlashAdapter adapter;
    private TextView brandDrawerLayoutTv,levelDrawerLayoutTv;
    private ImageView brandDrawerLayoutIv,levelDrawerLayoutIv;



    @Override
    public int initLayout() {
        return R.layout.recommend_newsflash;
    }

    @Override
    public void initView() {
        brandDrawerLayoutTv = bindView(R.id.news_flash_brand_tv);
        brandDrawerLayoutIv = bindView(R.id.news_flash_brand_iv);
        levelDrawerLayoutTv = bindView(R.id.news_flash_level_tv);
        levelDrawerLayoutIv = bindView(R.id.news_flash_level_iv);
        recyclerView = bindView(R.id.recommend_news_flash_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        adapter = new NewsFlashAdapter(context);
        brandDrawerLayoutIv.setOnClickListener(this);
        brandDrawerLayoutTv.setOnClickListener(this);
        levelDrawerLayoutIv.setOnClickListener(this);
        levelDrawerLayoutTv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        GsonRequest<NewsFlashBean> gsonRequest = new GsonRequest<>(Request.Method.GET, "http://app.api.autohome.com.cn/autov5.0.0/news/fastnewslist-pm2-b0-l0-s20-lastid0.json",
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }, new Response.Listener<NewsFlashBean>() {
            @Override
            public void onResponse(NewsFlashBean response) {
                adapter.setNewsFlashBean(response);
            }
        }, NewsFlashBean.class);
        requestQueue.add(gsonRequest);
        recyclerView.setAdapter(adapter);
        adapter.setRecyclerViewOnClickListener(this);
    }

    @Override
    public void onClick(int ids) {
        Intent intent = new Intent(context, NewsFlashDetail.class);
        intent.setAction(Intent.ACTION_VIEW);
        String url = "http://cont.app.autohome.com.cn/autov5.0.0/content/News/fastnewscontent-n"+
                ids+"-lastid0-o1.json";
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.news_flash_brand_iv:
                Intent brandIvIntent = new Intent("com.lanou3g.autohome.OPENNDRAWERLAYOUT");
                brandIvIntent.putExtra("drawerlayout",2);
                context.sendBroadcast(brandIvIntent);
                break;
            case R.id.news_flash_brand_tv:
                Intent brandTvIntent = new Intent("com.lanou3g.autohome.OPENNDRAWERLAYOUT");
                brandTvIntent.putExtra("drawerlayout",2);
                context.sendBroadcast(brandTvIntent);
                break;
            case R.id.news_flash_level_iv:
                Intent levelIvIntent = new Intent("com.lanou3g.autohome.OPENNDRAWERLAYOUT");
                levelIvIntent.putExtra("drawerlayout",3);
                context.sendBroadcast(levelIvIntent);
                break;
            case R.id.news_flash_level_tv:
                Intent levelTvIntent = new Intent("com.lanou3g.autohome.OPENNDRAWERLAYOUT");
                levelTvIntent.putExtra("drawerlayout",3);
                context.sendBroadcast(levelTvIntent);
                break;
        }
    }
}
