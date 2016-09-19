package com.share.photoshare.custom.ui;

import android.view.ViewGroup;

import com.share.photoshare.R;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.seles.tusdk.FilterImageViewInterface;
import org.lasque.tusdk.core.view.widget.button.TuSdkImageButton;
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
//
//
//    @Override
//    public TuFilterResultFragmentDelegate getDelegate() {
//        TuFilterResultFragmentDelegate  delegate = super.getDelegate();
//        return delegate;
//    }
//
//    protected void loadView(ViewGroup var1) {
//        super.loadView(var1);
//        ((FilterImageViewInterface)this.getImageView()).setImageBackgroundColor(TuSdkContext.getColor(R.color.gray_common2));
//    }
//
//    @Override
//    public TuSdkImageButton getCompleteButton() {
//
//        TuSdkImageButton button = this.getViewById(R.id.lsq_doneButton);
//        if(button != null) {
//            button.setOnClickListener(this.mButtonClickListener);
//        }
//        return button;
//    }
//
//
//    @Override
//    public TuSdkImageButton getCancelButton() {
//        TuSdkImageButton button = this.getViewById(R.id.lsq_cancelButton1);
//        if(button != null) {
//            button.setOnClickListener(this.mButtonClickListener);
//        }
//        return button;
//    }
}
