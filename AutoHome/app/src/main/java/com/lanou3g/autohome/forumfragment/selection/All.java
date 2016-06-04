package com.lanou3g.autohome.forumfragment.selection;

import android.content.Intent;
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
import com.lanou3g.autohome.forumadapter.AllAdapter;
import com.lanou3g.autohome.forumbean.AllBean;
import com.lanou3g.autohome.forumselectiondetail.AllDetail;
import com.lanou3g.autohome.recommendbean.GsonRequest;
import com.lanou3g.autohome.recommendfragment.RecyclerViewOnClickListener;
import com.lanou3g.autohome.utils.DividerItemDecoration;

/**
 * Created by dllo on 16/5/9.
 */
public class All extends BaseFragment implements RecyclerViewOnClickListener {
    private RecyclerView recyclerView;
    private AllAdapter adapter;
    private AllBean allBean;
    @Override
    public int initLayout() {
        return R.layout.selection_all;
    }

    @Override
    public void initView() {
        recyclerView = bindView(R.id.selection_all_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        adapter = new AllAdapter(context);

    }

    @Override
    public void initData() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        GsonRequest<AllBean> gsonRequest = new GsonRequest<>(Request.Method.GET,
                "http://app.api.autohome.com.cn/autov4.8.8/club/jingxuantopic-pm1-c0-p1-s30.json",
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("All", "All数据拉取失败");
                    }
                }, new Response.Listener<AllBean>() {
            @Override
            public void onResponse(AllBean response) {
                Log.d("All", "All数据拉取成功");
                adapter.setAllBean(response);
            }
        },AllBean.class);
        requestQueue.add(gsonRequest);
        recyclerView.setAdapter(adapter);
        adapter.setRecyclerViewOnClickListener(this);
    }

    @Override
    public void onClick(int ids) {
        Intent intent = new Intent(context, AllDetail.class);
        intent.setAction(Intent.ACTION_VIEW);
        String url = "http://forum.app.autohome.com.cn/autov5.0.0/forum/club/topiccontent-a2-pm2-v5.0.0-t"+
                ids + "-o0-p1-s20-c1-nt0-fs0-sp0-al0-cw320.json";
        intent.putExtra("url",url);
        startActivity(intent);

    }
}
