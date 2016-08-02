package cn.ucai.fulicenter.activity;

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

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.GoodAdapter;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.Utils;

/**
 * Created by Administrator on 2016/8/1.
 */
public class NewGoodFragment extends Fragment {
    private static final String TAG = NewGoodFragment.class.getSimpleName();
    FuliCenterManActivity mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    GoodAdapter mAdapter;
    List<NewGoodBean> mGoodList;
    TextView tvHint;
    int pageId = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = (FuliCenterManActivity) getContext();
        View layout = View.inflate(mContext, R.layout.fragment_new_good, null);
        mGoodList = new ArrayList<NewGoodBean>();
        initData();
        initView(layout);
        serListener();
        return  layout;
    }

    private void serListener() {
        setPullDownRefreshListener(); //下拉
        setPullUpRefreshListener(); // 上拉
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
                int f = mGridLayoutManager.findFirstVisibleItemPosition();
                int l = mGridLayoutManager.findLastVisibleItemPosition();
                Log.e(TAG, "f=" + f + "停止位置的下标" + l);
                lastItemPosition = mGridLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    //下拉刷新
    private void setPullDownRefreshListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tvHint.setVisibility(View.VISIBLE);
                pageId =1;
                initData();
            }
        });
    }
    //  获取数据 ，判断数据下标是否超过集合的长度
    private void initData() {
        findNewGoodList(new OkHttpUtils2.OnCompleteListener<NewGoodBean[]>() {
            @Override
            public void onSuccess(NewGoodBean[] result) {
                Log.e(TAG, "新品的result = " + result);
                mSwipeRefreshLayout.setRefreshing(false);
                tvHint.setVisibility(View.GONE);
                mAdapter.setFooterString(getResources().getString(R.string.load_more));
                mAdapter.setMore(true);
                if (result != null) {
                    Log.e(TAG, "新品的result长度 = " + result.length);
                    ArrayList<NewGoodBean> goodBeanArrayList = Utils.array2List(result);
                    mAdapter.initData(goodBeanArrayList);
                    if (goodBeanArrayList.size() < I.PAGE_SIZE_DEFAULT) { // 判断加载最后的下标
                        mAdapter.setMore(false);
                        mAdapter.setFooterString(getResources().getString(R.string.no_more));
                    }
                }
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, "新品的error = " + error);
                tvHint.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    // 服务端请求下载新品首页商品信息
    private void findNewGoodList(OkHttpUtils2.OnCompleteListener<NewGoodBean[]> listener) {
//        http://10.0.2.2/FuLiCenterServer/Server?request=find_new_boutique_goods&cat_id=0&page_id=0&page_size=10
        OkHttpUtils2<NewGoodBean[]> utils = new OkHttpUtils2<NewGoodBean[]>();
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGood.CAT_ID,String.valueOf(I.CAT_ID))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodBean[].class)
                .execute(listener);
    }

    private void initView(View layout) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.srl_new_good);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,R.color.google_yellow,
                R.color.google_red,R.color.google_green
        );
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_new_good);
        mGridLayoutManager = new GridLayoutManager(mContext, I.COLUM_NUM);  //  数据的初始化，括号后的表示每行表示的view个数
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); //  设置线性布局的排列方式
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new GoodAdapter(mContext, mGoodList);
        mRecyclerView.setAdapter(mAdapter);
        tvHint = (TextView) layout.findViewById(R.id.tv_refresh_hint);  // 图片加载

    }
}
