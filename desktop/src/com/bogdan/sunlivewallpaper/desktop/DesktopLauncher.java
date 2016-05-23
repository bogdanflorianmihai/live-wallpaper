
package com.bogdan.sunlivewallpaper.desktop;

import com.bogdan.sunlivewallpaper.Assets;
import com.bogdan.sunlivewallpaper.Element;
import com.bogdan.sunlivewallpaper.GdxCanvas;
import com.bogdan.sunlivewallpaper.Layer;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 480;
        config.height = 820;
        config.disableAudio = true;
        new LwjglApplication(new MyGdxCanvas(), config);
    }

    public static class MyGdxCanvas extends GdxCanvas {

        private String path = "xxhdpi/";

        @Override
        public void create() {
            super.create();

            setup();
        }

        private void setup() {
            setupBackground();
//            setupEarth();
//            setupOverlay();
//            setupSatelite();
        }

        private void setupBackground() {
            // create the background layer
            Layer backgroundLayer = new Layer();

            // add the layer to the canvas
            addLayer(backgroundLayer);

            // create the background element
            Element backgroundElement = new Element("background", new String[]{
                    path + "1bkg.png",
            }, 0, 0, false);

            Assets.getInstance().finishLoading();
            Texture bg = Assets.getInstance().get(path + "1bkg.png", Texture.class);
            setCameraSize(bg.getWidth(), bg.getHeight());

            // add the element to the layer
            backgroundLayer.addElement(backgroundElement);
        }

        private void setupEarth() {
            // create the earth layer
            Layer earthLayer = new Layer();

            // add the layer to the canvas
            addLayer(earthLayer);

            // create the earth
            Element earth = new Element("earth", new String[]{
                    path + "earth.png",
            }, 0, MathUtils.random(HEIGHT) / 2, true);

            earthLayer.addElement(earth);
        }

        private void setupOverlay() {
            // create the overlay layer
            Layer overlayLayer = new Layer();

            // add the layer to the canvas
            addLayer(overlayLayer);

            // create the overlay
            Element overlay1 = new Element("overlay1", new String[]{
                    path + "intermediate-bkg.png",
            }, 0, 0, true);
            Element overlay2 = new Element("overlay2", new String[]{
                    path + "4bkg.png",
            }, 0, 0, true);

            overlayLayer.addElement(overlay1);
            overlayLayer.addElement(overlay2);
        }

        private void setupSatelite() {
            // create the satelite layer
            Layer sateliteLayer = new Layer();

            // add the layer to the canvas
            addLayer(sateliteLayer);

            // create the satelite
            Element satelite = new Element("satelite", new String[]{
                    path + "satellite-v2-darker.png",
            }, 0, 0, true);

            sateliteLayer.addElement(satelite);
        }

    }
}
