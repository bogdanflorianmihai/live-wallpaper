
package com.bogdan.sunlivewallpaper;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by airtouch-nem-media-3 on 12/11/15.
 */
public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = "Assets";

    private static volatile Assets mInstance;

    private volatile AssetManager mAssetManager;
    private volatile ArrayList<Runnable> mQueue;

    private Assets() {
        init(new AssetManager());
    }

    public synchronized static Assets getInstance() {
        if (mInstance == null) {
            mInstance = new Assets();
        }
        return mInstance;
    }

    public void init(AssetManager assetManager) {
        if (mAssetManager != null)
            mAssetManager.dispose();

        this.mAssetManager = assetManager;
        this.mQueue = new ArrayList<Runnable>();

        mAssetManager.setErrorListener(this);
    }

    public synchronized boolean update() {
        if (mAssetManager == null) {
            return true;
        }

        if (mAssetManager.update()) {
            while (!mQueue.isEmpty()) {
                mQueue.remove(0).run();
            }
            return true;
        }
        return false;
    }

    public synchronized void finishLoading() {
        if (mAssetManager != null) {
            mAssetManager.finishLoading();
            while (!mQueue.isEmpty()) {
                mQueue.remove(0).run();
            }
        }
    }

    public synchronized float getProgress() {
        if (mAssetManager == null) {
            return 1.0f;
        }

        return mAssetManager.getProgress();
    }

    public synchronized <T> void load(String fileName, Class<T> type) {
        if (mAssetManager != null)
            mAssetManager.load(fileName, type);
    }

    public synchronized <T> void load(String fileName, Class<T> type, Runnable onCompleted) {
        if (mAssetManager != null) {
            load(fileName, type);
            mQueue.add(onCompleted);
        }
    }

    public synchronized void unload(String fileName) {
        if (mAssetManager != null)
            if (mAssetManager.isLoaded(fileName))
                mAssetManager.unload(fileName);
    }

    public synchronized boolean isLoaded(String fileName) {
        if (mAssetManager != null)
            return mAssetManager.isLoaded(fileName);
        return false;
    }

    public synchronized <T> T get(String fileName, Class<T> type) {
        if (mAssetManager != null)
            return mAssetManager.get(fileName, type);
        return null;
    }

    @Override
    public void dispose() {
        if (mAssetManager != null) {
            mAssetManager.dispose();
        }
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", throwable);
    }
}
