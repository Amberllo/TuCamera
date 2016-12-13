package com.mixcolours.photoshare.custom;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.mixcolours.photoshare.R;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.view.widget.button.TuSdkTextButton;
import org.lasque.tusdk.impl.components.widget.sticker.StickerBarTableView;
import org.lasque.tusdk.impl.components.widget.sticker.StickerBarView;
import org.lasque.tusdk.modules.view.widget.sticker.StickerCategory;
import org.lasque.tusdk.modules.view.widget.sticker.StickerData;
import org.lasque.tusdk.modules.view.widget.sticker.StickerGroup;
import org.lasque.tusdk.modules.view.widget.sticker.StickerLocalPackage;

import java.io.IOException;
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
        this.selectCateIndex(1);
    }

    protected View buildCateButton(StickerCategory category, int tag, LayoutParams layoutParams) {

        TuSdkTextButton button = (TuSdkTextButton) super.buildCateButton(category,tag,layoutParams);
        if(category.name.equals("lsq_sticker_cate_all")){
            button.setVisibility(View.GONE);
        }

        int[] var5 = new int[]{TuSdkContext.getColor(R.color.black_common), TuSdkContext.getColor(R.color.gray_common)};
        int[][] var6 = new int[][]{{16842913}, new int[0]};
        ColorStateList var7 = new ColorStateList(var6, var5);
        button.setTextColor(var7);
        return button;
    }

    @Override
    public StickerBarTableView getTableView() {
        return super.getTableView();
    }

    @Override
    protected List<StickerData> getStickerDatas(long categoryId) {
        List<StickerData> list = super.getStickerDatas(categoryId);
        if(categoryId == 2 && list.size()!=0){
            long groupId = list.get(0).groupId;
            list.clear();

            AssetManager am = getContext().getAssets();
            try {
                String[] stickers = am.list("Borader");
                for(String path: stickers){
                    String name = path.split("\\.")[0];
                    StickerData sticker = new StickerData();
                    sticker.stickerId = Long.valueOf(name);
                    sticker.categoryId = categoryId;
                    sticker.groupId =groupId;
                    sticker.stickerType = 1;
                    list.add(sticker);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;

    }
}
