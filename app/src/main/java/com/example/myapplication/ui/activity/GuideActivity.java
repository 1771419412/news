package com.example.myapplication.ui.activity;

import android.animation.Animator;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;

public class GuideActivity extends BaseActivity {

    private static final long OL = 1;
    private ImageView imgGuide;
    private Button btnGuide;
    private int count=0;
    private int[] imageArray=new int[]{
      R.drawable.ad_new_version1_img1,
            R.drawable.ad_new_version1_img2,
            R.drawable.ad_new_version1_img3,
            R.drawable.ad_new_version1_img4,
            R.drawable.ad_new_version1_img5,
            R.drawable.ad_new_version1_img6,
            R.drawable.ad_new_version1_img7,
    };
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    startAnimation();
                    break;
            }
        }
    };
    private MediaPlayer mMediaPlayer;
    private void playBackgroundMusic(){
        try {

            mMediaPlayer=MediaPlayer.create(this,R.raw.new_version);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.setVolume(1.0f,1.0f);
           // mMediaPlayer.prepare();
            mMediaPlayer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        playBackgroundMusic();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mMediaPlayer!=null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }

    private void startAnimation() {
        count++;
        count=count%imageArray.length;
        imgGuide.setBackgroundResource(imageArray[count]);

        imgGuide.setScaleX(1.0f);
        imgGuide.setScaleY(1.0f);

        imgGuide.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(3500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                            mHandler.sendEmptyMessageDelayed(0,1000);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
    }

    @Override
    public int getLayoutR(int id) {
        return R.layout.activity_guide;
    }

    @Override
    protected void initViews() {
        imgGuide = (ImageView) findViewById(R.id.img_guide);
        btnGuide = (Button) findViewById(R.id.btn_guide);

    }

    @Override
    protected void initListener() {
        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(NewsActivity.class);
            }
        });
    }

    @Override
    protected void initDate() {
        startAnimation();//显示动画
    }

}
