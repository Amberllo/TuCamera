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

        View button = super.buildCateButton(category,tag,layoutParams);
        if(category.name.equals("lsq_sticker_cate_all")){
            button.setVisibility(View.GONE);
        }
        return button;
    }



}
