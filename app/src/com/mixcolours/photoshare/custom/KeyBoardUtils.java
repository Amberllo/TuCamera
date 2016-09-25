package com.mixcolours.photoshare.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mixcolours.photoshare.R;

/**
 * Created by LYL on 2016/7/27.
 */
public class KeyBoardUtils {

    public static synchronized void hide(View view) {
        InputMethodManager m = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        m.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static synchronized void show(View view) {
        InputMethodManager m = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        m.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

}
