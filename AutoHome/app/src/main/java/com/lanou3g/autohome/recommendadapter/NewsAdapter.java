package com.lanou3g.autohome.recommendadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.recommendbean.NewsBean;
import com.lanou3g.autohome.recommendfragment.RecyclerViewOnClickListener;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/11.
 */
public class NewsAdapter extends BaseAdapter {

    private NewsBean newsBean;
    private Context context;
    public static final int THREE_IMAGE = 10;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void setNewsBean(NewsBean newsBean) {
        this.newsBean = newsBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return newsBean == null ? 0 : newsBean.getResult().getNewslist().size();

    }

    @Override
    public int getViewTypeCount() {
        return 11;
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
        int viewType = getItemViewType(position);
        ThreeImageViewHolder threeImageViewHolder = null;
        OneImageViewHolder oneImageViewHolder = null;
        if (convertView == null) {
            switch (viewType) {
                case THREE_IMAGE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.news_item_three, parent, false);
                    threeImageViewHolder = new ThreeImageViewHolder(convertView);
                    convertView.setTag(threeImageViewHolder);
                    break;
                default:
                    convertView = LayoutInflater.from(context).inflate(R.layout.news_item_one, parent, false);
                    oneImageViewHolder = new OneImageViewHolder(convertView);
                    convertView.setTag(oneImageViewHolder);
                    break;
            }
        }else {
            switch (viewType){
                case THREE_IMAGE:
                    threeImageViewHolder = (ThreeImageViewHolder) convertView.getTag();
                    break;
                default:
                    oneImageViewHolder = (OneImageViewHolder) convertView.getTag();
                    break;
            }
        }

        switch (viewType){
            case THREE_IMAGE:
                String url = newsBean.getResult().getNewslist().get(position).getIndexdetail();
                String[] args = url.split("㊣");
                threeImageViewHolder.threeTitleTv.setText(newsBean.getResult().getNewslist().get(position).getTitle());
                threeImageViewHolder.threeTimeTv.setText(newsBean.getResult().getNewslist().get(position).getTime());
                threeImageViewHolder.threeReplyTv.setText(newsBean.getResult().getNewslist().get(position).getReplycount() + "评论");
                setImage(threeImageViewHolder.threeFirstIv, args[0]);
                setImage(threeImageViewHolder.threeTwoIv, args[1]);
                setImage(threeImageViewHolder.threeThreeIv, args[2]);
                break;
            default:
                String imgUrl = newsBean.getResult().getNewslist().get(position).getSmallpic();
                oneImageViewHolder.newsOneTitleTv.setText(newsBean.getResult().getNewslist().get(position).getTitle());
                oneImageViewHolder.newsOneTimeTv.setText(newsBean.getResult().getNewslist().get(position).getTime());
                oneImageViewHolder.newsOneReplyTv.setText(newsBean.getResult().getNewslist().get(position).getReplycount()+"评论");
                setImage(oneImageViewHolder.oneIv,imgUrl);
                break;
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return newsBean.getResult().getNewslist().get(position).getMediatype();
    }

    //获取网络并设置图片的方法
    private void setImage(ImageView iconIv,String url) {
        Picasso.with(context).load(url).into(iconIv);

    }

    class OneImageViewHolder{

        ImageView oneIv;
        TextView newsOneTitleTv,newsOneTimeTv,newsOneReplyTv;
        public OneImageViewHolder(View itemView) {
            oneIv = (ImageView) itemView.findViewById(R.id.news_item_one_iv);
            newsOneTitleTv = (TextView) itemView.findViewById(R.id.news_item_one_title_tv);
            newsOneTimeTv = (TextView) itemView.findViewById(R.id.news_item_one_time_tv);
            newsOneReplyTv = (TextView) itemView.findViewById(R.id.news_item_one_replycount_tv);
        }
    }
    class ThreeImageViewHolder{

        ImageView threeFirstIv,threeTwoIv,threeThreeIv;
        TextView threeTitleTv,threeTimeTv,threeReplyTv;
        public ThreeImageViewHolder(View itemView) {
            threeFirstIv = (ImageView) itemView.findViewById(R.id.news_item_three_first_iv);
            threeTwoIv = (ImageView) itemView.findViewById(R.id.news_item_three_two_iv);
            threeThreeIv = (ImageView) itemView.findViewById(R.id.news_item_three_three_iv);
            threeTitleTv = (TextView) itemView.findViewById(R.id.news_item_three_title_tv);
            threeTimeTv = (TextView) itemView.findViewById(R.id.news_item_three_time_tv);
            threeReplyTv = (TextView) itemView.findViewById(R.id.news_item_three_replycount_tv);
        }
    }


}
