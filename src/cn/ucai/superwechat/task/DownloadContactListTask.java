package cn.ucai.superwechat.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import cn.ucai.superwechat.DemoApplication;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.bean.Result;
import cn.ucai.superwechat.bean.UserAvatar;
import cn.ucai.superwechat.data.OkHttpUtils2;
import cn.ucai.superwechat.utils.Utils;

/**  新建的类 目的：下载用户好友信息
 * Created by Administrator on 2016/7/20.
 */
public class DownloadContactListTask {
    private final static String TAG = DownloadContactListTask.class.getSimpleName();
    String username;
    Context context;

    public DownloadContactListTask( Context context,String username) {
        this.username = username;
        this.context = context;
    }

    public DownloadContactListTask(String username) {
        this.username = username;
    }
    public void excute(String username) {
        OkHttpUtils2<String> utils = new OkHttpUtils2<String>();
        utils.setRequestUrl(I.REQUEST_DOWNLOAD_CONTACT_ALL_LIST)
                .addParam(I.Contact.USER_NAME, username)
                .targetClass(String.class)
                .execute(new OkHttpUtils2.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Result result1 = Utils.getResultFromJson(result, UserAvatar.class);
                        List<UserAvatar> list = (List<UserAvatar>) result1.getRetData();
                        if (list != null && list.size() >= 0) {
                            DemoApplication.getInstance().setList(list);
                            context.sendStickyBroadcast(new Intent("Down_Contact_liattTsk"));
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "error = " + error);
                    }
                });
    }
}
