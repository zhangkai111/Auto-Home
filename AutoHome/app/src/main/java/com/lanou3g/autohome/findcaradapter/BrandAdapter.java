package com.lanou3g.autohome.findcaradapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.findcarbean.BrandItemBean;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * 继承BaseExpandableListAdapter
 * Created by dllo on 16/5/19.
 */
public class BrandAdapter extends BaseExpandableListAdapter {

    private BrandItemBean brandItemBean;
    private Context context;

    public BrandAdapter(Context context) {
        this.context = context;
    }

    public void setBrandItemBean(BrandItemBean brandItemBean) {
        this.brandItemBean = brandItemBean;
        notifyDataSetChanged();
    }

    //获取有几个组
    @Override
    public int getGroupCount() {
        return brandItemBean == null ? 0 : brandItemBean.getResult().getBrandlist().size();
    }

    //获取每个组中的子布局的条数
    @Override
    public int getChildrenCount(int groupPosition) {
        return brandItemBean == null ? 0 : brandItemBean.getResult().getBrandlist().get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return brandItemBean.getResult().getBrandlist().get(groupPosition).getLetter();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return brandItemBean.getResult().getBrandlist().get(groupPosition).getList().get(childPosition).getName();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    //绑定组布局的布局文件
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderTitle viewHolderTitle = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.brand_item_title,parent,false);
            viewHolderTitle = new ViewHolderTitle(convertView);
            convertView.setTag(viewHolderTitle);
        }else {
            viewHolderTitle = (ViewHolderTitle) convertView.getTag();
        }
        viewHolderTitle.titleTv.setText(brandItemBean.getResult().getBrandlist().get(groupPosition).getLetter());
        return convertView;

    }

    //绑定子布局的布局文件
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.brand_item,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(brandItemBean.getResult().getBrandlist().get(groupPosition).getList().get(childPosition).getImgurl()).into(viewHolder.brandIv);
        viewHolder.brandTv.setText(brandItemBean.getResult().getBrandlist().get(groupPosition).getList().get(childPosition).getName());

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //自布局的ViewHolder
    class ViewHolder{
        ImageView brandIv;
        TextView brandTv;
        public ViewHolder(View itemView){
            brandIv = (ImageView) itemView.findViewById(R.id.brand_item_brand_iv);
            brandTv = (TextView) itemView.findViewById(R.id.brand_item_brand_tv);
        }
    }
    //组布局的ViewHolder
    class ViewHolderTitle{

        TextView titleTv;
        public ViewHolderTitle(View title){
            titleTv = (TextView) title.findViewById(R.id.brand_item_title);
        }
    }
    //遍历所有的数据
    public int getPosition(int sectionIndex){
        for (int i = 0;i<getGroupCount();i++){
            String sortStr = brandItemBean.getResult().getBrandlist().get(i).getLetter();
            char firstCar = sortStr.toUpperCase().charAt(0);
            if (firstCar == sectionIndex){
                return i;
            }
        }
        return -1;
    }

}
