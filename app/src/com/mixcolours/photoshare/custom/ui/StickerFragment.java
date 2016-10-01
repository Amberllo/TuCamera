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

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.secret.StatisticsManger;
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
import com.mixcolours.photoshare.custom.CustomStickerBarView;
import com.mixcolours.photoshare.custom.FastBlurUtil;
import com.mixcolours.photoshare.photoview.PhotoView;

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
    boolean isFull = false;
    private static final int BoardCategoryId = 2;
    Bitmap originBitmap;
    ImageView fullImageView;
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
            Bitmap bitmap = BitmapUtils.getStickerFromAccess(getContext(),stickerData.stickerId);
            if(mViews.size()!=0){
                //判断是否只能加载一次相框
//                Toast.makeText(getActivity(),"相框只能加载一次",Toast.LENGTH_SHORT).show();
                mCurrentBoraderView.resetStickerBitmap(isBlur?BitmapUtils.combineBoraderBitmap(originBitmap,bitmap):bitmap);
                if(isFull)full(bitmap);
            }else{
                addBoraderSticker(bitmap);
            }




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
        TuSdkSize sdkSize = getImageWH(originBitmap);
        final BoraderStickerView boraderStickerView = new BoraderStickerView(getActivity(),sdkSize.width,sdkSize.height);
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

                full(stickerView.getStickerBitmap());
            }

            @Override
            public void onBlur(BoraderStickerView stickerView) {
                Bitmap stickerBitmap = stickerView.getStickerBitmap();
                if(isBlur){
                    getImageView().setImageBitmap(originBitmap);
                    stickerView.resetBitmap(stickerBitmap);
                    isBlur = false;
                }else{
                    getImageView().setImageBitmap(blurImage(originBitmap));

                    Bitmap posterBitmap = BitmapUtils.combineBoraderBitmap(originBitmap,stickerBitmap);
                    stickerView.resetBitmap(posterBitmap);
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

    private void full(Bitmap stickerBitmap){

        TuSdkSize fullWH = getFullScreenWH();
        float ratioSticker = (float)stickerBitmap.getWidth()/ (float)stickerBitmap.getHeight();
        float ratioFull = (float)fullWH.width / (float)fullWH.height;

        //1.将相框放大至全屏
        float scale = ratioSticker  > ratioFull ?
                (float)fullWH.width / (float)stickerBitmap.getWidth():
               (float)fullWH.height / (float)stickerBitmap.getHeight();
        final Bitmap fullBorader = BitmapUtils.resize(stickerBitmap,scale);
        refixView(fullBorader.getWidth(), fullBorader.getHeight(), new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                getFullImageView().setImageBitmap(fullBorader);

                Bitmap cropBitmap = BitmapUtils.cropBitmapCenter(originBitmap,fullBorader.getWidth(),fullBorader.getHeight());
                getImageView().setImageBitmap(cropBitmap);
                getImageView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });




        if(mCurrentBoraderView!=null){
            mCurrentBoraderView.setInEdit(false);
            mCurrentBoraderView.setVisibility(View.GONE);
        }
        if(getImageView() instanceof PhotoView){
            ((PhotoView)getImageView()).enable();
        }

        getFullImageView().setVisibility(View.VISIBLE);
        isFull  =  true;
    }
    private TuSdkResult generateBitmap() {

        Bitmap fullBitmap = null;
        Bitmap stickerBitmap = null;

        if(getStickerView().getVisibility() == View.VISIBLE) {
            RelativeLayout stickerView = getStickerView();
            stickerBitmap = Bitmap.createBitmap(stickerView.getWidth(), stickerView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas1 = new Canvas(stickerBitmap);
            stickerView.draw(canvas1);
        }


        if(isFull){
            ImageView fullImageView = getFullImageView();
            fullBitmap = Bitmap.createBitmap(fullImageView.getWidth(), fullImageView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvasFull = new Canvas(fullBitmap);
            fullImageView.draw(canvasFull);
        }


        Bitmap imageBitmap = Bitmap.createBitmap(getImageView().getWidth(),getImageView().getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasOrigin = new Canvas(imageBitmap);
        getImageView().draw(canvasOrigin);

        Bitmap bitmap = Bitmap.createBitmap(getImageView().getWidth(),getImageView().getHeight(), Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(bitmap);
        cv.drawBitmap(imageBitmap, 0 , 0, null);
        if(stickerBitmap!=null)cv.drawBitmap(stickerBitmap, 0, 0, null);
        if(fullBitmap!=null)cv.drawBitmap(fullBitmap, 0, 0, null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();


        String imagePath = FileUtils.saveBitmapToLocal(bitmap, getActivity());
        TuSdkResult result = new TuSdkResult();

        result.image = bitmap;
        result.imageFile = new File(imagePath);
        return result;

    }

    @Override
    public StickerBarView getStickerBarView() {
        CustomStickerBarView stickerBarView = (CustomStickerBarView) super.getStickerBarView();
        return stickerBarView;
    }

    public ImageView getFullImageView(){
        if(fullImageView==null){
            fullImageView = getViewById(R.id.lsq_fullImageView);
        }
        return fullImageView;
    }

    @Override
    public TuMaskRegionView getCutRegionView() {
        TuMaskRegionView regionView = super.getCutRegionView();
        regionView.setEdgeMaskColor(TuSdkContext.getColor(R.color.gray_common2));
        return regionView;
    }
    @Override
    protected void handleCompleteButton() {

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

    @Override
    protected void setImageRegionMask(Bitmap var1) {
        super.setImageRegionMask(var1);
        this.getCutRegionView().setVisibility(View.GONE);
    }

    @Override
    protected void viewDidLoad(ViewGroup viewGroup) {
        super.viewDidLoad(viewGroup);
        TuSdkSize size = getImageWH(getImage());
        refixView(size.width, size.height, new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                try {
                    StickerCategory category = StickerLocalPackage.shared().getCategories().get(0);
                    StickerData stickerData = category.datas.get(0).stickers.get(0);

                    Bitmap bitmap = BitmapUtils.getStickerFromAccess(getContext(),stickerData.stickerId);
                    addBoraderSticker(bitmap);

                }catch (Exception e){
                    e.printStackTrace();
                }
                getImageView().getViewTreeObserver() .removeOnGlobalLayoutListener(this);
            }
        });


    }

    private void refixView(int width,int height,ViewTreeObserver.OnGlobalLayoutListener listener){
        ImageView imageView = getImageView();
        StickerView imageWrapView = getStickerView();
        ImageView imageFullView = getFullImageView();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        RelativeLayout.LayoutParams paramsLayout = (RelativeLayout.LayoutParams) imageWrapView.getLayoutParams();
        RelativeLayout.LayoutParams paramsFull = (RelativeLayout.LayoutParams) imageFullView.getLayoutParams();

        params.width = width ;
        paramsLayout.width = width;
        paramsFull.width = width;

        params.height= height;
        paramsLayout.height = height;
        paramsFull.height = height;
        imageView.setLayoutParams(params);
        imageWrapView.setLayoutParams(paramsLayout);
        imageFullView.setLayoutParams(paramsFull);

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }



    private TuSdkSize getImageWH(Bitmap bitmap){
        TuSdkSize fullSize = getFullScreenWH();
        int width = 0,height = 0;
        if(getImage()!=null){
            if(bitmap.getWidth()<bitmap.getHeight()){
                height = fullSize.height;
                width = fullSize.height * bitmap.getWidth() / bitmap.getHeight();
            }else{
                height = fullSize.width * bitmap.getHeight() / bitmap.getWidth();
                width = fullSize.width;
            }
        }
        return TuSdkSize.create(width,height);

    }

    private TuSdkSize getFullScreenWH(){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int mSscreenWidth = dm.widthPixels;
        int mScreenHeight = dm.heightPixels;
        return TuSdkSize.create(mSscreenWidth,mScreenHeight-getStickerBarView().getHeight());
    }



}