package com.elkazajo.lastcaravan.noise;

import com.elkazajo.lastcaravan.actors.mobs.Jackal;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;

public final class NoiseSystem {

    private NoiseSystem() {
    }

    public static void emit(int cell, int radius) {

        if (radius <= 0 || Dungeon.level == null) {
            return;
        }

        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {

            if (mob instanceof Jackal
                    && Dungeon.level.distance(cell, mob.pos) <= radius) {

                ((Jackal) mob).hearNoise(cell);
            }
        }
    }
}
