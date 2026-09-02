package com.elkazajo.lastcaravan.scout;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class ScoutState implements Bundlable {

    private static final String WATER = "water";
    private static final String MAX_WATER = "max_water";

    private int water;
    private int maxWater;

    public ScoutState() {
        reset();
    }

    public void reset() {
        maxWater = 100;
        water = maxWater;
    }

    public int water() {
        return water;
    }

    public int maxWater() {
        return maxWater;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        bundle.put(WATER, water);
        bundle.put(MAX_WATER, maxWater);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {

        maxWater = bundle.contains(MAX_WATER)
                ? bundle.getInt(MAX_WATER)
                : 100;

        water = bundle.contains(WATER)
                ? bundle.getInt(WATER)
                : maxWater;
    }
}
