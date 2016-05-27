package com.lanou3g.autohome.recommenddetail;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseActivity;

/**
 * Created by dllo on 16/5/11.
 */
public class Detail extends BaseActivity {
    private WebView webView;
    private ImageView collectIv;
    private boolean isCollect;
    private TextView returnTv;

    @Override
    protected int getLayout() {
        return R.layout.detail;
    }

    @Override
    protected void initView() {
        webView = bindView(R.id.news_detail_wv);
        collectIv = bindView(R.id.detail_collect_collect_iv);
        collectIv.setOnClickListener(new View.OnClickListener() {
            int i = 1;

            @Override
            public void onClick(View v) {
                if (i % 2 == 0) {
                    collectIv.setImageResource(R.mipmap.collectblue);
                } else {
                    collectIv.setImageResource(R.mipmap.collectyello);
                }
                i++;
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

    }

}
