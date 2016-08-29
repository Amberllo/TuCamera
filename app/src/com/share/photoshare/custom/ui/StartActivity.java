package com.share.photoshare.custom.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.view.KeyEvent;

import com.share.photoshare.DemoEntryActivity;
import com.share.photoshare.R;

/**
 * Created by LYL on 2016/8/10.
 */
public class StartActivity extends Activity{
    CountDownTimer timer = new CountDownTimer(1500,1500) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            finish();
            startActivity(new Intent(StartActivity.this,CameraEntryActivity.class));
//            startActivity(new Intent(StartActivity.this,DemoEntryActivity.class));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_start);
        timer.cancel();
        timer.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            return false;
        } else if(keyCode == KeyEvent.KEYCODE_MENU) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
