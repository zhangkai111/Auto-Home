package com.lanou3g.autohome.mycollect;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lanou3g.autohome.Collect;

/**
 * Created by dllo on 16/5/27.
 */
public class MyCollectAdapter extends BaseAdapter {

    private Context context;
    private Collect collect;
    private SQLiteDatabase database;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


}
