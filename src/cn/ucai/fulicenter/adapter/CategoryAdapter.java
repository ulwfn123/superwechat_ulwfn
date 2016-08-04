package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.R;
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
        mGroupList.addAll(mGroupList);
        this.mChildList = new ArrayList<ArrayList<CategoryChildBean>>();
        mChildList.addAll(mChildList);
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;
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
    // 下载分类  的 大类型数据
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder houder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_group, null);
            houder = new GroupViewHolder();
            CategoryGroupBean group = getGroup(i);
            ImageUtils.setGroupCategoryImage(mContext, houder.ivGroupThumb, group.getImageUrl());
            houder.tvGroupName.setText(group.getName());
            houder.ivIndicator.setImageResource(R.drawable.expand_off);
            view.setTag(houder);
        } else {
            houder = (GroupViewHolder) view.getTag();
        }
        return view;
    }
    // 下载 分类  的小类型数据
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder holder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_cateogry_child, null);
            holder = new ChildViewHolder();
            final CategoryChildBean child = getChild(groupPosition, childPosition);
            if (child != null) {
                ImageUtils.setChildCategoryImage(mContext, holder.ivCategoryChildThumb, child.getImageUrl());
                holder.tvCategoryChildName.setText(child.getName());
            }
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
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
