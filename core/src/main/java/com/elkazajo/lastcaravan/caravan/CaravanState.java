package com.elkazajo.lastcaravan.caravan;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public class CaravanState implements Bundlable {

    private static final String POPULATION = "population";
    private static final String FOOD = "food";
    private static final String WATER = "water";
    private static final String MEDICINE = "medicine";
    private static final String MORALE = "morale";

    private int population;
    private int food;
    private int water;
    private int medicine;
    private int morale;

    public CaravanState() {
        reset();
    }

    public void reset() {
        population = 30;
        food = 24;
        water = 24;
        medicine = 2;
        morale = 70;
    }

    public int population() {
        return population;
    }

    public int food() {
        return food;
    }

    public int water() {
        return water;
    }

    public int medicine() {
        return medicine;
    }

    public int morale() {
        return morale;
    }

    public void addWater(int amount) {
        water += amount;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        bundle.put(POPULATION, population);
        bundle.put(FOOD, food);
        bundle.put(WATER, water);
        bundle.put(MEDICINE, medicine);
        bundle.put(MORALE, morale);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        population = bundle.getInt(POPULATION);
        food = bundle.getInt(FOOD);
        water = bundle.getInt(WATER);
        medicine = bundle.getInt(MEDICINE);
        morale = bundle.getInt(MORALE);
    }
}