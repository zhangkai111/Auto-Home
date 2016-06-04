package com.lanou3g.autohome.forumselectiondetail;

import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseActivity;

/**
 * Created by dllo on 16/5/12.
 */
public class AllDetail extends BaseActivity {

    private WebView webView;
    @Override
    protected int getLayout() {
        return R.layout.all_detail;
    }

    @Override
    protected void initView() {

        webView = bindView(R.id.all_detail_wv);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        //设置webview必须设置支持Javascript，
        webView.getSettings().setJavaScriptEnabled(true);
        //点击链接继续在当前browser中响应，而不是新开Android的系统browser中响应该链接，
        // 必须覆盖 webview的WebViewClient对象。
        //也就是不用打开手机中的浏览器，直接打开
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
