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

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abner.stickerdemo.utils.FileUtils;

import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.secret.StatisticsManger;
import org.lasque.tusdk.impl.components.sticker.TuEditStickerFragment;
import org.lasque.tusdk.impl.components.widget.sticker.StickerBarView;
import org.lasque.tusdk.impl.components.widget.sticker.StickerView;
import org.lasque.tusdk.modules.components.ComponentActType;
import org.lasque.tusdk.modules.view.widget.sticker.StickerCategory;
import org.lasque.tusdk.modules.view.widget.sticker.StickerData;
import org.lasque.tusdk.modules.view.widget.sticker.StickerFactory;
import org.lasque.tusdk.modules.view.widget.sticker.StickerLocalPackage;
import org.lasque.tusdk.modules.view.widget.sticker.StickerResult;

import com.share.photoshare.R;
import com.share.photoshare.custom.CustomStickerBarView;

import java.io.File;
import java.util.List;

/**
 * @author Amberllo
 * 帖子编辑页面
 */
public class StickerFragment extends TuEditStickerFragment
{


    int boradWidth = 0;
    int boradHeight = 0;

    private TuEditStickerFragmentDelegate delegate;

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

        try {
            StickerCategory category = StickerLocalPackage.shared().getCategories().get(0);
            StickerData stickerData = category.datas.get(0).stickers.get(0);
            fixBorder(stickerData);
            appendStickerItem(stickerData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setDelegate(TuEditStickerFragmentDelegate delegate) {
        super.setDelegate(delegate);
        this.delegate = delegate;
    }

    @Override
    public StickerBarView getStickerBarView() {


        CustomStickerBarView k = this.getViewById("lsq_sticker_bar");
            if( k != null) {
                k.setGridLayoutId(this.getGridLayoutId());
                k.setGridWidth(this.getGridWidth());
                k.setGridPadding(this.getGridPadding());
                k.setGridHeight(this.getGridHeight());
                k.setDelegate(this);

            }

        return k;
        
    }

    @Override
    public void onStickerBarViewSelected(StickerBarView stickerBarView, StickerData stickerData) {
        fixBorder(stickerData);
        appendStickerItem(stickerData);
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

    public StickerView getStickerView() {
        StickerView view =  this.getViewById("lsq_stickerView");
        view.setDelegate(new StickerView.StickerViewDelegate() {
            @Override
            public boolean canAppendSticker(StickerView stickerView, StickerData stickerData) {

                Rect rect = null;
                if(getCutRegionView() != null) {
                    rect = getCutRegionView().getRegionRect();
                }
                List<StickerResult> stickers = getStickerView().getResults(rect);
                if(stickerData.categoryId == 3){
                    //判断是否只能加载一次相框
                    for(StickerResult result:stickers){
                        if(result.item.categoryId == 3){
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

    @Override
    protected void handleCompleteButton() {

        if(this.getStickerView() == null) {
            this.handleBackButton();
        } else {
            final TuSdkResult result = new TuSdkResult();
            Rect rect = null;
            if(this.getCutRegionView() != null) {
                rect = this.getCutRegionView().getRegionRect();
            }
            this.loadOrginImage(result);
            result.stickers = this.getStickerView().getResults(rect);
            if(result.stickers != null && result.stickers.size() != 0) {

                result.image = StickerFactory.megerStickers(result.image, result.stickers);
                result.imageFile = new File(FileUtils.saveBitmapToLocal(result.image,getContext()));
                result.stickers = null;

                if(delegate!=null){
                    delegate.onTuEditStickerFragmentEdited(this,result);
                }
                handleBackButton();
            } else {
                this.handleBackButton();
            }
        }
    }

    boolean autoBorder;
    public void setAutoBorder(boolean autoBorder) {
        this.autoBorder = autoBorder;
    }



}