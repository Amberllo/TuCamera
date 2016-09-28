package com.mixcolours.photoshare.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.mixcolours.photoshare.R;

import org.lasque.tusdk.core.view.TuSdkImageView;
import org.lasque.tusdk.impl.components.widget.sticker.StickerListGrid;

/**
 * Created by apple on 16/9/28.
 */
public class CustomStickerListGrid extends StickerListGrid {

    private CircleImageView circleImageView;

    public CustomStickerListGrid(Context var1) {
        super(var1);
    }

    public CustomStickerListGrid(Context var1, AttributeSet var2) {
        super(var1, var2);
    }

    public CustomStickerListGrid(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    @Override
    protected void onLayouted() {
        super.onLayouted();
        TuSdkImageView imageView = super.getPosterView();
        if(imageView != null) {
            imageView.setCornerRadius(0);
        }

//        super.getPosterView().setVisibility(View.GONE);
    }

    o'n

    protected CircleImageView getCircleImageView(){
        if(this.circleImageView==null){
            circleImageView = getViewById(R.id.lsq_circlePosterView);
        }
        return this.circleImageView;
    }

}
