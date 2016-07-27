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
//        super.loadCategories(list);
        if(this.getParamsView() != null) {
            this.getParamsView().removeAllViews();

            LayoutParams var1 = new LayoutParams(0, -1, 1.0F);
            int var2 = 0;
            List<StickerCategory> shared = StickerLocalPackage.shared().getCategories();
//            for(Iterator var4 = shared.iterator(); var4.hasNext(); ++var2) {
//                StickerCategory var3 = (StickerCategory)var4.next();
//                this.buildCateButton(var3, var2, var1);
//            }
            this.buildCateButton(shared.get(0), var2, var1);
            this.selectCateIndex(0);
        }


    }
}
