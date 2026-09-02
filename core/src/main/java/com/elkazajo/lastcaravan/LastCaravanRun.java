package com.elkazajo.lastcaravan;

import com.elkazajo.lastcaravan.caravan.CaravanState;
import com.elkazajo.lastcaravan.scout.ScoutState;
import com.elkazajo.lastcaravan.scout.ScoutSurvival;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.watabou.utils.Bundle;

public final class LastCaravanRun {

    private static final String CARAVAN_STATE = "last_caravan_caravan_state";
    private static final String SCOUT_STATE = "last_caravan_scout_state";

    private static CaravanState caravanState = new CaravanState();
    private static ScoutState scoutState = new ScoutState();

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

    public static ScoutState scout() {
        return scoutState;
    }

    public static void spendScoutTime(Hero hero, float time) {

        if (phase != Phase.EXPEDITION) {
            return;
        }

        ScoutSurvival.spendTime(hero, scoutState, time);
    }

    public static void reset() {
        caravanState = new CaravanState();
        scoutState = new ScoutState();
        phase = Phase.EXPEDITION;
        expeditionNumber = 0;
    }

    public static void storeInBundle(Bundle bundle) {
        bundle.put(
                CARAVAN_STATE,
                caravanState);

        bundle.put(
                SCOUT_STATE,
                scoutState);

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

        if (bundle.contains(SCOUT_STATE)) {

            scoutState = (ScoutState) bundle.get(SCOUT_STATE);

        } else {

            // Fallback for saves created before personal water existed.
            scoutState = new ScoutState();
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

    public static void fillSaveInfo(
            GamesInProgress.Info info) {

        info.caravanPopulation = caravanState.population();

        info.caravanFood = caravanState.food();

        info.caravanWater = caravanState.water();

        info.caravanMedicine = caravanState.medicine();

        info.caravanMorale = caravanState.morale();

        // Internally the first expedition is 0.
        // In UI it is expedition 1.
        info.expeditionNumber = expeditionNumber + 1;

        info.atCaravan = phase == Phase.CARAVAN;
    }

    public static void previewSaveInfo(
            GamesInProgress.Info info,
            Bundle bundle) {

        CaravanState savedCaravan = null;

        if (bundle.contains(CARAVAN_STATE)) {

            savedCaravan = (CaravanState) bundle.get(
                    CARAVAN_STATE);
        }

        if (savedCaravan != null) {

            info.caravanPopulation = savedCaravan.population();

            info.caravanFood = savedCaravan.food();

            info.caravanWater = savedCaravan.water();

            info.caravanMedicine = savedCaravan.medicine();

            info.caravanMorale = savedCaravan.morale();

        } else {

            // Fallback for old development saves.
            info.caravanPopulation = 30;
            info.caravanFood = 24;
            info.caravanWater = 24;
            info.caravanMedicine = 2;
            info.caravanMorale = 70;
        }

        int savedExpedition = 0;

        if (bundle.contains(EXPEDITION_NUMBER)) {
            savedExpedition = bundle.getInt(EXPEDITION_NUMBER);
        }

        info.expeditionNumber = savedExpedition + 1;

        if (bundle.contains(RUN_PHASE)) {

            Phase savedPhase = bundle.getEnum(
                    RUN_PHASE,
                    Phase.class);

            info.atCaravan = savedPhase == Phase.CARAVAN;

        } else {

            info.atCaravan = false;
        }
    }
}
