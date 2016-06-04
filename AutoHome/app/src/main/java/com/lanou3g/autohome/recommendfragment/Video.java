package com.lanou3g.autohome.recommendfragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lanou3g.autohome.Collect;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.recommendadapter.VideoAdapter;
import com.lanou3g.autohome.recommendbean.VideoBean;
import com.lanou3g.autohome.Detail;
import com.lanou3g.autohome.utils.VolleySingle;

/**
 * Created by dllo on 16/5/9.
 * 视频
 */
public class Video extends BaseFragment implements AdapterView.OnItemClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private ILoadingLayout downLoadingLayout;
    private VideoAdapter videoAdapter;
    private TextView videoAllTv;
    private ImageView videoDrawerLayoutIv;
    private VideoBean videoBean;

    @Override
    public int initLayout() {
        return R.layout.recommend_video;
    }

    @Override
    public void initView() {
        pullToRefreshListView = bindView(R.id.recommend_video_lv);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        videoAllTv = bindView(R.id.recommend_video_all_tv);
        videoDrawerLayoutIv = bindView(R.id.recommend_video_drawerlayout_iv);
        videoAdapter = new VideoAdapter(context);
    }


    @Override
    public void initData() {

        downLoadingLayout = pullToRefreshListView.getLoadingLayoutProxy(true, false);
        downLoadingLayout.setRefreshingLabel("正在刷新");
        downLoadingLayout.setReleaseLabel("释放开始刷新");

        ILoadingLayout upLoadingLayout = pullToRefreshListView.getLoadingLayoutProxy(false, true);
        upLoadingLayout.setLastUpdatedLabel("正在加载");
        upLoadingLayout.setReleaseLabel("释放开始加载");

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                VolleySingle.addRequest("http://app.api.autohome.com.cn/autov5.0.0/news/videolist-pm2-vt0-s20-lastid0.json",
                        VideoBean.class, new Response.Listener<VideoBean>() {
                            @Override
                            public void onResponse(VideoBean response) {
                                videoBean = response;
                                videoAdapter.setVideoBean(response);
                                pullToRefreshListView.setAdapter(videoAdapter);
                                pullToRefreshListView.onRefreshComplete();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                String lastId = videoBean.getResult().getList().get(videoBean.getResult().getList().size() - 1).getLastid();
                String url = "http://app.api.autohome.com.cn/autov5.0.0/news/videolist-pm2-vt0-s20-lastid" + lastId + ".json";
                VolleySingle.addRequest(url, VideoBean.class, new Response.Listener<VideoBean>() {
                    @Override
                    public void onResponse(VideoBean response) {
                        videoBean.getResult().getList().addAll(response.getResult().getList());
                        videoAdapter.setVideoBean(videoBean);
                        pullToRefreshListView.onRefreshComplete();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        });

        VolleySingle.addRequest("http://app.api.autohome.com.cn/autov5.0.0/news/videolist-pm2-vt0-s20-lastid0.json",
                VideoBean.class, new Response.Listener<VideoBean>() {
                    @Override
                    public void onResponse(VideoBean response) {
                        videoBean = response;
                        videoAdapter.setVideoBean(response);
                        pullToRefreshListView.setAdapter(videoAdapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        pullToRefreshListView.setOnItemClickListener(this);
        videoAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //drawerLayout.openDrawer(Gravity.RIGHT);
                Intent intent = new Intent("com.lanou3g.autohome.OPENNDRAWERLAYOUT");
                intent.putExtra("drawerlayout", 1);
                context.sendBroadcast(intent);
            }
        });
        videoDrawerLayoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //drawerLayout.openDrawer(Gravity.RIGHT);
                Intent intent = new Intent("com.lanou3g.autohome.OPENNDRAWERLAYOUT");
                intent.putExtra("drawerlayout", 1);
                context.sendBroadcast(intent);
            }
        });


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, Detail.class);
        intent.setAction(Intent.ACTION_VIEW);
        String url = "http://v.autohome.com.cn/v_4_" + videoBean.getResult().getList().get(position).getId() + ".html";
        intent.putExtra("url", url);
        int ids = videoBean.getResult().getList().get(position).getId();
        String imageUrl = videoBean.getResult().getList().get(position).getSmallimg();
        String title = videoBean.getResult().getList().get(position).getTitle();
        String time = videoBean.getResult().getList().get(position).getTime();
        Collect collect = new Collect((long) ids, url, imageUrl, title, time);
        intent.putExtra("collect", collect);
        startActivity(intent);
    }
}
