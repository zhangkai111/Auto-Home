package com.lanou3g.autohome;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lanou3g.autohome.base.MyApplication;

/**
 * Created by dllo on 16/5/28.
 */
public class CollectSingle {

    private SQLiteDatabase db;//数据库
    private DaoMaster daoMaster;//管理者
    private DaoSession daoSession;//会话
    private CollectDao collectDao;//数据库内相应表的操作对象
    private Context context;
    private DaoMaster.DevOpenHelper helper;


    public DaoMaster.DevOpenHelper getHelper() {
        if (helper == null) {
            helper = new DaoMaster.DevOpenHelper(context, "Collect.db", null);
        }
        return helper;
    }


    public SQLiteDatabase getDb() {
        if (db == null) {
            db = getHelper().getWritableDatabase();
        }
        return db;
    }

    public DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(getDb());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }

    public CollectDao getCollectDao() {
        if (collectDao == null) {
            collectDao = getDaoSession().getCollectDao();
        }
        return collectDao;
    }

    private static CollectSingle ourInstance = new CollectSingle();

    public static CollectSingle getInstance() {
        return ourInstance;
    }

    private CollectSingle() {

        context = MyApplication.getContext();
    }

}
