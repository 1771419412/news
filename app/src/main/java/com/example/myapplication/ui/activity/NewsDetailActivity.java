package com.example.myapplication.ui.activity;

import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.bean.NewsEntity;

public class NewsDetailActivity extends BaseActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;
    @Override
    public int getLayoutR(int id) {
        return R.layout.activity_newsdetail;
    }

    @Override
    protected void initViews() {
        mProgressBar= (ProgressBar) findViewById(R.id.pb_01);

        initWebView();
    }

    private void initWebView() {
        mWebView= (WebView) findViewById(R.id.web_view);
        //禁止 使用外部浏览器
        mWebView.setWebViewClient(new WebViewClient());
        //设置webview支持的javascript脚本
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        //显示网页加载进度
        mWebView.setWebChromeClient(new WebChromeClient(){



            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){//加载完成  隐藏进度条
                    mProgressBar.setVisibility(View.GONE);
                }else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }

            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initDate() {

        NewsEntity.ResultBean resultBean= (NewsEntity.ResultBean) getIntent().getSerializableExtra("news");
        mWebView.loadUrl(resultBean.getUrl());

        if(getSupportActionBar()!=null){
            //显示标题栏左上角的返回图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            //显示标题栏
            getSupportActionBar().setTitle(resultBean.getTitle());
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
