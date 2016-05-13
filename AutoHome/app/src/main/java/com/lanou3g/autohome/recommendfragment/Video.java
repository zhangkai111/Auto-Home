package com.lanou3g.autohome.recommendfragment;

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
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.recommendbean.GsonRequest;
import com.lanou3g.autohome.recommendbean.VideoBean;
import com.lanou3g.autohome.recommendadapter.VideoAdapter;
import com.lanou3g.autohome.utils.DividerItemDecoration;

/**
 * Created by dllo on 16/5/9.
 * 视频
 */
public class Video extends BaseFragment {

    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;

    @Override
    public int initLayout() {
        return R.layout.recommend_video;
    }

    @Override
    public void initView() {
        recyclerView = bindView(R.id.recommend_video_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        videoAdapter = new VideoAdapter(context);
    }

    @Override
    public void initData() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        GsonRequest<VideoBean> gsonRequest = new GsonRequest<>(Request.Method.GET, "http://app.api.autohome.com.cn/autov5.0.0/news/videolist-pm2-vt0-s20-lastid0.json",
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Video", "Video获取数据失败");
                    }
                }, new Response.Listener<VideoBean>() {
            @Override
            public void onResponse(VideoBean response) {
                Log.d("Video", "Video获取数据成功");
                videoAdapter.setVideoBean(response);
            }
        },VideoBean.class);
        requestQueue.add(gsonRequest);
        recyclerView.setAdapter(videoAdapter);

    }
}
