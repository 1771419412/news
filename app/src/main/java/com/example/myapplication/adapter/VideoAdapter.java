package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.VideoEntity;
import com.example.myapplication.ui.activity.VideoPlayActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static android.content.ContentValues.TAG;

/**
 * Created by 雪无痕 on 2017/6/29.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{

    private Context mContext;
    private List<VideoEntity.ResultBean> mBeanList;

    public VideoAdapter(Context context, List<VideoEntity.ResultBean> beanList) {
        mContext = context;
        mBeanList = beanList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_video,parent,false);
        VideoViewHolder viewHolder=new VideoViewHolder(view) ;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {
        final VideoEntity.ResultBean video=mBeanList.get(position);
        //预加载缩列图
        Picasso.with(mContext).load(video.getCover()).into(holder.mImgVideo);
        //先是标题
        holder.mTvVideoTitle.setText(mBeanList.get(position).getTitle());
        //显示视频播放长度
        Log.e(TAG, "--------视频时长"+video.getLength() );
        String duration= DateFormat.format("mm:ss",video.getLength()*1000).toString();
        holder.mTvVideoDuration.setText(duration);

        //显示播放次数
        holder.mTvVideoPlay.setText(video.getPlayCount()+"");
        //参数1是视频的url
        //参数2是视频的标题
        holder.mVideoPlayer.setUp(video.getMp4_url(),video.getTitle());
        //加载图片作为视频的显示图片
        Picasso.with(mContext)
                .load(video.getCover())
                .into(holder.mVideoPlayer.ivThumb);

        Log.e(TAG, "------ship "+video.getCover() );


        //点击列表，跳转进入视频播放详情页
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, VideoPlayActivity.class);
                intent.putExtra("video_url",video.getMp4_url());
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return (mBeanList==null)?0:mBeanList.size();
    }

    public void addDatas(List<VideoEntity.ResultBean> videoList) {
        this.mBeanList.addAll(videoList);
        notifyDataSetChanged();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgVideo;
        private TextView mTvVideoTitle;
        private TextView mTvVideoDuration;
        private TextView mTvVideoPlay;
        private JCVideoPlayerStandard mVideoPlayer;

        public VideoViewHolder(View itemView) {
            super(itemView);

            mImgVideo= (ImageView) itemView.findViewById(R.id.img_video);
            mTvVideoTitle= (TextView) itemView.findViewById(R.id.tv_video_title);
            mTvVideoDuration= (TextView) itemView.findViewById(R.id.tv_video_duration);
            mTvVideoPlay= (TextView) itemView.findViewById(R.id.tv_video_play);
            mVideoPlayer= (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer_standard);

        }
    }
}