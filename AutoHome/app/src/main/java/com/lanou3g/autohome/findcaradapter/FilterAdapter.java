package com.lanou3g.autohome.findcaradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.findcarbean.FilterBean;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/20.
 *
 1.ListView

 2.ExpandableListView

 3.GridView

 4.WebView
 */
public class FilterAdapter extends BaseAdapter {

    private FilterBean filterBean;
    private Context context;

    public FilterAdapter(Context context) {
        this.context = context;
    }

    public void setFilterBean(FilterBean filterBean) {
        this.filterBean = filterBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return filterBean == null ? 0 : filterBean.getResult().getSeries().size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.filter_item,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(filterBean.getResult().getSeries().get(position).getThumburl()).into(viewHolder.thumbIv);
        viewHolder.seriesNameTv.setText(filterBean.getResult().getSeries().get(position).getSeriesname());
        viewHolder.priceTv.setText(filterBean.getResult().getSeries().get(position).getPricerange());
        if (filterBean.getResult().getSeries().get(position).getCornericon() != null) {
            viewHolder.cornerTV.setText(filterBean.getResult().getSeries().get(position).getCornericon());
        }
        return convertView;

    }

    class ViewHolder{
        ImageView thumbIv,cornerIv;
        TextView seriesNameTv,priceTv,cornerTV;
        public ViewHolder(View itemView){
            cornerIv = (ImageView) itemView.findViewById(R.id.filter_item_corner_iv);
            thumbIv = (ImageView) itemView.findViewById(R.id.filter_item_thumb_iv);
            seriesNameTv = (TextView) itemView.findViewById(R.id.filter_item_series_name_tv);
            priceTv = (TextView) itemView.findViewById(R.id.filter_item_price_tv);
            cornerTV = (TextView) itemView.findViewById(R.id.filter_item_corner_tv);
        }
    }
}
