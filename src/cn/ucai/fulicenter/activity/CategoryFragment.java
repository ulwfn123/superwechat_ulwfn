package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.CategoryAdapter;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.Utils;

/**
 * Created by Administrator on 2016/8/4.
 */
public class CategoryFragment extends Fragment {
    private static final String TAG = CategoryFragment.class.getSimpleName();
    FuliCenterManActivity mContext;
    ExpandableListView mExpandableListView;
    List<CategoryGroupBean> mGroupList;
    List<ArrayList<CategoryChildBean>> mChildList;
    CategoryAdapter mAdapter;
    int  groupCount;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = (FuliCenterManActivity) getContext();
        final View layout = View.inflate(mContext, R.layout.fragment_category, null);
        mGroupList = new ArrayList<CategoryGroupBean>();
        mChildList = new ArrayList<ArrayList<CategoryChildBean>>();
        initView(layout);
        initData();
        return  layout;
    }

    private void initData() {
        findCategrouyGroupList(new OkHttpUtils2.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                Log.e(TAG, "大类型result=" + result);
                if (result != null) {
                    final ArrayList<CategoryGroupBean> grouyList = Utils.array2List(result);
                    if (grouyList != null) {
                        Log.e(TAG, "大类型grouyList=" + grouyList);
                        mGroupList = grouyList;
                        int i=0;
                        for (CategoryGroupBean g : grouyList) {
                            mChildList.add(new ArrayList<CategoryChildBean>());
                            findCategoryChildList(g.getId(),i);
                            i++;
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, "大类型error=" + error);
            }
        });
    }
    // 下载分类  小类型数据
    private void findCategoryChildList( int catid, final int index) {
        OkHttpUtils2<CategoryChildBean[]> utils = new OkHttpUtils2<CategoryChildBean[]>();
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID,String.valueOf(catid) )
                .addParam(I.PAGE_ID,String.valueOf(I.PAGE_ID_DEFAULT))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CategoryChildBean[].class)
                .execute(new OkHttpUtils2.OnCompleteListener<CategoryChildBean[]>() {
                    @Override
                    public void onSuccess(CategoryChildBean[] result) {
                        Log.e(TAG, "小类型result=" + result);
                        groupCount++;
                        if (result != null) {
                            final ArrayList<CategoryChildBean> childList = Utils.array2List(result);
                            if (childList != null) {
                                mChildList.set(index, childList);
                            }
                        }
                        if (groupCount == mGroupList.size()) {
                            mAdapter.addAll(mGroupList, mChildList);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "大类型error=" + error);
                    }
                });
    }



    // 下载分类 大类型数据
    private void findCategrouyGroupList(OkHttpUtils2.OnCompleteListener<CategoryGroupBean[]> listener ) {
        OkHttpUtils2<CategoryGroupBean[]> utils = new OkHttpUtils2<CategoryGroupBean[]>();
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }
    //  属性初始化
    private void initView(View layout) {
        mAdapter = new CategoryAdapter(mContext,mGroupList,mChildList);
        mExpandableListView = (ExpandableListView) layout.findViewById(R.id.elvCategory);
        mExpandableListView.setGroupIndicator(null);//  设置  大类型 图片前面的下拉图标
        mExpandableListView.setAdapter(mAdapter);

    }



}
