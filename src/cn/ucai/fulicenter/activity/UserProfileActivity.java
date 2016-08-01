package cn.ucai.fulicenter.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMValueCallBack;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.applib.controller.HXSDKHelper;
import com.easemob.chat.EMChatManager;
import cn.ucai.fulicenter.DemoHXSDKHelper;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.db.UserDao;
import cn.ucai.fulicenter.domain.User;
import cn.ucai.fulicenter.listener.OnSetAvatarListener;
import cn.ucai.fulicenter.utils.UserUtils;
import cn.ucai.fulicenter.utils.Utils;

import com.squareup.picasso.Picasso;

public class UserProfileActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = UserProfileActivity.class.getSimpleName();
	private static final int REQUESTCODE_PICK = 1;
	private static final int REQUESTCODE_CUTTING = 2;
	private ImageView headAvatar;
	private ImageView headPhotoUpdate;
	private ImageView iconRightArrow;
	private TextView tvNickName;
	private TextView tvUsername;
	private ProgressDialog dialog;
	private RelativeLayout rlNickName;

	private OnSetAvatarListener mOnSetAvatarListener;  //修改 用户个人资料的头像 的添加的属性2个
	private String avatarName;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_profile);
		initView();
		initListener();
	}
	
	private void initView() {
		headAvatar = (ImageView) findViewById(R.id.user_head_avatar);
		headPhotoUpdate = (ImageView) findViewById(R.id.user_head_headphoto_update);
		tvUsername = (TextView) findViewById(R.id.user_username);
		tvNickName = (TextView) findViewById(R.id.user_nickname);
		rlNickName = (RelativeLayout) findViewById(R.id.rl_nickname);
		iconRightArrow = (ImageView) findViewById(R.id.ic_right_arrow);
	}
	
	private void initListener() {
		Intent intent = getIntent();
		String username = intent.getStringExtra("username");
		String hxid = intent.getStringExtra("groupId");  // 通过 环信ID获取 群组中全部人员 的信息
		boolean enableUpdate = intent.getBooleanExtra("setting", false);
		if (enableUpdate) {
			headPhotoUpdate.setVisibility(View.VISIBLE);
			iconRightArrow.setVisibility(View.VISIBLE);
			rlNickName.setOnClickListener(this);
			headAvatar.setOnClickListener(this);
		} else {
			headPhotoUpdate.setVisibility(View.GONE);
			iconRightArrow.setVisibility(View.INVISIBLE);
		}
		//设置 显示 用户的头像和昵称信息
		if (username == null||username.equals(EMChatManager.getInstance().getCurrentUser())) {
			tvUsername.setText(EMChatManager.getInstance().getCurrentUser());
			UserUtils.setAppCurrentUserNick(tvNickName);   //调用  仿写的 修改昵称方法，，设置登录账户昵称
//			UserUtils.setAppUserAvatar(this, EMChatManager.getInstance().getCurrentUser(),headAvatar);  // 调用 仿写修改头像方法
			UserUtils.setCurrentUserAvatar(this, headAvatar);   //   自带的显示图片方法
        } else if (hxid != null) {   // 通过 环信ID  显示群组中全部成员的昵称和头像
            tvUsername.setText(username);
//            UserUtils.setAppMemberNick(hxid,username, tvNickName);   // 通过 环信ID  显示群组中全部成员的昵称
            UserUtils.setAppUserAvatar(this, username, headAvatar);
        } else {
            tvUsername.setText(username);
            UserUtils.setAppUserNick(username, tvNickName);  //  直接调用自写方方 ,显示 搜索的用户昵称
            UserUtils.setAppUserAvatar(this, username, headAvatar);//  直接调用自写方方 ,显示 搜索的用户头像
//			asyncFetchUserInfo(username);  //方法好处，从服务器获取数据，处理好友修改资料
        }
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_head_avatar:
//			uploadHeadPhoto();
			mOnSetAvatarListener = new OnSetAvatarListener(UserProfileActivity.this,
					R.id.layout_upload_avatar, getAvatarName(), I.AVATAR_TYPE_USER_PATH);
			break;
		case R.id.rl_nickname:
			final EditText editText = new EditText(this);
			Log.e(TAG, "nick  ====" + FuliCenterApplication.getInstance().getUser().getMUserNick());
			editText.setText(FuliCenterApplication.getInstance().getUser().getMUserNick());   //设置当前个人资料修改后的昵称
			new Builder(this).setTitle(R.string.setting_nickname).setIcon(android.R.drawable.ic_dialog_info).setView(editText)
					.setPositiveButton(R.string.dl_ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog, int which) {
							final String nickString = editText.getText().toString();
							if (TextUtils.isEmpty(nickString)) {

								Toast.makeText(UserProfileActivity.this, getString(R.string.toast_nick_not_isnull), Toast.LENGTH_SHORT).show();
								return;
							}
							updateAppNick(nickString);  //更新本地数据库昵称
						}
					}).setNegativeButton(R.string.dl_cancel, null).show();
			break;
		default:
			break;
		}
	}
	//更新用户资料本地数据库   昵称
	private void updateAppNick(final String nickString) {
		final OkHttpUtils2<String> utils = new OkHttpUtils2<String>();
		utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
				.addParam(I.User.USER_NAME, FuliCenterApplication.getInstance().getUserName())
				.addParam(I.User.NICK,nickString)
				.targetClass(String.class)
				.execute(new OkHttpUtils2.OnCompleteListener<String>() {
					@Override
					public void onSuccess(String s) {
						Log.e(TAG, "s == " + s);
						Result result = Utils.getResultFromJson(s, UserAvatar.class);
						if (result != null && result.isRetMsg()) {
							UserAvatar user = (UserAvatar) result.getRetData();
							if (user != null) {
								updateRemoteNick(nickString);  //  更新 用户昵称   更新本地服务端和环信服务器
								FuliCenterApplication.getInstance().setUser(user);
								FuliCenterApplication.currentUserNick = user.getMUserNick();
								UserDao dao = new UserDao(UserProfileActivity.this);
								dao.updateUserNick(user);
							}
						} else {
							Toast.makeText(UserProfileActivity.this, R.string.toast_updatenick_fail ,Toast.LENGTH_LONG).show();
							dialog.dismiss();
						}
					}

					@Override
					public void onError(String error) {
						Log.i(TAG, "error=" + error.toString());
						Toast.makeText(UserProfileActivity.this, R.string.toast_updatenick_fail ,Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}
				});
//		dialog.show();
	}

	public void asyncFetchUserInfo(String username){
		((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().asyncGetUserInfo(username, new EMValueCallBack<User>() {
			
			@Override
			public void onSuccess(User user) {
				if (user != null) {
					tvNickName.setText(user.getNick());
					if(!TextUtils.isEmpty(user.getAvatar())){
						 Picasso.with(UserProfileActivity.this).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(headAvatar);
					}else{
						Picasso.with(UserProfileActivity.this).load(R.drawable.default_avatar).into(headAvatar);
					}
					UserUtils.saveUserInfo(user);
				}
			}
			
			@Override
			public void onError(int error, String errorMsg) {
			}
		});
	}
	// 用户 个人资料的 头像更新
	private void uploadHeadPhoto() {
		Builder builder = new Builder(this);
		builder.setTitle(R.string.dl_title_upload_photo);
		builder.setItems(new String[] { getString(R.string.dl_msg_take_photo), getString(R.string.dl_msg_local_upload) },
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0:
							Toast.makeText(UserProfileActivity.this, getString(R.string.toast_no_support),
									Toast.LENGTH_SHORT).show();
							break;
						case 1:
							Intent pickIntent = new Intent(Intent.ACTION_PICK,null);
							pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
							startActivityForResult(pickIntent, REQUESTCODE_PICK);
							break;
						default:
							break;
						}
					}
				});
		builder.create().show();
	}
	
	
	//  向环信服务器  添加修改 的用户 昵称
	private void updateRemoteNick(final String nickName) {
		dialog = ProgressDialog.show(this, getString(R.string.dl_update_nick), getString(R.string.dl_waiting));
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean updatenick = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().updateParseNickName(nickName);
				if (UserProfileActivity.this.isFinishing()) {
					return;
				}
				if (!updatenick) {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatenick_fail), Toast.LENGTH_SHORT)
									.show();
							dialog.dismiss();
						}
					});
				} else {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatenick_success), Toast.LENGTH_SHORT)
									.show();
							tvNickName.setText(nickName);
							dialog.dismiss();
						}
					});
				}
			}
		}).start();
//		dialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {   //   个人资料 的 头像 显示
			return;
		}
		mOnSetAvatarListener.setAvatar(requestCode,data,headAvatar);

		if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
			Log.e(TAG, "upload avatar to app server ....");
			uploadUserAvatar(data);   // 个人资料，头像
			return;
		}
		Log.e(TAG, "requestCode  = =" + requestCode);
		switch (requestCode) {
		case REQUESTCODE_PICK:
			if (data == null || data.getData() == null) {
				return;
			}
			startPhotoZoom(data.getData());
			break;
		case REQUESTCODE_CUTTING:
			if (data != null) {
				setPicToView(data);
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);

	}
	//  更新 用户 个人资料   中的 头像 信息
	private void uploadUserAvatar(final Intent data) {
		File file = new File(OnSetAvatarListener.getAvatarPath(UserProfileActivity.this, I.AVATAR_TYPE_USER_PATH),
				avatarName + I.AVATAR_SUFFIX_JPG);
		String userName = FuliCenterApplication.getInstance().getUserName();
		final OkHttpUtils2<Result> utils = new OkHttpUtils2<Result>();
		utils.setRequestUrl(I.REQUEST_UPLOAD_AVATAR)
				.addParam(I.NAME_OR_HXID,userName)
				.addParam(I.AVATAR_TYPE,I.AVATAR_TYPE_USER_PATH)
				.targetClass(Result.class)
				.addFile(file)
				.execute(new OkHttpUtils2.OnCompleteListener<Result>() {
					@Override
					public void onSuccess(Result result) {
						if (result.isRetMsg()) {
							Log.e(TAG, "result ====" + result);
							Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatephoto_success),
									Toast.LENGTH_SHORT).show();
							setPicToView(data);   // 传入  头像的数据
						}
					}

					@Override
					public void onError(String error) {
						Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatephoto_fail),
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", true);
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, REQUESTCODE_CUTTING);
	}
	
	/**
	 * save the picture data
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(getResources(), photo);
			headAvatar.setImageDrawable(drawable);
			uploadUserAvatar(Bitmap2Bytes(photo));
		}

	}
	// 头像图片的数据上传到环信服务器
	private void uploadUserAvatar(final byte[] data) {
		dialog = ProgressDialog.show(this, getString(R.string.dl_update_photo), getString(R.string.dl_waiting));
		new Thread(new Runnable() {

			@Override
			public void run() {
				final String avatarUrl = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().uploadUserAvatar(data);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dialog.dismiss();
						if (avatarUrl != null) {
							Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatephoto_success),
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatephoto_fail),
									Toast.LENGTH_SHORT).show();
						}

					}
				});

			}
		}).start();
		dialog.show();
	}
	
	
	public byte[] Bitmap2Bytes(Bitmap bm){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	//  修改 个人资料 头像的 添加方法
	public String getAvatarName() {
		avatarName = String.valueOf(System.currentTimeMillis());
		return avatarName;
	}
}
