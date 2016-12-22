package com.mixcolours.photoshare.custom.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mixcolours.photoshare.R;
import com.mixcolours.photoshare.WelcomeActivity;

/**
 * Created by LYL on 2016/8/10.
 */
public class StartActivity extends Activity {
    Handler handler = new Handler();
    CountDownTimer timer = new CountDownTimer(1000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imageView.setVisibility(View.GONE);
                    finish();
                }
            },1000);

            Intent i = new Intent(StartActivity.this,WelcomeActivity.class);
            i.putExtra("wantFullScreen",true);
            startActivity(i);

        }
    };
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.custom_start);
        imageView = (ImageView) findViewById(R.id.lsq_welcome_image);
        timer.cancel();
        timer.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

}
