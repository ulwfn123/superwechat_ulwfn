package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.CategoryChildActivity;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.utils.ImageUtils;

/**
 * Created by Administrator on 2016/8/4.
 */
public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    List<CategoryGroupBean> mGroupList;
    List<ArrayList<CategoryChildBean>> mChildList;

    public CategoryAdapter(Context mContext,
                           List<CategoryGroupBean> mGroupList,
                           List<ArrayList<CategoryChildBean>> mChildList) {
        this.mContext = mContext;
        this.mGroupList = new ArrayList<CategoryGroupBean>();
        this.mGroupList.addAll(mGroupList);
        this.mChildList = new ArrayList<ArrayList<CategoryChildBean>>();
        this.mChildList.addAll(mChildList);
    }

    @Override
    public int getGroupCount() {
        return mGroupList!=null?mGroupList.size():0;
    }

    @Override
    public int getChildrenCount(int i) {
        return mChildList.get(i).size();
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        if (mGroupList!=null) return mGroupList.get(groupPosition);
        return null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int chuldPosition) {
        if (mChildList.get(groupPosition)!=null
                &&mChildList.get(groupPosition).get(chuldPosition)!=null)
            return mChildList.get(groupPosition).get(chuldPosition);
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    // 分类  的 大类型数据
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder houder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_group, null);
            houder = new GroupViewHolder();
            // 属性初始化
            houder.ivIndicator = (ImageView) view.findViewById(R.id.iv_group_indicator);
            houder.tvGroupName = (TextView) view.findViewById(R.id.tv_group_name);
            houder.ivGroupThumb = (ImageView) view.findViewById(R.id.iv_group_thumb);

        } else {
            houder = (GroupViewHolder) view.getTag();
        }
        CategoryGroupBean group = getGroup(i);
        ImageUtils.setGroupCategoryImage(mContext, houder.ivGroupThumb, group.getImageUrl());
        Log.i("main", "CategoryAdapter赋值的"+group.getName());
        houder.tvGroupName.setText(group.getName());
        if (b) {
            houder.ivIndicator.setImageResource(R.drawable.expand_off);
        } else {
            houder.ivIndicator.setImageResource(R.drawable.expand_on);
        }
        view.setTag(houder);
        return view;
    }
    // 分类  的小类型数据
    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder holder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_cateogry_child, null);
            holder = new ChildViewHolder();
            holder.layoutCategoryChild = (RelativeLayout) view.findViewById(R.id.layout_category_child);
            holder.ivCategoryChildThumb = (ImageView) view.findViewById(R.id.iv_category_child_thumb);
            holder.tvCategoryChildName = (TextView) view.findViewById(R.id.tv_category_child_name);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        final CategoryChildBean child = getChild(groupPosition, childPosition);
        if (child != null) {
            ImageUtils.setChildCategoryImage(mContext, holder.ivCategoryChildThumb, child.getImageUrl());
//            ImageUtils.setGoodThumb(mContext, holder.ivCategoryChildThumb, child.getImageUrl());
            holder.tvCategoryChildName.setText(child.getName());
            holder.layoutCategoryChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, CategoryChildActivity.class)
                        .putExtra(I.CategoryChild.CAT_ID, child.getId())
                        .putExtra(I.CategoryGroup.NAME,mGroupList.get(groupPosition))
                        .putExtra("childList",mChildList.get(groupPosition)));

                }
            });
        }
        view.setTag(holder);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public void addAll(List<CategoryGroupBean> mGroupList, List<ArrayList<CategoryChildBean>> mChildList) {
        this.mGroupList.clear();
        this.mGroupList.addAll(mGroupList);
        this.mChildList.clear();
        this.mChildList.addAll(mChildList);
        notifyDataSetChanged();
    }

    class GroupViewHolder {
        ImageView ivGroupThumb;
        TextView tvGroupName;
        ImageView ivIndicator;
    }

    class ChildViewHolder {
        RelativeLayout layoutCategoryChild;
        ImageView ivCategoryChildThumb;
        TextView tvCategoryChildName;
    }
}
