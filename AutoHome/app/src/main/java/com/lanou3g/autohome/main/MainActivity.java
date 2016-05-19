package com.lanou3g.autohome.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseActivity;
import com.lanou3g.autohome.fragment.DiscoverFragment;
import com.lanou3g.autohome.fragment.FindCarFragment;
import com.lanou3g.autohome.fragment.ForumFragment;
import com.lanou3g.autohome.fragment.PersonFragment;
import com.lanou3g.autohome.fragment.RecommendFragment;
import com.lanou3g.autohome.recommendadapter.DrawerLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/5/9.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, DrawerLayout.DrawerListener {

    private Button recommendRb, forumRb, findcarRb, discoverRb, personRb;
    private DrawerLayout drawerLayout;
    private DrawerLayoutBroadcast drawerLayoutBroadcast;
    private ListView listView;
    private List<String> videoAllDrawerLayoutList, newsFlashBrandList, newsFlashLevelList;
    private DrawerLayoutAdapter drawerLayoutAdapter;
    private TextView drawerLayoutTitleTv;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //初始化组件
        listView = bindView(R.id.drawerlayout_lv);
        drawerLayoutAdapter = new DrawerLayoutAdapter(this);
        drawerLayoutTitleTv = bindView(R.id.drawerlayout_title_tv);

        recommendRb = bindView(R.id.main_recommend_rb);
        forumRb = bindView(R.id.main_forum_rb);
        findcarRb = bindView(R.id.main_findcar_rb);
        discoverRb = bindView(R.id.main_discover_rb);
        personRb = bindView(R.id.main_person_rb);
        drawerLayout = bindView(R.id.video_all_right_drawerlayout);

        //注册打开抽屉的广播
        drawerLayoutBroadcast = new DrawerLayoutBroadcast();
        IntentFilter newsflashIntentFilter = new IntentFilter();
        newsflashIntentFilter.addAction("com.lanou3g.autohome.OPENNDRAWERLAYOUT");
        registerReceiver(drawerLayoutBroadcast, newsflashIntentFilter);
        //设置监听器
        recommendRb.setOnClickListener(this);
        forumRb.setOnClickListener(this);
        findcarRb.setOnClickListener(this);
        discoverRb.setOnClickListener(this);
        personRb.setOnClickListener(this);
        drawerLayout.setDrawerListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //DrawerLayoutAdapter.ViewHolder holder= (DrawerLayoutAdapter.ViewHolder) view.getTag();
                Toast.makeText(MainActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //向视频页面的抽屉里添加数据
    private void initVideoDrawerLayoutData() {
        videoAllDrawerLayoutList = new ArrayList<>();
        videoAllDrawerLayoutList.add("全部");
        videoAllDrawerLayoutList.add("原创");
        videoAllDrawerLayoutList.add("实拍");
        videoAllDrawerLayoutList.add("试车");
        videoAllDrawerLayoutList.add("花边");
        videoAllDrawerLayoutList.add("事件");
        videoAllDrawerLayoutList.add("新车");
        videoAllDrawerLayoutList.add("广告");
        videoAllDrawerLayoutList.add("技术");
        videoAllDrawerLayoutList.add("二手车");
    }

    //向newsFlash页面品牌抽屉中添加数据
    private void initNewsFlashBrandDrawerLayoutData() {
        newsFlashBrandList = new ArrayList<>();
        newsFlashBrandList.add("安迪");
        newsFlashBrandList.add("阿斯顿马丁");
        newsFlashBrandList.add("标致");
        newsFlashBrandList.add("本田");
        newsFlashBrandList.add("宝马");
        newsFlashBrandList.add("北京");
        newsFlashBrandList.add("奔驰");
        newsFlashBrandList.add("布加迪");
    }

    //向newsFlash页面级别抽屉中添加数据
    private void initNewsFlashLevelDrawerLayoutData() {
        newsFlashLevelList = new ArrayList<>();
        newsFlashLevelList.add("不限");
        newsFlashLevelList.add("微型车");
        newsFlashLevelList.add("小型车");
        newsFlashLevelList.add("紧凑车型");
        newsFlashLevelList.add("中型车");
        newsFlashLevelList.add("中大型车");
        newsFlashLevelList.add("大型车");
        newsFlashLevelList.add("跑车");
}

    @Override
    protected void initData() {
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

        //设置五个RadioButton的点击事件替换Fragment
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

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    class DrawerLayoutBroadcast extends BroadcastReceiver {
        //设置页面的抽屉是从右边弹出来的
        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getIntExtra("drawerlayout",0);

            switch (type) {
                case 1:
                    drawerLayoutTitleTv.setText("视频分类");
                    initVideoDrawerLayoutData();
                    drawerLayoutAdapter.setList(videoAllDrawerLayoutList);
                    listView.setAdapter(drawerLayoutAdapter);
                    break;
                case 2:
                    drawerLayoutTitleTv.setText("选择品牌");
                    initNewsFlashBrandDrawerLayoutData();
                    drawerLayoutAdapter.setList(newsFlashBrandList);
                    listView.setAdapter(drawerLayoutAdapter);
                    break;
                case 3:
                    drawerLayoutTitleTv.setText("选择级别");
                    initNewsFlashLevelDrawerLayoutData();
                    drawerLayoutAdapter.setList(newsFlashLevelList);
                    listView.setAdapter(drawerLayoutAdapter);
                    break;
            }
            drawerLayout.openDrawer(Gravity.RIGHT);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消video页面抽屉的广播
        unregisterReceiver(drawerLayoutBroadcast);

    }
}
