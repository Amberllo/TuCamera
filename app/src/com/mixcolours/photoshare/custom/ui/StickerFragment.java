/**
 * TuSdkDemo
 * DemoEntryActivity.java
 *
 * @author 		Clear
 * @Date 		2014-11-15 下午4:30:52
 * @Copyright 	(c) 2014 tusdk.com. All rights reserved.
 * @link 		开发文档:http://tusdk.com/docs/android/api/
 */
package com.mixcolours.photoshare.custom.ui;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.secret.StatisticsManger;
import org.lasque.tusdk.core.view.widget.TuMaskRegionView;
import org.lasque.tusdk.core.view.widget.button.TuSdkImageButton;
import org.lasque.tusdk.impl.components.sticker.TuEditStickerFragment;
import org.lasque.tusdk.impl.components.widget.sticker.StickerBarView;
import org.lasque.tusdk.impl.components.widget.sticker.StickerItemView;
import org.lasque.tusdk.impl.components.widget.sticker.StickerView;
import org.lasque.tusdk.modules.components.ComponentActType;
import org.lasque.tusdk.modules.view.widget.sticker.StickerCategory;
import org.lasque.tusdk.modules.view.widget.sticker.StickerData;
import org.lasque.tusdk.modules.view.widget.sticker.StickerItemViewInterface;
import org.lasque.tusdk.modules.view.widget.sticker.StickerLocalPackage;
import org.lasque.tusdk.modules.view.widget.sticker.StickerResult;

import com.mixcolours.photoshare.R;
import com.mixcolours.photoshare.custom.FastBlurUtil;

import java.util.List;

/**
 * @author Amberllo
 * 帖子编辑页面
 */
public class StickerFragment extends TuEditStickerFragment
{


    private static final int BoardCategoryId = 2;
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
        StatisticsManger.appendComponent(ComponentActType.editStickerFragment);
        this.getImageView();
        this.getStickerView();
        this.getCutRegionView();
        this.getCancelButton();
        this.getCompleteButton();
        this.getListButton().setVisibility(View.GONE);
        this.getOnlineButton();
        if(this.getStickerBarView() != null) {
            this.getStickerBarView().loadCategories(this.getCategories());
        }

        originBitmap = getImage();
        try {
            StickerCategory category = StickerLocalPackage.shared().getCategories().get(0);
            StickerData stickerData = category.datas.get(0).stickers.get(0);

            fixBorder(stickerData);
            appendStickerItem(stickerData);

//            poster(stickerData);

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public TuSdkImageButton getCompleteButton() {

        TuSdkImageButton button = this.getViewById(R.id.lsq_doneButton);
        if(button != null) {
            button.setOnClickListener(this.mButtonClickListener);
        }
        return button;
    }


    @Override
    public TuSdkImageButton getCancelButton() {
        TuSdkImageButton button = this.getViewById(R.id.lsq_cancelButton);
        if(button != null) {
            button.setOnClickListener(this.mButtonClickListener);
        }
        return button;
    }

    @Override
    public void onStickerBarViewSelected(StickerBarView stickerBarView, StickerData stickerData) {
        System.out.println(getStickerView().getChildCount());
        fixBorder(stickerData);
        appendStickerItem(stickerData);
        if(stickerData.categoryId == BoardCategoryId){

            getImageView().setImageBitmap(blurImage(originBitmap));




            new CountDownTimer(1000,1000){

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    StickerView stickerView = getStickerView();
                    StickerItemView itemView = (StickerItemView) stickerView.getChildAt(stickerView.getChildCount()-1);
                    if(itemView==null)return;
                    final StickerItemViewInterface.StickerItemViewDelegate delegate = itemView.getDelegate();
                    itemView.setDelegate(new StickerItemViewInterface.StickerItemViewDelegate() {
                        @Override
                        public void onStickerItemViewClose(StickerItemViewInterface stickerItemViewInterface) {
                            delegate.onStickerItemViewClose(stickerItemViewInterface);
                            getImageView().setImageBitmap(originBitmap);
                        }

                        @Override
                        public void onStickerItemViewSelected(StickerItemViewInterface stickerItemViewInterface) {
                            delegate.onStickerItemViewSelected(stickerItemViewInterface);
                        }
                    });
                }
            }.start();


        }
//        else{
//            appendStickerItem(stickerData);
//        }


    }


//    private void poster(StickerData stickerData){
//        PosterComponentOption option = new PosterComponentOption()
//                .setDelegate(new PosterComponentOption.PosterDelegate() {
//                    @Override
//                    public void onPosterResult(PosterFragment fragment, TuSdkResult result) {
//
//                    }
//
//                    @Override
//                    public boolean onPosterResultAsync(PosterFragment fragment, TuSdkResult result) {
//                        return false;
//                    }
//                });
//
//
//        new PosterComponent(getActivity())
//                .setStickerData(stickerData)
//                .setOption(option)
//                .setImage(getImage())
//                .setTempFilePath(getTempFilePath())
//                .setImageSqlInfo(getImageSqlInfo())
//                .showComponent();
//    }

    private void fixBorder(StickerData stickerData){
        if(stickerData.categoryId == BoardCategoryId){
            if(boradWidth == 0  && boradHeight == 0){
                boradWidth = stickerData.width;
                boradHeight = stickerData.height;
            }
            stickerData.width = boradWidth * 2;
            stickerData.height = boradHeight * 2;
        }
    }

    public StickerView getStickerView() {
        StickerView view =  this.getViewById("lsq_stickerView");
        view.onStickerItemViewClose(new StickerItemViewInterface() {
            @Override
            public void setSelected(boolean b) {

            }

            @Override
            public void setSticker(StickerData stickerData) {

            }

            @Override
            public void setStroke(int i, int i1) {

            }

            @Override
            public void setParentFrame(Rect rect) {

            }

            @Override
            public void setDelegate(StickerItemViewDelegate stickerItemViewDelegate) {

            }

            @Override
            public StickerResult getResult(Rect rect) {
                return null;
            }
        });
        view.setDelegate(new StickerView.StickerViewDelegate() {
            @Override
            public boolean canAppendSticker(StickerView stickerView, StickerData stickerData) {

                Rect rect = null;
                if(getCutRegionView() != null) {
                    rect = getCutRegionView().getRegionRect();
                }
                List<StickerResult> stickers = getStickerView().getResults(rect);
                if(stickerData.categoryId == BoardCategoryId){
                    //判断是否只能加载一次相框
                    for(StickerResult result:stickers){
                        if(result.item.categoryId == BoardCategoryId){
                            Toast.makeText(getActivity(),"相框只能加载一次",Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                }
                return true;
            }
        });


        return view;
    }

    boolean autoBorder;
    public void setAutoBorder(boolean autoBorder) {
        this.autoBorder = autoBorder;
    }


    public TuMaskRegionView getCutRegionView() {
        TuMaskRegionView regionView = super.getCutRegionView();
        regionView.setEdgeMaskColor(TuSdkContext.getColor(R.color.gray_common2));
        return regionView;
    }

    private Bitmap blurImage(Bitmap originBitmap){
        int scale = 5;

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
}