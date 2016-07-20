package org.lasque.tusdkdemo.custom.suite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.lasque.tusdk.core.secret.StatisticsManger;
import org.lasque.tusdk.core.view.widget.button.TuSdkTextButton;
import org.lasque.tusdk.impl.components.edit.TuEditMultipleFragment;
import org.lasque.tusdk.modules.components.ComponentActType;
import org.lasque.tusdk.modules.components.edit.TuEditActionType;
import org.lasque.tusdkdemo.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LYL on 2016/7/19.
 */
public class CustomMultipleFragment extends TuEditMultipleFragment {


    @Override
    protected void loadView(ViewGroup view)
    {
        super.loadView(view);
        StatisticsManger.appendComponent(ComponentActType.editMultipleFragment);
        // 在这里使用 getViewById()方法找到新添加的视图

        this.getImageView();
        this.showView(this.getStepwrap(), !this.isDisableStepsSave());
        this.getStepPrevButton();
        this.getStepNextButton();
        this.getAutoAdjustButton();
        this.getCancelButton();
        this.getDoneButton();

        LinearLayout actionTypeLayout;
        if((actionTypeLayout = this.getActionsWrap()) != null) {
            actionTypeLayout.removeAllViews();
            actionTypeLayout.setVisibility(View.GONE);
        }

        LinearLayout actionTypeLayout2 = (LinearLayout)view.findViewById(R.id.lsq_actions_wrapview2);

        TuSdkTextButton filterButton = (TuSdkTextButton)actionTypeLayout2.findViewById(R.id.lsq_filterButton);
        TuSdkTextButton skinButton = (TuSdkTextButton)actionTypeLayout2.findViewById(R.id.lsq_skinButton);
        ImageView shareButton = (ImageView) actionTypeLayout2.findViewById(R.id.lsq_shareButton);
        TuSdkTextButton fontButton = (TuSdkTextButton)actionTypeLayout2.findViewById(R.id.lsq_fontButton);
        TuSdkTextButton stickerButton = (TuSdkTextButton)actionTypeLayout2.findViewById(R.id.lsq_stickerButton);


        filterButton.setTag(TuEditActionType.TypeFilter);
        filterButton.setOnClickListener(mButtonClickListener);

        skinButton.setTag(TuEditActionType.TypeSkin);
        skinButton.setOnClickListener(mButtonClickListener);

        shareButton.setOnClickListener(shareOnClickListener);

        fontButton.setOnClickListener(fontOnClickListener);

        stickerButton.setTag(TuEditActionType.TypeSticker);
        stickerButton.setOnClickListener(mButtonClickListener);

        this.refreshStepStates();

    }

    @Override
    protected View buildActionButton(TuEditActionType tuEditActionType) {
        return super.buildActionButton(tuEditActionType);
    }

    @Override
    public LinearLayout getActionsWrap() {
        return super.getActionsWrap();
    }

    @Override
    public List<TuEditActionType> getModules() {
        List<TuEditActionType> result = new ArrayList<>();

        result.add(TuEditActionType.TypeFilter);
        result.add(TuEditActionType.TypeSkin);
        return result;
    }

    View.OnClickListener fontOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(),"文字编辑!",Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener shareOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(),"分享功能!",Toast.LENGTH_SHORT).show();
        }
    };

}
