
package com.bogdan.sunlivewallpaper;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GdxCanvas extends ApplicationAdapter {
    public static String TAG = "GdxCanvas";

    public static int WIDTH;
    public static int HEIGHT;

    private SpriteBatch mBatch;

    private OrthographicCamera mCamera;
    private Viewport mViewport;
    private Stage mStage;

    private ArrayList<Layer> mLayers;

    @Override
    public void create() {

        // set the screen size
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        mLayers = new ArrayList<Layer>();

        mBatch = new SpriteBatch();

        // setup the stage
        mStage = new Stage();

        // setup the camera
        setCameraSize(WIDTH, HEIGHT);

        // setup the asset manager
        Assets.getInstance().init(new AssetManager());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        mViewport.update(width, height, true);

        System.out.println(width + " " + height);
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();

        // update all layers
        int numOfLayers = mLayers.size();
        for (int i = 0; i < numOfLayers; i++) {
            mLayers.get(i).act(delta);
        }

        // update the particle manager
        ParticleManager.getInstance().update(delta);
    }

    @Override
    public void render() {
        // update
        update();

        // clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // setup camera
        mViewport.apply(true);
        mBatch.setProjectionMatrix(mCamera.combined);

        // begin draw
        mBatch.begin();

        // draw all layers
        int numOfLayers = mLayers.size();
        for (int i = 0; i < numOfLayers; i++) {
            mLayers.get(i).draw(mBatch, 1f);
        }

        // draw the particles
        // TODO: - maybe get rid of the particle manager and implement the particles as elements in
        // order to support multi layer rendering
        ParticleManager.getInstance().draw(mBatch);

        // end draw
        mBatch.end();
    }

    @Override
    public void dispose() {
        super.dispose();

        Assets.getInstance().dispose();
    }

    /**
     * Helpers
     */

    public void addLayer(Layer layer) {
        mLayers.add(layer);
    }

    public Layer getLayerNamed(String name) {
        int index = -1;

        int numOfLayers = mLayers.size();
        for (int i = 0; i < numOfLayers; i++) {
            if (mLayers.get(i).getName().equals(name)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            return mLayers.get(index);
        }

        return null;
    }

    public void setCameraSize(int width, int height) {
        WIDTH = width;
        HEIGHT = height;

        mCamera = new OrthographicCamera();
        mViewport = new FillViewport(WIDTH, HEIGHT, mCamera);
        mViewport.apply(true);
        mStage.setViewport(mViewport);

        resize(WIDTH, HEIGHT);
    }

    /**
     * Getters, setters
     */

    public ArrayList<Layer> getLayers() {
        return mLayers;
    }

}
