package com.lanou3g.autohome.recommendfragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.recommendadapter.NewsFlashAdapter;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.recommendbean.GsonRequest;
import com.lanou3g.autohome.recommendbean.NewsFlashBean;
import com.lanou3g.autohome.recommenddetail.NewsFlashDetail;
import com.lanou3g.autohome.utils.DividerItemDecoration;
import com.lanou3g.autohome.utils.VolleySingle;

/**
 * Created by dllo on 16/5/9.
 * 快报
 */
public class NewsFlash extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private NewsFlashAdapter adapter;
    private NewsFlashBean newsFlashBean;
    private ILoadingLayout downLoadingLayout;
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
        pullToRefreshListView = bindView(R.id.recommend_news_flash_lv);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new NewsFlashAdapter(context);
        brandDrawerLayoutIv.setOnClickListener(this);
        brandDrawerLayoutTv.setOnClickListener(this);
        levelDrawerLayoutIv.setOnClickListener(this);
        levelDrawerLayoutTv.setOnClickListener(this);
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
                VolleySingle.addRequest("http://app.api.autohome.com.cn/autov5.0.0/news/fastnewslist-pm2-b0-l0-s20-lastid0.json",
                        NewsFlashBean.class, new Response.Listener<NewsFlashBean>() {
                            @Override
                            public void onResponse(NewsFlashBean response) {
                                newsFlashBean = response;
                                adapter.setNewsFlashBean(response);
                                pullToRefreshListView.setAdapter(adapter);
                                pullToRefreshListView.onRefreshComplete();
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
                String lastid = newsFlashBean.getResult().getList().get(newsFlashBean.getResult().getList().size() - 1).getLastid();
                String url = "http://app.api.autohome.com.cn/autov5.0.0/news/fastnewslist-pm2-b0-l0-s20-lastid" + lastid + ".json";
                VolleySingle.addRequest(url, NewsFlashBean.class,
                        new Response.Listener<NewsFlashBean>() {
                            @Override
                            public void onResponse(NewsFlashBean response) {
                                newsFlashBean.getResult().getList().addAll(response.getResult().getList());
                                adapter.setNewsFlashBean(newsFlashBean);
                                pullToRefreshListView.onRefreshComplete();
                                Toast.makeText(context, "加载成功", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
            }
        });

        VolleySingle.addRequest("http://app.api.autohome.com.cn/autov5.0.0/news/fastnewslist-pm2-b0-l0-s20-lastid0.json",
                NewsFlashBean.class, new Response.Listener<NewsFlashBean>() {
                    @Override
                    public void onResponse(NewsFlashBean response) {
                        newsFlashBean = response;
                        adapter.setNewsFlashBean(response);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, NewsFlashDetail.class);
        intent.setAction(Intent.ACTION_VIEW);
        String url = "http://cont.app.autohome.com.cn/autov5.0.0/content/News/fastnewscontent-n"+
                position+"-lastid0-o1.json";
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
