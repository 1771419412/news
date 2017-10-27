package com.example.myapplication.ui.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;

public class VideoPlayActivity extends BaseActivity {
    private VideoView mVideoView;
    private ProgressBar mProgressBar;
    private SeekBar mSeekBar;
    private GestureDetector mGestureDetector;
    private TextView timeStart;


    @Override
    public int getLayoutR(int id) {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initViews() {
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_02);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bak);
        timeStart= (TextView) findViewById(R.id.video_view_time);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void initListener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override  //正在
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //true 表示用户拖动seeknbar  导致 进度改变
                if (fromUser) {
                    //快进快退（从指定的位置开始播放）

                    mVideoView.seekTo(progress);
                }
            }

            @Override  //开始
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override  //结束
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mGestureDetector = new GestureDetector(this, onGestureListener);

    }

    private GestureDetector.OnGestureListener onGestureListener
            = new GestureDetector.SimpleOnGestureListener() {


        @Override //长按
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override//双击
        public boolean onDoubleTap(MotionEvent e) {

            if (mVideoView.isPlaying()) {
                mVideoView.pause();
            } else {
                mVideoView.start();
            }

            return true;
        }
    };


    @Override
    protected void initDate() {


        Uri videoUri = getIntent().getData();
        if (videoUri == null) {
            String videoUrl = getIntent().getStringExtra("video_url");
            //设置播放路径
            mVideoView.setVideoPath(videoUrl);
        } else {
            mVideoView.setVideoURI(videoUri);
        }

        //设置监听  缓冲
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
                //总时长
                mSeekBar.setMax(mVideoView.getDuration());
                //更新seekbar的进度
                updateVideoPosition();
                mProgressBar.setVisibility(View.GONE);


            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    updateVideoPosition();
                    break;
            }
        }
    };

    //更新seekbar的进度
    private void updateVideoPosition() {
        //设置seekbar的进度
        mSeekBar.setProgress(mVideoView.getCurrentPosition());
        //每隔0.3秒  刷新一次
        mHandler.sendEmptyMessageDelayed(0, 300);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出activity界面时 需要删除消息  退出handle 死循环，防止内存泄漏
        mHandler.removeCallbacksAndMessages(null);//删除所有的消息

    }
}
