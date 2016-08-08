package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.CollectAdapter;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.Utils;
import cn.ucai.fulicenter.view.DisplayUtils;

/**
 * Created by Administrator on 2016/8/1.
 */
public class CollectActivity extends BaseActivity {
    private static final String TAG = CollectActivity.class.getSimpleName();
    CollectActivity mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    CollectAdapter mAdapter;
    List<CollectBean> mGoodList;
    TextView tvHint;
    int pageId = 0;
    int action =I.ACTION_DOWNLOAD ;
    int  catId = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mContext = this;
        setContentView(R.layout.activity_collect);
        mGoodList = new ArrayList<CollectBean>();
        initData();
        initView();
        serListener();
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
                mSwipeRefreshLayout.setEnabled(mGridLayoutManager.findFirstVisibleItemPosition() == 0);
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
    //  获取数据 ，判断数据下标是否超过集合的长度
    private void initData() {
        String userName = FuliCenterApplication.getInstance().getUserName();
        Log.e(TAG, "userName = " + userName);
        if (userName.isEmpty())finish();
        findCollectList(new OkHttpUtils2.OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                Log.e(TAG, "新品的result = " + result);
                mSwipeRefreshLayout.setRefreshing(false);
                tvHint.setVisibility(View.GONE);
                mAdapter.setFooterString(getResources().getString(R.string.load_more));
                mAdapter.setMore(true);
                if (result != null) {
                    Log.e(TAG, "新品的result长度 = " + result.length);
                    ArrayList<CollectBean> collectList = Utils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(collectList);
                    } else {
                        mAdapter.addItem(collectList);
                    }
                    if (collectList.size() < I.PAGE_SIZE_DEFAULT) { // 判断加载最后的下标
                        mAdapter.setMore(false);
                        mAdapter.setFooterString(getResources().getString(R.string.no_more));
                    }
                } else {
                    mAdapter.setMore(false);
                    mAdapter.setFooterString(getResources().getString(R.string.no_more));
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
    private void findCollectList(OkHttpUtils2.OnCompleteListener<CollectBean[]> listener) {
        OkHttpUtils2<CollectBean[]> utils = new OkHttpUtils2<CollectBean[]>();
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME, FuliCenterApplication.getInstance().getUserName())
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }

    private void initView() {

        DisplayUtils.initbackWithTitle(mContext, "收藏的宝贝");
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_collect);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,R.color.google_yellow,
                R.color.google_red,R.color.google_green
        );
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_collect);
        mGridLayoutManager = new GridLayoutManager(mContext,2);  //  数据的初始化，括号后的表示每行表示的view个数
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); //  设置线性布局的排列方式
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new CollectAdapter(mContext,mGoodList);
        mRecyclerView.setAdapter(mAdapter);
        tvHint = (TextView)findViewById(R.id.tv_refresh_hint);  // 图片加载

    }
}
