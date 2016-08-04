package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import cn.ucai.fulicenter.R;

/**
 * Created by Administrator on 2016/8/1.
 */
public class FuliCenterManActivity extends BaseActivity {
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
                .hide(mBoutiqueAdapter).hide(mCategoryFragment)
                .show(mNewGoodFragment)
                .commit();

    }

    private void inigFragment() {
        mNewGoodFragment = new NewGoodFragment();
        mBoutiqueAdapter = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mFragment = new Fragment[5];
        mFragment[0] = mNewGoodFragment;
        mFragment[1] = mBoutiqueAdapter;
        mFragment[2] = mCategoryFragment;
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
                index = 4;
                break;
        }
        Log.e(TAG, "index = " + index+"  , currentIndex  = " + currentIndex);
        //  判断 点击下标
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


    public void setRadioButtonStatus(int index) {
      for (int i=0;i<mrbTabs.length;i++) {
          if (index == i) {
              mrbTabs[i].setChecked(true);
          } else {
              mrbTabs[i].setChecked(false);
          }
      }
    }
}
