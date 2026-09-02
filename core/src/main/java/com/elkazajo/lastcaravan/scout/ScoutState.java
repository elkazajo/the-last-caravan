package com.elkazajo.lastcaravan.scout;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class ScoutState implements Bundlable {

    private static final float TIME_PER_WATER = 20f;
    private static final float TIME_PER_DEHYDRATION_DAMAGE = 20f;

    private static final String WATER = "water";
    private static final String MAX_WATER = "max_water";
    private static final String WATER_USE_PROGRESS = "water_use_progress";
    private static final String DEHYDRATION_DAMAGE_PROGRESS =
            "dehydration_damage_progress";

    private int water;
    private int maxWater;
    private float waterUseProgress;
    private float dehydrationDamageProgress;

    public enum Hydration {
        NORMAL,
        THIRSTY,
        CRITICAL,
        DEHYDRATED;

        public boolean isImpaired() {
            return this == CRITICAL || this == DEHYDRATED;
        }
    }

    public ScoutState() {
        reset();
    }

    public void reset() {
        maxWater = 100;
        water = maxWater;
        waterUseProgress = 0f;
        dehydrationDamageProgress = 0f;
    }

    public int water() {
        return water;
    }

    public int maxWater() {
        return maxWater;
    }

    public Hydration hydration() {

        if (water <= 0) {
            return Hydration.DEHYDRATED;
        }

        if (water * 4 <= maxWater) {
            return Hydration.CRITICAL;
        }

        if (water * 2 <= maxWater) {
            return Hydration.THIRSTY;
        }

        return Hydration.NORMAL;
    }

    public int restoreWater(int amount) {

        if (amount <= 0 || water >= maxWater) {
            return 0;
        }

        int restored = Math.min(amount, maxWater - water);
        water += restored;
        dehydrationDamageProgress = 0f;

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

    public int spendDehydratedTime(float time) {

        if (time <= 0f || water > 0) {
            return 0;
        }

        dehydrationDamageProgress += time;

        int damage = (int) (
                dehydrationDamageProgress
                        / TIME_PER_DEHYDRATION_DAMAGE);

        if (damage > 0) {
            dehydrationDamageProgress -=
                    damage * TIME_PER_DEHYDRATION_DAMAGE;
        }

        return damage;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        bundle.put(WATER, water);
        bundle.put(MAX_WATER, maxWater);
        bundle.put(WATER_USE_PROGRESS, waterUseProgress);
        bundle.put(
                DEHYDRATION_DAMAGE_PROGRESS,
                dehydrationDamageProgress);
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

        dehydrationDamageProgress =
                bundle.contains(DEHYDRATION_DAMAGE_PROGRESS)
                        ? Math.max(
                                0f,
                                bundle.getFloat(
                                        DEHYDRATION_DAMAGE_PROGRESS))
                        : 0f;
    }
}
