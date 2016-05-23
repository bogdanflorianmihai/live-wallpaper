
package com.bogdan.sunlivewallpaper.example;

import com.bogdan.sunlivewallpaper.Assets;
import com.bogdan.sunlivewallpaper.Element;
import com.bogdan.sunlivewallpaper.GdxCanvas;
import com.bogdan.sunlivewallpaper.Layer;
import com.bogdan.sunlivewallpaper.ParticleManager;
import com.bogdan.sunlivewallpaper.example.elements.Sun;
import com.bogdan.sunlivewallpaper.util.Constants;
import com.bogdan.sunlivewallpaper.util.WallpaperUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MyGdxCanvas extends GdxCanvas {

    @Override
    public void create() {
        super.create();

        WallpaperUtils.setupAssetsPath();
        Assets.getInstance().load(Constants.PATH + Constants.ASSETS_PACK, TextureAtlas.class,
                new Runnable() {
                    @Override
                    public void run() {
                        setup();
                    }
                });
        Assets.getInstance().finishLoading();
    }

    private void setup() {
        setupBackground();
        setupSun();
        setupButterflyPool();

    }

    private void setupBackground() {
        // create the background layer
        Layer backgroundLayer = new Layer();

        // add the layer to the canvas
        addLayer(backgroundLayer);

        // create the background
        Element background = new Element("background", new String[] {
                "bg",}, Constants.PATH + Constants.ASSETS_PACK, 0, 0, false);

        backgroundLayer.addElement(background);

        setCameraSize((int) background.getWidth(), (int) background.getHeight());
    }
    private void setupSun(){
        // create the sun layer
        Layer sunLayer = new Layer("sun");

        // add the layer to the canvas
        addLayer(sunLayer);

        // create the sun
        Sun sun = new Sun("sun", new String[] {
                "sun",
        }, Constants.PATH + Constants.ASSETS_PACK, WIDTH / 1.15f , HEIGHT / 1.1f, true);

        //sun.setOrigin(WIDTH / 2, HEIGHT / 2);
        sunLayer.addElement(sun);

    }
    private void setupButterflyPool() {
        // create the snow particle
        ParticleEffect butterflyEffect = new ParticleEffect();
        butterflyEffect.load(Gdx.files.internal("particles/butterfly.particle"),
                Gdx.files.internal("particles/"));

        // create snow pool
        ParticleEffectPool butterflyPool = new ParticleEffectPool(butterflyEffect, 50, 50);
        ParticleManager.getInstance().addPool(butterflyPool);

        setupButterfly();
    }
    private void setupButterfly() {
        ParticleEffectPool.PooledEffect butterfly = ParticleManager.getInstance().obtainEffect(Constants.BUTTERFLY_PARTICLE);
        butterfly.setPosition(0, HEIGHT/2);
        butterfly.scaleEffect(Gdx.graphics.getDensity() / 2.5f);

        // add the snow
        ParticleManager.getInstance().addEffect(butterfly);

        // update it so that it's already on the screen
        ParticleManager.getInstance().update(10f);
    }




    @Override
    public void resume() {
        super.resume();

        // if there aren't any running effects
        if (ParticleManager.getInstance().getRunningEffects().size < 1) {
            // setup the snow again
            setupButterfly();
        }
    }
}
