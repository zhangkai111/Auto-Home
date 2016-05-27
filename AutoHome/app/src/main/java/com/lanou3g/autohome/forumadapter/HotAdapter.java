package com.lanou3g.autohome.forumadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.forumbean.HotBean;

/**
 * Created by dllo on 16/5/19.
 */
public class HotAdapter extends BaseAdapter {

    private Context context;
    private HotBean hotBean;

    public HotAdapter(Context context) {
        this.context = context;
    }

    public void setHotBean(HotBean hotBean) {
        this.hotBean = hotBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return hotBean == null ? 0 : hotBean.getResult().getList().size();
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.forum_hot_item,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.itemTitle.setText(hotBean.getResult().getList().get(position).getTitle());
        viewHolder.itemContent.setText(hotBean.getResult().getList().get(position).getBbsname());
        viewHolder.itemReplyCount.setText(hotBean.getResult().getList().get(position).getReplycounts() + "回帖");
        String url = hotBean.getResult().getList().get(position).getPostdate();
        String time = url.substring(5,16);
        viewHolder.itemTime.setText(time);
        return convertView;
    }

    class ViewHolder{

        TextView itemTitle,itemContent,itemTime,itemReplyCount;
        public ViewHolder(View itemView){
            itemTitle = (TextView) itemView.findViewById(R.id.form_hot_item_title_tv);
            itemContent = (TextView) itemView.findViewById(R.id.form_hot_item_content_tv);
            itemReplyCount = (TextView) itemView.findViewById(R.id.form_hot_item_replycontent);
            itemTime = (TextView) itemView.findViewById(R.id.form_hot_item_time_tv);
        }
    }

}
