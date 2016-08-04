package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.BoutiqueChildActivity;
import cn.ucai.fulicenter.activity.BoutiqueFragment;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.view.FooterViewHolder;
import cn.ucai.fulicenter.utils.ImageUtils;

/**
 * Created by Administrator on 2016/8/1.
 */
public class BoutiqueAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context mContext;
    List<BoutiqueBean> mBoutiqueList;
    BoutiqueViewHolder mBoutupueViewHolder;
//    FooterViewHolder mFooterViewHolder;
    boolean isMore;
    String footerString;



    public BoutiqueAdapter(Context context, List<BoutiqueBean> list) {
        mContext = context;
        mBoutiqueList = new ArrayList<BoutiqueBean>();
        mBoutiqueList.addAll(list);

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
                holder = new BoutiqueViewHolder(inflater.inflate(R.layout.item_boutique, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof BoutiqueViewHolder) {
            mBoutupueViewHolder = (BoutiqueViewHolder) holder;
            final BoutiqueBean boutique = mBoutiqueList.get(position);
            ImageUtils.setGoodThumb(mContext, mBoutupueViewHolder.ivBoutiqueThumb,boutique.getImageurl() );
            mBoutupueViewHolder.tvTitle.setText(boutique.getTitle());
            mBoutupueViewHolder.tvName.setText(boutique.getName());
            mBoutupueViewHolder.tvDesc.setText(boutique.getDescription());
            mBoutupueViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, BoutiqueChildActivity.class)
                            .putExtra(D.Boutique.KEY_GOODS_ID,boutique.getId())
                            .putExtra(D.Boutique.KEY_NAME,boutique.getName())
                    );
                }
            });
        }
//        if (holder instanceof FooterViewHolder) {
//            mFooterViewHolder = (FooterViewHolder) holder;
//            mFooterViewHolder.tvFooter.setText(footerString);
//        }
    }
    //  判断 下标的类型
    @Override
    public int getItemViewType(int position) {
//        if (position == getItemCount() - 1) {
//            return I.TYPE_FOOTER;
//        } else {
            return I.TYPE_ITEM;
//        }
    }

    @Override
    public int getItemCount() {
//        return mBoutiqueList !=null? mBoutiqueList.size()+1:1;
        return mBoutiqueList !=null? mBoutiqueList.size():0;
    }
    //判断 如果数组中不为空 ，则先清空，在添加
    public void initData(ArrayList<BoutiqueBean> list) {
        if (mBoutiqueList != null) {
            mBoutiqueList.clear();
        }
        mBoutiqueList.addAll(list);
        notifyDataSetChanged();
    }
    // 下拉刷新的数组添加
    public void addItem(ArrayList<BoutiqueBean> list) {
        mBoutiqueList.addAll(list);
        notifyDataSetChanged();
    }


    class BoutiqueViewHolder extends ViewHolder {
        RelativeLayout layout;
        ImageView ivBoutiqueThumb;
        TextView tvTitle;
        TextView tvName;
        TextView tvDesc;

        public BoutiqueViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout_boutique_item);
            ivBoutiqueThumb = (ImageView) itemView.findViewById(R.id.ivBoutiqueImg);
            tvTitle = (TextView) itemView.findViewById(R.id.tvBoutiqueTitle);
            tvName = (TextView) itemView.findViewById(R.id.tvBoutiqueName);
            tvDesc = (TextView) itemView.findViewById(R.id.tvBoutiqueDescription);
        }
    }
}
