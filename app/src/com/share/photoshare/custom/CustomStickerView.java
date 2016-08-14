//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.share.photoshare.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.secret.SdkValid;
import org.lasque.tusdk.core.view.TuSdkRelativeLayout;
import org.lasque.tusdk.core.view.TuSdkViewHelper;
import org.lasque.tusdk.impl.components.widget.sticker.StickerItemView;
import org.lasque.tusdk.modules.view.widget.sticker.StickerData;
import org.lasque.tusdk.modules.view.widget.sticker.StickerItemViewInterface;
import org.lasque.tusdk.modules.view.widget.sticker.StickerResult;
import org.lasque.tusdk.modules.view.widget.sticker.StickerItemViewInterface.StickerItemViewDelegate;

public final class CustomStickerView extends TuSdkRelativeLayout implements StickerItemViewDelegate {
    private CustomStickerView.StickerViewDelegate a;
    private int b;
    private List<StickerItemViewInterface> c = new ArrayList();

    public CustomStickerView(Context var1) {
        super(var1);
    }

    public CustomStickerView(Context var1, AttributeSet var2) {
        super(var1, var2);
    }

    public CustomStickerView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    public final CustomStickerView.StickerViewDelegate getDelegate() {
        return this.a;
    }

    public final void setDelegate(CustomStickerView.StickerViewDelegate var1) {
        this.a = var1;
    }

    private int getStickerItemViewResID() {
        if(this.b == 0) {
            this.b = StickerItemView.getLayoutId();
        }

        return this.b;
    }

    private void setStickerItemViewResID(int var1) {
        this.b = var1;
    }

    public final int stickersCount() {
        return this.c.size();
    }

    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.a = null;
    }

    public final void appenSticker(final StickerData var1) {
        boolean var10000;
        if(var1 == null) {
            var10000 = false;
        } else {
            boolean var4 = true;
            if(this.a != null) {
                var4 = this.a.canAppendSticker(this, var1);
            }

            if(!var4) {
                var10000 = false;
            } else if(this.stickersCount() >= SdkValid.shared.maxStickers()) {
                String var3 = TuSdkContext.getString("lsq_sticker_over_limit", new Object[]{Integer.valueOf(SdkValid.shared.maxStickers())});
                TuSdkViewHelper.alert(this.getContext(), (String)null, var3, TuSdkContext.getString("lsq_button_done"));
                var10000 = false;
            } else {
                var10000 = true;
            }
        }

        if(var10000) {
            TuSdk.messageHub().setStatus(this.getContext(), TuSdkContext.getString("lsq_sticker_loading"));
            (new Thread(new Runnable() {
                public void run() {
//                    CustomStickerView.a(CustomStickerView.this, var1);
                }
            })).start();
        }
    }

    public final void onStickerItemViewClose(StickerItemViewInterface var1) {
        if(var1 != null) {
            if(this.c.remove(var1)) {
                this.removeView((View)var1);
            }

        }
    }

    public final void onStickerItemViewSelected(StickerItemViewInterface var1) {
        if(var1 != null) {
            Iterator var3 = this.c.iterator();

            while(var3.hasNext()) {
                StickerItemViewInterface var2;
                (var2 = (StickerItemViewInterface)var3.next()).setSelected(var1.equals(var2));
            }

        }
    }

    public final List<StickerResult> getResults(Rect var1) {
        ArrayList var2 = new ArrayList(this.c.size());
        Iterator var4 = this.c.iterator();

        while(var4.hasNext()) {
            StickerItemViewInterface var3 = (StickerItemViewInterface)var4.next();
            var2.add(var3.getResult(var1));
        }

        return var2;
    }

    public final void cancelAllStickerSelected() {
        Iterator var2 = this.c.iterator();

        while(var2.hasNext()) {
            ((StickerItemViewInterface)var2.next()).setSelected(false);
        }

    }

    @SuppressLint({"ClickableViewAccessibility"})
    public final boolean onTouchEvent(MotionEvent var1) {
        if(var1.getAction() == 0) {
            this.cancelAllStickerSelected();
        }

        return super.onTouchEvent(var1);
    }

    public interface StickerViewDelegate {
        boolean canAppendSticker(CustomStickerView var1, StickerData var2);
    }
}
