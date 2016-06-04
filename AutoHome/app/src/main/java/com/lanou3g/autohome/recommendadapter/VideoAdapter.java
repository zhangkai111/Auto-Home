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
import com.lanou3g.autohome.recommendbean.VideoBean;
import com.lanou3g.autohome.recommendfragment.RecyclerViewOnClickListener;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/5/12.
 */
public class VideoAdapter extends BaseAdapter {

    private VideoBean videoBean;
    private Context context;

    public VideoAdapter(Context context) {
        this.context = context;
    }

    public void setVideoBean(VideoBean videoBean) {
        this.videoBean = videoBean;
        notifyDataSetChanged();
    }

    private void setImage(ImageView iconIv , String url){
        Picasso.with(context).load(url).into(iconIv);
    }

    @Override
    public int getCount() {
        return videoBean == null ? 0 : videoBean.getResult().getList().size();
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
        VideoViewHolder videoViewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.video_item,null);
            videoViewHolder = new VideoViewHolder(convertView);
            convertView.setTag(videoViewHolder);
        }else {
            videoViewHolder  = (VideoViewHolder) convertView.getTag();
        }
        videoViewHolder.videoTitle.setText(videoBean.getResult().getList().get(position).getTitle());
        videoViewHolder.videoTime.setText(videoBean.getResult().getList().get(position).getTime());
        videoViewHolder.videoPlaycount.setText(videoBean.getResult().getList().get(position).getPlaycount() + "播放");
        String imgUrl = videoBean.getResult().getList().get(position).getSmallimg();
        setImage(videoViewHolder.imageView,imgUrl);
        return convertView;
    }

    class VideoViewHolder{
        ImageView imageView;
        TextView videoTitle,videoTime,videoPlaycount;
        public VideoViewHolder(View itemView) {
            imageView = (ImageView) itemView.findViewById(R.id.video_item_iv);
            videoTitle = (TextView) itemView.findViewById(R.id.video_item_title_tv);
            videoTime = (TextView) itemView.findViewById(R.id.video_item_time_tv);
            videoPlaycount = (TextView) itemView.findViewById(R.id.video_item_playcount_tv);
        }
    }
}
