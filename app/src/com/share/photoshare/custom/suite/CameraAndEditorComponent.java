/** 
 * TuSdkDemo
 * CameraAndEditorComponent.java
 *
 * @author 		Yanlin
 * @Date 		2016-1-26 下午1:40:10 
 * @Copyright 	(c) 2015 tusdk.com. All rights reserved.
 * 
 */
package com.share.photoshare.custom.suite;

import android.app.Activity;

import org.lasque.tusdk.TuSdkGeeV1;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdk.core.utils.hardware.CameraHelper;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.TuEditMultipleComponent;
import org.lasque.tusdk.impl.components.camera.TuCameraFragment;
import org.lasque.tusdk.impl.components.camera.TuCameraFragment.TuCameraFragmentDelegate;
import org.lasque.tusdk.impl.components.camera.TuCameraOption;
import org.lasque.tusdk.modules.components.TuSdkComponent.TuSdkComponentDelegate;
import org.lasque.tusdk.modules.components.TuSdkHelperComponent;
import com.share.photoshare.R;
import com.share.photoshare.SampleBase;
import com.share.photoshare.SampleGroup.GroupType;
import com.share.photoshare.custom.ui.CameraFragment;
import com.share.photoshare.custom.ui.EditMultipleFragment;

/**
 * 拍照 + 编辑示例
 * 
 * @author Yanlin
 */
public class CameraAndEditorComponent extends SampleBase implements TuCameraFragmentDelegate
{
	public CameraAndEditorComponent()
	{
		super(GroupType.FeatureSample, R.string.sample_comp_CameraAndEditor);
	}

	@Override
	public void showSample(Activity activity)
	{
		showCamera(activity);
	}
	
	private void showCamera(Activity activity)
	{
		if (activity == null) return;
        // 如果不支持摄像头显示警告信息
        if (CameraHelper.showAlertIfNotSupportCamera(activity)) return;
		TuCameraFragment fragment = getFragment();
		// see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/base/TuSdkHelperComponent.html
		this.componentHelper = new TuSdkHelperComponent(activity);
		// 开启相机
		this.componentHelper.presentModalNavigationActivity(fragment, true);

	}

    public TuCameraFragment getFragment(){

        // 组件选项配置
        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/camera/TuCameraOption.html
        TuCameraOption option = new TuCameraOption();

        // 保存到临时文件 (默认不保存, 当设置为true时, TuSdkResult.imageFile, 处理完成后将自动清理原始图片)
        option.setSaveToTemp(true);

        // 保存到系统相册 (默认不保存, 当设置为true时, TuSdkResult.sqlInfo, 处理完成后将自动清理原始图片)
        option.setSaveToAlbum(true);

        // 照片输出压缩率 (默认:90，0-100 如果设置为0 将保存为PNG格式)
        option.setOutputCompress(100);

        // 是否开启滤镜支持 (默认: 关闭)
        option.setEnableFilters(false);

        // 默认是否显示滤镜视图 (默认: 不显示, 如果mEnableFilters = false, mShowFilterDefault将失效)
        option.setShowFilterDefault(false);

        // 是否保存最后一次使用的滤镜
        option.setSaveLastFilter(true);

        // 自动选择分组滤镜指定的默认滤镜
        option.setAutoSelectGroupDefaultFilter(true);

        // 开启用户滤镜历史记录
        option.setEnableFiltersHistory(false);

        // 开启在线滤镜
        option.setEnableOnlineFilter(false);

        // 显示滤镜标题视图
        option.setDisplayFiltersSubtitles(false);

        option.setDisplayAlbumPoster(true);

        option.setSaveToAlbum(true);

        // 是否开启脸部追踪 (需要相机人脸追踪权限，请访问tusdk.com 控制台开启权限)
        option.enableFaceDetection = true;

        // 控制器类型
        option.setComponentClazz(CameraFragment.class);

        TuCameraFragment fragment = option.fragment();
        fragment.setDelegate(this);
        return fragment;
    }

	/**
	 * 获取一个拍摄结果。
	 * 
	 * 相机的拍摄结果是TuSdkResult对象，依照设置，输出结果可能是 Bitmap、File或者ImageSqlInfo。
	 * 在本例中，拍摄结束后直接关闭了相机界面，依照需求，还可以将拍摄结果作为输入源传给编辑组件，从而实现拍摄编辑一体操作。
	 * 欢迎访问文档中心 http://tusdk.com/doc 查看更多示例。
	 * 
	 * @param fragment
	 *            默认相机视图控制器
	 * @param result
	 *            拍摄结果
	 */
	@Override
	public void onTuCameraFragmentCaptured(TuCameraFragment fragment, TuSdkResult result)
	{
//		fragment.hubDismissRightNow();
//		fragment.dismissActivityWithAnim();
		TLog.d("onTuCameraFragmentCaptured: %s", result);

		// 组件委托
		TuSdkComponentDelegate delegate = new TuSdkComponentDelegate()
		{
			@Override
			public void onComponentFinished(TuSdkResult result, Error error, TuFragment lastFragment)
			{
				TLog.d("onEditMultipleComponentReaded: %s | %s", result, error);
				// 默认输出为 Bitmap  -> result.image
			}
		};

        TuEditMultipleComponent component = new CustomEditMultipleComponent(fragment);
        component.setDelegate(delegate);
		component.componentOption().editMultipleOption().setComponentClazz(EditMultipleFragment.class);
        component.componentOption().editMultipleOption().setSaveToAlbum(true);

		// 指定根视图资源ID
		component.componentOption().editMultipleOption().setRootViewLayoutId(R.layout.custom_multiple_fragment_layout);
		component.componentOption().editFilterOption().setEnableFilterConfig(true);
		component.setImage(result.image)
		// 设置系统照片
				.setImageSqlInfo(result.imageSqlInfo)
				// 设置临时文件
				.setTempFilePath(result.imageFile)
				// 在组件执行完成后自动关闭组件
				.setAutoDismissWhenCompleted(true)
				// 开启组件
				.showComponent();
	}

	/**
	 * 获取一个拍摄结果 (异步方法)
	 * 
	 * @param fragment
	 *            默认相机视图控制器
	 * @param result
	 *            拍摄结果
	 * @return 是否截断默认处理逻辑 (默认: false, 设置为True时使用自定义处理逻辑)
	 */
	@Override
	public boolean onTuCameraFragmentCapturedAsync(TuCameraFragment fragment, TuSdkResult result)
	{
		TLog.d("onTuCameraFragmentCapturedAsync: %s", result);
		return false;
	}

	/**
	 * 请求从相机界面跳转到相册界面。只有 设置mDisplayAlbumPoster为true (默认:false) 才会发生该事件
	 * 
	 * @param fragment
	 *            系统相册控制器
	 */
	@Override
	public void onTuAlbumDemand(TuCameraFragment fragment)
	{

	}

	@Override
	public void onComponentError(TuFragment fragment, TuSdkResult result, Error error)
	{
		TLog.d("onComponentError: fragment - %s, result - %s, error - %s", fragment, result, error);
	}

}
