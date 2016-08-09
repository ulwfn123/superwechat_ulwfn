package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.Utils;

/**  新建的类 目的：下载用户好友信息
 * Created by Administrator on 2016/7/20.
 */
public class DownloadCartListTask {
    //   在 当前activity 中 显示TAG
    private final static String TAG = DownloadCartListTask.class.getSimpleName();
    String username;
    Context mContext;
    public DownloadCartListTask(Context context, String username) {
        mContext = context;
        this.username = username;
    }

    public DownloadCartListTask(String username) {
        this.username = username;
    }
    // 使用用户名 下载用户好友信息
    public void excute() {
        OkHttpUtils2<CartBean[]> utils = new OkHttpUtils2<CartBean[]>();
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME, username)
                .addParam(I.PAGE_ID,I.PAGE_ID_DEFAULT+"")
                .addParam(I.PAGE_SIZE,I.PAGE_SIZE_DEFAULT+"")
                .targetClass(CartBean[].class)
                .execute(new OkHttpUtils2.OnCompleteListener<CartBean[]>() {  ///????
                    @Override
                    public void onSuccess(CartBean[] s) {
                        Log.e(TAG, "s  = " + s);
                        if (s != null) {
                            final ArrayList<CartBean> list = Utils.array2List(s);
                            final List<CartBean> cartList = FuliCenterApplication.getInstance().getCartList();
                            for (final  CartBean cart : list) {
                                if (!cartList.contains(cart)) {
                                    OkHttpUtils2<GoodDetailsBean> utils = new OkHttpUtils2<GoodDetailsBean>();
                                    utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                                            .addParam(D.GoodDetails.KEY_GOODS_ID,String.valueOf(cart.getGoodsId()))
                                            .targetClass(GoodDetailsBean.class)
                                            .execute(new OkHttpUtils2.OnCompleteListener<GoodDetailsBean>() {
                                                @Override
                                                public void onSuccess(GoodDetailsBean result) {
                                                    cart.setGoods(result);
                                                }

                                                @Override
                                                public void onError(String error) {

                                                }
                                            });
                                    cartList.add(cart);
                                } else {
                                    cartList.get(cartList.indexOf(cart)).setChecked(cart.isChecked());
                                    cartList.get(cartList.indexOf(cart)).setCount(cart.getCount());
                                }
                            }
                            mContext.sendStickyBroadcast(new Intent("update_cart_list"));
                        }
                    }
                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "error = " + error);
                    }
                });
    }
//    private void getGoodDetailsByGoodId(OkHttpUtils2.OnCompleteListener<GoodDetailsBean> listener,final int goodid) {
//        OkHttpUtils2<GoodDetailsBean> utils = new OkHttpUtils2<GoodDetailsBean>();
//        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
//                .addParam(D.GoodDetails.KEY_GOODS_ID,String.valueOf(goodid))
//                .targetClass(GoodDetailsBean.class)
//                .execute(listener);
//    }
}
