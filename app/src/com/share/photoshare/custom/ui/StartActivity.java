package com.share.photoshare.custom.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.share.photoshare.R;
import com.share.photoshare.custom.suite.CameraAndEditorComponent;
import org.lasque.tusdk.impl.activity.TuFragmentActivity;

/**
 * Created by LYL on 2016/8/10.
 */
public class StartActivity extends TuFragmentActivity {
    Handler handler = new Handler();
    CountDownTimer timer = new CountDownTimer(1000,2000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            new CameraAndEditorComponent().showSample(StartActivity.this);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imageView.setVisibility(View.GONE);
                }
            },2000);
        }
    };
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = getViewById(R.id.lsq_welcome_image);
        timer.cancel();
        timer.start();
    }

    /** 初始化控制器 */
    @Override
    protected void initActivity()
    {
        super.initActivity();
        this.setRootView(R.layout.custom_start, 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

}
