package com.elkazajo.lastcaravan;

import com.elkazajo.lastcaravan.caravan.CaravanState;
import com.watabou.utils.Bundle;

public final class LastCaravanRun {

    private static final String CARAVAN_STATE = "last_caravan_caravan_state";

    private static CaravanState caravanState = new CaravanState();

    private LastCaravanRun() {
    }

    private static final String RUN_PHASE = "last_caravan_run_phase";

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
    }

    public static void storeInBundle(Bundle bundle) {
        bundle.put(
                CARAVAN_STATE,
                caravanState);

        bundle.put(
                RUN_PHASE,
                phase);
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
}