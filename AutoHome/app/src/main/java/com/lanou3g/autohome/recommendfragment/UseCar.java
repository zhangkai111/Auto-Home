package com.lanou3g.autohome.recommendfragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.forumselectiondetail.AllDetail;
import com.lanou3g.autohome.recommendadapter.UseCarAdapter;
import com.lanou3g.autohome.recommendbean.GsonRequest;
import com.lanou3g.autohome.recommendbean.UseCarBean;
import com.lanou3g.autohome.utils.DividerItemDecoration;

/**
 * Created by dllo on 16/5/9.
 */
public class UseCar extends BaseFragment implements RecyclerViewOnClickListener {

    private UseCarAdapter adapter;
    private RecyclerView recyclerView;
    private UseCarBean useCarBean;

    @Override
    public int initLayout() {
        return R.layout.recommend_usecar;
    }

    @Override
    public void initView() {
        recyclerView = bindView(R.id.recommend_usecar_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        adapter = new UseCarAdapter(context);
    }

    @Override
    public void initData() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        GsonRequest<UseCarBean> gsonRequest = new GsonRequest<>(Request.Method.GET,
                "http://app.api.autohome.com.cn/autov5.0.0/news/newslist-pm2-c0-nt82-p1-s20-l0.json",
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("UseCar", "UseCar拉取数据失败");
                    }

                }, new Response.Listener<UseCarBean>() {
            @Override
            public void onResponse(UseCarBean response) {
                Log.d("UseCar", "UseCar拉取数据成功");
                useCarBean = response;
                adapter.setUseCarBean(response);
            }
        },UseCarBean.class);
        requestQueue.add(gsonRequest);
        recyclerView.setAdapter(adapter);
        adapter.setRecyclerViewOnClickListener(this);
    }

    @Override
    public void onClick(int ids) {
        Intent intent = new Intent(new Intent(context, AllDetail.class));
        intent.setAction(Intent.ACTION_VIEW);
        int viewType = adapter.getItemViewType(ids);
        String url = "";
        if (viewType == 10) {
            url = "http://app.api.autohome.com.cn/autov5.0.0/news/newsdetailpicarticle-pm2-nid" +
                     useCarBean.getResult().getNewslist().get(ids).getId()+ ".json";
        }else {
            url = "http://cont.app.autohome.com.cn/autov5.0.0/content/news/newscontent-n" +
                    useCarBean.getResult().getNewslist().get(ids).getId() + "-t0.json";
        }
        intent.putExtra("url",url);
        startActivity(intent);
    }
}
