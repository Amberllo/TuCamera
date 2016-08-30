package com.share.photoshare.custom.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.share.photoshare.R;
import com.share.photoshare.custom.suite.CameraAndEditorComponent;
import org.lasque.tusdk.impl.activity.TuFragmentActivity;

/**
 * Created by LYL on 2016/8/10.
 */
public class StartActivity extends TuFragmentActivity {
    CountDownTimer timer = new CountDownTimer(1000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            new CameraAndEditorComponent().showSample(StartActivity.this);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer.cancel();
        timer.start();
    }

    /** 初始化控制器 */
    @Override
    protected void initActivity()
    {
        super.initActivity();
        this.setRootView(R.layout.custom_start, 0);
//        this.setTheme(setTheme(););
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

}
