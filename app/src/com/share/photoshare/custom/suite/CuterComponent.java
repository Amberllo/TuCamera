package com.share.photoshare.custom.suite;

import android.app.Activity;

import com.share.photoshare.custom.ui.CuterFragment;
import com.share.photoshare.custom.ui.FilterFragment;

import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.view.widget.TuSdkNavigatorBar;
import org.lasque.tusdk.impl.TuAnimType;
import org.lasque.tusdk.impl.components.TuEditMultipleComponent;
import org.lasque.tusdk.impl.components.edit.TuEditCuterFragment;
import org.lasque.tusdk.impl.components.edit.TuEditCuterOption;
import org.lasque.tusdk.impl.components.filter.TuEditFilterFragment;
import org.lasque.tusdk.impl.components.filter.TuEditFilterOption;
import org.lasque.tusdk.modules.components.TuSdkHelperComponent;

/**
 * Created by apple on 16/7/20.
 */
public abstract class CuterComponent extends TuEditMultipleComponent{

    TuSdkHelperComponent componentHelper;
//    SkinComponentOption option;
    public CuterComponent(Activity activity) {
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
            TuEditCuterOption option = new TuEditCuterOption();

            // 控制器类型，这里指定为扩展类 ExtendFilterFragment
            option.setComponentClazz(CuterFragment.class);
            // 设置根视图布局资源ID
            // option.setRootViewLayoutId(TuEditFilterFragment.getLayoutId());

            // 是否在控制器结束后自动删除临时文件
            option.setAutoRemoveTemp(true);


            // 需要显示的滤镜名称列表 (如果为空将显示所有自定义滤镜)
            // 访问文档查看详细用法  @see-http://tusdk.com/docs/android/customize-filter
            // option.setFilterGroup(Arrays.asList(filters));

            // 是否渲染滤镜封面 (使用设置的滤镜直接渲染，需要拥有滤镜列表封面设置权限，请访问TuSDK.com控制台)
            // option.setRenderFilterThumb(true)

            // 是否显示处理结果预览图 (默认：关闭，调试时可以开启)
            option.setShowResultPreview(true);

            TuEditCuterFragment fragment = option.fragment();

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

    public abstract void onTuEditCuterResult(TuSdkResult result);

    @Override
    public boolean onTuEditCuterFragmentEditedAsync(TuEditCuterFragment fragment, TuSdkResult tuSdkResult) {
        fragment.hubDismissRightNow();
        fragment.dismissActivityWithAnim();
        onTuEditCuterResult(tuSdkResult);
        return super.onTuEditCuterFragmentEditedAsync(fragment, tuSdkResult);
    }

}
