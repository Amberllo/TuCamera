/** 
 * TuSdkDemo
 * DemoEntryActivity.java
 *
 * @author 		Clear
 * @Date 		2014-11-15 下午4:30:52 
 * @Copyright 	(c) 2014 tusdk.com. All rights reserved.
 * @link 		开发文档:http://tusdk.com/docs/android/api/
 */
package org.lasque.tusdkdemo.custom.ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import org.lasque.tusdk.core.activity.ActivityHelper;
import org.lasque.tusdk.core.activity.ActivityObserver;
import org.lasque.tusdk.core.view.TuSdkImageView;
import org.lasque.tusdk.impl.components.camera.TuCameraFragment;
import org.lasque.tusdkdemo.R;
import org.lasque.tusdkdemo.custom.suite.AlbumEditMultipleComponent;

/**
 * @author Amberllo
 * 自定义相机页面
 */
public class CameraFragment extends TuCameraFragment
{

    /**
     * 布局ID
     */
    public static int getLayoutId()
    {
        return R.layout.custom_camera_fragment_layout;
    }

    @Override
    protected void loadView(ViewGroup view)
    {
        super.loadView(view);
        TuSdkImageView albumButton = (TuSdkImageView)view.findViewById(R.id.lsq_albumPosterView);
        albumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlbumEditMultipleComponent().showSample(getActivity());
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.exit(0);
    }
}