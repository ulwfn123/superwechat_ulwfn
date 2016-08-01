package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.Map;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
//import cn.ucai.fulicenter.bean.GroupAvatar;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.Utils;

/**  新建的类 目的：群组用户好友信息
 * Created by Administrator on 2016/7/20.
 */
public class DownloadGroupListTask {
    //   在 当前activity 中 显示TAG
    private final static String TAG = DownloadGroupListTask.class.getSimpleName();
    String username;
    Context mContext;

    public DownloadGroupListTask(Context context, String username) {
        mContext = context;
        this.username = username;
    }

    public DownloadGroupListTask(String username) {
        this.username = username;
    }
    // 使用用户名 下载用户好友信息
//    public void excute() {
//        final OkHttpUtils2<String> utils = new OkHttpUtils2<String>();
//        utils.setRequestUrl(I.REQUEST_FIND_GROUP_BY_USER_NAME)
//                .addParam(I.User.USER_NAME, username)
//                .targetClass(String.class)
//                .execute(new OkHttpUtils2.OnCompleteListener<String>() {
//                    @Override
//                    public void onSuccess(String s) {
//                        Log.e(TAG, "s  = " + s);
//                        Result result =  Utils.getListResultFromJson(s, GroupAvatar.class);
//
//                        List<GroupAvatar> list = (List<GroupAvatar>) result.getRetData();
//                        Log.e(TAG, " result.getRetData()  === " +  result.getRetData());
//                        if (list != null && list.size() >= 0) {
//                            FuliCenterApplication.getInstance().setGroupList(list);
//                            Map<String, GroupAvatar> groupMap = FuliCenterApplication.getInstance().getGroupMap();// 添加 群组的集合
//                            Log.e(TAG, " groupMap  === " +  groupMap);
//                            for (GroupAvatar g : list) {
//                                groupMap.put(g.getMAvatarUserName(), g);
//                            }
//                            mContext.sendStickyBroadcast(new Intent("update_group_list"));
//                        }
//                    }
//
//                    @Override
//                    public void onError(String error) {
//                        Log.e(TAG, "error = " + error);
//                    }
//                });
//    }
}
