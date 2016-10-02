/** 
 * TuSDK
 * TuApplication.java
 *
 * author 		Clear
 * Date 		2014-11-18 下午5:14:34 
 * Copyright 	(c) 2014 tusdk.com. All rights reserved.
 * 
 */
package com.mixcolours.photoshare;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.mixcolours.photoshare.custom.CustomImageDownloader;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LargestLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.lasque.tusdk.core.TuSdkApplication;
import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.struct.TuSdkSize;
import org.lasque.tusdk.core.utils.hardware.HardwareHelper;

import java.io.File;

/**
 * 应用对象
 * 
 * @author Clear
 */
public class TuApplication extends TuSdkApplication
{
	/** 应用程序创建 */
	@Override
	public void onCreate()
	{
		super.onCreate();

		/**
		 ************************* TuSDK 集成三部曲 *************************
		 * 
		 * 1. 在官网注册开发者账户
		 * 
		 * 2. 下载SDK和示例代码
		 * 
		 * 3. 创建应用，获取appkey，导出资源包
		 * 
		 ************************* TuSDK 集成三部曲 ************************* 
		 * 
		 * 关于TuSDK体积 (约2M大小)
		 * 
		 * Android 编译知识：
		 * APK文件包含了Java代码，JNI库和资源文件；
		 * JNI库包含arm64-v8a,armeabi等不同CPU的编译结果的集合，这些都会编译进 APK 文件；
		 * 在安装应用时，系统会自动选择最合适的JNI版本，其他版本不会占用空间；
		 * 参考TuSDK Demo的APK 大小，除去资源和JNI库，SDK本身的大小约2M；
		 * 
		 * 开发文档:http://tusdk.com/doc
		 */
		
		// 设置资源类，当 Application id 与 Package Name 不相同时，必须手动调用该方法, 且在 init 之前执行。
		// TuSdk.setResourcePackageClazz(org.lasque.tusdkdemo.R.class);
		
		// 自定义 .so 文件路径，在 init 之前调用
		// NativeLibraryHelper.shared().mapLibrary(NativeLibType.LIB_CORE, "libtusdk-library.so 文件路径");
		// NativeLibraryHelper.shared().mapLibrary(NativeLibType.LIB_IMAGE, "libtusdk-image.so 文件路径");

		// 设置输出状态，建议在接入阶段开启该选项，以便定位问题。
		this.setEnableLog(true);
		/**
	     *  初始化SDK，应用密钥是您的应用在 TuSDK 的唯一标识符。每个应用的包名(Bundle Identifier)、密钥、资源包(滤镜、贴纸等)三者需要匹配，否则将会报错。
	     *
	     *  @param appkey 应用秘钥 (请前往 http://tusdk.com 申请秘钥)
	     */

		TuSdkContext.init(this);
		TuSdkSize var1 = TuSdkContext.getScreenSize();

		File var2 = StorageUtils.getCacheDirectory(this);
		File var3;
		if(!(var3 = new File(var2, "imageCache")).exists() && !var3.mkdirs()) {
			var3 = var2;
		}

		int var5 = Math.min((int)(HardwareHelper.appMemoryBit() / 8L), 1703936);
		ImageLoaderConfiguration var4 = (new ImageLoaderConfiguration.Builder(this))
				.memoryCacheExtraOptions(var1.width, var1.height)
				.diskCacheExtraOptions(var1.width, var1.height, (BitmapProcessor)null)
				.threadPoolSize(2)
				.threadPriority(4)
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LargestLimitedMemoryCache(var5))
				.memoryCacheSize(var5)
				.memoryCacheSizePercentage(13)
				.diskCache(new UnlimitedDiskCache(var3)).diskCacheSize(209715200)
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator()).imageDownloader(new CustomImageDownloader(this))
				.imageDecoder(new BaseImageDecoder(false))
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.build();
		ImageLoader.getInstance().init(var4);

        this.initPreLoader(this.getApplicationContext(), "90332d115bf8df36-02-7lryp1");

		
		/**
	     *  指定开发模式,需要与lsq_tusdk_configs.json中masters.key匹配， 如果找不到devType将默认读取master字段
	     *  如果一个应用对应多个包名，则可以使用这种方式来进行集成调试。
	     */
		// this.initPreLoader(this.getApplicationContext(), "12aa4847a3a9ce68-04-ewdjn1", "debug");

		// 如果不想继承TuSdkApplication，直接在自定义Application.onCreate()方法中调用以下方法
		// 初始化全局变量
		// TuSdk.enableDebugLog(true);
		// 开发ID (请前往 http://tusdk.com 获取您的APP 开发秘钥)
		// TuSdk.init(this.getApplicationContext(), "12aa4847a3a9ce68-04-ewdjn1");
		// 需要指定开发模式 需要与lsq_tusdk_configs.json中masters.key匹配， 如果找不到devType将默认读取master字段
		// TuSdk.init(this.getApplicationContext(), "12aa4847a3a9ce68-04-ewdjn1", "debug");



	}

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}