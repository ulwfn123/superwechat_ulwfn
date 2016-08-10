package cn.ucai.fulicenter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.CartAdapter;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.Utils;

/**
 * Created by Administrator on 2016/8/3.
 */
public class CartFragment extends Fragment {
    private static final String TAG = CartFragment.class.getSimpleName();
    FuliCenterManActivity mContext;
    List<CartBean> mCarList;

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    CartAdapter mAdapter;
    TextView tvHint;

    TextView tvSumPrice,tvSavePrice,tvSuy;
    TextView tvnothing;
    UpdateCartRecriver mReceiver;

    int pageId = 1;
    int action =I.ACTION_DOWNLOAD;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContext = (FuliCenterManActivity) getContext();
        View layout = View.inflate(mContext, R.layout.fragment_cart, null);
        mCarList = new ArrayList<CartBean>();
        initView(layout);
        initData();
        serListener();
        return layout;
    }
    private void serListener() {
        setPullDownRefreshListener(); //下拉
        setPullUpRefreshListener(); // 上拉
        setUpdateCartListerner();

    }
    //   上拉刷新
    private void setPullUpRefreshListener() {
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItemPosition;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int a = RecyclerView.SCROLL_STATE_DRAGGING;
                int b = RecyclerView.SCROLL_STATE_IDLE;
                int c = RecyclerView.SCROLL_STATE_SETTLING;
                Log.e(TAG, "newState = " + newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastItemPosition == mAdapter.getItemCount() - 1) {
                    pageId += I.PAGE_SIZE_DEFAULT;
                    initData();
                }
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int f = mLinearLayoutManager.findFirstVisibleItemPosition();
                int l = mLinearLayoutManager.findLastVisibleItemPosition();
                Log.e(TAG, "f=" + f + "停止位置的下标" + l);
                lastItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                mSwipeRefreshLayout.setEnabled(mLinearLayoutManager.findFirstVisibleItemPosition() == 0);
                if (f == -1  ||  l== - 1) {  //  加载错下标的保护
                    lastItemPosition = mAdapter.getItemCount() - 1;
                }
            }
        });
    }
    //下拉刷新
    private void setPullDownRefreshListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                action = I.ACTION_PULL_DOWN;
                tvHint.setVisibility(View.VISIBLE);
                pageId =1;
                initData();
            }
        });
    }
    //下载数据
    private void initData() {
        final List<CartBean> cartList = FuliCenterApplication.getInstance().getCartList();
        mCarList.clear();
        mCarList.addAll(cartList);
        mSwipeRefreshLayout.setRefreshing(false);
        tvHint.setVisibility(View.GONE);
        mAdapter.setMore(true);
        if (cartList != null&&mCarList.size()>0) {
            Log.e(TAG, "cartList = = " + cartList.size());
            if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                mAdapter.initItem(mCarList);
            } else {
                mAdapter.addItem(mCarList);
            }
            if (mCarList.size() < I.PAGE_SIZE_DEFAULT) {
                mAdapter.setMore(false);
            }
            tvnothing.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            sunPrice();
        } else {
            mAdapter.setMore(false);
            tvnothing.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
        }
    }

    //   属性初始化
    private void initView(View layout) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.srl_car);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,R.color.google_yellow,
                R.color.google_red,R.color.google_green
        );
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); //  设置线性布局的排列方式
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_car);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new CartAdapter(mContext, mCarList);
        mRecyclerView.setAdapter(mAdapter);
        tvHint = (TextView) layout.findViewById(R.id.tv_refresh_hint);  // 图片加载

        tvSumPrice = (TextView) layout.findViewById(R.id.tv_cart_frag_pc);
        tvSavePrice = (TextView) layout.findViewById(R.id.tv_cart_frag_jueyue);
        tvSuy = (TextView) layout.findViewById(R.id.tv_cart_frag_buy);
        tvnothing = (TextView) layout.findViewById(R.id.tv_nothing);
        tvnothing.setVisibility(View.VISIBLE);
    }

    private void sunPrice() {
        if (mCarList != null && mCarList.size() > 0) {
            int sumPrice = 0;
            int rankPrice = 0;
            for (CartBean cart : mCarList) {
                final GoodDetailsBean good = cart.getGoods();
                if (good != null && cart.isChecked()) {
                    sumPrice += convertPrice(good.getCurrencyPrice())*cart.getCount();
                    rankPrice += convertPrice(good.getRankPrice())*cart.getCount();
                }
            }
            tvSumPrice.setText("合计：￥" + sumPrice);
            tvSavePrice.setText("节省：￥" + (sumPrice - rankPrice));
        } else {
            tvSumPrice.setText("合计：￥00.00");
            tvSavePrice.setText("节省：￥00.00");
        }
    }

    private void setUpdateCartListerner() {
        mReceiver = new UpdateCartRecriver();
        IntentFilter filter = new IntentFilter("update_cart_list");
        mContext.registerReceiver(mReceiver,filter);
    }

    private int convertPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }

    class UpdateCartRecriver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
        }
    }

}
