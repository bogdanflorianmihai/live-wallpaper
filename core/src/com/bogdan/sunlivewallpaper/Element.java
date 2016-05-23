
package com.bogdan.sunlivewallpaper;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by airtouch-nem-media-3 on 11/11/15.
 */
public class Element extends Actor implements Comparable<String> {

    private ArrayList<Sprite> mFrames;
    private Sprite mCurrentFrame;

    private int mCurrentFrameIndex = 0;

    private boolean mCentered;

    private boolean mSizeAlreadySet = false;

    public Element() {
        super();
    }

    public Element(String name, ArrayList<Sprite> frames, float x, float y, boolean centered) {
        this(frames, x, y, centered);

        setName(name);
    }

    public Element(String name, String[] frames, float x, float y, boolean centered) {
        this(name, new ArrayList<Sprite>(), x, y, centered);

        setFramesNamed(frames);
    }

    public Element(String name, String[] frames, String atlasName, float x, float y,
            boolean centered) {
        this(name, new ArrayList<Sprite>(), x, y, centered);

        setFramesNamed(frames, atlasName);
    }

    public Element(ArrayList<Sprite> frames, float x, float y, boolean centered) {
        super();

        this.setFrames(frames);
        this.setCentered(centered);

        // set the element size based on the first frame
        updateElementSize();

        // set the current frame
        if (frames.size() > 0) {
            Sprite frame = frames.get(0);
            setCurrentFrame(frame);
        } else {
            setCurrentFrame(new Sprite(new Texture(1, 1, Pixmap.Format.RGB888)));
        }

        // position the element
        this.setPosition(x, y);

        // position the frames
        for (Sprite frame : frames) {
            if (centered) {
                frame.setCenter(x, y);
            } else {
                frame.setPosition(x, y);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // draw the current frame
        if (isVisible()) {
            mCurrentFrame.draw(batch, parentAlpha);
        }
    }

    /**
     * Helpers
     */

    public void updateFramePosition() {
        if (isCentered())
            getCurrentFrame().setCenter(getX() + getWidth() / 2f, getY() + getHeight() / 2f);
        else
            getCurrentFrame().setPosition(getX(), getY());
    }

    public void updateFramesPosition() {
        int numOfFrames = getFrames().size();
        for (int i = 0; i < numOfFrames; i++) {
            if (isCentered())
                getFrames().get(i).setCenter(getX() + getWidth() / 2f, getY() + getHeight() / 2f);
            else
                getFrames().get(i).setPosition(getX(), getY());
        }
    }

    public void updateFrameAlpha() {
        mCurrentFrame.setAlpha(getColor().a);

        if (getColor().a < 0.0001f)
            setVisible(false);
        else
            setVisible(true);
    }

    public void updateFrameRotation() {
        getCurrentFrame().setRotation(getRotation());
    }

    public void updateFrameScale() {
        getCurrentFrame().setScale(getScaleX(), getScaleY());
    }

    public void updateCurrentFrame() {
        // update the current frame's position
        updateFramePosition();

        // update the current frame's alpha
        updateFrameAlpha();
    }

    public void updateElementSize() {
        if (getFrames().size() > 0) {
            Sprite frame = getFrames().get(0);
            this.setSize(frame.getWidth(), frame.getHeight());

            if (isCentered())
                if (!mSizeAlreadySet) {
                    this.setPosition(getX() - getWidth() / 2f, getY() - getHeight() / 2f);
                    mSizeAlreadySet = true;
                }
        } else {
            this.setSize(1, 1);
        }
    }

    public void setFramesNamed(final String[] frames) {
        final ArrayList<Sprite> tempFrames = new ArrayList<Sprite>();

        // load each frame
        for (int i = 0; i < frames.length; i++) {
            tempFrames.add(new Sprite(new Texture(1, 1, Pixmap.Format.RGB888)));

            if (Assets.getInstance().isLoaded(frames[i])) {

                // if it's already loaded, set the texture to the sprite
                tempFrames.get(i)
                        .set(new Sprite(Assets.getInstance().get(frames[i], Texture.class)));

            } else {

                // else add it to the load queue
                final int index = i;
                Assets.getInstance().load(frames[i], Texture.class, new Runnable() {
                    @Override
                    public void run() {
                        // after it's loaded, set the texture to the sprite
                        tempFrames.get(index).set(
                                new Sprite(Assets.getInstance().get(frames[index], Texture.class)));
                    }
                });
            }
        }
        Assets.getInstance().finishLoading();
        setFrames(tempFrames);
        updateElementSize();
        updateFramesPosition();
    }

    public void setFramesNamed(final String[] frames, String atlasName) {
        final ArrayList<Sprite> tempFrames = new ArrayList<Sprite>();

        TextureAtlas atlas = Assets.getInstance().get(atlasName, TextureAtlas.class);

        // load each frame
        for (int i = 0; i < frames.length; i++) {
            TextureAtlas.AtlasRegion frame = atlas.findRegion(frames[i]);
            frame.getTexture().setFilter(Texture.TextureFilter.Linear,
                    Texture.TextureFilter.Linear);

            tempFrames.add(new Sprite(frame));
        }
        setFrames(tempFrames);
        updateElementSize();
        updateFramesPosition();
    }

    /**
     * Getters, setters
     */

    public ArrayList<Sprite> getFrames() {
        return mFrames;
    }

    public void setFrames(ArrayList<Sprite> frames) {
        mFrames = frames;

        if (mFrames.size() > 0) {
            mCurrentFrame = mFrames.get(0);
        }
    }

    public Sprite getCurrentFrame() {
        return mCurrentFrame;
    }

    public void setCurrentFrame(Sprite currentFrame) {
        mCurrentFrame = currentFrame;
    }

    public boolean isCentered() {
        return mCentered;
    }

    public void setCentered(boolean centered) {
        mCentered = centered;
    }

    public int getCurrentFrameIndex() {
        return mCurrentFrameIndex;
    }

    public void setCurrentFrameIndex(int currentFrameIndex) {
        mCurrentFrameIndex = currentFrameIndex;
    }

    @Override
    public int compareTo(String o) {
        return getName().compareTo(o);
    }
}
