package com.example.myapplication.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by 雪无痕 on 2017/6/27.
 */

public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    protected Activity mActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(mRootView ==null){
            mRootView =LayoutInflater.from(mActivity)
                    .inflate(getLayoutRes(),container,false);
            initViews();
            initListener();
            initDate();
        }

        return mRootView;
    }

    protected abstract int getLayoutRes();

    protected abstract void initViews();

    protected abstract void initListener();

    protected abstract void initDate();
    private Toast mToast;
    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mActivity, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
