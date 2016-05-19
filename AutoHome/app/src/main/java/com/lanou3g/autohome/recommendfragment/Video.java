package com.lanou3g.autohome.recommendfragment;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.recommendadapter.DrawerLayoutAdapter;
import com.lanou3g.autohome.recommendbean.GsonRequest;
import com.lanou3g.autohome.recommendbean.VideoBean;
import com.lanou3g.autohome.recommendadapter.VideoAdapter;
import com.lanou3g.autohome.recommenddetail.NewsDetail;
import com.lanou3g.autohome.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/5/9.
 * 视频
 */
public class Video extends BaseFragment implements RecyclerViewOnClickListener {

    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private TextView videoAllTv;
    private ImageView videoDrawerLayoutIv;
    @Override
    public int initLayout() {
        return R.layout.recommend_video;
    }

    @Override
    public void initView() {
        videoAllTv = bindView(R.id.recommend_video_all_tv);
        videoDrawerLayoutIv = bindView(R.id.recommend_video_drawerlayout_iv);
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
        videoAdapter.setRecyclerViewOnClickListener(this);
        videoAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //drawerLayout.openDrawer(Gravity.RIGHT);
                Intent intent = new Intent("com.lanou3g.autohome.OPENNDRAWERLAYOUT");
                intent.putExtra("drawerlayout",1);
                context.sendBroadcast(intent);
            }
        });
        videoDrawerLayoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //drawerLayout.openDrawer(Gravity.RIGHT);
                Intent intent = new Intent("com.lanou3g.autohome.OPENNDRAWERLAYOUT");
                intent.putExtra("drawerlayout",1);
                context.sendBroadcast(intent);
            }
        });


    }

    @Override
    public void onClick(int ids) {
        Intent intent = new Intent(context, NewsDetail.class);
        intent.setAction(Intent.ACTION_VIEW);
        String url = "http://v.autohome.com.cn/v_4_"+
                ids +".html";
        intent.putExtra("url",url);
        startActivity(intent);
    }
}
