package com.elkazajo.lastcaravan.scout;

import com.elkazajo.lastcaravan.items.Bandage;
import com.elkazajo.lastcaravan.items.ScoutKnife;
import com.elkazajo.lastcaravan.items.ScoutWater;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;

public final class ScoutLoadout {

    private ScoutLoadout() {
    }

    public static void init(
            Hero hero,
            HeroClass selectedClass
    ) {

        HeroClass temporaryClass =
                selectedClass != null
                        ? selectedClass
                        : HeroClass.WARRIOR;

        // Temporary compatibility with SPD hero sprites/save data.
        hero.heroClass = temporaryClass;

        Talent.initClassTalents(hero);

        ScoutKnife knife =
                new ScoutKnife();

        knife.identify();

        hero.belongings.weapon =
                knife;

        ScoutWater water =
                new ScoutWater(2);

        water.collect(
                hero.belongings.backpack
        );

        Bandage bandage =
                new Bandage();

        bandage.collect(
                hero.belongings.backpack
        );

        Dungeon.quickslot.setSlot(
                0,
                bandage
        );
    }
}