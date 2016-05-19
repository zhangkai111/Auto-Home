package com.lanou3g.autohome.recommenddetail;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseActivity;
import com.lanou3g.autohome.recommendadapter.NewsFlashDetailAdapter;
import com.lanou3g.autohome.recommendbean.GsonRequest;
import com.lanou3g.autohome.recommendbean.NewsFlashDetailBean;
import com.lanou3g.autohome.swipe.RefreshableView;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/12.
 */
public class NewsFlashDetail extends BaseActivity {

    private ListView listView;
    private NewsFlashDetailBean newsFlashDetailBean;
    private NewsFlashDetailAdapter newsFlashDetailAdapter;
    private ImageView headIv, headUserIv;
    private TextView headNewsTypeName, headEdit, headTitleTv, headAuthor, headCountTv, headState, headNameTv, headTimeTv, headContentTv;
    private View headView;

    @Override
    protected int getLayout() {
        return R.layout.newsflash_detail;
    }

    @Override
    protected void initView() {
        listView = bindView(R.id.news_flash_detail_lv);
        headView = getLayoutInflater().inflate(R.layout.newsflash_item_head, null);
        headIv = (ImageView) headView.findViewById(R.id.newsflash_item_head_iv);
        headUserIv = (ImageView) headView.findViewById(R.id.newsflash_item_head_below_iv);
        headNewsTypeName = (TextView) headView.findViewById(R.id.newsflash_item_head_news_type_name_tv);
        headEdit = (TextView) headView.findViewById(R.id.newsflash_item_head_edit_tv);
        headTitleTv = (TextView) headView.findViewById(R.id.newsflash_item_head_news_title_tv);
        headAuthor = (TextView) headView.findViewById(R.id.newsflash_item_head_author_tv);
        headCountTv = (TextView) headView.findViewById(R.id.newsflash_item_head_reviewcount_tv);
        headState = (TextView) headView.findViewById(R.id.newsflash_item_head_state_tv);
        headNameTv = (TextView) headView.findViewById(R.id.newsflash_item_head_below_name_tv);
        headTimeTv = (TextView) headView.findViewById(R.id.newsflash_item_head_below_time_tv);
        headContentTv = (TextView) headView.findViewById(R.id.newsflash_item_head_below_content_tv);
        newsFlashDetailAdapter = new NewsFlashDetailAdapter(getApplication());
        newsFlashDetailAdapter.setNewsFlashDetailBean(newsFlashDetailBean);
        listView.setAdapter(newsFlashDetailAdapter);
        listView.addHeaderView(headView);

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonRequest<NewsFlashDetailBean> gsonRequest = new GsonRequest<>(Request.Method.GET, url,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("NewsFlashDetail", "NewsFlashDetail拉取数据失败");
                    }
                }, new Response.Listener<NewsFlashDetailBean>() {
            @Override
            public void onResponse(NewsFlashDetailBean response) {
                Log.d("NewsFlashDetail", "NewsFlashDetail拉取数据成功");
                setData(response);
                newsFlashDetailAdapter.setNewsFlashDetailBean(response);
            }
        }, NewsFlashDetailBean.class);
        requestQueue.add(gsonRequest);
        listView.setAdapter(newsFlashDetailAdapter);

    }

    private void setData(NewsFlashDetailBean newsFlashDetailBean) {
        headNewsTypeName.setText(newsFlashDetailBean.getResult().getNewsdata().getNewstypeanme());
        headTitleTv.setText(newsFlashDetailBean.getResult().getNewsdata().getTitle());
        headCountTv.setText(newsFlashDetailBean.getResult().getNewsdata().getReviewcount() + "人浏览");
        headNameTv.setText(newsFlashDetailBean.getResult().getNewsdata().getNewsauthor());
        headAuthor.setText(newsFlashDetailBean.getResult().getNewsdata().getNewsauthor());
        headTimeTv.setText(newsFlashDetailBean.getResult().getNewsdata().getCreatetime());
        headEdit.setText("编辑");
        if (newsFlashDetailBean.getResult().getNewsdata().getNewsstate() == 2) {
            headState.setText("播报结束");
        } else {
            headState.setText("播报中");
        }
        headContentTv.setText(newsFlashDetailBean.getResult().getNewsdata().getSummary());
        setImage(headIv, newsFlashDetailBean.getResult().getNewsdata().getImg());
        setImage(headUserIv, newsFlashDetailBean.getResult().getNewsdata().getHeadimg());
    }

    private void setImage(ImageView image, String url) {
        Picasso.with(getApplication()).load(url).into(image);
    }

}
