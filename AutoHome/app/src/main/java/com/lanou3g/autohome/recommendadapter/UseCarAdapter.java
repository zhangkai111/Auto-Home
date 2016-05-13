package com.lanou3g.autohome.recommendadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.recommendbean.UseCarBean;
import com.lanou3g.autohome.recommendfragment.RecyclerViewOnClickListener;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/13.
 */
public class UseCarAdapter extends RecyclerView.Adapter {

    private UseCarBean useCarBean;
    private Context context;
    private RecyclerViewOnClickListener recyclerViewOnClickListener;
    public static final int THREE_IMAGE = 10;

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener recyclerViewOnClickListener) {
        this.recyclerViewOnClickListener = recyclerViewOnClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return useCarBean.getResult().getNewslist().get(position).getMediatype();
    }

    public UseCarAdapter(Context context) {
        this.context = context;
    }

    public void setUseCarBean(UseCarBean useCarBean) {
        this.useCarBean = useCarBean;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == THREE_IMAGE){
            View view = LayoutInflater.from(context).inflate(R.layout.usecar_item_three,parent,false);
            viewHolder = new ThreeImageViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.usecar_item_one,parent,false);
            viewHolder = new OneImageViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == THREE_IMAGE){
            String url = useCarBean.getResult().getNewslist().get(position).getIndexdetail();
            String[] args = url.split("㊣");
            ThreeImageViewHolder threeImageViewHolder = (ThreeImageViewHolder) holder;
            threeImageViewHolder.threeTitleTv.setText(useCarBean.getResult().getNewslist().get(position).getTitle());
            threeImageViewHolder.threeTimeTv.setText(useCarBean.getResult().getNewslist().get(position).getTime());
            threeImageViewHolder.threeReplyTv.setText(useCarBean.getResult().getNewslist().get(position).getReplycount()+ "评论");
            setImage(threeImageViewHolder.threeFirstIv, args[0]);
            setImage(threeImageViewHolder.threeTwoIv, args[1]);
            setImage(threeImageViewHolder.threeThreeIv, args[2]);
        }else {
            String imgUrl = useCarBean.getResult().getNewslist().get(position).getSmallpic();
            OneImageViewHolder oneImageViewHolder = (OneImageViewHolder) holder;
            oneImageViewHolder.useCarTitleTv.setText(useCarBean.getResult().getNewslist().get(position).getTitle());
            oneImageViewHolder.useCarOneTimeTv.setText(useCarBean.getResult().getNewslist().get(position).getTime());
            oneImageViewHolder.useCarOneReplyTv.setText(useCarBean.getResult().getNewslist().get(position).getReplycount() + "评论");
            setImage(oneImageViewHolder.oneIv, imgUrl);
        }
        if (recyclerViewOnClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    //int ids = useCarBean.getResult().getNewslist().get(pos).getId();
                    recyclerViewOnClickListener.onClick(pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return useCarBean == null ? 0 : useCarBean.getResult().getNewslist().size();
    }

    private void setImage(ImageView iconIv , String url){
        Picasso.with(context).load(url).into(iconIv);
    }


    class OneImageViewHolder extends RecyclerView.ViewHolder{

        ImageView oneIv;
        TextView useCarTitleTv,useCarOneTimeTv,useCarOneReplyTv;
        public OneImageViewHolder(View itemView) {
            super(itemView);
            oneIv = (ImageView) itemView.findViewById(R.id.usecar_item_one_iv);
            useCarTitleTv = (TextView) itemView.findViewById(R.id.usecar_item_one_title_tv);
            useCarOneTimeTv = (TextView) itemView.findViewById(R.id.usecar_item_one_time_tv);
            useCarOneReplyTv = (TextView) itemView.findViewById(R.id.usecar_item_one_replycount_tv);
        }
    }
    class ThreeImageViewHolder extends RecyclerView.ViewHolder{

        ImageView threeFirstIv,threeTwoIv,threeThreeIv;
        TextView threeTitleTv,threeTimeTv,threeReplyTv;
        public ThreeImageViewHolder(View itemView) {
            super(itemView);
            threeFirstIv = (ImageView) itemView.findViewById(R.id.usecar_item_three_first_iv);
            threeTwoIv = (ImageView) itemView.findViewById(R.id.usecar_item_three_two_iv);
            threeThreeIv = (ImageView) itemView.findViewById(R.id.usecar_item_three_three_iv);
            threeTitleTv = (TextView) itemView.findViewById(R.id.usecar_item_three_title_tv);
            threeTimeTv = (TextView) itemView.findViewById(R.id.usecar_item_three_time_tv);
            threeReplyTv = (TextView) itemView.findViewById(R.id.usecar_item_three_replycount_tv);
        }
    }
}
