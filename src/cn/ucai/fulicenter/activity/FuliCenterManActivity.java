package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import cn.ucai.fulicenter.DemoHXSDKHelper;
import cn.ucai.fulicenter.R;

/**
 * Created by Administrator on 2016/8/1.
 */
public class FuliCenterManActivity extends BaseActivity {
    public static final int ACTION_LOGIN =100;
    private static final String TAG = FuliCenterManActivity.class.getSimpleName();
    RadioButton rbNewGood;
    RadioButton rbBoutique;
    RadioButton rbCategory;
    RadioButton rbCart;
    RadioButton rbPersonalCenter;
    TextView tvCartHint;
    RadioButton[] mrbTabs;
    int index ;
    int currentIndex;
    Fragment[]  mFragment;
    NewGoodFragment mNewGoodFragment;
    BoutiqueFragment mBoutiqueAdapter;
    CategoryFragment mCategoryFragment;
    PersonalCenterFragment mPersonalCenterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fulicenter_man);
        initView();
        inigFragment();
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction().
                add(R.id.fragment_container, mNewGoodFragment)
                .add(R.id.fragment_container, mBoutiqueAdapter)
                .add(R.id.fragment_container, mCategoryFragment)
//                .add(R.id.fragment_container, mPersonalCenterFragment)
                .hide(mBoutiqueAdapter).hide(mCategoryFragment)
//                .hide(mPersonalCenterFragment)
                .show(mNewGoodFragment)
                .commit();

    }

    private void inigFragment() {
        mNewGoodFragment = new NewGoodFragment();
        mBoutiqueAdapter = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();
        mFragment = new Fragment[5];
        mFragment[0] = mNewGoodFragment;
        mFragment[1] = mBoutiqueAdapter;
        mFragment[2] = mCategoryFragment;
        mFragment[4] = mPersonalCenterFragment;

    }

    private void initView() {
        rbNewGood = (RadioButton) findViewById(R.id.layout_new_good);
        rbBoutique = (RadioButton) findViewById(R.id.layout_boutique);
        rbCategory = (RadioButton) findViewById(R.id.layout_category);
        rbCart = (RadioButton) findViewById(R.id.layout_cart);
        rbPersonalCenter = (RadioButton) findViewById(R.id.layout_personal_center);

        tvCartHint = (TextView) findViewById(R.id.tvCarHint);
        mrbTabs = new RadioButton[5];
        mrbTabs[0] = rbNewGood;
        mrbTabs[1] = rbBoutique;
        mrbTabs[2] = rbCategory;
        mrbTabs[3] = rbCart;
        mrbTabs[4] = rbPersonalCenter;

        mNewGoodFragment = new NewGoodFragment();



    }

    public void onChecaedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_new_good:
                index = 0;
                break;
            case R.id.layout_boutique:
                index = 1;
                break;
            case R.id.layout_category:
                index = 2;
                break;
            case R.id.layout_cart:
                index = 3;
                break;
            case R.id.layout_personal_center:
                if (DemoHXSDKHelper.getInstance().isLogined()) {
                    index = 4;
                } else {
                    gotoLogin();//
                }
                break;
        }
        Log.e(TAG, "index = " + index+"  , currentIndex  = " + currentIndex);
        //  判断 点击下标
        setmFragment();
    }

    private void setmFragment() {
        Log.e(TAG, "index1231321 = " + index+"  , currentIndex123123  = " + currentIndex);
        if (index != currentIndex) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(mFragment[currentIndex]);
            if (!mFragment[index].isAdded()) {
                trx.add(R.id.fragment_container, mFragment[index]);
            }
            trx.show(mFragment[index]).commit();
            setRadioButtonStatus(index);
            currentIndex = index;
        }
    }
    // 跳转Activity
    private void gotoLogin() {
        startActivityForResult(new Intent(this, LoginActivity.class),ACTION_LOGIN);
    }


    public void setRadioButtonStatus(int index) {
      for (int i=0;i<mrbTabs.length;i++) {
          if (index == i) {
              mrbTabs[i].setChecked(true);
          } else {
              mrbTabs[i].setChecked(false);
          }
      }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResultA");
        if (requestCode == ACTION_LOGIN) {
            if (DemoHXSDKHelper.getInstance().isLogined()) {

            } else {
                setRadioButtonStatus(currentIndex);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onRseume");
        if (DemoHXSDKHelper.getInstance().isLogined()) {

        } else {
            index = currentIndex;
            if (index == 4) {
                index=0;
            }
            setmFragment();
        }
    }
}
