package com.mixcolours.photoshare.custom.ui;

import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mixcolours.photoshare.R;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.seles.SelesParameters;

import org.lasque.tusdk.core.view.widget.button.TuSdkImageButton;
import org.lasque.tusdk.core.view.widget.button.TuSdkTextButton;
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

    @Override
    protected void buildActionButtons() {
        LinearLayout var1;
        if((var1 = this.getActionsWrap()) != null) {
            var1.removeAllViews();
            SelesParameters parameters;
            if((parameters = this.getFilterParameter()) != null && parameters.size() != 0) {
//                int var2 = 0;
//
//                for(Iterator var3 = parameters.getArgKeys().iterator(); var3.hasNext(); ++var2) {
//                    String var4 = (String)var3.next();
//                    this.buildActionButton(var4, var2);
//                }

                List<String> keys = parameters.getArgKeys();
                this.buildActionButton(keys.get(1), 1);
                this.buildActionButton(keys.get(2), 2);
                this.buildActionButton(keys.get(3), 3);


            }
        }
    }

    @Override
    protected View buildActionButton(String key, int tag) {
        if(key == null) {
            return null;
        } else {
            String var3 = String.format("lsq_filter_set_%s", new Object[]{key});
            key = String.format("lsq_style_default_edit_icon_%s", new Object[]{key}).toLowerCase();
            int var4 = (int)Math.floor((double)TuSdkContext.getDisplaySize().width / 4.5D);
            TuSdkTextButton button = new TuSdkTextButton(this.getActivity());
            LinearLayout.LayoutParams layoutParams;
            (layoutParams = new LinearLayout.LayoutParams(var4, -1)).setMargins(0, 0, 0, 0);
            button.setLayoutParams(layoutParams);
            button.setGravity(17);
            var4 = TuSdkContext.dip2px(18.0F);
            button.setPadding(0, var4, 0, TuSdkContext.dip2px(10.0F));

            button.setTextColor(-1);
            button.setTextSize(2, 10.0F);
            button.setText(TuSdkContext.getString(var3));
            button.setCompoundDrawables((Drawable)null, TuSdkContext.getDrawable(key), (Drawable)null, (Drawable)null);
            button.setTag(Integer.valueOf(tag));
            button.setOnClickListener(this.mButtonClickListener);


            DisplayMetrics metric = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
            int width = metric.widthPixels/3;     // 屏幕宽度（像素）
            button.setWidth(width);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT,1);

            if(this.getActionsWrap() != null) {
                this.getActionsWrap().addView(button,params);
            }



            return button;
        }
    }

}
