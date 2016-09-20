package com.share.photoshare.custom.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.share.photoshare.R;
import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.seles.SelesParameters;
import org.lasque.tusdk.core.view.widget.button.TuSdkImageButton;
import org.lasque.tusdk.impl.components.filter.TuEditSkinFragment;

import java.util.List;

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


    protected void loadView(ViewGroup var1) {
        super.loadView(var1);
        (this.getImageView()).setImageBackgroundColor(TuSdkContext.getColor(R.color.gray_common2));
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

    @Override
    protected void buildActionButtons() {
        LinearLayout var1;
        if((var1 = this.getActionsWrap()) != null) {
            var1.removeAllViews();
            SelesParameters parameters;
            if((parameters = this.getFilterParameter()) != null && parameters.size() != 0) {

                List<String> keys = parameters.getArgKeys();
                View btn1 = this.buildActionButton(keys.get(1), 1);
                View btn2 = this.buildActionButton(keys.get(2), 2);
                View btn3 = this.buildActionButton(keys.get(3), 3);
                var1.removeAllViews();
                LinearLayout.LayoutParams LayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,1);
                var1.addView(btn1,LayoutParams);
                var1.addView(btn2,LayoutParams);
                var1.addView(btn3,LayoutParams);

            }
        }
    }

    @Override
    public LinearLayout getActionsWrap() {
        return this.getViewById("lsq_actions_wrapview1");
    }

    @Override
    public View getOptionsWrap() {
        return this.getViewById("lsq_actions_wrapview1");
    }

    @Override
    protected void setConfigViewShowState(boolean b) {
        super.setConfigViewShowState(b);
    }
}
