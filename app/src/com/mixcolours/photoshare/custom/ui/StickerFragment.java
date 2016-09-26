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
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.TuSdkResult;
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

import com.example.abner.stickerdemo.utils.FileUtils;
import com.example.abner.stickerdemo.view.BoraderStickerView;
import com.mixcolours.photoshare.R;
import com.mixcolours.photoshare.custom.BitmapUtils;
import com.mixcolours.photoshare.custom.FastBlurUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Amberllo
 * 帖子编辑页面
 */
public class StickerFragment extends TuEditStickerFragment
{

    boolean isBlur = false;
    private static final int BoardCategoryId = 2;
    int boradWidth = 0;
    int boradHeight = 0;

    Bitmap originBitmap;
    BoraderStickerView mCurrentBoraderView;
    List<View> mViews = new ArrayList<>();

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

            Bitmap bitmap = BitmapUtils.getStickerFromAccess(getContext(),stickerData.stickerId);
            addBoraderSticker(bitmap);

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

        StickerLocalPackage.shared().loadStickerItem(stickerData);
        if(stickerData.categoryId == BoardCategoryId){

            if(mViews.size()!=0){
                //判断是否只能加载一次相框
                Toast.makeText(getActivity(),"相框只能加载一次",Toast.LENGTH_SHORT).show();
                return;
            }

            Bitmap bitmap = BitmapUtils.getStickerFromAccess(getContext(),stickerData.stickerId);
            addBoraderSticker(bitmap);

        }else{
            appendStickerItem(stickerData);
            setStickerButtonDelegate();
        }

    }

    private void setStickerButtonDelegate(){
        if (mCurrentBoraderView != null) {
            mCurrentBoraderView.setInEdit(false);
        }
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                StickerView stickerView = getStickerView();
                StickerItemView itemView = (StickerItemView) stickerView.getChildAt(stickerView.getChildCount()-1);
                if(itemView!=null){
                    final StickerItemViewInterface.StickerItemViewDelegate delegate = itemView.getDelegate();
                    itemView.setDelegate(new StickerItemViewInterface.StickerItemViewDelegate() {
                        @Override
                        public void onStickerItemViewClose(StickerItemViewInterface stickerItemViewInterface) {
                            delegate.onStickerItemViewClose(stickerItemViewInterface);
                        }

                        @Override
                        public void onStickerItemViewSelected(StickerItemViewInterface stickerItemViewInterface) {
                            delegate.onStickerItemViewSelected(stickerItemViewInterface);
                            if(mCurrentBoraderView!=null)
                            mCurrentBoraderView.setInEdit(false);

                        }
                    });
                    timer.cancel();
                }
            }
        },200);



    }


    //添加气泡
    private void addBoraderSticker(final Bitmap stickerBitmap) {

        final BoraderStickerView boraderStickerView = new BoraderStickerView(getActivity());
        boraderStickerView.setBitmap(stickerBitmap);
        boraderStickerView.setOperationListener(new BoraderStickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(boraderStickerView);
                getStickerView().removeView(boraderStickerView);
                getImageView().setImageBitmap(blurImage(originBitmap));

            }

            @Override
            public void onEdit(BoraderStickerView stickerView) {
                setCurrentEdit(stickerView);
            }

            @Override
            public void onFull(BoraderStickerView stickerView) {

                float scale = 1.0f;
                if(getImage().getWidth() > getImage().getHeight()){
                    //横图
                    scale = ((float)getImageView().getHeight() /(float)stickerBitmap.getHeight());
                }else{
                    //竖图
                    scale = ((float)getImageView().getWidth() /(float)stickerBitmap.getWidth());
                }
                Bitmap fullScreen = BitmapUtils.resize(stickerBitmap,scale);
                Bitmap posterBitmap = BitmapUtils.combineBoraderBitmap(originBitmap,fullScreen);
                getImageView().setImageBitmap(posterBitmap);
//                getStickerView().setVisibility(View.GONE);

            }

            @Override
            public void onBlur(BoraderStickerView stickerView) {
                if(isBlur){
                    getImageView().setImageBitmap(originBitmap);
                    boraderStickerView.setBitmap(stickerBitmap);
                    isBlur = false;
                }else{
                    getImageView().setImageBitmap(blurImage(originBitmap));
                    Bitmap posterBitmap = BitmapUtils.combineBoraderBitmap(originBitmap,stickerBitmap);
                    boraderStickerView.setBitmap(posterBitmap);
                    isBlur = true;
                }
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        getStickerView().addView(boraderStickerView, lp);
        mViews.add(boraderStickerView);
        setCurrentEdit(boraderStickerView);
    }


    /**
     * 设置当前处于编辑模式的气泡
     */
    private void setCurrentEdit(BoraderStickerView stickerView) {
        if (mCurrentBoraderView != null) {
            mCurrentBoraderView.setInEdit(false);
        }
        mCurrentBoraderView = stickerView;
        mCurrentBoraderView.setInEdit(true);
        getStickerView().cancelAllStickerSelected();
    }

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
        view.setDelegate(new StickerView.StickerViewDelegate() {
            @Override
            public boolean canAppendSticker(StickerView stickerView, StickerData stickerData) {

                if(stickerData.categoryId == BoardCategoryId && mViews.size()!=0){

                    //判断是否只能加载一次相框
                    Toast.makeText(getActivity(),"相框只能加载一次",Toast.LENGTH_SHORT).show();
                    return false;

                }

                return true;
            }
        });


        return view;
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

    private TuSdkResult generateBitmap() {

        Bitmap bitmap = Bitmap.createBitmap(getStickerView().getWidth(),
                getStickerView().getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        getStickerView().draw(canvas);

        String imagePath = FileUtils.saveBitmapToLocal(bitmap, getActivity());
        TuSdkResult result = new TuSdkResult();

        result.image = bitmap;
        result.imageFile = new File(imagePath);
        return result;

    }

    @Override
    protected void handleCompleteButton() {
//        super.handleCompleteButton();
        if(this.getStickerView() == null) {
            this.handleBackButton();
        } else {
            TuSdkResult result = new TuSdkResult();
            Rect rect = null;
            if(this.getCutRegionView() != null) {
                rect = this.getCutRegionView().getRegionRect();
            }

            result.stickers = this.getStickerView().getResults(rect);


            if(result.stickers != null && result.stickers.size() != 0) {
//                this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
//                (new Thread(new Runnable() {
//                    public void run() {
//                        asyncEditWithResult(result);
//                    }
//                })).start();

//                ImageView imageView = getViewById(R.id.lsq_resultImage);
//                imageView.setVisibility(View.VISIBLE);
//                this.loadOrginImage(result);
//                Bitmap bitmap = StickerFactory.megerStickers(result.image, result.stickers);


//                getStickerView().cancelAllStickerSelected();
//                if(mCurrentBoraderView !=null) mCurrentBoraderView.setInEdit(false);
//                result = generateBitmap();
//                imageView.setImageBitmap(result.image);

            } else {
                this.handleBackButton();
            }
        }
    }

}