package com.share.photoshare.custom.ui;

import com.share.photoshare.R;

import org.lasque.tusdk.impl.components.filter.TuEditSkinFragment;

/**
 * Created by apple on 16/8/22.
 */
public class SkinFragment extends TuEditSkinFragment{

    public static int getLayoutViewId(){
        return R.layout.custom_skin_fragment_layout;
    }

    @Override
    public void setRootViewLayoutId(int i) {
        super.setRootViewLayoutId(getLayoutViewId());
    }


    @Override
    public TuFilterResultFragmentDelegate getDelegate() {
        TuFilterResultFragmentDelegate  delegate = super.getDelegate();
        return delegate;
    }

    @Override
    protected void handleCompleteButton() {
        super.handleCompleteButton();
        handleBackButton();
    }

}
