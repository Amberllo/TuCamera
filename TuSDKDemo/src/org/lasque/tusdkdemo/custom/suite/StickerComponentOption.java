package org.lasque.tusdkdemo.custom.suite;

import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.activity.TuImageResultFragment;
import org.lasque.tusdk.impl.components.sticker.TuEditStickerFragment;
import org.lasque.tusdk.impl.components.sticker.TuEditStickerOption;
import org.lasque.tusdkdemo.R;
import org.lasque.tusdkdemo.custom.ui.StickerFragment;

/**
 * Created by LYL on 2016/7/27.
 */
public class StickerComponentOption extends TuEditStickerOption{
    @Override
    public TuEditStickerFragment fragment() {
        StickerFragment fragment = null;

        TuFragment var1;
        if((var1 = super.fragmentInstance()) instanceof TuImageResultFragment) {
            fragment = (StickerFragment)var1;
            if(fragment != null) {
                fragment.setShowResultPreview(this.isShowResultPreview());
                fragment.setAutoRemoveTemp(this.isAutoRemoveTemp());
                fragment.setRootViewLayoutId(StickerFragment.getLayoutViewId());
                fragment.setGridLayoutId(this.getGridLayoutId());
                fragment.setGridWidth(this.getGridWidth());
                fragment.setGridHeight(this.getGridHeight());
                fragment.setGridPadding(this.getGridPadding());
                fragment.setCategories(this.getCategories());
                fragment.setStickerViewDelegate(this.getStickerViewDelegate());
            }
        }

        return fragment;
    }

    @Override
    public Class<?> getComponentClazz() {
        return StickerFragment.class;
    }
}
