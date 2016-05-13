package com.lanou3g.autohome.recommendadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.recommendbean.NewestBean;
import com.lanou3g.autohome.recommendfragment.RecyclerViewOnClickListener;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/10.
 */
public class NewestAdapter extends RecyclerView.Adapter {

    private NewestBean newestBean;
    private RecyclerViewOnClickListener recyclerViewOnClickListener;
    private Context context;
    public static final int THREE_IMAGE = 6;

    @Override
    public int getItemViewType(int position) {
        return newestBean.getResult().getNewslist().get(position).getMediatype();
    }

    public NewestAdapter(Context context) {
        this.context = context;
    }

    public void setNewestBean(NewestBean newestBean) {
        this.newestBean = newestBean;
        notifyDataSetChanged();
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener recyclerViewOnClickListener) {
        this.recyclerViewOnClickListener = recyclerViewOnClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        //通过viewType来绑定布局
        if (viewType == THREE_IMAGE){
            View threeImageView = LayoutInflater.from(context).inflate(R.layout.newest_item_three, parent, false);
            viewHolder = new ThreeImageViewHolder(threeImageView);

        }else {
            View oneImageView = LayoutInflater.from(context).inflate(R.layout.newest_item_one, parent, false);
            viewHolder = new OneImageViewHolder(oneImageView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        //通过viewType判断是一张图片还是三张图片
        //如果viewType是6,说明是三张图片的,否则是一张图片的
        if (viewType == THREE_IMAGE) {
            String url = newestBean.getResult().getNewslist().get(position).getIndexdetail();
            String[] args = url.split("㊣");
            String[] args1 = args[2].split(",");
            ThreeImageViewHolder threeImageViewHolder = (ThreeImageViewHolder) holder;
            threeImageViewHolder.titleTv.setText(newestBean.getResult().getNewslist().get(position).getTitle());
            threeImageViewHolder.timeTv.setText(newestBean.getResult().getNewslist().get(position).getTime());
            if (newestBean.getResult().getNewslist().get(position).getMediatype() == 3) {
                threeImageViewHolder.replyTv.setText(newestBean.getResult().getNewslist().get(position).getReplycount() + "播放");
            }else {
                threeImageViewHolder.replyTv.setText(newestBean.getResult().getNewslist().get(position).getReplycount() + "评论");
            }
            SetImage(threeImageViewHolder.firstIv, args1[0]);
            SetImage(threeImageViewHolder.twoIv, args1[1]);
            SetImage(threeImageViewHolder.threeIv, args1[2]);
        }else {
            //获取网络图片的网址
            String imageUrl = newestBean.getResult().getNewslist().get(position).getSmallpic();
            OneImageViewHolder oneImageViewHolder = (OneImageViewHolder) holder;
            //设置数据
            oneImageViewHolder.titleTv.setText(newestBean.getResult().getNewslist().get(position).getTitle());
            oneImageViewHolder.timeTv.setText(newestBean.getResult().getNewslist().get(position).getTime());
            if (newestBean.getResult().getNewslist().get(position).getMediatype() == 3) {
                oneImageViewHolder.replyTv.setText(newestBean.getResult().getNewslist().get(position).getReplycount() + "播放");
            }else {
                oneImageViewHolder.replyTv.setText(newestBean.getResult().getNewslist().get(position).getReplycount() + "评论");
            }
            SetImage(oneImageViewHolder.iconIv, imageUrl);
        }
        if (recyclerViewOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    int ids = newestBean.getResult().getNewslist().get(pos).getId();
                    recyclerViewOnClickListener.onClick(ids);
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return newestBean != null ? newestBean.getResult().getNewslist().size() : 0;
    }


    //获取网络并设置图片的方法
    private void SetImage(ImageView iconIv,String url) {

//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
//            @Override
//            public Bitmap getBitmap(String url) {
//                return null;
//            }
//            @Override
//            public void putBitmap(String url, Bitmap bitmap) {
//
//            }
//        });
//        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iconIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
//        imageLoader.get(url,listener);
        Picasso.with(context).load(url).into(iconIv);
        //可以设置加载失败时的图片,或者正在加载时的图片
//        Picasso.with(this).load("http://www2.autoimg.cn/newsdfs/g16/M13/73/8C/1024x512_0_autohomecar__wKjBx1ctTwuAMmaRAAI-ReHh1jQ977.jpg").placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);

    }
    //一张图片的ViewHolder
    class OneImageViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv, timeTv, replyTv;
        ImageView iconIv;

        public OneImageViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.newest_item_one_title_tv);
            timeTv = (TextView) itemView.findViewById(R.id.newest_item_one_time_tv);
            replyTv = (TextView) itemView.findViewById(R.id.newest_item_one_replycount_tv);
            iconIv = (ImageView) itemView.findViewById(R.id.newest_item_one_iv);
        }
    }

    //三张图的ViewHolder
    class ThreeImageViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv, timeTv, replyTv;
        ImageView firstIv, twoIv, threeIv;

        public ThreeImageViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.newest_item_three_title_tv);
            timeTv = (TextView) itemView.findViewById(R.id.newest_item_three_time_tv);
            replyTv = (TextView) itemView.findViewById(R.id.newest_item_three_three_replycount_tv);
            firstIv = (ImageView) itemView.findViewById(R.id.newest_item_three_first_iv);
            twoIv = (ImageView) itemView.findViewById(R.id.newest_item_three_two_iv);
            threeIv = (ImageView) itemView.findViewById(R.id.newest_item_three_three_iv);
        }
    }
}