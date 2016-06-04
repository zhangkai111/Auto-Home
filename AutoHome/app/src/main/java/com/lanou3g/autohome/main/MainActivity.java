package com.lanou3g.autohome.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseActivity;
import com.lanou3g.autohome.fragment.DiscoverFragment;
import com.lanou3g.autohome.fragment.FindCarFragment;
import com.lanou3g.autohome.fragment.ForumFragment;
import com.lanou3g.autohome.fragment.PersonFragment;
import com.lanou3g.autohome.fragment.RecommendFragment;
import com.lanou3g.autohome.recommendadapter.DrawerLayoutAdapter;
import com.lanou3g.autohome.utils.ExampleUtil;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

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
    public static boolean isForeground = false;
    private List<Fragment> fragments;
    private FragmentManager manager;
    private FragmentTransaction transaction;

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
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


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
        //设置首页为推荐页面
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        fragments = new ArrayList<>();
        fragments.add(new RecommendFragment());
        fragments.add(new ForumFragment());
        fragments.add(new FindCarFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new PersonFragment());
        //首次进入的时候，让第一个fragment显示，也就是推荐页显示
        manager.beginTransaction()
                .add(R.id.main_replace__framelayout, fragments.get(0))
                .add(R.id.main_replace__framelayout, fragments.get(1))
                .add(R.id.main_replace__framelayout, fragments.get(2))
                .add(R.id.main_replace__framelayout, fragments.get(3))
                .add(R.id.main_replace__framelayout, fragments.get(4))
                .show(fragments.get(0))
                .hide(fragments.get(1))
                .hide(fragments.get(2))
                .hide(fragments.get(3))
                .hide(fragments.get(4))
                .commit();


//        //在onCreate中,替换首页
//        //保证第一次进入界面,显示的不是占位布局
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.main_activity_framelayout, new RecommendFragment());
//        fragmentTransaction.commit();

        registerMessageReceiver();  // used for receive msg
    }

    @Override
    public void onClick(View v) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        //设置五个RadioButton的点击事件替换Fragment
        switch (v.getId()) {
            case R.id.main_recommend_rb:
//                fragmentTransaction.replace(R.id.main_activity_framelayout, new RecommendFragment());
                changeFragment(0);
                break;

            case R.id.main_forum_rb:
//                fragmentTransaction.replace(R.id.main_activity_framelayout, new ForumFragment());
                changeFragment(1);
                break;
            case R.id.main_findcar_rb:
//                fragmentTransaction.replace(R.id.main_activity_framelayout, new FindCarFragment());
                changeFragment(2);
                break;
            case R.id.main_discover_rb:
//                fragmentTransaction.replace(R.id.main_activity_framelayout, new DiscoverFragment());
                changeFragment(3);
                break;
            case R.id.main_person_rb:
//                fragmentTransaction.replace(R.id.main_activity_framelayout, new PersonFragment());
                changeFragment(4);
                break;
        }

        transaction.commit();

    }

    //设置所有的fragment都是隐藏的，点击那个位置的fragment就让那个位置的fragment显示
    private void changeFragment(int pos){
        manager.beginTransaction()
                .hide(fragments.get(0))
                .hide(fragments.get(1))
                .hide(fragments.get(2))
                .hide(fragments.get(3))
                .hide(fragments.get(4))
                .show(fragments.get(pos))
                .commit();
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
            int type = intent.getIntExtra("drawerlayout", 0);

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
        unregisterReceiver(mMessageReceiver);

    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init(){
        JPushInterface.init(getApplicationContext());
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }





    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }

            }
        }
    }

}
