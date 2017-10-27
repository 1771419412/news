package com.example.myapplication.ui.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.adapter.VideoAdapter;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.bean.VideoEntity;
import com.example.myapplication.util.URLManger;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by 雪无痕 on 2017/6/27.
 */

public class VideoFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private VideoAdapter mVideoAdapter;
    private List<VideoEntity.ResultBean> mVideoList;
    private SpringView mSpringView;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initViews() {
        mSpringView= (SpringView) mRootView.findViewById(R.id.spring_view02);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initDate() {
        getVideoData(true);
        getSpringView();
    }

    private void getSpringView() {
        mSpringView.setHeader(new RotationHeader(getContext()));
        mSpringView.setFooter(new RotationFooter(getContext()));

        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //下拉
                getVideoData(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSpringView.onFinishFreshAndLoad();
                    }
                },2000);
            }

            @Override
            public void onLoadmore() {
                getVideoData(false);
                //上拉
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSpringView.onFinishFreshAndLoad();
                    }
                },2000);
            }
        });
    }
    /*加载第几页数据*/
    private int pageNo = 1;
    private void getVideoData(final boolean refresh) {
       final String VideoURL=URLManger.getVideoURL(pageNo);
        new HttpUtils().send(HttpRequest.HttpMethod.GET, VideoURL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                    String json =responseInfo.result;
                Log.e(TAG, "----视频数据"+json);
                json =  json.replace("V9LG4B3A0", "result");

                Gson gson=new Gson();
                VideoEntity videoEntity=gson.fromJson(json,VideoEntity.class);
                mVideoList=videoEntity.getResult();
                Log.e(TAG, "------解析视频json:"+videoEntity.getResult().size() );
                if (refresh) {
                    // 显示数据到列表中
                    pageNo = 1;
                    showDatas(videoEntity);
                } else {
                    mVideoAdapter.addDatas(mVideoList);
                }
                pageNo++;
                mSpringView.onFinishFreshAndLoad();
                    //TODO
                // showDatas(videoEntity);
            }
                //显示数据



            @Override
            public void onFailure(HttpException error, String msg) {
                    error.printStackTrace();
            }
        });
    }

    private void showDatas(VideoEntity videoEntity) {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        LinearLayoutManager manager=new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(manager);
        mVideoAdapter = new VideoAdapter(mActivity,videoEntity.getResult());
        mRecyclerView.setAdapter(mVideoAdapter);



    }


}
