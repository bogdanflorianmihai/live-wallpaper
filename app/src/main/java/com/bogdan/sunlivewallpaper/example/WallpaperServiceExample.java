
package com.bogdan.sunlivewallpaper.example;

import com.bogdan.sunlivewallpaper.engine.WallpaperService;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Created by airtouch-nem-media-3 on 10/11/15.
 */
public class WallpaperServiceExample extends WallpaperService {

    private MyGdxCanvas mCanvas;

    @Override
    public void onCreateApplication() {
        super.onCreateApplication();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.disableAudio = true;
        mCanvas = new MyGdxCanvas();
        initialize(mCanvas, config);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mCanvas.dispose();
    }
}
