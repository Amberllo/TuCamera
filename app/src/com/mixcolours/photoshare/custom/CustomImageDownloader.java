package com.mixcolours.photoshare.custom;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by Amberllo on 2016/10/2.
 */

public class CustomImageDownloader extends BaseImageDownloader{
    public CustomImageDownloader(Context context) {
        super(context,5000,30*1000);
    }

    @Override
    public InputStream getStream(String imageUri, Object extra) throws IOException {
        if(CustomScheme.ofUri(imageUri) == CustomScheme.Sticker){
            return getStreamFromSticker(imageUri, extra);
        }else{
            return super.getStream(imageUri, extra);
        }

    }

    private InputStream getStreamFromSticker(String imageUri, Object extra) throws IOException {
        String stickerId = CustomScheme.Sticker.crop(imageUri);
        InputStream inputStream = context.getAssets().open("Borader/"+stickerId+".png");
        return inputStream;
    }


    public static enum CustomScheme {
        Sticker("sticker");


        private String scheme;
        private String uriPrefix;

        private CustomScheme(String scheme) {
            this.scheme = scheme;
            this.uriPrefix = scheme + "://";
        }

        public static CustomScheme ofUri(String uri) {
            if(uri != null) {
                CustomScheme[] var4;
                int var3 = (var4 = values()).length;

                for(int var2 = 0; var2 < var3; ++var2) {
                    CustomScheme s = var4[var2];
                    if(s.belongsTo(uri)) {
                        return s;
                    }
                }
            }

            return Sticker;
        }

        private boolean belongsTo(String uri) {
            return uri.toLowerCase(Locale.US).startsWith(this.uriPrefix);
        }

        public String wrap(String path) {
            return this.uriPrefix + path;
        }

        public String crop(String uri) {
            if(!this.belongsTo(uri)) {
                throw new IllegalArgumentException(String.format("URI [%1$s] doesn\'t have expected scheme [%2$s]", new Object[]{uri, this.scheme}));
            } else {
                return uri.substring(this.uriPrefix.length());
            }
        }
    }
}
