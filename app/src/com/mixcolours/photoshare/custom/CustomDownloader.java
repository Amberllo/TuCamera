package com.mixcolours.photoshare.custom;

import android.net.Uri;

import com.squareup.picasso.Downloader;

import java.io.IOException;

/**
 * Created by Amberllo on 2016/10/2.
 */

public class CustomDownloader implements Downloader {
    @Override
    public Response load(Uri uri, int networkPolicy) throws IOException {
        return null;
    }

    @Override
    public void shutdown() {

    }
}
