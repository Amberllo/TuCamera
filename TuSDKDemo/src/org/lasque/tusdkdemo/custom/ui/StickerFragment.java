/**
 * TuSdkDemo
 * DemoEntryActivity.java
 *
 * @author 		Clear
 * @Date 		2014-11-15 下午4:30:52
 * @Copyright 	(c) 2014 tusdk.com. All rights reserved.
 * @link 		开发文档:http://tusdk.com/docs/android/api/
 */
package org.lasque.tusdkdemo.custom.ui;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import com.example.abner.stickerdemo.utils.FileUtils;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.secret.StatisticsManger;
import org.lasque.tusdk.impl.components.sticker.TuEditStickerFragment;
import org.lasque.tusdk.impl.components.widget.sticker.StickerBarView;
import org.lasque.tusdk.modules.components.ComponentActType;
import org.lasque.tusdk.modules.view.widget.sticker.StickerFactory;
import org.lasque.tusdkdemo.R;
import org.lasque.tusdkdemo.custom.CustomStickerBarView;

import java.io.File;

/**
 * @author Amberllo
 * 帖子编辑页面
 */
public class StickerFragment extends TuEditStickerFragment
{

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
//                this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
//                (new Thread(new Runnable() {
//                    public void run() {
//                        StickerFragment.this.asyncEditWithResult(var1);
//                    }
//                })).start();
            } else {
                this.handleBackButton();
            }
        }
    }
}