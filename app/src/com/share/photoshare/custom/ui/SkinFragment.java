package com.share.photoshare.custom.ui;

import android.view.View;
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
    protected void loadView(ViewGroup var1) {
        super.loadView(var1);
        (this.getImageView()).setImageBackgroundColor(TuSdkContext.getColor(R.color.gray_common2));
//        View bottomBar = this.getViewById("lsq_bottomBar");
//        if(bottomBar!=null)bottomBar.setVisibility(View.GONE);

    }


    @Override
    public TuSdkImageButton getCompleteButton() {

        TuSdkImageButton button = this.getViewById(R.id.lsq_doneButton1);
        if(button != null) {
            button.setOnClickListener(this.mButtonClickListener);
        }
        return button;
    }


    @Override
    public TuSdkImageButton getCancelButton() {
        TuSdkImageButton button = this.getViewById(R.id.lsq_cancelButton1);
        if(button != null) {
            button.setOnClickListener(this.mButtonClickListener);
        }
        return button;
    }
}
