
package com.bogdan.sunlivewallpaper;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Created by airtouch-nem-media-3 on 11/12/15.
 */
public class ParticleManager {

    public static final String TAG = "ParticleManager";

    private static volatile ParticleManager mInstance;

    private volatile Array<ParticleEffectPool> mEffectPools = new Array<ParticleEffectPool>();
    private volatile Array<ParticleEffectPool.PooledEffect> mRunningEffects = new Array<ParticleEffectPool.PooledEffect>();

    public synchronized static ParticleManager getInstance() {
        if (mInstance == null) {
            mInstance = new ParticleManager();
        }
        return mInstance;
    }

    public void update(float dt) {
        for (int i = mRunningEffects.size - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = mRunningEffects.get(i);
            effect.update(dt);
            if (effect.isComplete()) {
                effect.free();
                mRunningEffects.removeIndex(i);
            }
        }
    }

    public void draw(SpriteBatch batch) {
        for (int i = mRunningEffects.size - 1; i >= 0; i--) {
            mRunningEffects.get(i).draw(batch);
        }
    }

    public void clear() {
        for (int i = mRunningEffects.size - 1; i >= 0; i--)
            mRunningEffects.get(i).free();
        mRunningEffects.clear();
    }

    public void reset() {
        for (int i = mRunningEffects.size - 1; i >= 0; i--)
            mRunningEffects.get(i).reset();
    }

    public void addPool(ParticleEffectPool pool) {
        mEffectPools.add(pool);
    }

    public ParticleEffectPool.PooledEffect obtainEffect(short effectId) {
        return mEffectPools.get(effectId).obtain();
    }

    public void addEffect(ParticleEffectPool.PooledEffect effect) {
        mRunningEffects.add(effect);
    }

    public Array<ParticleEffectPool.PooledEffect> getRunningEffects() {
        return mRunningEffects;
    }
}
