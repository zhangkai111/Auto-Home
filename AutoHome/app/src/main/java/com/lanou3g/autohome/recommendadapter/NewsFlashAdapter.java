package com.lanou3g.autohome.recommendadapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.recommendbean.NewsFlashBean;
import com.lanou3g.autohome.recommendfragment.RecyclerViewOnClickListener;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/11.
 */
public class NewsFlashAdapter extends BaseAdapter {

    private NewsFlashBean newsFlashBean;
    private Context context;
    public static final int STATE = 2;
    public static final int STATEZERO = 0;

    public void setNewsFlashBean(NewsFlashBean newsFlashBean) {
        this.newsFlashBean = newsFlashBean;
        notifyDataSetChanged();
    }

    public NewsFlashAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return newsFlashBean.getResult().getList().get(position).getState();
    }

    @Override
    public int getCount() {
        return newsFlashBean == null ? 0 : newsFlashBean.getResult().getList().size();
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
        ItemViewHolder itemViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.newsflash_item, null);
            itemViewHolder = new ItemViewHolder(convertView);
            convertView.setTag(itemViewHolder);
        }else {
            itemViewHolder = (ItemViewHolder) convertView.getTag();
        }
        int viewType = getItemViewType(position);
        String url = newsFlashBean.getResult().getList().get(position).getBgimage();
        itemViewHolder.typeNameTv.setText(newsFlashBean.getResult().getList().get(position).getTypename());
        if (viewType == STATE) {
            itemViewHolder.stateTv.setBackgroundColor(Color.GRAY);
            itemViewHolder.stateTv.setText("播报结束");
        } else if (viewType == STATEZERO){
            itemViewHolder.stateTv.setBackgroundColor(Color.GREEN);
            itemViewHolder.stateTv.setText("即将播报");
        } else {
            itemViewHolder.stateTv.setBackgroundColor(Color.BLUE);
            itemViewHolder.stateTv.setText("播报中");
        }
        itemViewHolder.titleTv.setText(newsFlashBean.getResult().getList().get(position).getTitle());
        itemViewHolder.timeTv.setText(newsFlashBean.getResult().getList().get(position).getCreatetime());
        itemViewHolder.reviewcountTv.setText(newsFlashBean.getResult().getList().get(position).getReviewcount() + "人浏览");
        setImage(itemViewHolder.imageView, url);

        return convertView;
    }


    //获取网络并设置图片的方法
    private void setImage(ImageView iconIv, String url) {
        Picasso.with(context).load(url).into(iconIv);
    }

    class ItemViewHolder {

        TextView typeNameTv, stateTv, titleTv, reviewcountTv, timeTv;
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            typeNameTv = (TextView) itemView.findViewById(R.id.news_flash_item_typename_tv);
            stateTv = (TextView) itemView.findViewById(R.id.news_flash_item_state_tv);
            titleTv = (TextView) itemView.findViewById(R.id.news_flash_item_title_tv);
            reviewcountTv = (TextView) itemView.findViewById(R.id.news_flash_item_reviewcount_tv);
            timeTv = (TextView) itemView.findViewById(R.id.news_flash_item_time_tv);
            imageView = (ImageView) itemView.findViewById(R.id.news_flash_item_iv);

        }
    }
}
