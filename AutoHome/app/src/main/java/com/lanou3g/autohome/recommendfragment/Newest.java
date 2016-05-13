package com.lanou3g.autohome.recommendfragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.recommendadapter.NewestAdapter;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.recommendadapter.NewsAdapter;
import com.lanou3g.autohome.recommendbean.GsonRequest;
import com.lanou3g.autohome.recommendbean.NewestBean;
import com.lanou3g.autohome.recommenddetail.NewsDetail;
import com.lanou3g.autohome.utils.DividerItemDecoration;
import com.lanou3g.autohome.utils.ImagePaperAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/9.
 */
public class Newest extends BaseFragment implements RecyclerViewOnClickListener {

    private RecyclerViewHeader recyclerViewHeader;
    private RecyclerView recyclerView;
    private NewestAdapter adapter;
    private LayoutInflater inflater;
    private NewestBean newestBean;
    private ViewPager mviewPager;
    /**用于小圆点图片*/
    private List<ImageView> dotViewList;
    /**用于存放轮播效果图片*/
    private List<ImageView> list;

    LinearLayout dotLayout;

    private int currentItem  = 0;//当前页面

    boolean isAutoPlay = true;//是否自动轮播

    private ScheduledExecutorService scheduledExecutorService;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if(msg.what == 100){
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
        //加载头布局最新页面头布局(轮播图)
        recyclerViewHeader = RecyclerViewHeader.fromXml(context,R.layout.newest_recycleview_head);
        recyclerView = bindView(R.id.newest_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //RecyclerView 每条item之间的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        recyclerViewHeader.attachTo(recyclerView);
        adapter = new NewestAdapter(context);

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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        GsonRequest<NewestBean> gsonRequest = new GsonRequest<>(Request.Method.GET,
                "http://app.api.autohome.com.cn/autov4.2.5/news/newslist-a2-pm1-v4.2.5-c0-nt0-p1-s30-l0.html",
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Newest", "最新页面拉取数据失败");
                    }
                }, new Response.Listener<NewestBean>() {
            @Override
            public void onResponse(NewestBean response) {
                Log.d("NewestFragment", "最新页面拉取成功,正在进行解析");
                newestBean = response;
                adapter.setNewestBean(response);
                initImageView();
            }
        }, NewestBean.class);
        requestQueue.add(gsonRequest);
        recyclerView.setAdapter(adapter);
        adapter.setRecyclerViewOnClickListener(this);

    }

    @Override
    public void onClick(int ids) {
        Intent intent = new Intent(context, NewsDetail.class);
        intent.setAction(Intent.ACTION_VIEW);
        String url = "http://cont.app.autohome.com.cn/autov4.2.5/content/News/newscontent-a2-pm1-v4.2.5-n"+
                ids+"-lz0-sp0-nt0-sa1-p0-c1-fs0-cw320.html";
        intent.putExtra("url",url);
        startActivity(intent);
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
                    ( dotViewList.get(pos)).setBackgroundResource(R.mipmap.point_pressed);
                } else {
                    ( dotViewList.get(i)).setBackgroundResource(R.mipmap.point_unpressed);
                }
            }
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        recyclerView.scrollToPosition(0);
        Log.d("NewestFragment", "onResume");

    }
}