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
package cn.ucai.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import cn.ucai.fulicenter.DemoApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.listener.OnSetAvatarListener;
import cn.ucai.fulicenter.utils.Utils;

import com.easemob.exceptions.EaseMobException;

import java.io.File;

/**
 * 注册页
 * 
 */
public class RegisterActivity extends BaseActivity {
	private static final String TAG = RegisterActivity.class.getSimpleName();
	private EditText userNameEditText;
	private EditText userNickEditText;
	private EditText passwordEditText;
	private EditText confirmPwdEditText;
	private RelativeLayout layoutAvatar;
	private ImageView imAvatar;
	String avatarName;

	String username ;
	String nick ;
	String pwd ;
	ProgressDialog pd;

	private OnSetAvatarListener mOnSetAvatarListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
		setLitener();
	}
	//登录和注册的监听
	private void setLitener() {
		findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				register();
			}
		});
		findViewById(R.id.layout_register_avatar).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mOnSetAvatarListener = new OnSetAvatarListener(RegisterActivity.this,
						R.id.layout_register, getAvatarname(), I.AVATAR_TYPE_USER_PATH);
			}
		});
	}
	// 头像返回时调用的方法
	@Override                     //请求码          判断码              数据
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}

		mOnSetAvatarListener.setAvatar(requestCode,data,imAvatar);
	}
	// 设置 图片的名称,利用 电脑的时间
	private String getAvatarname() {
		avatarName = String.valueOf(System.currentTimeMillis());
		return avatarName;

	}

	private void initView() {
		userNameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);
		confirmPwdEditText = (EditText) findViewById(R.id.confirm_password);
		userNickEditText = (EditText) findViewById(R.id.Nick);
		layoutAvatar = (RelativeLayout) findViewById(R.id.layout_register_avatar);
		imAvatar = (ImageView) findViewById(R.id.iv_avatar);
	}

	/**
	 * 注册
	 * 
	 * @param
	 */
	private void register() {
		username = userNameEditText.getText().toString().trim();
		nick = userNickEditText.getText().toString().trim();
		pwd = passwordEditText.getText().toString().trim();
		String confirm_pwd = confirmPwdEditText.getText().toString().trim();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
			userNameEditText.requestFocus();
			return;
		}else if (!username.matches("[\\w][\\w\\d_]+")) {  //这里注意正则表达式为“反”
			Toast.makeText(this, getResources().getString(R.string.illegal_user_name), Toast.LENGTH_SHORT).show();
			passwordEditText.requestFocus();
			return;
		} else if (TextUtils.isEmpty(nick)) {   //  判断 昵称是否为空
			Toast.makeText(this, getResources().getString(R.string.toast_nick_not_isnull), Toast.LENGTH_SHORT).show();
			passwordEditText.requestFocus();
			return;
		}else if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
			passwordEditText.requestFocus();
			return;
		} else if (TextUtils.isEmpty(confirm_pwd)) {
			Toast.makeText(this, getResources().getString(R.string.Confirm_password_cannot_be_empty), Toast.LENGTH_SHORT).show();
			confirmPwdEditText.requestFocus();
			return;
		} else if (!pwd.equals(confirm_pwd)) {
			Toast.makeText(this, getResources().getString(R.string.Two_input_password), Toast.LENGTH_SHORT).show();
			return;
		}

		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
			pd = new ProgressDialog(this);
			pd.setMessage(getResources().getString(R.string.Is_the_registered));
			pd.show();
			registerAppSever();

		}
	}
	//获取被上传头像的地址
	private void registerAppSever() {
		File file = new File(OnSetAvatarListener.getAvatarPath(RegisterActivity.this, I.AVATAR_TYPE_USER_PATH),
				avatarName + I.AVATAR_SUFFIX_JPG);
		final OkHttpUtils2<Result> utils2 = new OkHttpUtils2<Result>();
		utils2.setRequestUrl(I.REQUEST_REGISTER)
				.addParam(I.User.USER_NAME,username)
				.addParam(I.User.PASSWORD,pwd)
				.addParam(I.User.NICK,nick)
				.targetClass(Result.class)
				.addFile(file)
				.execute(new OkHttpUtils2.OnCompleteListener<Result>() {
					@Override
					public void onSuccess(Result result) {
						if (result.isRetMsg()) {
							registerEMServer();
						} else {
							// 注册失败
							Log.e(TAG, "register fail ..." + result.getRetCode());
							pd.dismiss();  // 退出请求
							Toast.makeText(getApplicationContext(),R.string.Registration_failed
									+ Utils.getResourceString(RegisterActivity.this,result.getRetCode()),Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onError(String error) {
						//输出异常
						Log.e(TAG, "register fail ..." + error.toString());
						pd.dismiss();

					}
				});
	}
	//注册账户
	private void registerEMServer() {
		//新建线程
		new Thread(new Runnable() {
			public void run() {
				try {
					// 调用sdk注册方法
					EMChatManager.getInstance().createAccountOnServer(username, pwd);
					runOnUiThread(new Runnable() {
						public void run() {
							if (!RegisterActivity.this.isFinishing())
								pd.dismiss();
							// 保存用户名
							DemoApplication.getInstance().setUserName(username);
							Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), Toast.LENGTH_SHORT).show();
							finish();
						}
					});
				} catch (final EaseMobException e) {
					unRegisterAppSevert(); //注册失败，删除本地库中文件
					runOnUiThread(new Runnable() {
						public void run() {
							if (!RegisterActivity.this.isFinishing())
								pd.dismiss();
							int errorCode=e.getErrorCode();
							if(errorCode==EMError.NONETWORK_ERROR){
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
							}else if(errorCode == EMError.USER_ALREADY_EXISTS){
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
							}else if(errorCode == EMError.UNAUTHORIZED){
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
							}else if(errorCode == EMError.ILLEGAL_USER_NAME){
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name),Toast.LENGTH_SHORT).show();
							}else{
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed) + e.getMessage(), Toast.LENGTH_SHORT).show();
							}
						}
					});
				}
			}
		}).start();
	}
	//注册失败，删除本地库中文件
	private void unRegisterAppSevert() {
		OkHttpUtils2<Result> utils2 = new OkHttpUtils2<Result>();
		utils2.setRequestUrl(I.REQUEST_UNREGISTER)
				.addParam(I.User.USER_NAME,username)
				.targetClass(Result.class)
				.execute(new OkHttpUtils2.OnCompleteListener<Result>() {
					@Override
					public void onSuccess(Result result) {
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(String error) {
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
					}
				});
	}

	public void back(View view) {
		finish();
	}

}
