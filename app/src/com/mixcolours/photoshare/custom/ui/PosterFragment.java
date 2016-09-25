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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.abner.stickerdemo.view.StickerView;
import com.mixcolours.photoshare.R;
import com.mixcolours.photoshare.custom.FastBlurUtil;
import com.mixcolours.photoshare.custom.suite.PosterComponentOption;

import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.secret.SdkValid;
import org.lasque.tusdk.impl.activity.TuImageResultFragment;
import org.lasque.tusdk.modules.view.widget.sticker.StickerData;
import org.lasque.tusdk.modules.view.widget.sticker.StickerLocalPackage;

/**
 * @author Amberllo
 * 帖子编辑页面
 */
public class PosterFragment extends TuImageResultFragment
{

    private static final int BoardCategoryId = 2;
    private View bottomBar;
    private ImageView imageView;
    private RelativeLayout imageWrapView;
    private StickerData stickerData;
    private StickerView stickerView;
    //当前处于编辑状态的气泡
    private StickerView mCurrentEditTextView;

    public static int getLayoutViewId(){
        return R.layout.custom_poster_fragment_layout;
    }

    @Override
    public void setRootViewLayoutId(int i) {
        super.setRootViewLayoutId(getLayoutViewId());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saveInstance) {
        if(this.getRootViewLayoutId() == 0) {
            this.setRootViewLayoutId(getLayoutViewId());
        }
        return super.onCreateView(inflater, viewGroup, saveInstance);
    }

    @Override
    protected void loadView(ViewGroup viewGroup) {

        bottomBar = viewGroup.findViewById(R.id.lsq_config_bottomBar);
        imageView = (ImageView)viewGroup.findViewById(R.id.lsq_imageView);
        imageWrapView = (RelativeLayout) viewGroup.findViewById(R.id.lsq_imageWrapView);


    }

    @Override
    protected void viewDidLoad(ViewGroup viewGroup) {

        int width,height;

        Bitmap bitmap = getImage();

        if(getImage()!=null && imageView!=null){
            imageView.setImageBitmap(blurImage(bitmap,5));

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


            Bitmap image = SdkValid.shared.readSticker(stickerData.groupId,stickerData.stickerImageName);
//            Bitmap image = StickerLocalPackage.shared().loadThumb(stickerData, stickerData.getPosterView());
            addSticker(image);
        }

    }

    //添加气泡
    private void addSticker(Bitmap bitmap) {
        if(bitmap==null)return;
        stickerView = new StickerView(getContext());
        stickerView.setBitmap(bitmap);
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
//                mViews.remove(textStickerView);
//                imageWrapView.removeView(textStickerView);


            }

            @Override
            public void onEdit(StickerView stickerView) {
                setCurrentEdit(stickerView);
            }

            @Override
            public void onTop(StickerView stickerView) {

            }

//            @Override
//            public void onClick(TextStickerView stickerView) {
//            }
//
//            @Override
//            public void onTop(TextStickerView stickerView) {
////                int position = mViews.indexOf(stickerView);
////                if (position == mViews.size() - 1) {
////                    return;
////                }
////                TextStickerView textView = (TextStickerView) mViews.remove(position);
////                mViews.add(mViews.size(), textView);
//            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        imageWrapView.addView(stickerView, lp);
        setCurrentEdit(stickerView);
    }

    private void fixBorder(){
        if(stickerData.categoryId == BoardCategoryId){

        }
    }

    /**
     * 设置当前处于编辑模式的气泡
     */
    private void setCurrentEdit(StickerView stickerView) {

        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentEditTextView = stickerView;
        mCurrentEditTextView.setInEdit(true);
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
        if(this.delegate != null)delegate.onPosterResult(this, tuSdkResult);
    }

    @Override
    protected boolean asyncNotifyProcessing(TuSdkResult tuSdkResult) {
        return this.delegate == null?false:this.delegate.onPosterResultAsync(this, tuSdkResult);
    }

    public void setStickerData(StickerData stickerData) {
        this.stickerData = stickerData;
//        stickerData.width = boradWidth * 2;
//        stickerData.height = boradHeight * 2;
    }

    PosterComponentOption.PosterDelegate delegate;
    public void setDelegate(PosterComponentOption.PosterDelegate delegate) {
        this.delegate = delegate;
    }
}