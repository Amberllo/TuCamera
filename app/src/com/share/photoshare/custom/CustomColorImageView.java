package com.share.photoshare.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by apple on 16/8/2.
 */
public class CustomColorImageView extends ImageView{

    private int color = Color.WHITE;

    public CustomColorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setColor(int color){
        this.color = color;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();



        float radius = BitmapUtils.dip2px(getContext(),15);
        float x = width/2;
        float y = height/2;

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        canvas.drawCircle(x,y,radius,paint);
    }
}
