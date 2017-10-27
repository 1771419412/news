package com.example.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.NewsEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 雪无痕 on 2017/6/28.
 */

public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    /*列表显示的新闻数据*/
    private List<NewsEntity.ResultBean> mBeanList;
    private ImageView imgIcon;
    private TextView tvTitle;
    private TextView tvSource;
    private TextView tvComment;
    private ImageView img01;
    private ImageView img02;
    private ImageView img03;

    private TextView tvTitle2;

    private TextView tvComment2;

    private View mView;

    public NewsAdapter(Context context, List<NewsEntity.ResultBean> beanList) {
        mContext = context;
        mBeanList = beanList;
    }

    @Override
    public int getCount() {
        return (mBeanList == null) ? 0 : mBeanList.size();
    }

    @Override
    public NewsEntity.ResultBean getItem(int position) {
        return mBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NewsEntity.ResultBean info = (NewsEntity.ResultBean) getItem(position);

        int itemViewType = getItemViewType(position);
        if (itemViewType == ITEM_TYPE_WITH_1_IMAGE) {

            if (convertView == null) {
                mView = View.inflate(mContext, R.layout.item_news, null);
            }else {
                mView=convertView;
            }

            imgIcon = (ImageView) mView.findViewById(R.id.img_icon);
            tvTitle = (TextView) mView.findViewById(R.id.tv_title);
            tvSource = (TextView) mView.findViewById(R.id.tv_source);
            tvComment = (TextView) mView.findViewById(R.id.tv_comment);

            tvTitle.setText(info.getTitle());
            tvSource.setText(info.getSource());
            tvComment.setText(info.getReplyCount() + "跟帖");
            Picasso.with(mContext).load(info.getImgsrc()).into(imgIcon);
        } else if (itemViewType == ITEM_TYPE_WITH_3_IMAGE) {
            if (convertView == null) {
                mView = View.inflate(mContext, R.layout.item_news_02, null);
            }else {
                mView=convertView;
            }

            tvTitle2 = (TextView) mView.findViewById(R.id.tv_title2);

            tvComment2 = (TextView) mView.findViewById(R.id.tv_comment2);
            img01 = (ImageView) mView.findViewById(R.id.img_01);
            img02 = (ImageView) mView.findViewById(R.id.img_02);
            img03 = (ImageView) mView.findViewById(R.id.img_03);
            tvTitle2.setText(info.getTitle());
            tvComment2.setText(info.getReplyCount()+"跟帖");
            try{

                Picasso.with(mContext).load(info.getImgsrc()).into(img01);
                Picasso.with(mContext).load(info.getImgextra().get(0).getImgsrc()).into(img02);
                Picasso.with(mContext).load(info.getImgextra().get(1).getImgsrc()).into(img03);
            }catch (Exception e){
                e.printStackTrace();
            }


        }


        return mView;
    }

    //================多种item的列表显示(begin)=======================
    private static final int ITEM_TYPE_WITH_1_IMAGE = 0;
    private static final int ITEM_TYPE_WITH_3_IMAGE = 1;

    @Override
    public int getItemViewType(int position) {
        NewsEntity.ResultBean item = getItem(position);
        if (item.getImgextra() == null || item.getImgextra().size() == 0) {
            // 只有一张图片的item
            return ITEM_TYPE_WITH_1_IMAGE;
        } else {
            // item中有三张图片
            return ITEM_TYPE_WITH_3_IMAGE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //================多种item的列表显示(end)=========================


    public void setDatas(List<NewsEntity.ResultBean> dataList) {
        this.mBeanList=dataList;
        notifyDataSetChanged();
    }

    public void addDatas(List<NewsEntity.ResultBean> dataList) {
        this.mBeanList.addAll(dataList);
        notifyDataSetChanged();
    }
}
