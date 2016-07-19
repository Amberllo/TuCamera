package org.lasque.tusdkdemo.custom.suite;

import android.view.ViewGroup;

import org.lasque.tusdk.impl.components.edit.TuEditMultipleFragment;
import org.lasque.tusdkdemo.R;

/**
 * Created by LYL on 2016/7/19.
 */
public class CustomMultipleFragment extends TuEditMultipleFragment {


    @Override
    protected void loadView(ViewGroup view)
    {
        super.loadView(view);
        // 在这里使用 getViewById()方法找到新添加的视图

    }
}
