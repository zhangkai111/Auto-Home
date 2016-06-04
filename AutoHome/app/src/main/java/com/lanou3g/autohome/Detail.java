package com.lanou3g.autohome;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou3g.autohome.base.BaseActivity;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by dllo on 16/5/11.
 */
public class Detail extends BaseActivity {
    private WebView webView;
    private ImageView collectIv,shareIv;
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
        shareIv = bindView(R.id.detail_collect_share_iv);
        shareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
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

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

//        oks.setTitle("分享标题--Title");
//        oks.setTitleUrl("http://mob.com");
//        oks.setText("分享测试文--Text");
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");

// 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
