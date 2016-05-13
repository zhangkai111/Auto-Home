package com.lanou3g.autohome.recommendadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.recommendbean.VideoBean;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/12.
 */
public class VideoAdapter extends RecyclerView.Adapter {

    private VideoBean videoBean;
    private Context context;

    public VideoAdapter(Context context) {
        this.context = context;
    }

    public void setVideoBean(VideoBean videoBean) {
        this.videoBean = videoBean;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(context).inflate(R.layout.video_item,parent,false);
        viewHolder = new VideoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
        videoViewHolder.videoTitle.setText(videoBean.getResult().getList().get(position).getTitle());
        videoViewHolder.videoTime.setText(videoBean.getResult().getList().get(position).getTime());
        videoViewHolder.videoPlaycount.setText(videoBean.getResult().getList().get(position).getPlaycount() + "播放");
        String imgUrl = videoBean.getResult().getList().get(position).getSmallimg();
        setImage(videoViewHolder.imageView,imgUrl);
    }

    @Override
    public int getItemCount() {
        return videoBean == null ? 0 : videoBean.getResult().getList().size();
    }

    private void setImage(ImageView iconIv , String url){
        Picasso.with(context).load(url).into(iconIv);
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView videoTitle,videoTime,videoPlaycount;
        public VideoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.video_item_iv);
            videoTitle = (TextView) itemView.findViewById(R.id.video_item_title_tv);
            videoTime = (TextView) itemView.findViewById(R.id.video_item_time_tv);
            videoPlaycount = (TextView) itemView.findViewById(R.id.video_item_playcount_tv);
        }
    }
}
