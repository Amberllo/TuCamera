package com.share.photoshare.custom.suite;

import android.app.Activity;

import com.share.photoshare.custom.ui.StickerFragment;

import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.impl.TuAnimType;
import org.lasque.tusdk.impl.activity.TuFilterResultFragment;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.TuEditMultipleComponent;
import org.lasque.tusdk.impl.components.filter.TuEditFilterFragment;
import org.lasque.tusdk.impl.components.filter.TuEditSkinFragment;
import org.lasque.tusdk.impl.components.sticker.TuEditStickerFragment;
import org.lasque.tusdk.modules.components.TuSdkHelperComponent;

/**
 * Created by apple on 16/7/20.
 */
public abstract class SkinComponent extends TuEditMultipleComponent{

    TuSdkHelperComponent componentHelper;
    SkinComponentOption option;
    public SkinComponent(Activity activity) {
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

            if(option==null)return this;

            TuEditSkinFragment fragment = option.fragment();
            fragment.setDelegate(this);
            fragment.setImage(this.getImage());
            fragment.setTempFilePath(this.getTempFilePath());
            fragment.setImageSqlInfo(this.getImageSqlInfo());
            // 美颜页面
            this.componentHelper.presentModalNavigationActivity(fragment, TuAnimType.fade, TuAnimType.fade, true);

            return this;
        }
    }


    public SkinComponent setOption(SkinComponentOption option) {
        this.option = option;
        return this;
    }

    @Override
    public void onTuFilterResultFragmentEdited(TuFilterResultFragment tuFilterResultFragment, TuSdkResult tuSdkResult) {
        onTuEditSkinResult(tuSdkResult);
    }

    public abstract void onTuEditSkinResult(TuSdkResult result);

}
