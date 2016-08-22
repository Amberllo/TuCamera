package com.share.photoshare.custom.ui;

import com.share.photoshare.R;

import org.lasque.tusdk.core.seles.tusdk.FilterWrap;

import org.lasque.tusdk.impl.activity.TuFilterResultFragment;
import org.lasque.tusdk.impl.components.filter.TuEditSkinFragment;

/**
 * Created by apple on 16/8/22.
 */
public class SkinFragment extends TuEditSkinFragment{

    private FilterWrap filterWrap;
    TuFilterResultFragment.TuFilterResultFragmentDelegate delegate;


    public void setDelegate(TuFilterResultFragment.TuFilterResultFragmentDelegate delegate) {
        super.setDelegate(delegate);
        this.delegate = delegate;
    }

    public static int getLayoutViewId(){
        return R.layout.custom_skin_fragment_layout;
    }

    @Override
    public void setRootViewLayoutId(int i) {
        super.setRootViewLayoutId(getLayoutViewId());
    }


    @Override
    protected void setImageViewFilter(FilterWrap filterWrap) {
        super.setImageViewFilter(filterWrap);
        this.filterWrap = filterWrap;
    }

    @Override
    public TuFilterResultFragmentDelegate getDelegate() {
        TuFilterResultFragmentDelegate  delegate = super.getDelegate();
        return delegate;
    }

    @Override
    protected void handleCompleteButton() {
        super.handleCompleteButton();
//        final TuSdkResult tuSdkResult;
//        (tuSdkResult = new TuSdkResult()).filterWrap = this.filterWrap;
//        loadOrginImage(tuSdkResult);
//        if(tuSdkResult.filterWrap != null) {
//            float var2 = TuSdkSize.create(tuSdkResult.image).limitScale();
//            tuSdkResult.image = BitmapHelper.imageScale(tuSdkResult.image, var2);
//            tuSdkResult.image = tuSdkResult.filterWrap.clone().process(tuSdkResult.image);
//            tuSdkResult.imageFile = new File(FileUtils.saveBitmapToLocal(tuSdkResult.image,getContext()));
//        }
//
//        if(delegate!=null){
//            delegate.onTuFilterResultFragmentEdited(SkinFragment.this,tuSdkResult);
//        }
        handleBackButton();
    }

}
