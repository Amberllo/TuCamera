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
import android.os.CountDownTimer;
import android.widget.Toast;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.secret.StatisticsManger;
import org.lasque.tusdk.core.seles.tusdk.FilterManager;
import org.lasque.tusdk.core.seles.tusdk.FilterManager.FilterManagerDelegate;
import org.lasque.tusdk.impl.activity.TuFragmentActivity;
import org.lasque.tusdk.modules.components.ComponentActType;
import com.share.photoshare.R;
import com.share.photoshare.custom.suite.CameraAndEditorComponent;
import com.share.photoshare.examples.feature.CameraAndEditorSample;

/**
 * @author Clear
 */
public class CameraEntryActivity extends TuFragmentActivity
{

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new CameraAndEditorComponent().showSample(this);
    }



}