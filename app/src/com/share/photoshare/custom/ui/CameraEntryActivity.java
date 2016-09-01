/** 
 * TuSdkDemo
 * DemoEntryActivity.java
 *
 * @author 		Clear
 * @Date 		2014-11-15 下午4:30:52 
 * @Copyright 	(c) 2014 tusdk.com. All rights reserved.
 * @link 		开发文档:http://tusdk.com/docs/android/api/
 */
package com.share.photoshare.custom.ui;

import android.os.Bundle;
import android.view.View;
import org.lasque.tusdk.impl.activity.TuFragmentActivity;
import com.share.photoshare.R;
import com.share.photoshare.custom.suite.CameraAndEditorComponent;

/**
 * @author Clear
 */
public class CameraEntryActivity extends TuFragmentActivity
{

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        root = getViewById(R.id.lsq_camera_activity_layout);

        new CameraAndEditorComponent().showSample(this);
    }

    View root;
    @Override
    protected void initActivity() {
        super.initActivity();
        this.setRootView(R.layout.custom_activity_home,0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
    }
}