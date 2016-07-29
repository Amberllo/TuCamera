package com.share.photoshare.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.view.widget.button.TuSdkTextButton;
import org.lasque.tusdk.impl.components.widget.sticker.StickerBarView;
import org.lasque.tusdk.modules.view.widget.sticker.StickerCategory;
import org.lasque.tusdk.modules.view.widget.sticker.StickerLocalPackage;

import java.util.Iterator;
import java.util.List;

/**
 * Created by LYL on 2016/7/27.
 */
public class CustomStickerBarView extends StickerBarView {
    public CustomStickerBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void loadCategories(List<StickerCategory> list) {
        super.loadCategories(list);
        this.selectCateIndex(1);
    }

    protected View buildCateButton(StickerCategory category, int tag, LayoutParams layoutParams) {

        TuSdkTextButton button = new TuSdkTextButton(this.getContext());
        int[] color = new int[]{TuSdkContext.getColor("lsq_filter_config_highlight"), TuSdkContext.getColor("lsq_color_white")};
        int[][] states = new int[][]{{16842913}, new int[0]};
        ColorStateList colorStateList = new ColorStateList(states, color);
        button.setTextColor(colorStateList);
        button.setGravity(17);
        button.setText(TuSdkContext.getString(category.name));
        button.setTextSize(2, 12.0F);
        button.setEllipsize(TextUtils.TruncateAt.END);
        button.setLines(1);
        button.setTag(Integer.valueOf(tag));
        button.setOnClickListener(this.mButtonClickListener);
        this.getParamsView().addView(button, layoutParams);

        if(category.name.equals("lsq_sticker_cate_all")){
            button.setVisibility(View.GONE);
        }
        return button;
    }

}