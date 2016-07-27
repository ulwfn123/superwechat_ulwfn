/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ucai.superwechat;

import android.app.Application;
import android.content.Context;

import com.easemob.EMCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ucai.superwechat.adapter.GroupAdapter;
import cn.ucai.superwechat.bean.GroupAvatar;
import cn.ucai.superwechat.bean.MemberUserAvatar;
import cn.ucai.superwechat.bean.UserAvatar;

public class DemoApplication extends Application {

	public static Context applicationContext;
	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";
	public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();
	private static DemoApplication instance;
	// login user name
	public final String PREF_USERNAME = "username";
    // 当前登录用户的 群组集合
    public List<GroupAvatar> groupList = new ArrayList<GroupAvatar>();
    // 添加 群组中 群聊 人员的 昵称属性
    public Map<String, HashMap<String, MemberUserAvatar>> memberMap = new HashMap<String, HashMap<String, MemberUserAvatar>>();
	//  为下载全局当前用户好 定义属性
	private UserAvatar user;
	//全局的当前 登录用户的好友集合
	private List<UserAvatar> userlist = new ArrayList<UserAvatar>();
	//获取昵称的  定义属性
	private Map<String, UserAvatar> userMap = new HashMap<String, UserAvatar>();
	//获取当前登录用户的 群组的集合
	private Map<String, GroupAvatar> GroupMap = new HashMap<String, GroupAvatar>();

	public static DemoApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = this;
		instance = this;

		/**
		 * this function will initialize the HuanXin SDK
		 *
		 * @return boolean true if caller can continue to call HuanXin related APIs after calling onInit, otherwise false.
		 *
		 * 环信初始化SDK帮助函数
		 * 返回true如果正确初始化，否则false，如果返回为false，请在后续的调用中不要调用任何和环信相关的代码
		 *
		 * for example:
		 * 例子：
		 *
		 * public class DemoHXSDKHelper extends HXSDKHelper
		 *
		 * HXHelper = new DemoHXSDKHelper();
		 * if(HXHelper.onInit(context)){
		 *     // do HuanXin related work
		 * }
		 */
		hxSDKHelper.onInit(applicationContext);
	}

	/**
	 * 获取当前登陆用户名
	 *
	 * @return
	 */
	public String getUserName() {
		return hxSDKHelper.getHXId();
	}

	/**
	 * 设置用户名
	 *
	 * @param
	 */
	public void setUserName(String username) {
		hxSDKHelper.setHXId(username);
	}

	/**
	 * 获取密码
	 *
	 * @return
	 */
	public String getPassword() {
		return hxSDKHelper.getPassword();
	}

	/**
	 * 设置密码 下面的实例代码 只是demo，实际的应用中需要加password 加密后存入 preference 环信sdk
	 * 内部的自动登录需要的密码，已经加密存储了
	 *
	 * @param pwd
	 */
	public void setPassword(String pwd) {
		hxSDKHelper.setPassword(pwd);
	}

	/**
	 * 退出登录,清空数据
	 */
	public void logout(final boolean isGCM, final EMCallBack emCallBack) {
		// 先调用sdk logout，在清理app中自己的数据
		hxSDKHelper.logout(isGCM, emCallBack);
	}

	public UserAvatar getUser() {
		return user;
	}

	public void setUser(UserAvatar user) {
		this.user = user;
	}

	public List<UserAvatar> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<UserAvatar> userlist) {
		this.userlist = userlist;
	}

	public Map<String, UserAvatar> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, UserAvatar> userMap) {
		this.userMap = userMap;
	}

    public List<GroupAvatar> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupAvatar> groupList) {
        this.groupList = groupList;
    }

    public Map<String, HashMap<String, MemberUserAvatar>> getMemberMap() {
        return memberMap;
    }

    public void setMemberMap(Map<String, HashMap<String, MemberUserAvatar>> memberMap) {
        this.memberMap = memberMap;
    }

	public Map<String, GroupAvatar> getGroupMap() {
		return GroupMap;
	}

	public void setGroupMap(Map<String, GroupAvatar> groupMap) {
		GroupMap = groupMap;
	}
}
