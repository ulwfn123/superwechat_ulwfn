package cn.ucai.fulicenter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.DemoHXSDKHelper;
import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.db.DemoDBManager;
import cn.ucai.fulicenter.task.DownloadCollectCountTask;
import cn.ucai.fulicenter.view.DisplayUtils;
import cn.ucai.fulicenter.view.FlowIndicator;
import cn.ucai.fulicenter.view.SlideAutoLoopView;

/**
 * Created by Administrator on 2016/8/3.
 */
public class GoodDetailsActivity extends BaseActivity {
    private static final String TAG = GoodDetailsActivity.class.getSimpleName();
    GoodDetailsActivity mContext;
    ImageView  ivShare,ivCollect,ivCart;
    TextView tvCartCount;
    TextView tvGoodEnglishName,tvGoodName,tvGoodPriceCurrent,tvGoodPriceShop;
    SlideAutoLoopView mSlideAutoLoopView;
    FlowIndicator mFlowIndicator;
    WebView wvGoodBrief;
    GoodDetailsBean mGoodDetail;
    int mGooId;
    boolean isCollect;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activtiyt_good_details);  // 必须这条命令， 创建布局文件
        mContext = this;
        initView();
        initData();
        setListemer();

    }

    private void setListemer() {
        MyOnClickListener listener = new MyOnClickListener();
        ivCollect.setOnClickListener(listener);

    }

    private void initData() {
        mGooId = getIntent().getIntExtra(D.GoodDetails.KEY_ENGLISH_NAME, 0);
        Log.e(TAG, "GoodDetailsActivity.goodi = " + mGooId);
        if (mGooId > 0) {
            getGoodDetailsByGoodId(new OkHttpUtils2.OnCompleteListener<GoodDetailsBean>() {
                @Override
                public void onSuccess(GoodDetailsBean result) {
                    Log.e(TAG, "GoodDetailsActivity.result = " + result);

                    mGoodDetail = result;  // 把数据库中请求到的数据 赋值到实体类的变量中
                    showGoodDetails(); // 向View对象赋值
                }

                @Override
                public void onError(String error) {
                    Log.e(TAG, "error = " + error);
                    finish();
                }
            });

        } else {
            finish();
            Toast.makeText(mContext,"获取的商品ID为空",Toast.LENGTH_SHORT).show();
        }
    }
    //  向View  对象赋值
    private void showGoodDetails() {
        tvGoodEnglishName.setText(mGoodDetail.getGoodsEnglishName());
        tvGoodName.setText(mGoodDetail.getGoodsName());
        tvGoodPriceCurrent.setText(mGoodDetail.getCurrencyPrice());
        tvGoodPriceShop.setText(mGoodDetail.getShopPrice());
        mSlideAutoLoopView.startPlayLoop(mFlowIndicator,getAlbumImageUrl(),getAlbumImageSize());   //  图片轮播的加载
        Log.e(TAG, "图片下的详细资料" + mGoodDetail.getGoodsBrief().toString());
        wvGoodBrief.loadDataWithBaseURL(null, mGoodDetail.getGoodsBrief(), D.TEXT_HTML, D.UTF_8, null); // 图片 下面的webView文本的加载
    }
    // 轮播的图片
    private int getAlbumImageSize() {
        if (mGoodDetail.getProperties() != null && mGoodDetail.getProperties().length > 0) {
            return mGoodDetail.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumImageUrl() {
        String[] albbumImageUrl = new String[]{};
        if (mGoodDetail.getProperties() != null && mGoodDetail.getProperties().length > 0) {
            AlbumsBean[] albums = mGoodDetail.getProperties()[0].getAlbums();
            albbumImageUrl = new String[albums.length];
            for (int i=0;i<albbumImageUrl.length;i++) {
                albbumImageUrl[i] = albums[i].getImgUrl();
            }
        }
        return albbumImageUrl;
    }

    // 服务器连接请求数据,下载 商品数据信息
    private void getGoodDetailsByGoodId(OkHttpUtils2.OnCompleteListener<GoodDetailsBean> listener) {
        Log.e(TAG, "mgooid" + mGooId);
        OkHttpUtils2<GoodDetailsBean> utils = new OkHttpUtils2<GoodDetailsBean>();
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(D.GoodDetails.KEY_GOODS_ID,String.valueOf(mGooId))
                .targetClass(GoodDetailsBean.class)
                .execute(listener);
    }

    /**
     *  属性初始化
      */
    private void initView() {
        DisplayUtils.initBack(mContext);
        ivShare = (ImageView) findViewById(R.id.iv_good_share);
        ivCollect = (ImageView) findViewById(R.id.iv_good_collect);
        ivCart = (ImageView) findViewById(R.id.iv_good_cart);
        tvCartCount = (TextView) findViewById(R.id.iv_cart_count);
        tvGoodEnglishName = (TextView) findViewById(R.id.iv_good_name_english);
        tvGoodName = (TextView) findViewById(R.id.iv_good_name);
        tvGoodPriceCurrent = (TextView) findViewById(R.id.tv_good_price_current);
        tvGoodPriceShop = (TextView) findViewById(R.id.tv_good_price_shop);
        mSlideAutoLoopView = (SlideAutoLoopView) findViewById(R.id.salv);
        mFlowIndicator = (FlowIndicator) findViewById(R.id.indicator);
        wvGoodBrief = (WebView) findViewById(R.id.wv_good_brief);
        WebSettings settings = wvGoodBrief.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCollecStatus();
    }
    // 查询 是否已收藏
    private void initCollecStatus() {
        final String userName = FuliCenterApplication.getInstance().getUserName();
        if (DemoHXSDKHelper.getInstance().isLogined()) {  //判断是否为登录状态
            OkHttpUtils2<MessageBean> utils = new OkHttpUtils2<MessageBean>();
            utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                    .addParam(I.Collect.USER_NAME, userName)
                    .addParam(I.Collect.GOODS_ID, mGooId + "")
                    .targetClass(MessageBean.class)
                    .execute(new OkHttpUtils2.OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            Log.e(TAG, "result=" + result);
                            if (result != null && result.isSuccess()) {
                                isCollect = true;
                            } else {
                                isCollect = false;
                            }
                            undateCollectStatus();
                        }
                        @Override
                        public void onError(String error) {
                            Log.e(TAG, "error=" + error);
                            ivCollect.setImageResource(R.drawable.bg_collect_in);
                        }
                    });
        }
    }

    //判断商品是否 以收藏是否boolear
    private void  undateCollectStatus() {
        if (isCollect) {
            ivCollect.setImageResource(R.drawable.bg_collect_out);
        } else {
            ivCollect.setImageResource(R.drawable.bg_collect_in);
        }
    }

    // 点击   取消收藏d的 方法
    private void deleteCollectStatus(String user, int getGoodsId) {
         //  判断是否是登录状态
            Log.e(TAG, "121111111111111111+" +mGooId + "删除的名称" + FuliCenterApplication.getInstance().getUserName());
            OkHttpUtils2<MessageBean> utils = new OkHttpUtils2<MessageBean>();
            utils.setRequestUrl(I.REQUEST_DELETE_COLLECT)
                    .addParam(I.Collect.USER_NAME, user)
                    .addParam(I.Collect.GOODS_ID, String.valueOf(getGoodsId))
                    .targetClass(MessageBean.class)
                    .execute(new OkHttpUtils2.OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            Log.e(TAG, "result=111111111111111111" + result);
                            if (result != null && result.isSuccess()) {
                                new DownloadCollectCountTask(mContext, FuliCenterApplication.getInstance().getUserName());
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
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_good_collect:
                    if (isCollect) {
                        // 删除收藏
                        final String userName = FuliCenterApplication.getInstance().getUserName();
                        deleteCollectStatus(userName, mGoodDetail.getGoodsId());
                    } else {

                    }
                    break;
            }
        }
    }


}
