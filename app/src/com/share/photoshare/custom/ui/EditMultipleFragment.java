package com.share.photoshare.custom.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.abner.stickerdemo.utils.FileUtils;
import com.share.photoshare.custom.suite.CuterComponent;
import com.share.photoshare.custom.suite.FilterComponent;
import com.share.photoshare.custom.suite.SkinComponent;
import com.share.photoshare.custom.suite.SkinComponentOption;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;
import com.umeng.soexample.model.Defaultcontent;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.secret.StatisticsManger;
import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdk.core.utils.image.BitmapHelper;
import org.lasque.tusdk.core.view.TuSdkImageView;
import org.lasque.tusdk.core.view.TuSdkViewHelper;
import org.lasque.tusdk.core.view.widget.button.TuSdkTextButton;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.edit.TuEditMultipleFragment;
import org.lasque.tusdk.impl.components.filter.TuEditSkinFragment;
import org.lasque.tusdk.modules.components.ComponentActType;
import org.lasque.tusdk.modules.components.TuSdkComponent;
import org.lasque.tusdk.modules.components.edit.TuEditActionType;
import com.share.photoshare.R;
import com.share.photoshare.custom.BitmapUtils;
import com.share.photoshare.custom.suite.StickerComponent;
import com.share.photoshare.custom.suite.StickerComponentOption;
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
//        onSticker(true);
//        handleAction(TuEditActionType.TypeCuter);
    }



    View.OnClickListener fontOnClickListener = new TuSdkViewHelper.OnSafeClickListener(){

        @Override
        public void onSafeClick(View view) {

            TextStickerComponent component = new TextStickerComponent(getActivity());
            TextStickerComponentOption option = new TextStickerComponentOption();
            option.setDelegate(new TextStickerComponentOption.TextStickerDelegate() {
                @Override
                public void onTextStickerResult(final TuSdkResult result) {
                    setResult(result);
                }
            });
            component.setOption(option)
                    .setImage(getImage())
                    // 设置系统照片
                    .setImageSqlInfo(getImageSqlInfo())
                    // 设置临时文件
                    .setTempFilePath(getTempFilePath())
                    // 在组件执行完成后自动关闭组件
                    .setAutoDismissWhenCompleted(true)
                    // 开启组件
                    .showComponent();

        }
    };

    View.OnClickListener stickerOnClickListener = new TuSdkViewHelper.OnSafeClickListener(){

        @Override
        public void onSafeClick(View view) {
            onSticker(false);
        }
    };

    View.OnClickListener filterOnClickListener = new TuSdkViewHelper.OnSafeClickListener(){

        @Override
        public void onSafeClick(View view) {

            FilterComponent component = new FilterComponent(getActivity()) {
                @Override
                public void onTuEditFilterResult(TuSdkResult result) {
                    setResult(result);
                }
            };
            component.setImage(getImage())
                    // 设置系统照片
                    .setImageSqlInfo(getImageSqlInfo())
                    // 设置临时文件
                    .setTempFilePath(getTempFilePath())
                    // 在组件执行完成后自动关闭组件
                    .setAutoDismissWhenCompleted(true)
                    // 开启组件
                    .showComponent();

        }
    };


    View.OnClickListener skinOnClickListener = new TuSdkViewHelper.OnSafeClickListener(){

        @Override
        public void onSafeClick(View view) {
            SkinComponent component = new SkinComponent(getActivity()) {
                @Override
                public void onTuEditSkinResult(TuSdkResult result) {
                    setResult(result);
                }
            };
            SkinComponentOption option = new SkinComponentOption();
            component.setOption(option)
                    .setImage(getImage())
                    // 设置系统照片
                    .setImageSqlInfo(getImageSqlInfo())
                    // 设置临时文件
                    .setTempFilePath(getTempFilePath())
                    // 在组件执行完成后自动关闭组件
                    .setAutoDismissWhenCompleted(true)
                    // 开启组件
                    .showComponent();
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
        CuterComponent component = new CuterComponent(getActivity()) {
            @Override
            public void onTuEditCuterResult(TuSdkResult result) {
                setResult(result);
            }
        };
        component.setImage(getImage())
                // 设置系统照片
                .setImageSqlInfo(getImageSqlInfo())
                // 设置临时文件
                .setTempFilePath(getTempFilePath())
                // 在组件执行完成后自动关闭组件
                .setAutoDismissWhenCompleted(true)
                // 开启组件
                .showComponent();
    }

    private void onSticker(boolean autoBorder){
        StickerComponent component = new StickerComponent(getActivity()) {
            @Override
            public void onTuEditStickerResult(TuSdkResult result) {
                setResult(result);
            }
        };
        StickerComponentOption option = new StickerComponentOption();
        component.setAutoBorder(autoBorder);
        component.setOption(option)
                .setImage(getImage())
                // 设置系统照片
                .setImageSqlInfo(getImageSqlInfo())
                // 设置临时文件
                .setTempFilePath(getTempFilePath())
                // 在组件执行完成后自动关闭组件
                .setAutoDismissWhenCompleted(true)
                // 开启组件
                .showComponent();
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

    private void setResult(final TuSdkResult result){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setImage(result.image);
                setTempFilePath(result.imageFile);
                setDisplayImage(result.image);
                appendHistory(result.imageFile);
            }
        });

    }

}
