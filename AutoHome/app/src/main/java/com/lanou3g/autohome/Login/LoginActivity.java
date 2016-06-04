package com.lanou3g.autohome.Login;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseActivity;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by samsung on 2016/5/29.
 */
public class LoginActivity extends BaseActivity {

    private RelativeLayout qqLay;
    private int i = 1;

    @Override
    protected int getLayout() {
        ShareSDK.initSDK(this);//初始化ShareSDK
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        qqLay = bindView(R.id.aty_login_qq_login_lay);

        qqLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.setPlatformActionListener(new PlatformActionListener() {
                    //登录成功时调用的方法，这里登录成功时让他结束当前页面，返回个人页
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        finish();
                    }
                    //登录失败时调用的方法
                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }
                    //取消登录时调用的方法
                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                //切换用户
                qq.authorize();
                qq.showUser(null);
            }
        });

    }

    @Override
    protected void initData() {

    }
}
