package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.DemoHXSDKHelper;
import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.GoodDetailsActivity;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.task.DownloadCollectCountTask;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by Administrator on 2016/8/1.
 */
public class CollectAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final String TAG = CollectAdapter.class.getSimpleName();
    Context mContext;
    List<CollectBean> mCollectList;
    CollectViewHolder mCollectViewHolder;
    FooterViewHolder mFooterViewHolder;
    boolean isMore;
    String footerString;
    int sortBy;

    public CollectAdapter(Context context, List<CollectBean> list) {
        mContext = context;
        mCollectList = new ArrayList<CollectBean>();
        mCollectList.addAll(list);

    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
        notifyDataSetChanged();
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
                holder = new CollectViewHolder(inflater.inflate(R.layout.ltem_collect, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof CollectViewHolder) {
            mCollectViewHolder = (CollectViewHolder) holder;
            final CollectBean collect = mCollectList.get(position);
            ImageUtils.setGoodThumb(mContext, mCollectViewHolder.ivGoodThumb, collect.getGoodsThumb());
            mCollectViewHolder.tvGoodName.setText(collect.getGoodsName());
            Log.e(TAG, "good        =123112" + collect.toString());

            mCollectViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext,
                            GoodDetailsActivity.class)
                            .putExtra(D.GoodDetails.KEY_ENGLISH_NAME, collect.getGoodsId()));
                }
            });
            //删除收藏的商品
            mCollectViewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (DemoHXSDKHelper.getInstance().isLogined()) {
                        Log.e(TAG, "121111111111111111+" + collect.getGoodsId() + "删除的名称" + FuliCenterApplication.getInstance().getUserName());
                        OkHttpUtils2<MessageBean> utils = new OkHttpUtils2<MessageBean>();
                        utils.setRequestUrl(I.REQUEST_DELETE_COLLECT)
                                .addParam(I.Collect.USER_NAME, FuliCenterApplication.getInstance().getUserName())
                                .addParam(I.Collect.GOODS_ID, String.valueOf(collect.getGoodsId()))
                                .targetClass(MessageBean.class)
                                .execute(new OkHttpUtils2.OnCompleteListener<MessageBean>() {
                                    @Override
                                    public void onSuccess(MessageBean result) {
                                        Log.e(TAG, "result=aaaaaaaaaaaaaaaaaa" + result);
                                        if (result != null && result.isSuccess()) {
                                            mCollectList.remove(collect);
                                            new DownloadCollectCountTask(mContext, FuliCenterApplication.getInstance().getUserName());
                                            notifyDataSetChanged();
                                        } else {
                                            Log.e(TAG, "delete fail ");
                                        }
                                        Toast.makeText(mContext, result.getMsg(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(String error) {
                                        Log.e(TAG, "error=" + error);
                                    }
                                });
                    } else {
                        Log.e(TAG, "不是登录状态或者链接不上服务器");
                    }
                }
            });

            Log.e(TAG, "good.getId" + collect.getId());
        }
        if (holder instanceof FooterViewHolder) {
            mFooterViewHolder = (FooterViewHolder) holder;
            mFooterViewHolder.tvFooter.setText(footerString);
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
        return mCollectList != null ? mCollectList.size() + 1 : 1;
    }

    //判断 如果数组中不为空 ，则先清空，在添加
    public void initData(ArrayList<CollectBean> list) {
        if (mCollectList != null) {
            mCollectList.clear();
        }
        mCollectList.addAll(list);
        notifyDataSetChanged();
    }

    // 下拉刷新的数组添加
    public void addItem(ArrayList<CollectBean> list) {
        mCollectList.addAll(list);
        notifyDataSetChanged();
    }

    class CollectViewHolder extends ViewHolder {
        LinearLayout layout;
        ImageView ivGoodThumb;
        TextView tvGoodName;
        ImageView ivDelete;

        public CollectViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.layout_good_collect);
            ivGoodThumb = (ImageView) itemView.findViewById(R.id.niv_good_thumb);
            tvGoodName = (TextView) itemView.findViewById(R.id.tv_good_name);
            ivDelete = (ImageView) itemView.findViewById(R.id.iv_collect_delete);
        }
    }
}
