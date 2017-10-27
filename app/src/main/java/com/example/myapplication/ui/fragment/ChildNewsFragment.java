package com.example.myapplication.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.myapplication.R;
import com.example.myapplication.adapter.NewsAdapter;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.bean.NewsEntity;
import com.example.myapplication.ui.activity.NewsDetailActivity;
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

public class ChildNewsFragment extends BaseFragment {
    /*新闻类别id*/
    private String channelId;
    private TextView mTextView;
    private ListView mListView;
    private SliderLayout mSliderLayout;
    private SpringView mSpringView;
    private View mHeadView;
    private List<NewsEntity.ResultBean> mDataList;
    private NewsAdapter mNewsAdapter;

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_child_news;
    }

    @Override
    protected void initViews() {

        mListView = (ListView) mRootView.findViewById(R.id.list_view);
        mSpringView = (SpringView) mRootView.findViewById(R.id.spring_view);
        mNewsAdapter = new NewsAdapter(getContext(), null);
        mListView.setAdapter(mNewsAdapter);

    }

    @Override
    protected void initListener() {

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //有头部才需要减一
                int index = position;
                if (mListView.getHeaderViewsCount() > 0) {
                    index = index - 1;
                }
                //用户点击的新闻
                NewsEntity.ResultBean resultBean = (NewsEntity.ResultBean) parent.getItemAtPosition(position);
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra("news", resultBean);
                startActivity(intent);

            }

        });

    }

    @Override
    protected void initDate() {
        getDataFromServer(true);
        getSpringView();
    }

    private void getSpringView() {
        mSpringView.setHeader(new RotationHeader(getContext()));
        mSpringView.setFooter(new RotationFooter(getContext()));

        //设置监听器
        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                getDataFromServer(true);
                //下拉
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSpringView.onFinishFreshAndLoad();
                    }
                }, 2500);
            }

            @Override
            public void onLoadmore() {
                //上拉
                getDataFromServer(false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSpringView.onFinishFreshAndLoad();
                    }
                }, 2500);
            }
        });
    }

    /*加载第几页数据*/
    private int pageNo = 1;

    /**
     * @param refresh true 表示下拉刷新
     */
    private void getDataFromServer(final boolean refresh) {


        //请求服务器获取详细数据
        String url = URLManger.getUrl(channelId, pageNo);
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                Log.e(TAG, "---服务器返回的json数据：" + json);
                json = json.replace(channelId, "result");
                Gson gson = new Gson();
                NewsEntity newsEntity = gson.fromJson(json, NewsEntity.class);
                Log.e(TAG, "---json解析：" + newsEntity.getResult().size());
                mDataList = newsEntity.getResult();
                if (refresh) {
                    // 显示数据到列表中
                    pageNo = 1;
                    showDatas(mDataList);
                } else {
                    mNewsAdapter.addDatas(mDataList);
                }
                pageNo++;
                mSpringView.onFinishFreshAndLoad();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }
        });

    }

    private void showDatas(List<NewsEntity.ResultBean> newsEntity) {
        /*if(newsEntity==null || newsEntity.getResult()==null || newsEntity.getResult().size()==0){

            Log.e(TAG, "-----没有获取到新闻数据 " );
            return;
        }*/
        //如果有头部  就移除
        if (mListView.getHeaderViewsCount() > 0) {
            mListView.removeHeaderView(mHeadView);
        }
        //显示轮播图片
        NewsEntity.ResultBean firstNews = mDataList.get(0);
        //有轮播广告
        if (firstNews.getAds() != null && firstNews.getAds().size() > 0) {
            mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.list_header, mListView, false);
            mSliderLayout = (SliderLayout) mHeadView.findViewById(R.id.slider_layout);
            List<NewsEntity.ResultBean.AdsBean> ads = firstNews.getAds();
            for (int i = 0; i < ads.size(); i++) {
                //一则广告数据

                NewsEntity.ResultBean.AdsBean adsBean = ads.get(i);
                TextSliderView sliderView = new TextSliderView(mActivity);
                sliderView.description(adsBean.getTitle()).image(adsBean.getImgsrc());

                mSliderLayout.addSlider(sliderView);

                sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        showToast(slider.getDescription());
                    }
                });
            }
            //添加列表头布局
            mListView.addHeaderView(mHeadView);
        }else {

        }

        //显示新闻列
        mNewsAdapter.setDatas(newsEntity);
    }

    @Override
    public void onPause() {
        super.onPause();
        /*JCVideoPlayer.releaseAllVideos();*/
    }
}
