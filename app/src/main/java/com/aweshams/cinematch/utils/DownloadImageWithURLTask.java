package com.aweshams.cinematch.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by irteza on 2018-02-19.
 */

public class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {

    ImageView imageView;

    public DownloadImageWithURLTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String[] urls) {
        URL url = null;
        Bitmap bmp = null;
        try {
            url = new URL(urls[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
