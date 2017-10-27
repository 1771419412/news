package com.example.myapplication.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import static android.R.attr.id;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutR(id));
        initViews();
        initListener();
        initDate();

    }

    public abstract int getLayoutR(int id) ;

    protected abstract void initViews();

    protected abstract void initListener();

    protected abstract void initDate();
    public void goTo(Class activity){
        Intent intent=new Intent(this,activity);
        startActivity(intent);
        finish();
    }
    private Toast mToast;
    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
