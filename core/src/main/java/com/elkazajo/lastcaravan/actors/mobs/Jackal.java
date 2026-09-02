package com.elkazajo.lastcaravan.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RatSprite;
import com.watabou.utils.Random;

public class Jackal extends Mob {

    {
        // Temporary sprite until LAST CARAVAN gets its own jackal art.
        spriteClass = RatSprite.class;

        HP = HT = 18;

        defenseSkill = 5;

        // LAST CARAVAN does not reward XP for kills.
        EXP = 0;
        maxLvl = 0;

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

    @Override
    public String name() {
        return Messages.get(
                "lastcaravan.actors.mobs.jackal.name"
        );
    }

    @Override
    public String info() {
        return Messages.get(
                "lastcaravan.actors.mobs.jackal.desc"
        );
    }
}