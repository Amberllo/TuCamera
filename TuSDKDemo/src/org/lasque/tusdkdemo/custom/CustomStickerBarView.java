package org.lasque.tusdkdemo.custom;

import android.content.Context;
import android.util.AttributeSet;

import org.lasque.tusdk.impl.components.widget.sticker.StickerBarView;
import org.lasque.tusdk.modules.view.widget.sticker.StickerCategory;
import org.lasque.tusdk.modules.view.widget.sticker.StickerLocalPackage;

import java.util.ArrayList;
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
//        if(this.getParamsView() != null) {
////            this.getParamsView().removeAllViews();
//
//            LayoutParams layoutParams = new LayoutParams(0, -1, 1.0F);
//            int tag = 0;
//            List<StickerCategory> shared = StickerLocalPackage.shared().getCategories();
//            for(Iterator var4 = shared.iterator(); var4.hasNext(); ++tag) {
//                StickerCategory var3 = (StickerCategory)var4.next();
//                this.buildCateButton(var3, tag, layoutParams);
//            }
//            this.selectCateIndex(0);
//        }


    }


    public void loadCategoriesList(List<StickerCategory> var1) {
        if(this.getParamsView() != null) {
            this.getParamsView().removeAllViews();
            super.loadCategories(var1);
        }
    }

}
