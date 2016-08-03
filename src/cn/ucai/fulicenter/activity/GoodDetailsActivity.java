package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.GoodAdapter;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.raw.FlowIndicator;
import cn.ucai.fulicenter.raw.SlideAutoLoopView;

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

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activtiyt_good_details);  // 必须这条命令， 创建布局文件
        mContext = this;
        initView();
        initData();

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
        wvGoodBrief.loadDataWithBaseURL(null, mGoodDetail.getGoodsBrief(), D.TEXT_HTML, D.UTF_8, null); // 图片 下面的webView文本的加载
    }

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

}
