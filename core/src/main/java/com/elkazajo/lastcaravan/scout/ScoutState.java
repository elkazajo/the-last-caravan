package com.elkazajo.lastcaravan.scout;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class ScoutState implements Bundlable {

    private static final float TIME_PER_WATER = 20f;

    private static final String WATER = "water";
    private static final String MAX_WATER = "max_water";
    private static final String WATER_USE_PROGRESS = "water_use_progress";

    private int water;
    private int maxWater;
    private float waterUseProgress;

    public ScoutState() {
        reset();
    }

    public void reset() {
        maxWater = 100;
        water = maxWater;
        waterUseProgress = 0f;
    }

    public int water() {
        return water;
    }

    public int maxWater() {
        return maxWater;
    }

    public int restoreWater(int amount) {

        if (amount <= 0 || water >= maxWater) {
            return 0;
        }

        int restored = Math.min(amount, maxWater - water);
        water += restored;

        return restored;
    }

    public int spendExpeditionTime(float time) {

        if (time <= 0f || water <= 0) {
            return 0;
        }

        waterUseProgress += time;

        int waterSpent = Math.min(
                water,
                (int) (waterUseProgress / TIME_PER_WATER));

        if (waterSpent > 0) {
            water -= waterSpent;
            waterUseProgress -= waterSpent * TIME_PER_WATER;

            if (water == 0) {
                waterUseProgress = 0f;
            }
        }

        return waterSpent;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        bundle.put(WATER, water);
        bundle.put(MAX_WATER, maxWater);
        bundle.put(WATER_USE_PROGRESS, waterUseProgress);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {

        maxWater = bundle.contains(MAX_WATER)
                ? bundle.getInt(MAX_WATER)
                : 100;

        water = bundle.contains(WATER)
                ? bundle.getInt(WATER)
                : maxWater;

        waterUseProgress = bundle.contains(WATER_USE_PROGRESS)
                ? Math.max(0f, bundle.getFloat(WATER_USE_PROGRESS))
                : 0f;
    }
}
