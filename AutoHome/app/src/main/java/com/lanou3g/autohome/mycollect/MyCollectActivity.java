package com.lanou3g.autohome.mycollect;

import com.lanou3g.autohome.Collect;
import com.lanou3g.autohome.CollectDao;
import com.lanou3g.autohome.CollectSingle;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseActivity;

/**
 * Created by dllo on 16/5/26.
 */
public class MyCollectActivity extends BaseActivity {
//    private SQLiteDatabase db;
//    private DaoMaster daoMaster;
//    private DaoSession daoSession;
    private CollectDao collectDao;


    @Override
    protected int getLayout() {
        return R.layout.aty_my_collect;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //单例的PersonDao在所有类当中我们保证只是用同一个PersonDao
        collectDao = CollectSingle.getInstance().getCollectDao();
        Collect collect = new Collect();
        //collectDao.insert();
    }
}
