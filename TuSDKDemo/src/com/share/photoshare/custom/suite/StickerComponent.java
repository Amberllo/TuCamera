package com.share.photoshare.custom.suite;

import android.app.Activity;

import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.impl.TuAnimType;
import org.lasque.tusdk.impl.components.TuEditMultipleComponent;
import org.lasque.tusdk.impl.components.sticker.TuEditStickerFragment;
import org.lasque.tusdk.modules.components.TuSdkHelperComponent;

/**
 * Created by apple on 16/7/20.
 */
public abstract class StickerComponent extends TuEditMultipleComponent{

    TuSdkHelperComponent componentHelper;
    StickerComponentOption option;
    public StickerComponent(Activity activity) {
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

            TuEditStickerFragment fragment = option.fragment();
            fragment.setDelegate(this);
            fragment.setImage(this.getImage());
            fragment.setTempFilePath(this.getTempFilePath());
            fragment.setImageSqlInfo(this.getImageSqlInfo());

            // 文字贴纸页面
            this.componentHelper.presentModalNavigationActivity(fragment, TuAnimType.fade, TuAnimType.fade, true);

            return this;
        }
    }


    public StickerComponent setOption(StickerComponentOption option) {
        this.option = option;
        return this;
    }

    @Override
    public boolean onTuEditStickerFragmentEditedAsync(TuEditStickerFragment tuEditStickerFragment, TuSdkResult tuSdkResult) {
        onTuEditStickerResult(tuSdkResult);
        return super.onTuEditStickerFragmentEditedAsync(tuEditStickerFragment, tuSdkResult);
    }

    @Override
    public void onTuEditStickerFragmentEdited(TuEditStickerFragment tuEditStickerFragment, TuSdkResult tuSdkResult) {
        super.onTuEditStickerFragmentEdited(tuEditStickerFragment, tuSdkResult);
        onTuEditStickerResult(tuSdkResult);
    }

    public abstract void onTuEditStickerResult(TuSdkResult result);

}
