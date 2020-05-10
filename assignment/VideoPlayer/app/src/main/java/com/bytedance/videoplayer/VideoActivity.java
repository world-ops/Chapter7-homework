package com.bytedance.videoplayer;

        import android.annotation.SuppressLint;
        import android.content.res.Configuration;
        import android.media.AudioTrack;
        import android.media.MediaPlayer;
        import android.net.IpSecManager;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.MediaController;
        import android.widget.RelativeLayout;
        import android.widget.SeekBar;
        import android.widget.TextView;
        import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private SeekBar seekBar;
    private TextView tv_now;
    private TextView tv_all;
    private Button button_fullscreen;
    private String path;
    private int screen_width, screen_height;
    private boolean screen_flag = true;//判断屏幕转向

    private Handler handler = new Handler();
    private Runnable run =  new Runnable() {
        int  currentPosition, duration;
        public void run() {
            // 获得当前播放时间和当前视频的长度
            currentPosition = videoView.getCurrentPosition();
            duration = videoView.getDuration();
            int time = ((currentPosition * 100) / duration);
            // 设置进度条的主要进度，表示当前的播放时间
            seekBar.setProgress(time);
            tv_now.setText(msecToTime(currentPosition));
            handler.postDelayed(run, 1000);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setTitle("VideoView");
        tv_now = findViewById(R.id.currentTime);
        tv_all = findViewById(R.id.totalTime);


        Button buttonPause = findViewById(R.id.buttonPause);
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
            }
        });

        Button buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
            }
        });


        seekBar = findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            boolean isTouch = false;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (isTouch){
                    int process = seekBar.getProgress();
                    if (videoView != null) {
                        videoView.seekTo(process  * videoView.getDuration()/ 100);
                        Log.d("linyuebei", "onProgressChanged: "+process+"-----"+videoView.getDuration()+"____"+process * videoView.getDuration() / 100 );
                    }
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTouch = true;
                videoView.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isTouch = false;
                videoView.start();
            }
        });



        button_fullscreen = findViewById(R.id.button_fullscreen);
        button_fullscreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });


        videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(getVideoPath(R.raw.bytedance));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //videoView.setMediaController(new MediaController(videoView.this));//系统自带的视频控制条

                int time_all = videoView.getDuration();
                tv_all.setText(msecToTime(time_all));
                tv_now.setText("00:00:00");
                handler.postDelayed(run,1000);
            }
        });
    }

    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }
    private String msecToTime(int time){
        String timeStr;
        int hour;
        if(time <= 0)
            return "00:00";
        else{
            int second = time / 1000;
            int minute = second / 60;
            if(second < 60){
                timeStr = "00:00:" + unitFormat(second);
            }
            else if (minute < 60){
                second = second % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            }
            else {
                hour = minute / 60;
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }

        }
        return timeStr;
    }
    private String unitFormat(int i) {
        //时分秒的格式转换
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


}
