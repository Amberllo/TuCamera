package com.mixcolours.photoshare.custom.ui;

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
import org.lasque.tusdk.core.view.widget.button.TuSdkTextButton;
import org.lasque.tusdk.impl.components.edit.TuEditMultipleFragment;
import org.lasque.tusdk.modules.components.ComponentActType;
import org.lasque.tusdk.modules.components.edit.TuEditActionType;

import com.mixcolours.photoshare.R;
import java.io.File;


/**
 * Created by Amberllo on 2016/7/19.
 */
public class EditMultipleFragment extends TuEditMultipleFragment {

    TuSdkImageView filterButton;
    TuSdkTextButton skinButton;
    TuSdkImageView shareButton;
    TuSdkImageView fontButton;
    TuSdkImageView stickerButton;

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
        skinButton = (TuSdkTextButton)actionTypeLayout2.findViewById(R.id.lsq_skinButton);
        shareButton = (TuSdkImageView) actionTypeLayout2.findViewById(R.id.lsq_shareButton);
        fontButton = (TuSdkImageView)actionTypeLayout2.findViewById(R.id.lsq_fontButton);
        stickerButton = (TuSdkImageView)actionTypeLayout2.findViewById(R.id.lsq_stickerButton);


        filterButton.setOnClickListener(filterOnClickListener);
        skinButton.setOnClickListener(skinOnClickListener);
        shareButton.setOnClickListener(shareOnClickListener);
        fontButton.setOnClickListener(fontOnClickListener);
        stickerButton.setOnClickListener(stickerOnClickListener);
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

    @Override
    protected void handleCompleteButton() {
        System.out.println("handleCompleteButton");
        super.handleCompleteButton();
    }

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
            String file = FileUtils.saveShareBitmapToLocal(bitmap,getActivity());
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


    private Bitmap shareOnComplete(){
        final TuSdkResult tuSdkResult;
        (tuSdkResult = new TuSdkResult()).imageFile = this.getLastSteps();
        if(tuSdkResult.imageFile != null && tuSdkResult.imageFile.exists() && tuSdkResult.imageFile.isFile()) {
            return BitmapHelper.getBitmap(tuSdkResult.imageFile, true);
        }
        return null;
    }

    @Override
    protected void backUIThreadNotifyProcessing(TuSdkResult tuSdkResult) {
        super.backUIThreadNotifyProcessing(tuSdkResult);
    }
}
