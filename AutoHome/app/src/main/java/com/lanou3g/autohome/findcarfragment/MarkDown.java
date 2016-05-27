package com.lanou3g.autohome.findcarfragment;

import android.content.Context;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.findcaradapter.MarkDownAdapter;
import com.lanou3g.autohome.findcarbean.MarkDownBean;
import com.lanou3g.autohome.recommendbean.GsonRequest;

/**
 * Created by dllo on 16/5/10.
 * 降价
 */
public class MarkDown extends BaseFragment {

    private MarkDownAdapter markDownAdapter;
    private MarkDownBean markDownBean;
    private ListView listView;

    @Override
    public int initLayout() {
        return R.layout.findcar_markdown;
    }

    @Override
    public void initView() {
        listView = bindView(R.id.findcar_markdown_lv);
        markDownAdapter = new MarkDownAdapter(context);
        markDownAdapter.setMarkDownBean(markDownBean);
        listView.setAdapter(markDownAdapter);
    }

    @Override
    public void initData() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        GsonRequest<MarkDownBean> gsonRequest = new GsonRequest<>(Request.Method.GET,
                "http://223.99.255.20/cars.app.autohome.com.cn/dealer_v5.7.0/dealer/pdspecs-pm2-pi0-c110100-o0-b0-ss0-sp0-p1-s20-l0-minp0-maxp0-lon0.0-lat0.0.json",
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, new Response.Listener<MarkDownBean>() {
            @Override
            public void onResponse(MarkDownBean response) {
                markDownAdapter.setMarkDownBean(response);
            }
        },MarkDownBean.class);
        requestQueue.add(gsonRequest);
    }
}
