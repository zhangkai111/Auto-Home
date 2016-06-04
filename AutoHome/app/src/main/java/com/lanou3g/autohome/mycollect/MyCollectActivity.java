package com.lanou3g.autohome.mycollect;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lanou3g.autohome.Collect;
import com.lanou3g.autohome.CollectDao;
import com.lanou3g.autohome.CollectSingle;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseActivity;
import com.lanou3g.autohome.Detail;

import java.util.List;

/**
 * Created by dllo on 16/5/26.
 */
public class MyCollectActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    //    private SQLiteDatabase db;
    //    private DaoMaster daoMaster;
    //    private DaoSession daoSession;
    private CollectDao collectDao;
    private MyCollectAdapter myCollectAdapter;
    private List<Collect> collectList;
    private ListView listView;
    private TextView returnTv;

    @Override
    protected int getLayout() {
        return R.layout.aty_my_collect;
    }

    @Override
    protected void initView() {
        listView = bindView(R.id.aty_my_collect_lv);
        returnTv = bindView(R.id.aty_my_collect_return_tv);
        returnTv.setOnClickListener(this);
    }

    //在收藏的页面进入详情,取消收藏再退出详情页后,收藏页面执行onRestart方法,再从数据库中查一遍数据
    @Override
    protected void onRestart() {
        super.onRestart();
        collectDao = CollectSingle.getInstance().getCollectDao();
        collectList = collectDao.queryBuilder().list();
        myCollectAdapter.setCollectList(collectList);
        listView.setAdapter(myCollectAdapter);
    }

    @Override
    protected void initData() {
        myCollectAdapter = new MyCollectAdapter(this);
        //单例的PersonDao在所有类当中我们保证只是用同一个PersonDao
        collectDao = CollectSingle.getInstance().getCollectDao();
        //从数据库中取出数据，并存到集合当中
        collectList = collectDao.queryBuilder().list();
        myCollectAdapter.setCollectList(collectList);

        listView.setAdapter(myCollectAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aty_my_collect_return_tv:
                finish();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, Detail.class);
        intent.putExtra("url",collectList.get(position).getUrl());
        intent.putExtra("collect",collectList.get(position));
        startActivity(intent);
    }
}
