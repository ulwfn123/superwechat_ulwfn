package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.GoodDetailsActivity;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.raw.FooterViewHolder;
import cn.ucai.fulicenter.utils.ImageUtils;

/**
 * Created by Administrator on 2016/8/1.
 */
public class GoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<NewGoodBean> mGoodList;
    GoodViewHolder mGoodViewHolder;
    FooterViewHolder mFooterViewHolder;
    boolean isMore;
    String footerString;

    public GoodAdapter(Context context, List<NewGoodBean> list) {
        mContext = context;
        mGoodList = new ArrayList<NewGoodBean>();
        mGoodList.addAll(list);

    }

    public String getFooterString() {
        return footerString;
    }

    public void setFooterString(String footerString) {
        this.footerString = footerString;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {  // 判断类型
            case I.TYPE_FOOTER:
                holder = new FooterViewHolder(inflater.inflate(R.layout.item_footer, parent, false));
                break;
            case I.TYPE_ITEM:
                holder = new GoodViewHolder(inflater.inflate(R.layout.ltem_good, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GoodViewHolder) {
            mGoodViewHolder = (GoodViewHolder) holder;
            NewGoodBean good = mGoodList.get(position);
            ImageUtils.setGoodThumb(mContext,mGoodViewHolder.ivGoodThumb,good.getGoodsThumb() );
            mGoodViewHolder.tvGoodName.setText(good.getGoodsName());
            mGoodViewHolder.tvGoodPrice.setText(good.getPromotePrice());
            mGoodViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, GoodDetailsActivity.class));
                }
            });
        }
    }
    //  判断 下标的类型
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mGoodList!=null?mGoodList.size()+1:1;
    }
    //判断 如果数组中不为空 ，则先清空，在添加
    public void initData(ArrayList<NewGoodBean> list) {
        if (mGoodList != null) {
            mGoodList.clear();
        }
        mGoodList.addAll(list);
        soryByAddTime();
        notifyDataSetChanged();
    }
    // 下拉刷新的数组添加
    public void addItem(ArrayList<NewGoodBean> list) {
        mGoodList.addAll(list);
        soryByAddTime();
        notifyDataSetChanged();
    }

    //   新品中的对象的排序方法 ，， 按照时间排序
    private void soryByAddTime() {
        Collections.sort(mGoodList, new Comparator<NewGoodBean>() {
            @Override
            public int compare(NewGoodBean goodLeft, NewGoodBean goodRight) {
                return (int) (Long.valueOf(goodRight.getAddTime()) - Long.valueOf(goodLeft.getAddTime()));
            }
        });
    }

    class GoodViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView ivGoodThumb;
        TextView tvGoodName;
        TextView tvGoodPrice;

        public GoodViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.layout_good);
            ivGoodThumb = (ImageView) itemView.findViewById(R.id.niv_good_thumb);
            tvGoodName = (TextView) itemView.findViewById(R.id.tv_good_name);
            tvGoodPrice = (TextView) itemView.findViewById(R.id.tv_good_prove);
        }
    }
}
