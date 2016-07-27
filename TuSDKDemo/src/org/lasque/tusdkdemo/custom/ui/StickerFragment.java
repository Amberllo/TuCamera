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

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.abner.stickerdemo.utils.FileUtils;
import com.example.abner.stickerdemo.view.BubbleInputDialog;
import com.example.abner.stickerdemo.view.BubbleTextView;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.secret.SdkValid;
import org.lasque.tusdk.core.secret.StatisticsManger;
import org.lasque.tusdk.core.view.widget.button.TuSdkImageButton;
import org.lasque.tusdk.core.view.widget.button.TuSdkTextButton;
import org.lasque.tusdk.impl.activity.TuImageResultFragment;
import org.lasque.tusdk.impl.components.sticker.TuEditStickerFragment;
import org.lasque.tusdk.impl.components.widget.sticker.StickerBarView;
import org.lasque.tusdk.modules.components.ComponentActType;
import org.lasque.tusdk.modules.view.widget.sticker.StickerCategory;
import org.lasque.tusdk.modules.view.widget.sticker.StickerLocalPackage;
import org.lasque.tusdkdemo.R;
import org.lasque.tusdkdemo.custom.CustomStickerBarView;
import org.lasque.tusdkdemo.custom.suite.TextStickerComponentOption;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amberllo
 * 帖子编辑页面
 */
public class StickerFragment extends TuEditStickerFragment
{

    public static int getLayoutViewId(){
        return R.layout.custom_sticker_fragment_layout;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.setRootViewLayoutId(getLayoutViewId());
        return !SdkValid.shared.sdkValid()?null:super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    protected void loadView(ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.editStickerFragment);
        this.getImageView();
        this.getStickerView();
        this.getCutRegionView();
        this.getCancelButton();
        this.getCompleteButton();
        this.getListButton().setVisibility(View.GONE);
        this.getOnlineButton();
        if(this.getStickerBarView() != null) {
            this.getStickerBarView().loadCategories(this.getCategories());
        }


//        CustomStickerBarView stickerBarView = (CustomStickerBarView)viewGroup.findViewById(R.id.lsq_sticker_bar);
//        if(stickerBarView != null) {
//            stickerBarView.setGridLayoutId(this.getGridLayoutId());
//            stickerBarView.setGridWidth(this.getGridWidth());
//            stickerBarView.setGridPadding(this.getGridPadding());
//            stickerBarView.setGridHeight(this.getGridHeight());
//            stickerBarView.setDelegate(this);
//            stickerBarView.loadCategories(this.getCategories());
//        }

    }




}