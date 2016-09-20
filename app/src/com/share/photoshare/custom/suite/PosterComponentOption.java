package com.share.photoshare.custom.suite;

import com.share.photoshare.R;
import com.share.photoshare.custom.ui.PosterFragment;

import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.impl.activity.TuImageResultOption;

/**
 * Created by apple on 16/7/21.
 */
public class PosterComponentOption extends TuImageResultOption {
    @Override
    protected Class<?> getDefaultComponentClazz() {
        return PosterFragment.class;
    }

    @Override
    protected int getDefaultRootViewLayoutId() {
        return R.layout.custom_textedit_fragment_layout;
    }

    public PosterFragment fragment() {
        PosterFragment var1;
        var1 = this.fragmentInstance();
        return var1;
    }

    PosterDelegate delegate;

    public void setDelegate(PosterDelegate delegate){
        this.delegate = delegate;
    }


    public PosterDelegate getDelegate() {
        return delegate;
    }

    public interface PosterDelegate{
        void onPosterResult(PosterFragment fragment, TuSdkResult result);
        boolean onPosterResultAsync(PosterFragment fragment, TuSdkResult result);
    }

}
