package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.utils.ImageUtils;

/**
 * Created by Administrator on 2016/8/1.
 */
public class CartAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context mContext;
    List<CartBean> mCareList;
    CarViewHolder mCarViewHolder;
    boolean isMore;

    public CartAdapter(Context context, List<CartBean> list) {
        mContext = context;
        mCareList = new ArrayList<CartBean>();
        mCareList.addAll(list);

    }


    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder holder = new CarViewHolder(inflater.inflate(R.layout.item_cart, parent, false));
//        setListener();
        return holder;
    }

    private void setListener() {

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof CarViewHolder) {
            mCarViewHolder = (CarViewHolder) holder;
            final CartBean cart = mCareList.get(position);
            mCarViewHolder.mCartBox.setChecked(cart.isChecked());
            if (cart.getGoods()!= null) {
                ImageUtils.setGoodThumb(mContext, mCarViewHolder.ivCartThumb,cart.getGoods().getGoodsThumb());
                mCarViewHolder.tvShuliang.setText("("+cart.getCount()+")");
                mCarViewHolder.tvName.setText(cart.getUserName());
                mCarViewHolder.tvjiage.setText(cart.getGoods().getCurrencyPrice());
            }
            mCarViewHolder.ivadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCarViewHolder.tvShuliang.setText(String.valueOf(cart.getCount()+1));
                    mCareList.add(cart);
                }
            });
            mCarViewHolder.ivdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCarViewHolder.tvShuliang.setText(String.valueOf(cart.getCount()-1));
                    mCareList.remove(cart);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCareList != null ? mCareList.size() : 0;
    }

    //判断 如果数组中不为空 ，则先清空，在添加
    public void initData(ArrayList<CartBean> list) {
        if (mCareList != null) {
            mCareList.clear();
        }
        mCareList.addAll(list);
        notifyDataSetChanged();
    }

    public void initItem(List<CartBean> list) {
        if (mCareList != null) {
            mCareList.clear();
        }
        mCareList.addAll(list);
        notifyDataSetChanged();
    }

    // 下拉刷新的数组添加
    public void addItem(List<CartBean> list) {
        mCareList.addAll(list);
        notifyDataSetChanged();
    }

    class CarViewHolder extends ViewHolder {
        ImageView ivCartThumb;
        TextView tvShuliang;
        TextView tvName;
        TextView tvjiage;
        ImageView ivadd;
        ImageView ivdelete;
        CheckBox mCartBox;
        public CarViewHolder(View itemView) {
            super(itemView);
            mCartBox = (CheckBox) itemView.findViewById(R.id.cb_cart_selected);
            ivCartThumb = (ImageView) itemView.findViewById(R.id.iv_cart_good_avatar);
            tvShuliang = (TextView) itemView.findViewById(R.id.tv_cart_text);
            tvName = (TextView) itemView.findViewById(R.id.tv_cart_good_name);
            tvjiage = (TextView) itemView.findViewById(R.id.tv_car_jiage);
            ivadd = (ImageView) itemView.findViewById(R.id.iv_cart_add);
            ivdelete = (ImageView) itemView.findViewById(R.id.iv_cart_delete);

        }
    }

//    class UpCartShangPing implements View.OnClickListener {
//
//        @Override
//        public void onClick(View view) {
//
//        }
//    }



}
