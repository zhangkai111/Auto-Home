package com.lanou3g.autohome.recommenddetail;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou3g.autohome.Collect;
import com.lanou3g.autohome.CollectDao;
import com.lanou3g.autohome.CollectSingle;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseActivity;

import java.util.List;

/**
 * Created by dllo on 16/5/11.
 */
public class Detail extends BaseActivity {
    private WebView webView;
    private ImageView collectIv;
    private Collect collect;
    private CollectDao collectDao;//数据库表的操作对象
    private TextView returnTv;
    private int i = 0;

    @Override
    protected int getLayout() {
        return R.layout.detail;
    }

    @Override
    protected void initView() {
        webView = bindView(R.id.news_detail_wv);
        collectIv = bindView(R.id.detail_collect_collect_iv);
        collectIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                i++;
                if (i % 2 == 0) {
                    collectIv.setImageResource(R.mipmap.collectblue);
                    collectDao.deleteByKey(collect.getId());
                    Toast.makeText(Detail.this, "取消收藏", Toast.LENGTH_SHORT).show();
                } else {
                    collectIv.setImageResource(R.mipmap.collectyello);
                    collectDao.insert(collect);
                    Toast.makeText(Detail.this, "收藏成功", Toast.LENGTH_SHORT).show();
                }
            }
        });


        returnTv = bindView(R.id.detail_collect_return_tv);
        returnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);

        collectDao = CollectSingle.getInstance().getCollectDao();
        collect = (Collect) intent.getSerializableExtra("collect");
        List<Collect> collectList = collectDao.queryBuilder().list();
        //进入详情页时,判断是否收藏过,(查询该数据类中的ID是否在数据库中存在),如果收藏过,就把图标变为黄色
        if (collectList.size() > 0) {
            for (Collect collect1 : collectList) {
                if (collect1.getId().toString().equals(collect.getId().toString())) {
                    collectIv.setImageResource(R.mipmap.collectyello);
                    i++;
                }
            }
        }


    }

}
