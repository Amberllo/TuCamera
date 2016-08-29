package com.share.photoshare.custom.suite;

import android.app.Activity;

import com.share.photoshare.R;
import com.share.photoshare.custom.ui.FilterFragment;
import com.share.photoshare.examples.feature.ExtendFilterFragment;

import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.utils.image.BitmapHelper;
import org.lasque.tusdk.impl.TuAnimType;
import org.lasque.tusdk.impl.activity.TuFilterResultFragment;
import org.lasque.tusdk.impl.components.TuEditMultipleComponent;
import org.lasque.tusdk.impl.components.filter.TuEditFilterFragment;
import org.lasque.tusdk.impl.components.filter.TuEditFilterOption;
import org.lasque.tusdk.impl.components.filter.TuEditSkinFragment;
import org.lasque.tusdk.modules.components.TuSdkHelperComponent;

/**
 * Created by apple on 16/7/20.
 */
public abstract class FilterComponent extends TuEditMultipleComponent{

    TuSdkHelperComponent componentHelper;
//    SkinComponentOption option;
    public FilterComponent(Activity activity) {
        super(activity);
        this.componentHelper = new TuSdkHelperComponent(activity);
    }

    @Override
    protected void initComponent() {

    }

    @Override
    public TuEditMultipleComponent showComponent() {
        if(this.showAlertIfCannotSaveFile()) {
            return this;
        } else {

//            if(option==null)return this;


            // 组件选项配置
            // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditFilterOption.html
            TuEditFilterOption option = new TuEditFilterOption();

            // 控制器类型，这里指定为扩展类 ExtendFilterFragment
            option.setComponentClazz(FilterFragment.class);
            // 设置根视图布局资源ID
            // option.setRootViewLayoutId(TuEditFilterFragment.getLayoutId());

            // 是否在控制器结束后自动删除临时文件
            option.setAutoRemoveTemp(true);
            // 显示滤镜标题视图
            option.setDisplayFiltersSubtitles(true);
            // 开启在线滤镜
            option.setEnableOnlineFilter(true);
            // 开启用户滤镜历史记录
            option.setEnableFiltersHistory(true);

            // 需要显示的滤镜名称列表 (如果为空将显示所有自定义滤镜)
            // 访问文档查看详细用法  @see-http://tusdk.com/docs/android/customize-filter
            // option.setFilterGroup(Arrays.asList(filters));

            // 是否渲染滤镜封面 (使用设置的滤镜直接渲染，需要拥有滤镜列表封面设置权限，请访问TuSDK.com控制台)
            // option.setRenderFilterThumb(true)

            // 是否显示处理结果预览图 (默认：关闭，调试时可以开启)
            option.setShowResultPreview(true);

            TuEditFilterFragment fragment = option.fragment();

            // 输入的图片对象 (处理优先级: Image > TempFilePath > ImageSqlInfo)
            fragment.setImage(this.getImage());
            fragment.setTempFilePath(this.getTempFilePath());
            fragment.setImageSqlInfo(this.getImageSqlInfo());

            fragment.setDelegate(this);

            // 美颜页面
            this.componentHelper.presentModalNavigationActivity(fragment, TuAnimType.fade, TuAnimType.fade, true);

            return this;
        }
    }

    public abstract void onTuEditFilterResult(TuSdkResult result);



    @Override
    public boolean onTuEditFilterFragmentEditedAsync(TuEditFilterFragment tuEditFilterFragment,final TuSdkResult tuSdkResult) {
        onTuEditFilterResult(tuSdkResult);
        tuEditFilterFragment.hubDismissRightNow();
        tuEditFilterFragment.dismissActivityWithAnim();
        return super.onTuEditFilterFragmentEditedAsync(tuEditFilterFragment, tuSdkResult);
    }
}
