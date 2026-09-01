package com.elkazajo.lastcaravan;

import com.elkazajo.lastcaravan.caravan.CaravanState;
import com.watabou.utils.Bundle;

public final class LastCaravanRun {

    private static final String CARAVAN_STATE = "last_caravan_caravan_state";

    private static CaravanState caravanState = new CaravanState();

    private LastCaravanRun() {
    }

    private static final String RUN_PHASE = "last_caravan_run_phase";

    private static final String EXPEDITION_NUMBER = "last_caravan_expedition_number";

    private static int expeditionNumber = 0;

    public enum Phase {
        EXPEDITION,
        CARAVAN
    }

    private static Phase phase = Phase.EXPEDITION;

    public static CaravanState caravan() {
        return caravanState;
    }

    public static void reset() {
        caravanState = new CaravanState();
        phase = Phase.EXPEDITION;
        expeditionNumber = 0;
    }

    public static void storeInBundle(Bundle bundle) {
        bundle.put(
                CARAVAN_STATE,
                caravanState);

        bundle.put(
                RUN_PHASE,
                phase);

        bundle.put(
                EXPEDITION_NUMBER,
                expeditionNumber);
    }

    public static void restoreFromBundle(Bundle bundle) {

        if (bundle.contains(CARAVAN_STATE)) {

            caravanState = (CaravanState) bundle.get(CARAVAN_STATE);

        } else {

            reset();
        }

        if (bundle.contains(RUN_PHASE)) {
            phase = bundle.getEnum(
                    RUN_PHASE,
                    Phase.class);
        } else {
            phase = Phase.EXPEDITION;
        }

        if (bundle.contains(EXPEDITION_NUMBER)) {
            expeditionNumber = bundle.getInt(EXPEDITION_NUMBER);
        } else {
            expeditionNumber = 0;
        }
    }

    public static Phase phase() {
        return phase;
    }

    public static void startExpedition() {
        phase = Phase.EXPEDITION;
    }

    public static void enterCaravan() {
        phase = Phase.CARAVAN;
    }

    public static int expeditionNumber() {
        return expeditionNumber;
    }

    public static void beginNextExpedition() {
        expeditionNumber++;
        phase = Phase.EXPEDITION;
    }

    public static long expeditionSeed(long baseSeed) {
        return baseSeed + 1_000_003L * expeditionNumber;
    }
}