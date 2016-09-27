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

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.secret.StatisticsManger;
//import org.lasque.tusdk.core.struct.TuSdkSize;
import org.lasque.tusdk.core.struct.TuSdkSize;
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
//    int boradWidth = 0;
//    int boradHeight = 0;

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
        this.getCutRegionView().setVisibility(View.GONE);

        this.getCancelButton();
        this.getCompleteButton();
        this.getListButton().setVisibility(View.GONE);
        this.getOnlineButton();
        if(this.getStickerBarView() != null) {
            this.getStickerBarView().loadCategories(this.getCategories());
        }

        originBitmap = getImage();
        this.getViewById(R.id.lsq_stickerView_root).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mCurrentBoraderView!=null)mCurrentBoraderView.setInEdit(false);
                return false;
            }
        });
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
                getImageView().setImageBitmap(originBitmap);

            }

            @Override
            public void onEdit(BoraderStickerView stickerView) {
                setCurrentEdit(stickerView);
            }

            @Override
            public void onFull(BoraderStickerView stickerView) {

                Bitmap posterBitmap = BitmapUtils.combineBoraderBitmap(originBitmap,stickerBitmap);
                float scale = 1.0f;
                //调整相框大小
                if(posterBitmap.getWidth() > posterBitmap.getHeight()){
                    //横相框
                    scale = (float)getImageView().getWidth() / (float)posterBitmap.getWidth();
                }else{
                    //竖相框
                    scale = (float)getImageView().getHeight() / (float)posterBitmap.getHeight();
                }
                Bitmap fullScreen = BitmapUtils.resize(posterBitmap,scale);
                getImageView().setImageBitmap(fullScreen);
                refixView(fullScreen);
                mCurrentBoraderView.setInEdit(false);
                getStickerView().setVisibility(View.GONE);
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

//    private void fixBorder(StickerData stickerData){
//        if(stickerData.categoryId == BoardCategoryId){
//            if(boradWidth == 0  && boradHeight == 0){
//                boradWidth = stickerData.width;
//                boradHeight = stickerData.height;
//            }
//            stickerData.width = boradWidth * 2;
//            stickerData.height = boradHeight * 2;
//        }
//    }



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





        RelativeLayout warpper = getStickerView();
        Bitmap stickerBitmap = Bitmap.createBitmap(warpper.getWidth(),warpper.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(stickerBitmap);
        warpper.draw(canvas1);

        Bitmap imageBitmap = Bitmap.createBitmap(getImageView().getWidth(),getImageView().getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(imageBitmap);
        getImageView().draw(canvas2);


        Bitmap bitmap = Bitmap.createBitmap(getImageView().getWidth(),getImageView().getHeight(), Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(bitmap);
        cv.drawBitmap(imageBitmap, 0 , 0, null);
        if(warpper.getVisibility() == View.VISIBLE){
            cv.drawBitmap(stickerBitmap, 0, 0, null);
        }
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();


        String imagePath = FileUtils.saveBitmapToLocal(bitmap, getActivity());
        TuSdkResult result = new TuSdkResult();

        result.image = bitmap;
        result.imageFile = new File(imagePath);
        return result;

    }

    @Override
    protected void handleCompleteButton() {
//        getCutRegionView().setVisibility(View.VISIBLE);

        getStickerView().cancelAllStickerSelected();
        if(mCurrentBoraderView!=null)mCurrentBoraderView.setInEdit(false);

        if(this.getStickerView() == null) {
            this.handleBackButton();
        } else {
            final TuSdkResult tuSdkResult = new TuSdkResult();
            Rect rect = null;
            if(this.getCutRegionView() != null) {
                rect = this.getCutRegionView().getRegionRect();
            }

            tuSdkResult.stickers = this.getStickerView().getResults(rect);
            if( mViews.size()!=0 ||  (tuSdkResult.stickers != null && tuSdkResult.stickers.size() != 0)) {
                this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
                (new Thread(new Runnable() {
                    public void run() {
                        asyncEditWithResult(tuSdkResult);
                    }
                })).start();
            } else {
                this.handleBackButton();
            }
        }

    }


    @Override
    protected void asyncEditWithResult(TuSdkResult result) {

        result = generateBitmap();
        this.asyncProcessingIfNeedSave(result);
    }

    protected void setImageRegionMask(Bitmap var1) {
        super.setImageRegionMask(var1);
        this.getCutRegionView().setVisibility(View.GONE);
    }

    @Override
    protected void viewDidLoad(ViewGroup viewGroup) {
        super.viewDidLoad(viewGroup);
        refixView(getImage());
        try {
            StickerCategory category = StickerLocalPackage.shared().getCategories().get(0);
            StickerData stickerData = category.datas.get(0).stickers.get(0);

            Bitmap bitmap = BitmapUtils.getStickerFromAccess(getContext(),stickerData.stickerId);
            addBoraderSticker(bitmap);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void refixView(Bitmap bitmap){
        ImageView imageView = getImageView();
        StickerView imageWrapView = getStickerView();
        int height;
        int width;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        RelativeLayout.LayoutParams paramsLayout = (RelativeLayout.LayoutParams) imageWrapView.getLayoutParams();

        if(bitmap.getWidth()<bitmap.getHeight()){
            int resizeWidth = imageView.getHeight() * bitmap.getWidth() / bitmap.getHeight();
            params.width = resizeWidth ;
            paramsLayout.width = resizeWidth;

            height = imageView.getHeight();
            width = resizeWidth;

        }else{
            int resizeHeight = imageView.getWidth() * bitmap.getHeight() / bitmap.getWidth();
            params.height= resizeHeight;
            paramsLayout.height = resizeHeight;

            height = resizeHeight;
            width = imageView.getWidth();

        }

        imageView.setLayoutParams(params);
        imageWrapView.setLayoutParams(paramsLayout);
    }


}