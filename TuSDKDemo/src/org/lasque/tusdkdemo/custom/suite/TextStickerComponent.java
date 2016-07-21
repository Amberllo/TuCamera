package org.lasque.tusdkdemo.custom.suite;

import android.app.Activity;

import org.lasque.tusdk.impl.TuAnimType;
import org.lasque.tusdk.impl.components.edit.TuEditMultipleFragment;
import org.lasque.tusdk.modules.components.TuEditMultipleComponentBase;
import org.lasque.tusdk.modules.components.TuSdkComponent;
import org.lasque.tusdk.modules.components.TuSdkHelperComponent;

/**
 * Created by apple on 16/7/20.
 */
public class TextStickerComponent extends TuEditMultipleComponentBase {
    TextStickerOption option;
    TuSdkHelperComponent componentHelper;
    public TextStickerComponent(Activity activity) {
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

            TextStickerFragment fragment = option.fragment();
            fragment.setImage(this.getImage());
            fragment.setTempFilePath(this.getTempFilePath());
            fragment.setImageSqlInfo(this.getImageSqlInfo());

            // 文字贴纸页面
            this.componentHelper.presentModalNavigationActivity(fragment,TuAnimType.fade, TuAnimType.fade, true);
//            fragment.setDelegate(this);


            return this;
        }
    }

    public TextStickerComponent setOption(TextStickerOption option) {
        this.option = option;
        return this;
    }


}
