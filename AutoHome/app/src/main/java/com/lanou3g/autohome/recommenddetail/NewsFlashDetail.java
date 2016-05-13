package com.lanou3g.autohome.recommenddetail;

import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseActivity;

/**
 * Created by dllo on 16/5/12.
 */
public class NewsFlashDetail extends BaseActivity {

    private WebView webView;
    @Override
    protected int getLayout() {
        return R.layout.newsflash_detail;
    }

    @Override
    protected void initView() {

        webView = bindView(R.id.newsflash_detail_wv);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.d("NewsDetail", url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }
}
