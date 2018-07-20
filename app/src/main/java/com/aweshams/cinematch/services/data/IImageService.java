package com.aweshams.cinematch.services.data;

/**
 * Created by irteza on 2018-05-24.
 */

import android.graphics.Bitmap;

import com.aweshams.cinematch.utils.promises.Promise;

import java.net.URL;

/**
 * Provides image services for fetching and manipulating images.
 */
public interface IImageService {

    /**
     * Gets a image as a {@link Bitmap} from a remote URL.
     *
     * @param imageUrl The remove URL of the image.
     * @return A promise of the {@link Bitmap} image.
     */
    Promise<Bitmap> getImage(URL imageUrl);
}
