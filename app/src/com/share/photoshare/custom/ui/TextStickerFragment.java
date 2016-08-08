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

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.abner.stickerdemo.utils.FileUtils;
import com.example.abner.stickerdemo.view.TextStickerInputDialog;
import com.example.abner.stickerdemo.view.TextStickerView;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.view.widget.button.TuSdkImageButton;
import org.lasque.tusdk.impl.activity.TuImageResultFragment;
import com.share.photoshare.R;
import com.share.photoshare.custom.CustomColorImageView;
import com.share.photoshare.custom.suite.TextStickerComponentOption;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Amberllo
 * 文字编辑页面
 */
public class TextStickerFragment extends TuImageResultFragment implements View.OnClickListener
{
    TextStickerComponentOption.TextStickerDelegate delegate;
    //气泡输入框
    TextStickerInputDialog mTextStickerInputDialog;
    RelativeLayout imageWrapView;
    ImageView imageView;
    TuSdkImageButton cancelButton;
    TuSdkImageButton okButton;
    CustomColorImageView colorButton;
    TuSdkImageButton lsq_fontButton;
    //存储贴纸列表
    ArrayList<View> mViews = new ArrayList<>();

    //当前处于编辑状态的气泡
    TextStickerView mCurrentEditTextView;

    View bottomBar;

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
        bottomBar = viewGroup.findViewById(R.id.lsq_config_bottomBar);
        imageView = (ImageView)viewGroup.findViewById(R.id.lsq_imageView);
        imageWrapView = (RelativeLayout)viewGroup.findViewById(R.id.lsq_imageWrapView);

        cancelButton = (TuSdkImageButton)viewGroup.findViewById(R.id.lsq_configCancelButton);
        cancelButton.setOnClickListener(this);
        okButton = (TuSdkImageButton)viewGroup.findViewById(R.id.lsq_configCompleteButton);
        okButton.setOnClickListener(this);

        lsq_fontButton = (TuSdkImageButton)viewGroup.findViewById(R.id.lsq_fontButton);
        lsq_fontButton.setOnClickListener(this);

        colorButton = (CustomColorImageView) viewGroup.findViewById(R.id.lsq_colorButton);
        colorButton.setOnClickListener(this);

        mTextStickerInputDialog = new TextStickerInputDialog(getContext());
        mTextStickerInputDialog.setCompleteCallBack(new TextStickerInputDialog.CompleteCallBack() {
            @Override
            public void onComplete(View bubbleTextView, String str) {
                ((TextStickerView) bubbleTextView).setFontText(str);
            }
        });

    }

    @Override
    protected void viewDidLoad(ViewGroup viewGroup) {

        Bitmap bitmap = getImage();



        if(getImage()!=null && imageView!=null){
            imageView.setImageBitmap(bitmap);

            if(bitmap.getWidth()<bitmap.getHeight()){
                int resizeWidth = imageView.getHeight() * bitmap.getWidth() / bitmap.getHeight();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                params.width = resizeWidth ;

                RelativeLayout.LayoutParams paramsLayout = (RelativeLayout.LayoutParams) imageWrapView.getLayoutParams();
                paramsLayout.width = resizeWidth;

                imageView.setLayoutParams(params);
                imageWrapView.setLayoutParams(paramsLayout);

            }else{
                int resizeHeight = imageView.getWidth() * bitmap.getHeight() / bitmap.getWidth();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                params.height= resizeHeight;

                RelativeLayout.LayoutParams paramsLayout = (RelativeLayout.LayoutParams) imageWrapView.getLayoutParams();
                paramsLayout.height = resizeHeight;

                imageView.setLayoutParams(params);
                imageWrapView.setLayoutParams(paramsLayout);
            }

        }
    }


    //添加气泡
    private void addTextSticker() {

        final TextStickerView textStickerView = new TextStickerView(getContext());
        textStickerView.setOperationListener(new TextStickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(textStickerView);
                imageWrapView.removeView(textStickerView);

            }

            @Override
            public void onEdit(TextStickerView stickerView) {
                setCurrentEdit(stickerView);
            }

            @Override
            public void onClick(TextStickerView stickerView) {
                onStickerClick(stickerView);
            }

            @Override
            public void onTop(TextStickerView stickerView) {
                int position = mViews.indexOf(stickerView);
                if (position == mViews.size() - 1) {
                    return;
                }
                TextStickerView textView = (TextStickerView) mViews.remove(position);
                mViews.add(mViews.size(), textView);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        imageWrapView.addView(textStickerView, lp);
        mViews.add(textStickerView);
        setCurrentEdit(textStickerView);
        onStickerClick(textStickerView);
    }

    private void onStickerClick(final TextStickerView stickerView){
        mTextStickerInputDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                bottomBar.setVisibility(View.INVISIBLE);
            }
        });

        mTextStickerInputDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomBar.setVisibility(View.VISIBLE);
            }
        });

        mTextStickerInputDialog.removeOnTextChangeCallback();

        colorButton.setColor(stickerView.getFontColor());
        mTextStickerInputDialog.setTextStickerView(stickerView);
        mTextStickerInputDialog.show();
        mTextStickerInputDialog.setOnTextChangeCallback(new TextStickerInputDialog.OnTextChangeCallback() {
            @Override
            public void onText(String text) {
                stickerView.setFontText(text);
            }
        });
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

    public void setDelegate(TextStickerComponentOption.TextStickerDelegate delegate) {
        this.delegate = delegate;
    }



    protected void handleCompleteButton() {
        if(mCurrentEditTextView!=null)mCurrentEditTextView.setInEdit(false);
        if(this.mViews.size() == 0) {
            this.handleBackButton();
        } else {

            final TuSdkResult result = generateBitmap();

            if(result.image!= null) {
//                this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
                (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TextStickerFragment.this.asyncEditWithResult(result);
                    }
                })).start();
                if(delegate!=null)delegate.onTextStickerResult(result);
                handleBackButton();

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
        }else if(v == colorButton){
            showColorPicker();
        }else if(v == lsq_fontButton){
            addTextSticker();
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


    /**
     * 设置当前处于编辑模式的气泡
     */
    private void setCurrentEdit(TextStickerView stickerView) {
        colorButton.setColor(stickerView.getFontColor());
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentEditTextView = stickerView;
        mCurrentEditTextView.setInEdit(true);
    }

    private TuSdkResult generateBitmap() {

        Bitmap bitmap = Bitmap.createBitmap(imageWrapView.getWidth(),
                imageWrapView.getHeight()
                , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        imageWrapView.draw(canvas);

        String imagePath = FileUtils.saveBitmapToLocal(bitmap, getActivity());
        TuSdkResult result = new TuSdkResult();

        result.image = bitmap;
        result.imageFile = new File(imagePath);
        return result;

    }

    private void showColorPicker(){
        ColorPickerDialogBuilder
                .with(getActivity())
                .setTitle(getResString(R.string.color_lib_title))
                .initialColor(mCurrentEditTextView.getFontColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                        toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
//                        mCurrentEditTextView.setFontColor(selectedColor);
                    }
                })
                .setPositiveButton(getResString(R.string.color_lib_ok), new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {

                        colorButton.setColor(selectedColor);
                        mCurrentEditTextView.setFontColor(selectedColor);
                    }
                })
                .setNegativeButton(getResString(R.string.color_lib_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }



}