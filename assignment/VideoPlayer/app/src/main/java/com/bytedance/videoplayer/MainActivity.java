package com.bytedance.videoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.imageView);
        String url = "https://s3.pstatp.com/toutiao/static/img/logo.271e845.png";
        Glide.with(this).load(url).into(imageView);

        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToVideoPlayer();
            }
        },3000);

    }
    private void jumpToVideoPlayer(){
        startActivity(new Intent(this, VideoActivity.class));
    }
}
