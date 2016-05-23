
package com.bogdan.sunlivewallpaper;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by airtouch-nem-media-3 on 11/11/15.
 */
public class Layer implements Comparable<String> {

    private ArrayList<Element> mElements;
    private String mName;

    public Layer() {
        this("", new ArrayList<Element>());
    }

    public Layer(ArrayList<Element> elements) {
        this("", elements);
    }

    public Layer(String name) {
        this(name, new ArrayList<Element>());
    }

    public Layer(String name, ArrayList<Element> elements) {
        this.setElements(elements);
        this.setName(name);
    }

    public void act(float delta) {
        int numOfElements = getElements().size();
        for (int i = 0; i < numOfElements; i++) {
            getElements().get(i).act(delta);
        }
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        int numOfElements = getElements().size();
        for (int i = 0; i < numOfElements; i++) {
            getElements().get(i).draw(batch, parentAlpha);
        }
    }

    /**
     * Helpers
     */

    public void addElement(Element element) {
        mElements.add(element);
    }

    public Element getElementNamed(String name) {
        int index = -1;

        int numOfElements = mElements.size();
        for (int i = 0; i < numOfElements; i++) {
            if (mElements.get(i).getName().equals(name)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            return mElements.get(index);
        }

        return null;
    }

    /**
     * Getters, setters
     */

    public ArrayList<Element> getElements() {
        return mElements;
    }

    public void setElements(ArrayList<Element> elements) {
        mElements = elements;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public int compareTo(String o) {
        return getName().compareTo(o);
    }

}
