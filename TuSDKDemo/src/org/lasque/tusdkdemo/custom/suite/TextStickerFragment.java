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
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.impl.activity.TuImageResultFragment;
import org.lasque.tusdkdemo.R;
import sizeadjusttextstickview.view.StickerTextView;

/**
 * @author Amberllo
 * 文字编辑页面
 */
public class TextStickerFragment extends TuImageResultFragment
{
    TextStickerOption.TextStickerDelegate delegate;
    StickerTextView stickerView;
    EditText edt_input;
    RelativeLayout imageWrapView;
    ImageView imageView;

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

        addStikerTextView();
    }

    @Override
    protected void viewDidLoad(ViewGroup viewGroup) {


        if(getImage()!=null && imageView!=null){
            imageView.setImageBitmap(getImage());
        }
    }


    private void addStikerTextView() {
        stickerView = new StickerTextView(getContext(),true);
        stickerView.setOnStickerTouchListener(onStickerTouchListener);
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
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
//
//    protected void handleCompleteButton() {
//        if(this.getStickerView() == null) {
//            this.handleBackButton();
//        } else {
//            final TuSdkResult result = new TuSdkResult();
//            Rect var2 = null;
//            if(this.getCutRegionView() != null) {
//                var2 = this.getCutRegionView().getRegionRect();
//            }
//
//            result.stickers = this.getStickerView().getResults(var2);
//            if(result.stickers != null && result.stickers.size() != 0) {
//                this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
//                (new Thread(new Runnable() {
//                    public void run() {
//                        TextStickerFragment.this.asyncEditWithResult(var1);
//                    }
//                })).start();
//            } else {
//                this.handleBackButton();
//            }
//        }
//    }

    StickerTextView.OnStickerTouchListener onStickerTouchListener = new StickerTextView.OnStickerTouchListener() {
        @Override
        public void onCopy(StickerTextView stickerView) {

        }

        @Override
        public void onDelete(StickerTextView stickerView) {
            edt_input.setVisibility(View.GONE);
        }

        @Override
        public void onMoveToHead(StickerTextView stickerView) {

        }

        @Override
        public void onDoubleClick(StickerTextView stickerView) {
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
}