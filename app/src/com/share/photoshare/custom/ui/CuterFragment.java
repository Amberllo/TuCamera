package com.share.photoshare.custom.ui;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.share.photoshare.R;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.seles.tusdk.FilterImageViewInterface;
import org.lasque.tusdk.core.view.TuSdkViewHelper;
import org.lasque.tusdk.core.view.widget.button.TuSdkImageButton;
import org.lasque.tusdk.core.view.widget.button.TuSdkTextButton;
import org.lasque.tusdk.impl.components.edit.TuEditCuterFragment;
import org.lasque.tusdk.impl.components.filter.TuEditFilterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amberllo on 16/8/22.
 */
public class CuterFragment extends TuEditCuterFragment{

    public static int getLayoutViewId(){
        return R.layout.custom_cuter_fragment_layout;
    }

    @Override
    public void setRootViewLayoutId(int i) {
        super.setRootViewLayoutId(getLayoutViewId());
    }


    @Override
    protected void handleCompleteButton() {
        super.handleCompleteButton();
        handleBackButton();
    }

    @Override
    protected void loadView(ViewGroup viewGroup) {
        super.loadView(viewGroup);



        this.getViewById(R.id.lsq_doneButton1).setOnClickListener(new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(View view) {
                handleCompleteButton();
            }
        });

        this.getViewById(R.id.lsq_cancelButton1).setOnClickListener(new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(View view) {
                handleBackButton();
            }
        });

        LinearLayout optBar = getOptionBar();
        for(int i=0;i<optBar.getChildCount();i++){
            if(optBar.getChildAt(i).getId() == R.id.lsq_trunButton1){
                optBar.getChildAt(i).setOnClickListener(new TuSdkViewHelper.OnSafeClickListener() {
                    @Override
                    public void onSafeClick(View view) {
                        handleTrunButton();
                    }
                });
            }
            if(optBar.getChildAt(i).getId() == R.id.lsq_mirrorButton1){
                optBar.getChildAt(i).setOnClickListener(new TuSdkViewHelper.OnSafeClickListener() {
                    @Override
                    public void onSafeClick(View view) {
                        handleMirrorButton();
                    }
                });
            }

        }

    }

    @Override
    public LinearLayout getOptionBar() {

        View scrollParent = this.getViewById(R.id.lsq_optionBar_root);
        LinearLayout optBar = (LinearLayout) scrollParent.findViewById(R.id.lsq_optionBar);

        if(optBar != null && this.getRatioTypes().length < 2) {
            this.showView(optBar, false);
        }
        return optBar;
    }



    @Override
    public List<TuSdkTextButton> getRatioButtons() {
        List<TuSdkTextButton> result = new ArrayList<>();
        if(this.getOptionBar() != null) {
            LinearLayout optionBar = this.getOptionBar();
            int[] ratioTypes = this.getRatioTypes();

            for(int i = 0; i < ratioTypes.length; ++i) {
                int type = ratioTypes[i];

                if( i < optionBar.getChildCount() && optionBar.getChildAt(i) instanceof TuSdkTextButton ){
                    final TuSdkTextButton button = (TuSdkTextButton) optionBar.getChildAt(i);
                    this.buildRatioActionButton(type, ratioTypes.length,button);
                    button.index = type;
                    button.setOnClickListener(new TuSdkViewHelper.OnSafeClickListener() {
                        @Override
                        public void onSafeClick(View view) {
                            handleRatioButton(button);
                        }
                    });
                    boolean select;
                    if(this.getCurrentRatioType() <= 0) {
                        select = false;
                    } else {
                        select = type == this.getCurrentRatioType();
                    }

                    button.setSelected(select);
//                    optionBar.addView(button);
                    result.add(button);
                }
            }
        }

        return result;
    }


    protected TuSdkTextButton buildRatioActionButton(int var1, int var2,TuSdkTextButton textButton) {
        String var3;
        String var6;
        switch(var1) {
            case 1:
                var6 = "lsq_edit_cuter_ratio_orgin";
                var3 = "lsq_style_default_ratio_orgin";
                break;
            case 2:
                var6 = "lsq_edit_cuter_ratio_1_1";
                var3 = "lsq_style_default_ratio_1_1";
                break;
            case 4:
                var6 = "lsq_edit_cuter_ratio_2_3";
                var3 = "lsq_style_default_ratio_2_3";
                break;
            case 8:
                var6 = "lsq_edit_cuter_ratio_3_4";
                var3 = "lsq_style_default_ratio_3_4";
                break;
            case 16:
                var6 = "lsq_edit_cuter_ratio_9_16";
                var3 = "lsq_style_default_ratio_9_16";
                break;
            default:
                return null;
        }

        int color_orange = TuSdkContext.getColor("lsq_color_orange");
        var2 = (int)Math.floor((double)(TuSdkContext.getDisplaySize().width / var2));

        LinearLayout.LayoutParams params;
        (params = new LinearLayout.LayoutParams(var2, -1)).setMargins(0, 0, 0, 0);
        textButton.setLayoutParams(params);
        textButton.setGravity(17);
        var2 = TuSdkContext.dip2px(18.0F);
        textButton.setPadding(0, var2, 0, TuSdkContext.dip2px(10.0F));
        textButton.setSelectedColor(color_orange);
        textButton.setText(TuSdkContext.getString(var6));
        textButton.setCompoundDrawables((Drawable)null, TuSdkContext.getDrawable(var3), (Drawable)null, (Drawable)null);
        return textButton;
    }
}
