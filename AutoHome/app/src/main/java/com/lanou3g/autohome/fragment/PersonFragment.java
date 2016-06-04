package com.lanou3g.autohome.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.lanou3g.autohome.Login.LoginActivity;
import com.lanou3g.autohome.mycollect.MyCollectActivity;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseFragment;
import com.lanou3g.autohome.utils.MyDraweable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/9.
 * 我
 */
public class PersonFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout myCollectLayout;
    private ImageView userIcon;
    private TextView userName;
    private LinearLayout userLayout, qqLayout, weboLayout, weChatLayout;
    private Platform qq;
    private int i = 1;

    //用handler来刷新UI
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            List<String> strs = (List<String>) msg.obj;
            setImage(userIcon, strs.get(0));
            userName.setText(strs.get(1));
            return false;
        }
    });

    @Override
    public int initLayout() {
        ShareSDK.initSDK(context);
        qq = ShareSDK.getPlatform(QQ.NAME);
        return R.layout.fragment_person;
    }

    @Override
    public void initView() {

        myCollectLayout = bindView(R.id.fragment_person_collect_layout);
        myCollectLayout.setOnClickListener(this);

        userLayout = bindView(R.id.user_name_login_layout);
        qqLayout = bindView(R.id.qq_login_layout);
        weboLayout = bindView(R.id.webo_login_layout);
        weChatLayout = bindView(R.id.we_chat_layout);
        userIcon = bindView(R.id.user_icon);
        userName = bindView(R.id.person_login_tv);

        userLayout.setOnClickListener(this);
        qqLayout.setOnClickListener(this);
        weboLayout.setOnClickListener(this);
        weChatLayout.setOnClickListener(this);

    }

    @Override
    public void initData() {

        qqLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 1) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    i++;
                } else {
                    qq.authorize();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (qq.getDb().isValid()) {
            //开线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //让线程休眠2秒，防止时间太短，图片没有加载出来
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //把图片头像网址和名字存到集合中，通过handler传出去
                    List<String> list = new ArrayList<>();
                    String icon = qq.getDb().getUserIcon();
                    String name = qq.getDb().getUserName();
                    list.add(icon);
                    list.add(name);
                    Message msg = new Message();
                    msg.obj = list;
                    handler.sendMessage(msg);
                }
            }).start();
        }
    }

    //获取网络并设置图片的方法，用Picasso会报错
    private void setImage(ImageView iconIv, String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(url,
                new com.android.volley.Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        //设置获取的头像图片，改成圆角的
                        userIcon.setImageDrawable(new MyDraweable(response));
                    }
                }, 0, 0, Bitmap.Config.ARGB_8888, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        requestQueue.add(imageRequest);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.fragment_person_collect_layout:
                intent = new Intent(context, MyCollectActivity.class);
                break;
            case R.id.user_name_login_layout:
                intent = new Intent(context, LoginActivity.class);
                break;
            case R.id.webo_login_layout:
                intent = new Intent(context,LoginActivity.class);
                break;
            case R.id.we_chat_layout:
                intent = new Intent(context, LoginActivity.class);
                break;
        }
        startActivity(intent);

    }
}
