package com.share.photoshare.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.share.photoshare.R;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.view.widget.button.TuSdkTextButton;
import org.lasque.tusdk.impl.components.widget.filter.ParameterConfigView;
import java.util.List;

/**
 * Created by Amberllo on 16/8/23.
 */
public class CustomParameterConfigView extends ParameterConfigView {
    public CustomParameterConfigView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void setParams(List<String> list, int var2) {
        super.setParams(list, var2);
        LinearLayout layout = this.getParamsView();
        for(int i=0;i<layout.getChildCount();i++){
            View child = layout.getChildAt(i);
            if(child instanceof TuSdkTextButton){
                TuSdkTextButton var7 = (TuSdkTextButton) child;
                int[] var8 = new int[]{TuSdkContext.getColor(R.color.black_common), TuSdkContext.getColor(R.color.gray_common)};
                int[][] var9 = new int[][]{{16842913}, new int[0]};
                ColorStateList var11 = new ColorStateList(var9, var8);
                var7.setTextColor(var11);
            }


        }
    }
}
