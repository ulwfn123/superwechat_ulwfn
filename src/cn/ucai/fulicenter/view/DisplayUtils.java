package cn.ucai.fulicenter.view;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.ucai.fulicenter.R;

/**
 * Created by Administrator on 2016/8/3.
 */
public class DisplayUtils {
    public static void initBack(final Activity activity) {
        activity.findViewById(R.id.backClickArea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    public static void initbackWithTitle(final Activity activity, final String title) {
        initBack(activity);
        ((TextView)activity.findViewById(R.id.tv_common_title)).setText(title); // 修改 返回按键后的题目
    }
    public static void initBouWithTitle(final Activity activity, final String title) {
        initBack(activity);
        ((TextView)activity.findViewById(R.id.tv_tongyong_biaoti)).setText(title); // 标题为土色
    }
}
