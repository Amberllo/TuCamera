package com.mixcolours.photoshare.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.mixcolours.photoshare.R;

/**
 * Created by Amberllo on 16/12/22.
 */
public class CustomIndicatorView extends View{

    private int color_select = R.color.green_common;
    private int color_normal= R.color.gray_common2;
    private int color = color_normal;

    public CustomIndicatorView(Context context){
        super(context);
    }

    public CustomIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSelect(boolean isSelect){
        this.color = isSelect?color_select:color_normal;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(color));
        paint.setAntiAlias(true);
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/4,paint);
    }


}
