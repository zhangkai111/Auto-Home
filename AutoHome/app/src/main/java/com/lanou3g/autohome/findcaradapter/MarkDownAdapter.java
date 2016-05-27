package com.lanou3g.autohome.findcaradapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.findcarbean.MarkDownBean;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/20.
 */
public class MarkDownAdapter extends BaseAdapter {

    private Context context;
    private MarkDownBean markDownBean;

    public MarkDownAdapter(Context context) {
        this.context = context;
    }

    public void setMarkDownBean(MarkDownBean markDownBean) {
        this.markDownBean = markDownBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return markDownBean == null ? 0 : markDownBean.getResult().getCarlist().size();
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.findcar_markdown_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(markDownBean.getResult().getCarlist().get(position).getSpecpic()).into(viewHolder.imageView);
        viewHolder.titleTv.setText(markDownBean.getResult().getCarlist().get(position).getSeriesname());
        viewHolder.specnameTv.setText(markDownBean.getResult().getCarlist().get(position).getSpecname());
        viewHolder.nowPriceTv.setText(markDownBean.getResult().getCarlist().get(position).getDealer().getSpecprice());
        viewHolder.primePriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.primePriceTv.setText(markDownBean.getResult().getCarlist().get(position).getFctprice());
        double money = Double.parseDouble((markDownBean.getResult().getCarlist().get(position).getFctprice()));
        double money1 = Double.parseDouble((markDownBean.getResult().getCarlist().get(position).getDealerprice()));
        viewHolder.markDownTv.setText("降" + (money - money1) + "万");
        viewHolder.cityTv.setText(markDownBean.getResult().getCarlist().get(position).getDealer().getCity());
        viewHolder.shortNameTv.setText(markDownBean.getResult().getCarlist().get(position).getDealer().getShortname());
        viewHolder.orderrange.setText(markDownBean.getResult().getCarlist().get(position).getOrderrange());
        return convertView;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTv, specnameTv, nowPriceTv, primePriceTv, markDownTv, cityTv, shortNameTv, orderrange;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.findcar_markdown_item_iv);
            titleTv = (TextView) itemView.findViewById(R.id.findcar_markdown_item_title_tv);
            specnameTv = (TextView) itemView.findViewById(R.id.findcar_markdown_item_title_specname_tv);
            nowPriceTv = (TextView) itemView.findViewById(R.id.findcar_markdown_item_now_price_tv);
            primePriceTv = (TextView) itemView.findViewById(R.id.findcar_markdown_item_prime_price_tv);
            markDownTv = (TextView) itemView.findViewById(R.id.findcar_markdown_item_markdown_tv);
            cityTv = (TextView) itemView.findViewById(R.id.findcar_markdown_item_city_tv);
            shortNameTv = (TextView) itemView.findViewById(R.id.findcar_markdown_item_shortname_tv);
            orderrange = (TextView) itemView.findViewById(R.id.findcar_markdown_item_orderrange_tv);

        }
    }
}