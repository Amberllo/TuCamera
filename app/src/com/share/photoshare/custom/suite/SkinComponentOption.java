package com.share.photoshare.custom.suite;

import com.share.photoshare.custom.ui.SkinFragment;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.activity.TuImageResultFragment;
import org.lasque.tusdk.impl.components.filter.TuEditSkinFragment;
import org.lasque.tusdk.impl.components.filter.TuEditSkinOption;

/**
 * Created by Amberllo on 2016/8/20.
 */
public class SkinComponentOption extends TuEditSkinOption {



    @Override
    public TuEditSkinFragment fragment() {
        super.fragment();
        SkinFragment fragment = null;

        TuFragment var1;
        if((var1 = super.fragmentInstance()) instanceof TuImageResultFragment) {
            fragment = (SkinFragment)var1;
            if(fragment != null) {
                fragment.setShowResultPreview(this.isShowResultPreview());
                fragment.setAutoRemoveTemp(this.isAutoRemoveTemp());
                fragment.setRootViewLayoutId(SkinFragment.getLayoutViewId());

            }
        }

        return fragment;
    }

    @Override
    public Class<?> getComponentClazz() {
        return SkinFragment.class;
    }


}
