/**
 * TuSdkDemo
 * DemoEntryActivity.java
 *
 * @author 		Clear
 * @Date 		2014-11-15 下午4:30:52
 * @Copyright 	(c) 2014 tusdk.com. All rights reserved.
 * @link 		开发文档:http://tusdk.com/docs/android/api/
 */
package com.share.photoshare.custom.ui;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.share.photoshare.R;
import com.share.photoshare.custom.FastBlurUtil;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.secret.StatisticsManger;
import org.lasque.tusdk.core.view.widget.TuMaskRegionView;
import org.lasque.tusdk.core.view.widget.button.TuSdkImageButton;
import org.lasque.tusdk.impl.activity.TuImageResultFragment;
import org.lasque.tusdk.impl.components.sticker.TuEditStickerFragment;
import org.lasque.tusdk.impl.components.widget.sticker.StickerBarView;
import org.lasque.tusdk.impl.components.widget.sticker.StickerView;
import org.lasque.tusdk.modules.components.ComponentActType;
import org.lasque.tusdk.modules.view.widget.sticker.StickerCategory;
import org.lasque.tusdk.modules.view.widget.sticker.StickerData;
import org.lasque.tusdk.modules.view.widget.sticker.StickerLocalPackage;
import org.lasque.tusdk.modules.view.widget.sticker.StickerResult;

import java.util.List;

/**
 * @author Amberllo
 * 帖子编辑页面
 */
public class PosterFragment extends TuImageResultFragment
{


    int boradWidth = 0;
    int boradHeight = 0;

    Bitmap originBitmap;

    public static int getLayoutViewId(){
        return R.layout.custom_sticker_fragment_layout;
    }

    @Override
    public void setRootViewLayoutId(int i) {
        super.setRootViewLayoutId(getLayoutViewId());
    }

    @Override
    protected void loadView(ViewGroup viewGroup) {
//        StatisticsManger.appendComponent(ComponentActType.editStickerFragment);
//        this.getImageView();
//        this.getStickerView();
//        this.getCutRegionView();
//        this.getCancelButton();
//        this.getCompleteButton();
//        this.getListButton().setVisibility(View.GONE);
//        this.getOnlineButton();
//        if(this.getStickerBarView() != null) {
//            this.getStickerBarView().loadCategories(this.getCategories());
//        }
//
//        originBitmap = getImage();
//        try {
//            StickerCategory category = StickerLocalPackage.shared().getCategories().get(0);
//            StickerData stickerData = category.datas.get(0).stickers.get(0);
//
//            fixBorder(stickerData);
//            appendStickerItem(stickerData);
//        }catch (Exception e){
//            e.printStackTrace();
//        }


    }

    @Override
    protected void viewDidLoad(ViewGroup viewGroup) {

    }

    private void fixBorder(StickerData stickerData){
        if(stickerData.categoryId == 3){
            if(boradWidth == 0  && boradHeight == 0){
                boradWidth = stickerData.width;
                boradHeight = stickerData.height;
            }
            stickerData.width = boradWidth * 2;
            stickerData.height = boradHeight * 2;
        }
    }


    private Bitmap blurImage(Bitmap originBitmap, int scale){
        if(scale== 0){
            return originBitmap;
        }

        long start = System.currentTimeMillis();
        int scaleRatio = 10;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originBitmap,
                originBitmap.getWidth() / scaleRatio,
                originBitmap.getHeight() / scaleRatio,
                false);
        Bitmap blurBitmap = FastBlurUtil.doBlur(scaledBitmap, scale, true);
        Log.i("blurtime"," scale = "+scale +" " + String.valueOf(System.currentTimeMillis() - start));
        return blurBitmap ;
    }

    @Override
    protected void notifyProcessing(TuSdkResult tuSdkResult) {

    }

    @Override
    protected boolean asyncNotifyProcessing(TuSdkResult tuSdkResult) {
        return false;
    }
}