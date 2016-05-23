
package com.bogdan.sunlivewallpaper.example;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.bogdan.sunlivewallpaper.util.WallpaperUtils;

public class SetWallpaperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(com.bogdan.sunlivewallpaper.R.layout.activity_set_wallpaper);

        ImageButton activateButton = (ImageButton) findViewById(com.bogdan.sunlivewallpaper.R.id.activate);
        ImageButton moreButton = (ImageButton) findViewById(com.bogdan.sunlivewallpaper.R.id.more);

        activateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeWallpaper();
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(com.bogdan.sunlivewallpaper.R.string.url);

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        // setup the assets path based on density
        WallpaperUtils.setupAssetsPath(this);
    }

    public void onClick(View view) {
        changeWallpaper();
    }

    private void changeWallpaper() {
        Intent intent = new Intent();

        if (Build.VERSION.SDK_INT >= 16) {
            intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    new ComponentName(this, WallpaperServiceExample.class));
        } else {
            intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        }

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
