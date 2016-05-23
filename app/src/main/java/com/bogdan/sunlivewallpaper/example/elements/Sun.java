package com.bogdan.sunlivewallpaper.example.elements;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.bogdan.sunlivewallpaper.Element;

import java.util.ArrayList;

/**
 * Created by Bogdan on 5/19/2016.
 */
public class Sun extends Element {
    private float mOriginalX;
    private float mOriginalY;

    public Sun(String name, ArrayList<Sprite> frames, float x, float y, boolean centered) {
        super(name, frames, x, y, centered);
        setup();
    }

    public Sun(String name, String[] frames, float x, float y, boolean centered) {
        super(name, frames, x, y, centered);
        setup();
    }

    public Sun(String name, String[] frames, String atlasName, float x, float y, boolean centered) {
        super(name, frames, atlasName, x, y, centered);
        setup();
    }

    public Sun(ArrayList<Sprite> frames, float x, float y, boolean centered) {
        super(frames, x, y, centered);
        setup();
    }
    private void setup(){
        mOriginalX = getX();
        mOriginalY = getY();

        // setup the actions
        resetActions();
    }
    private void resetActions(){
        Action rotate = Actions.rotateBy(360, 30f);
        addAction(Actions.forever(rotate));

    }
    @Override
    public void act(float delta) {
        super.act(delta);

        updateFrameRotation();

    }
    @Override
    public void setOrigin(float originX, float originY) {
        super.setOrigin(originX, originY);

        getCurrentFrame().setOrigin(originX, originY);
    }
}

