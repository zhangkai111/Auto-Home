package com.lanou3g.autohome.main;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.fragment.DiscoverFragment;
import com.lanou3g.autohome.fragment.FindCarFragment;
import com.lanou3g.autohome.fragment.ForumFragment;
import com.lanou3g.autohome.fragment.PersonFragment;
import com.lanou3g.autohome.fragment.RecommendFragment;

/**
 * Created by dllo on 16/5/9.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private Button recommendRb,forumRb,findcarRb,discoverRb,personRb;

    @Override
    protected void onCreate(Bundle arg0) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(arg0);
        setContentView(R.layout.activity_main);

        //初始化组件
        recommendRb = (Button) findViewById(R.id.main_recommend_rb);
        forumRb = (Button) findViewById(R.id.main_forum_rb);
        findcarRb = (Button) findViewById(R.id.main_findcar_rb);
        discoverRb = (Button) findViewById(R.id.main_discover_rb);
        personRb = (Button) findViewById(R.id.main_person_rb);

        //设置监听器
        recommendRb.setOnClickListener(this);
        forumRb.setOnClickListener(this);
        findcarRb.setOnClickListener(this);
        discoverRb.setOnClickListener(this);
        personRb.setOnClickListener(this);

        //在onCreate中,替换首页
        //保证第一次进入界面,显示的不是占位布局
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.main_activity_framelayout, new RecommendFragment());
        fragmentTransaction.commit();



    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (v.getId()) {
            case R.id.main_recommend_rb:
                fragmentTransaction.replace(R.id.main_activity_framelayout, new RecommendFragment());

                break;

            case R.id.main_forum_rb:
                fragmentTransaction.replace(R.id.main_activity_framelayout, new ForumFragment());

                break;
            case R.id.main_findcar_rb:
                fragmentTransaction.replace(R.id.main_activity_framelayout, new FindCarFragment());

                break;
            case R.id.main_discover_rb:
                fragmentTransaction.replace(R.id.main_activity_framelayout, new DiscoverFragment());

                break;
            case R.id.main_person_rb:
                fragmentTransaction.replace(R.id.main_activity_framelayout, new PersonFragment());

                break;
        }

        fragmentTransaction.commit();

    }

}
