/** 
 * TuSdkDemo
 * DemoEntryActivity.java
 *
 * @author 		Clear
 * @Date 		2014-11-15 下午4:30:52 
 * @Copyright 	(c) 2014 tusdk.com. All rights reserved.
 * @link 		开发文档:http://tusdk.com/docs/android/api/
 */
package org.lasque.tusdkdemo.custom;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.seles.tusdk.FilterManager;
import org.lasque.tusdk.core.seles.tusdk.FilterManager.FilterManagerDelegate;
import org.lasque.tusdk.impl.activity.TuFragmentActivity;
import org.lasque.tusdkdemo.R;
import org.lasque.tusdkdemo.custom.suite.CameraComponent;

/**
 * @author Amberllo
 */
public class CameraEntryActivity extends TuFragmentActivity{

	/** 初始化控制器 */
	@Override
	protected void initActivity(){
		super.initActivity();
	}


	/**
	 * 初始化视图
	 */
	@Override
	protected void initView(){
		super.initView();

		
		// 异步方式初始化滤镜管理器 (注意：如果需要一开启应用马上执行SDK组件，需要做该检测，否则可以忽略检测)
		// 需要等待滤镜管理器初始化完成，才能使用所有功能
		TuSdk.messageHub().setStatus(this, R.string.lsq_initing);
		TuSdk.checkFilterManager(mFilterManagerDelegate);

        //进入页面直接打开相机
        showCameraComponent();

	}

	/** 滤镜管理器委托 */
	private FilterManagerDelegate mFilterManagerDelegate = new FilterManagerDelegate(){
		@Override
		public void onFilterManagerInited(FilterManager manager){
			TuSdk.messageHub().showSuccess(CameraEntryActivity.this, R.string.lsq_inited);
		}
	};


	/** 打开相机组件 */
	private void showCameraComponent(){
		new CameraComponent().showSample(this);
	}

}