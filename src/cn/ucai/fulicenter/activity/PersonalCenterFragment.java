package cn.ucai.fulicenter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.ucai.fulicenter.DemoHXSDKHelper;
import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.utils.UserUtils;

/**
 * Created by Administrator on 2016/8/8.
 */
public class PersonalCenterFragment extends Fragment {
    private static final String TAG = PersonalCenterFragment.class.getSimpleName();
    FuliCenterManActivity mContext;
    TextView mtvUserName,mtvSettings;
    ImageView mivUserAvatar,mivMSG;
    TextView mtvCollectCount;
    LinearLayout mllv_sub1,mllv_sub2,mllv_sub3,mllv_sub4,mllv_sub5;
    LinearLayout layoutCollect,layoutUserCenter;
    // 初始化 继承广播类
    UpdateCollectCount mReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = (FuliCenterManActivity) getContext();
        final View layout = View.inflate(mContext, R.layout.fragment_personal_center, null);
        initView(layout);
        initData();
        setListener();
        return layout ;
    }

    private void initData() {
        if (DemoHXSDKHelper.getInstance().isLogined()) {
            final UserAvatar user = FuliCenterApplication.getInstance().getUser();
            Log.e(TAG, "user+ ==" + user);
            UserUtils.setAppCurrentUserNick(mtvUserName);
            UserUtils.setAppCurrentUserAvatar(mContext,mivUserAvatar);
        }

    }

    //设置监听
    private void setListener() {
        MyClickListener listener = new MyClickListener();
        mtvSettings.setOnClickListener(listener);
        layoutUserCenter.setOnClickListener(listener);
        layoutCollect.setOnClickListener(listener);
        updateCollectCountListener();
    }

    private void initView(View layout) {
        mtvUserName = (TextView) layout.findViewById(R.id.tv_user_name);
        mivUserAvatar = (ImageView) layout.findViewById(R.id.iv_user_avatar);
        mivMSG = (ImageView) layout.findViewById(R.id.iv_personal_XiaoXi);
        mtvSettings = (TextView) layout.findViewById(R.id.iv_personal_Shezhi);
        mtvCollectCount = (TextView) layout.findViewById(R.id.tv_baobei_tv);
        layoutCollect = (LinearLayout) layout.findViewById(R.id.llv_baobei);
        layoutUserCenter = (LinearLayout) layout.findViewById(R.id.ll_user_Xingxi);

        mllv_sub1 = (LinearLayout) layout.findViewById(R.id.llv_sub1);
        mllv_sub2 = (LinearLayout) layout.findViewById(R.id.llv_sub2);
        mllv_sub3 = (LinearLayout) layout.findViewById(R.id.llv_sub3);
        mllv_sub4 = (LinearLayout) layout.findViewById(R.id.llv_sub4);
        mllv_sub5 = (LinearLayout) layout.findViewById(R.id.llv_sub5);


    }

    private void updateCollectCountListener() {
        mReceiver = new UpdateCollectCount();
        IntentFilter filter = new IntentFilter("update_collect");
        mContext.registerReceiver(mReceiver, filter);
    }

    //复写 销毁方法
    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(mReceiver);
    }

    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            // 判断 是否登录账户
            if (DemoHXSDKHelper.getInstance().isLogined()) {
                switch (view.getId()) {
                    case R.id.ll_user_Xingxi:
                    case R.id.iv_personal_Shezhi:
                        startActivity(new Intent(mContext, SettingsActivity.class));
                        break;
                    case R.id.llv_baobei:
                        startActivity(new Intent(mContext,CollectActivity.class));
                }
            } else {
                Log.e(TAG, "没有登录");
            }
        }
    }

    class UpdateCollectCount extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final int count = FuliCenterApplication.getInstance().getCollectCount();
            Log.e(TAG, "count    =   " + count);
            mtvCollectCount.setText(String.valueOf(count));
        }
    }
}
