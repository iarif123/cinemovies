package com.aweshams.cinematch.services.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.aweshams.cinematch.services.util.AsyncPromise;
import com.aweshams.cinematch.utils.promises.Promise;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by irteza on 2018-05-24.
 */

public class ImageService implements IImageService {

    @Override
    public Promise<Bitmap> getImage(URL imageUrl) {
        return AsyncPromise
                .executeAsync((URL url) -> {
                    InputStream stream = url.openStream();
                    return BitmapFactory.decodeStream(stream);
                })
                .on(imageUrl);
    }

}
