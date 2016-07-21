package org.lasque.tusdkdemo.custom.suite;

import org.lasque.tusdk.impl.activity.TuImageResultOption;
import org.lasque.tusdkdemo.R;

/**
 * Created by apple on 16/7/21.
 */
public class TextStickerOption extends TuImageResultOption {
    @Override
    protected Class<?> getDefaultComponentClazz() {
        return TextStickerFragment.class;
    }

    @Override
    protected int getDefaultRootViewLayoutId() {
        return R.layout.custom_textedit_fragment_layout;
    }

    public TextStickerFragment fragment() {
        TextStickerFragment var1;
        var1 = this.fragmentInstance();
        return var1;
    }

}
