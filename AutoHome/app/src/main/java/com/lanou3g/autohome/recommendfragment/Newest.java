package com.lanou3g.autohome.recommendfragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lanou3g.autohome.Collect;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.recommendadapter.NewestAdapter;
import com.lanou3g.autohome.recommendbean.GsonRequest;
import com.lanou3g.autohome.recommendbean.NewestBean;
import com.lanou3g.autohome.Detail;
import com.lanou3g.autohome.utils.ImagePaperAdapter;
import com.lanou3g.autohome.utils.VolleySingle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/9.
 */
public class Newest extends BaseFragment implements AdapterView.OnItemClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private View headImageView;
    private ILoadingLayout downLoadingLayout;
    private NewestAdapter adapter;
    private LayoutInflater inflater;
    private NewestBean newestBean;
    private ViewPager mviewPager;
    private Collect collect;
    /**
     * 用于小圆点图片
     */
    private List<ImageView> dotViewList;
    /**
     * 用于存放轮播效果图片
     */
    private List<ImageView> list;

    LinearLayout dotLayout;

    private int currentItem = 0;//当前页面

    boolean isAutoPlay = true;//是否自动轮播

    private ScheduledExecutorService scheduledExecutorService;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (msg.what == 100) {
                mviewPager.setCurrentItem(currentItem);
            }
        }

    };


    @Override
    public int initLayout() {
        return R.layout.recommend_newest;
    }

    @Override
    public void initView() {
        pullToRefreshListView = bindView(R.id.newest_lv);
        //Mode设置为Mode.BOTH后，下拉和上拉都会执行onRefresh()中的方法了。
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        adapter = new NewestAdapter(context);
        pullToRefreshListView.setAdapter(adapter);

        //加载头布局最新页面头布局(轮播图)
        listView = pullToRefreshListView.getRefreshableView();
        headImageView = getLayoutInflater(null).inflate(R.layout.newest_recycleview_head, null);
        listView.addHeaderView(headImageView);

        pullToRefreshListView.setOnItemClickListener(this);

        inflater = LayoutInflater.from(context);
        mviewPager = bindView(R.id.newest_myviewPager);
        dotLayout = bindView(R.id.newest_dotLayout);
        dotLayout.removeAllViews();


        if (isAutoPlay) {
            startPlay();
        }
    }

    @Override
    public void initData() {

        /*
        //下拉的时候显示下拉刷新,上拉的时候显示上拉加载
        mExpandList.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.pull_to_load));
        mExpandList.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.loading));
        mExpandList.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.release_to_load));
         */
        downLoadingLayout = pullToRefreshListView.getLoadingLayoutProxy(true, false);
        downLoadingLayout.setRefreshingLabel("正在刷新");
        downLoadingLayout.setReleaseLabel("释放开始刷新");

        ILoadingLayout upLoadingLayout = pullToRefreshListView.getLoadingLayoutProxy(false, true);
        upLoadingLayout.setLastUpdatedLabel("正在加载");
        upLoadingLayout.setReleaseLabel("释放开始加载");

        //刷新加载
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                VolleySingle.addRequest("http://app.api.autohome.com.cn/autov4.2.5/news/newslist-a2-pm1-v4.2.5-c0-nt0-p1-s30-l0.html", NewestBean.class,
                        new Response.Listener<NewestBean>() {
                            @Override
                            public void onResponse(NewestBean response) {
                                newestBean = response;
                                adapter.setNewestBean(response);
                                pullToRefreshListView.onRefreshComplete();
                                //设置下拉时显示刷新的时间
                                String str = DateUtils.formatDateTime(getContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                                downLoadingLayout.setLastUpdatedLabel("最后更新时间" + str);
                                Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "刷新失败", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            //上拉加载
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                VolleySingle.addRequest("http://app.api.autohome.com.cn/autov4.2.5/news/newslist-a2-pm1-v4.2.5-c0-nt0-p" + newestBean.getResult().getNewslist().get(newestBean.getResult().getNewslist().size() - 1).getId() + "-s30-l" + newestBean.getResult().getNewslist().get(newestBean.getResult().getNewslist().size() - 1).getLasttime() + ".html",
                        NewestBean.class, new Response.Listener<NewestBean>() {
                            @Override
                            public void onResponse(NewestBean response) {
                                //上拉加载的时候吧加载出的数据加到已有的数据上
                                newestBean.getResult().getNewslist().addAll(response.getResult().getNewslist());
                                adapter.setNewestBean(newestBean);
                                //onRefreshComplete记得是在setadpter后面执行不然无效
                                //如果已经在获取数据了就onRefreshComplete()，就是将下拉收起
                                pullToRefreshListView.onRefreshComplete();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        GsonRequest<NewestBean> gsonRequest = new GsonRequest<>(Request.Method.GET,
                "http://app.api.autohome.com.cn/autov4.2.5/news/newslist-a2-pm1-v4.2.5-c0-nt0-p1-s30-l0.html",
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, new Response.Listener<NewestBean>() {
            @Override
            public void onResponse(NewestBean response) {

                newestBean = response;
                adapter.setNewestBean(response);
                initImageView();
            }
        }, NewestBean.class);
        requestQueue.add(gsonRequest);

    }


    public void initImageView() {
        dotViewList = new ArrayList<>();
        list = new ArrayList<>();

        for (int i = 0; i < newestBean.getResult().getFocusimg().size(); i++) {
            ImageView dotView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
            params.leftMargin = 15;//设置小圆点的外边距
            params.rightMargin = 15;

            params.height = 20;//设置小圆点的大小
            params.width = 20;

            if (i == 0) {
                dotView.setBackgroundResource(R.mipmap.point_pressed);
            } else {
                dotView.setBackgroundResource(R.mipmap.point_unpressed);
            }
            dotLayout.addView(dotView, params);

            dotViewList.add(dotView);
            //上面是动态添加小圆点
        }

        for (int i = 0; i < newestBean.getResult().getFocusimg().size(); i++) {
            ImageView imageView = (ImageView) inflater.inflate(R.layout.scroll_view_item, null);
            String url = newestBean.getResult().getFocusimg().get(i).getImgurl();
            Picasso.with(context).load(url).into(imageView);
            list.add(imageView);
        }
        ImagePaperAdapter adapter = new ImagePaperAdapter((ArrayList) list);
        mviewPager.setAdapter(adapter);
        mviewPager.setCurrentItem(0);
        mviewPager.setOnPageChangeListener(new MyPageChangeListener());
    }
    // 开始轮播图切换

    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
        //根据他的参数说明，第一个参数是执行的任务，第二个参数是第一次执行的间隔，第三个参数是执行任务的周期；

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, Detail.class);
        intent.setAction(Intent.ACTION_VIEW);
        int viewType = adapter.getItemViewType(position - 2);
        String url = "";
        switch (viewType) {
            case 3:
                url = "http://v.autohome.com.cn/v_4_" + newestBean.getResult().getNewslist().get(position - 2).getId() + ".html";
                break;
            case 5:
                url = "http://forum.app.autohome.com.cn/autov5.0.0/forum/club/topiccontent-a2-pm2-v5.0.0-t" + newestBean.getResult().getNewslist().get(position - 2).getId() + "-o0-p1-s20-c1-nt0-fs0-sp0-al0-cw320.json";
                break;
            default:
                url = "http://cont.app.autohome.com.cn/autov4.2.5/content/News/newscontent-a2-pm1-v4.2.5-n" +
                        newestBean.getResult().getNewslist().get(position - 2).getId() + "-lz0-sp0-nt0-sa1-p0-c1-fs0-cw320.html";
                break;
        }
        intent.putExtra("url", url);

        int ids = newestBean.getResult().getNewslist().get(position - 2).getId();
        String imageUrl = newestBean.getResult().getNewslist().get(position- 2).getSmallpic();
        String title = newestBean.getResult().getNewslist().get(position - 2).getTitle();
        String time = newestBean.getResult().getNewslist().get(position - 2).getTime();
        Collect collect = new Collect((long)ids,url,imageUrl,title,time);
        intent.putExtra("collect",collect);
        startActivity(intent);
    }


    //执行轮播图切换任务
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (mviewPager) {
                currentItem = (currentItem + 1) % list.size();
                handler.sendEmptyMessage(100);
            }
        }
    }

    //ViewPager的监听器
    //当ViewPager中页面的状态发生改变时调用
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    System.out.println(" 手势滑动，空闲中");
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    System.out.println(" 界面切换中");
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (mviewPager.getCurrentItem() == mviewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        mviewPager.setCurrentItem(0);
                        System.out.println(" 滑动到最后一张");
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (mviewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        mviewPager.setCurrentItem(mviewPager.getAdapter().getCount() - 1);
                        System.out.println(" 滑动到第一张");
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub
            //这里面动态改变小圆点的被背景，来实现效果
            currentItem = pos;
            for (int i = 0; i < dotViewList.size(); i++) {
                if (i == pos) {
                    (dotViewList.get(pos)).setBackgroundResource(R.mipmap.point_pressed);
                } else {
                    (dotViewList.get(i)).setBackgroundResource(R.mipmap.point_unpressed);
                }
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //recyclerView.scrollToPosition(0);

    }
}