/** 
 * TuSdkDemo
 * DemoEntryActivity.java
 *
 * @author 		Clear
 * @Date 		2014-11-15 下午4:30:52 
 * @Copyright 	(c) 2014 tusdk.com. All rights reserved.
 * @link 		开发文档:http://tusdk.com/docs/android/api/
 */
package org.lasque.tusdkdemo.custom.suite;

import android.view.View;
import android.view.ViewGroup;
import org.lasque.tusdk.impl.components.camera.TuCameraFragment;
import org.lasque.tusdkdemo.R;

/**
 * @author Amberllo
 * 自定义相机页面
 */
public class CameraFragment extends TuCameraFragment implements View.OnClickListener
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

        // 在这里使用 getViewById()方法找到新添加的视图
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}