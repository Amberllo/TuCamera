package org.lasque.tusdkdemo.custom.suite;

import android.app.Activity;
import android.widget.Toast;

import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.edit.TuEditMultipleFragment;
import org.lasque.tusdk.modules.components.TuSdkHelperComponent;
import org.lasque.tusdk.modules.components.edit.TuEditActionType;
import org.lasque.tusdkdemo.R;
import org.lasque.tusdkdemo.SampleBase;
import org.lasque.tusdkdemo.SampleGroup;

/**
 * Created by apple on 16/7/20.
 */
public class TextStickerComponent extends SampleBase implements TuEditMultipleFragment.TuEditMultipleFragmentDelegate{
    /**
     * 文字贴纸
     */
    public TextStickerComponent() {
        super(SampleGroup.GroupType.UISample, R.string.lsq_edit_entry_fontsticker);
    }

    @Override
    public void showSample(Activity activity) {
        TextStickerOption option = new TextStickerOption();
        // see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/base/TuSdkHelperComponent.html
        this.componentHelper = new TuSdkHelperComponent(activity);
        // 文字贴纸页面
        this.componentHelper.presentModalNavigationActivity(option.fragment(), true);
    }

    @Override
    public void onComponentError(TuFragment tuFragment, TuSdkResult tuSdkResult, Error error) {

    }

    @Override
    public void onTuEditMultipleFragmentEdited(TuEditMultipleFragment tuEditMultipleFragment, TuSdkResult tuSdkResult) {

    }

    @Override
    public boolean onTuEditMultipleFragmentEditedAsync(TuEditMultipleFragment tuEditMultipleFragment, TuSdkResult tuSdkResult) {
        return false;
    }

    @Override
    public void onTuEditMultipleFragmentAction(TuEditMultipleFragment tuEditMultipleFragment, TuEditActionType tuEditActionType) {

    }
}
