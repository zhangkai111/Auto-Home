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
import com.lanou3g.autohome.recommendbean.NewestBean;
import com.lanou3g.autohome.recommendfragment.RecyclerViewOnClickListener;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/10.
 */
public class NewestAdapter extends BaseAdapter {

    private NewestBean newestBean;
    private Context context;

    public NewestAdapter(Context context) {
        this.context = context;
    }

    public void setNewestBean(NewestBean newestBean) {
        this.newestBean = newestBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return newestBean != null ? newestBean.getResult().getNewslist().size() : 0;

    }

    @Override
    public int getViewTypeCount() {
        return 7;
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
    public int getItemViewType(int position) {
        return newestBean.getResult().getNewslist().get(position).getMediatype();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        ThreeImageViewHolder threeImageViewHolder = null;
        OneImageViewHolder oneImageViewHolder = null;

        //通过ViewType来判断加载不同布局
        if (convertView == null) {
            switch (viewType) {
                case 6:
                    convertView = LayoutInflater.from(context).inflate(R.layout.newest_item_three, parent, false);
                    threeImageViewHolder = new ThreeImageViewHolder(convertView);
                    convertView.setTag(threeImageViewHolder);
                    break;
                default:
                    convertView = LayoutInflater.from(context).inflate(R.layout.newest_item_one, parent, false);
                    oneImageViewHolder = new OneImageViewHolder(convertView);
                    convertView.setTag(oneImageViewHolder);
                    break;
            }
        } else {
            switch (viewType) {
                case 6:
                    threeImageViewHolder = (ThreeImageViewHolder) convertView.getTag();
                    break;
                default:
                    oneImageViewHolder = (OneImageViewHolder) convertView.getTag();
                    break;
            }

        }
        switch (viewType) {
            case 6:
                String url = newestBean.getResult().getNewslist().get(position).getIndexdetail();
                String[] args = url.split("㊣");
                String[] args1 = args[2].split(",");
                threeImageViewHolder.titleTv.setText(newestBean.getResult().getNewslist().get(position).getTitle());
                threeImageViewHolder.timeTv.setText(newestBean.getResult().getNewslist().get(position).getTime());
                setImage(threeImageViewHolder.firstIv, args1[0]);
                setImage(threeImageViewHolder.twoIv, args1[1]);
                setImage(threeImageViewHolder.threeIv, args1[2]);
                threeImageViewHolder.replyTv.setText(newestBean.getResult().getNewslist().get(position).getReplycount() + "图片");
                break;
            default:
                //获取网络图片的网址
                String imageUrl = newestBean.getResult().getNewslist().get(position).getSmallpic();
                //设置数据
                oneImageViewHolder.titleTv.setText(newestBean.getResult().getNewslist().get(position).getTitle());
                oneImageViewHolder.timeTv.setText(newestBean.getResult().getNewslist().get(position).getTime());
                if (newestBean.getResult().getNewslist().get(position).getMediatype() == 3) {
                    oneImageViewHolder.replyTv.setText(newestBean.getResult().getNewslist().get(position).getReplycount() + "播放");
                } else {
                    oneImageViewHolder.replyTv.setText(newestBean.getResult().getNewslist().get(position).getReplycount() + "评论");
                }
                setImage(oneImageViewHolder.iconIv, imageUrl);
                break;
        }
        return convertView;
    }


    //获取网络并设置图片的方法
    private void setImage(ImageView iconIv, String url) {
        Picasso.with(context).load(url).into(iconIv);
    }


    //一张图片的ViewHolder
    class OneImageViewHolder {
        TextView titleTv, timeTv, replyTv;
        ImageView iconIv;

        public OneImageViewHolder(View itemView) {
            titleTv = (TextView) itemView.findViewById(R.id.newest_item_one_title_tv);
            timeTv = (TextView) itemView.findViewById(R.id.newest_item_one_time_tv);
            replyTv = (TextView) itemView.findViewById(R.id.newest_item_one_replycount_tv);
            iconIv = (ImageView) itemView.findViewById(R.id.newest_item_one_iv);
        }
    }

    //三张图的ViewHolder
    class ThreeImageViewHolder {
        TextView titleTv, timeTv, replyTv;
        ImageView firstIv, twoIv, threeIv;

        public ThreeImageViewHolder(View itemView) {
            titleTv = (TextView) itemView.findViewById(R.id.newest_item_three_title_tv);
            timeTv = (TextView) itemView.findViewById(R.id.newest_item_three_time_tv);
            replyTv = (TextView) itemView.findViewById(R.id.newest_item_three_three_replycount_tv);
            firstIv = (ImageView) itemView.findViewById(R.id.newest_item_three_first_iv);
            twoIv = (ImageView) itemView.findViewById(R.id.newest_item_three_two_iv);
            threeIv = (ImageView) itemView.findViewById(R.id.newest_item_three_three_iv);
        }
    }
}