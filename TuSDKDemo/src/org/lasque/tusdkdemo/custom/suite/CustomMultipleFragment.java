package org.lasque.tusdkdemo.custom.suite;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.lasque.tusdk.core.secret.StatisticsManger;
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
//            Iterator iterator = this.getModules().iterator();
//
//            while(iterator.hasNext()) {
//                TuEditActionType actionType = (TuEditActionType)iterator.next();
                View actionButton;
                if((actionButton = buildActionButton(TuEditActionType.TypeFilter)) != null) {
                    actionButton.setTag(TuEditActionType.TypeFilter);
                    actionButton.setOnClickListener(mButtonClickListener);
                    actionTypeLayout.addView(actionButton);
                }
//            }

//            result.add(TuEditActionType.TypeFilter);
//            result.add(TuEditActionType.TypeSkin);
        }

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
}
