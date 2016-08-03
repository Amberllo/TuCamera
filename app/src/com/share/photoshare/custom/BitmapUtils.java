package com.share.photoshare.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.share.photoshare.R;

/**
 * Created by LYL on 2016/7/27.
 */
public class BitmapUtils {

    public static synchronized Bitmap decodeForWidthHeight(Resources res,int id, int reqWidth, int reqHeight) {

        Bitmap bitmap = BitmapFactory.decodeResource(res, id);
        if(bitmap.getWidth() >= bitmap.getHeight()){
            //长图
            float scale = bitmap.getWidth() <= reqWidth ?  (float)bitmap.getWidth() /  (float)reqWidth : (float)reqWidth / (float)bitmap.getWidth();
            bitmap = resize(bitmap, scale);
        }else{
            //高图
            float scale = bitmap.getHeight() <= reqHeight ?  (float)bitmap.getHeight() /  (float)reqHeight : (float)reqHeight / (float)bitmap.getHeight();
            bitmap = resize(bitmap, scale);
        }


        return bitmap;
    }

    /**
     * Calculate an inSampleSize for use in a {@link BitmapFactory.Options}
     * object when decoding bitmaps using the decode* methods from
     * {@link BitmapFactory}. This implementation calculates the closest
     * inSampleSize that will result in the final decoded bitmap having a width
     * and height equal to or larger than the requested width and height. This
     * implementation does not ensure a power of 2 is returned for inSampleSize
     * which can be faster when decoding but results in a larger bitmap which
     * isn't as useful for caching purposes.
     *
     * @param options
     *            An options object with out* params already populated (run
     *            through a decode* method with inJustDecodeBounds==true
     * @param reqWidth
     *            The requested width of the resulting bitmap
     * @param reqHeight
     *            The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    public static int calculateSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        //先根据宽度进行缩小
        while (width / inSampleSize > reqWidth) {
            inSampleSize++;
        }
        //然后根据高度进行缩小
        while (height / inSampleSize > reqHeight) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    private static Bitmap resize(Bitmap bitmap,float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

    public static Bitmap getBordorBitmap(Context context,Bitmap source){
        if(source==null)return null;

        Bitmap originBitmap = source;
        Bitmap boraderBitmap = BitmapUtils.decodeForWidthHeight(context.getResources(), R.drawable.img_board_default,originBitmap.getWidth(),originBitmap.getHeight());

        int maxWidth = originBitmap.getWidth() > boraderBitmap.getWidth()? originBitmap.getWidth(): boraderBitmap.getWidth();
        int maxHeight =  originBitmap.getHeight() > boraderBitmap.getHeight()? originBitmap.getHeight(): boraderBitmap.getHeight();

        Bitmap composedBitmap = Bitmap.createBitmap(maxWidth, maxHeight , Bitmap.Config.ARGB_8888);

        Canvas cv = new Canvas(composedBitmap);
        if(originBitmap.getWidth() == maxWidth ){
            //计算高度漂移
            cv.drawBitmap(source, 0,  ((float)(maxHeight - originBitmap.getHeight()))/2, null);
            cv.drawBitmap(boraderBitmap, 0,0, null);
        }else{
            //计算宽度漂移
            cv.drawBitmap(source, ((float)(maxWidth - originBitmap.getWidth()))/2 , 0, null);
            cv.drawBitmap(boraderBitmap, 0, 0, null);
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

}
