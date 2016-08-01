package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.Map;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.Utils;

/**  新建的类 目的：下载用户好友信息
 * Created by Administrator on 2016/7/20.
 */
public class DownloadContactListTask {
    //   在 当前activity 中 显示TAG
    private final static String TAG = DownloadContactListTask.class.getSimpleName();
    String username;
    Context mContext;

    public DownloadContactListTask( Context context,String username) {
        mContext = context;
        this.username = username;
    }

    public DownloadContactListTask(String username) {
        this.username = username;
    }
    // 使用用户名 下载用户好友信息
    public void excute() {
        OkHttpUtils2<String> utils = new OkHttpUtils2<String>();
        utils.setRequestUrl(I.REQUEST_DOWNLOAD_CONTACT_ALL_LIST)
                .addParam(I.Contact.USER_NAME, username)
                .targetClass(String.class)
                .execute(new OkHttpUtils2.OnCompleteListener<String>() {  ///????
                    @Override
                    public void onSuccess(String s) {
                        Log.e(TAG, "s  = " + s);
                        Result result =  Utils.getListResultFromJson(s, UserAvatar.class);
                        if (result != null) {

                            List<UserAvatar> list = (List<UserAvatar>) result.getRetData();
                            if (list != null && list.size() >= 0) {
                                Log.e(TAG, "list.size = " + list.size());
                                FuliCenterApplication.getInstance().setUserlist(list);
                                mContext.sendStickyBroadcast(new Intent("update_contact_list"));
                                // 设置好友昵称
                                Map<String, UserAvatar> userMap = FuliCenterApplication.getInstance().getUserMap();
                                for (UserAvatar u : list) {
                                    userMap.put(u.getMUserName(), u);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "error = " + error);
                    }
                });
    }
}
