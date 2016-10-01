package com.mixcolours.photoshare.custom.ui;

import android.view.ViewGroup;

import com.mixcolours.photoshare.R;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.seles.tusdk.FilterImageViewInterface;
import org.lasque.tusdk.core.view.widget.button.TuSdkImageButton;
import org.lasque.tusdk.impl.components.filter.TuEditFilterFragment;
/**
 * Created by Amberllo on 16/8/22.
 */
public class FilterFragment extends TuEditFilterFragment{

    public static int getLayoutViewId(){
        return R.layout.custom_filter_fragment_layout;
    }

    @Override
    public void setRootViewLayoutId(int i) {
        super.setRootViewLayoutId(getLayoutViewId());
    }


    @Override
    protected void loadView(ViewGroup viewGroup) {
        super.loadView(viewGroup);
        ((FilterImageViewInterface)this.getImageView()).setImageBackgroundColor(TuSdkContext.getColor(R.color.gray_common2));
    }

    @Override
    protected void viewDidLoad(ViewGroup viewGroup) {
        super.viewDidLoad(viewGroup);
    }

    @Override
    public TuSdkImageButton getCompleteButton() {

        TuSdkImageButton button = this.getViewById(R.id.lsq_doneButton);
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



    @Override
    public void hubError(int i) {
//        super.hubError(i);
    }

    @Override
    public void hubError(String s) {
//        super.hubError(s);
    }

    @Override
    public void hubSuccess(String var1) {
//        TuSdk.messageHub().showSuccess(this.getActivity(), var1);
    }

    @Override
    public void hubSuccess(int var1) {
//        TuSdk.messageHub().showSuccess(this.getActivity(), var1);
    }
}
