package com.elkazajo.lastcaravan.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RatSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Jackal extends Mob {

    private static final String TERRITORY_CENTER = "territory_center";
    private static final String RETURNING_TO_TERRITORY = "returning_to_territory";

    private static final int PATROL_RADIUS = 6;
    private static final int MAX_CHASE_DISTANCE = 10;

    private int territoryCenter = -1;
    private boolean returningToTerritory;

    public enum Awareness {
        UNAWARE,
        ALERTED,
        ENGAGED
    }

    {
        // Temporary sprite until LAST CARAVAN gets its own jackal art.
        spriteClass = RatSprite.class;

        HP = HT = 18;

        defenseSkill = 5;

        // LAST CARAVAN does not reward XP for kills.
        EXP = 0;
        maxLvl = 0;

        WANDERING = new TerritoryWandering();
        state = WANDERING;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(3, 5);
    }

    @Override
    public int attackSkill(Char target) {
        return 10;
    }

    public void setTerritoryCenter(int cell) {
        territoryCenter = cell;
    }

    @Override
    protected boolean act() {

        if (territoryCenter == -1) {
            // Compatibility fallback for Jackals from old development saves.
            territoryCenter = pos;
        }

        if (!returningToTerritory
                && state == HUNTING
                && Dungeon.level.distance(pos, territoryCenter) >= MAX_CHASE_DISTANCE) {

            returningToTerritory = true;
            clearEnemy();
            target = territoryCenter;
        }

        if (returningToTerritory
                && Dungeon.level.distance(pos, territoryCenter) <= 1) {

            returningToTerritory = false;
            target = -1;
        }

        return super.act();
    }

    @Override
    protected Char chooseEnemy() {

        if (returningToTerritory) {
            return null;
        }

        return super.chooseEnemy();
    }

    public void hearNoise(int cell) {

        if ((state == HUNTING && enemySeen)
                || state == FLEEING
                || state == PASSIVE) {

            return;
        }

        if (territoryCenter != -1
                && Dungeon.level.distance(cell, territoryCenter) > MAX_CHASE_DISTANCE) {

            return;
        }

        returningToTerritory = false;
        clearEnemy();

        state = INVESTIGATING;
        target = cell;
        alerted = true;
    }

    private class TerritoryWandering extends Mob.Wandering {

        @Override
        protected int randomDestination() {

            if (territoryCenter == -1) {
                return pos;
            }

            int centerX = territoryCenter % Dungeon.level.width();
            int centerY = territoryCenter / Dungeon.level.width();

            int minX = Math.max(1, centerX - PATROL_RADIUS);
            int maxX = Math.min(Dungeon.level.width() - 2, centerX + PATROL_RADIUS);
            int minY = Math.max(1, centerY - PATROL_RADIUS);
            int maxY = Math.min(Dungeon.level.height() - 2, centerY + PATROL_RADIUS);

            int destination = territoryCenter;
            int tries = 30;

            do {
                int x = Random.IntRange(minX, maxX);
                int y = Random.IntRange(minY, maxY);
                destination = x + y * Dungeon.level.width();
                tries--;
            } while (tries > 0 && !isValidPatrolDestination(destination));

            return isValidPatrolDestination(destination)
                    ? destination
                    : territoryCenter;
        }
    }

    private boolean isValidPatrolDestination(int cell) {
        return Dungeon.level.passable[cell]
                && Dungeon.level.distance(cell, territoryCenter) <= PATROL_RADIUS
                && Dungeon.level.heaps.get(cell) == null
                && Dungeon.level.traps.get(cell) == null
                && Dungeon.level.plants.get(cell) == null;
    }

    public Awareness awareness() {

        if (enemySeen) {
            return Awareness.ENGAGED;
        }

        if (state == HUNTING || state == INVESTIGATING) {
            return Awareness.ALERTED;
        }

        return Awareness.UNAWARE;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);

        bundle.put(TERRITORY_CENTER, territoryCenter);
        bundle.put(RETURNING_TO_TERRITORY, returningToTerritory);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        if (bundle.contains(TERRITORY_CENTER)) {
            territoryCenter = bundle.getInt(TERRITORY_CENTER);
        }

        returningToTerritory = bundle.getBoolean(RETURNING_TO_TERRITORY);
    }

    @Override
    public String name() {
        return Messages.get(
                "lastcaravan.actors.mobs.jackal.name"
        );
    }

    @Override
    public String info() {

        String awarenessKey;

        switch (awareness()) {
            case ENGAGED:
                awarenessKey = "lastcaravan.actors.mobs.jackal.engaged";
                break;
            case ALERTED:
                awarenessKey = "lastcaravan.actors.mobs.jackal.alerted";
                break;
            default:
                awarenessKey = "lastcaravan.actors.mobs.jackal.unaware";
                break;
        }

        return Messages.get(
                "lastcaravan.actors.mobs.jackal.desc"
        ) + "\n\n" + Messages.get(
                "lastcaravan.actors.mobs.jackal.awareness",
                new Object[] { Messages.get(awarenessKey) }
        );
    }
}
