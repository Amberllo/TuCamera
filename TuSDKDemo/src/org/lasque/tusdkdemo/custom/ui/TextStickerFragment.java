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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.abner.stickerdemo.utils.FileUtils;
import com.example.abner.stickerdemo.view.BubbleInputDialog;
import com.example.abner.stickerdemo.view.BubbleTextView;
import com.example.abner.stickerdemo.view.StickerView;
import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.view.widget.button.TuSdkImageButton;
import org.lasque.tusdk.impl.activity.TuImageResultFragment;
import org.lasque.tusdkdemo.R;
import org.lasque.tusdkdemo.custom.suite.TextStickerOption;
import java.util.ArrayList;

/**
 * @author Amberllo
 * 文字编辑页面
 */
public class TextStickerFragment extends TuImageResultFragment implements View.OnClickListener

{
    TextStickerOption.TextStickerDelegate delegate;
    //气泡输入框
    private BubbleInputDialog mBubbleInputDialog;
    RelativeLayout imageWrapView;
    ImageView imageView;
    TuSdkImageButton cancelButton;
    TuSdkImageButton okButton;

    //存储贴纸列表
    private ArrayList<View> mViews = new ArrayList<>();
    //当前处于编辑状态的贴纸
    private StickerView mCurrentView;
    //当前处于编辑状态的气泡
    private BubbleTextView mCurrentEditTextView;
    public static int getLayoutId() {
        return R.layout.custom_textedit_fragment_layout;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saveInstance) {
        if(this.getRootViewLayoutId() == 0) {
            this.setRootViewLayoutId(getLayoutId());
        }
        return super.onCreateView(inflater, viewGroup, saveInstance);
    }

    @Override
    protected void loadView(ViewGroup viewGroup) {

        imageView = (ImageView)viewGroup.findViewById(R.id.lsq_imageView);
        imageWrapView = (RelativeLayout)viewGroup.findViewById(R.id.lsq_imageWrapView);

        cancelButton = (TuSdkImageButton)viewGroup.findViewById(R.id.lsq_configCancelButton);
        cancelButton.setOnClickListener(this);
        okButton = (TuSdkImageButton)viewGroup.findViewById(R.id.lsq_configCompleteButton);
        okButton.setOnClickListener(this);
        mBubbleInputDialog = new BubbleInputDialog(getContext());
        mBubbleInputDialog.setCompleteCallBack(new BubbleInputDialog.CompleteCallBack() {
            @Override
            public void onComplete(View bubbleTextView, String str) {
                ((BubbleTextView) bubbleTextView).setText(str);
            }
        });

        addBubble();
    }

    @Override
    protected void viewDidLoad(ViewGroup viewGroup) {


        if(getImage()!=null && imageView!=null){
            imageView.setImageBitmap(getImage());
        }
    }


    //添加气泡
    private void addBubble() {
        final BubbleTextView bubbleTextView = new BubbleTextView(getContext(),Color.WHITE,0);
        bubbleTextView.setImageResource(R.mipmap.bubble_7_rb);
        bubbleTextView.setOperationListener(new BubbleTextView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(bubbleTextView);
                imageWrapView.removeView(bubbleTextView);
            }

            @Override
            public void onEdit(BubbleTextView bubbleTextView) {
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
                mCurrentEditTextView.setInEdit(false);
                mCurrentEditTextView = bubbleTextView;
                mCurrentEditTextView.setInEdit(true);
            }

            @Override
            public void onClick(BubbleTextView bubbleTextView) {

                mBubbleInputDialog.setBubbleTextView(bubbleTextView);
                mBubbleInputDialog.show();
            }

            @Override
            public void onTop(BubbleTextView bubbleTextView) {
                int position = mViews.indexOf(bubbleTextView);
                if (position == mViews.size() - 1) {
                    return;
                }
                BubbleTextView textView = (BubbleTextView) mViews.remove(position);
                mViews.add(mViews.size(), textView);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        imageWrapView.addView(bubbleTextView, lp);
        mViews.add(bubbleTextView);
        setCurrentEdit(bubbleTextView);
    }

    protected void handleBackButton() {
        this.navigatorBarBackAction(null);
    }

    @Override
    protected void notifyProcessing(TuSdkResult tuSdkResult) {

    }

    @Override
    protected boolean asyncNotifyProcessing(TuSdkResult tuSdkResult) {
        return false;
    }

    public void setDelegate(TextStickerOption.TextStickerDelegate delegate) {
        this.delegate = delegate;
    }



    protected void handleCompleteButton() {
        if(this.mViews.size() == 0) {
            this.handleBackButton();
        } else {

            mCurrentView.setInEdit(false);
            generateBitmap();

            final TuSdkResult result = new TuSdkResult();
//            Rect rect = null;
//            if(this.getCutRegionView() != null) {
//                rect = this.getCutRegionView().getRegionRect();
//            }
//            result.stickers = this.stickerView.getResults(var2);
            result.image = saveViewBitmap (imageWrapView);
            if(result.image!= null) {
                this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
                (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TextStickerFragment.this.asyncEditWithResult(result);
                    }
                })).start();
                if(delegate!=null)delegate.onTextStickerResult(result);
                handleBackButton();
                imageView.setImageBitmap(result.image);
            } else {
                this.handleBackButton();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == cancelButton){
            handleBackButton();
        }else if(v == okButton){
            handleCompleteButton();
        }
    }

    protected void asyncEditWithResult(TuSdkResult result) {
        this.loadOrginImage(result);
//        if(result.stickers != null) {
//            result.image = StickerFactory.megerStickers(result.image, result.stickers);
//            result.stickers = null;
//        }
        this.asyncProcessingIfNeedSave(result);
    }



    private Bitmap saveViewBitmap(View view) {
// get current view bitmap
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = view.getDrawingCache(true);

        Bitmap bmp = duplicateBitmap(bitmap);
        if (bitmap != null && !bitmap.isRecycled()) { bitmap.recycle(); bitmap = null; }
        // clear the cache
        view.setDrawingCacheEnabled(false);
        return bmp;
    }


    public static Bitmap duplicateBitmap(Bitmap bmpSrc)
    {
        if (null == bmpSrc)
        { return null; }

        int bmpSrcWidth = bmpSrc.getWidth();
        int bmpSrcHeight = bmpSrc.getHeight();

        Bitmap bmpDest = Bitmap.createBitmap(bmpSrcWidth, bmpSrcHeight, Bitmap.Config.ARGB_8888);
        if (null != bmpDest) {
            Canvas canvas = new Canvas(bmpDest);
            final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);
            canvas.drawBitmap(bmpSrc, rect, rect, null);
        }
        return bmpDest;
    }

    /**
     * 设置当前处于编辑模式的气泡
     */
    private void setCurrentEdit(BubbleTextView bubbleTextView) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentEditTextView = bubbleTextView;
        mCurrentEditTextView.setInEdit(true);
    }

    private void generateBitmap() {

        Bitmap bitmap = Bitmap.createBitmap(imageWrapView.getWidth(),
                imageWrapView.getHeight()
                , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        imageWrapView.draw(canvas);

        String iamgePath = FileUtils.saveBitmapToLocal(bitmap, getActivity());
//        Intent intent = new Intent(this, DisplayActivity.class);
//        intent.putExtra("image", iamgePath);
//        startActivity(intent);
    }

}