package com.mixcolours.photoshare.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.mixcolours.photoshare.R;

import org.lasque.tusdk.impl.components.widget.sticker.StickerBarTableView;
import org.lasque.tusdk.impl.components.widget.sticker.StickerListGrid;

/**
 * Created by apple on 16/9/28.
 */
public class CustomStickerBarTableView extends StickerBarTableView {
    public CustomStickerBarTableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public int getCellLayoutId() {
        return R.layout.custom_sticker_list_grid;
    }

    @Override
    protected void onViewCreated(StickerListGrid stickerListGrid, ViewGroup viewGroup, int i) {
        super.onViewCreated(stickerListGrid, viewGroup, i);

    }


}
