package com.elkazajo.lastcaravan.levels;

import com.elkazajo.lastcaravan.levels.painters.SteppePainter;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.levels.RegularLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.elkazajo.lastcaravan.levels.rooms.OpenSteppeRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.entrance.EntranceRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.exit.ExitRoom;
import com.elkazajo.lastcaravan.levels.rooms.RoadRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.builders.Builder;
import com.shatteredpixel.shatteredpixeldungeon.levels.builders.LineBuilder;
import com.elkazajo.lastcaravan.levels.rooms.FarmRoom;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.elkazajo.lastcaravan.scenes.CaravanScene;
import com.watabou.noosa.Game;
import com.elkazajo.lastcaravan.LastCaravanRun;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;

import java.io.IOException;
import java.util.ArrayList;

public class SteppeLevel extends RegularLevel {

    private static final String WATER_OBJECTIVE_COMPLETED = "lc_water_objective_completed";

    private boolean waterObjectiveCompleted = false;

    private static final String EXPEDITION_RETURNED = "lc_expedition_returned";

    private boolean expeditionReturned = false;

    {
        color1 = 0xC7A96B;
        color2 = 0x8F7545;
    }

    @Override
    protected int nTraps() {
        return 0;
    }

    @Override
    protected Painter painter() {
        return new SteppePainter()
                .setWater(0.05f, 2)
                .setGrass(0.65f, 6);
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_SEWERS;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_SEWERS;
    }

    @Override
    protected ArrayList<Room> initRooms() {

        ArrayList<Room> rooms = new ArrayList<>();

        rooms.add(roomEntrance = EntranceRoom.createEntrance());

        rooms.add(new OpenSteppeRoom());
        rooms.add(new OpenSteppeRoom());

        rooms.add(new FarmRoom());

        rooms.add(new RoadRoom());
        rooms.add(new RoadRoom());

        rooms.add(roomExit = ExitRoom.createExit());

        return rooms;
    }

    @Override
    protected Builder builder() {

        LineBuilder builder = new LineBuilder();

        // All LAST CARAVAN areas should be part of the expedition route.
        builder.setPathLength(
                1f,
                new float[] { 1 });

        // Do not inject random SPD connection rooms.
        builder.setTunnelLength(
                new float[] { 1 },
                new float[] { 1 });

        // The route can bend rather than being perfectly straight.
        builder.setPathVariance(55f);

        // Occasionally connect areas which happen to touch.
        builder.setExtraConnectionChance(0.10f);

        return builder;
    }

    public boolean isWaterObjectiveCompleted() {
        return waterObjectiveCompleted;
    }

    public void completeWaterObjective() {

        if (waterObjectiveCompleted) {
            return;
        }

        waterObjectiveCompleted = true;

        GLog.p(
                Messages.get(
                        "lastcaravan.levels.steppelevel.objective_complete"));
        GLog.i(
                Messages.get(
                        "lastcaravan.levels.steppelevel.return_unlocked"));
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);

        bundle.put(
                WATER_OBJECTIVE_COMPLETED,
                waterObjectiveCompleted);
        bundle.put(
                EXPEDITION_RETURNED,
                expeditionReturned);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        waterObjectiveCompleted = bundle.getBoolean(WATER_OBJECTIVE_COMPLETED);
        expeditionReturned = bundle.getBoolean(EXPEDITION_RETURNED);
    }

    public boolean isExpeditionReturned() {
        return expeditionReturned;
    }

    @Override
    public boolean activateTransition(
            Hero hero,
            LevelTransition transition) {

        // LAST CARAVAN expedition does not continue to SPD floor 2.
        if (transition.type == LevelTransition.Type.REGULAR_EXIT) {

            GLog.w(
                    Messages.get(
                            "lastcaravan.levels.steppelevel.no_descent"));

            return false;
        }

        // Depth 1 entrance in SPD is a SURFACE transition.
        // We reuse it as the return point to the caravan.
        if (transition.type == LevelTransition.Type.SURFACE) {

            if (!waterObjectiveCompleted) {

                GLog.w(
                        Messages.get(
                                "lastcaravan.levels.steppelevel.return_locked"));

                return false;
            }

            if (!expeditionReturned) {

                expeditionReturned = true;

                LastCaravanRun.caravan().addWater(4);

                LastCaravanRun.enterCaravan();

                GLog.p(
                        Messages.get(
                                "lastcaravan.levels.steppelevel.expedition_complete"));

                try {
                    Dungeon.saveAll();
                } catch (IOException e) {
                    ShatteredPixelDungeon.reportException(e);
                }
            }

            Game.switchScene(CaravanScene.class);

            return true;
        }

        return super.activateTransition(hero, transition);
    }

    @Override
    protected void createMobs() {
        // Enemies are temporarily disabled while testing the Steppe prototype.
    }

    @Override
    public int mobLimit() {
        return 0;
    }
}