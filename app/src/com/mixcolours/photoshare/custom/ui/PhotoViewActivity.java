package com.mixcolours.photoshare.custom.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mixcolours.photoshare.R;
import com.mixcolours.photoshare.photoview.PhotoView;

/**
 * Created by LYL on 2016/8/10.
 */
public class PhotoViewActivity extends Activity {

    PhotoView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.custom_photoview);
        imageView = (PhotoView) findViewById(R.id.lsq_photoview_image);
        imageView.enable();
    }

}
