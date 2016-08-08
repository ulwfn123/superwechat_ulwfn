package cn.ucai.fulicenter.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.applib.controller.HXSDKHelper;
import cn.ucai.fulicenter.DemoHXSDKHelper;
import cn.ucai.fulicenter.R;
//import cn.ucai.fulicenter.bean.MemberUserAvatar;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.domain.User;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class UserUtils {
	private  static final String TAG = UserUtils.class.getSimpleName();

	/**
     * 根据username获取相应user，由于demo没有真实的用户数据，这里给的模拟的数据；
     * @param username
     * @return
     */
    public static User getUserInfo(String username){
        User user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getContactList().get(username);
        if(user == null){
            user = new User(username);
        }
            
        if(user != null){
            //demo没有这些数据，临时填充
        	if(TextUtils.isEmpty(user.getNick()))
        		user.setNick(username);
        }
        return user;
    }

	/**    仿写
	 * 根据username用户名称获取相应useravatar用户头像
	 */
	public static UserAvatar getAppUserInfo(String username){
		UserAvatar user = FuliCenterApplication.getInstance().getUserMap().get(username);
		if(user == null){
			user = new UserAvatar(username);
		}
		return user;
	}
    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
    	User user = getUserInfo(username);
        if(user != null && user.getAvatar() != null){
            Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
        }else{
            Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
        }
    }

    
    /**
     * 设置当前用户头像
     */
	public static void setCurrentUserAvatar(Context context, ImageView imageView) {
		User user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
		if (user != null && user.getAvatar() != null) {
			Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
		} else {
			Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
		}
	}
	//设置当前用户头像 仿写

	public static void setAppCurrentUserAvatar(Context context, ImageView imageView) {
		String name = FuliCenterApplication.getInstance().getUserName();
		setAppUserAvatar(context,name,imageView);

	}
    
    /**
     * 设置用户昵称
     */
    public static void setUserNick(String username,TextView textView){
    	User user = getUserInfo(username);
    	if(user != null){
    		textView.setText(user.getNick());
    	}else{
    		textView.setText(username);
    	}
    }
	  //设置用户好友昵称 仿写

	public static void setAppUserNick(String username,TextView textView){
		UserAvatar user = getAppUserInfo(username);
		setAppUserNick(user, textView);
	}
	// 设置当前用户昵称  仿写  上面调用
	public static void setAppUserNick(UserAvatar user,TextView textView){
		if (user != null) {
			if (user.getMUserNick() != null) {
				textView.setText(user.getMUserNick());
			} else {
				textView.setText(user.getMUserName());
			}
		} else {
			textView.setText(user.getMUserName());
		}
	}

    /**
     * 设置当前用户昵称
     */
    public static void setCurrentUserNick(TextView textView){
    	User user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
    	if(textView != null){
    		textView.setText(user.getNick());
    	}
    }

	 //设置当前用户昵称  仿写
	public static void setAppCurrentUserNick(TextView textView){
		UserAvatar user = FuliCenterApplication.getInstance().getUser();
		if(textView != null&&user!=null){
			if (user.getMUserNick() != null) {
				textView.setText(user.getMUserNick());
			}
			textView.setText(user.getMUserName());
		}
	}

    /**
     * 保存或更新某个用户
     * @param  newUser
     */
	public static void saveUserInfo(User newUser) {
		if (newUser == null || newUser.getUsername() == null) {
			return;
		}
		((DemoHXSDKHelper) HXSDKHelper.getInstance()).saveContact(newUser);
	}
	//  设置y用户 头像  仿写
	public static void setAppUserAvatar(Context context, String username, ImageView imageView){
		String path = "";
		if(path != null && username != null){
			path = getUserAvatarPath(username);
			Picasso.with(context).load(path).placeholder(R.drawable.default_avatar).into(imageView);
		}else{
			Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
		}
	}
	//  设置  用户 好友头像
    public static String getUserAvatarPath(String username) {
		StringBuilder path = new StringBuilder(I.SERVER_ROOT);
		path.append(I.QUESTION).append(I.KEY_REQUEST)
				.append(I.EQU).append(I.REQUEST_DOWNLOAD_AVATAR)
				.append(I.AND)
				.append(I.AVATAR_TYPE).append(I.EQU).append(username);
		return path.toString();
	}

//    //  设置  群组成员 头像
//    public static String getGroupAvatarPath(String hxid) {
//        StringBuilder path = new StringBuilder(I.SERVER_ROOT);
//        path.append(I.QUESTION).append(I.KEY_REQUEST)
//                .append(I.EQU).append(I.REQUEST_DOWNLOAD_AVATAR)
//                .append(I.AND)
//                .append(I.NAME_OR_HXID).append(I.EQU).append(hxid)
//                .append(I.AND)
//                .append(I.AVATAR_TYPE).append(I.EQU).append(I.AVATAR_TYPE_GROUP_PATH);
//        return path.toString();
//    }

//    //   群组中 昵称的显示      通过环信ID 获取用户名 及用户名的昵称 添加到textView
//	public static void setAppMemberNick(String hxid, String username, TextView textView) {
//		MemberUserAvatar member = getAppMemberInfo(hxid,username);
//        Log.e(TAG, "member  ==" + member);
//        if (member != null && member.getMUserNick() != null) {
//            textView.setText(member.getMUserNick());
//        } else {
//            textView.setText(member.getMUserName());
//        }
//    }
//    //  上面 调用的 方法 是通过 环信ID  获取  群组中 用户 的个人资料
//    public static MemberUserAvatar getAppMemberInfo(String hxid,String username){
//        MemberUserAvatar member = null;
//        Map<String,MemberUserAvatar> user =  FuliCenterApplication.getInstance().getMemberMap().get(hxid);
//        Log.e(TAG, "user  ==" + user+ "userName  ==  " + username);
//        Log.e(TAG, "hxid  ==" + hxid);
//        if (user == null || user.size() < 0) {
//            return null;
//        } else {
//            member = user.get(username);
//            Log.e(TAG, "member" + member);
//        }
//        Log.e(TAG, "member111111" + member);
//        return member;
//    }
}
