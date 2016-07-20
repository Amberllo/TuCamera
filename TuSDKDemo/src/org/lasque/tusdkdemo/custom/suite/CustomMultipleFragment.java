package org.lasque.tusdkdemo.custom.suite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
        for(int i=0;i<actionTypeLayout2.getChildCount();i++){
            View child = actionTypeLayout2.getChildAt(i);
            if(child instanceof TuSdkTextButton){
//                child = buildActionButton()
            }
//            Iterator var4 = this.getModules().iterator();
//
//            while(var4.hasNext()) {
//                TuEditActionType var3 = (TuEditActionType)var4.next();
//                View var5;
//                if((var5 = var6.buildActionButton(var3)) != null) {
//                    var5.setTag(var3);
//                    var5.setOnClickListener(var6.mButtonClickListener);
//                    var2.addView(var5);
//                }
//            }
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
