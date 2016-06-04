package com.lanou3g.autohome.recommendfragment;

import android.content.Intent;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lanou3g.autohome.Collect;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.recommendadapter.NewsAdapter;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.recommendbean.NewsBean;
import com.lanou3g.autohome.Detail;
import com.lanou3g.autohome.utils.VolleySingle;

/**
 * Created by dllo on 16/5/9.
 */
public class News extends BaseFragment implements AdapterView.OnItemClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private NewsAdapter adapter;
    private ILoadingLayout downLoadingLayout;
    private NewsBean newsBean;

    @Override
    public int initLayout() {
        return R.layout.recommend_news;
    }

    @Override
    public void initView() {

        pullToRefreshListView = bindView(R.id.recommend_news_lv);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new NewsAdapter(context);

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
                VolleySingle.addRequest("http://app.api.autohome.com.cn/autov5.0.0/news/newslist-pm1-c0-nt1-p1-s30-l0.json",
                        NewsBean.class, new Response.Listener<NewsBean>() {
                            @Override
                            public void onResponse(NewsBean response) {
                                newsBean = response;
                                adapter.setNewsBean(response);
                                pullToRefreshListView.setAdapter(adapter);
                                pullToRefreshListView.onRefreshComplete();
                                //设置下拉时显示刷新的时间
                                String str = DateUtils.formatDateTime(getContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                                downLoadingLayout.setLastUpdatedLabel("最后更新时间" + str);
                                Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                String url = "http://app.api.autohome.com.cn/autov5.0.0/news/newslist-pm2-c0-nt1-p2-s20-l" + newsBean.getResult().getNewslist().get(newsBean.getResult().getNewslist().size() - 1).getId() + ".json";
                VolleySingle.addRequest(url, NewsBean.class, new Response.Listener<NewsBean>() {
                    @Override
                    public void onResponse(NewsBean response) {

                        newsBean.getResult().getNewslist().addAll(newsBean.getResult().getNewslist());
                        pullToRefreshListView.onRefreshComplete();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        });


        VolleySingle.addRequest("http://app.api.autohome.com.cn/autov5.0.0/news/newslist-pm1-c0-nt1-p1-s30-l0.json",
                NewsBean.class, new Response.Listener<NewsBean>() {
                    @Override
                    public void onResponse(NewsBean response) {
                        newsBean = response;
                        adapter.setNewsBean(response);
                        pullToRefreshListView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        pullToRefreshListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, Detail.class);
        intent.setAction(Intent.ACTION_VIEW);
        String url = "http://cont.app.autohome.com.cn/autov4.2.5/content/News/newscontent-a2-pm1-v4.2.5-n"+
                newsBean.getResult().getNewslist().get(position -1).getId() +"-lz0-sp0-nt0-sa1-p0-c1-fs0-cw320.html";
        intent.putExtra("url",url);

        int ids = newsBean.getResult().getNewslist().get(position -1).getId();
        String imageUrl = newsBean.getResult().getNewslist().get(position -1).getSmallpic();
        String title = newsBean.getResult().getNewslist().get(position -1).getTitle();
        String time = newsBean.getResult().getNewslist().get(position - 1).getTime();
        Collect collect = new Collect((long)ids,url,imageUrl,title,time);
        intent.putExtra("collect",collect);
        startActivity(intent);
    }
}
