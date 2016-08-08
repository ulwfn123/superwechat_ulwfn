package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;

/**  新建的类 目的：下载用户好友信息
 * Created by Administrator on 2016/7/20.
 */
public class DownloadCollectCountTask {
    //   在 当前activity 中 显示TAG
    private final static String TAG = DownloadCollectCountTask.class.getSimpleName();
    String username;
    Context mContext;

    public DownloadCollectCountTask(Context context, String username) {
        mContext = context;
        this.username = username;
    }
    // 使用用户名 下载用户好友信息
    public void excute() {
        OkHttpUtils2<MessageBean> utils = new OkHttpUtils2<MessageBean>();
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME, username)
                .targetClass(MessageBean.class)
                .execute(new OkHttpUtils2.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean msg) {
                        Log.e(TAG, "msg  = " + msg);
                        if (msg != null) {
                            if (msg.isSuccess()) {
                                FuliCenterApplication.getInstance().setCollectCount(Integer.valueOf(msg.getMsg()));
                            } else {
                                FuliCenterApplication.getInstance().setCollectCount(0);
                            }
                            mContext.sendStickyBroadcast(new Intent("update_collect"));
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "error = " + error);
                    }
                });
    }
}
