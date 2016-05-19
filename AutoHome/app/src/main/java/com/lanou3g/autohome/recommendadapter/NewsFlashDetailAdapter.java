package com.lanou3g.autohome.recommendadapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.recommendbean.NewsFlashDetailBean;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/17.
 */
public class NewsFlashDetailAdapter extends BaseAdapter {

    private NewsFlashDetailBean newsFlashDetailBean;
    private Context context;
//    public static final int ATTACHTYPEONE = 1;//详情页的item图片是一张的
//    public static final int ATTACHTYPETWO = 0;//详情页的item图片是两张的


    public NewsFlashDetailAdapter(Context context) {
        this.context = context;
    }

    public void setNewsFlashDetailBean(NewsFlashDetailBean newsFlashDetailBean) {
        this.newsFlashDetailBean = newsFlashDetailBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return newsFlashDetailBean == null ? 0 : newsFlashDetailBean.getResult().getMessagelist().size();
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
            //绑定item布局
            convertView = LayoutInflater.from(context).inflate(R.layout.newsflash_detail_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //newsFlashDetail页面的item设置数据
        Picasso.with(context).load(newsFlashDetailBean.getResult().getMessagelist().get(position).getHeadimg()).into(viewHolder.userIv);
        viewHolder.nameTv.setText(newsFlashDetailBean.getResult().getMessagelist().get(position).getAuthorname());
        Picasso.with(context).load(newsFlashDetailBean.getResult().getMessagelist().get(position).getAttachments().get(0).getPicurl()).into(viewHolder.detailIv);

        viewHolder.timeTv.setText(newsFlashDetailBean.getResult().getMessagelist().get(position).getPublishtime());
        viewHolder.contentTv.setText(newsFlashDetailBean.getResult().getMessagelist().get(position).getContent());
        return convertView;
    }

    class ViewHolder {
        ImageView userIv, detailIv;
        TextView nameTv, timeTv, contentTv;

        public ViewHolder(View itemView) {
            userIv = (ImageView) itemView.findViewById(R.id.newsflash_detail_item_iv);
            detailIv = (ImageView) itemView.findViewById(R.id.newsflash_detail_item_detail_iv);
            nameTv = (TextView) itemView.findViewById(R.id.newsflash_detail_item_name_tv);
            timeTv = (TextView) itemView.findViewById(R.id.newsflash_detail_item_time_tv);
            contentTv = (TextView) itemView.findViewById(R.id.newsflash_detail_item_content_tv);

        }
    }

}
