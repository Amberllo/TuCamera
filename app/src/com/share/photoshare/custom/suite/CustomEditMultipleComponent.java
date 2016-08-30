package com.share.photoshare.custom.suite;

import com.share.photoshare.custom.ui.CuterFragment;
import com.share.photoshare.custom.ui.FilterFragment;
import com.share.photoshare.custom.ui.SkinFragment;
import com.share.photoshare.custom.ui.StickerFragment;
import com.share.photoshare.custom.ui.TextStickerFragment;

import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.utils.image.BitmapHelper;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.TuEditMultipleComponent;
import org.lasque.tusdk.impl.components.edit.TuEditCuterFragment;
import org.lasque.tusdk.impl.components.edit.TuEditCuterOption;
import org.lasque.tusdk.impl.components.edit.TuEditMultipleFragment;
import org.lasque.tusdk.impl.components.filter.TuEditFilterFragment;
import org.lasque.tusdk.impl.components.filter.TuEditFilterOption;
import org.lasque.tusdk.impl.components.filter.TuEditSkinFragment;
import org.lasque.tusdk.impl.components.filter.TuEditSkinOption;
import org.lasque.tusdk.impl.components.sticker.TuEditStickerFragment;
import org.lasque.tusdk.impl.components.sticker.TuEditStickerOption;
import org.lasque.tusdk.modules.components.edit.TuEditActionType;

/**
 * Created by apple on 16/8/29.
 */
public class CustomEditMultipleComponent extends TuEditMultipleComponent implements TextStickerComponentOption.TextStickerDelegate{

    public CustomEditMultipleComponent(TuFragment tuFragment) {
        super(tuFragment);
    }


    @Override
    protected void handleCutButton(TuEditMultipleFragment tuEditMultipleFragment) {

        TuEditCuterOption option = this.componentOption().editCuterOption();
        option.setComponentClazz(CuterFragment.class);
        TuEditCuterFragment fragment;
        (fragment = option.fragment()).setDelegate(this);
        option.fragment().setDelegate(this);
        this.handleAction(tuEditMultipleFragment, fragment);

    }

    @Override
    protected void handleFilterButton(TuEditMultipleFragment tuEditMultipleFragment) {
        TuEditFilterFragment fragment;
        TuEditFilterOption option = this.componentOption().editFilterOption();
        option.setComponentClazz(FilterFragment.class);
        (fragment = option.fragment()).setDelegate(this);
        this.handleAction(tuEditMultipleFragment, fragment);
    }


    @Override
    protected void handleStickerButton(TuEditMultipleFragment tuEditMultipleFragment) {
        TuEditStickerFragment fragment;
        TuEditStickerOption option = this.componentOption().editStickerOption();
        option.setComponentClazz(StickerFragment.class);
        (fragment = option.fragment()).setDelegate(this);
        this.handleAction(tuEditMultipleFragment, fragment);
    }
    TuEditMultipleFragment tuEditMultipleFragment;
    protected void handleTextStickerButton(TuEditMultipleFragment tuEditMultipleFragment){
        this.tuEditMultipleFragment = tuEditMultipleFragment;
        TextStickerFragment fragment;
        TextStickerComponentOption option = new TextStickerComponentOption();
        (fragment = option.fragment()).setDelegate(this);
        this.handleAction(tuEditMultipleFragment, fragment);
    }

    protected void handleSkinButton(TuEditMultipleFragment tuEditMultipleFragment){

        TuEditSkinFragment fragment;
        TuEditSkinOption option = this.componentOption().editSkinOption();
        option.setComponentClazz(SkinFragment.class);
        (fragment = option.fragment()).setDelegate(this);
        this.handleAction(tuEditMultipleFragment, fragment);
    }

    @Override
    public void onTextStickerResult(TextStickerFragment fragment, TuSdkResult result) {
        this.onActionEdited(fragment, result);
    }

    @Override
    public boolean onTextStickerResultAsync(TextStickerFragment fragment, final TuSdkResult result) {
        this.onActionEdited(fragment, result);
        if((result.image = BitmapHelper.imageResize(result.image, this.tuEditMultipleFragment.getImageDisplaySize(), true)) != null) {
            this.tuEditMultipleFragment.runOnUiThread(new Runnable() {
                public void run() {
                    tuEditMultipleFragment.setDisplayImage(result.image);
                }
            });
        }
        return false;
    }

    @Override
    public boolean onTuEditStickerFragmentEditedAsync(TuEditStickerFragment tuEditStickerFragment, TuSdkResult tuSdkResult) {
        return super.onTuEditStickerFragmentEditedAsync(tuEditStickerFragment, tuSdkResult);
    }

    @Override
    public void onTuEditMultipleFragmentAction(TuEditMultipleFragment tuEditMultipleFragment, TuEditActionType tuEditActionType) {
        if(tuEditActionType == TuEditActionType.TypeUnknow){
            handleTextStickerButton(tuEditMultipleFragment);

        }else if(tuEditActionType == TuEditActionType.TypeSkin) {
            handleSkinButton(tuEditMultipleFragment);
            return;
        }else{

            super.onTuEditMultipleFragmentAction(tuEditMultipleFragment, tuEditActionType);
        }

    }


}
