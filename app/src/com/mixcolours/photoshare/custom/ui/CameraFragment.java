/** 
 * TuSdkDemo
 * DemoEntryActivity.java
 *
 * @author 		Clear
 * @Date 		2014-11-15 下午4:30:52 
 * @Copyright 	(c) 2014 tusdk.com. All rights reserved.
 * @link 		开发文档:http://tusdk.com/docs/android/api/
 */
package com.mixcolours.photoshare.custom.ui;

import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;

import org.lasque.tusdk.core.view.TuSdkImageView;
import org.lasque.tusdk.core.view.TuSdkViewHelper;
import org.lasque.tusdk.impl.components.camera.TuCameraFragment;
import com.mixcolours.photoshare.R;
import com.mixcolours.photoshare.custom.suite.AlbumEditMultipleComponent;

/**
 * @author Amberllo
 * 自定义相机页面
 */
public class CameraFragment extends TuCameraFragment
{

    /**
     * 布局ID
     */

    public static int getLayoutViewId(){
        return R.layout.custom_camera_fragment_layout;
    }

    @Override
    public void setRootViewLayoutId(int i) {
        super.setRootViewLayoutId(getLayoutViewId());
    }

    @Override
    protected void loadView(ViewGroup view)
    {
        super.loadView(view);
        TuSdkImageView albumButton = (TuSdkImageView)view.findViewById(R.id.lsq_albumPosterView);
        albumButton.setOnClickListener(new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(View view) {
                new AlbumEditMultipleComponent().showSample(getActivity());
            }
        });

        TuSdkImageView switchButton = (TuSdkImageView)view.findViewById(R.id.lsq_switchButton1);
        switchButton.setOnClickListener(new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(View view) {
                handleSwitchButton();
            }
        });


        TuSdkImageView squareButton = (TuSdkImageView)view.findViewById(R.id.lsq_squareButton);
        squareButton.setOnClickListener(new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(View view) {

            }
        });

        TuSdkImageView flashButton = (TuSdkImageView)view.findViewById(R.id.lsq_flashButton1);
        flashButton.setOnClickListener(new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(View view) {
                handleFlashButton();
            }
        });

    }


    @Override
    public void hubSuccess(String var1) {
//        TuSdk.messageHub().showSuccess(this.getActivity(), var1);
    }

    @Override
    public void hubSuccess(int var1) {
//        TuSdk.messageHub().showSuccess(this.getActivity(), var1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.exit(0);
//        getActivity().finish();
//        System.out.println("onDestroy");
    }
}