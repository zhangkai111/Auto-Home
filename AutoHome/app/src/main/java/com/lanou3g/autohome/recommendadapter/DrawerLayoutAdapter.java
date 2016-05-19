package com.lanou3g.autohome.recommendadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lanou3g.autohome.R;

import java.util.List;

/**
 * Created by dllo on 16/5/14.
 */
public class DrawerLayoutAdapter extends BaseAdapter {

    List<String> list;
    private Context context;


    public DrawerLayoutAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.drawerlayout_listview_item,parent,false);
            viewHolder = new ViewHolder(convertView);
            //把初始化好的ViewHolder放到convertView里
            convertView.setTag(viewHolder);
        }else {
            //证明convertView不是新的
            //它已经注入过布局,并且有holder
            //把convertView里的holder取出来
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        //从数据集合里取出对应position位置的一条数据
        viewHolder.textView.setText(list.get(position));
        return convertView;
    }


    class ViewHolder {
        TextView textView;
        public ViewHolder (View itemView){
            textView = (TextView) itemView.findViewById(R.id.drawerlayout_listview_item_tv);
        }
    }
}
