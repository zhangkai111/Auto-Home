package com.lanou3g.autohome.findcarfragment;

import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.findcaradapter.FilterAdapter;
import com.lanou3g.autohome.findcarbean.FilterBean;
import com.lanou3g.autohome.recommendbean.GsonRequest;
import com.lanou3g.autohome.utils.VolleySingle;

/**
 * Created by dllo on 16/5/10.
 * 筛选
 */
public class Filter extends BaseFragment implements View.OnClickListener {
    private PullToRefreshListView listView;
    private FilterBean filterBean;
    private FilterAdapter filterAdapter;
    private ILoadingLayout startLoading;
    private RelativeLayout relativeLayout;
    private PopupWindow popupWindow;
    int i = 0;

    @Override
    public int initLayout() {
        return R.layout.findcar_filter;
    }

    @Override
    public void initView() {

        listView = bindView(R.id.findcar_filter_lv);
        listView.setMode(PullToRefreshBase.Mode.BOTH);

        relativeLayout = bindView(R.id.findcar_filter_condition_rl);
        relativeLayout.setOnClickListener(this);

        filterAdapter = new FilterAdapter(context);
        filterAdapter.setFilterBean(filterBean);
        listView.setAdapter(filterAdapter);
    }

    @Override
    public void initData() {

        startLoading = listView.getLoadingLayoutProxy(true,false);
        startLoading.setRefreshingLabel("正在刷新");
        startLoading.setReleaseLabel("释放开始刷新");

        ILoadingLayout loadingLayout = listView.getLoadingLayoutProxy(false,true);
        loadingLayout.setRefreshingLabel("正在加载");
        loadingLayout.setPullLabel("上拉加载");
        showPopup();
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                VolleySingle.addRequest("http://223.99.255.20/cars.app.autohome.com.cn/cars_v5.7.0/cars/gethotseries-a2-pm2-v5.8.5-p1-s20.json",
                        FilterBean.class, new Response.Listener<FilterBean>() {
                            @Override
                            public void onResponse(FilterBean response) {
                                filterAdapter.setFilterBean(response);
                                listView.onRefreshComplete();
                                String str = DateUtils.formatDateTime(getContext(),System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                                startLoading.setLastUpdatedLabel("上次更新时间" + str);
                                Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "刷新失败", Toast.LENGTH_SHORT).show();
                            }
                        });

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载

                //没有接口
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        GsonRequest<FilterBean> gsonRequest = new GsonRequest<>(Request.Method.GET, "http://223.99.255.20/cars.app.autohome.com.cn/cars_v5.7.0/cars/gethotseries-a2-pm2-v5.8.5-p1-s20.json",
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, new Response.Listener<FilterBean>() {
            @Override
            public void onResponse(FilterBean response) {
                filterAdapter.setFilterBean(response);
            }
        },FilterBean.class);
        requestQueue.add(gsonRequest);


    }

    @Override
    public void onClick(View v) {
        //判断点击的次数，点击偶数次让他隐藏，奇数次让他显示
        i ++;
        if (i % 2 != 0) {
            relativeLayout.setVisibility(View.VISIBLE);
            popupWindow.showAsDropDown(relativeLayout);
        }else {
            popupWindow.dismiss();
        }
    }
    //popupWindow方法
    private void showPopup(){
        popupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.popup_window,null);
        popupWindow.setContentView(view);

    }

}
