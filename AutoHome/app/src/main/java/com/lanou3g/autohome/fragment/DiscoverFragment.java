package com.lanou3g.autohome.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.fragmentadapter.DiscoverAdapter;
import com.lanou3g.autohome.fragmentbean.DiscoverBean;
import com.lanou3g.autohome.fragmentbean.DiscoverHeadBean;
import com.lanou3g.autohome.fragmentbean.DiscoverImageBean;
import com.lanou3g.autohome.recommenddetail.Detail;
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
 * 发现
 */
public class DiscoverFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView listView;
    private ILoadingLayout downLoadingLayout;
    private PullToRefreshListView pullToRefreshListView;
    private DiscoverAdapter discoverAdapter;
    private DiscoverBean discoverBean;
    private DiscoverImageBean discoverImageBean;
    private LayoutInflater inflater;
    private ViewPager mviewPager;
    private DiscoverHeadBean discoverHeadBean;
    private View imageHead, viewHead, viewCarHead, viewFourCarHead;
    //活动专区
    private ImageView voiceIv, activityIv, compareIv, queryIv, friendIv, lovecarIv, buycarIv, homeIv;
    private TextView voiceTv, activityTv, compareTv, queryTv, friendTv, lovecarTv, buycarTv, homeTv, atyMoreTv;
    private ImageView atyFirstIv, atySecondIv, atyThirdIv, shopIv, buyIv, usedIv, beautyIv, insuranceIv, oilIv;
    //特惠热卖
    private ImageView hotFiritIv, hotSecondIv, hotThirdIv, hotForthIv;
    private TextView titleHotTv, hotFirstTitleTv, hotFirstShortTitleTv, hotFirstPrice, hotFirstFctPrice, hotSecondTitleTv, hotSecondShortTitleTv, hotSecondPrice, hotSecondFctPrice, hotThirdTitleTv, hotThirdShortTitleTv, hotThirdPrice, hotThirdFctPrice, hotForthTitleTv, hotForthShortTitleTv, hotForthPrice, hotForthFctPrice;
    //品牌精品
    private ImageView firstBrandIv, secondBrandIv, thirdBrandIv, forthBrandIv;
    private TextView titleBrandTv, firstBrandTitleTv, secondBrandTitleTv, thirdBrandTitleTv, forthBrandTitleTv, firstBrandShortTitleTv, secondBrandShortTitleTv, thirdBrandShortTitleTv, forthBrandShortTitleTv, firstBrandPriceTv, secondBrandPriceTv, thirdBrandPriceTv, forthBrandPriceTv, firstBrandFctPrice, secondBrandFctPrice, thirdBrandFctPrice, forthBrandFctPrice;
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
        return R.layout.fragment_discover;
    }

    @Override
    public void initView() {
        pullToRefreshListView = bindView(R.id.fragment_discover_lv);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        listView = pullToRefreshListView.getRefreshableView();
        discoverAdapter = new DiscoverAdapter(context);
        imageHead = getLayoutInflater(null).inflate(R.layout.discover_image_head, null);
        listView.addHeaderView(imageHead);

        viewHead = getLayoutInflater(null).inflate(R.layout.discover_head, null);
        initViewHeadData();
        listView.addHeaderView(viewHead);

        viewCarHead = getLayoutInflater(null).inflate(R.layout.discover_head_four_car, null);
        initCarViewHeadData();
        listView.addHeaderView(viewCarHead);

        viewFourCarHead = getLayoutInflater(null).inflate(R.layout.discover_head_four_car, null);
        initFourCarViewHeadData();
        listView.addHeaderView(viewFourCarHead);

        listView.setOnItemClickListener(this);

        inflater = LayoutInflater.from(context);
        mviewPager = bindView(R.id.discover_myviewPager);
        dotLayout = bindView(R.id.discover_dotLayout);
        dotLayout.removeAllViews();


        if (isAutoPlay) {
            startPlay();
        }
    }

    private void initFourCarViewHeadData() {
        //品牌精选

        titleBrandTv = (TextView) viewFourCarHead.findViewById(R.id.discover_head_four_car);
        firstBrandIv = (ImageView) viewFourCarHead.findViewById(R.id.discover_head_car_first_iv);
        firstBrandTitleTv = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_first_title_tv);
        firstBrandShortTitleTv = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_first_short_title_tv);
        firstBrandPriceTv = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_first_price_tv);
        firstBrandFctPrice = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_first_fctprice_tv);

        secondBrandIv = (ImageView) viewFourCarHead.findViewById(R.id.discover_head_car_second_iv);
        secondBrandTitleTv = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_second_title_tv);
        secondBrandShortTitleTv = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_second_short_title_tv);
        secondBrandPriceTv = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_second_price_tv);
        secondBrandFctPrice = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_second_fctprice_tv);

        thirdBrandIv = (ImageView) viewFourCarHead.findViewById(R.id.discover_head_car_third_iv);
        thirdBrandTitleTv = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_third_title_tv);
        thirdBrandShortTitleTv = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_third_short_title_tv);
        thirdBrandPriceTv = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_third_price_tv);
        thirdBrandFctPrice = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_third_fctprice_tv);

        forthBrandIv = (ImageView) viewFourCarHead.findViewById(R.id.discover_head_car_forth_iv);
        forthBrandTitleTv = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_forth_title_tv);
        forthBrandShortTitleTv = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_forth_short_title_tv);
        forthBrandPriceTv = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_forth_price_tv);
        forthBrandFctPrice = (TextView) viewFourCarHead.findViewById(R.id.discover_head_car_forth_fctprice_tv);
    }

    private void initCarViewHeadData() {

        //特惠热卖
        titleHotTv = (TextView) viewCarHead.findViewById(R.id.discover_head_four_car);
        hotFiritIv = (ImageView) viewCarHead.findViewById(R.id.discover_head_car_first_iv);
        hotFirstTitleTv = (TextView) viewCarHead.findViewById(R.id.discover_head_car_first_title_tv);
        hotFirstShortTitleTv = (TextView) viewCarHead.findViewById(R.id.discover_head_car_first_short_title_tv);
        hotFirstPrice = (TextView) viewCarHead.findViewById(R.id.discover_head_car_first_price_tv);
        hotFirstFctPrice = (TextView) viewCarHead.findViewById(R.id.discover_head_car_first_fctprice_tv);

        hotSecondIv = (ImageView) viewCarHead.findViewById(R.id.discover_head_car_second_iv);
        hotSecondTitleTv = (TextView) viewCarHead.findViewById(R.id.discover_head_car_second_title_tv);
        hotSecondShortTitleTv = (TextView) viewCarHead.findViewById(R.id.discover_head_car_second_short_title_tv);
        hotSecondPrice = (TextView) viewCarHead.findViewById(R.id.discover_head_car_second_price_tv);
        hotSecondFctPrice = (TextView) viewCarHead.findViewById(R.id.discover_head_car_second_fctprice_tv);

        hotThirdIv = (ImageView) viewCarHead.findViewById(R.id.discover_head_car_third_iv);
        hotThirdTitleTv = (TextView) viewCarHead.findViewById(R.id.discover_head_car_third_title_tv);
        hotThirdShortTitleTv = (TextView) viewCarHead.findViewById(R.id.discover_head_car_third_short_title_tv);
        hotThirdPrice = (TextView) viewCarHead.findViewById(R.id.discover_head_car_third_price_tv);
        hotThirdFctPrice = (TextView) viewCarHead.findViewById(R.id.discover_head_car_third_fctprice_tv);

        hotForthIv = (ImageView) viewCarHead.findViewById(R.id.discover_head_car_forth_iv);
        hotForthTitleTv = (TextView) viewCarHead.findViewById(R.id.discover_head_car_forth_title_tv);
        hotForthShortTitleTv = (TextView) viewCarHead.findViewById(R.id.discover_head_car_forth_short_title_tv);
        hotForthPrice = (TextView) viewCarHead.findViewById(R.id.discover_head_car_forth_price_tv);
        hotForthFctPrice = (TextView) viewCarHead.findViewById(R.id.discover_head_car_forth_fctprice_tv);


    }

    private void initViewHeadData() {
        //发现轮播图下的
        voiceIv = (ImageView) viewHead.findViewById(R.id.discover_head_scroll_car_iv);
        voiceTv = (TextView) viewHead.findViewById(R.id.discover_head_scroll_car_tv);
        activityIv = (ImageView) viewHead.findViewById(R.id.discover_head_scroll_activity_iv);
        activityTv = (TextView) viewHead.findViewById(R.id.discover_head_scroll_activity_tv);
        compareIv = (ImageView) viewHead.findViewById(R.id.discover_head_scroll_compare_iv);
        compareTv = (TextView) viewHead.findViewById(R.id.discover_head_scroll_compare_tv);
        queryIv = (ImageView) viewHead.findViewById(R.id.discover_head_scroll_query_iv);
        queryTv = (TextView) viewHead.findViewById(R.id.discover_head_scroll_query_tv);
        friendIv = (ImageView) viewHead.findViewById(R.id.discover_head_scroll_friend_iv);
        friendTv = (TextView) viewHead.findViewById(R.id.discover_head_scroll_friend_tv);
        lovecarIv = (ImageView) viewHead.findViewById(R.id.discover_head_scroll_valuation_iv);
        lovecarTv = (TextView) viewHead.findViewById(R.id.discover_head_scroll_valuation_tv);
        buycarIv = (ImageView) viewHead.findViewById(R.id.discover_head_scroll_plan_iv);
        buycarTv = (TextView) viewHead.findViewById(R.id.discover_head_scroll_plan_tv);
        homeIv = (ImageView) viewHead.findViewById(R.id.discover_head_scroll_home_iv);
        homeTv = (TextView) viewHead.findViewById(R.id.discover_head_scroll_home_tv);

        voiceIv.setOnClickListener(this);
        activityIv.setOnClickListener(this);
        compareIv.setOnClickListener(this);
        queryIv.setOnClickListener(this);
        friendIv.setOnClickListener(this);
        lovecarIv.setOnClickListener(this);
        buycarIv.setOnClickListener(this);
        homeIv.setOnClickListener(this);

        //活动专区
        atyFirstIv = (ImageView) viewHead.findViewById(R.id.discover_head_first_iv);
        atySecondIv = (ImageView) viewHead.findViewById(R.id.discover_head_second_iv);
        atyThirdIv = (ImageView) viewHead.findViewById(R.id.discover_head_third_iv);
        atyMoreTv = (TextView) viewHead.findViewById(R.id.discover_more_activity_tv);

        atyFirstIv.setOnClickListener(this);
        atySecondIv.setOnClickListener(this);
        atyThirdIv.setOnClickListener(this);
        atyMoreTv.setOnClickListener(this);
        //六组图片
        shopIv = (ImageView) viewHead.findViewById(R.id.discover_head_car_shop_iv);
        buyIv = (ImageView) viewHead.findViewById(R.id.discover_head_buy_iv);
        usedIv = (ImageView) viewHead.findViewById(R.id.discover_head_used_car_iv);
        beautyIv = (ImageView) viewHead.findViewById(R.id.discover_head_beauty_iv);
        insuranceIv = (ImageView) viewHead.findViewById(R.id.discover_head_insurance_iv);
        oilIv = (ImageView) viewHead.findViewById(R.id.discover_head_oil_iv);

        shopIv.setOnClickListener(this);
        buyIv.setOnClickListener(this);
        usedIv.setOnClickListener(this);
        beautyIv.setOnClickListener(this);
        insuranceIv.setOnClickListener(this);
        oilIv.setOnClickListener(this);

    }


    @Override
    public void initData() {

        downLoadingLayout = pullToRefreshListView.getLoadingLayoutProxy(true, false);
        downLoadingLayout.setRefreshingLabel("正在刷新");
        downLoadingLayout.setReleaseLabel("释放开始刷新");

        ILoadingLayout upLoadingLayout = pullToRefreshListView.getLoadingLayoutProxy(false, true);
        upLoadingLayout.setReleaseLabel("正在加载");
        upLoadingLayout.setPullLabel("上拉加载");

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                VolleySingle.addRequest("http://223.99.255.20/mobilenc.app.autohome.com.cn/discover_v5.8.0/mall/intelligentrecommend.ashx?a=2&pm=2&v=5.8.6&uid=0&deviceid=869765028748315&gps=&cityid=110100&pid=0&pageindex=1&pagesize=20&hid",
                        DiscoverBean.class, new Response.Listener<DiscoverBean>() {
                            @Override
                            public void onResponse(DiscoverBean response) {
                                discoverBean = response;
                                discoverAdapter.setDiscoverBean(discoverBean);
                                pullToRefreshListView.onRefreshComplete();
                                String str = DateUtils.formatDateTime(getContext(),System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
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

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        //轮播图

        VolleySingle.addRequest("http://app.api.autohome.com.cn/autov5.0.0/mobile/appadvert-a2-pm1-v5.0.1-sid2-pid340000-cid0-lat0.000000-lng0.000000.json",
                DiscoverImageBean.class, new Response.Listener<DiscoverImageBean>() {
                    @Override
                    public void onResponse(DiscoverImageBean response) {
                        discoverImageBean = response;
                        initImageView();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //item和汽车的头布局

        VolleySingle.addRequest("http://223.99.255.20/mobilenc.app.autohome.com.cn/discover_v5.8.0/mall/intelligentrecommend.ashx?a=2&pm=2&v=5.8.6&uid=0&deviceid=869765028748315&gps=&cityid=110100&pid=0&pageindex=1&pagesize=20&hid",
                DiscoverBean.class, new Response.Listener<DiscoverBean>() {
                    @Override
                    public void onResponse(DiscoverBean response) {
                        discoverBean = response;
                        discoverAdapter.setDiscoverBean(discoverBean);
                        //特惠热卖
                        Picasso.with(context).load(discoverBean.getResult().getModulelist().get(0).getList().get(0).getLogo()).into(hotFiritIv);
                        titleHotTv.setText(discoverBean.getResult().getModulelist().get(0).getTitle());
                        hotFirstTitleTv.setText(discoverBean.getResult().getModulelist().get(0).getList().get(0).getTitle());
                        hotFirstShortTitleTv.setText(discoverBean.getResult().getModulelist().get(0).getList().get(0).getAdinfo());
                        hotFirstPrice.setText(discoverBean.getResult().getModulelist().get(0).getList().get(0).getPrice());
                        hotFirstFctPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        hotFirstFctPrice.setText(discoverBean.getResult().getModulelist().get(0).getList().get(0).getFctprice());

                        Picasso.with(context).load(discoverBean.getResult().getModulelist().get(0).getList().get(1).getLogo()).into(hotSecondIv);
                        hotSecondTitleTv.setText(discoverBean.getResult().getModulelist().get(0).getList().get(1).getTitle());
                        hotSecondShortTitleTv.setText(discoverBean.getResult().getModulelist().get(0).getList().get(1).getAdinfo());
                        hotSecondPrice.setText(discoverBean.getResult().getModulelist().get(0).getList().get(1).getPrice());
                        hotSecondFctPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        hotSecondFctPrice.setText(discoverBean.getResult().getModulelist().get(0).getList().get(1).getFctprice());

                        Picasso.with(context).load(discoverBean.getResult().getModulelist().get(0).getList().get(2).getLogo()).into(hotThirdIv);
                        hotThirdTitleTv.setText(discoverBean.getResult().getModulelist().get(0).getList().get(2).getTitle());
                        hotThirdShortTitleTv.setText(discoverBean.getResult().getModulelist().get(0).getList().get(2).getAdinfo());
                        hotThirdPrice.setText(discoverBean.getResult().getModulelist().get(0).getList().get(2).getPrice());
                        hotThirdFctPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        hotThirdFctPrice.setText(discoverBean.getResult().getModulelist().get(0).getList().get(2).getFctprice());

                        Picasso.with(context).load(discoverBean.getResult().getModulelist().get(0).getList().get(3).getLogo()).into(hotForthIv);
                        hotForthTitleTv.setText(discoverBean.getResult().getModulelist().get(0).getList().get(3).getTitle());
                        hotForthShortTitleTv.setText(discoverBean.getResult().getModulelist().get(0).getList().get(3).getAdinfo());
                        hotForthPrice.setText(discoverBean.getResult().getModulelist().get(0).getList().get(3).getPrice());
                        hotForthFctPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        hotForthFctPrice.setText(discoverBean.getResult().getModulelist().get(0).getList().get(3).getFctprice());

                        //品牌精品
                        Picasso.with(context).load(discoverBean.getResult().getModulelist().get(1).getList().get(0).getLogo()).into(firstBrandIv);
                        titleBrandTv.setText(discoverBean.getResult().getModulelist().get(1).getTitle());
                        firstBrandTitleTv.setText(discoverBean.getResult().getModulelist().get(1).getList().get(0).getTitle());
                        firstBrandShortTitleTv.setText(discoverBean.getResult().getModulelist().get(1).getList().get(0).getAdinfo());
                        firstBrandPriceTv.setText(discoverBean.getResult().getModulelist().get(1).getList().get(0).getPrice());
                        firstBrandFctPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        firstBrandFctPrice.setText(discoverBean.getResult().getModulelist().get(1).getList().get(0).getFctprice());

                        Picasso.with(context).load(discoverBean.getResult().getModulelist().get(1).getList().get(1).getLogo()).into(secondBrandIv);
                        secondBrandTitleTv.setText(discoverBean.getResult().getModulelist().get(1).getList().get(1).getTitle());
                        secondBrandShortTitleTv.setText(discoverBean.getResult().getModulelist().get(1).getList().get(1).getAdinfo());
                        secondBrandPriceTv.setText(discoverBean.getResult().getModulelist().get(1).getList().get(1).getPrice());
                        secondBrandFctPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        secondBrandFctPrice.setText(discoverBean.getResult().getModulelist().get(1).getList().get(1).getFctprice());

                        Picasso.with(context).load(discoverBean.getResult().getModulelist().get(1).getList().get(2).getLogo()).into(thirdBrandIv);
                        thirdBrandTitleTv.setText(discoverBean.getResult().getModulelist().get(1).getList().get(2).getTitle());
                        thirdBrandShortTitleTv.setText(discoverBean.getResult().getModulelist().get(1).getList().get(2).getAdinfo());
                        thirdBrandPriceTv.setText(discoverBean.getResult().getModulelist().get(1).getList().get(2).getPrice());
                        thirdBrandFctPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        thirdBrandFctPrice.setText(discoverBean.getResult().getModulelist().get(1).getList().get(2).getFctprice());

                        Picasso.with(context).load(discoverBean.getResult().getModulelist().get(1).getList().get(3).getLogo()).into(forthBrandIv);
                        forthBrandTitleTv.setText(discoverBean.getResult().getModulelist().get(1).getList().get(3).getTitle());
                        forthBrandShortTitleTv.setText(discoverBean.getResult().getModulelist().get(1).getList().get(3).getAdinfo());
                        forthBrandPriceTv.setText(discoverBean.getResult().getModulelist().get(1).getList().get(3).getPrice());
                        forthBrandFctPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        forthBrandFctPrice.setText(discoverBean.getResult().getModulelist().get(1).getList().get(3).getFctprice());


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //活动专区头布局
        VolleySingle.addRequest("http://223.99.255.20/mobilenc.app.autohome.com.cn/discover_v5.8.0/mobile/functionlist-a2-pm2-v5.8.5-pid210000-cid210200.json",
                DiscoverHeadBean.class,
                new Response.Listener<DiscoverHeadBean>() {
                    @Override
                    public void onResponse(DiscoverHeadBean response) {
                        discoverHeadBean = response;
                        Picasso.with(context).load(discoverHeadBean.getResult().getFunctionlist().get(0).getIconurl()).into(voiceIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getFunctionlist().get(1).getIconurl()).into(activityIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getFunctionlist().get(2).getIconurl()).into(compareIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getFunctionlist().get(3).getIconurl()).into(queryIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getFunctionlist().get(4).getIconurl()).into(friendIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getFunctionlist().get(5).getIconurl()).into(lovecarIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getFunctionlist().get(6).getIconurl()).into(buycarIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getFunctionlist().get(7).getIconurl()).into(homeIv);

                        Picasso.with(context).load(discoverHeadBean.getResult().getImageads().getImageadsinfo().get(0).getImgurl()).into(atyFirstIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getImageads().getImageadsinfo().get(1).getImgurl()).into(atySecondIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getImageads().getImageadsinfo().get(2).getImgurl()).into(atyThirdIv);

                        Picasso.with(context).load(discoverHeadBean.getResult().getBusinesslist().get(0).getIconurl()).into(shopIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getBusinesslist().get(1).getIconurl()).into(buyIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getBusinesslist().get(2).getIconurl()).into(usedIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getBusinesslist().get(3).getIconurl()).into(beautyIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getBusinesslist().get(4).getIconurl()).into(insuranceIv);
                        Picasso.with(context).load(discoverHeadBean.getResult().getBusinesslist().get(5).getIconurl()).into(oilIv);

                        voiceTv.setText(discoverHeadBean.getResult().getFunctionlist().get(0).getTitle());
                        activityTv.setText(discoverHeadBean.getResult().getFunctionlist().get(1).getTitle());
                        compareTv.setText(discoverHeadBean.getResult().getFunctionlist().get(2).getTitle());
                        queryTv.setText(discoverHeadBean.getResult().getFunctionlist().get(3).getTitle());
                        friendTv.setText(discoverHeadBean.getResult().getFunctionlist().get(4).getTitle());
                        lovecarTv.setText(discoverHeadBean.getResult().getFunctionlist().get(5).getTitle());
                        buycarTv.setText(discoverHeadBean.getResult().getFunctionlist().get(6).getTitle());
                        homeTv.setText(discoverHeadBean.getResult().getFunctionlist().get(7).getTitle());

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        pullToRefreshListView.setAdapter(discoverAdapter);
    }

    public void initImageView() {
        dotViewList = new ArrayList<>();
        list = new ArrayList<>();


        for (int i = 0; i < discoverImageBean.getResult().getList().size(); i++) {
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

        for (int i = 0; i < discoverImageBean.getResult().getList().size(); i++) {
            ImageView imageView = (ImageView) inflater.inflate(R.layout.scroll_view_item, null);
            String url = discoverImageBean.getResult().getList().get(i).getImgurl();
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
        if (position > 3) {
            Intent intent = new Intent(context, Detail.class);
            intent.setAction(Intent.ACTION_VIEW);
            String url = discoverBean.getResult().getGoodslist().getList().get(position - 5).getMurl();
            intent.putExtra("url", url);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, Detail.class);
        String url = "";
        switch (v.getId()) {
            case R.id.discover_head_scroll_car_iv:
                url = discoverHeadBean.getResult().getFunctionlist().get(0).getUrl();
                break;
            case R.id.discover_head_scroll_activity_iv:
                url = discoverHeadBean.getResult().getFunctionlist().get(1).getUrl();
                break;
            case R.id.discover_head_scroll_compare_iv:
                url = discoverHeadBean.getResult().getFunctionlist().get(2).getUrl();
                break;
            case R.id.discover_head_scroll_query_iv:
                url = discoverHeadBean.getResult().getFunctionlist().get(3).getUrl();
                break;
            case R.id.discover_head_scroll_friend_iv:
                url = discoverHeadBean.getResult().getFunctionlist().get(4).getUrl();
                break;
            case R.id.discover_head_scroll_valuation_iv:
                url = discoverHeadBean.getResult().getFunctionlist().get(5).getUrl();
                break;
            case R.id.discover_head_scroll_plan_iv:
                url = discoverHeadBean.getResult().getFunctionlist().get(6).getUrl();
                break;
            case R.id.discover_head_scroll_home_iv:
                url = discoverHeadBean.getResult().getFunctionlist().get(7).getUrl();
                break;
            case R.id.discover_head_first_iv:
                url = discoverHeadBean.getResult().getImageads().getImageadsinfo().get(0).getUrl();
                break;
            case R.id.discover_head_second_iv:
                url = discoverHeadBean.getResult().getImageads().getImageadsinfo().get(1).getUrl();
                break;
            case R.id.discover_head_third_iv:
                url = discoverHeadBean.getResult().getImageads().getImageadsinfo().get(2).getUrl();
                break;
            case R.id.discover_more_activity_tv:
                url = discoverHeadBean.getResult().getImageads().getMoreactivitysurl();
                break;
            case R.id.discover_head_car_shop_iv:
                url = discoverHeadBean.getResult().getBusinesslist().get(0).getUrl();
                break;
            case R.id.discover_head_buy_iv:
                url = discoverHeadBean.getResult().getBusinesslist().get(1).getUrl();
                break;
            case R.id.discover_head_used_car_iv:
                url = discoverHeadBean.getResult().getBusinesslist().get(2).getUrl();
                break;
            case R.id.discover_head_beauty_iv:
                url = discoverHeadBean.getResult().getBusinesslist().get(3).getUrl();
                break;
            case R.id.discover_head_insurance_iv:
                url = discoverHeadBean.getResult().getBusinesslist().get(4).getUrl();
                break;
            case R.id.discover_head_oil_iv:
                url = discoverHeadBean.getResult().getBusinesslist().get(5).getUrl();
                break;
        }
        intent.putExtra("url", url);
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

}
