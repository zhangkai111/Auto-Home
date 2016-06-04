package com.lanou3g.autohome.mycollect;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.Collect;
import com.lanou3g.autohome.R;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/27.
 */
public class MyCollectAdapter extends BaseAdapter {

    private Context context;
    private List<Collect> collectList;

    public MyCollectAdapter(Context context) {
        this.context = context;
    }

    public void setCollectList(List<Collect> collectList) {
        this.collectList = collectList;
        Log.d("MyCollectAdapter", "collectList.size():" + collectList.size());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return collectList == null ? 0 : collectList.size();
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.my_collect_item,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Collect collect = collectList.get(position);
        viewHolder.titleTv.setText(collect.getTitle());
        viewHolder.timeTv.setText(collect.getTime());

        //判断收藏的有几张图片
        if (collect.getImageUrl().length() != 0){
            Picasso.with(context).load(collect.getImageUrl()).error(R.mipmap.icon_error).into(viewHolder.imageView);
        }else {
            viewHolder.imageView.setImageResource(R.mipmap.icon_error);
        }

        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView titleTv,timeTv;
        public ViewHolder(View itemView){
            imageView = (ImageView) itemView.findViewById(R.id.collect_item_iv);
            titleTv = (TextView) itemView.findViewById(R.id.collect_item_title);
            timeTv = (TextView) itemView.findViewById(R.id.collect_item_time);
        }
    }

}
