package com.mixcolours.photoshare.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import com.mixcolours.photoshare.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LYL on 2016/7/27.
 */
public class BitmapUtils {

    public static Bitmap resize(Bitmap bitmap,float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

    public static Bitmap combineBoraderBitmap(Bitmap source,Bitmap boraderBitmap){
        if(source==null || boraderBitmap==null)return null;
        int maxWidth = boraderBitmap.getWidth();
        int maxHeight = boraderBitmap.getHeight();

        //画布
        Bitmap composedBitmap = Bitmap.createBitmap(maxWidth, maxHeight, Bitmap.Config.ARGB_8888);
        Bitmap resizeBitmap = cropBitmapCenter(source,maxWidth,maxHeight);
        Canvas cv = new Canvas(composedBitmap);
        cv.drawBitmap(resizeBitmap, 0 , 0, null);
        cv.drawBitmap(boraderBitmap, 0, 0, null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return composedBitmap;
    }

    public static Bitmap cropBitmapCenter(Bitmap source,int maxWidth,int maxHeight){

        float rateBorader = (float)maxWidth / (float)maxHeight;

        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        float rateSource = (float)sourceWidth / (float)sourceHeight;
        //画布
        Bitmap composedBitmap = Bitmap.createBitmap(maxWidth, maxHeight, Bitmap.Config.ARGB_8888);
        float scale = 1.0f;
        if(rateSource > rateBorader ){
            //原图宽高比大,y方向拉伸,
            scale = ((float)maxHeight) / ((float)source.getHeight());
        }else if(rateSource < rateBorader){
            scale = ((float)maxWidth)/((float)source.getWidth());
        }

        Bitmap resizeBitmap = resize(source,scale);
        Canvas cv = new Canvas(composedBitmap);
        if(resizeBitmap.getWidth() == maxWidth ){
            //计算高度漂移
            cv.drawBitmap(resizeBitmap, 0,  ((float)(maxHeight - resizeBitmap.getHeight()))/2, null);
        }else{
            //计算宽度漂移
            cv.drawBitmap(resizeBitmap, ((float)(maxWidth - resizeBitmap.getWidth()))/2 , 0, null);
        }
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return composedBitmap;
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static Bitmap getStickerFromAccess(Context context,long stickerId){
        try {
            InputStream inputStream = context.getAssets().open("Borader/"+stickerId+".png");
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static Bitmap blurImage(Bitmap originBitmap){
        int scale = 2;

        long start = System.currentTimeMillis();
        int scaleRatio = 10;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originBitmap,
                originBitmap.getWidth() / scaleRatio,
                originBitmap.getHeight() / scaleRatio,
                false);
        Bitmap blurBitmap = FastBlurUtil.doBlur(scaledBitmap, scale, true);
        Log.i("blurtime"," scale = "+scale +" " + String.valueOf(System.currentTimeMillis() - start));
        return blurBitmap ;
    }
}
