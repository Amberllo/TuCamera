/** 
 * TuSdkDemo
 * EditMultipleComponentSimple.java
 *
 * @author 		Clear
 * @Date 		2015-4-21 下午1:38:04 
 * @Copyright 	(c) 2015 tusdk.com. All rights reserved.
 * 
 */
package com.share.photoshare.custom.suite;

import android.app.Activity;

import org.lasque.tusdk.TuSdkGeeV1;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.TuEditMultipleComponent;
import org.lasque.tusdk.modules.components.TuSdkComponent.TuSdkComponentDelegate;
import org.lasque.tusdk.modules.components.TuSdkHelperComponent;
import com.share.photoshare.R;
import com.share.photoshare.SampleBase;
import com.share.photoshare.SampleGroup.GroupType;
import com.share.photoshare.custom.ui.EditMultipleFragment;

/**
 * 照片美化组件范例
 * 
 * @author Clear
 */
public class AlbumEditMultipleComponent extends SampleBase
{
	/** 照片美化组件范例 */
	public AlbumEditMultipleComponent()
	{
		super(GroupType.SuiteSample, R.string.sample_EditMultipleComponent);
	}

	/**
	 * 组件显示入口，在本例中，启动编辑器前，先从相册组件选择图片作为输入源，按照开发需求，可以选择多种方式来启动编辑器，比如相机拍照后直接调用编辑器。
	 * 欢迎访问文档中心 http://tusdk.com/doc 查看更多示例。
	 * 
	 * SDK中所有的编辑组件都支持三种格式的输入源： Bitmap | File | ImageSqlInfo
	 * 
	 * // 设置图片
	 * component.setImage(result.image)
	 *  		// 设置系统照片
	 *  		.setImageSqlInfo(result.imageSqlInfo)
	 *  		// 设置临时文件
	 *  		.setTempFilePath(result.imageFile)
	 * 
	 * 处理优先级: Image > TempFilePath > ImageSqlInfo
	 * 
	 */
	@Override
	public void showSample(Activity activity)
	{
		if (activity == null) return;
		// see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/base/TuSdkHelperComponent.html
		this.componentHelper = new TuSdkHelperComponent(activity);

		TuSdkGeeV1.albumCommponent(activity, new TuSdkComponentDelegate()
		{
			@Override
			public void onComponentFinished(TuSdkResult result, Error error, TuFragment lastFragment)
			{
				openEditMultiple(result, error, lastFragment);
			}
		}).showComponent();
	}

	/** 开启照片美化组件 */
	private void openEditMultiple(TuSdkResult result, Error error, TuFragment lastFragment)
	{
		if (result == null || error != null) return;

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

        TuEditMultipleComponent component = TuSdkGeeV1.editMultipleCommponent(lastFragment, delegate);
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
	

}