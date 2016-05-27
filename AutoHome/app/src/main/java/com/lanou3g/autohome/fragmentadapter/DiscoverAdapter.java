package com.lanou3g.autohome.fragmentadapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.fragmentbean.DiscoverBean;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/13.
 */
public class DiscoverAdapter extends BaseAdapter {

    private DiscoverBean discoverBean;
    private Context context;

    public DiscoverAdapter(Context context) {
        this.context = context;
    }

    public void setDiscoverBean(DiscoverBean discoverBean) {
        this.discoverBean = discoverBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return discoverBean == null?0:discoverBean.getResult().getGoodslist().getList().size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.discover_item,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(discoverBean.getResult().getGoodslist().getList().get(position).getLogo()).into(viewHolder.itemIv);
        viewHolder.titleTv.setText(discoverBean.getResult().getGoodslist().getList().get(position).getTitle());
        viewHolder.shortTitleTv.setText(discoverBean.getResult().getGoodslist().getList().get(position).getShorttitle());
        viewHolder.priceTv.setText(discoverBean.getResult().getGoodslist().getList().get(position).getPrice());
        viewHolder.fctpriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.fctpriceTv.setText(discoverBean.getResult().getGoodslist().getList().get(position).getFctprice());
        return convertView;
    }

    class ViewHolder{
        ImageView itemIv;
        TextView titleTv,shortTitleTv,priceTv,fctpriceTv;
        public ViewHolder(View itemView){
            itemIv = (ImageView) itemView.findViewById(R.id.discover_item_iv);
            titleTv = (TextView) itemView.findViewById(R.id.discover_item_title_tv);
            shortTitleTv = (TextView) itemView.findViewById(R.id.discover_item_short_title_tv);
            priceTv = (TextView) itemView.findViewById(R.id.discover_item_price_tv);
            fctpriceTv = (TextView) itemView.findViewById(R.id.discover_item_fctprice_tv);

        }
    }

}
