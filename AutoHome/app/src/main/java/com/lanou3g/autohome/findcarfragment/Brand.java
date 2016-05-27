package com.lanou3g.autohome.findcarfragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.findcaradapter.BrandAdapter;
import com.lanou3g.autohome.findcarbean.BrandItemBean;
import com.lanou3g.autohome.findcarbean.BrandHotBrandBean;
import com.lanou3g.autohome.findcarbean.BrandMainBean;
import com.lanou3g.autohome.utils.SideBar;
import com.lanou3g.autohome.utils.VolleySingle;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/10.
 * 品牌
 */
public class Brand extends BaseFragment {

    private SideBar sideBar;
    private ExpandableListView expandableListView;
    private BrandHotBrandBean brandHotBrandBean;
    private BrandMainBean brandMainBean;
    private BrandItemBean brandItemBean;
    private BrandAdapter brandAdapter;
    private View hotBrandHead,mainBrandHead,headView;

    private ImageView publicIv,hondaIv,toyotaIv,nowIv,jiliIv,fordIv,audiIv,nissanIv,haverIv,kiaIv;
    private TextView publicTv,hondaTv,toyotaTv,nowTv,jiliTv,fordTv,audiTv,nissanTv,haverTv,kiaTv;

    private ImageView mainFirstIv,mainSecondIv,mainThirdIv;
    private TextView mainFirstTv,mainSecondTv,mainThirdTv;

    private ImageView mySaveIv,historyIv,hotIv;
    private TextView mySaveTv,historyTv,hotTv;

    private TextView textView;

    @Override
    public int initLayout() {
        return R.layout.findcar_brand;
    }

    @Override
    public void initView() {

        expandableListView = bindView(R.id.findcar_brand_lv);
        brandAdapter = new BrandAdapter(context);
        brandAdapter.setBrandItemBean(brandItemBean);
        expandableListView.setGroupIndicator(null);
        textView = bindView(R.id.dialog);
        sideBar =bindView(R.id.sidrbar);

        headView = LayoutInflater.from(context).inflate(R.layout.brand_head,null);
        initBrandHeadData();
        expandableListView.addHeaderView(headView);

        hotBrandHead = LayoutInflater.from(context).inflate(R.layout.brand_head_hot_brand,null);
        initHotBrandHeadData();
        expandableListView.addHeaderView(hotBrandHead);



//        mainBrandHead = LayoutInflater.from(context).inflate(R.layout.brand_head_main_car,null);
//        initMainBrandHeadData();
//        expandableListView.addHeaderView(mainBrandHead);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                sideBar.setTextView(textView);
                int pos = brandAdapter.getPosition(s.charAt(0));
                if (pos != -1) {
                    expandableListView.setSelectedGroup(pos);
                }
            }
        });

    }

    private void initBrandHeadData() {
        mySaveIv = (ImageView) headView.findViewById(R.id.brand_head_my_save_iv);
        mySaveTv = (TextView) headView.findViewById(R.id.brand_head_my_save_tv);
        historyIv = (ImageView) headView.findViewById(R.id.brand_head_history_iv);
        historyTv = (TextView) headView.findViewById(R.id.brand_head_history_tv);
        hotIv = (ImageView) headView.findViewById(R.id.brand_head_hot_iv);
        hotTv = (TextView) headView.findViewById(R.id.brand_head_hot_tv);
    }

    private void initMainBrandHeadData() {

        mainFirstIv = (ImageView) mainBrandHead.findViewById(R.id.brand_head_main_first_iv);
        mainSecondIv = (ImageView) mainBrandHead.findViewById(R.id.brand_head_main_second_iv);
        mainThirdIv = (ImageView) mainBrandHead.findViewById(R.id.brand_head_main_third_iv);
        mainFirstTv = (TextView) mainBrandHead.findViewById(R.id.brand_head_main_first_tv);
        mainSecondTv = (TextView) mainBrandHead.findViewById(R.id.brand_head_main_second_tv);
        mainThirdTv = (TextView) mainBrandHead.findViewById(R.id.brand_head_main_third_tv);

    }

    private void initHotBrandHeadData() {
        publicIv = (ImageView) hotBrandHead.findViewById(R.id.brand_hot_public_iv);
        publicTv = (TextView) hotBrandHead.findViewById(R.id.brand_hot_public_tv);
        hondaIv = (ImageView) hotBrandHead.findViewById(R.id.brand_hot_honda_iv);
        hondaTv = (TextView) hotBrandHead.findViewById(R.id.brand_hot_honda_tv);
        toyotaIv = (ImageView) hotBrandHead.findViewById(R.id.brand_hot_toyota_iv);
        toyotaTv = (TextView) hotBrandHead.findViewById(R.id.brand_hot_toyota_tv);
        nowIv = (ImageView) hotBrandHead.findViewById(R.id.brand_hot_now_iv);
        nowTv = (TextView) hotBrandHead.findViewById(R.id.brand_hot_now_tv);
        jiliIv = (ImageView) hotBrandHead.findViewById(R.id.brand_hot_jili_iv);
        jiliTv = (TextView) hotBrandHead.findViewById(R.id.brand_hot_jili_tv);
        fordIv = (ImageView) hotBrandHead.findViewById(R.id.brand_hot_ford_iv);
        fordTv = (TextView) hotBrandHead.findViewById(R.id.brand_hot_ford_tv);
        audiIv = (ImageView) hotBrandHead.findViewById(R.id.brand_hot_audi_iv);
        audiTv = (TextView) hotBrandHead.findViewById(R.id.brand_hot_audi_tv);
        nissanIv = (ImageView) hotBrandHead.findViewById(R.id.brand_hot_nissan_iv);
        nissanTv = (TextView) hotBrandHead.findViewById(R.id.brand_hot_nissan_tv);
        haverIv = (ImageView) hotBrandHead.findViewById(R.id.brand_hot_haver_iv);
        haverTv = (TextView) hotBrandHead.findViewById(R.id.brand_hot_haver_tv);
        kiaIv = (ImageView) hotBrandHead.findViewById(R.id.brand_hot_kia_iv);
        kiaTv = (TextView) hotBrandHead.findViewById(R.id.brand_hot_kia_tv);

    }


    @Override
    public void initData() {
        VolleySingle.addRequest("http://app.api.autohome.com.cn/autov5.0.0/news/brandsfastnews-pm1-ts0.json",
                BrandItemBean.class,
                new Response.Listener<BrandItemBean>() {
                    @Override
                    public void onResponse(BrandItemBean response) {
                        brandItemBean = response;
                        brandAdapter.setBrandItemBean(response);
                        expandableListView.setAdapter(brandAdapter);
                        if (response.getResult().getBrandlist().size() > 0) {
                            for (int i = 0; i < response.getResult().getBrandlist().size(); i++) {
                                expandableListView.expandGroup(i);
                            }
                        }
                        //取消父item的点击收缩
                        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                            @Override
                            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                                return true;
                            }
                        });
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        VolleySingle.addRequest("http://223.99.255.20/cars.app.autohome.com.cn/dealer_v5.7.0/dealer/hotbrands-pm2.json",
                BrandHotBrandBean.class,
                new Response.Listener<BrandHotBrandBean>() {
                    @Override
                    public void onResponse(BrandHotBrandBean response) {
                        brandHotBrandBean = response;
                        Picasso.with(context).load(brandHotBrandBean.getResult().getList().get(0).getImg()).into(publicIv);
                        Picasso.with(context).load(brandHotBrandBean.getResult().getList().get(1).getImg()).into(hondaIv);
                        Picasso.with(context).load(brandHotBrandBean.getResult().getList().get(2).getImg()).into(toyotaIv);
                        Picasso.with(context).load(brandHotBrandBean.getResult().getList().get(3).getImg()).into(nowIv);
                        Picasso.with(context).load(brandHotBrandBean.getResult().getList().get(4).getImg()).into(jiliIv);
                        Picasso.with(context).load(brandHotBrandBean.getResult().getList().get(5).getImg()).into(fordIv);
                        Picasso.with(context).load(brandHotBrandBean.getResult().getList().get(6).getImg()).into(audiIv);
                        Picasso.with(context).load(brandHotBrandBean.getResult().getList().get(7).getImg()).into(nissanIv);
                        Picasso.with(context).load(brandHotBrandBean.getResult().getList().get(8).getImg()).into(haverIv);
                        Picasso.with(context).load(brandHotBrandBean.getResult().getList().get(9).getImg()).into(kiaIv);

                        publicTv.setText(brandHotBrandBean.getResult().getList().get(0).getName());
                        hondaTv.setText(brandHotBrandBean.getResult().getList().get(1).getName());
                        toyotaTv.setText(brandHotBrandBean.getResult().getList().get(2).getName());
                        nowTv.setText(brandHotBrandBean.getResult().getList().get(3).getName());
                        jiliTv.setText(brandHotBrandBean.getResult().getList().get(4).getName());
                        fordTv.setText(brandHotBrandBean.getResult().getList().get(5).getName());
                        audiTv.setText(brandHotBrandBean.getResult().getList().get(6).getName());
                        nissanTv.setText(brandHotBrandBean.getResult().getList().get(7).getName());
                        haverTv.setText(brandHotBrandBean.getResult().getList().get(8).getName());
                        kiaTv.setText(brandHotBrandBean.getResult().getList().get(9).getName());

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


        //接口有改动
//        VolleySingle.addRequest("http://223.99.255.20/adnewnc.app.autohome.com.cn/autov5.7.0/ad/infoad.ashx?version=5.8.5&platform=2&appid=2&networkid=0&adtype=1&provinceid=210000&cityid=0&lng=121.551079&lat=38.889656&gps_city=210200&pageid=04704225-c34a-425c-8e4b-f8781eaf19dd&isretry=1&deviceid=99000628573771",
//                BrandMainBean.class,
//                new Response.Listener<BrandMainBean>() {
//                    @Override
//                    public void onResponse(BrandMainBean response) {
//                        brandMainBean = response;
//                        Picasso.with(context).load(brandMainBean.getResult().getList().get(0).getImg()).into(mainFirstIv);
//                        mainFirstTv.setText(brandMainBean.getResult().getList().get(0).getSeriesname());
//                        Picasso.with(context).load(brandMainBean.getResult().getList().get(1).getImg()).into(mainSecondIv);
//                        mainSecondTv.setText(brandMainBean.getResult().getList().get(1).getSeriesname());
//                        Picasso.with(context).load(brandMainBean.getResult().getList().get(2).getImg()).into(mainThirdIv);
//                        mainThirdTv.setText(brandMainBean.getResult().getList().get(2).getSeriesname());
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });

    }
}
