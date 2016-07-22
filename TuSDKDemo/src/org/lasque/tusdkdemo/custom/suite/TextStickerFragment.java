/** 
 * TuSdkDemo
 * DemoEntryActivity.java
 *
 * @author 		Clear
 * @Date 		2014-11-15 下午4:30:52 
 * @Copyright 	(c) 2014 tusdk.com. All rights reserved.
 * @link 		开发文档:http://tusdk.com/docs/android/api/
 */
package org.lasque.tusdkdemo.custom.suite;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.view.widget.button.TuSdkImageButton;
import org.lasque.tusdk.impl.activity.TuImageResultFragment;

import org.lasque.tusdk.modules.view.widget.sticker.StickerFactory;
import org.lasque.tusdkdemo.R;

import cn.rosen.sizeadjusttextstickview.view.StickerView;

/**
 * @author Amberllo
 * 文字编辑页面
 */
public class TextStickerFragment extends TuImageResultFragment implements View.OnClickListener

{
    TextStickerOption.TextStickerDelegate delegate;
    StickerView stickerView;
    EditText edt_input;
    RelativeLayout imageWrapView;
    ImageView imageView;
    TuSdkImageButton cancelButton;
    TuSdkImageButton okButton;

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
        edt_input = (EditText)viewGroup.findViewById(R.id.lsq_input);
        edt_input.addTextChangedListener(textWatcher);
        cancelButton = (TuSdkImageButton)viewGroup.findViewById(R.id.lsq_configCancelButton);
        cancelButton.setOnClickListener(this);
        okButton = (TuSdkImageButton)viewGroup.findViewById(R.id.lsq_configCompleteButton);
        okButton.setOnClickListener(this);
        addStikerTextView();
    }

    @Override
    protected void viewDidLoad(ViewGroup viewGroup) {


        if(getImage()!=null && imageView!=null){
            imageView.setImageBitmap(getImage());
        }
    }


    private void addStikerTextView() {
        stickerView = new StickerView(getContext(),true);
        stickerView.setOnStickerTouchListener(onStickerTouchListener);
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        rl.addRule(RelativeLayout.CENTER_IN_PARENT);

        imageWrapView.addView(stickerView,rl);
        imageWrapView.addView(stickerView.getSizeTextView());

        Bitmap bitmap  = BitmapFactory.decodeResource(getResources(),R.mipmap.txt_button_0);
        stickerView.setTextDraw(bitmap,28,29.5f,118,85);
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

//    public final void appendStickerItem(StickerData var1) {
//        if(var1 != null && this.getStickerView() != null) {
//            this.getStickerView().appenSticker(var1);
//        }
//    }

    protected void handleCompleteButton() {
        if(this.stickerView  == null) {
            this.handleBackButton();
        } else {
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

    StickerView.OnStickerTouchListener onStickerTouchListener = new StickerView.OnStickerTouchListener() {
        @Override
        public void onCopy(StickerView stickerView) {

        }

        @Override
        public void onDelete(StickerView stickerView) {
            edt_input.setVisibility(View.GONE);
        }

        @Override
        public void onMoveToHead(StickerView stickerView) {

        }

        @Override
        public void onDoubleClick(StickerView stickerView) {
            edt_input.setVisibility(View.VISIBLE);
        }
    };


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            stickerView.resetText(s.toString());
        }
    };

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

}