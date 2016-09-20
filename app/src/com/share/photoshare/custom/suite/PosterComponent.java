package com.share.photoshare.custom.suite;

import android.app.Activity;

import com.share.photoshare.custom.ui.PosterFragment;
import com.share.photoshare.custom.ui.TextStickerFragment;

import org.lasque.tusdk.impl.TuAnimType;
import org.lasque.tusdk.modules.components.TuEditMultipleComponentBase;
import org.lasque.tusdk.modules.components.TuSdkComponent;
import org.lasque.tusdk.modules.components.TuSdkHelperComponent;

/**
 * Created by apple on 16/7/20.
 */
public class PosterComponent extends TuEditMultipleComponentBase {
    PosterComponentOption option;
    TuSdkHelperComponent componentHelper;
    public PosterComponent(Activity activity) {
        super(activity);
        this.componentHelper = new TuSdkHelperComponent(activity);
    }

    /**
     * 文字贴纸
     */

    @Override
    protected void initComponent() {

    }

    @Override
    public TuSdkComponent showComponent() {
        if(this.showAlertIfCannotSaveFile()) {
            return this;
        } else {

            if(option==null)return this;

            PosterFragment fragment = option.fragment();
//            fragment.setDelegate(option.getDelegate());
            fragment.setImage(this.getImage());
            fragment.setTempFilePath(this.getTempFilePath());
            fragment.setImageSqlInfo(this.getImageSqlInfo());

            // 文字贴纸页面
            this.componentHelper.presentModalNavigationActivity(fragment,TuAnimType.fade, TuAnimType.fade, true);

            return this;
        }
    }

    public PosterComponent setOption(PosterComponentOption option) {
        this.option = option;
        return this;
    }



}
