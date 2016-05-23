
package com.bogdan.sunlivewallpaper.util;

import android.content.Context;

import com.badlogic.gdx.Gdx;

/**
 * Created by airtouch-nem-media-3 on 13/11/15.
 */
public class WallpaperUtils {

    public static void setupAssetsPath() {
        float density = Gdx.graphics.getDensity();

        setupAssetsPath(density);
    }

    public static void setupAssetsPath(Context context) {
        float density = context.getResources().getDisplayMetrics().density;

        setupAssetsPath(density);
    }

    public static void setupAssetsPath(float density) {
        String path;

        if (density <= 1.0f)
            path = "mdpi/";
        else if (density <= 1.5f)
            path = "hdpi/";
        else if (density <= 2.0f)
            path = "xhdpi/";
        else
            path = "xxhdpi/";

        Constants.PATH = path;
    }

}
