
package com.bogdan.sunlivewallpaper.engine;

import java.util.Calendar;
import java.util.Date;

import android.content.SharedPreferences;
import android.util.Log;

import com.bogdan.sunlivewallpaper.util.Constants;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;

/**
 * Created by airtouch-nem-media-3 on 06/11/15.
 */
public class WallpaperService extends AndroidLiveWallpaperService {

    public static final String TAG = "WallpaperService";

    protected int mUnlockedLast, mUnlockedNow;
    protected long mInstallTime, mTotalTime;
    protected Calendar mCalendar;

    @Override
    public void onCreate() {
        super.onCreate();

        mCalendar = Calendar.getInstance();
    }

    @Override
    public Engine onCreateEngine() {
        setupPreferences();

        return new AndroidWallpaperEngine();
    }

    /**
     * Helper function. Sets up the preferences
     */
    protected void setupPreferences() {
        SharedPreferences prefs = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
        Date date = new Date();

        mUnlockedNow = 0;
        mUnlockedLast = 0;

        if (prefs.getBoolean(Constants.FIRST_TIME, true)) {
            SharedPreferences.Editor editor = prefs.edit();

            mInstallTime = System.currentTimeMillis();

            editor.putBoolean(Constants.FIRST_TIME, false);
            editor.putString(Constants.INSTALL_DATE, date.toString());
            editor.putInt(Constants.UNLOCKED_NOW, mUnlockedNow);
            editor.putLong(Constants.INSTALL_TIME_MILLIS, mInstallTime);

            editor.commit();

            Log.d(TAG, "Install time: " + mInstallTime);
        } else {
            mInstallTime = prefs.getLong(Constants.INSTALL_TIME_MILLIS, 0);
            mUnlockedNow = prefs.getInt(Constants.UNLOCKED_NOW, 0);
            mUnlockedLast = mUnlockedNow;

            Log.d(TAG, "Installed at: " + mInstallTime);
        }
    }

}
