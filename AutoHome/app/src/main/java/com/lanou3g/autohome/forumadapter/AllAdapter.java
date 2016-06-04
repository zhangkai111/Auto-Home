package com.lanou3g.autohome.forumadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.forumbean.AllBean;
import com.lanou3g.autohome.recommendfragment.RecyclerViewOnClickListener;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/12.
 */
public class AllAdapter extends RecyclerView.Adapter {

    private AllBean allBean;
    private Context context;
    //RecyclerView没有点击事件，如果要用点击事件需要自定义接口
    private RecyclerViewOnClickListener recyclerViewOnClickListener;

    //提供set方法
    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener recyclerViewOnClickListener) {
        this.recyclerViewOnClickListener = recyclerViewOnClickListener;
    }

    public AllAdapter(Context context) {
        this.context = context;
    }

    public void setAllBean(AllBean allBean) {
        this.allBean = allBean;
        notifyDataSetChanged();
    }
    

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(context).inflate(R.layout.all_item, parent, false);
        viewHolder = new AllViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        AllViewHolder allViewHolder = (AllViewHolder) holder;
        allViewHolder.allTitleTv.setText(allBean.getResult().getList().get(position).getTitle());
        allViewHolder.allBbsnameTv.setText(allBean.getResult().getList().get(position).getBbsname());
        allViewHolder.allReplycountTv.setText(allBean.getResult().getList().get(position).getReplycounts() + "回");
        Picasso.with(context).load(allBean.getResult().getList().get(position).getSmallpic()).error(R.mipmap.ic_launcher).into(allViewHolder.imageView);

        //传递点击Item的位置
        if (recyclerViewOnClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    int ids = allBean.getResult().getList().get(pos).getTopicid();
                    recyclerViewOnClickListener.onClick(ids);
                }
            });
        }
    }

    class AllViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView allTitleTv,allBbsnameTv,allReplycountTv;
        public AllViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.all_item_iv);
            allTitleTv = (TextView) itemView.findViewById(R.id.all_item_title_tv);
            allBbsnameTv = (TextView) itemView.findViewById(R.id.all_item_bbsname_tv);
            allReplycountTv = (TextView) itemView.findViewById(R.id.all_item_replycounts_tv);
        }
    }

    @Override
    public int getItemCount() {
        return allBean == null ? 0 : allBean.getResult().getList().size();
    }
}
