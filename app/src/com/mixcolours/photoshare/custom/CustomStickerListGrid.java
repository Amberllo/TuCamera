package com.mixcolours.photoshare.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.mixcolours.photoshare.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.lasque.tusdk.core.view.TuSdkImageView;
import org.lasque.tusdk.impl.components.widget.sticker.StickerListGrid;
import org.lasque.tusdk.modules.view.widget.sticker.StickerData;
import org.lasque.tusdk.modules.view.widget.sticker.StickerLocalPackage;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apple on 16/9/28.
 */
public class CustomStickerListGrid extends StickerListGrid {

    private ImageView circleImageView;

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
        if(super.getPosterView()!=null){
            super.getPosterView().setVisibility(View.GONE);
        }
    }




    protected ImageView getCircleImageView(){
        if(this.circleImageView==null){
            circleImageView = getViewById(R.id.lsq_circlePosterView);
        }
        return this.circleImageView;
    }

    @Override
    protected void bindModel() {
        super.bindModel();

        StickerData stickerData = this.getModel();
        StickerLocalPackage.shared().loadStickerItem(stickerData);
        circleImageView.setImageBitmap(stickerData.getImage());
    }

    @Override
    public void viewNeedRest() {
        super.viewNeedRest();

        StickerLocalPackage.shared().cancelLoadImage(this.getCircleImageView());
        if(this.getCircleImageView() != null) {
            this.getCircleImageView().setImageBitmap((Bitmap)null);
        }
    }
}
