package com.share.photoshare.custom.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.abner.stickerdemo.utils.FileUtils;

import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.secret.StatisticsManger;
import org.lasque.tusdk.core.utils.image.BitmapHelper;
import org.lasque.tusdk.core.view.TuSdkImageView;
import org.lasque.tusdk.core.view.TuSdkViewHelper;
import org.lasque.tusdk.impl.components.edit.TuEditMultipleFragment;
import org.lasque.tusdk.modules.components.ComponentActType;
import org.lasque.tusdk.modules.components.edit.TuEditActionType;

import com.share.photoshare.R;
import com.share.photoshare.custom.BitmapUtils;
import com.share.photoshare.custom.suite.TextStickerComponent;
import com.share.photoshare.custom.suite.TextStickerComponentOption;

import java.io.File;


/**
 * Created by Amberllo on 2016/7/19.
 */
public class EditMultipleFragment extends TuEditMultipleFragment {

    TuSdkImageView filterButton;
    TuSdkImageView skinButton;
    TuSdkImageView shareButton;
    TuSdkImageView fontButton;
    TuSdkImageView stickerButton;
    TuSdkImageView saveButton;
    @Override
    protected void loadView(ViewGroup view)
    {

        StatisticsManger.appendComponent(ComponentActType.editMultipleFragment);
        // 在这里使用 getViewById()方法找到新添加的视图

        this.getImageView();
        this.showView(this.getStepwrap(), !this.isDisableStepsSave());
        this.getStepPrevButton();
        this.getStepNextButton();
        this.getAutoAdjustButton().setVisibility(View.GONE);
        this.getCancelButton();
        this.getDoneButton();

        getStepwrap().setVisibility(View.GONE);

        LinearLayout actionTypeLayout;
        if((actionTypeLayout = this.getActionsWrap()) != null) {
            actionTypeLayout.removeAllViews();
            actionTypeLayout.setVisibility(View.GONE);
        }

        LinearLayout actionTypeLayout2 = (LinearLayout)view.findViewById(R.id.lsq_actions_wrapview2);

        filterButton = (TuSdkImageView)actionTypeLayout2.findViewById(R.id.lsq_filterButton);
        skinButton = (TuSdkImageView)actionTypeLayout2.findViewById(R.id.lsq_skinButton);
        shareButton = (TuSdkImageView) actionTypeLayout2.findViewById(R.id.lsq_shareButton);
        fontButton = (TuSdkImageView)actionTypeLayout2.findViewById(R.id.lsq_fontButton);
        stickerButton = (TuSdkImageView)actionTypeLayout2.findViewById(R.id.lsq_stickerButton);
        saveButton = (TuSdkImageView)actionTypeLayout2.findViewById(R.id.lsq_saveButton);


        filterButton.setOnClickListener(filterOnClickListener);
        skinButton.setOnClickListener(skinOnClickListener);
        shareButton.setOnClickListener(shareOnClickListener);
        fontButton.setOnClickListener(fontOnClickListener);
        stickerButton.setOnClickListener(stickerOnClickListener);
        saveButton.setOnClickListener(saveOnClickListener);
        this.refreshStepStates();
    }

    @Override
    protected void viewDidLoad(ViewGroup viewGroup) {
        super.viewDidLoad(viewGroup);
        onCuter();
    }



    View.OnClickListener fontOnClickListener = new TuSdkViewHelper.OnSafeClickListener(){

        @Override
        public void onSafeClick(View view) {
            handleAction(TuEditActionType.TypeUnknow);
        }
    };


    View.OnClickListener stickerOnClickListener = new TuSdkViewHelper.OnSafeClickListener(){

        @Override
        public void onSafeClick(View view) {
            onSticker();
        }
    };

    View.OnClickListener filterOnClickListener = new TuSdkViewHelper.OnSafeClickListener(){

        @Override
        public void onSafeClick(View view) {

            handleAction(TuEditActionType.TypeFilter);

        }
    };


    View.OnClickListener skinOnClickListener = new TuSdkViewHelper.OnSafeClickListener(){

        @Override
        public void onSafeClick(View view) {
            handleAction(TuEditActionType.TypeSkin);
        }
    };



    View.OnClickListener saveOnClickListener = new TuSdkViewHelper.OnSafeClickListener(){

        @Override
        public void onSafeClick(View view) {
            handleCompleteButton();
        }
    };



    View.OnClickListener shareOnClickListener = new TuSdkViewHelper.OnSafeClickListener(){

        @Override
        public void onSafeClick(View view) {
            shareSingleImage();

        }
    };


    //分享单张图片
    public void shareSingleImage() {
        Bitmap bitmap = shareOnComplete();
        if(bitmap!=null){
            String file = FileUtils.saveShareBitmapToLocal(bitmap,getContext());
            Uri imageUri = Uri.fromFile(new File(file));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.setType("image/*");
            startActivity(Intent.createChooser(shareIntent, "分享到"));
        }

    }

    private void onCuter(){
        handleAction(TuEditActionType.TypeCuter);

    }

    private void onSticker(){
        handleAction(TuEditActionType.TypeSticker);
    }

    public void setDefaultBorder(){

        Bitmap composedBitmap = BitmapUtils.getBordorBitmap(getContext(),getImage());
        String filepath = FileUtils.saveBitmapToLocal(composedBitmap,getContext());
        setTempFilePath(new File(filepath));
        setDisplayImage(composedBitmap);
        appendHistory(new File(filepath));
    }

    private Bitmap shareOnComplete(){
        final TuSdkResult tuSdkResult;
        (tuSdkResult = new TuSdkResult()).imageFile = this.getLastSteps();
        if(tuSdkResult.imageFile != null && tuSdkResult.imageFile.exists() && tuSdkResult.imageFile.isFile()) {
            return BitmapHelper.getBitmap(tuSdkResult.imageFile, true);
        }
        return null;
    }



}
