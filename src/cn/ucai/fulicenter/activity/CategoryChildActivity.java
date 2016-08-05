package cn.ucai.fulicenter.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.view.CatChildFilterButton;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.GoodAdapter;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.Utils;
import cn.ucai.fulicenter.view.DisplayUtils;

/**
 * Created by Administrator on 2016/8/1.
 */
public class CategoryChildActivity extends BaseActivity {
    private static final String TAG = CategoryChildActivity.class.getSimpleName();
    CategoryChildActivity mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    GoodAdapter mAdapter;
    List<NewGoodBean> mGoodList;
    TextView tvHint;
    Button btnSortPrice,btnSortAddTime;
    boolean mSortPriceAsc;
    boolean mSoetAddTimeAsc;
    ArrayList<CategoryChildBean> childList;
    CatChildFilterButton mCatChildFilterButton;
    int  sortBy;
    String name;

    int pageId = 0;
    int action =I.ACTION_DOWNLOAD ;
    int  catId = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mContext = this;
        setContentView(R.layout.activity_category_child);
        mGoodList = new ArrayList<NewGoodBean>();
        sortBy = I.SORT_BY_ADDTIME_DESC;  // 设置为时间 降序
        initData();
        initView();
        serListener();

    }

    private void serListener() {
        setPullDownRefreshListener(); //下拉
        setPullUpRefreshListener(); // 上拉
        SortStatusChangdListener listener = new SortStatusChangdListener();  // 实例化监听
        btnSortPrice.setOnClickListener(listener);   // 价格的监听
        btnSortAddTime.setOnClickListener(listener);// 时间的监听
        mCatChildFilterButton.setOnCatFilterClickListener(name,childList);
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
        catId= getIntent().getIntExtra(I.CategoryChild.CAT_ID, 0);
        childList = (ArrayList<CategoryChildBean>) getIntent().getSerializableExtra("childList");
        Log.e(TAG, "catid = " + catId);
        if (catId<0)finish();
        findBoutqueChildList(new OkHttpUtils2.OnCompleteListener<NewGoodBean[]>() {
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
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(goodBeanArrayList);
                    } else {
                        mAdapter.addItem(goodBeanArrayList);
                    }
                    if (goodBeanArrayList.size() < I.PAGE_SIZE_DEFAULT) { // 判断加载最后的下标
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
    private void findBoutqueChildList(OkHttpUtils2.OnCompleteListener<NewGoodBean[]> listener) {
        OkHttpUtils2<NewGoodBean[]> utils = new OkHttpUtils2<NewGoodBean[]>();
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam(I.NewAndBoutiqueGood.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodBean[].class)
                .execute(listener);
    }

    private void initView() {
//       String name=  getIntent().getStringExtra(D.Boutique.KEY_NAME); // 修改 返回按键后的题目
        DisplayUtils.initBack(mContext);
        name = getIntent().getStringExtra(I.CategoryGroup.NAME);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_category_child);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,R.color.google_yellow,
                R.color.google_red,R.color.google_green
        );
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_category_child);
        mGridLayoutManager = new GridLayoutManager(mContext,2);  //  数据的初始化，括号后的表示每行表示的view个数
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); //  设置线性布局的排列方式
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new GoodAdapter(mContext, mGoodList);
        mRecyclerView.setAdapter(mAdapter);
        tvHint = (TextView)findViewById(R.id.tv_refresh_hint);  // 图片加载
        btnSortPrice = (Button) findViewById(R.id.btn_sort_price);  // 实列化 价格时间 按键
        btnSortAddTime = (Button) findViewById(R.id.btn_sort_addtime);
        mCatChildFilterButton = (CatChildFilterButton) findViewById(R.id.btnCatChildFilter);
    }

    class SortStatusChangdListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Drawable drawable;
            switch (view.getId()) {
                case R.id.btn_sort_price:
                    if (mSortPriceAsc) {
                        sortBy = I.SORT_BY_PRICE_ASC;
                        drawable = getResources().getDrawable(R.drawable.arrow_order_up);  //修改 分类“价格”后面的 向上箭头
                    } else {
                        sortBy = I.SORT_BY_PRICE_DESC;
                        drawable = getResources().getDrawable(R.drawable.arrow_order_down);
                    }
                    mSortPriceAsc = !mSortPriceAsc;
                    drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
                    btnSortPrice.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
                    break;
                case R.id.btn_sort_addtime:
                    if (mSoetAddTimeAsc) {
                        sortBy = I.SORT_BY_ADDTIME_ASC;
                        drawable = getResources().getDrawable(R.drawable.arrow_order_up); //修改 分类“价格”后面的 向上箭头
                    } else {
                        sortBy = I.SORT_BY_ADDTIME_DESC;
                        drawable = getResources().getDrawable(R.drawable.arrow_order_down);
                    }
                    mSoetAddTimeAsc = !mSoetAddTimeAsc;
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    btnSortAddTime.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
                    break;
            }
            mAdapter.setSortBy(sortBy);
        }
    }
}