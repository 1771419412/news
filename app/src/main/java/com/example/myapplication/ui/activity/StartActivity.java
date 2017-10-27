package com.example.myapplication.ui.activity;

import android.os.SystemClock;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.util.SharedPrefUtil;

public class StartActivity extends BaseActivity {

    @Override
    public int getLayoutR(int id) {
        return R.layout.activity_start;
    }

    @Override
    protected void initViews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                boolean firstRun= SharedPrefUtil.getBoolean(getApplicationContext(),"firstRun",true);
                if(firstRun){
                    SharedPrefUtil.saveBoolean(StartActivity.this,"firstRun",false);
                    goTo(GuideActivity.class);
                }else {
                    goTo(NewsActivity.class);
                }

            }
        }).start();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initDate() {

    }
}
